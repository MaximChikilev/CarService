package com.example.carservice;

import com.example.carservice.entity.User;
import com.example.carservice.entity.UserRole;
import com.example.carservice.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppConfig extends GlobalMethodSecurityConfiguration{
    @Bean
    public CommandLineRunner demo(final UserService userService,
                                  final PasswordEncoder encoder) {
        return new CommandLineRunner() {
            @Override
            public void run(String... strings) throws Exception {
                User user = new User("admin", encoder.encode("admin"), UserRole.ADMIN, "");
                userService.save(user);
            }
        };
    }
}
