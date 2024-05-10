package com.romm.todopp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.romm.todopp.service.MyUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(authorize -> authorize
        .requestMatchers("/").permitAll()
        .requestMatchers("/auth/register").permitAll()
        .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
        .requestMatchers("/h2-database/**").permitAll()
        
        .anyRequest().permitAll()
        )
        .formLogin(withDefaults())
        .httpBasic(withDefaults());

        httpSecurity.headers(
            headers -> headers.frameOptions(
                frameOptions -> frameOptions.disable()
            )
        );
        httpSecurity.csrf(csrf -> csrf.disable());
        
        return httpSecurity.build();
    }

    @Bean
    UserDetailsService userDetailsService() {
        return new MyUserDetailsService();
    }

    @Bean // isso especifica o bcrypt como método de hashing para as senhas, mas ele já é o default do spring.
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
