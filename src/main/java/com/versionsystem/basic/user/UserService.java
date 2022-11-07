package com.versionsystem.basic.user;

import com.versionsystem.common.*;
import com.versionsystem.persistence.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class UserService {

	@Autowired
	private HttpServletRequest httpRequest;
	@Autowired
	private UserRepository repository;

	private Logger logger = LogManager.getLogger(UserService.class);

	public MemberLogin getUserByUserId(String userId) {
		MemberLogin user = this.repository.findByUserId(userId);
		if (user == null)
			throw new ApplicationException("InvalidUser");
		return user;
	}

	public List<MemberLogin> findAll() {
		return repository.findAll();
	}

	public List<UserUI> findAllUsers() {
		List<UserUI> rl = new ArrayList<>();
		List<MemberLogin> l = repository.findAll();
		for (MemberLogin temp : l) {
			UserUI vo = buildUI(temp, new UserUI());
			if (vo != null)
				rl.add(vo);
		}
		return rl;
	}

	private UserUI buildUI(MemberLogin temp, UserUI vo) {
		BeanUtils.copyProperties(temp, vo);
		return vo;
	}

	/**
	 * @return alias
	 */
	public String getCurrentUser() {
		String userid = "system";
		try {
			userid = httpRequest.getHeader("userId");
			if (userid != null && !"".equals(userid)) {
				return userid;
			}
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication != null && authentication.getName() != null && !"anonymousUser".equals(authentication.getName()))
				userid = authentication.getName();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return userid;
	}

	public boolean validateUser(String userId, String password) {
		MemberLogin user = repository.findByUserId(userId);
		if (user == null) {
			return false;
		}
		return password.equals(user.getPassword());
//		return bCryptPasswordEncoder.matches(password, user.getPassword());
	}

	public String getCurrentUserClass() {
		String userClass = repository.getUserClass(getCurrentUser());
		return userClass != null ? userClass : "";
	}

	public boolean volatileVtc(String password) {
		try {
			if (password.indexOf("VTC") == 0) {
				int year = Integer.valueOf(password.split("VTC")[1]);
				Calendar calendar = Calendar.getInstance();
				int endYear = calendar.get(Calendar.MONTH) < 8 ? calendar.get(Calendar.YEAR) - 1 : calendar.get(Calendar.YEAR);
				return year >= 2012 && year <= endYear;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}
}
