package com.versionsystem.basic.security;

import com.versionsystem.basic.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecurityLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	public SecurityLoginSuccessHandler() {
		super("/login");
	}

	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth)
			throws IOException, ServletException {
		if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
			response.getWriter().print("{\"success\":true,\"targetUrl\": \"" + this.getTargetUrlParameter() + "\"}");
			response.getWriter().flush();
		} else {
			super.onAuthenticationSuccess(request, response, auth);
		}
	}

}
