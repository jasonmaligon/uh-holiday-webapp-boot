package edu.hawaii.its.holiday.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Locale;

@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @GetMapping(value = { "", "/" })
    public String home(Locale locale) {
        logger.info("User at home. The client locale is {}.", locale);
        return "home";
    }

    @GetMapping(value = "/contact")
    public String contact() {
        logger.info("User at contact.");
        return "contact";
    }

    @GetMapping(value = "/faq")
    public String faq() {
        logger.info("User at faq.");
        return "faq";
    }

    @GetMapping(value = { "/grid" })
    public String grid() {
        logger.debug("User at holidaygrid.");
        return "grid";
    }

    @GetMapping(value = "/404")
    public String invalid() {
        return "redirect:/";
    }

}
