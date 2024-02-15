package co.edu.unicauca.sgph.asignatura.infrastructure.output.persistence.gateway;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import co.edu.unicauca.sgph.asignatura.aplication.output.GestionarAsignaturaGatewayIntPort;
import co.edu.unicauca.sgph.asignatura.domain.model.Asignatura;
import co.edu.unicauca.sgph.asignatura.infrastructure.output.persistence.entity.AsignaturaEntity;
import co.edu.unicauca.sgph.asignatura.infrastructure.output.persistence.repository.AsignaturaRepositoryInt;

@Service
public class GestionarAsignaturaGatewayImplAdapter implements GestionarAsignaturaGatewayIntPort {

	private final AsignaturaRepositoryInt asignaturaRepositoryInt;
	private final ModelMapper asignaturaMapper;

	public GestionarAsignaturaGatewayImplAdapter(AsignaturaRepositoryInt asignaturaRepositoryInt,
			@Qualifier("asignaturaMapper") ModelMapper asignaturaMapper) {
		this.asignaturaRepositoryInt = asignaturaRepositoryInt;
		this.asignaturaMapper = asignaturaMapper;
	}

	@Override
	public Asignatura guardarAsignatura(Asignatura asignatura) {
		return this.asignaturaMapper.map(
				this.asignaturaRepositoryInt.save(this.asignaturaMapper.map(asignatura, AsignaturaEntity.class)),
				Asignatura.class);
	}

	@Override
	public List<Asignatura> consultarAsignaturasPorIdPrograma(Long idPrograma) {
		List<AsignaturaEntity> lstAsignaturaEntity = this.asignaturaRepositoryInt
				.consultarAsignaturasPorIdPrograma(idPrograma);
		if (lstAsignaturaEntity.isEmpty()) {
			return new ArrayList<>();
		} else {
			return this.asignaturaMapper.map(lstAsignaturaEntity, new TypeToken<List<Asignatura>>() {
			}.getType());
		}
	}
}