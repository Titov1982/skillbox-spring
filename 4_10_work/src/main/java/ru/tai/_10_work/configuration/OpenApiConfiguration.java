package ru.tai._10_work.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI openApiDescription() {
        Server localhostServer = new Server();
        localhostServer.setUrl("http://localhost:8080");
        localhostServer.setDescription("Local env");

        Contact contact = new Contact();
        contact.setName("Titov Andrey");
        contact.setEmail("titov@mail.ru");
        contact.setUrl("http://titov.ru");

        Info info = new Info()
                .title("Пользовательский API")
                .version("1.0")
                .contact(contact)
                .description("API для пользователя");

        return new OpenAPI().info(info).servers(List.of(localhostServer));
    }

}
