package co.edu.unicauca.sgph.horario.aplication.input;

import java.util.List;

import co.edu.unicauca.sgph.curso.domain.model.Curso;
import co.edu.unicauca.sgph.horario.domain.model.Horario;

public interface GestionarHorarioCUIntPort {

	/**
	 * Método encargado de obtener los horarios de un curso
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param curso
	 * @return
	 */
	public List<Horario> consultarHorarioPorCurso(Curso curso);

}
