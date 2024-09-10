package com.cooking.home_recipes.controllers;

import com.cooking.home_recipes.services.RecipeService;
import com.cooking.home_recipes.utils.Const;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@Controller
public class HomeController {

    @Autowired
    private RecipeService recipeService;

    @ModelAttribute // todo idea to inject user data in response
    public void addUser(Model model, @AuthenticationPrincipal @Nullable Principal user) {
        model.addAttribute("user", user);
    }

    @GetMapping("/")
    public String getHome(Model model, HttpServletRequestWrapper req) {
        // todo create DTOs
        var latestItems = this.recipeService.getLatestThree();

        model.addAttribute("latestItems", latestItems);
        System.out.println(model);
        return "home/home.html";
    }

}

