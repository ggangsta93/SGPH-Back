package co.edu.unicauca.sgph.edificio.aplication.output;

import java.util.List;

import co.edu.unicauca.sgph.edificio.domain.model.Edificio;

public interface GestionarEdificioGatewayIntPort {

	public Edificio consultarEdificioPorNombre(String nombre);

	public Edificio guardarEdificio(Edificio edificio);
	
	/**
	 * Método encargado de consultar los edificios asociados a una lista de
	 * facultades </br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param lstIdFacultad
	 * @return
	 */
	public List<Edificio> consultarEdificiosPorIdFacultad(List<Long> lstIdFacultad);
}
