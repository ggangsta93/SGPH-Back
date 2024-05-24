package co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.gateway;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import co.edu.unicauca.sgph.espaciofisico.aplication.output.GestionarEdificioGatewayIntPort;
import co.edu.unicauca.sgph.espaciofisico.domain.model.Edificio;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.EdificioEntity;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.repository.EdificioRepositoryInt;

@Service
public class GestionarEdificioGatewayImplAdapter implements GestionarEdificioGatewayIntPort {

	@PersistenceContext
	private EntityManager entityManager;

	private EdificioRepositoryInt edificioRepositoryInt;
	private ModelMapper modelMapper;

	public GestionarEdificioGatewayImplAdapter(EdificioRepositoryInt edificioRepositoryInt, ModelMapper modelMapper) {
		this.edificioRepositoryInt = edificioRepositoryInt;
		this.modelMapper = modelMapper;
	}

	/**
	 * @see co.edu.unicauca.sgph.espaciofisico.aplication.output.GestionarEdificioGatewayIntPort#consultarEdificios()
	 */
	@Override
	public List<Edificio> consultarEdificios() {
		List<EdificioEntity> lstEdificioEntity = this.edificioRepositoryInt.findAll();
		return this.modelMapper.map(lstEdificioEntity, new TypeToken<List<Edificio>>() {
		}.getType());
	}

}