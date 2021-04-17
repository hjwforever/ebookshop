package com.aruoxi.ebookshop.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Schema(description = "注册信息DTO")
@Tag(name = "DTO")
public class RegistrationDto {

    @NotEmpty
    @Size(min = 4, max = 20)
    @Schema(description = "用户名", required = true)
    private String username;

    @Email
    @NotEmpty
    @Schema(description = "邮箱", required = true)
    private String email;

    @NotEmpty
    @Size(min = 6, max = 16)
    @Schema(description = "密码", required = true)
    private String password;

    @NotEmpty
    @Size(min = 6, max = 16)
    @Schema(description = "确认密码")
    private String confirmPassword;

    @AssertTrue
    @Schema(description = "是否同意协议")
    private Boolean terms;
}
