package com.cooking.home_recipes.controllers;

import com.cooking.home_recipes.auth.JWTUtil;
import com.cooking.home_recipes.dtos.LoginDto;
import com.cooking.home_recipes.dtos.RegisterDto;
import com.cooking.home_recipes.utils.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final String LOGIN_MODEL_ATTR = "loginForm";
    private final String REGISTER_MODEL_ATTR = "registerForm";
    private final AuthenticationManager manager;

    public AuthController(AuthenticationManager manager) {
        this.manager = manager;
    }

    @GetMapping("/login")
    public String getLogin(@ModelAttribute LoginDto item, Model model) {
        System.out.println(model);
        System.out.println(item.getEmail());

        model.addAttribute(LOGIN_MODEL_ATTR, new LoginDto());

        System.out.println("pass get login");
        return "auth/login";
    }

    @PostMapping(path = "/login", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String login(@ModelAttribute LoginDto item, Model model, HttpServletResponse res) {
        System.out.println("@PostMapping(path = \"/login\"");
        // this is not JWT
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                item.getEmail(),
                item.getPassword()
        );

        System.out.println(token);
        try {
            // fails on invalid creds
            Authentication authentication = this.manager.authenticate(token);
            System.out.println(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwtToken = JWTUtil.generateToken((User) authentication.getPrincipal());
            System.out.println(jwtToken);

            ResponseCookie authCookie = ResponseCookie.from(Const.AUTH_COOKIE_NAME).value(jwtToken).httpOnly(true).path("/").maxAge(Const.AUTH_COOKIE_MAX_AGE).build();
            res.setHeader(HttpHeaders.SET_COOKIE, authCookie.toString());
            return "redirect:/";
        } catch (Exception e) {
            System.out.println(e.getMessage());

            var currentErrors = PureFunctions.getErrorsObj(model);

            currentErrors.addMessage(new ErrorMessage(e.getMessage()));

            model.addAttribute(LOGIN_MODEL_ATTR, new LoginDto(item.getEmail()));

            System.out.println(model.getAttribute(Const.ERRORS_MODEL_NAME));

            return "auth/login";
        }

    }

    @GetMapping("/register")
    public String getRegister(Model model) {
        model.addAttribute(REGISTER_MODEL_ATTR, new RegisterDto());

        return "auth/register";
    }

    @PostMapping(path = "/register", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String register(@ModelAttribute RegisterDto item, Model model) {
        String name = item.getName();
        String email = item.getEmail();
        String password = item.getPassword();
        String rePassword = item.getRePassword();
        ErrorMessagesCarrier errors = PureFunctions.getErrorsObj(model);

        if (StringValidators.isEmptyString(name)) {
            errors.addMessage("name is required");
        }

        if (StringValidators.isEmptyString(email)) {
            errors.addMessage("email is required");
        }

        if (StringValidators.isEmptyString(password)) {
            errors.addMessage("password is required");
        }

        if (StringValidators.isEmptyString(rePassword)) {
            errors.addMessage("repeat password is required");
        }

        if (!StringValidators.isEmptyString(password) && !StringValidators.isEmptyString(rePassword) && !StringValidators.isSameString(password, rePassword)) {
            errors.addMessage("Passwords must match");
        }

        System.out.println(name);
        System.out.println(email);
        System.out.println(password);
        System.out.println(rePassword);

        if (!errors.messages.isEmpty()) {
            model.addAttribute(REGISTER_MODEL_ATTR, item);

            return "auth/register";
        }

        // do register then do login then redirect to home
        // userService.register

        System.out.println("make register");

        return "auth/register";
    }

}
