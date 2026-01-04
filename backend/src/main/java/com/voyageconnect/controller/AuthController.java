package com.voyageconnect.controller;

import com.voyageconnect.controller.dto.RegisterRequest;
import com.voyageconnect.model.User;
import com.voyageconnect.model.UserRole;
import com.voyageconnect.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import com.voyageconnect.security.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final long jwtExpireMs;
    private final boolean cookieSecure;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,
                          @Value("${app.jwt.secret:dev-secret}") String jwtSecret,
                          @Value("${app.jwt.expire-ms:86400000}") long jwtExpireMs,
                          @Value("${app.cookie.secure:false}") boolean cookieSecure) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = new JwtUtil(jwtSecret, jwtExpireMs);
        this.jwtExpireMs = jwtExpireMs;
        this.cookieSecure = cookieSecure;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        if (userRepository.findByEmail(req.getEmail()).isPresent()) {
            return ResponseEntity.status(409).body("Email already used");
        }

        User u = User.builder()
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .role(UserRole.USER)
                .build();

        User saved = userRepository.save(u);
        return ResponseEntity.created(URI.create("/api/users/" + saved.getId())).build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody RegisterRequest req, HttpServletResponse response) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
        String role = "USER";
        if (auth.getAuthorities() != null && !auth.getAuthorities().isEmpty()) role = auth.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");
        String token = jwtUtil.generateToken(req.getEmail(), role);
        Cookie cookie = new Cookie("VC_TOKEN", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(cookieSecure);
        cookie.setPath("/");
        cookie.setMaxAge((int) (jwtExpireMs / 1000));
        response.addCookie(cookie);
        return ResponseEntity.ok().body("ok");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("VC_TOKEN", "");
        cookie.setHttpOnly(true);
        cookie.setSecure(cookieSecure);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return ResponseEntity.ok().body("ok");
    }
}
