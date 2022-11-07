package com.versionsystem.vos.news.repo;

import com.versionsystem.persistence.model.VosNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NewsRepository extends JpaRepository<VosNews,Long> {

	@Query(value = "SELECT * FROM VOS_NEWS" +
		" where vtc_flag='Y' and ( expiry_date is null or to_char(expiry_date, 'yyyyMMdd')>=to_char(sysdate, 'yyyyMMdd')) order by news_Date desc",nativeQuery = true)
	List<VosNews> findAllByExpiryDate();
}
