package com.versionsystem.basic.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class SecurityAccessController {

	@Autowired
	private HttpSession session;

	@RequestMapping("/login")
	public String login(Model model, @RequestParam(required = false) String message) {
		model.addAttribute("message", message);
		return "security/login";
	}

	@RequestMapping(value = "/denied")
	public String denied() {
		return "security/denied";
	}

	@RequestMapping(value = "/home")
	public String home() {
		System.out.println("in home");
		return "home";
	}

	@RequestMapping(value = "/login/failure")
	public String loginFailure() {
		String message = "Login_Failure!";
		return "redirect:/login?message=" + message;
	}

	@RequestMapping(value = "/logout/success")
	public String logoutSuccess() {
		session.invalidate();
		String message = "Logout_Success!";
		return "redirect:/login?message=" + message;
	}

}
