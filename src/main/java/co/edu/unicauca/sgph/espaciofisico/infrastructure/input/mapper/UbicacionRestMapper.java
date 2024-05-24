package co.edu.unicauca.sgph.espaciofisico.infrastructure.input.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.edu.unicauca.sgph.espaciofisico.domain.model.Ubicacion;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.UbicacionOutDTO;

@Mapper(componentModel = "spring")
public interface UbicacionRestMapper {

	@Mapping(target = "idMunicipio", expression = "java(ubicacion.getMunicipio().getIdMunicipio())")
	UbicacionOutDTO toUbicacionOutDTO(Ubicacion ubicacion);

	List<UbicacionOutDTO> toLstUbicacionOutDTO(List<Ubicacion> lstUbicacion);
}