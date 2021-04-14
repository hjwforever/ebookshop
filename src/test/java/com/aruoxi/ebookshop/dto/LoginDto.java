package com.aruoxi.ebookshop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Schema
public class LoginDto {
	@NotBlank
	private String username;

	@NotBlank
	private String password;
}
