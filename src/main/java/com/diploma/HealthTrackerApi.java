package com.diploma;

import com.diploma.domain.User;
import com.diploma.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HealthTrackerApi {
    public static void main(String[] args) {
        SpringApplication.run(HealthTrackerApi.class, args);
    }

    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> userService.create(User.builder()
                        .name("John")
                        .surname("Smith")
                        .email("john@gmail.com")
                        .password("12345")
                        .role("ROLE_USER")
                .build());
    }
}
