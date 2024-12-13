package com.cooking.home_recipes.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class AuthConfiguration {

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(PasswordEncoder passwordEncoder) {
        InMemoryUserDetailsManager manager  = new InMemoryUserDetailsManager();
        UserDetails adminUser = User.withUsername("pesho@mama.com").password(passwordEncoder().encode("123456")).build();
        manager.createUser(adminUser);

        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        System.out.println("called authenticationManager");
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class).build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(HttpMethod.GET, "/styles/**").permitAll(); // all statics put in styles

                    auth.requestMatchers(HttpMethod.GET, "/").permitAll(); // all can visit home

                    auth.requestMatchers(HttpMethod.GET, "/auth/login").anonymous();
                    auth.requestMatchers(HttpMethod.POST, "/auth/login").anonymous();
                    auth.requestMatchers(HttpMethod.GET, "/auth/register").anonymous();
                    auth.requestMatchers(HttpMethod.POST, "/auth/register").anonymous();
                    auth.requestMatchers(HttpMethod.GET, "/auth/logout").authenticated();

                    auth.requestMatchers(HttpMethod.PUT, "/**").authenticated();
                    auth.requestMatchers(HttpMethod.POST, "/**").authenticated();
                    auth.requestMatchers(HttpMethod.PATCH, "/**").authenticated();
                    auth.requestMatchers(HttpMethod.DELETE, "/**").authenticated();
                })
                .build();
    }

//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder, InMemoryUserDetailsManager manager) {
//        UserDetails adminUser = User.withUsername("root").password(passwordEncoder().encode("123456")).build();
//
//        manager.createUser(adminUser);
//
//        // todo load users at start
//        // todo save users at create
//        // todo save users at update????
//
//        return manager;
//    }

}
