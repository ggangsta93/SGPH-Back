package co.edu.unicauca.sgph.facultad.aplication.input;

import java.util.List;

import co.edu.unicauca.sgph.facultad.domain.model.Facultad;

public interface GestionarFacultadCUIntPort {

	public Facultad guardarFacultad(Facultad facultad);

	/**
	 * Método encargado de consultar todas las facultades
	 * 
	 * @author apedro
	 * 
	 * @return Lista de objetos de tipo Facultad
	 */
	public List<Facultad> consultarFacultades();
}