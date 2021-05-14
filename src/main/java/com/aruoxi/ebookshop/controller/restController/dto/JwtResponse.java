package com.aruoxi.ebookshop.controller.restController.dto;

import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class JwtResponse {
	private String token;
	private String refreshToken;
	private String type = "Bearer";
	private Long id;
	private String username;
	private String email;
	private List<String> roles;

	public JwtResponse(String accessToken, String refreshToken, Long id, String username, String email, List<String> roles) {
		this.token = accessToken;
		this.refreshToken = refreshToken;
		this.id = id;
		this.username = username;
		this.email = email;
		this.roles = roles.stream().map(role -> role.toUpperCase().startsWith("ROLE_") ? role.substring(5).toLowerCase() : role.toLowerCase()).collect(Collectors.toList());
	}
}
