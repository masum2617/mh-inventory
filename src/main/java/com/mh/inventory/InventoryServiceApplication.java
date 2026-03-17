package com.mh.inventory;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
//@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableWebSecurity
@EnableFeignClients
@OpenAPIDefinition(
		info = @Info(
				title = "Inventory System",
				description = "REST API Documentation",
				version = "v1.0",
				contact = @Contact(
						name = "Masum Hasan",
						email = "masum2617@gmail.com",
						url = "https://www.linkedin.com/in/masum-hasan/"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.linkedin.com/in/masum-hasan/"
				)
		)
//		externalDocs = @ExternalDocumentation(
//				description =  "EazyBank Accounts microservice REST API Documentation",
//				url = "https://www.eazybytes.com/swagger-ui.html"
//		)
)
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

}
