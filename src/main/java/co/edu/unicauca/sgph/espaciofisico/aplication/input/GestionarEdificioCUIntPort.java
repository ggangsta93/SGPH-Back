package co.edu.unicauca.sgph.espaciofisico.aplication.input;

import java.util.List;

import co.edu.unicauca.sgph.espaciofisico.domain.model.Edificio;

public interface GestionarEdificioCUIntPort {
	/**
	 * Método encargado de consultar todos los edificios <br>
	 * 
	 * @author apedro
	 * 
	 * @return Listas de instancias Edificio
	 */
	public List<Edificio> consultarEdificios();
}