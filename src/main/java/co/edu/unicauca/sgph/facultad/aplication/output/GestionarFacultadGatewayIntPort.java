package co.edu.unicauca.sgph.facultad.aplication.output;

import java.util.List;

import co.edu.unicauca.sgph.facultad.domain.model.Facultad;

public interface GestionarFacultadGatewayIntPort {
	
	public Facultad guardarFacultad(Facultad facultad);
	
	/**
	 * Método encargado de consultar todas las facultades
	 * 
	 * @author apedro
	 * 
	 * @return
	 */
	public List<Facultad> consultarFacultades();
}
