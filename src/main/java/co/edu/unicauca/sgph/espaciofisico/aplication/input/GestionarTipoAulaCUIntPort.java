package co.edu.unicauca.sgph.espaciofisico.aplication.input;

import java.util.List;

import co.edu.unicauca.sgph.espaciofisico.domain.model.TipoAula;

public interface GestionarTipoAulaCUIntPort {

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

