package com.aruoxi.ebookshop.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Schema
public class RegistrationDto {

    @NotEmpty
    @Size(min = 4, max = 20)
    private String username;

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    @Size(min = 6, max = 16)
    private String password;

    @NotEmpty
    @Size(min = 6, max = 16)
    private String confirmPassword;

    @AssertTrue
    private Boolean terms;
}
