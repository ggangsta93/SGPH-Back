package co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.gateway;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import co.edu.unicauca.sgph.espaciofisico.aplication.output.GestionarUbicacionGatewayIntPort;
import co.edu.unicauca.sgph.espaciofisico.domain.model.Ubicacion;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.UbicacionEntity;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.repository.UbicacionRepositoryInt;

@Service
public class GestionarUbicacionGatewayImplAdapter implements GestionarUbicacionGatewayIntPort {

	@PersistenceContext
	private EntityManager entityManager;

	private UbicacionRepositoryInt ubicacionRepositoryInt;
	private ModelMapper modelMapper;

	public GestionarUbicacionGatewayImplAdapter(UbicacionRepositoryInt ubicacionRepositoryInt,
			ModelMapper modelMapper) {
		this.ubicacionRepositoryInt = ubicacionRepositoryInt;
		this.modelMapper = modelMapper;
	}

	/**
	 * @see co.edu.unicauca.sgph.espaciofisico.aplication.output.GestionarUbicacionGatewayIntPort#consultarUbicaciones()
	 */
	@Override
	public List<Ubicacion> consultarUbicaciones() {
		List<UbicacionEntity> lstUbicacionEntity = this.ubicacionRepositoryInt.findAll();
		return this.modelMapper.map(lstUbicacionEntity, new TypeToken<List<Ubicacion>>() {
		}.getType());
	}

}