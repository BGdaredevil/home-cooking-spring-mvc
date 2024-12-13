package com.cooking.home_recipes.dtos;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;

public class LoginDto {
    private String email;
    private String password;

    LoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
