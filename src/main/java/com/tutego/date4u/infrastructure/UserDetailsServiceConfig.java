package com.tutego.date4u.infrastructure;

import com.tutego.date4u.core.Unicorn;
import com.tutego.date4u.core.UnicornService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration( proxyBeanMethods = false )
public class UserDetailsServiceConfig {

    @Autowired
    UnicornService unicornService;

    @Bean
    UserDetailsService userDetailsService() {
        return email -> {
            Unicorn user = unicornService.getUnicornByEmail(email);

            if (user != null) {
                return User.withUsername(email)
                        .password(user.getPassword())
                        .roles()
                        .build();
            } else {
                    throw new UsernameNotFoundException(email);
            }
        };
    }
}