package co.edu.unicauca.sgph.reservatemporal.application.input;

import java.util.List;

import org.springframework.data.domain.Page;

import co.edu.unicauca.sgph.horario.infrastructure.input.DTORequest.FiltroFranjaHorariaDisponibleCursoDTO;
import co.edu.unicauca.sgph.horario.infrastructure.input.DTOResponse.FranjaLibreOutDTO;
import co.edu.unicauca.sgph.reservatemporal.domain.model.ReservaTemporal;
import co.edu.unicauca.sgph.reservatemporal.infrastructure.input.DTORequest.ReservaTemporalInDTO;
import co.edu.unicauca.sgph.reservatemporal.infrastructure.input.DTOResponse.ReservaTemporalOutDTO;

public interface GestionarReservaTemporalCUIntPort {
	ReservaTemporalOutDTO guardarReserva(ReservaTemporalInDTO inDTO);

    List<ReservaTemporal> consultarReservas();

    ReservaTemporal consultarReservaPorId(Long id);
    
    public Page<FranjaLibreOutDTO> consultarFranjasLibres(FiltroFranjaHorariaDisponibleCursoDTO filtro);
}
