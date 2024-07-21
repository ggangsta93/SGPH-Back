package co.edu.unicauca.sgph.espaciofisico.aplication.output;

import java.util.List;

import co.edu.unicauca.sgph.espaciofisico.domain.model.Ubicacion;

public interface GestionarUbicacionGatewayIntPort {
	/**
	 * Método encargado de consultar todas las ubicaciones <br>
	 * 
	 * @author apedro
	 * 
	 * @return Lista de instancias de Ubicacion
	 */
	public List<Ubicacion> consultarUbicaciones();
}
