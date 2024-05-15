package co.edu.unicauca.sgph.asignatura.domain.useCase;

import java.util.List;

import co.edu.unicauca.sgph.asignatura.aplication.input.GestionarAsignaturaCUIntPort;
import co.edu.unicauca.sgph.asignatura.aplication.output.AsignaturaFormatterResultsIntPort;
import co.edu.unicauca.sgph.asignatura.aplication.output.GestionarAsignaturaGatewayIntPort;
import co.edu.unicauca.sgph.asignatura.domain.model.Asignatura;
import co.edu.unicauca.sgph.asignatura.infrastructure.input.DTORequest.FiltroAsignaturaInDTO;
import co.edu.unicauca.sgph.asignatura.infrastructure.input.DTOResponse.AsignaturaOutDTO;
import org.springframework.data.domain.Page;

public class GestionarAsignaturaCUAdapter implements GestionarAsignaturaCUIntPort {

	private final GestionarAsignaturaGatewayIntPort gestionarAsignaturaGatewayIntPort;
	private final AsignaturaFormatterResultsIntPort asignaturaFormatterResultsIntPort;

	public GestionarAsignaturaCUAdapter(GestionarAsignaturaGatewayIntPort gestionarAsignaturaGatewayIntPort,
			AsignaturaFormatterResultsIntPort asignaturaFormatterResultsIntPort) {
		this.gestionarAsignaturaGatewayIntPort = gestionarAsignaturaGatewayIntPort;
		this.asignaturaFormatterResultsIntPort = asignaturaFormatterResultsIntPort;
	}

	/**
	 * @see co.edu.unicauca.sgph.asignatura.aplication.input.GestionarAsignaturaCUIntPort#guardarAsignatura(co.edu.unicauca.sgph.asignatura.domain.model.Asignatura)
	 */
	@Override
	public Asignatura guardarAsignatura(Asignatura asignatura) {
		return this.gestionarAsignaturaGatewayIntPort.guardarAsignatura(asignatura);
	}

	/**
	 * @see co.edu.unicauca.sgph.asignatura.aplication.input.GestionarAsignaturaCUIntPort#consultarAsignaturasPorIdPrograma(java.lang.Long)
	 */
	@Override
	public List<Asignatura> consultarAsignaturasPorIdPrograma(Long idPrograma) {
		return this.gestionarAsignaturaGatewayIntPort.consultarAsignaturasPorIdPrograma(idPrograma);
	}

	@Override
	public AsignaturaOutDTO obtenerAsignaturaPorId(final Long idAsignatura) {
		return this.gestionarAsignaturaGatewayIntPort.obtenerAsignaturaPorId(idAsignatura);
	}

	@Override
	public Page<AsignaturaOutDTO> filtrarAsignaturas(FiltroAsignaturaInDTO filtro) {
		return this.gestionarAsignaturaGatewayIntPort.filtrarAsignaturas(filtro);
	}

	@Override
	public Asignatura inactivarAsignaturaPorId(Long idAsignatura) {
		return this.gestionarAsignaturaGatewayIntPort.inactivarAsignaturaPorId(idAsignatura);
	}
}