package co.edu.unicauca.sgph.curso.aplication.output;

import java.util.List;

import co.edu.unicauca.sgph.curso.domain.model.Curso;

public interface GestionarCursoGatewayIntPort {

	/**
	 * Método encargado de consultar un curso por grupo y asignatura <br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param grupo
	 * @param idAsignatura
	 * @return
	 */
	@Deprecated
	public Curso consultarCursoPorGrupoYAsignatura(String grupo, Long idAsignatura);

	/**
	 * Método encargado de guardar o actualizar un curso <br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param curso
	 * @return
	 */
	public Curso guardarCurso(Curso curso);

	/**
	 * Método encargado de obtener el curso por su identificador, si no existe
	 * retorna nulo.
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.com.co>
	 * 
	 * @param idCurso
	 * @return Retorna el curso si existe, caso contrario retorna nulo
	 */
	public Curso consultarCursoPorIdCurso(Long idCurso);

	/**
	 * Método encargado de consultar los agrupadores de espacios físicos asociados
	 * al curso<br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param cursoInDTO
	 * @return Lista de idAgrupadorEspacioFisico
	 */
	public List<Long> consultarAgrupadoresEspaciosFisicosPorCurso(Long idCurso);

}
