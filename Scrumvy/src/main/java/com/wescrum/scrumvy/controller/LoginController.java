package com.wescrum.scrumvy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    	@GetMapping("/showLogin")
	public String showLogin() {		
		return "login";		
	}
	
	@GetMapping("/access-denied")
	public String showAccessDenied() {		
		return "access-denied";		
	}
}
