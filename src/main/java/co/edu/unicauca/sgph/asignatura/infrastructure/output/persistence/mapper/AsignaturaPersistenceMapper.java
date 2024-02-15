package co.edu.unicauca.sgph.asignatura.infrastructure.output.persistence.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AsignaturaPersistenceMapper {
	
    @Bean(name="asignaturaMapper")
    @Primary
    ModelMapper crearAsignaturaMapper() {
        return new ModelMapper();
    }    
}