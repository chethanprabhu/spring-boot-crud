package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class securityUserConfig {
    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        UserDetails chethan = User.builder().username("chethan").password("{noop}chethan").roles("ADMIN", "DEVELOPER", "DESIGNER").build();
        UserDetails jyothi = User.builder().username("jyothi").password("{noop}jyothi").roles("DEVELOPER", "DESIGNER").build();
        UserDetails anaya = User.builder().username("anaya").password("{noop}anaya").roles("DESIGNER").build();

        return new InMemoryUserDetailsManager(chethan, jyothi, anaya);
    }
}
