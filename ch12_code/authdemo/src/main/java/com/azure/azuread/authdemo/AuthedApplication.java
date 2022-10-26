package com.azure.azuread.authdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;

@SpringBootApplication
@RestController
public class AuthedApplication {

	public static void main(String[] args) {
		System.out.println("Hello");
		SpringApplication.run(AuthedApplication.class, args);
	}

	@GetMapping("SuperUser")
	@ResponseBody
	@PreAuthorize("hasAuthority('APPROLE_superuser')")
	public String SuperUser() {
		return "This is a Super User !! You have all kinds of Access in the WORLD !!";
	}

}
