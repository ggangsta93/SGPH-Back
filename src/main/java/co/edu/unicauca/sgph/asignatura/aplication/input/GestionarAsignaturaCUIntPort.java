package co.edu.unicauca.sgph.asignatura.aplication.input;

import java.util.List;

import co.edu.unicauca.sgph.asignatura.domain.model.Asignatura;

public interface GestionarAsignaturaCUIntPort {
	
	/**
	 * Método encargado de guardar o actualizar una asignatura </br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param asignatura
	 * @return
	 */
	public Asignatura guardarAsignatura(Asignatura asignatura);

	public List<Asignatura> consultarAsignaturasPorIdPrograma(Long idPrograma);
}
