package com.aruoxi.ebookshop.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Schema(description = "登录信息DTO")
@Tag(name = "DTO")
public class LoginDto {
	@NotBlank
	@Schema(description = "用户名", required = true)
	private String username;

	@NotBlank
	@Schema(description = "密码", required = true)
	private String password;
}
