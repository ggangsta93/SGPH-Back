package co.edu.unicauca.sgph.asignatura.infrastructure.input.mapper;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.edu.unicauca.sgph.asignatura.domain.model.Asignatura;
import co.edu.unicauca.sgph.asignatura.infrastructure.input.DTORequest.AsignaturaOutDTO;
import co.edu.unicauca.sgph.asignatura.infrastructure.input.DTOResponse.AsignaturaInDTO;
import co.edu.unicauca.sgph.aula.domain.model.TipoAula;

@Mapper(componentModel = "spring")
public interface AsignaturaRestMapper {

	@Mapping(target = "idPrograma", expression = "java(asignatura.getPrograma().getIdPrograma())")
	@Mapping(target = "lstIdTipoAula", source = "asignatura.tiposAula")
	AsignaturaOutDTO toAsignaturaOutDTO(Asignatura asignatura);

	@Mapping(target = "programa", expression = "java(new Programa(asignaturaInDTO.getIdPrograma()))")
	@Mapping(target = "tiposAula", source = "asignaturaInDTO.lstIdTipoAula")
	Asignatura toAsignatura(AsignaturaInDTO asignaturaInDTO);

	List<AsignaturaOutDTO> toLstAsignaturaOutDTO(List<Asignatura> lstAsignatura);

	default List<TipoAula> toTipoAula(List<Long> lstTipoAula) {
		List<TipoAula> tiposAula = new ArrayList<>();
		for (Long idTipoAula : lstTipoAula) {
			TipoAula tipoAula = new TipoAula();
			tipoAula.setIdTipoAula(idTipoAula);
			tiposAula.add(tipoAula);
		}
		return tiposAula;
	}

	default List<Long> tolstIdTipoAula(List<TipoAula> tiposAula) {
		List<Long> lstIdTipoAula = new ArrayList<>();
		for (TipoAula tipoAula : tiposAula) {
			lstIdTipoAula.add(tipoAula.getIdTipoAula());
		}
		return lstIdTipoAula;
	}
}