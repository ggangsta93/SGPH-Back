package co.edu.unicauca.sgph.edificio.aplication.input;

import java.util.List;

import co.edu.unicauca.sgph.edificio.domain.model.Edificio;

public interface GestionarEdificioCUIntPort {

	public Edificio consultarEdificioPorNombre(String nombre);

	public Edificio guardarEdificio(Edificio edificio);
	
	/**
	 * Método encargado de consultar los edificios asociados a una lista de
	 * facultades <br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param lstIdFacultad
	 * @return
	 */
	public List<Edificio> consultarEdificiosPorIdFacultad(List<Long> lstIdFacultad);
}
