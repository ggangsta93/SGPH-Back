package co.edu.unicauca.sgph.reservatemporal.infrastructure.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import co.edu.unicauca.sgph.reservatemporal.domain.model.ReservaTemporal;
import co.edu.unicauca.sgph.reservatemporal.infrastructure.input.DTORequest.ReservaTemporalInDTO;
import co.edu.unicauca.sgph.reservatemporal.infrastructure.input.DTOResponse.ReservaTemporalOutDTO;

@Mapper(componentModel = "spring")
public interface ReservaTemporalRestMapper {

	ReservaTemporalOutDTO toReservaTemporalOutDTO(ReservaTemporal reservaTemporal);

    ReservaTemporal toReservaTemporal(ReservaTemporalInDTO inDTO);

    ReservaTemporalInDTO toReservaTemporalInDTO(ReservaTemporal reservaTemporal);
    
    List<ReservaTemporalOutDTO> toLstReservaTemporalOutDTO(List<ReservaTemporal> lstReservaTemporal);
	
    default Page<ReservaTemporalOutDTO> toPageReservaTemporalOutDTO(Page<ReservaTemporal> page) {
        return page.map(this::toReservaTemporalOutDTO);
    }
}
