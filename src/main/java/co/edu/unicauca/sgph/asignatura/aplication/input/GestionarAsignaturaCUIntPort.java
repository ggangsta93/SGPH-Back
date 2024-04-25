package co.edu.unicauca.sgph.asignatura.aplication.input;

import java.util.List;

import org.springframework.data.domain.Page;

import co.edu.unicauca.sgph.asignatura.domain.model.Asignatura;
import co.edu.unicauca.sgph.asignatura.infrastructure.input.DTOResponse.AsignaturaOutDTO;
import co.edu.unicauca.sgph.asignatura.infrastructure.input.DTORequest.FiltroAsignaturaDTO;

public interface GestionarAsignaturaCUIntPort {
	
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
	
	/**
	 * Método encargado de obtener las asignaturas dado un conjunto de criterios de
	 * busqueda.
	 * 
	 * @author Julieth Fernanda Hurtado Sanchez <juliethhs@unicauca.edu.co>
	 * 
	 * @param filtroAsignaturaDTO
	 * @return
	 */	
	public Page<AsignaturaOutDTO> consultarAsignaturasPorFiltro(
			FiltroAsignaturaDTO filtroAsignaturaDTO);
}
