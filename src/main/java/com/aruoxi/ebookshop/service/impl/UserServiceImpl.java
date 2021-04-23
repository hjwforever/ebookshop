package com.aruoxi.ebookshop.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.aruoxi.ebookshop.common.RegexUtil;
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
    @Resource
    RegexUtil regexUtil;

    @Override
    public User findUser(String usernameOrEmail) {
        Optional<User> user = regexUtil.isEmail(usernameOrEmail) ?
            userRepository.findByEmail(usernameOrEmail) :
            userRepository.findByUsername(usernameOrEmail);
        return user.orElse(null);
    }

    @Override
    public User findByEmail(String email){
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByEmail(username).orElse(null);
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

        User user = new User(registration.getUsername(),email,passwordEncoder.encode(registration.getPassword()));

        strRoles.forEach(role -> {
                Role _role = roleService.findByName(role);
                roles.add(_role);
            });
        log.info("roles = " + roles);
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
