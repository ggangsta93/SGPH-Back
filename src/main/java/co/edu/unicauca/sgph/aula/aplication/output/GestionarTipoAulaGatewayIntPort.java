package co.edu.unicauca.sgph.aula.aplication.output;

import java.util.List;

import co.edu.unicauca.sgph.aula.domain.model.TipoAula;

public interface GestionarTipoAulaGatewayIntPort {

	/**
	 * Método encargado de consultar los tipos de aulas por facultad </br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param lstIdFacultad
	 * @return
	 */
	public List<TipoAula> consultarTipoAulasPorIdFacultad(List<Long> lstIdFacultad);
}

