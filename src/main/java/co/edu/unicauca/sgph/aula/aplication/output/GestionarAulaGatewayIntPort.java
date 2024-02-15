package co.edu.unicauca.sgph.aula.aplication.output;

import java.util.List;

import org.springframework.data.domain.Page;

import co.edu.unicauca.sgph.aula.domain.model.Aula;
import co.edu.unicauca.sgph.aula.domain.model.TipoAula;
import co.edu.unicauca.sgph.aula.infrastructure.input.DTORequest.FiltroAulaDTO;
import co.edu.unicauca.sgph.aula.infrastructure.input.DTOResponse.AulaDTO;

public interface GestionarAulaGatewayIntPort {

	public Aula consultarAulaPorIdAula(Long idAula);

	public Aula guardarAula(Aula aula);

	public List<String> consultarAulaHorarioPorIdCurso(Long idCurso);
	

	public Page<AulaDTO> consultarAulas(FiltroAulaDTO filtroAulaDTO);

	/**
	 * Método encargado de consultar los tipo de aula asociados a una lista de
	 * edificios </br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param lstIdEdificio
	 * @return
	 */
	public List<TipoAula> consultarTipoAulasPorIdEdificio(List<Long> lstIdEdificio);
}
