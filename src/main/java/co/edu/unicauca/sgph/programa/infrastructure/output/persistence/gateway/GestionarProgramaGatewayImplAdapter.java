package co.edu.unicauca.sgph.programa.infrastructure.output.persistence.gateway;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import co.edu.unicauca.sgph.programa.aplication.output.GestionarProgramaGatewayIntPort;
import co.edu.unicauca.sgph.programa.domain.model.Programa;
import co.edu.unicauca.sgph.programa.infrastructure.output.persistence.entity.ProgramaEntity;
import co.edu.unicauca.sgph.programa.infrastructure.output.persistence.repository.ProgramaRepositoryInt;

@Service
public class GestionarProgramaGatewayImplAdapter implements GestionarProgramaGatewayIntPort {

	private ProgramaRepositoryInt programaRepositoryInt;
	private ModelMapper modelMapper;

	public GestionarProgramaGatewayImplAdapter(ProgramaRepositoryInt programaRepositoryInt, ModelMapper modelMapper) {
		this.programaRepositoryInt = programaRepositoryInt;
		this.modelMapper = modelMapper;
	}

	@Override
	public Programa consultarProgramaPorNombre(String nombre) {
		return this.modelMapper.map(this.programaRepositoryInt.findByNombre(nombre), Programa.class);
	}

	@Override
	public Programa guardarPrograma(Programa programa) {
		ProgramaEntity programaEntity = this.programaRepositoryInt
				.save(this.modelMapper.map(programa, ProgramaEntity.class));
		return this.modelMapper.map(programaEntity, Programa.class);
	}

	/**
	 * @see co.edu.unicauca.sgph.aplication.output.GestionarProgramaGatewayIntPort#consultarProgramasPorIdFacultad(java.util.List)
	 */
	@Override
	public List<Programa> consultarProgramasPorIdFacultad(List<Long> lstIdFacultad) {
		List<ProgramaEntity> programaEntities = this.programaRepositoryInt
				.consultarProgramasPorIdFacultad(lstIdFacultad);
		return this.modelMapper.map(programaEntities, new TypeToken<List<Programa>>() {
		}.getType());
	}

	/**
	 * @see co.edu.unicauca.sgph.programa.aplication.output.GestionarProgramaGatewayIntPort#consultarProgramas()
	 */
	@Override
	public List<Programa> consultarProgramas() {
		return this.modelMapper.map(this.programaRepositoryInt.findAll(), new TypeToken<List<Programa>>() {
		}.getType());
	}
}