package com.Nhom19.shopQuanAo.configuration;


import com.Nhom19.shopQuanAo.entity.Admin;
import com.Nhom19.shopQuanAo.enums.Role;
import com.Nhom19.shopQuanAo.repository.AdminRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.HashSet;

@Configuration
public class ApplicationInitConfig {
    @Bean
    public ApplicationRunner applicationRunner(AdminRepository adminRepository) {
        return agrs -> {
            if (adminRepository.findByUsername("admin").isEmpty()) {
                var roles = new HashSet<String>();
                roles.add(Role.ADMIN.name());
                Admin admin = new Admin();
                admin.setUsername("admin");
//              PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
                admin.setPassword("admin");

                adminRepository.save(admin);
            }
        };
    }
}
