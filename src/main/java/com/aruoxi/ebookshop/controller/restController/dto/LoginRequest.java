package com.aruoxi.ebookshop.controller.restController.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Schema(example = "{\n" +
		"  \"username\": \"zhangsan@ebookshop.com\",\n" +
		"  \"password\": \"12121212\"\n" +
		"}")
public class LoginRequest {
	@NotBlank
	@Schema(description = "用户名或邮箱", required = true)
	private String username;

	@NotBlank
	@Schema(description = "密码", required = true)
	private String password;
}
