package com.versionsystem.basic.user;

import com.versionsystem.persistence.model.MemberLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<MemberLogin, Long> {

	MemberLogin findByUserId(String user);

	@Query(value = "SELECT PATIENT_CATEGORY FROM PATIENT WHERE NO = (SELECT PATIENT_NO FROM MEMBER_LOGIN WHERE USER_ID = ?) ", nativeQuery = true)
	String getUserClass(String user);

}
