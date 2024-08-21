package net.yixingong.dining.reviews;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Spring Boot Dining Reviews App REST APIs",
                description = "Spring Boot Dining Reviews App REST APIs Documentation",
                version = "v1.0",
                contact = @Contact(
                        name = "Yixin Gong",
                        email = "adrianna.yixin@gmail.com",
                        url = "https://www.linkedin.com/in/yixin-gong/"
                )
        ), externalDocs = @ExternalDocumentation(
        description = "Spring Boot Dining Reviews App Documentation"
))
public class DiningReviewsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiningReviewsApplication.class, args);
    }
}