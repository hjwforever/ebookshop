package com.aruoxi.ebookshop.controller.restController;

import com.aruoxi.ebookshop.common.CommonResult;
import com.aruoxi.ebookshop.common.JwtUtils;
import com.aruoxi.ebookshop.controller.restController.dto.UserInfo;
import com.aruoxi.ebookshop.domain.User;
import com.aruoxi.ebookshop.repository.UserRepository;
import com.aruoxi.ebookshop.service.UserService;
import com.aruoxi.ebookshop.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/user")
public class RestUserController {

  private static final Logger log = LoggerFactory.getLogger(RestUserController.class);
  @Resource
  private UserServiceImpl userService;
  @Resource
  private UserRepository userRepository;
  @Resource
  JwtUtils jwtUtils;

  @GetMapping("info")
//  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  private CommonResult<UserInfo> getUserInfo(HttpServletRequest request, @RequestParam String token) {
    String name = jwtUtils.getUserNameFromJwtToken(token);
    User user = userService.findUser(name);
    UserInfo userInfo = new UserInfo(user);
    log.info("userInfo" + userInfo);
    return CommonResult.success("success",userInfo);
  }

}
