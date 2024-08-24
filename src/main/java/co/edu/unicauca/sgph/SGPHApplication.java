package co.edu.unicauca.sgph;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class SGPHApplication {
	public static void main(final String[] args) {
			SpringApplication.run(SGPHApplication.class, args);
	}
}
