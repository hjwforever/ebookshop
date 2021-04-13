package com.aruoxi.ebookshop.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .components(new Components())
        .info(new Info()
            .title("E-BookShop API")
            .description("This is E-BookShop‘s Spring Boot RESTful service using springdoc-openapi and OpenAPI 3.")
            .version("0.1 Beta")
            .contact(new io.swagger.v3.oas.models.info.Contact().url("http://ebookshop.aruoxi.com").name("admin:hjwforever").email("hjwforever@foxmail.com"))
        );
  }
}