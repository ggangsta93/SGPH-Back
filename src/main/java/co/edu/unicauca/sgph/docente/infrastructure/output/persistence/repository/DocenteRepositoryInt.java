package co.edu.unicauca.sgph.docente.infrastructure.output.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.edu.unicauca.sgph.docente.infrastructure.output.persistence.entity.DocenteEntity;



public interface DocenteRepositoryInt extends JpaRepository<DocenteEntity, Long> {

	/**
	 * Método encargado de consultar un docente por su identificación<br>
	 * 
	 * @author apedro
	 * 
	 * @param idTipoIdentificacion
	 * @param numeroIdentificacion
	 * @return
	 */
	@Query("SELECT doc "
			+ " FROM DocenteEntity doc "
			+ " JOIN doc.persona per "
			+ " WHERE per.tipoIdentificacion.idTipoIdentificacion = :idTipoIdentificacion "
			+ " AND per.numeroIdentificacion = :numeroIdentificacion "
			+ "")
	public DocenteEntity consultarDocentePorIdentificacion(Long idTipoIdentificacion, String numeroIdentificacion);
	/**
	 * Método encargado de consultar un docente por su numero identificación<br>
	 *
	 * @author apedro
	 *
	 * @param numeroIdentificacion
	 * @return
	 */
	@Query("SELECT doc "
			+ " FROM DocenteEntity doc "
			+ " JOIN doc.persona per "
			+ " WHERE per.numeroIdentificacion = :numeroIdentificacion")
	 DocenteEntity consultarDocentePorIdentificacion(String numeroIdentificacion);
	/**
	 * Método encargado de consultar un docente por persona<br>
	 * 
	 * @author apedro
	 * 
	 * @param idPersona
	 * @return
	 */
	@Query("SELECT doc "
			+ " FROM DocenteEntity doc "
			+ " WHERE doc.persona.idPersona = :idPersona "
			+ "")
	public DocenteEntity consultarDocentePorIdPersona(Long idPersona);
	
	/**
	 * Método encargado de consultar los nombres de los docentes de un curso<br>
	 * 
	 * @author apedro
	 * 
	 * @param idCurso
	 * @return
	 */
	@Query("SELECT TRIM(CONCAT(p.primerNombre,' ', COALESCE(p.segundoNombre,''),' ', p.primerApellido,' ', COALESCE(p.segundoApellido,'')))"
			+ " FROM CursoEntity c JOIN c.docentes d JOIN d.persona p "
			+ " WHERE c.idCurso = :idCurso")
	public List<String> consultarNombresDocentesPorIdCurso(@Param("idCurso") Long idCurso);
	
	
	/**
	 * Método encargado de obtener los docentes asociadas a un curso.<br>
	 * 
	 * @author apedro
	 * 
	 * @param idCurso
	 * @return
	 */
	@Query("SELECT doc "
			+ " FROM DocenteEntity doc "
			+ " JOIN doc.cursos cur "
			+ " WHERE cur.idCurso = :idCurso ")
	public List<DocenteEntity> consultarDocentePorIdCurso(@Param("idCurso") Long idCurso);
	
	
	@Query("SELECT doc "
			+ " FROM DocenteEntity doc "
			+ " WHERE doc.codigo = :codigo "
			+ " AND (:idDocente IS NULL OR doc.idDocente != :idDocente) "
			+ "")
	public DocenteEntity consultarDocentePorCodigo(String codigo, Long idDocente);
	
	@Query("SELECT doc "
			+ " FROM DocenteEntity doc "
			+ " WHERE doc.persona.idPersona = :idPersona "
			+ " AND (:idDocente IS NULL OR doc.idDocente != :idDocente) "
			+ "")
	public DocenteEntity consultarUsuarioPorIdPersona(Long idPersona, Long idDocente);	

}