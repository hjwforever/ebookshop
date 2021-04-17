//package com.aruoxi.ebookshop.config;
//
//import io.swagger.annotations.ApiOperation;
//import io.swagger.v3.oas.models.Components;
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Info;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.core.env.Profiles;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//
//@Configuration
//public class SwaggerConfig {
//
//  @Bean
//  public Docket initDocket(Environment env) {
//
//    //设置要暴漏接口文档的配置环境
//    Profiles profile = Profiles.of("dev", "test");
//    boolean flag = env.acceptsProfiles(profile);
//    return new Docket(DocumentationType.OAS_30)
//        .apiInfo(apiInfo())
//        .enable(flag)
//        .select()
//        .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
//        .paths(PathSelectors.any())
//        .build();
//  }
//
//  private ApiInfo apiInfo() {
//    return new ApiInfoBuilder()
//        .title("E-BookShop API")
//        .description("This is E-BookShop‘s Spring Boot RESTful service using springdoc-openapi and OpenAPI 3.")
//        .contact(new Contact("cfx, ds, hjw", "http://ebookshop.aruoxi.com", "hjwforever@foxmail.com"))
//        .version("1.0")
//        .build();
//  }
//}
////@Configuration
////public class OpenApiConfig {
////  @Bean
////  public OpenAPI customOpenAPI() {
////    return new OpenAPI()
////        .components(new Components())
////        .info(new Info()
////            .title("E-BookShop API")
////            .description("This is E-BookShop‘s Spring Boot RESTful service using springdoc-openapi and OpenAPI 3.")
////            .version("0.1 Beta")
////            .contact(new io.swagger.v3.oas.models.info.Contact().url("http://ebookshop.aruoxi.com").name("admin:hjwforever").email("hjwforever@foxmail.com"))
////        );
////  }
////}