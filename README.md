- [E-Book Shop](#e-book-shop)
  - [Project Structure](#project-structure)
  - [API Doc](#api-doc)
  - [E-Book Shop 功能文档](#e-book-shop-功能文档)
    - [基本功能](#基本功能)
    - [进阶功能](#进阶功能)
    - [集成功能](#集成功能)


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
![Online Document](https://cdn.jsdelivr.net/gh/hjwforever/images@main/img/2021/04/17/springdoc.png)

[http://localhost:8081/springdoc/api-docs](http://localhost:8081/springdoc/api-docs)
![JSON Document](https://cdn.jsdelivr.net/gh/hjwforever/images@main/img/2021/04/17/JsonDocumen.png)


- [E-Book Shop 功能文档](#e-book-shop-功能文档)
  - [基本功能](#基本功能)
  - [进阶功能](#进阶功能)
  - [集成功能](#集成功能)

## E-Book Shop 功能文档
> 详情可看**附件设计文档**或**项目在线接口文档**
### 基本功能
- 登录
- 注册
- 看书
- 上传
- 音乐播放器
- 书籍的增删改查(API)

### 进阶功能
1. 分页+ajax局部刷新
- 书籍列表分页 及 局部刷新
- 阅读书籍分页 及 局部刷新
2.  拦截器及过滤器 (intercepters/filters)
- 路由拦截器
- 全局异常拦截器
- 登录跳转至首页
3. 集成Spring Security (Authentication and authorization)
- 登录注册
- 权限分级
- 密码加密
- 路由拦截
4. 权限分级
-  游客(未登录用户)只能浏览书籍列表，不可阅读及下载

- 登录用户(User) 可以阅读及下载书籍, 但不可上传书籍

- 管理员(Admin) 拥有以上所有权限， 并可上传及修改书籍 （增删改查）


### 集成功能
1. SpringSecurity
2. Druid数据库监控(访问路径 http:/localhost:8081/druid)
   ![2021/04/17/druid](https://cdn.jsdelivr.net/gh/hjwforever/images@main/img/2021/04/17/druid.png)
3. Springdoc在线接口文档
- Online Document: [http://localhost:8081/springdoc/index.html](http://localhost:8081/springdoc/index.html)
  ![Online Document](https://cdn.jsdelivr.net/gh/hjwforever/images@main/img/2021/04/17/springdoc.png)

- JSON Document: [http://localhost:8081/springdoc/api-docs](http://localhost:8081/springdoc/api-docs)
  ![JSON Document](https://cdn.jsdelivr.net/gh/hjwforever/images@main/img/2021/04/17/JsonDocumen.png)
4. 音乐播放器
   ![2021/04/17/music](https://cdn.jsdelivr.net/gh/hjwforever/images@main/img/2021/04/17/music.png)
