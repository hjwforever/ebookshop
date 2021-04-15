package com.aruoxi.ebookshop.controller.restController;

import com.aruoxi.ebookshop.common.CommonResult;
import com.aruoxi.ebookshop.controller.dto.LoginDto;
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


@RestController
public class RestLoginController {

    @GetMapping("/api/login")
    public CommonResult login() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            // todo
            //  return "redirect:/contacts";
            return CommonResult.fail(HttpStatus.BAD_REQUEST, "未授权");
        } else return CommonResult.success();
    }

    @GetMapping(value = "/api/logout")
    public CommonResult logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return CommonResult.success();
    }
}
