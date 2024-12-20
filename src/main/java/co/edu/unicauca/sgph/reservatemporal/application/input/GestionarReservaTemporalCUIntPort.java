package co.edu.unicauca.sgph.reservatemporal.application.input;

import java.util.List;

import co.edu.unicauca.sgph.reservatemporal.domain.model.ReservaTemporal;

public interface GestionarReservaTemporalCUIntPort {
	ReservaTemporal guardarReserva(ReservaTemporal reservaTemporal);

    List<ReservaTemporal> consultarReservas();

    ReservaTemporal consultarReservaPorId(Long id);
}
