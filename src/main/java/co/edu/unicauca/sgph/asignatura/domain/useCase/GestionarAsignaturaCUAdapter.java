package co.edu.unicauca.sgph.asignatura.domain.useCase;

import java.util.List;

import co.edu.unicauca.sgph.asignatura.aplication.input.GestionarAsignaturaCUIntPort;
import co.edu.unicauca.sgph.asignatura.aplication.output.AsignaturaFormatterResultsIntPort;
import co.edu.unicauca.sgph.asignatura.aplication.output.GestionarAsignaturaGatewayIntPort;
import co.edu.unicauca.sgph.asignatura.domain.model.Asignatura;

public class GestionarAsignaturaCUAdapter implements GestionarAsignaturaCUIntPort {

	private final GestionarAsignaturaGatewayIntPort gestionarAsignaturaGatewayIntPort;
	private final AsignaturaFormatterResultsIntPort asignaturaFormatterResultsIntPort;

	public GestionarAsignaturaCUAdapter(GestionarAsignaturaGatewayIntPort gestionarAsignaturaGatewayIntPort,
			AsignaturaFormatterResultsIntPort asignaturaFormatterResultsIntPort) {
		this.gestionarAsignaturaGatewayIntPort = gestionarAsignaturaGatewayIntPort;
		this.asignaturaFormatterResultsIntPort = asignaturaFormatterResultsIntPort;
	}

	@Override
	public Asignatura guardarAsignatura(Asignatura asignatura) {
		return this.gestionarAsignaturaGatewayIntPort.guardarAsignatura(asignatura);
	}

	@Override
	public List<Asignatura> consultarAsignaturasPorIdPrograma(Long idPrograma) {
		return this.gestionarAsignaturaGatewayIntPort.consultarAsignaturasPorIdPrograma(idPrograma);
	}
}