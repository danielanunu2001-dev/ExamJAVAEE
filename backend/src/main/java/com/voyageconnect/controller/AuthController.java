package com.voyageconnect.controller;

import com.voyageconnect.controller.dto.RegisterRequest;
import com.voyageconnect.model.User;
import com.voyageconnect.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import com.voyageconnect.security.JwtUtil;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoints for user registration and login")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final long jwtExpireMs;
    private final boolean cookieSecure;

    public AuthController(UserService userService, AuthenticationManager authenticationManager,
                          @Value("${app.jwt.secret:dev-secret}") String jwtSecret,
                          @Value("${app.jwt.expire-ms:86400000}") long jwtExpireMs,
                          @Value("${app.cookie.secure:false}") boolean cookieSecure) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = new JwtUtil(jwtSecret, jwtExpireMs);
        this.jwtExpireMs = jwtExpireMs;
        this.cookieSecure = cookieSecure;
    }

    @Operation(summary = "Register a new user", responses = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "409", description = "Email already in use")
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        try {
            User saved = userService.register(req);
            return ResponseEntity.created(URI.create("/api/users/" + saved.getId())).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    @Operation(summary = "Login a user", responses = {
            @ApiResponse(responseCode = "200", description = "Login successful, JWT token set in cookie"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
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

    @Operation(summary = "Logout a user")
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
