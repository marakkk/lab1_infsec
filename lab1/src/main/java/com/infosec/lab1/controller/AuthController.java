package com.infosec.lab1.controller;

import com.infosec.lab1.dto.AuthResponse;
import com.infosec.lab1.dto.LoginRequest;
import com.infosec.lab1.exceptions.AuthenticationException;
import com.infosec.lab1.jwt.JwtUtils;
import com.infosec.lab1.repo.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            log.error("Wrong password");
            throw new AuthenticationException("Wrong password");
        }

        String token = jwtUtils.generateToken(user.getUsername());
        log.info("Token was generated successfully: {}", token);
        return ResponseEntity.ok(new AuthResponse(token));
    }

}
