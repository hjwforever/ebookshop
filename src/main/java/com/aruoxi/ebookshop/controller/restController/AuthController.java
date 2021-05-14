package com.aruoxi.ebookshop.controller.restController;

import com.aruoxi.ebookshop.common.CommonResult;
import com.aruoxi.ebookshop.controller.restController.dto.*;
import com.aruoxi.ebookshop.common.JwtUtils;
import com.aruoxi.ebookshop.domain.RefreshToken;
import com.aruoxi.ebookshop.domain.User;
import com.aruoxi.ebookshop.exception.TokenRefreshException;
import com.aruoxi.ebookshop.repository.UserRepository;
import com.aruoxi.ebookshop.service.impl.RefreshTokenService;
import com.aruoxi.ebookshop.service.impl.UserDetailsImpl;
import com.aruoxi.ebookshop.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.catalina.Store;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Tag(name = "注册API接口")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private static final Logger log = LoggerFactory.getLogger(AuthController.class);
	@Resource
	AuthenticationManager authenticationManager;
	@Resource
	UserRepository userRepository;
	@Resource
	UserServiceImpl userService;
	@Resource
	RefreshTokenService refreshTokenService;
	@Resource
	JwtUtils jwtUtils;

	@Operation(summary = "进行登录,返回登录信息",
			description = "根据用户名(或邮箱)及密码登录",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "登录信息",
							content = @Content(mediaType = "application/json",
							schema = @Schema(implementation = LoginRequest.class))),
					@ApiResponse(
							responseCode = "400",
							description = "返回400时候错误的原因")}/*,security = @SecurityRequirement(name = "需要认证")*/)
	@PostMapping(value = {"/signin","/login"})
	public CommonResult<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
			String jwt = jwtUtils.generateJwtToken(userDetails);

			List<String> roles = userDetails.getAuthorities().stream()
					.map(GrantedAuthority::getAuthority)
					.collect(Collectors.toList());

			RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

			return CommonResult.success("login successfully!", new JwtResponse(jwt,refreshToken.getToken(),
					userDetails.getId(),
					userDetails.getUsername(),
					userDetails.getEmail(),
					roles));
		} catch (BadCredentialsException exception) {
			return CommonResult.fail(HttpStatus.BAD_REQUEST,"用户名或密码错误");
		}
	}

	@Operation(summary = "注册新用户",
			description = "注册新用户",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "注册信息",
							content = @Content(mediaType = "application/json",
									schema = @Schema(implementation = RestRegistrationDto.class)))})
	@PostMapping(value = {"/signup","/register"})
	public CommonResult<Object> registerUser(@Valid @RequestBody RestRegistrationDto registrationDto, BindingResult result) {
		if (result.hasErrors()) {
			return CommonResult.fail(HttpStatus.BAD_REQUEST, Objects.requireNonNull(result.getFieldError()).getField() + result.getFieldError().getDefaultMessage());
		}

		if (userRepository.existsByUsername(registrationDto.getUsername())) {
			return CommonResult.fail(HttpStatus.BAD_REQUEST,"Error: Username is already taken!");
		}

		if (userRepository.existsByEmail(registrationDto.getEmail())) {
			return CommonResult.fail(HttpStatus.BAD_REQUEST,"Error: Email is already in use!");
		}

		User user = userService.save(registrationDto);

		if (user == null) {
			return CommonResult.fail(HttpStatus.BAD_REQUEST,"Error: the user already exists!");
		}

		UserInfo userInfo = new UserInfo(user);
		log.info("UserInfo = " + userInfo);
		return CommonResult.success("User registered successfully!", userInfo);
	}

	@PostMapping("/refreshtoken")
	public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
		String requestRefreshToken = request.getRefreshToken();

		return refreshTokenService.findByToken(requestRefreshToken)
				.map(refreshTokenService::verifyExpiration)
				.map(RefreshToken::getUser)
				.map(user -> {
					String token = jwtUtils.generateTokenFromUsername(user.getUsername());
					return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
				})
				.orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
						"Refresh token is not in database!"));
	}

}
