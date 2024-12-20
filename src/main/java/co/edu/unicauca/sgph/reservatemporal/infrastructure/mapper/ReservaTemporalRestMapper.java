package co.edu.unicauca.sgph.reservatemporal.infrastructure.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import co.edu.unicauca.sgph.reservatemporal.domain.model.ReservaTemporal;
import co.edu.unicauca.sgph.reservatemporal.infrastructure.input.DTORequest.ReservaTemporalInDTO;
import co.edu.unicauca.sgph.reservatemporal.infrastructure.input.DTOResponse.ReservaTemporalOutDTO;

@Mapper(componentModel = "spring")
public interface ReservaTemporalRestMapper {

	ReservaTemporalOutDTO toReservaTemporalOutDTO(ReservaTemporal reservaTemporal);

    ReservaTemporal toReservaTemporal(ReservaTemporalInDTO inDTO);

    List<ReservaTemporalOutDTO> toLstReservaTemporalOutDTO(List<ReservaTemporal> lstReservaTemporal);
	
}
