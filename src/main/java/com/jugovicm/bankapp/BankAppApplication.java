package com.jugovicm.bankapp;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
	info = @Info(
			title = "Banking App",
			description = "Backend REST APIs",
			version = "v1.0",
			contact = @Contact(
				name = "Milica Jugovic",
				email = "jugmic@yahoo.com",
				url = "https://github.com/jugovicm/bank-app-advanced"
			),
			license = @License(
				name = "Banking App",
				url = "https://github.com/jugovicm/bank-app-advanced"
			)
   ),
	externalDocs = @ExternalDocumentation(
		description = "Banking App Documentation",
		url = "https://github.com/jugovicm/bank-app-advanced"
	)
)
public class BankAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankAppApplication.class, args);
	}

}
