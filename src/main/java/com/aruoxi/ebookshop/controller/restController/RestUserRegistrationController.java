package com.aruoxi.ebookshop.controller.restController;

import com.aruoxi.ebookshop.common.CommonResult;
import com.aruoxi.ebookshop.controller.restController.dto.RestRegistrationDto;
import com.aruoxi.ebookshop.domain.User;
import com.aruoxi.ebookshop.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Objects;

@Tag(name = "注册API接口")
@RestController
@RequestMapping(value = {"/api/signup","/api/register","/api/registration"})
public class RestUserRegistrationController {

    private static final Logger log = LoggerFactory.getLogger(RestUserRegistrationController.class);
    @Resource
    private UserService userService;

    @PostMapping
    @Operation(summary = "注册新用户",
        description = "注册新用户")
    public CommonResult registerUserAccount(@Valid @RequestBody RestRegistrationDto userDto,
                                      BindingResult result){
        log.info("userDto = " + userDto);
        User existing = userService.findByEmail(userDto.getEmail());
        if (existing != null) {
            return CommonResult.fail(HttpStatus.BAD_REQUEST,"There is already an account registered with that email");
        }

        if (result.hasErrors()) {
            return CommonResult.fail(HttpStatus.BAD_REQUEST, Objects.requireNonNull(result.getFieldError()).getField() + "  " + Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }

        User user = userService.save(userDto);
        return CommonResult.success(user);
    }

}
