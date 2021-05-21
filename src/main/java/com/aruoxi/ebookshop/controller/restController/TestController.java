package com.aruoxi.ebookshop.controller.restController;

import com.aruoxi.ebookshop.common.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "测试权限 API接口")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

	@GetMapping("/all")
	@Operation(summary = "测试公共权限",
			description = "测试公共权限",
			security = @SecurityRequirement(name = "无需权限"))
	public CommonResult<String> allAccess() {
		return CommonResult.success("Public Content.");
	}

	@GetMapping("/user")
	@PreAuthorize("hasAnyRole('USER','SELLER','ADMIN')")
	@Operation(summary = "测试用户权限",
			description = "测试用户权限",
			security = @SecurityRequirement(name = "至少需要user权限"))
	public CommonResult<String> userAccess() {
		return CommonResult.success("User Content.");
	}

	@GetMapping("/seller")
	@PreAuthorize("hasRole('SELLER')")
	@Operation(summary = "测试卖家权限",
			description = "测试卖家权限",
			security = @SecurityRequirement(name = "需要seller权限"))
	public CommonResult<String> moderatorAccess() {
		return CommonResult.success("SELLER Board.");
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "测试管理员权限",
			description = "测试管理员权限",
			security = @SecurityRequirement(name = "需要admin权限"))
	public CommonResult<String> adminAccess() {
		return CommonResult.success("Admin Board.");
	}
}
