package com.versionsystem.basic.user;

import com.versionsystem.common.ApplicationException;
import com.versionsystem.common.DataMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService service;

	private Logger logger = LogManager.getLogger(UserController.class);

	@RequestMapping(value = "/read")
	public List<UserUI> list() throws Exception {
		try {
			return service.findAllUsers();
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping("check")
	public DataMap check() {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			DataMap result = new DataMap();
			if (authentication == null) {
				result.put("authentication", "noop");
			} else {
				result.put("authentication.name", authentication.getName());
				result.put("authentication.has", authentication.isAuthenticated());
				result.put("authentication.detail", authentication.getDetails());
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("InvalidUser");
		}
	}

	@RequestMapping("exception")
	public DataMap exception() {
		throw new ApplicationException("Test exception");
	}

	@RequestMapping(value = "/loginValid")
	public DataMap login(@RequestBody DataMap request) throws Exception {
		try {
			DataMap result = new DataMap();
			if (!request.has("username") || !request.has("password")) {
				throw new RuntimeException("InvalidUser");
			}
			String username = request.get("username").toString();
			String password = request.get("password").toString();
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			authentication.setAuthenticated(service.validateUser(username, password));
			result.put("valid_time", System.currentTimeMillis());
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("InvalidUser");
		}
	}

}
