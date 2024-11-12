package co.edu.unicauca.sgph.curso.aplication.input;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.repository.query.Param;

import co.edu.unicauca.sgph.curso.domain.model.Curso;
import co.edu.unicauca.sgph.periodoacademico.domain.model.PeriodoAcademico;
import co.edu.unicauca.sgph.periodoacademico.infrastructure.output.persistence.entity.PeriodoAcademicoEntity;

public interface GestionarCursoCUIntPort {

	/**
	 * Método encargado de consultar un curso por grupo y asignatura <br>
	 * 
	 * @author apedro
	 * 
	 * @param grupo
	 * @param idAsignatura
	 * @return
	 */
	
	public Curso consultarCursoPorGrupoYAsignatura(String grupo, Long idAsignatura);

	/**
	 * Método encargado de guardar o actualizar un curso <br>
	 * 
	 * @author apedro
	 * 
	 * @param curso
	 * @return
	 */
	public Curso guardarCurso(Curso curso);

	/**
	 * Método encargado de obtener el curso por su identificador<br>
	 * 
	 * @author apedro
	 * 
	 * @param idCurso
	 * @return
	 */
	public Curso consultarCursoPorIdCurso(Long idCurso);

	/**
	 * Método encargado de consultar los agrupadores de espacios físicos asociados
	 * al curso<br>
	 * 
	 * @author apedro
	 * 
	 * @param cursoInDTO
	 * @return Lista de idAgrupadorEspacioFisico
	 */
	public List<Long> consultarAgrupadoresEspaciosFisicosPorCurso(Long idCurso);

	Curso obtenerCurso(Long id);

	Boolean eliminarCurso(Long id);
	List<Curso> consultarCursosPorIdPeriodoYIdPrograma(Long idPeriodo, Long idPrograma);

	Boolean existsCursoByGrupoYAsignatura(String grupo, Long idAsignatura);
	
	Boolean existsCursoByGrupoYCupoYPeriodoYAsignatura(String grupo, Integer cupo, Long idPeriodo, Long idAsignatura);
	
	List<Curso> findCursoByGrupoYCupoYPeriodoYAsignatura(String grupo, Long idAsignatura);
	
	@Transactional
	int actualizarCurso(PeriodoAcademicoEntity periodoAcademico, Integer cupo, String grupo, Long asignaturaId);
}
