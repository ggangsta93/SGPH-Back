package co.edu.unicauca.sgph.asignatura.infrastructure.input.mapper;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.edu.unicauca.sgph.asignatura.domain.model.Asignatura;
import co.edu.unicauca.sgph.asignatura.infrastructure.input.DTORequest.AsignaturaInDTO;
import co.edu.unicauca.sgph.asignatura.infrastructure.input.DTOResponse.AsignaturaOutDTO;
import co.edu.unicauca.sgph.espaciofisico.domain.model.TipoEspacioFisico;

@Mapper(componentModel = "spring")
public interface AsignaturaRestMapper {

	@Mapping(target = "idPrograma", expression = "java(asignatura.getPrograma().getIdPrograma())")
	@Mapping(target = "lstIdTipoEspacioFisico", source = "asignatura.tiposEspacioFisico")
	AsignaturaOutDTO toAsignaturaOutDTO(Asignatura asignatura);

	@Mapping(target = "programa", expression = "java(new Programa(asignaturaInDTO.getIdPrograma()))")
	@Mapping(target = "tiposEspacioFisico", source = "asignaturaInDTO.lstIdTipoEspacioFisico")
	Asignatura toAsignatura(AsignaturaInDTO asignaturaInDTO);

	List<AsignaturaOutDTO> toLstAsignaturaOutDTO(List<Asignatura> lstAsignatura);

	default List<TipoEspacioFisico> toTipoEspacioFisico(List<Long> lstTipoEspacioFisico) {
		List<TipoEspacioFisico> tiposEspaciosFisicos = new ArrayList<>();
		for (Long idTipoEspacioFisico : lstTipoEspacioFisico) {
			TipoEspacioFisico tipoEspacioFisico = new TipoEspacioFisico();
			tipoEspacioFisico.setIdTipoEspacioFisico(idTipoEspacioFisico);
			tiposEspaciosFisicos.add(tipoEspacioFisico);
		}
		return tiposEspaciosFisicos;
	}

	default List<Long> tolstIdTipoEspacioFisico(List<TipoEspacioFisico> tiposEspaciosFisicos) {
		List<Long> lstIdTipoEspacioFisico = new ArrayList<>();
		for (TipoEspacioFisico tipoEspacioFisico : tiposEspaciosFisicos) {
			lstIdTipoEspacioFisico.add(tipoEspacioFisico.getIdTipoEspacioFisico());
		}
		return lstIdTipoEspacioFisico;
	}
}