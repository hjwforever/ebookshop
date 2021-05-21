package com.aruoxi.ebookshop.controller.restController;

import com.aruoxi.ebookshop.common.CommonResult;
import com.aruoxi.ebookshop.common.JwtUtils;
import com.aruoxi.ebookshop.controller.restController.dto.UserInfo;
import com.aruoxi.ebookshop.domain.User;
import com.aruoxi.ebookshop.repository.UserRepository;
import com.aruoxi.ebookshop.service.UserService;
import com.aruoxi.ebookshop.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("api/user")
public class RestUserController {

  private static final Logger log = LoggerFactory.getLogger(RestUserController.class);
  @Resource
  private UserServiceImpl userService;
  @Resource
  private UserRepository userRepository;
  @Resource
  private JwtUtils jwtUtils;

//  @PreAuthorize("hasRole('ADMIN')")
//  @GetMapping("/")
//  @Operation(summary = "获取用户列表",
//      description = "获取用户列表",
//      security = @SecurityRequirement(name = "需要admin权限"))
//  @Hidden
//  private CommonResult<List<User>> getUserList() {
//    List<User> users = userRepository.findAll();
//    return CommonResult.success(users);
//  }

  @GetMapping("/info")
  @Operation(summary = "获取个人信息",
      description = "更具获取个人信息",
      security = @SecurityRequirement(name = "至少需要用户身份"))
  private CommonResult<UserInfo> getUserInfo(HttpServletRequest request, @RequestParam String token) {
    String name = jwtUtils.getUserNameFromJwtToken(token);
    User user = userService.findUser(name);
    UserInfo userInfo = new UserInfo(user);
    log.info("userInfo" + userInfo);
    return CommonResult.success("success",userInfo);
  }
}
