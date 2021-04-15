package com.aruoxi.ebookshop.controller.restController.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@Schema
public class RestRegistrationDto {

    @NotEmpty
    @Size(min = 4, max = 20)
    private String username;

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    @Size(min = 6, max = 16)
    private String password;

    private Set<String> roles;

    public RestRegistrationDto(@NotEmpty @Size(min = 4, max = 20) String username, @Email @NotEmpty String email, @NotEmpty @Size(min = 6, max = 16) String password, Set<String> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        if (roles == null || roles.isEmpty()) {
            this.roles = new HashSet<>();
            this.roles.add("ROLE_USER");
        } else this.roles = roles;
    }
}