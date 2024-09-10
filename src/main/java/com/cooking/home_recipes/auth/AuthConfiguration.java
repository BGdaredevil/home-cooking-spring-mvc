package com.cooking.home_recipes.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        return new InMemoryUserDetailsManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder, InMemoryUserDetailsManager manager) {
        UserDetails adminUser = User.withUsername("root").password(passwordEncoder().encode("123456")).build();

        manager.createUser(adminUser);

        // todo load users at start
        // todo save users at create
        // todo save users at update????

        return manager;
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(HttpMethod.GET, "/**").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/auth/login").not().authenticated();
                    auth.requestMatchers(HttpMethod.GET, "/auth/logout").authenticated();
                    auth.requestMatchers(HttpMethod.PUT, "/**").authenticated();
                    auth.requestMatchers(HttpMethod.POST, "/**").authenticated();
                    auth.requestMatchers(HttpMethod.PATCH, "/**").authenticated();
                    auth.requestMatchers(HttpMethod.DELETE, "/**").authenticated();
                }).build();
    }
}
