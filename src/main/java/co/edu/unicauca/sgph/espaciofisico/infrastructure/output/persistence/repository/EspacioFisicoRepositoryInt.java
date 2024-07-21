package co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.EspacioFisicoEntity;
import org.springframework.data.repository.query.Param;

public interface EspacioFisicoRepositoryInt extends JpaRepository<EspacioFisicoEntity, Long> {
	
	
	/**
	 * Método encargado de consultar un espacio físico por su identificador
	 * único<br>
	 * -Utilizado en la pantalla de Gestionar espacios físicos<br>
	 * 
	 * @author apedro
	 * 
	 * @param idEspacioFisico
	 * @return
	 */
	public EspacioFisicoEntity findByIdEspacioFisico(Long idEspacioFisico);	
		
	/**
	 * Método encargado de obtener los horarios formateados a partir del curso.
	 * Ejemplo del formato: 'LUNES 07:00-09:00 Salón 204-Edificio nuevo'
	 * 
	 * 
	 * @author apedro
	 * 
	 * @param idCurso
	 * @return
	 */
	@Query("SELECT TRIM(CONCAT(h.dia,' ',"
			+ " LPAD(EXTRACT(HOUR FROM h.horaInicio), 2, '0'),':',LPAD(EXTRACT(MINUTE FROM h.horaInicio), 2, '0'),'-',"
			+ " LPAD(EXTRACT(HOUR FROM h.horaFin), 2, '0'),':',LPAD(EXTRACT(MINUTE FROM h.horaFin), 2, '0'),' ',"
			+ " ef.salon))"
			+ " FROM HorarioEntity h "
			+ " JOIN h.espaciosFisicos ef "
			+ " LEFT JOIN ef.tipoEspacioFisico tef"
			+ " WHERE h.curso.idCurso = :idCurso"
			+ " AND ef.idEspacioFisico IN (SELECT horEsp.espacioFisico.idEspacioFisico "
			+ "						  	   FROM HorarioEspacioEntity horEsp "
			+ "	    		               WHERE horEsp.horario.idHorario = h.idHorario "
			+ "						       AND horEsp.esPrincipal = :esPrincipal ) "
			+ "")
	public List<String> consultarEspacioFisicoHorarioPorIdCurso(Long idCurso, Boolean esPrincipal);	

	/**
	 * Método encargado de consultar todos los edificios de los espacios físicos
	 * <br>
	 * 
	 * @author apedro
	 * 
	 * @return Nombres de los edificios
	 */
	@Query("SELECT DISTINCT e.edificio FROM EspacioFisicoEntity e")
	public List<String> consultarEdificios();

	/**
	 * Método encargado de consultar todos las ubicaciones de los espacios físicos
	 * <br>
	 * 
	 * @author apedro
	 * 
	 * @return Nombres de las ubicaciones
	 */
	@Query("SELECT DISTINCT e.ubicacion FROM EspacioFisicoEntity e")
	public List<String> consultarUbicaciones();
	
	
	/**
	 * Método encargado de consultar los edificios de los espacios físicos por
	 * ubicación <br>
	 * 
	 * @author apedro
	 * 
	 * @return Identificadores de edificios
	 */
	@Query("SELECT DISTINCT e.edificio.idEdificio FROM EspacioFisicoEntity e WHERE e.ubicacion.idUbicacion IN (:lstIdUbicacion)")
	public List<Long> consultarEdificiosPorUbicacion(List<Long> lstIdUbicacion);
	
		
	/**
	 * Método encargado de consultar la capacidad, el estado y salon de espacios
	 * físicos dado una lista de identificadores únicos <br>
	 * 
	 * @author apedro
	 * 
	 * @param lstIdEspacioFisico
	 * @return
	 */
	@Query("SELECT new co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.EspacioFisicoEntity(e.idEspacioFisico, e.capacidad, e.salon, e.estado)"
			+ " FROM EspacioFisicoEntity e "
			+ "WHERE e.idEspacioFisico IN (:lstIdEspacioFisico)")
	public List<EspacioFisicoEntity> consultarCapacidadEstadoYSalonPorListaIdEspacioFisico(List<Long> lstIdEspacioFisico);

	/**
	 * Método encargado de obtener el espacio físico principal de un curso dado el
	 * identificador único de horario <br>
	 * 
	 * @author apedro
	 * 
	 * @param idHorario
	 * @param esPrincipal
	 * @return
	 */
	@Query("SELECT e"
			+ " FROM EspacioFisicoEntity e "
			+ " JOIN e.horarios horarios "
			+ " WHERE horarios.idHorario = :idHorario"
			+ " AND e.idEspacioFisico IN (SELECT horEsp.espacioFisico.idEspacioFisico "
			+ "						  	   FROM HorarioEspacioEntity horEsp "
			+ "	    		               WHERE horEsp.horario.idHorario = :idHorario "
			+ "						       AND horEsp.esPrincipal = :esPrincipal ) ")
	public EspacioFisicoEntity consultarEspacioFisicoCursoPorIdHorario(Long idHorario, Boolean esPrincipal);	
	
	@Query("SELECT e FROM EspacioFisicoEntity e WHERE " +
			"(:salon IS NULL OR LOWER(e.salon) LIKE LOWER(CONCAT('%', :salon, '%'))) AND " +
			"(:ubicacion IS NULL OR LOWER(e.ubicacion) LIKE LOWER(CONCAT('%', :ubicacion, '%'))) AND " +
			"(:tipoEspacioFisico IS NULL OR LOWER(e.tipoEspacioFisico.tipo) LIKE LOWER(CONCAT('%', :tipoEspacioFisico, '%')))")
	List<EspacioFisicoEntity> obtenerEspacioFisicoPorFiltro(@Param("salon") String salon,
												 @Param("ubicacion") String ubicacion,
												 @Param("tipoEspacioFisico") String tipoEspacioFisico);
}
