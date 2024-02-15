package co.edu.unicauca.sgph.asignatura.aplication.output;

import java.util.List;

import co.edu.unicauca.sgph.asignatura.domain.model.Asignatura;

public interface GestionarAsignaturaGatewayIntPort {

	public Asignatura guardarAsignatura(Asignatura asignatura);

	public List<Asignatura> consultarAsignaturasPorIdPrograma(Long idPrograma);
}
