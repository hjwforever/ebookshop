package com.aruoxi.ebookshop.config;

import com.aruoxi.ebookshop.controller.dto.RegistrationDto;
import com.aruoxi.ebookshop.domain.Book;
import com.aruoxi.ebookshop.domain.User;
import com.aruoxi.ebookshop.service.UserService;
import com.aruoxi.ebookshop.service.impl.BookServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Component
public class DatabaseInitializer implements ApplicationListener<ApplicationReadyEvent> {

    @Resource
    private BookServiceImpl bookService;

    private static final Logger LOG = LoggerFactory.getLogger(DatabaseInitializer.class);

    private final List<String> emails = Arrays.asList(
            "test",
            "cfx",
            "ds",
            "hjw"
    );
    private final List<String> usernames = Arrays.asList(
            "Ada Lovelace",
            "Alan Turing",
            "Dennis Ritchie",
            "Mark Jhon"
    );

//    private final List<String> roles = Arrays.asList(
//            "Role_USER",
//            "Role_ADMIN",
//            "Role_SELLER"
//    );

    private final UserService userService;

    public DatabaseInitializer(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
//        LOG.info("----------开始添加用户-------------------");
        for (int i = 0; i < emails.size(); ++i) {
            RegistrationDto request = new RegistrationDto();
            request.setUsername(usernames.get(i));
            request.setEmail(emails.get(i));
            String password = "123456";
            request.setPassword(password);
            User savedUser = userService.save(request);
//            LOG.info(savedUser != null ? "创建新用户 " + savedUser : "用户创建失败");
        }
//        LOG.info("----------添加完毕-------------------");

        Book book = new Book();
        book.setBookName("12");
        bookService.save(book);
    }

}
