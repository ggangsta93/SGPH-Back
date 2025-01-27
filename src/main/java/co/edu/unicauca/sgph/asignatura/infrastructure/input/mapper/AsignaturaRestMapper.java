package co.edu.unicauca.sgph.asignatura.infrastructure.input.mapper;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.edu.unicauca.sgph.agrupador.domain.model.AgrupadorEspacioFisico;
import co.edu.unicauca.sgph.asignatura.domain.model.Asignatura;
import co.edu.unicauca.sgph.asignatura.infrastructure.input.DTORequest.AsignaturaInDTO;
import co.edu.unicauca.sgph.asignatura.infrastructure.input.DTOResponse.AsignaturaOutDTO;

import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface AsignaturaRestMapper {

	@Mapping(target = "idPrograma", source = "asignatura.programa.idPrograma")
	@Mapping(target = "lstIdAgrupadorEspacioFisico", source = "asignatura.agrupadores", qualifiedByName = "toLstIdAgrupadorEspacioFisico")
	@Mapping(target = "nombrePrograma", source = "asignatura.programa.nombre")
    @Mapping(target = "nombreFacultad", source = "asignatura.programa.facultad.nombre")
    @Mapping(target = "idFacultad", source = "asignatura.programa.facultad.idFacultad")
	AsignaturaOutDTO toAsignaturaOutDTO(Asignatura asignatura);

	// Mapear de AsignaturaInDTO a Asignatura (para guardar/actualizar)
    @Mapping(target = "idAsignatura", source = "idAsignatura") // No se modifica el ID
    @Mapping(target = "programa", expression = "java(new Programa(asignaturaInDTO.getIdPrograma()))")
    @Mapping(target = "agrupadores", source = "asignaturaInDTO.lstIdAgrupadorEspacioFisico", qualifiedByName = "toAgrupadorEspacioFisico")
    @Mapping(target = "nombre", source = "nombre")
    @Mapping(target = "codigoAsignatura", source = "codigoAsignatura")
    @Mapping(target = "OID", source = "OID")
    @Mapping(target = "semestre", source = "semestre")
    @Mapping(target = "pensum", source = "pensum")
    @Mapping(target = "horasSemana", source = "horasSemana")
    @Mapping(target = "aplicaEspacioSecundario", source = "aplicaEspacioSecundario")
	Asignatura toAsignatura(AsignaturaInDTO asignaturaInDTO);

	@Named("handleNullList")
	static List<Long> handleNullList(List<Long> lstIdAgrupadorEspacioFisico) {
		return lstIdAgrupadorEspacioFisico != null ? lstIdAgrupadorEspacioFisico : new ArrayList<>();
	}
	List<AsignaturaOutDTO> toLstAsignaturaOutDTO(List<Asignatura> lstAsignatura);

	@Named("toAgrupadorEspacioFisico")
	default List<AgrupadorEspacioFisico> toAgrupadorEspacioFisico(List<Long> lstIdAgrupadorEspacioFisico) {
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

	@Named("toLstIdAgrupadorEspacioFisico")
	default List<Long> toLstIdAgrupadorEspacioFisico(List<AgrupadorEspacioFisico> agrupadores) {
		if (agrupadores == null) {
            return new ArrayList<>();
        }
		List<Long> lstIdAgrupadorEspacioFisico = new ArrayList<>();
		for (AgrupadorEspacioFisico agrupadorEspacioFisico : agrupadores) {
			lstIdAgrupadorEspacioFisico.add(agrupadorEspacioFisico.getIdAgrupadorEspacioFisico());
		}
		return lstIdAgrupadorEspacioFisico;
	}
}