package co.edu.unicauca.sgph.asignatura.aplication.output;

import java.util.List;

import co.edu.unicauca.sgph.asignatura.domain.model.Asignatura;

public interface GestionarAsignaturaGatewayIntPort {

	/**
	 * Método encargado de guardar o actualizar una asignatura <br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param asignatura
	 * @return
	 */
	public Asignatura guardarAsignatura(Asignatura asignatura);

	/**
	 * Método encargado de consultar las asignaturas por programa <br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param idPrograma
	 * @return
	 */
	public List<Asignatura> consultarAsignaturasPorIdPrograma(Long idPrograma);
}
