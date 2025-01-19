package co.edu.unicauca.sgph.reservatemporal.domain.useCase;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import co.edu.unicauca.sgph.horario.infrastructure.input.DTORequest.FiltroFranjaHorariaDisponibleCursoDTO;
import co.edu.unicauca.sgph.horario.infrastructure.input.DTOResponse.FranjaLibreOutDTO;
import co.edu.unicauca.sgph.reservatemporal.application.input.GestionarReservaTemporalCUIntPort;
import co.edu.unicauca.sgph.reservatemporal.application.output.GestionarReservaTemporalGatewayIntPort;
import co.edu.unicauca.sgph.reservatemporal.domain.model.ReservaTemporal;
import co.edu.unicauca.sgph.reservatemporal.infrastructure.input.DTORequest.ReservaTemporalInDTO;
import co.edu.unicauca.sgph.reservatemporal.infrastructure.input.DTOResponse.ReservaTemporalOutDTO;
import co.edu.unicauca.sgph.reservatemporal.infrastructure.mapper.ReservaTemporalRestMapper;

@Service
public class GestionarReservaTemporalCUAdapter implements GestionarReservaTemporalCUIntPort{

	private final GestionarReservaTemporalGatewayIntPort gestionarReservaTemporalGatewayIntPort;
	private final ReservaTemporalRestMapper mapper;

    public GestionarReservaTemporalCUAdapter(GestionarReservaTemporalGatewayIntPort gestionarReservaTemporalGatewayIntPort,
    		ReservaTemporalRestMapper mapper) {
        this.gestionarReservaTemporalGatewayIntPort = gestionarReservaTemporalGatewayIntPort;
        this.mapper = mapper;
    }
    
	@Override
	public ReservaTemporalOutDTO guardarReserva(ReservaTemporalInDTO inDTO) {
		return this.gestionarReservaTemporalGatewayIntPort.guardarReserva(inDTO);
	}

	@Override
	public ReservaTemporal consultarReservaPorId(Long id) {
		return this.gestionarReservaTemporalGatewayIntPort.consultarReservaPorId(id);
	}

	@Override
	public Page<FranjaLibreOutDTO> consultarFranjasLibres(FiltroFranjaHorariaDisponibleCursoDTO filtro) {
		return this.gestionarReservaTemporalGatewayIntPort.consultarFranjasLibres(filtro);
	}

	@Override
	public Page<ReservaTemporalInDTO> consultarReservas(String tipoIdentificacion, String identificacion,
			String estadoReserva, Pageable pageable) {
		Page<ReservaTemporal> reservas = gestionarReservaTemporalGatewayIntPort.consultarReservas(
		        tipoIdentificacion, identificacion, estadoReserva, pageable);

		return reservas.map(r -> {
	        ReservaTemporalInDTO dto = mapper.toReservaTemporalInDTO(r);	        
	        dto.setIdentificacion(r.getNumeroIdentificacion());	        
	        return dto;
	    });
	}

	@Override
	public ReservaTemporalInDTO aprobarReserva(Long reservaId, String motivo) {
		ReservaTemporal reserva = gestionarReservaTemporalGatewayIntPort.aprobarReserva(reservaId, motivo);
		ReservaTemporalInDTO dto = mapper.toReservaTemporalInDTO(reserva);
		dto.setIdentificacion(reserva.getNumeroIdentificacion());	    
	    return dto;
	}

	@Override
	public ReservaTemporalInDTO rechazarReserva(Long reservaId, String motivo) {
		ReservaTemporal reserva = gestionarReservaTemporalGatewayIntPort.rechazarReserva(reservaId, motivo);
		ReservaTemporalInDTO dto = mapper.toReservaTemporalInDTO(reserva);
		dto.setIdentificacion(reserva.getNumeroIdentificacion());	    
	    return dto;
	}

	@Scheduled(cron = "0 0/5 * * * ?") // Cada 5 minutos
	public void finalizarReservasVencidasProgramadas() {
		gestionarReservaTemporalGatewayIntPort.finalizarReservasVencidasProgramadas();
    }

	@Override
	public byte[] generarExcelHistorialReservasPorPeriodo(Long idPeriodo) {
		return gestionarReservaTemporalGatewayIntPort.generarExcelHistorialReservasPorPeriodo(idPeriodo);
	}

	@Override
	public ReservaTemporal cancelarReserva(Long reservaId, String motivo) {
		return gestionarReservaTemporalGatewayIntPort.cancelarReserva(reservaId, motivo);
	}
	
}
