package com.careerstreet.user_service.repository;

import com.careerstreet.user_service.entity.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {
    @Bean
    public CommandLineRunner loadData(RoleRepository roleRepository) {
        return args -> {
            // Kiểm tra và thêm quyền "ADMIN" nếu chưa có
            if (roleRepository.findByRoleName("admin") == null) {
                roleRepository.save(new Role("admin"));
                System.out.println("Role 'admin' đã được thêm vào cơ sở dữ liệu.");
            }

            // Kiểm tra và thêm quyền "candidate" nếu chưa có
            if (roleRepository.findByRoleName("candidate") == null) {
                roleRepository.save(new Role("candidate"));
                System.out.println("Role 'candidate' đã được thêm vào cơ sở dữ liệu.");
            }

            // Kiểm tra và thêm quyền "employer" nếu chưa có
            if (roleRepository.findByRoleName("employer") == null) {
                roleRepository.save(new Role("employer"));
                System.out.println("Role 'employer' đã được thêm vào cơ sở dữ liệu.");
            }
        };
    }
}
