package co.edu.unicauca.sgph.reservatemporal.infrastructure.output.persistence.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.edu.unicauca.sgph.reservatemporal.domain.model.ReservaTemporal;
import co.edu.unicauca.sgph.reservatemporal.infrastructure.input.DTORequest.ReservaTemporalInDTO;
import co.edu.unicauca.sgph.reservatemporal.infrastructure.output.persistence.entity.ReservaTemporalEntity;

@Configuration
public class ReservaTemporalPersistenceMapper {
	
	@Bean
    public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();

	    modelMapper.getConfiguration()
	               // Evita la coincidencia automática de nombres de fields
	               .setFieldMatchingEnabled(false)
	               // Ignora ambigüedades de mapeo
	               .setAmbiguityIgnored(true)
	               // Si no quieres mapear valores nulos
	               .setSkipNullEnabled(true);

	    // Mapeo explícito de la ENTIDAD -> DTO
	    modelMapper.typeMap(ReservaTemporalEntity.class, ReservaTemporalInDTO.class)
	        // Indica que el campo 'numeroIdentificacion' de la entidad va al 'identificacion' del DTO
	        .addMapping(ReservaTemporalEntity::getNumeroIdentificacion,
	                    ReservaTemporalInDTO::setIdentificacion)

	        // Indica que 'tipoIdentificacion' de la entidad va al 'tipoIdentificacion' del DTO
	        .addMapping(ReservaTemporalEntity::getTipoIdentificacion,
	                    ReservaTemporalInDTO::setTipoIdentificacion)

	        // Mapea el objeto estado al String 'estado' del DTO
	        .addMapping(entity -> entity.getEstado() != null ? entity.getEstado().getDescripcion() : null,
	                    ReservaTemporalInDTO::setEstado);

	    return modelMapper;
    }
}
