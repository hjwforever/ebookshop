package com.aruoxi.ebookshop.controller.restController.dto;

import com.aruoxi.ebookshop.domain.Role;
import com.aruoxi.ebookshop.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Schema(example = "{\n" +
		"  \"name\": \"张三\",\n" +
		"  \"avatar\": \"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif\",\n" +
		"  \"email\": \"zhangsan@ebookshop.com\",\n" +
		"  \"introduction\": \"我就是张三\",\n" +
		"  \"roles\": [\n" +
		"    \"user\"\n" +
		"  ]\n" +
		"}")
@Tag(name = "DTO")
public class UserInfo {

	@NotEmpty
	@Size(min = 4, max = 20)
	@Schema(description = "用户id", example = "21217821", required = true, maxLength = 20)
	private Long uid;

	@NotEmpty
	@Size(min = 4, max = 20)
	@Schema(description = "用户名", example = "zhangsan", required = true,minLength = 4, maxLength = 20)
	private String username;

	@NotEmpty
	@Size(min = 4, max = 20)
	@Schema(description = "用户昵称", example = "张三123", required = true,minLength = 4, maxLength = 20)
	private String name;

	@Schema(description = "头像", type = "String")
	private String avatar;

	@Email
	@NotEmpty
	@Schema(description = "邮箱", example = "zhangsan@ebookshop.com", required = true)
	private String email;


	@Schema(description = "角色数组", type = "Array")
	private Set<String> roles;

	@Schema(description = "介绍", type = "String")
	private String introduction;

	public UserInfo() {
		this.name = "张三";
		this.username = "zhangsan";
		this.uid = 1221211212L;
		this.avatar = "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif";
		this.email = "zhangsan@ebookshop.com";
		this.introduction = "我就是张三";
		this.roles = new HashSet<>();
		this.roles.add("user");
	}

	public UserInfo(@NotEmpty @Size(min = 4, max = 20) String name, @Email @NotEmpty String email, @NotEmpty @Size(min = 6, max = 16) String password, Set<String> roles) {
		this.name = name;
		this.email = email;
		if (roles == null || roles.isEmpty()) {
			this.roles = new HashSet<>();
			this.roles.add("user");
		} else this.roles = roles;
	}

	public UserInfo(User user) {
		this.username = user.getUsername();
		this.uid = user.getId();
		this.name = user.getNickname();
		this.introduction = user.getIntroduction();
		this.avatar = user.getAvatar();
		this.email = user.getEmail();
		if (user.getRoles() != null) {
			this.roles = user.getRoles().stream().map(role -> role.getName().toUpperCase().startsWith("ROLE_") ? role.getName().substring(5).toLowerCase() : role.getName().toLowerCase()).collect(Collectors.toSet());
		} else {
			this.roles = new HashSet<>();
			this.roles.add("user");
		}
	}
}
