package com.versionsystem.basic.parameter;

import com.versionsystem.basic.user.UserService;
import com.versionsystem.common.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParameterService {

	@Autowired
	private UserService userService;
	private Logger logger = LogManager.getLogger(ParameterService.class);

	public String findKey(String key) {
		return "";
	}

	public DataMap readSystemParas() {
		DataMap para = new DataMap();

		para.put("currentUser", userService.getCurrentUser());
		para.put("currentUserClass", userService.getCurrentUserClass());

		return para;
	}
}
