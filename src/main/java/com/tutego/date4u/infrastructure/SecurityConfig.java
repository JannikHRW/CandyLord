package com.tutego.date4u.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration(proxyBeanMethods = false)
public class SecurityConfig {

    @Autowired
    private UserDetailsServiceConfig userDetailsService;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http ) throws Exception {
        http
                .authorizeHttpRequests( authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers( "/index" ).permitAll()
                        .requestMatchers("/register").permitAll().requestMatchers("/register/save").permitAll()
                        .anyRequest().authenticated())
                .formLogin(
        form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .permitAll()
                ).logout(
                logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .permitAll()
        );
        return http.build();
    }
}