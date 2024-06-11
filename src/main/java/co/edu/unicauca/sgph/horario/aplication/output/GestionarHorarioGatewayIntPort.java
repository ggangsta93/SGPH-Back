package co.edu.unicauca.sgph.horario.aplication.output;

import java.util.List;

import co.edu.unicauca.sgph.curso.domain.model.Curso;
import co.edu.unicauca.sgph.horario.domain.model.Horario;

public interface GestionarHorarioGatewayIntPort {

	/**
	 * Método encargado de almacenar un horario
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param horario
	 * @return
	 */
	public Horario guardarHorario(Horario horario);
	
	/**
	 * Método encargado de almacenar un horario utilizando una nueva transacción. Es
	 * útil para el método que genera el horario basado en semestre anterior. (Es
	 * una solución para el problema que no persiste los horarios completamente)
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param horario
	 * @return
	 */
	public Horario guardarHorarioConNuevaTransaccion(Horario horario);

	/**
	 * Método encargado de eliminar un horario
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param horario
	 */
	public void eliminarHorario(Horario horario);	

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