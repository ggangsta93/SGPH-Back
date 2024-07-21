package co.edu.unicauca.sgph.espaciofisico.aplication.output;

import java.util.List;

import co.edu.unicauca.sgph.espaciofisico.domain.model.Edificio;

public interface GestionarEdificioGatewayIntPort {
	/**
	 * Método encargado de consultar todos los edificios <br>
	 * 
	 * @author apedro
	 * 
	 * @return Listas de instancias Edificio
	 */
	public List<Edificio> consultarEdificios();
}
