package co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AulaPersistenceMapper {
	
    @Bean(name="aulaMapper")
    ModelMapper crearAulaMapper() {
        return new ModelMapper();
    }    
}