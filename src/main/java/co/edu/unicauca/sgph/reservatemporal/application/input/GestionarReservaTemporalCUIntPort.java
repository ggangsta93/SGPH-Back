package co.edu.unicauca.sgph.reservatemporal.application.input;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import co.edu.unicauca.sgph.horario.infrastructure.input.DTORequest.FiltroFranjaHorariaDisponibleCursoDTO;
import co.edu.unicauca.sgph.horario.infrastructure.input.DTOResponse.FranjaLibreOutDTO;
import co.edu.unicauca.sgph.reservatemporal.domain.model.ReservaTemporal;
import co.edu.unicauca.sgph.reservatemporal.infrastructure.input.DTORequest.ReservaTemporalInDTO;
import co.edu.unicauca.sgph.reservatemporal.infrastructure.input.DTOResponse.ReservaTemporalOutDTO;

public interface GestionarReservaTemporalCUIntPort {
	ReservaTemporalOutDTO guardarReserva(ReservaTemporalInDTO inDTO);

	Page<ReservaTemporalInDTO> consultarReservas(String tipoIdentificacion, String identificacion, String estadoReserva, Pageable pageable);

    ReservaTemporal consultarReservaPorId(Long id);
    
    public Page<FranjaLibreOutDTO> consultarFranjasLibres(FiltroFranjaHorariaDisponibleCursoDTO filtro);
    
    ReservaTemporalInDTO aprobarReserva(Long reservaId, String motivo);
    
    ReservaTemporalInDTO rechazarReserva(Long reservaId, String motivo);
    
    void finalizarReservasVencidasProgramadas();
    
    byte[] generarExcelHistorialReservasPorPeriodo(Long idPeriodo);
    
 // Cancelar una reserva (Nuevo método)
    ReservaTemporal cancelarReserva(Long reservaId, String motivo);

}
