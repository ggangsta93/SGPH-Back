package co.edu.unicauca.sgph.docente.infrastructure.output.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.edu.unicauca.sgph.common.infrastructure.output.persistence.entities.TipoIdentificacionEntity;
import co.edu.unicauca.sgph.docente.infrastructure.output.persistence.entity.DocenteEntity;
import co.edu.unicauca.sgph.usuario.infrastructure.output.persistence.entity.UsuarioEntity;



public interface DocenteRepositoryInt extends JpaRepository<DocenteEntity, Long> {

	/**
	 * Método encargado de consultar un docente por su identificación<br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param idTipoIdentificacion
	 * @param numeroIdentificacion
	 * @return
	 */
	public DocenteEntity findByTipoIdentificacionAndNumeroIdentificacion(TipoIdentificacionEntity tipoIdentificacion,
			String numeroIdentificacion);

	/**
	 * Método encargado de consultar un docente por su identificador único <br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param idPersona
	 * @return
	 */
	public DocenteEntity findByIdPersona(Long idPersona);
	
	/**
	 * Método encargado de consultar los nombres de los docentes de un curso<br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param idCurso
	 * @return
	 */
	@Query("SELECT TRIM(CONCAT(d.primerNombre,' ', COALESCE(d.segundoNombre,''),' ', d.primerApellido,' ', COALESCE(d.segundoApellido,'')))"
			+ " FROM CursoEntity c JOIN c.docentes d "
			+ " WHERE c.idCurso = :idCurso")
	public List<String> consultarNombresDocentesPorIdCurso(@Param("idCurso") Long idCurso);
	
	
	/**
	 * Método encargado de obtener los docentes asociadas a un curso.<br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
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
			+ " AND (:idPersona IS NULL OR doc.idPersona != :idPersona) "
			+ "")
	DocenteEntity consultarDocentePorCodigo(String codigo, Long idPersona);
}
