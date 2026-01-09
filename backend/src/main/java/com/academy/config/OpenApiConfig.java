package com.academy.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI academyManagerOpenAPI() {
    Server devServer = new Server();
    devServer.setUrl("http://localhost:8080");
    devServer.setDescription("Development server");

    Contact contact = new Contact();
    contact.setEmail("contact@academy.com");
    contact.setName("Academy Manager API Support");

    License license = new License().name("MIT License").url("https://opensource.org/licenses/MIT");

    Info info =
        new Info()
            .title("Academy Manager API")
            .version("1.0.0")
            .contact(contact)
            .description(
                "REST API for managing academies, coaches, students, training sessions, and more.")
            .license(license);

    return new OpenAPI().info(info).servers(List.of(devServer));
  }
}

