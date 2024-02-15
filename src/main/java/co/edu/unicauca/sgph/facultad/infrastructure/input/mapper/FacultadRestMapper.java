package co.edu.unicauca.sgph.facultad.infrastructure.input.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import co.edu.unicauca.sgph.facultad.domain.model.Facultad;
import co.edu.unicauca.sgph.facultad.infrastructure.input.DTORequest.FacultadInDTO;
import co.edu.unicauca.sgph.facultad.infrastructure.input.DTOResponse.FacultadOutDTO;

@Mapper(componentModel = "spring")
public interface FacultadRestMapper {

	FacultadOutDTO toFacultadOutDTO(Facultad facultad);

	Facultad toFacultad(FacultadInDTO facultadInDTO);

	List<FacultadOutDTO> toLstFacultadOutDTO(List<Facultad> lstFacultad);
}