package co.edu.unicauca.sgph;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


//@EnableConfigurationProperties
@EnableAsync
@SpringBootApplication
//@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class SGPHApplication {
	public static void main(final String[] args) {
			SpringApplication.run(SGPHApplication.class, args);
		}
}
