package com.wescrum.scrumvy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class LoginController {

    @GetMapping("/showLogin")
    public String showLogin(@ModelAttribute("registrationSuccess") String registrationSuccess, Model model) {
        if (registrationSuccess == null) {
        } else {
            model.addAttribute("registrationSuccess", registrationSuccess);
        }
        return "login";
    }

    @GetMapping("/access-denied")
    public String showAccessDenied() {
        return "access-denied";
    }
}
