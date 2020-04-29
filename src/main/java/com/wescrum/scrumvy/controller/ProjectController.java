package com.wescrum.scrumvy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/project")
public class ProjectController {

    @GetMapping("/createProject")
    public String createProject(Model model) {
        
        return "home";
    }
}
