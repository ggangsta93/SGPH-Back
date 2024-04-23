package co.edu.unicauca.sgph.edificio.infrastructure.output.persistence.gateway;

import java.util.List;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import co.edu.unicauca.sgph.edificio.aplication.output.GestionarEdificioGatewayIntPort;
import co.edu.unicauca.sgph.edificio.domain.model.Edificio;
import co.edu.unicauca.sgph.edificio.infrastructure.output.persistence.entity.EdificioEntity;
import co.edu.unicauca.sgph.edificio.infrastructure.output.persistence.repository.EdificioRepositoryInt;

@Service
public class GestionarEdificioGatewayImplAdapter implements GestionarEdificioGatewayIntPort {

	private EdificioRepositoryInt edificioRepositoryInt;
	private ModelMapper modelMapper;

	public GestionarEdificioGatewayImplAdapter(EdificioRepositoryInt edificioRepositoryInt, ModelMapper modelMapper) {
		this.edificioRepositoryInt = edificioRepositoryInt;
		this.modelMapper = modelMapper;
	}

	@Override
	public Edificio consultarEdificioPorNombre(String nombre) {
		EdificioEntity edificioEntity = this.edificioRepositoryInt.findByNombre(nombre);
		return Objects.isNull(edificioEntity) ? null : this.modelMapper.map(edificioEntity, Edificio.class);
	}

	@Override
	public Edificio guardarEdificio(Edificio edificio) {
		EdificioEntity edificioEntity = this.edificioRepositoryInt
				.save(this.modelMapper.map(edificio, EdificioEntity.class));
		return this.modelMapper.map(edificioEntity, Edificio.class);
	}

	/**
	 * @see co.edu.unicauca.sgph.edificio.aplication.output.GestionarEdificioGatewayIntPort#consultarEdificiosPorIdFacultad(java.util.List)
	 */
	@Override
	public List<Edificio> consultarEdificiosPorIdFacultad(List<Long> lstIdFacultad) {
		List<EdificioEntity> edificioEntities = this.edificioRepositoryInt
				.consultarEdificiosPorIdFacultad(lstIdFacultad);
		return this.modelMapper.map(edificioEntities, new TypeToken<List<Edificio>>() {
		}.getType());
	}
}