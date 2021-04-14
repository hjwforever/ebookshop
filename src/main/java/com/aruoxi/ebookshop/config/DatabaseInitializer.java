package com.aruoxi.ebookshop.config;

import com.aruoxi.ebookshop.controller.dto.RegistrationDto;
import com.aruoxi.ebookshop.domain.Book;
import com.aruoxi.ebookshop.domain.User;
import com.aruoxi.ebookshop.repository.BookRepository;
import com.aruoxi.ebookshop.service.BookService;
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

@Component
public class DatabaseInitializer implements ApplicationListener<ApplicationReadyEvent> {

    @Resource
    private BookRepository bookRepository;

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

    private final List<String> bookname = Arrays.asList(
            "傲慢与偏见",
            "小王子",
            "自深深处",
            "王后的项链",
            "珍妮姑娘",
            "安娜·卡列尼娜",
            "飘",
            "雾都孤儿",
            "双城记",
            "复活",
            "洛丽塔",
            "普宁",
            "百年孤独",
            "动物庄园",
            "简爱",
            "情书",
            "徒然草"
    );

    private final List<String> author = Arrays.asList(
            "[英] 简·奥斯汀",
            "[法] 安东尼·德·圣-埃克苏佩里",
            "[爱尔兰] 奥斯卡·王尔德",
            "大仲马",
            "[美国] 西奥多·德莱塞",
            "[俄] 列夫·托尔斯泰",
            "[美国] 玛格丽特·米切尔",
            "[英]狄更斯",
            "[英] 查尔斯·狄更斯",
            "[俄]列夫·托尔斯泰",
            "[美] 弗拉基米尔·纳博科夫",
            "[美] 弗拉基米尔·纳博科夫",
            "[哥伦比亚] 加西亚·马尔克斯",
            "乔治•奥威尔 (George Orwell)",
            "[英] 夏洛蒂·勃朗特",
            "[日] 岩井俊二",
            "[日] 吉田兼好"
    );

    private final List<String> bookimag = Arrays.asList(
            "https://img2.doubanio.com/view/subject/s/public/s4250062.jpg",
            "https://img2.doubanio.com/view/subject/s/public/s1103152.jpg",
            "https://img1.doubanio.com/view/subject/s/public/s3051859.jpg",
            "https://img1.doubanio.com/view/subject/s/public/s33475967.jpg",
            "https://img3.doubanio.com/view/subject/l/public/s3515331.jpg",
            "https://img1.doubanio.com/view/subject/s/public/s5763939.jpg",
            "https://img1.doubanio.com/view/subject/s/public/s1078958.jpg",
            "https://img3.doubanio.com/view/subject/s/public/s1300531.jpg",
            "https://img2.doubanio.com/view/subject/s/public/s29832712.jpg",
            "https://img9.doubanio.com/view/subject/s/public/s1204746.jpg",
            "https://img1.doubanio.com/view/subject/s/public/s1483347.jpg",
            "https://img3.doubanio.com/view/subject/s/public/s1987350.jpg",
            "https://img3.doubanio.com/view/subject/s/public/s27237850.jpg",
            "https://img3.doubanio.com/view/subject/s/public/s3858411.jpg",
            "https://img1.doubanio.com/view/subject/s/public/s33473798.jpg",
            "https://img9.doubanio.com/view/subject/s/public/s1127135.jpg",
            "https://img9.doubanio.com/view/subject/s/public/s3666786.jpg"
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

/**
 * 添加书籍信息
  */
        for(int i=0;i<bookname.size();i++){
            Book request = new Book();
            request.setBookName(bookname.get(i));
            request.setAuthor(author.get(i));
            request.setBookCoverImg(bookimag.get(i));
            bookRepository.save(request);
        }
    }
}