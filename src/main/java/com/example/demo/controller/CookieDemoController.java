package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalTime;

@RestController
@RequestMapping("/cookie")
public class CookieDemoController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CookieDemoController.class);

    @RequestMapping("/set")
    public String setCookie(@RequestParam String key,
                            @RequestParam String value,
                            HttpServletResponse response) {
        Cookie cookie = new Cookie(key, LocalTime.now().toString());
        response.addCookie(cookie);
        LOGGER.info(LocalTime.now().toString());
        LOGGER.info("setCookie key: {} value: {}", key, value);
        return "ok";
    }

}
