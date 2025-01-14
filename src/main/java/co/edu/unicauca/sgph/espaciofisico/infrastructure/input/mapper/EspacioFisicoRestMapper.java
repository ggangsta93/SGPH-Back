package co.edu.unicauca.sgph.espaciofisico.infrastructure.input.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.edu.unicauca.sgph.agrupador.domain.model.AgrupadorEspacioFisico;
import co.edu.unicauca.sgph.agrupador.infrastructure.input.mapper.AgrupadorEspacioFisicoRestMapper;
import co.edu.unicauca.sgph.espaciofisico.domain.model.EspacioFisico;
import co.edu.unicauca.sgph.espaciofisico.domain.model.RecursoEspacioFisico;
import co.edu.unicauca.sgph.espaciofisico.domain.model.TipoEspacioFisico;
import co.edu.unicauca.sgph.espaciofisico.domain.model.Ubicacion;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTORequest.EspacioFisicoInDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTORequest.RecursoInDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.EspacioFisicoOutDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.RecursoOutDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.TipoEspacioFisicoOutDTO;
import org.mapstruct.Mappings;


@Mapper(componentModel = "spring", uses = { AgrupadorEspacioFisicoRestMapper.class, RecursoMapper.class })
public interface EspacioFisicoRestMapper {

	@Mappings({
			@Mapping(target = "idTipoEspacioFisico", expression = "java(espacioFisico.getTipoEspacioFisico() != null ?  espacioFisico.getTipoEspacioFisico().getIdTipoEspacioFisico(): null)"),
			@Mapping(target = "nombreTipoEspacioFisico", expression = "java(espacioFisico.getTipoEspacioFisico() != null ?  espacioFisico.getTipoEspacioFisico().getTipo(): null)"),
			@Mapping(target = "idEdificio", expression = "java(espacioFisico.getEdificio() != null ? espacioFisico.getEdificio().getIdEdificio() : null)"),
			@Mapping(target = "nombreEdificio", expression = "java(espacioFisico.getEdificio() != null ? espacioFisico.getEdificio().getNombre() : null)"),
			@Mapping(target = "idUbicacion", expression = "java(espacioFisico.getUbicacion() != null ? espacioFisico.getUbicacion().getIdUbicacion() : null)"),
		    @Mapping(target = "nombreUbicacion", expression = "java(espacioFisico.getUbicacion() != null ? espacioFisico.getUbicacion().getNombre() : null)"),
		    @Mapping(
		    	    target = "recursos",
		    	    expression = "java(mapEntidadToOutDto(espacioFisico.getRecursosEspacioFisico()))"
		    	),
			@Mapping(target = "lstIdAgrupadorEspacioFisico", source = "agrupadores"),
			@Mapping(target = "OID", source = "OID")
	})
	EspacioFisicoOutDTO toEspacioFisicoOutDTO(EspacioFisico espacioFisico);

	@Mapping(target = "horarios", ignore = true)
	@Mapping(
		    target = "recursosEspacioFisico",
		    expression = "java(mapDtoToEntidad(espacioFisicoInDTO.getRecursos()))"
		)
	@Mapping(target = "tipoEspacioFisico", expression = "java(new TipoEspacioFisico(espacioFisicoInDTO.getIdTipoEspacioFisico()))")
	//@Mapping(target = "edificio", expression = "java(espacioFisicoInDTO.getIdEdificio() != null ? new Edificio(espacioFisicoInDTO.getIdEdificio()) : null)")
	@Mapping(target = "ubicacion", expression = "java(mapUbicacion(espacioFisicoInDTO.getIdUbicacion()))")
	@Mapping(target = "agrupadores", source = "espacioFisicoInDTO.lstIdAgrupadorEspacioFisico")
	EspacioFisico toEspacioFisico(EspacioFisicoInDTO espacioFisicoInDTO);

	List<EspacioFisicoOutDTO> toLstEspacioFisicoOutDTO(List<EspacioFisico> lstEspacioFisico);

	List<TipoEspacioFisicoOutDTO> toLstTipoEspacioFisicoOutDTO(List<TipoEspacioFisico> lstTipoEspacioFisico);

	default List<AgrupadorEspacioFisico> toAgrupadorEspacioFisico(List<Long> lstIdAgrupadorEspacioFisico) {
	    // Verificar si la lista es null, si es así, retornar una lista vacía
	    if (lstIdAgrupadorEspacioFisico == null) {
	        return new ArrayList<>();
	    }
	    
	    List<AgrupadorEspacioFisico> agrupadores = new ArrayList<>();
	    for (Long idAgrupadorEspacioFisico : lstIdAgrupadorEspacioFisico) {
	        AgrupadorEspacioFisico agrupadorEspacioFisico = new AgrupadorEspacioFisico();
	        agrupadorEspacioFisico.setIdAgrupadorEspacioFisico(idAgrupadorEspacioFisico);
	        agrupadores.add(agrupadorEspacioFisico);
	    }
	    return agrupadores;
	}


	default List<Long> toLstIdAgrupadorEspacioFisico(List<AgrupadorEspacioFisico> agrupadores) {
		List<Long> lstIdAgrupadorEspacioFisico = new ArrayList<>();
		for (AgrupadorEspacioFisico agrupadorEspacioFisico : agrupadores) {
			lstIdAgrupadorEspacioFisico.add(agrupadorEspacioFisico.getIdAgrupadorEspacioFisico());
		}
		return lstIdAgrupadorEspacioFisico;
	}
	

    // ---------------------------------------------------------
    //   Métodos auxiliares para Recursos
    //   (1) ENTIDAD → DTO de salida
    //   (2) DTO de entrada → ENTIDAD
    // ---------------------------------------------------------
    /**
     * (1) Convierte una lista de RecursoEspacioFisico (Entidad)
     *     a una lista de RecursoOutDTO para la salida.
     */
    default List<RecursoOutDTO> mapEntidadToOutDto(List<RecursoEspacioFisico> recursos) {
        if (recursos == null) {
            return null;
        }
        return recursos.stream().map(recurso -> {
            RecursoOutDTO dto = new RecursoOutDTO();
            dto.setIdRecursoEspacioFisico(recurso.getIdRecursoEspacioFisico());

            // Verifica si recurso.getRecursoFisico() no es null
            if (recurso.getRecursoFisico() != null) {
                dto.setIdRecurso(recurso.getRecursoFisico().getIdRecursoFisico());
                dto.setNombre(recurso.getRecursoFisico().getNombre());
                // dto.setDescripcion(...) si aplica
            }
            return dto;
        }).collect(Collectors.toList());
    }

    default List<RecursoEspacioFisico> mapDtoToEntidad(List<Long> idsDeRecursos) {
        if (idsDeRecursos == null) {
            return null;
        }
        List<RecursoEspacioFisico> recursos = new ArrayList<>();
        for (Long id : idsDeRecursos) {
            RecursoEspacioFisico entity = new RecursoEspacioFisico();
            entity.setIdRecursoEspacioFisico(id);
            // si RecursoEspacioFisico requiere más lógica, aquí la manejas
            recursos.add(entity);
        }
        return recursos;
    }


    // ---------------------------------------------------------
    //   Método auxiliar para Ubicacion
    // ---------------------------------------------------------
    default Ubicacion mapUbicacion(Long idUbicacion) {
        if (idUbicacion == null) {
            return null;
        }
        return new Ubicacion(idUbicacion);
    }


}