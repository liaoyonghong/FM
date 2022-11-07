package com.versionsystem.app.downloadCustomer.repo;

import com.versionsystem.persistence.model.DownloadCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface DownloadCustomerRepository extends JpaRepository<DownloadCustomer, String> {

	List<DownloadCustomer> findByTitle(String title);
	List<DownloadCustomer> findByTitleAndIdNot(String title, String id);

	@Query(value = "SELECT * FROM Download_Customer where company=?1 AND uploadtime BETWEEN ?2 and ?3", nativeQuery = true)
	List<DownloadCustomer> getByCompany(String company, Date from, Date to);

	@Query(value = "SELECT * FROM DOWNLOAD_CUSTOMER WHERE COMPANY = ?1 AND UPLOADTIME BETWEEN ?3 AND ?4 " +
		"AND EXISTS (SELECT * FROM CLASS_FILES WHERE CLASS = ?2 AND DOCID = DOWNLOAD_CUSTOMER.ID) ", nativeQuery = true)
	List<DownloadCustomer> getByCompanyAndClass(String company, String userClass, Date from, Date to);

}
