package co.edu.unicauca.sgph.programa.aplication.output;

import java.util.List;

import co.edu.unicauca.sgph.programa.domain.model.Programa;

public interface GestionarProgramaGatewayIntPort {
	
	public Programa consultarProgramaPorNombre(String nombre);

	public Programa guardarPrograma(Programa programa);

	/**
	 * Método encargado de consultar los programas asociados a una lista de
	 * facultades <br>
	 * 
	 * @author apedro
	 * 
	 * @param lstIdFacultad
	 * @return
	 */
	public List<Programa> consultarProgramasPorIdFacultad(List<Long> lstIdFacultad);
	
	/**
	 * Método encargado de consultar todos los programas<br>
	 * 
	 * @author apedro
	 * 
	 * @return
	 */
	public List<Programa> consultarProgramas();
	
	/**
	 * Método encargado de consultar un programa dado el identificador único </br>
	 * 
	 * @author apedro
	 * 
	 * @param idPrograma Identificador único del programa
	 * @return
	 */
	public Programa consultarProgramaPorId(Long idPrograma);
	
	/**
	 * Método encargado de consultar los programas permitidos para el usuario que se
	 * encuentra logueado<br>
	 * 
	 * @author apedro
	 * 
	 * @return
	 */
	public List<Programa> consultarProgramasPermitidosPorUsuario();

}