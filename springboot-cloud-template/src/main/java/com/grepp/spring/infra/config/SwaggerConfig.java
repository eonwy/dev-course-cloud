package com.grepp.spring.infra.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    public OpenAPI openApiSpec() {
        return new OpenAPI()
            .info(
                new Info()
                    .title("Spring Boot Infrastructure API")
                    .description("please reference this [link] for ErrorCode")
                    .version("v1.0.0"))
            .components(new Components()
                .addSecuritySchemes("bearer-auth",
                    new SecurityScheme()
                        .name("bearer-auth")
                        .type(Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .description("insert JWT. except Bearer")))
            .addSecurityItem(new SecurityRequirement().addList("bearer-auth"));
    }
}
