package com.thesearch.mylaptopshop.data;

import java.util.Set;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.thesearch.mylaptopshop.model.Role;
import com.thesearch.mylaptopshop.model.User;
import com.thesearch.mylaptopshop.repository.RoleRepository;
import com.thesearch.mylaptopshop.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Transactional
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Set<String> defaultRoles = Set.of("ROLE_ADMIN", "ROLE_USER");
        // createDefaultUserIfNotExists();
        createDefaultRoleIfNotExists(defaultRoles);
        // createDefaultAdminIfNotExists();
    }

    private void createDefaultUserIfNotExists() {
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        for (int i = 1; i <= 5; i++) {
            String defaultEmail = "LQA" + i + "@gmail.com";
            if (userRepository.existsByEmail(defaultEmail)) {
                continue;
            }
            User user = new User();
            user.setFirstName("Anh" + i);
            user.setLastName("Luong Quang");
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Set.of(userRole));
            userRepository.save(user);
        }
    }

    private void createDefaultAdminIfNotExists() {
        Role userRole = roleRepository.findByName("ROLE_ADMIN").get();
        for (int i = 1; i <= 2; i++) {
            String defaultEmail = "quanh2104" + i + "@gmail.com";
            if (userRepository.existsByEmail(defaultEmail)) {
                continue;
            }
            User user = new User();
            user.setFirstName("Quang Anh" + i);
            user.setLastName("Luong");
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Set.of(userRole));
            userRepository.save(user);
        }
    }

    private void createDefaultRoleIfNotExists(Set<String> roles) {
        roles.stream().filter(role -> roleRepository.findByName(role).isEmpty()).map(Role::new)
                .forEach(roleRepository::save);
    }
}
