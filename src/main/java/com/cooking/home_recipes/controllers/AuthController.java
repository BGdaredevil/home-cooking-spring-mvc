package com.cooking.home_recipes.controllers;

import com.cooking.home_recipes.dtos.LoginDto;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller

@RequestMapping("/auth")
public class AuthController {
    @GetMapping("/login")
    public String getLogin(Model model) {

        model.addAttribute("item", new LoginDto());
        System.out.println(model);
        return "auth/login";
    }

    @GetMapping("/register")
    public String getRegister() {
        return "auth/register";
    }

    @PostMapping(path = "/login", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE} )
//    @PostMapping(path = "/login")
    public String login( LoginDto item) {
        System.out.println(item);

        return "auth/login";
    }
    @PostMapping("/register")
    public String register() {
        return "auth/register";
    }

}
