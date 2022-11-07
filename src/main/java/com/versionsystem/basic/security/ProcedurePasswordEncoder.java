package com.versionsystem.basic.security;

import com.versionsystem.basic.user.UserService;
import org.apache.commons.io.IOUtils;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.util.Calendar;

@Service
@Transactional
public class ProcedurePasswordEncoder implements PasswordEncoder {

	@PersistenceContext
	private EntityManager em;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private UserService userService;

	@Override
	public String encode(CharSequence rawPassword) {
		final StringBuffer resultBuffer = new StringBuffer();
		em.unwrap(Session.class).doWork(connection -> {
			CallableStatement cs = connection.prepareCall("{?=call CMS_PROC.XXencryptPassword(?)}");
			cs.registerOutParameter(1, Types.VARCHAR);
			cs.setString(2, rawPassword.toString());
			cs.execute();
			resultBuffer.append(cs.getString(1));
			cs.close();
		});
		return resultBuffer.toString();
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		try {
			String username = request.getParameter("username");
			if (username != null && "VTC".equals(request.getParameter("username").toUpperCase())) {
				return userService.volatileVtc(rawPassword.toString().toUpperCase());
			}
		} catch (Exception e) {
			return false;
		}
		return encode(rawPassword.toString()).equals(encodedPassword);
	}

}
