package co.edu.unicauca.sgph.reservatemporal.domain.useCase;

import java.util.List;

import org.springframework.data.domain.Page;
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
	public List<ReservaTemporal> consultarReservas() {
		return this.gestionarReservaTemporalGatewayIntPort.consultarReservas();
	}

	@Override
	public ReservaTemporal consultarReservaPorId(Long id) {
		return this.gestionarReservaTemporalGatewayIntPort.consultarReservaPorId(id);
	}

	@Override
	public Page<FranjaLibreOutDTO> consultarFranjasLibres(FiltroFranjaHorariaDisponibleCursoDTO filtro) {
		return this.gestionarReservaTemporalGatewayIntPort.consultarFranjasLibres(filtro);
	}

}
