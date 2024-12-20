package co.edu.unicauca.sgph.reservatemporal.application.output;

import java.util.List;

import co.edu.unicauca.sgph.reservatemporal.domain.model.ReservaTemporal;

public interface GestionarReservaTemporalGatewayIntPort {
	ReservaTemporal guardarReserva(ReservaTemporal reservaTemporal);

    List<ReservaTemporal> consultarReservas();

    ReservaTemporal consultarReservaPorId(Long id);
}
