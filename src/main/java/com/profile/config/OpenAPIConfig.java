package com.profile.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;

@Configuration
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
        tags = {
                @Tag(name = "Profile", description = "Operations related to user profiles"),
                @Tag(name = "Authentication", description = "Authentication related operations")
        },
        security = @SecurityRequirement(name = "BearerAuth"),
        servers = {
                @Server(url = "localhost:8080/v1", description = "Production server")
        }
)
public class OpenAPIConfig {
}