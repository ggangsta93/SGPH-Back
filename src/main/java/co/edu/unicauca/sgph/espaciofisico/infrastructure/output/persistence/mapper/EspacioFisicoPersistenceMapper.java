package co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EspacioFisicoPersistenceMapper {
	
    @Bean(name="espacioFisicoMapper")
    ModelMapper crearEspacioFisicoMapper() {
        return new ModelMapper();
    }    
}