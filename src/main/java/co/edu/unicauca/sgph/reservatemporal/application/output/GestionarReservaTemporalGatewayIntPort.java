package co.edu.unicauca.sgph.reservatemporal.application.output;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import co.edu.unicauca.sgph.horario.infrastructure.input.DTORequest.FiltroFranjaHorariaDisponibleCursoDTO;
import co.edu.unicauca.sgph.horario.infrastructure.input.DTOResponse.FranjaLibreOutDTO;
import co.edu.unicauca.sgph.reservatemporal.domain.model.ReservaTemporal;
import co.edu.unicauca.sgph.reservatemporal.infrastructure.input.DTORequest.ReservaTemporalInDTO;
import co.edu.unicauca.sgph.reservatemporal.infrastructure.input.DTOResponse.ReservaTemporalOutDTO;

public interface GestionarReservaTemporalGatewayIntPort {
	public ReservaTemporalOutDTO guardarReserva(ReservaTemporalInDTO inDTO);

    List<ReservaTemporal> consultarReservas();

    ReservaTemporal consultarReservaPorId(Long id);
    
    public Page<FranjaLibreOutDTO> consultarFranjasLibres(FiltroFranjaHorariaDisponibleCursoDTO filtro);
    
    Page<ReservaTemporal> consultarReservas(String tipoIdentificacion, String identificacion, String estadoReserva, Pageable pageable);
    
    ReservaTemporal aprobarReserva(Long reservaId, String motivo);
    
    ReservaTemporal rechazarReserva(Long reservaId, String motivo);
    
    void finalizarReservasVencidasProgramadas();
}
