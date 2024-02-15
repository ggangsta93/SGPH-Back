package co.edu.unicauca.sgph.programa.aplication.input;

import java.util.List;

import co.edu.unicauca.sgph.programa.domain.model.Programa;

public interface GestionarProgramaCUIntPort {
	
	public Programa consultarProgramaPorNombre(String nombre);

	public Programa guardarPrograma(Programa programa);

	/**
	 * Método encargado de consultar los programas asociados a una lista de
	 * facultades </br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param lstIdFacultad
	 * @return
	 */
	public List<Programa> consultarProgramasPorIdFacultad(List<Long> lstIdFacultad);
	
	/**
	 * Método encargado de consultar todos los programas</br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @return
	 */
	public List<Programa> consultarProgramas();
}
