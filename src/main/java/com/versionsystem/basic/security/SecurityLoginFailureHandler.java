package com.versionsystem.basic.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecurityLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	public SecurityLoginFailureHandler() {
		setDefaultFailureUrl("/login/failure");
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
		throws IOException, ServletException {
		String exceptoinType = exception.getMessage();//InvalidUser,InvalidSecurityCode

		if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
			String locale = (String) request.getSession(false).getAttribute("locale");
			String errorMsg = " Invalid username or password,please try again! ";
			if ("zh-HK".equals(locale)) {
				if ("ExpiredSecurityCode".equals(exceptoinType)) {
					errorMsg = "此安全碼已經過期，請重新生成!";
				} else {
					errorMsg = "用戶名或密碼錯誤，請重新輸入!";
				}
			} else if ("zh-CN".equals(locale)) {
				if ("ExpiredSecurityCode".equals(exceptoinType)) {
					errorMsg = "此安全码已经过期，请重新生成!";
				} else {
					errorMsg = "用户名或密码错误，请重新输入!";
				}
			} else {
				if ("ExpiredSecurityCode".equals(exceptoinType)) {
					errorMsg = "This security code is expired,please request a new code again!";
				}
			}
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().print("{\"success\":false, \"message\": \"" + errorMsg + "\"}");
			response.getWriter().flush();
		} else {
			super.onAuthenticationFailure(request, response, exception);
		}
	}

}
