package com.example.demo.interceptor;

import com.example.demo.annotation.LoginRequire;
import com.example.demo.controller.CookieDemoController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

public class AuthInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthInterceptor.class);


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        //便于下列操作
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        // 获取类注解
        LoginRequire permissionClass = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), LoginRequire.class);
        // 获取方法注解
        LoginRequire permissionMethod = AnnotationUtils.findAnnotation(handlerMethod.getMethod(),LoginRequire.class);

        if (permissionClass == null && permissionMethod == null) {
            // 不需要校验权限，直接放行
            return true;
        }
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token") && cookie.getValue() != null){
                //check
                if (cookie.getValue().equals(request.getSession().getAttribute("token"))){
                    LOGGER.info("token验证通过");
                    return true;
                }
            }
        }
        String returnUrl = request.getRequestURL().toString();
        returnUrl = "https://www.baidu.com/";
        LOGGER.info("拦截对应uri {}",returnUrl);
//        String encodeUrl = URLEncoder.encode(returnUrl,"UTF-8");
//        String resUrl = request.getLocalAddr() + ":" + request.getLocalPort() +"/returnTest?returnUrl=" + encodeUrl;
//        LOGGER.info(resUrl);
//        response.sendRedirect(resUrl);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
