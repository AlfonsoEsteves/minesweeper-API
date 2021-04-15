package com.example.minesweeper.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomBasicAuthenticationEntryPoint entryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().anyRequest().authenticated()
                .and().httpBasic().authenticationEntryPoint(entryPoint);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // The endpoint for signing up does not need authentication
        web.ignoring()
                .antMatchers(HttpMethod.POST, "/user")
                .antMatchers(HttpMethod.GET, "/")
                .antMatchers(HttpMethod.GET, "/index")
                .antMatchers(HttpMethod.GET, "/index.html");
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

