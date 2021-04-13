package com.aruoxi.ebookshop.controller;

import com.aruoxi.ebookshop.controller.dto.RegistrationDto;
import com.aruoxi.ebookshop.domain.User;
import com.aruoxi.ebookshop.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
@RequestMapping(value = {"/registration","register", "/signup"})
public class UserRegistrationController {

    @Resource
    private UserService userService;

    @ModelAttribute("user")
    public RegistrationDto userRegistrationDto() {
        return new RegistrationDto();
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        return "registration";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") @Valid RegistrationDto userDto,
                                      BindingResult result){

        User existing = userService.findByEmail(userDto.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }

        if (result.hasErrors()) {
            return "registration";
        }

        userService.save(userDto);
        return "redirect:/registration?success";
    }

}
