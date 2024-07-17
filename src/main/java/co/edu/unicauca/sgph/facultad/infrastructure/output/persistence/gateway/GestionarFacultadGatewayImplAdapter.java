package co.edu.unicauca.sgph.facultad.infrastructure.output.persistence.gateway;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import co.edu.unicauca.sgph.facultad.aplication.output.GestionarFacultadGatewayIntPort;
import co.edu.unicauca.sgph.facultad.domain.model.Facultad;
import co.edu.unicauca.sgph.facultad.infrastructure.output.persistence.entity.FacultadEntity;
import co.edu.unicauca.sgph.facultad.infrastructure.output.persistence.repository.FacultadRepositoryInt;

@Service
public class GestionarFacultadGatewayImplAdapter implements GestionarFacultadGatewayIntPort {

	private FacultadRepositoryInt facultadRepositoryInt;
	private ModelMapper modelMapper;

	public GestionarFacultadGatewayImplAdapter(FacultadRepositoryInt facultadRepositoryInt, ModelMapper modelMapper) {
		this.facultadRepositoryInt = facultadRepositoryInt;
		this.modelMapper = modelMapper;
	}

	@Override
	public Facultad guardarFacultad(Facultad facultad) {
		FacultadEntity facultadEntity = this.facultadRepositoryInt
				.save(this.modelMapper.map(facultad, FacultadEntity.class));
		return this.modelMapper.map(facultadEntity, Facultad.class);
	}

	/**
	 * @see co.edu.unicauca.sgph.aplication.output.GestionarFacultadGatewayIntPort#consultarFacultades()
	 */
	@Override
	public List<Facultad> consultarFacultades() {
		return this.modelMapper.map(facultadRepositoryInt.findAll(), new TypeToken<List<Facultad>>() {
		}.getType());
	}
}
