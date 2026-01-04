package com.voyageconnect.service.interfaces;

import com.voyageconnect.controller.dto.RegisterRequest;
import com.voyageconnect.model.User;
import java.util.Optional;

public interface UserService {
    User register(RegisterRequest request);
    Optional<User> findByEmail(String email);
}
