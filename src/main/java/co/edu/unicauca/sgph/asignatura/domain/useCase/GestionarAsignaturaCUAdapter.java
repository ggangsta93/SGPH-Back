package co.edu.unicauca.sgph.asignatura.domain.useCase;

import java.util.List;

import org.springframework.data.domain.Page;

import co.edu.unicauca.sgph.asignatura.aplication.input.GestionarAsignaturaCUIntPort;
import co.edu.unicauca.sgph.asignatura.aplication.output.AsignaturaFormatterResultsIntPort;
import co.edu.unicauca.sgph.asignatura.aplication.output.GestionarAsignaturaGatewayIntPort;
import co.edu.unicauca.sgph.asignatura.domain.model.Asignatura;
import co.edu.unicauca.sgph.asignatura.infrastructure.input.DTOResponse.AsignaturaOutDTO;
import co.edu.unicauca.sgph.asignatura.infrastructure.input.DTORequest.FiltroAsignaturaDTO;

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
	public Page<AsignaturaOutDTO> consultarAsignaturasPorFiltro(FiltroAsignaturaDTO filtroAsignaturaDTO) {
		Page<AsignaturaOutDTO> listaAsignaturasDTO = this.gestionarAsignaturaGatewayIntPort
				.consultarAsignaturasPorFiltro(filtroAsignaturaDTO);		
		return listaAsignaturasDTO;
	}
	
	
}