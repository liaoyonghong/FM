package com.versionsystem.app.downloadCustomer.controller;

import com.versionsystem.app.downloadCustomer.model.DownloadCustomerUI;
import com.versionsystem.app.downloadCustomer.service.DownloadCustomerService;
import com.versionsystem.common.DataMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/downloadCustomer")
public class DownloadCustomerController {

	@Autowired
	private DownloadCustomerService service;
	private final Logger logger = LogManager.getLogger(DownloadCustomerController.class);

	@RequestMapping(value = "/vocAll")
	public List<DownloadCustomerUI> list() {
		try {
			return service.listBy("VOC");
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
	}

	@RequestMapping(value = "/findByClass")
	public List<DownloadCustomerUI> findByClass() {
		try {
			return service.findByClass("VOC");
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
	}

	@RequestMapping("/getByDocid/{docid}")
	public List<String> getByDocid(@PathVariable String docid) {
		try {
			return service.queryFileClasses(docid);
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
	}

	@RequestMapping(value = "/getPatientCate")
	public List<String> getPatientCategory() {
		try {
			return service.getPatientCategory("VOC");
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public DownloadCustomerUI create(@RequestParam("file") CommonsMultipartFile file, @RequestParam("title") String title,
									 @RequestParam("filename") String filename,
									 @RequestParam("classes") List<String> classes) throws IOException {
		try {
			return service.create(new DownloadCustomerUI(title, filename, "VOC", classes), file);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw e;
		}
	}

	@RequestMapping(value = "/update")
	public DownloadCustomerUI update(@RequestBody DownloadCustomerUI vo) {
		try {
			return service.update(vo);
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
	}

	@RequestMapping(value = "/destroy")
	public DownloadCustomerUI destroy(@RequestBody DownloadCustomerUI vo) {
		try {
			return service.destroy(vo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw e;
		}
	}

	@RequestMapping(value = "/destroySome")
	public boolean destroySome(@RequestBody List<DownloadCustomerUI> voL) {
		try {
			for (DownloadCustomerUI vo : voL) {
				service.destroy(vo);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw e;
		}
	}

	@RequestMapping(value = "/assignClassFiles/{cate}")
	public DataMap assignClassFiles(@RequestBody List<DownloadCustomerUI> voL, @PathVariable("cate") String cate) {
		try {
			return service.assignClassFiles(voL, cate);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw e;
		}
	}
}
