package com.versionsystem.app.downloadCustomer.service;

import com.versionsystem.app.downloadCustomer.model.DownloadCustomerUI;
import com.versionsystem.app.downloadCustomer.repo.ClassFilesRepository;
import com.versionsystem.app.downloadCustomer.repo.DownloadCustomerRepository;
import com.versionsystem.basic.user.UserService;
import com.versionsystem.common.*;
import com.versionsystem.persistence.model.ClassFiles;
import com.versionsystem.persistence.model.ClassFilesId;
import com.versionsystem.persistence.model.DownloadCustomer;
import com.versionsystem.service.impl.FileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DownloadCustomerService {

	@Autowired
	private DownloadCustomerRepository repository;
	@Autowired
	private ClassFilesRepository classFilesRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private FileService fileService;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	private final String DEFAULT_COMPANY = "VOC";

	public List<DownloadCustomerUI> listBy(String company) {
		List<DownloadCustomerUI> rl = new ArrayList<>();
		for (DownloadCustomer po : repository.getByCompany(company, DateTools.paraseStringToDate("2012-04-01", null),
			DateTools.paraseStringToDate("3010-03-31", null))) {
			DownloadCustomerUI vo = new DownloadCustomerUI();
			BeanUtils.copyProperties(po, vo);
			vo.setClasses(queryFileClasses(po.getId()));
			rl.add(vo);
		}
		return rl;
	}

	public List<DownloadCustomerUI> findByClass(String company) {
		String userClass = userService.getCurrentUserClass();
		if ("".equals(userClass)) {
			return listBy(company);
		}
		List<DownloadCustomerUI> rl = new ArrayList<>();
		for (DownloadCustomer po : repository.getByCompanyAndClass(company, userClass, DateTools.paraseStringToDate("2012-04-01", null),
			DateTools.paraseStringToDate("3010-03-31", null))) {
			DownloadCustomerUI vo = new DownloadCustomerUI();
			BeanUtils.copyProperties(po, vo);
			rl.add(vo);
		}
		return rl;
	}

	public List<String> getPatientCategory(String company) {
		DataList maps = new DataList();
		List<String> result = maps.singleValList("patientCategory");
		maps.parse(jdbcTemplate.queryForList("SELECT DISTINCT PATIENT_CATEGORY from PATIENT where COMPANY=? AND PATIENT_CATEGORY IS NOT NULL", company));
		return result;
	}

	@Transactional
	public DownloadCustomerUI create(DownloadCustomerUI vo, CommonsMultipartFile file) throws IOException {
		if (file == null)
			throw ApplicationException.PARAM_ERROR;
		validateBeforeSave(vo);

		DataMap dm = fileService.uploadFile(file, vo.getFilename());
		DownloadCustomer po = new DownloadCustomer();
		BeanUtils.copyProperties(vo, po);
		po.setFilename(dm.val("fileName"));
		po.setUpdatetime(new Date());
		po.setUploadtime(po.getUpdatetime());
		po = repository.saveAndFlush(po);
		for (String cate : vo.getClasses()) {
			jdbcTemplate.update("INSERT INTO CLASS_FILES(CLASS, DOCID, SEQ, COMPANY) VALUES (?, ?, ?, ?)",
				cate, po.getId(), getNewSeq(cate), DEFAULT_COMPANY);
		}
		vo.setId(po.getId());
		return vo;
	}

	@Transactional
	public DownloadCustomerUI update(DownloadCustomerUI vo) {
		validateBeforeSave(vo);
		DownloadCustomer po = repository.findOne(vo.getId());
		if (po != null) {
			po.setTitle(vo.getTitle());
			List<String> newCates = vo.getClasses();
			List<ClassFiles> existedClassFiles = classFilesRepository.findByClassFilesIdDocidAndCompany(po.getId(), DEFAULT_COMPANY);
			List<String> existedCates = existedClassFiles.stream().map(classFiles -> classFiles.getClassFilesId().getClas()).collect(Collectors.toList());
			for (String newCate : newCates) {
				if (!existedCates.contains(newCate)) {
					jdbcTemplate.update("INSERT INTO CLASS_FILES(CLASS, DOCID, SEQ, COMPANY) VALUES (?, ?, ?, ?)",
						newCate, po.getId(), getNewSeq(newCate), DEFAULT_COMPANY);
				}
			}

			existedClassFiles = existedClassFiles.stream().filter(
				classFiles -> !newCates.contains(classFiles.getClassFilesId().getClas())).collect(Collectors.toList());
			classFilesRepository.deleteInBatch(existedClassFiles);
			po.setUpdatetime(new Date());
			repository.saveAndFlush(po);
		}
		return vo;
	}

	private void validateBeforeSave(DownloadCustomerUI vo) {
		List<DownloadCustomer> others;
		if (vo.getId() == null) {
			others = repository.findByTitle(vo.getTitle());
		} else {
			others = repository.findByTitleAndIdNot(vo.getTitle(), vo.getId() == null ? "" : vo.getId());
		}
		if (!others.isEmpty()) {
			throw new ApplicationException("Title has been existed, please use other title name!");
		}
	}

	public List<String> queryFileClasses(String docid) {
		return queryForOneField("SELECT CLASS FROM CLASS_FILES WHERE DOCID='" + docid + "' AND CLASS IS NOT NULL AND COMPANY='"
			+ DEFAULT_COMPANY + "'", "class");
	}

	private long getNewSeq(String newCate) {
		List<String> maxSeqs = queryForOneField("SELECT NVL(MAX(SEQ)+1, 0) seq FROM CLASS_FILES where CLASS='" + newCate + "'", "seq");
		return Long.parseLong(maxSeqs.get(0));
	}

	private List<String> queryForOneField(String sql, String field) {
		DataList maps = new DataList();
		List<String> result = maps.singleValList(field);
		maps.parse(jdbcTemplate.queryForList(sql));
		return result;
	}

	@Transactional
	public DownloadCustomerUI destroy(DownloadCustomerUI vo) {
		DownloadCustomer po = repository.findOne(vo.getId());
		if (po != null) {
			classFilesRepository.deleteByClassFilesIdDocid(po.getId());
			repository.delete(po);
			deleteFileFromDisk(po.getFilename());
		}
		return vo;
	}

	private void deleteFileFromDisk(String filename) {
		String path = fileService.getUploadPath();
		File file = new File(path + "/" + filename);
		if (file.exists())
			file.delete();
	}

	@Transactional
	public DataMap assignClassFiles(List<DownloadCustomerUI> voL, String cate) {
		if (StringUtils.isEmpty(cate) || voL == null)
			throw ApplicationException.PARAM_ERROR;

		DataMap result = new DataMap();
		classFilesRepository.deleteByCompanyAndClassFilesIdClas(DEFAULT_COMPANY, cate);
		for (DownloadCustomerUI vo : voL) {
			if (StringUtils.isNotEmpty(vo.getId())) {
				classFilesRepository.saveAndFlush(new ClassFiles(new ClassFilesId(cate, vo.getId()), getNewSeq(cate), DEFAULT_COMPANY));
			}
		}
		result.put("success", true);
		return result;
	}
}
