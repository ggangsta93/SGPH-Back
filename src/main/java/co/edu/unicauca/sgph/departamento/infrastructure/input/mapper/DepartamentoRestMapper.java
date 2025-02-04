package co.edu.unicauca.sgph.departamento.infrastructure.input.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.edu.unicauca.sgph.departamento.domain.model.Departamento;
import co.edu.unicauca.sgph.departamento.infrastructure.input.DTORequest.DepartamentoInDTO;
import co.edu.unicauca.sgph.departamento.infrastructure.input.DTOResponse.DepartamentoOutDTO;

@Mapper(componentModel = "spring")
public interface DepartamentoRestMapper {

	@Mapping(target = "idFacultad", source = "departamento.facultad.idFacultad")
	DepartamentoOutDTO toDepartamentoOutDTO(Departamento departamento);

	@Mapping(target = "facultad", expression = "java(new Facultad(departamentoInDTO.getIdFacultad()))")
	Departamento toDepartamento(DepartamentoInDTO departamentoInDTO);

	List<DepartamentoOutDTO> toLstDepartamentoOutDTO(List<Departamento> lstDepartamento);
		
}