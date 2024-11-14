package co.edu.unicauca.sgph.departamento.aplication.input;

import java.util.List;

import co.edu.unicauca.sgph.departamento.domain.model.Departamento;

public interface GestionarDepartamentoCUIntPort {

	/**
	 * Método encargado de consultar guardar y/o actualizar un departamento<br>
	 * 
	 * @author apedro
	 * 
	 * @return
	 */
	public Departamento guardarDepartamento(Departamento departamento);

	/**
	 * Método encargado de consultar todos los departamentos<br>
	 * 
	 * @author apedro
	 * 
	 * @return
	 */
	public List<Departamento> consultarDepartamentos();
	
	public Departamento consultarDepartamentoPorNombre(String nombre);
}
