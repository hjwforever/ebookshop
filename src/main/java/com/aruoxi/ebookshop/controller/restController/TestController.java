package com.aruoxi.ebookshop.controller.restController;

import com.aruoxi.ebookshop.common.CommonResult;
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
	public CommonResult<String> allAccess() {
		return CommonResult.success("Public Content.");
	}

	@GetMapping("/user")
	@PreAuthorize("hasAnyRole('USER','SELLER','ADMIN')")
	public CommonResult<String> userAccess() {
		return CommonResult.success("User Content.");
	}

	@GetMapping("/seller")
	@PreAuthorize("hasRole('SELLER')")
	public CommonResult<String> moderatorAccess() {
		return CommonResult.success("SELLER Board.");
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public CommonResult<String> adminAccess() {
		return CommonResult.success("Admin Board.");
	}
}
