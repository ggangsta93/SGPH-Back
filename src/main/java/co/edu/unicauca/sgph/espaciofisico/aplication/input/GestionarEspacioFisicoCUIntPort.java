package co.edu.unicauca.sgph.espaciofisico.aplication.input;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;

import co.edu.unicauca.sgph.espaciofisico.domain.model.EspacioFisico;
import co.edu.unicauca.sgph.espaciofisico.domain.model.TipoEspacioFisico;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTORequest.FiltroEspacioFisicoDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.EspacioFisicoDTO;

public interface GestionarEspacioFisicoCUIntPort {

	public EspacioFisico consultarEspacioFisicoPorIdEspacioFisico(Long idEspacioFisico);

	public EspacioFisico guardarEspacioFisico(EspacioFisico espacioFisico);

	public List<String> consultarEspacioFisicoHorarioPorIdCurso(Long idCurso);

	public Page<EspacioFisicoDTO> consultarEspaciosFisicos(@RequestBody FiltroEspacioFisicoDTO filtroEspacioFisicoDTO);

	/**
	 * Método encargado de consultar los tipo de espacios físicos asociados a una
	 * lista de edificios </br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param lstIdEdificio
	 * @return
	 */
	public List<TipoEspacioFisico> consultarTiposEspaciosFisicosPorIdEdificio(List<Long> lstIdEdificio);
}