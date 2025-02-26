package org.example.jwt.service;

import org.example.jwt.model.dto.request.AppUserRequest;
import org.example.jwt.model.dto.response.AppUserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AppUserService extends UserDetailsService {
    AppUserResponse register(AppUserRequest appUserRequest);
}