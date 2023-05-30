package com.alura.foro;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Libreria APIS",version = "1.0",description = "Manejo de Librerias APIS"))
public class ForoInicialApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForoInicialApplication.class, args);
	}

}
