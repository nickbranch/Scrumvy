package com.wescrum.scrumvy.controller;

import com.wescrum.scrumvy.dto.UserDto;
import com.wescrum.scrumvy.entity.User;
import com.wescrum.scrumvy.service.UserService;
import java.util.logging.Logger;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private UserService userService;
    private Logger logger = Logger.getLogger(getClass().getName());

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/showRegister")
    public String showRegister(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "registrationForm";
    }

    @PostMapping("/processRegistrationForm")
    public String processRegistrationForm(
            @Valid @ModelAttribute("userDto") UserDto userDto,
            BindingResult theBindingResult,
            Model model) {

        String userName = userDto.getUserName();
        logger.info("Processing registration form for: " + userName);

        // form validation
        if (theBindingResult.hasErrors()) {
            return "registrationForm";
        }

        // check the database if user already exists
        User existing = userService.findByUserName(userName);
        if (existing != null) {
            model.addAttribute("userDto", new UserDto());
            model.addAttribute("registrationError", "User name already exists.");

            logger.warning("User name already exists.");
            return "registrationForm";
        }

        // create user account        						
        userService.save(userDto);

        logger.info("Successfully created user: " + userName);

        return "registrationCompleted";
    }
}
