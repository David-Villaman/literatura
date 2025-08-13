package com.example.literatura;

import com.example.literatura.Principal.Principal;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraturaApplication {

	public static void main(String[] args) {
        SpringApplication.run(LiteraturaApplication.class, args);

        // Ejecutar la aplicación de consola
        Principal app = new Principal();
        app.muestraElMenu();
	}

}
