package co.edu.unicauca.sgph.departamento.infrastructure.output.persistence.gateway;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import co.edu.unicauca.sgph.departamento.aplication.output.GestionarDepartamentoGatewayIntPort;
import co.edu.unicauca.sgph.departamento.domain.model.Departamento;
import co.edu.unicauca.sgph.departamento.infrastructure.output.persistence.entity.DepartamentoEntity;
import co.edu.unicauca.sgph.departamento.infrastructure.output.persistence.repository.DepartamentoRepositoryInt;

@Service
public class GestionarDepartamentoGatewayImplAdapter implements GestionarDepartamentoGatewayIntPort {

	private DepartamentoRepositoryInt departamentoRepositoryInt;
	private ModelMapper modelMapper;

	public GestionarDepartamentoGatewayImplAdapter(DepartamentoRepositoryInt departamentoRepositoryInt,
			ModelMapper modelMapper) {
		this.departamentoRepositoryInt = departamentoRepositoryInt;
		this.modelMapper = modelMapper;
	}

	/**
	 * @see co.edu.unicauca.sgph.departamento.aplication.output.GestionarDepartamentoGatewayIntPort#guardarDepartamento(co.edu.unicauca.sgph.departamento.domain.model.Departamento)
	 */
	@Override
	public Departamento guardarDepartamento(Departamento departamento) {
		DepartamentoEntity departamentoEntity = this.departamentoRepositoryInt
				.save(this.modelMapper.map(departamento, DepartamentoEntity.class));
		return this.modelMapper.map(departamentoEntity, Departamento.class);
	}

	/**
	 * @see co.edu.unicauca.sgph.departamento.aplication.output.GestionarDepartamentoGatewayIntPort#consultarDepartamentos()
	 */
	@Override
	public List<Departamento> consultarDepartamentos() {
		return this.modelMapper.map(this.departamentoRepositoryInt.findAll(), new TypeToken<List<Departamento>>() {
		}.getType());
	}
	
	public Long obtenerIdDepartamentoPorNombre(String nombreDepartamento) {
        return departamentoRepositoryInt.findByNombre(nombreDepartamento)
                .map(DepartamentoEntity::getIdDepartamento)
                .orElseThrow(() -> new RuntimeException("Departamento no encontrado: " + nombreDepartamento));
    }

	@Override
	public Departamento consultarDepartamentoPorNombre(String nombre) {
		DepartamentoEntity departamentoEntity = this.departamentoRepositoryInt.consultarDepartamentoPorNombre(nombre);	    
	    return departamentoEntity != null ? this.modelMapper.map(departamentoEntity, Departamento.class) : null;
	}
}