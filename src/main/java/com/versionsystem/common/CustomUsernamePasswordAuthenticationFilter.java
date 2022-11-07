package com.versionsystem.common;

import com.versionsystem.basic.security.SecurityLoginFailureHandler;
import com.versionsystem.basic.security.SecurityLoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	public CustomUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
		setFilterProcessesUrl("/filterUrl");
		setAuthenticationManager(authenticationManager);
		setAuthenticationSuccessHandler(new SecurityLoginSuccessHandler());
		setAuthenticationFailureHandler(new SecurityLoginFailureHandler());
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
		throws AuthenticationException {
		String locale = request.getParameter("j_locale");
		String securityCode = request.getParameter("j_securityCode");
		request.getSession(true).setAttribute("locale", locale);
		request.getSession(true).setAttribute("justLogin", true);
		// request.getSession().setAttribute("validateSecurityCode", false);
		request.getSession(true).setAttribute("securityCode", securityCode);
		return super.attemptAuthentication(request, response);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
											  AuthenticationException failed) throws IOException, ServletException {
		super.unsuccessfulAuthentication(request, response, failed);

		System.out.println("==failed login==");
	}

}
