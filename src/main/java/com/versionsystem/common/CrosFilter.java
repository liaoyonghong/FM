package com.versionsystem.common;

import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CrosFilter extends OncePerRequestFilter {
	static final String ORIGIN = "Origin";


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
//	response.setHeader("Access-Control-Allow-Origin", "https://cms.aiaunion.com.hk/EmrApplication");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.addHeader("Access-Control-Max-Age", "10");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		String reqHead = request.getHeader("Access-Control-Request-Headers");
		if (!StringUtils.isEmpty(reqHead)) {
			response.addHeader("Access-Control-Allow-Headers", reqHead);
		}
		response.setHeader("Access-Control-Expose-Headers", "x-requested-with");
		filterChain.doFilter(request, response);
	}
}
