package com.wescrum.scrumvy.controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.wescrum.scrumvy.entity.Order;
import com.wescrum.scrumvy.entity.User;
import com.wescrum.scrumvy.service.PaypalService;
import com.wescrum.scrumvy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PaypalController {

    @Autowired
    PaypalService paypalService;

    @Autowired
    UserService userService;

    public static final String SUCCESS_URL = "pay/success";
    public static final String CANCEL_URL = "pay/cancel";

    @PostMapping("/pay")
    public String payment(RedirectAttributes redirectAttributes) {
        if (userService.getLoggedinUser().getPremium() == true) {
            redirectAttributes.addFlashAttribute("createProjectError", "You are already a premium user. Thank you.");
             return "redirect:/";
        }
        Order order = new Order(50, "EUR", "paypal", "SALE", "Premium Subscription To Scrumvy Project");
        try {
            Payment payment = paypalService.createPayment(order.getPrice(), order.getCurrency(), order.getMethod(),
                    order.getIntent(), order.getDescription(), "http://localhost:8082/" + CANCEL_URL,
                    "http://localhost:8082/" + SUCCESS_URL);
            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    return "redirect:" + link.getHref();
                }
            }

        } catch (PayPalRESTException e) {

            e.printStackTrace();
        }
        return "redirect:/";
    }

    @GetMapping(value = CANCEL_URL)
    public String cancelPay() {
        return "redirect:/";
    }

    @GetMapping(value = SUCCESS_URL)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            System.out.println(payment.toJSON());
            if (payment.getState().equals("approved")) {
                User user = userService.getLoggedinUser();
                user.setPremium(true);
                userService.saveUserWithProject(user);
                return "success";
            }
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/";
    }

}
