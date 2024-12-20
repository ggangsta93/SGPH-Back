package co.edu.unicauca.sgph.reservatemporal.domain.useCase;

import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.unicauca.sgph.reservatemporal.application.input.GestionarReservaTemporalCUIntPort;
import co.edu.unicauca.sgph.reservatemporal.application.output.GestionarReservaTemporalGatewayIntPort;
import co.edu.unicauca.sgph.reservatemporal.domain.model.ReservaTemporal;

@Service
public class GestionarReservaTemporalCUAdapter implements GestionarReservaTemporalCUIntPort{

	private final GestionarReservaTemporalGatewayIntPort gestionarReservaTemporalGatewayIntPort;

    public GestionarReservaTemporalCUAdapter(GestionarReservaTemporalGatewayIntPort gestionarReservaTemporalGatewayIntPort) {
        this.gestionarReservaTemporalGatewayIntPort = gestionarReservaTemporalGatewayIntPort;
    }
    
	@Override
	public ReservaTemporal guardarReserva(ReservaTemporal reservaTemporal) {
		return this.gestionarReservaTemporalGatewayIntPort.guardarReserva(reservaTemporal);
	}

	@Override
	public List<ReservaTemporal> consultarReservas() {
		return this.gestionarReservaTemporalGatewayIntPort.consultarReservas();
	}

	@Override
	public ReservaTemporal consultarReservaPorId(Long id) {
		return this.gestionarReservaTemporalGatewayIntPort.consultarReservaPorId(id);
	}

}
