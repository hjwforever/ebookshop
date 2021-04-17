package com.aruoxi.ebookshop.controller.restController;

import com.aruoxi.ebookshop.common.CommonResult;
import com.aruoxi.ebookshop.controller.dto.LoginDto;
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
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Tag(name = "登录API接口")
@RestController
public class RestLoginController {
    private static final Logger log = LoggerFactory.getLogger(RestLoginController.class);


    @RequestMapping("/api/login")
    @Operation(summary = "测试登录的接口",
        description = "描述的文字",
        responses = {
            @ApiResponse(description = "登录信息",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Store.class))),
            @ApiResponse(responseCode = "400", description = "返回400时候错误的原因")},
        security = @SecurityRequirement(name = "需要认证"))
    public CommonResult login() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("auth = " + auth);
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return CommonResult.fail(HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.getReasonPhrase());
        } else return CommonResult.success();
    }

    @Operation(summary = "登出",
        description = "登出并清除登录信息")
    @GetMapping(value = "/api/logout")
    public CommonResult logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return CommonResult.success();
    }

}
