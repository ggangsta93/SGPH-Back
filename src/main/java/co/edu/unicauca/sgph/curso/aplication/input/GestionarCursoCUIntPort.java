package co.edu.unicauca.sgph.curso.aplication.input;

import co.edu.unicauca.sgph.curso.domain.model.Curso;

public interface GestionarCursoCUIntPort {
	
	public Curso consultarCursoPorGrupoYAsignatura(String grupo, Long idAsignatura);

	public Curso guardarCurso(Curso curso);

	/**
	 * Método encargado de obtener el curso por su identificador
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.com.co>
	 * 
	 * @param idCurso
	 * @return
	 */
	public Curso consultarCursoPorIdCurso(Long idCurso);	
	
}
