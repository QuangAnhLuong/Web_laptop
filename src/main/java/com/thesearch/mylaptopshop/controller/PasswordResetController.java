package com.thesearch.mylaptopshop.controller;

import com.thesearch.mylaptopshop.response.ApiResponse;
import com.thesearch.mylaptopshop.service.auth.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/password-reset")
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;

    // Yêu cầu gửi link reset mật khẩu
    @PostMapping("/request")
    public ResponseEntity<ApiResponse> requestResetPassword(@RequestParam String email) {
        try {
            passwordResetService.sendResetPasswordLink(email);
            return ResponseEntity.ok(new ApiResponse("Link reset mật khẩu đã được gửi tới email.", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Email không hợp lệ hoặc có lỗi xảy ra.", null));
        }
    }

    // Đặt lại mật khẩu
    @PostMapping("/reset")
    public ResponseEntity<ApiResponse> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        try {
            if (passwordResetService.validateToken(token)) {
                // Cập nhật mật khẩu
                passwordResetService.updatePassword(token, newPassword);
                return ResponseEntity.ok(new ApiResponse("Mật khẩu đã được đặt lại thành công.", null));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Token không hợp lệ hoặc đã hết hạn.", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Đã xảy ra lỗi trong quá trình đặt lại mật khẩu.", null));
        }
    }
}
