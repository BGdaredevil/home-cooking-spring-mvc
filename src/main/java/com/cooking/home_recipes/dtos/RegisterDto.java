package com.cooking.home_recipes.dtos;

public class RegisterDto {
    private String name;
    private String email;
    private String password;
    private String rePassword;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRePassword() {
        return rePassword;
    }
}
