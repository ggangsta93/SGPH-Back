package co.edu.unicauca.sgph.espaciofisico.aplication.input;

import java.util.List;

import co.edu.unicauca.sgph.espaciofisico.domain.model.Edificio;

public interface GestionarEdificioCUIntPort {
	/**
	 * Método encargado de consultar todos los edificios <br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @return Listas de instancias Edificio
	 */
	public List<Edificio> consultarEdificios();
}