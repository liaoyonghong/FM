package com.versionsystem.app.downloadCustomer.repo;

import com.versionsystem.persistence.model.ClassFiles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassFilesRepository extends JpaRepository<ClassFiles, String> {

	List<ClassFiles> findByClassFilesIdDocidAndCompany(String docid, String company);
	void deleteByClassFilesIdDocid(String docid);
	void deleteByCompanyAndClassFilesIdClas(String company, String cate);

}
