package co.edu.unicauca.sgph.programa.aplication.input;

import java.util.List;

import co.edu.unicauca.sgph.programa.domain.model.Departamento;

public interface GestionarDepartamentoCUIntPort {

	/**
	 * Método encargado de consultar guardar y/o actualizar un departamento<br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @return
	 */
	public Departamento guardarDepartamento(Departamento departamento);

	/**
	 * Método encargado de consultar todos los departamentos<br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @return
	 */
	public List<Departamento> consultarDepartamentos();
}
