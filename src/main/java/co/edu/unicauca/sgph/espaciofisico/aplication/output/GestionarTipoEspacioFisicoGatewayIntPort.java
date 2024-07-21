package co.edu.unicauca.sgph.espaciofisico.aplication.output;

import java.util.List;

import co.edu.unicauca.sgph.espaciofisico.domain.model.TipoEspacioFisico;

public interface GestionarTipoEspacioFisicoGatewayIntPort {

	/**
	 * Método encargado de consultar los tipos de espacios físicos por ubicaciones
	 * <br>
	 * 
	 * @author apedro
	 * 
	 * @param lstIdUbicacion
	 * @return
	 */
	public List<TipoEspacioFisico> consultarTiposEspaciosFisicosPorUbicaciones(List<Long> lstIdUbicacion);

	List<TipoEspacioFisico> consultarTiposEspaciosFisicos();
}
