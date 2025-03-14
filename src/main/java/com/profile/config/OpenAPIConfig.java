package com.profile.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "PassThrough",
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
)
@OpenAPIDefinition(
        info = @Info(
                title = "Profile API",
                description = "API for managing user profiles",
                version = "1.0.0",
                contact = @Contact(
                        name = "Ariel Wilkomm",
                        email = "arielwilkomm@gmail.com",
                        url = "https://www.linkedin.com/in/ariel-wilkomm-6398b0161/"
                )
        ),
        security = @SecurityRequirement(name = "PassThrough"),
        servers = {
                @Server(url = "localhost:8080/v1", description = "Production server")
        }
)
public class OpenAPIConfig {
}