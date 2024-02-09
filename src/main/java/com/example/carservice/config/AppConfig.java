package com.example.carservice.config;

import com.example.carservice.entity.CustomUser;
import com.example.carservice.entity.UserRole;
import com.example.carservice.services.GpsTrackerManagerService;
import com.example.carservice.services.UserService;
import com.example.carservice.services.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppConfig extends GlobalMethodSecurityConfiguration {

  @Value("${spring.mail.username}")
  private String fromAddress;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public CommandLineRunner demo(
      final UserService userService,
      final Utils utils,
      final GpsTrackerManagerService gpsService) {
    return new CommandLineRunner() {
      @Override
      public void run(String... strings) throws Exception {
        userService.save(new CustomUser("admin", "password", UserRole.ROLE_ADMIN, "madmax@gmail.com"));
        userService.save(new CustomUser("user", "user", UserRole.ROLE_MANAGER, "user@gmail.com"));
        utils.loadAllEntityLists();
        //gpsService.uploadGpsTrackerData();
      }
    };
  }
}
