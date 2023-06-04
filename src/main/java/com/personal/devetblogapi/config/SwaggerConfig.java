package com.personal.devetblogapi.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
  @Value("${devet-blog.openapi.dev-url}")
  private String devUrl;

  @Value("${devet-blog.openapi.prod-url}")
  private String prodUrl;

  @Bean
  public OpenAPI customOpenAPI() {

    Server devServer =
        new Server().url(devUrl).description("Server URL in Development environment");
    Server prodServer =
        new Server().url(prodUrl).description("Server URL in Production environment");

    Contact contact =
        new Contact()
            .email("devet.279@gmail.com")
            .name("Devet")
            .url("https://facebook.com/thangq.279");

    License mitLicense =
        new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");
    Info info =
        new Info()
            .title("HRM API")
            .description("This API exposes endpoints for Devet Blog")
            .version("1.0")
            .contact(contact)
            .license(mitLicense);

    SecurityScheme securityScheme =
        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT");
    Components components = new Components().addSecuritySchemes("bearer-key", securityScheme);

    return new OpenAPI().components(components).info(info).servers(List.of(devServer, prodServer));
  }
}
