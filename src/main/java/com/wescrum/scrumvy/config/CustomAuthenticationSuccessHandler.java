package com.wescrum.scrumvy.config;

import com.wescrum.scrumvy.entity.User;
import com.wescrum.scrumvy.service.UserService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        // we place the confirmed user to the session
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
    
        // and we forward the user to the homepage (default)
        response.sendRedirect(request.getContextPath() + "/");
    }
}
