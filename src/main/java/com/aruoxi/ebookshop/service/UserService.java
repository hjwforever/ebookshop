package com.aruoxi.ebookshop.service;

import com.aruoxi.ebookshop.controller.dto.RegistrationDto;
import com.aruoxi.ebookshop.controller.restController.dto.RestRegistrationDto;
import com.aruoxi.ebookshop.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User findUser(String usernameOrEmail);
    User findByEmail(String email);
    User findByUsername(String username);
    User save(RestRegistrationDto registration);
}
