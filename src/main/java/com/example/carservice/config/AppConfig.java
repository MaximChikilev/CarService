package com.example.carservice.config;

import com.example.carservice.entity.CustomUser;
import com.example.carservice.entity.MaintenanceSchedule;
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
    public CommandLineRunner demo(final UserService userService,
                                  final PasswordEncoder encoder,
                                  final Utils utils,
                                  final GpsTrackerManagerService gpsService) {
        return new CommandLineRunner() {
            @Override
            public void run(String... strings) throws Exception {
                userService.save(new CustomUser("admin",encoder.encode("password"), UserRole.ROLE_ADMIN,"madmax@gmail.com"));
                utils.loadAllEntityLists();
                gpsService.uploadGpsTrackerData();
            }
        };
    }
    @Bean
    @Scope("prototype")
    public SimpleMailMessage reminderMessageTemplate() {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setSubject("Car Service. Reminder to sign up for the service");
        message.setText("Dear %s, you have an appointment for service at: %t (%s)");
        message.setFrom(fromAddress);

        return message;
    }

}
