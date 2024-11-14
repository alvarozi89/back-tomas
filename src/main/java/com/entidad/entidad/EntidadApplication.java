package com.entidad.entidad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.entidad.entidad.repository")
public class EntidadApplication {

	public static void main(String[] args) {
		SpringApplication.run(EntidadApplication.class, args);
	}

}
