package com.example.carservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf()
        .disable()
        .authorizeRequests()
        .antMatchers("/")
        .hasAnyRole("ADMIN", "MANAGER")
        .antMatchers("/clientPage")
        .hasAnyRole("CLIENT")
        .antMatchers("/register")
        .permitAll()
        .and()
        .exceptionHandling()
        .accessDeniedPage("/unauthorized")
        .and()
        .formLogin()
        .loginPage("/login")
        .defaultSuccessUrl("/home", true)
        .loginProcessingUrl("/j_spring_security_check")
        .failureUrl("/login?error")
        .usernameParameter("j_login")
        .passwordParameter("j_password")
        .permitAll()
        .and()
        .logout()
        .permitAll()
        .logoutUrl("/logout")
        .logoutSuccessUrl("/login?logout");

    return http.build();
  }
}
