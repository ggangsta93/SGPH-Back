package co.edu.unicauca.sgph.agrupador.domain.useCase;

import java.util.List;

import org.springframework.data.domain.Page;

import co.edu.unicauca.sgph.agrupador.aplication.input.GestionarAgrupadorEspacioFisicoCUIntPort;
import co.edu.unicauca.sgph.agrupador.aplication.output.AgrupadorEspacioFisicoFormatterResultsIntPort;
import co.edu.unicauca.sgph.agrupador.aplication.output.GestionarAgrupadorEspacioFisicoGatewayIntPort;
import co.edu.unicauca.sgph.agrupador.domain.model.AgrupadorEspacioFisico;
import co.edu.unicauca.sgph.agrupador.infrastructure.input.DTORequest.AsignacionEspacioFisicoDTO;
import co.edu.unicauca.sgph.agrupador.infrastructure.input.DTORequest.FiltroGrupoDTO;
import co.edu.unicauca.sgph.agrupador.infrastructure.input.DTOResponse.AgrupadorEspacioFisicoOutDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.MensajeOutDTO;

public class GestionarAgrupadorEspacioFisicoCUAdapter implements GestionarAgrupadorEspacioFisicoCUIntPort {

	private final GestionarAgrupadorEspacioFisicoGatewayIntPort gestionarAgrupadorEspacioFisicoGatewayIntPort;
	private final AgrupadorEspacioFisicoFormatterResultsIntPort agrupadorEspacioFisicoFormatterResultsIntPort;

	public GestionarAgrupadorEspacioFisicoCUAdapter(
			GestionarAgrupadorEspacioFisicoGatewayIntPort gestionarAgrupadorEspacioFisicoGatewayIntPort,
			AgrupadorEspacioFisicoFormatterResultsIntPort agrupadorEspacioFisicoFormatterResultsIntPort) {
		this.gestionarAgrupadorEspacioFisicoGatewayIntPort = gestionarAgrupadorEspacioFisicoGatewayIntPort;
		this.agrupadorEspacioFisicoFormatterResultsIntPort = agrupadorEspacioFisicoFormatterResultsIntPort;
	}

	/**
	 * @see co.edu.unicauca.sgph.agrupador.aplication.input.GestionarAgrupadorEspacioFisicoCUIntPort#consultarAgrupadoresEspaciosFisicosPorIdAgrupadorEspacioFisico(java.util.List)
	 */
	@Override
	public List<AgrupadorEspacioFisico> consultarAgrupadoresEspaciosFisicosPorIdAgrupadorEspacioFisico(
			List<Long> idAgrupadorEspacioFisico) {
		return this.gestionarAgrupadorEspacioFisicoGatewayIntPort
				.consultarAgrupadoresEspaciosFisicosPorIdAgrupadorEspacioFisico(idAgrupadorEspacioFisico);
	}

	/**
	 * @see co.edu.unicauca.sgph.agrupador.aplication.input.GestionarAgrupadorEspacioFisicoCUIntPort#consultarAgrupadoresEspaciosFisicosPorIdFacultad(java.util.List)
	 */
	@Override
	public List<AgrupadorEspacioFisico> consultarAgrupadoresEspaciosFisicosPorIdFacultad(List<Long> idFacultad) {
		return this.gestionarAgrupadorEspacioFisicoGatewayIntPort
				.consultarAgrupadoresEspaciosFisicosPorIdFacultad(idFacultad);
	}

	/**
	 * @see co.edu.unicauca.sgph.agrupador.aplication.input.GestionarAgrupadorEspacioFisicoCUIntPort#consultarAgrupadoresEspaciosFisicosAsociadosACursoPorIdCurso(java.lang.Long)
	 */
	@Override
	public List<AgrupadorEspacioFisico> consultarAgrupadoresEspaciosFisicosAsociadosACursoPorIdCurso(Long idCurso) {
		return this.gestionarAgrupadorEspacioFisicoGatewayIntPort
				.consultarAgrupadoresEspaciosFisicosAsociadosACursoPorIdCurso(idCurso);
	}

	@Override
	public Page<AgrupadorEspacioFisicoOutDTO> filtrarGrupos(FiltroGrupoDTO filtro) {
		return this.gestionarAgrupadorEspacioFisicoGatewayIntPort.filtrarGrupos(filtro);
	}

	@Override
	public AgrupadorEspacioFisicoOutDTO guardarGrupo(AgrupadorEspacioFisicoOutDTO agrupador) {
		return this.gestionarAgrupadorEspacioFisicoGatewayIntPort.guardarGrupo(agrupador);
	}
	
	@Override
	public MensajeOutDTO guardarAsignacion(AsignacionEspacioFisicoDTO asignacion) {
		return this.gestionarAgrupadorEspacioFisicoGatewayIntPort.guardarAsignacion(asignacion);
	}
}