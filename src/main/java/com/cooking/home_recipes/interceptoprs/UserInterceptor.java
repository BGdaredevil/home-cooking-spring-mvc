package com.cooking.home_recipes.interceptoprs;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

// todo not used
public class UserInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("pre");
        System.out.println(request.getPathTranslated());
        System.out.println(request.getContextPath());
        System.out.println(request.getServletPath());



        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        System.out.println("post");

//        modelAndView.addObject("stamat", "asan");

        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
}
