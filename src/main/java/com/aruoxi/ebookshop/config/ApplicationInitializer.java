package com.aruoxi.ebookshop.config;

import com.aruoxi.ebookshop.controller.dto.RegistrationDto;
import com.aruoxi.ebookshop.domain.User;
import com.aruoxi.ebookshop.repository.BookRepository;
import com.aruoxi.ebookshop.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Component
public class ApplicationInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationInitializer.class);

    private final List<String> emails = Arrays.asList("test","cfx","ds","hjw");
    private final List<String> usernames = Arrays.asList("Ada Lovelace","Alan Turing","Dennis Ritchie","Mark Jhon");

    private final UserService userService;

    public ApplicationInitializer(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
//        LOG.info("----------开始添加用户-------------------");

//        for (int i = 0; i < emails.size(); ++i) {
//            RegistrationDto request = new RegistrationDto();
//            request.setUsername(usernames.get(i));
//            request.setEmail(emails.get(i));
//            String password = "123456";
//            request.setPassword(password);
//            User savedUser = userService.save(request);
//            LOG.info(savedUser != null ? "创建新用户 " + savedUser : "用户创建失败");
//        }

//        LOG.info("----------添加完毕-------------------");
    }
}