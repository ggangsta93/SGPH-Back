package co.edu.unicauca.sgph.asignatura.domain.useCase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;

import co.edu.unicauca.sgph.asignatura.aplication.input.GestionarAsignaturaCUIntPort;
import co.edu.unicauca.sgph.asignatura.aplication.output.AsignaturaFormatterResultsIntPort;
import co.edu.unicauca.sgph.asignatura.aplication.output.GestionarAsignaturaGatewayIntPort;
import co.edu.unicauca.sgph.asignatura.domain.model.Asignatura;
import co.edu.unicauca.sgph.asignatura.infrastructure.input.DTORequest.AsignaturaInDTO;
import co.edu.unicauca.sgph.asignatura.infrastructure.input.DTORequest.FiltroAsignaturaInDTO;
import co.edu.unicauca.sgph.asignatura.infrastructure.input.DTOResponse.AsignaturaOutDTO;
import co.edu.unicauca.sgph.asignatura.infrastructure.output.persistence.entity.EstadoAsignaturaEnum;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.MensajeOutDTO;
import co.edu.unicauca.sgph.periodoacademico.aplication.output.GestionarPeriodoAcademicoGatewayIntPort;
import co.edu.unicauca.sgph.periodoacademico.domain.model.PeriodoAcademico;

public class GestionarAsignaturaCUAdapter implements GestionarAsignaturaCUIntPort {

	private final GestionarAsignaturaGatewayIntPort gestionarAsignaturaGatewayIntPort;
	private final AsignaturaFormatterResultsIntPort asignaturaFormatterResultsIntPort;
	private final GestionarPeriodoAcademicoGatewayIntPort gestionarPeriodoAcademicoGatewayIntPort;

	public GestionarAsignaturaCUAdapter(GestionarAsignaturaGatewayIntPort gestionarAsignaturaGatewayIntPort,
			AsignaturaFormatterResultsIntPort asignaturaFormatterResultsIntPort,
			GestionarPeriodoAcademicoGatewayIntPort gestionarPeriodoAcademicoGatewayIntPort) {
		this.gestionarAsignaturaGatewayIntPort = gestionarAsignaturaGatewayIntPort;
		this.asignaturaFormatterResultsIntPort = asignaturaFormatterResultsIntPort;
		this.gestionarPeriodoAcademicoGatewayIntPort = gestionarPeriodoAcademicoGatewayIntPort;
	}

	/**
	 * @see co.edu.unicauca.sgph.asignatura.aplication.input.GestionarAsignaturaCUIntPort#guardarAsignatura(co.edu.unicauca.sgph.asignatura.domain.model.Asignatura)
	 */
	@Override
	public Asignatura guardarAsignatura(Asignatura asignatura) {
		return this.gestionarAsignaturaGatewayIntPort.guardarAsignatura(asignatura);
	}

	/**
	 * @see co.edu.unicauca.sgph.asignatura.aplication.input.GestionarAsignaturaCUIntPort#consultarAsignaturasPorIdProgramaYEstado(java.lang.Long,
	 *      co.edu.unicauca.sgph.asignatura.infrastructure.output.persistence.entity.EstadoAsignaturaEnum)
	 */
	@Override
	public List<Asignatura> consultarAsignaturasPorIdProgramaYEstado(Long idPrograma,
			EstadoAsignaturaEnum estadoAsignaturaEnum) {
		return this.gestionarAsignaturaGatewayIntPort.consultarAsignaturasPorIdProgramaYEstado(idPrograma,
				estadoAsignaturaEnum);
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

	@Override
	public MensajeOutDTO cargaMasivaAsignaturas(AsignaturaInDTO asignatura) {
		return this.gestionarAsignaturaGatewayIntPort.cargaMasivaAsignaturas(asignatura);
	}

	@Override
	public List<Asignatura> obtenerAsignaturasPorOids(List<String> oid) {
		return this.gestionarAsignaturaGatewayIntPort.obtenerAsignaturasPorListaOids(oid);
	}

	/**
	 * @see co.edu.unicauca.sgph.asignatura.aplication.input.GestionarAsignaturaCUIntPort#consultarAsignaturasDeLosCursosPorIdPrograma(java.lang.Long)
	 */
	@Override
	public List<Asignatura> consultarAsignaturasDeLosCursosPorIdPrograma(Long idPrograma) {

		// Se consulta periodo académico vigente
		PeriodoAcademico periodoAcademicoVigente = gestionarPeriodoAcademicoGatewayIntPort
				.consultarPeriodoAcademicoVigente();

		// Se valida que exista periodo académico vigente
		if (Objects.isNull(periodoAcademicoVigente)) {
			return new ArrayList<>();
		}

		return this.gestionarAsignaturaGatewayIntPort.consultarAsignaturasDeLosCursosPorIdPrograma(idPrograma,
				periodoAcademicoVigente.getIdPeriodoAcademico());
	}
}