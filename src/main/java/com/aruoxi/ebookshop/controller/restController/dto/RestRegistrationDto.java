package com.aruoxi.ebookshop.controller.restController.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@Schema(example = "{\n" +
    "  \"password\": \"12121212\",\n" +
    "  \"roles\": [\n" +
    "    \"user\"\n" +
    "  ],\n" +
    "  \"email\": \"zhan21gsan@ebookshop.com\",\n" +
    "  \"username\": \"张三123\"\n" +
    "}")
@Tag(name = "DTO")
public class RestRegistrationDto {

    @NotEmpty
    @Size(min = 4, max = 20)
    @Schema(description = "用户名", example = "张三123", required = true,minLength = 4, maxLength = 20)
    private String username;

    @Email
    @NotEmpty
    @Schema(description = "邮箱", example = "zhangsan@ebookshop.com", required = true)
    private String email;

    @NotEmpty
    @Size(min = 6, max = 16)
    @Schema(description = "密码", example = "123456", required = true, minLength = 6, maxLength = 16)
    private String password;

    @Schema(description = "角色数组", type = "Array")
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