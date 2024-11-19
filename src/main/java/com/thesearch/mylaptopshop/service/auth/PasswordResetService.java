package com.thesearch.mylaptopshop.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.thesearch.mylaptopshop.repository.UserRepository;
import com.thesearch.mylaptopshop.model.User;
import com.thesearch.mylaptopshop.exception.ResourceNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.UUID;

@Service
public class PasswordResetService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    public void sendResetPasswordLink(String email) {
        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            throw new ResourceNotFoundException("User not found with email: " + email);
        }

        String token = generateResetToken(user);
        sendEmailWithResetLink(user, token);
    }

    public boolean validateToken(String token) {
        return true;
    }

    // Cập nhật mật khẩu người dùng
    public void updatePassword(String token, String newPassword) {
        if (validateToken(token)) {
            String email = getEmailFromToken(token); // Trích xuất email từ token
            User user = userRepository.findByEmail(email).orElse(null);

            if (user == null) {
                throw new ResourceNotFoundException("User not found with email: " + email);
            }

            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        }
    }

    private String generateResetToken(User user) {
        return UUID.randomUUID().toString();
    }

    private void sendEmailWithResetLink(User user, String token) {
        String resetLink = "http://localhost:8080/api/password-reset/reset?token=" + token;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Yêu cầu đặt lại mật khẩu");
        message.setText("Vui lòng nhấp vào liên kết sau để đặt lại mật khẩu của bạn: " + resetLink);

        mailSender.send(message);
        System.out.println("Send email to " + user.getEmail() + " with link: " + resetLink);
    }

    private String getEmailFromToken(String token) {
        return "user@example.com";
    }
}
