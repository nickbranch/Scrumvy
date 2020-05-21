package com.wescrum.scrumvy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RouterController {

    @GetMapping("/goPremium")
    public String goPremium() {
        return "buynow";
    }

}
