package com.example.demo.controller;

import com.example.demo.enity.User;
import com.example.demo.service.Impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/")
public class LoginController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserServiceImpl userService;

    /**
     * test 8080
     *
     * @return
     */
    @RequestMapping("/")
    public String start() {
        return "hello";
    }

    /**
     * test user from user
     *
     * @return
     */
    @RequestMapping(value = "/findAll")
    public List<User> findAll() {
        List<User> users = userService.getAllUser();


        return userService.getAllUser();


    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request,
                        HttpServletResponse response,
                        @RequestParam(value = "uid", required = false) Long uid,
                        @RequestParam(value = "passWord", required = false) String passWord) {
        HttpSession session = request.getSession();

        Cookie[] cookieArray = request.getCookies();
        for (Cookie cookie : cookieArray) {
            if (cookie.getName().equals("login")) {
                String uuid = cookie.getValue();
                //查询session是否相等，相等验证通过
                if (uuid != null && session.getAttribute("uuid").equals(uuid)) {
                    System.out.println("3");
                    LOGGER.info("cookie校验通过");
                    LOGGER.info("uid: {} 登录",session.getAttribute("uid"));
                    return "true";
                }
            }
        }
        //cookie还没有、不合法或者过期了，重新写cookie和session
        if (uid == null || passWord == null){
            System.out.println("uid == null || passWord == null");
            return "false";
        }
        //查询db是否验证通过
        User user = userService.getUserByLogin(uid, passWord);

        if (user==null){
            System.out.println("user==null");
            return "false";
        }
        String uuid = UUID.randomUUID().toString();
        session.setAttribute("uid", user.getUid());
        session.setAttribute("userName", user.getUserName());
        session.setAttribute("uuid", uuid);
        Cookie newCookie = new Cookie("login", uuid);
        //cookie有效时间 单位秒 设置5分钟
        newCookie.setMaxAge(300);
        //放回response
        response.addCookie(newCookie);
        LOGGER.info("db校验通过");
        LOGGER.info("uid: {} 登录",session.getAttribute("uid"));
        return "true";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletResponse response){
        Cookie newCookie = new Cookie("login", null);
        newCookie.setMaxAge(0);
        response.addCookie(newCookie);
        return "logout";
    }

}
