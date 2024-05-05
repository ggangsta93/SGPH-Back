package co.edu.unicauca.sgph.espaciofisico.domain.useCase;

import java.util.List;

import org.springframework.data.domain.Page;

import co.edu.unicauca.sgph.espaciofisico.aplication.input.GestionarEspacioFisicoCUIntPort;
import co.edu.unicauca.sgph.espaciofisico.aplication.output.EspacioFisicoFormatterResultsIntPort;
import co.edu.unicauca.sgph.espaciofisico.aplication.output.GestionarEspacioFisicoGatewayIntPort;
import co.edu.unicauca.sgph.espaciofisico.domain.model.EspacioFisico;
import co.edu.unicauca.sgph.espaciofisico.domain.model.TipoEspacioFisico;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTORequest.FiltroEspacioFisicoDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.EspacioFisicoDTO;

public class GestionarEspacioFisicoCUAdapter implements GestionarEspacioFisicoCUIntPort {

	private final GestionarEspacioFisicoGatewayIntPort gestionarEspacioFisicoGatewayIntPort;
	private final EspacioFisicoFormatterResultsIntPort espacioFisicoFormatterResultsIntPort;

	public GestionarEspacioFisicoCUAdapter(GestionarEspacioFisicoGatewayIntPort gestionarEspacioFisicoGatewayIntPort,
			EspacioFisicoFormatterResultsIntPort espacioFisicoFormatterResultsIntPort) {
		this.gestionarEspacioFisicoGatewayIntPort = gestionarEspacioFisicoGatewayIntPort;
		this.espacioFisicoFormatterResultsIntPort = espacioFisicoFormatterResultsIntPort;
	}

	@Override
	public EspacioFisico guardarEspacioFisico(EspacioFisico espacioFisico) {
		return this.gestionarEspacioFisicoGatewayIntPort.guardarEspacioFisico(espacioFisico);
	}

	/**
	 * @see co.edu.unicauca.sgph.espaciofisico.aplication.input.GestionarEspacioFisicoCUIntPort#consultarEspacioFisicoHorarioPorIdCurso(java.lang.Long)
	 */
	@Override
	public List<String> consultarEspacioFisicoHorarioPorIdCurso(Long idCurso) {
		return this.gestionarEspacioFisicoGatewayIntPort.consultarEspacioFisicoHorarioPorIdCurso(idCurso);
	}

	/**
	 * @see co.edu.unicauca.sgph.espaciofisico.aplication.input.GestionarEspacioFisicoCUIntPort#consultarEspacioFisicoPorIdEspacioFisico(java.lang.Long)
	 */
	@Override
	public EspacioFisico consultarEspacioFisicoPorIdEspacioFisico(Long idEspacioFisico) {
		return this.gestionarEspacioFisicoGatewayIntPort.consultarEspacioFisicoPorIdEspacioFisico(idEspacioFisico);
	}

	/**
	 * @see co.edu.unicauca.sgph.espaciofisico.aplication.input.GestionarEspacioFisicoCUIntPort#consultarEspaciosFisicos(co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTORequest.FiltroEspacioFisicoDTO)
	 */
	@Override
	public Page<EspacioFisicoDTO> consultarEspaciosFisicos(FiltroEspacioFisicoDTO filtroEspacioFisicoDTO) {
		return this.gestionarEspacioFisicoGatewayIntPort.consultarEspaciosFisicos(filtroEspacioFisicoDTO);
	}
	
	/** 
	 * @see co.edu.unicauca.sgph.espaciofisico.aplication.input.GestionarEspacioFisicoCUIntPort#consultarTiposEspaciosFisicosPorEdificio(java.util.List)
	 */
	@Override
	public List<TipoEspacioFisico> consultarTiposEspaciosFisicosPorEdificio(List<String> lstEdificio) {
		return this.gestionarEspacioFisicoGatewayIntPort.consultarTiposEspaciosFisicosPorEdificio(lstEdificio);
	}

	/**
	 * @see co.edu.unicauca.sgph.espaciofisico.aplication.input.GestionarEspacioFisicoCUIntPort#consultarEdificios()
	 */
	@Override
	public List<String> consultarEdificios() {
		return this.gestionarEspacioFisicoGatewayIntPort.consultarEdificios();
	}

	/**
	 * @see co.edu.unicauca.sgph.espaciofisico.aplication.input.GestionarEspacioFisicoCUIntPort#consultarUbicaciones()
	 */
	@Override
	public List<String> consultarUbicaciones() {
		return this.gestionarEspacioFisicoGatewayIntPort.consultarUbicaciones();
	}

	/** 
	 * @see co.edu.unicauca.sgph.espaciofisico.aplication.input.GestionarEspacioFisicoCUIntPort#consultarEdificiosPorUbicacion(java.util.List)
	 */
	@Override
	public List<String> consultarEdificiosPorUbicacion(List<String> lstUbicacion) {
		return this.gestionarEspacioFisicoGatewayIntPort.consultarEdificiosPorUbicacion(lstUbicacion);
	}
}