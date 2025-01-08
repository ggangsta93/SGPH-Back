package co.edu.unicauca.sgph.espaciofisico.infrastructure.input.mapper;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.edu.unicauca.sgph.agrupador.domain.model.AgrupadorEspacioFisico;
import co.edu.unicauca.sgph.agrupador.infrastructure.input.mapper.AgrupadorEspacioFisicoRestMapper;
import co.edu.unicauca.sgph.espaciofisico.domain.model.EspacioFisico;
import co.edu.unicauca.sgph.espaciofisico.domain.model.TipoEspacioFisico;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTORequest.EspacioFisicoInDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.EspacioFisicoOutDTO;
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
		    @Mapping(target = "recursos", expression = "java(espacioFisico.getRecursosEspacioFisico() != null ? espacioFisico.getRecursosEspacioFisico().stream().map(recurso -> { RecursoOutDTO recursoOutDTO = new RecursoOutDTO(); recursoOutDTO.setIdRecurso(recurso.getIdRecurso()); recursoOutDTO.setNombre(recurso.getNombre()); return recursoOutDTO; }).collect(Collectors.toList()) : null)"),
			@Mapping(target = "lstIdAgrupadorEspacioFisico", source = "agrupadores"),
			@Mapping(target = "OID", source = "OID")
	})
	EspacioFisicoOutDTO toEspacioFisicoOutDTO(EspacioFisico espacioFisico);

	@Mapping(target = "horarios", ignore = true)
	@Mapping(target = "recursosEspacioFisico", ignore = true)
	@Mapping(target = "tipoEspacioFisico", expression = "java(new TipoEspacioFisico(espacioFisicoInDTO.getIdTipoEspacioFisico()))")
	//@Mapping(target = "edificio", expression = "java(espacioFisicoInDTO.getIdEdificio() != null ? new Edificio(espacioFisicoInDTO.getIdEdificio()) : null)")
	@Mapping(target = "ubicacion", expression = "java(espacioFisicoInDTO.getIdUbicacion() != null ? new Ubicacion(espacioFisicoInDTO.getIdUbicacion()) : null)")
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
}