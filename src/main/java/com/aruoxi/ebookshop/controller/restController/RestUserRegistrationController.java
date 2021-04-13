package com.aruoxi.ebookshop.controller.restController;

import com.aruoxi.ebookshop.common.CommonResult;
import com.aruoxi.ebookshop.controller.dto.RegistrationDto;
import com.aruoxi.ebookshop.domain.User;
import com.aruoxi.ebookshop.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@Tag(name = "注册接口")
@RestController
@RequestMapping(value = {"/api/signup","/api/register","api/registration"})
public class RestUserRegistrationController {

    @Resource
    private UserService userService;

    @PostMapping
    public CommonResult registerUserAccount(@Valid @RequestBody RegistrationDto userDto,
                                      BindingResult result){
        User existing = userService.findByEmail(userDto.getEmail());
        if (existing != null) {
            return CommonResult.fail(HttpStatus.BAD_REQUEST,"There is already an account registered with that email");
        }

        if (result.hasErrors()) {
            return CommonResult.fail(HttpStatus.BAD_REQUEST,"error");
        }

        userService.save(userDto);
        return CommonResult.success();
    }

}
