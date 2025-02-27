package com.cooking.home_recipes.auth;

import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.RequestMatcherRedirectFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class AuthConfiguration {
    @Autowired
    private final HandleExceptionsEntryPoint handleExceptionsEntryPoint;
    public AuthConfiguration(HandleExceptionsEntryPoint handleExceptionsEntryPoint) {
        this.handleExceptionsEntryPoint = handleExceptionsEntryPoint;
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(PasswordEncoder passwordEncoder) {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
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
//                .exceptionHandling(
//                        httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(handleExceptionsEntryPoint)
//                ) // todo not needed currently
//                .addFilterBefore(new UsernamePasswordAuthFilter(),( myclass extends UsernamePasswordAuthFilter).class)
//                .addFilterBefore(new UsernamePasswordAuthFilter(), UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(new CookieAuthenticationFilter(), UsernamePasswordAuthFilter.class)
//                .addFilter(new UsernamePasswordAuthFilter())
                .addFilterBefore(new UsernamePasswordAuthFilter(), UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(
                        httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> {
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
                .exceptionHandling(
                        e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )
//                .exceptionHandling(
//                        httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(handleExceptionsEntryPoint)
//                )
//                .formLogin(cust -> cust
//                        .loginPage("/auth/login")
//                        .defaultSuccessUrl("/")
//                        .usernameParameter("email")
//                        .loginProcessingUrl("/auth/login")
//                        .failureUrl("/auth/login?error")
//                        .permitAll()
//                )
//                .logout(
//                        httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer.deleteCookies("pesho").permitAll()
//                )
                .build();
    }

    // handles static files and bypasses security
    @Bean
    WebSecurityCustomizer configureWebSecurity() {
        return (web) -> web.ignoring().requestMatchers("/**");
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
