package com.versionsystem.basic.exception;

import com.versionsystem.common.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ApplicationExceptionController {

	@ExceptionHandler(ApplicationException.class)
	public ModelAndView handleError(HttpServletRequest req, Exception ex) {
		ModelAndView mav = new ModelAndView();
		mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		mav.addObject("message", ex.getMessage());
		mav.setView(new MappingJackson2JsonView());
		return mav;
	}

}
