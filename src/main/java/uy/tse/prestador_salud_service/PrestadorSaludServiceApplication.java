package uy.tse.prestador_salud_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class PrestadorSaludServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrestadorSaludServiceApplication.class, args);
	}

}
