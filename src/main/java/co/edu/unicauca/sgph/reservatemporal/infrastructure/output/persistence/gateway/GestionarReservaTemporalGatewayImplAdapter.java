package co.edu.unicauca.sgph.reservatemporal.infrastructure.output.persistence.gateway;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import co.edu.unicauca.sgph.reservatemporal.application.output.GestionarReservaTemporalGatewayIntPort;
import co.edu.unicauca.sgph.reservatemporal.domain.model.ReservaTemporal;
import co.edu.unicauca.sgph.reservatemporal.infrastructure.output.persistence.entity.ReservaTemporalEntity;
import co.edu.unicauca.sgph.reservatemporal.infrastructure.output.persistence.repository.ReservaTemporalRepositoryInt;

@Service
public class GestionarReservaTemporalGatewayImplAdapter implements GestionarReservaTemporalGatewayIntPort{

	private final ReservaTemporalRepositoryInt reservaTemporalRepositoryInt;
    private final ModelMapper mapper;

    public GestionarReservaTemporalGatewayImplAdapter(ReservaTemporalRepositoryInt repository, ModelMapper mapper) {
        this.reservaTemporalRepositoryInt = repository;
        this.mapper = mapper;
    }
	
	@Override
	public ReservaTemporal guardarReserva(ReservaTemporal reservaTemporal) {
		 ReservaTemporalEntity entity = mapper.map(reservaTemporal, ReservaTemporalEntity.class);
	        return mapper.map(reservaTemporalRepositoryInt.save(entity), ReservaTemporal.class);
	}

	@Override
	public List<ReservaTemporal> consultarReservas() {
		return mapper.map(reservaTemporalRepositoryInt.findAll(), new TypeToken<List<ReservaTemporal>>() {}.getType());
	}

	@Override
	public ReservaTemporal consultarReservaPorId(Long id) {
		return reservaTemporalRepositoryInt.findById(id)
                .map(entity -> mapper.map(entity, ReservaTemporal.class))
                .orElse(null);
	}

}
