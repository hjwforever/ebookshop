
<!-- @import "[TOC]" {cmd="toc" depthFrom=1 depthTo=6 orderedList=false} -->

<!-- code_chunk_output -->

- [E-Book Shop](#e-book-shop)
    - [Project Structure](#project-structure)
    - [API Doc](#api-doc)

<!-- /code_chunk_output -->
# E-Book Shop
> E-BookShop Project for Spring Boot with Spring Security, Alibaba Druid,  Thymeleaf, MySQL, Springdoc

## Project Structure
```bash
├─.gitignore
├─.idea
├─.mvn
├─ebookshop.iml
├─LICENSE ------------------------------------------ // MIT License
├─mvnw
├─mvnw.cmd
├─pom.xml
├─README.md ---------------------------------------- // README文档
├─src
│ ├─main
│ │ ├─java
│ │ │ └─com
│ │ │   └─aruoxi
│ │ │     └─ebookshop
│ │ │       ├─common
│ │ │       │ └─CommonResult.java
│ │ │       ├─config ------------------------------- // 所有配置类
│ │ │       │ ├─ApplicationInitializer.java -------- // 程序初始化
│ │ │       │ ├─ExceptionHandle.java --------------- // 全局异常拦截器
│ │ │       │ ├─MyProps.java ----------------------- // 读取特定配置用于初始化
│ │ │       │ ├─SecurityConfiguration.java --------- // Spring Security配置类
│ │ │       │ └─springdoc -------------------------- // springdoc配置类
│ │ │       ├─controller --------------------------- // 路由控制器
│ │ │       │ ├─BookController.java
│ │ │       │ ├─dto -------------------------------- // 数据传输对象类集合
│ │ │       │ ├─LoginController.java
│ │ │       │ ├─MainController.java
│ │ │       │ ├─restController
│ │ │       │ └─UserRegistrationController.java
│ │ │       ├─domain ------------------------------- // 实体类
│ │ │       │ ├─Book.java
│ │ │       │ ├─QBook.java ------------------------- // Book属性类
│ │ │       │ ├─Role.java
│ │ │       │ └─User.java
│ │ │       ├─EbookshopApplication.java ------------ // 主程序启动类
│ │ │       ├─exception ---------------------------- // 自定义异常类
│ │ │       ├─repository --------------------------- // repository
│ │ │       │ ├─BookRepository.java
│ │ │       │ ├─RoleRepository.java
│ │ │       │ └─UserRepository.java
│ │ │       └─service ------------------------------ // 服务类
│ │ │         ├─BookService.java
│ │ │         ├─impl ------------------------------- // 服务的具体实现类
│ │ │         │ ├─BookServiceImpl.java
│ │ │         │ ├─RoleServiceImpl.java
│ │ │         │ └─UserServiceImpl.java
│ │ │         ├─RoleService.java
│ │ │         └─UserService.java
│ │ └─resources
│ │   ├─application.yml ---------------------------- // 配置文件
│ │   ├─banner.txt --------------------------------- // 自定义banner
│ │   ├─data.sql ----------------------------------- // 初始化数据sql
│ │   ├─META-INF
│ │   ├─schema.sql --------------------------------- // 初始化数据结构sql
│ │   ├─static ------------------------------------- // 静态文件
│ │   │ ├─assets
│ │   │ ├─css
│ │   │ ├─images
│ │   │ ├─js
│ │   │ └─uploadFile ------------------------------- // 上传文件
│ │   └─templates ---------------------------------- // 模板
│ │     ├─content.html
│ │     ├─home.html
│ │     ├─login.html
│ │     └─registration.html
│ └─test ------------------------------------------- // 测试类
│   └─java
│     └─com
│       └─aruoxi
│         └─ebookshop
│           ├─ApplicationTests.java
│           ├─dto
│           └─EbookshopApplicationTests.java
└─target
```

## API Doc
[http://localhost:8081/springdoc/index.html](http://localhost:8081/springdoc/index.html)
![Online Document](http://rb.gy/mfkhxf)

[http://localhost:8081/springdoc/api-docs](http://localhost:8081/springdoc/api-docs)
![JSON Document](http://rb.gy/4onqpz)
