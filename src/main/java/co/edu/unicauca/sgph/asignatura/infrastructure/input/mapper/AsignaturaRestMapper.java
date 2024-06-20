package co.edu.unicauca.sgph.asignatura.infrastructure.input.mapper;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.edu.unicauca.sgph.asignatura.domain.model.Asignatura;
import co.edu.unicauca.sgph.asignatura.infrastructure.input.DTORequest.AsignaturaInDTO;
import co.edu.unicauca.sgph.asignatura.infrastructure.input.DTOResponse.AsignaturaOutDTO;
import co.edu.unicauca.sgph.espaciofisico.domain.model.AgrupadorEspacioFisico;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface AsignaturaRestMapper {

	@Mapping(target = "idPrograma", source = "asignatura.programa.idPrograma")
	@Mapping(target = "lstIdAgrupadorEspacioFisico", source = "asignatura.agrupadores")
	AsignaturaOutDTO toAsignaturaOutDTO(Asignatura asignatura);

	@Mapping(target = "programa", expression = "java(new Programa(asignaturaInDTO.getIdPrograma()))")
	@Mapping(target = "agrupadores", source = " asignaturaInDTO.lstIdAgrupadorEspacioFisico", qualifiedByName = "handleNullList")
	Asignatura toAsignatura(AsignaturaInDTO asignaturaInDTO);

	@Named("handleNullList")
	static List<Long> handleNullList(List<Long> lstIdAgrupadorEspacioFisico) {
		return lstIdAgrupadorEspacioFisico != null ? lstIdAgrupadorEspacioFisico : new ArrayList<>();
	}
	List<AsignaturaOutDTO> toLstAsignaturaOutDTO(List<Asignatura> lstAsignatura);

	default List<AgrupadorEspacioFisico> toAgrupadorEspacioFisico(List<Long> lstIdAgrupadorEspacioFisico) {
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