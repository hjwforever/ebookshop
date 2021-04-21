package com.aruoxi.ebookshop.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.aruoxi.ebookshop.controller.dto.RegistrationDto;
import com.aruoxi.ebookshop.controller.restController.dto.RestRegistrationDto;
import com.aruoxi.ebookshop.domain.Role;
import com.aruoxi.ebookshop.domain.User;
import com.aruoxi.ebookshop.repository.UserRepository;
import com.aruoxi.ebookshop.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
    @Resource
    private UserRepository userRepository;
    @Resource
    private BCryptPasswordEncoder passwordEncoder;
    @Resource
    private RoleServiceImpl roleService;

    @Override
    public User findByEmail(String email){
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public User save(RestRegistrationDto registration) {
        Set<Role> roles = new HashSet<>();
        Set<String> strRoles = registration.getRoles();
        String email = registration.getEmail();
        String username = registration.getUsername();

        if (userRepository.findByEmail(email).isPresent()) {
            //  throw new ValidationException("Email exists!");
            return null;
        }

        if (userRepository.findByUsername(username).isPresent()) {
            //  throw new ValidationException("Username exists!");
            return null;
        }

        if (strRoles.isEmpty()) {
            strRoles.add("ROLE_USER");
        }

        User user = new User();
        user.setUsername(registration.getUsername());
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(registration.getPassword()));

        strRoles.forEach(role -> {
                Role _role = roleService.findByName(role);
                roles.add(_role);
            });
        log.info("roles = " + roles);

        log.info("roles Size = " + roles.size());
        log.info("roles Size = " + roles.stream().count());
        log.info("roles Size filter = " + roles.stream().filter(Objects::nonNull));
        log.info("roles Size filter count = " + roles.stream().filter(Objects::nonNull).count());
        user.setRoles(roles.stream().noneMatch(Objects::nonNull) ?
            Collections.singletonList(roleService.findByName("ROLE_USER"))
            : roles);
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

}
