package co.edu.unicauca.sgph.gestionplanificacion.labordocencia.infrastructure.output.persistence.gateway;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import co.edu.unicauca.sgph.gestionplanificacion.labordocencia.aplication.output.GestionarLaborDocenciaGatewayIntPort;
import co.edu.unicauca.sgph.gestionplanificacion.labordocencia.infrastructure.output.persistence.repository.LaborDocenciaRepositoryInt;

@Service
public class GestionarLaborDocenciaGatewayImplAdapter implements GestionarLaborDocenciaGatewayIntPort {

	@PersistenceContext
	private EntityManager entityManager;

	private LaborDocenciaRepositoryInt laborDocenciaRepositoryInt;
	private ModelMapper modelMapper;

	public GestionarLaborDocenciaGatewayImplAdapter(LaborDocenciaRepositoryInt laborDocenciaRepositoryInt,
			ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
}