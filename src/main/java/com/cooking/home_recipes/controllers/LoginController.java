package com.cooking.home_recipes.controllers;

import com.cooking.home_recipes.auth.JWTUtil;
import com.cooking.home_recipes.dtos.LoginDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private final AuthenticationManager manager;

    public LoginController(AuthenticationManager manager) {
        this.manager = manager;
    }

    @PostMapping(path = "/auth/login", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<String> login(LoginDto item) {
//    public ResponseEntity<String> login(@RequestBody MultiValueMap item) {
        System.out.println("=========================================================");
        System.out.println(item);
        System.out.println(item.getEmail());
        System.out.println(item.getPassword());

        // this is not JWT
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                item.getEmail(),
                item.getPassword()
        );

        System.out.println(token);

        //        // fails on invalid creds
        Authentication authentication = this.manager.authenticate(token);
        System.out.println(authentication);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = JWTUtil.generateToken((User) authentication.getPrincipal());
        System.out.println(jwtToken);

        return ResponseEntity.ok(jwtToken);
//        return ResponseEntity.ok("sad");

    }
}
