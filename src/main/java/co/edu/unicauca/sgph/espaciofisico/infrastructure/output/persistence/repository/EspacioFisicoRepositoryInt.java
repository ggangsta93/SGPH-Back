package co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.EspacioFisicoEntity;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.TipoEspacioFisicoEntity;

public interface EspacioFisicoRepositoryInt extends JpaRepository<EspacioFisicoEntity, Long> {
	
	
	/**
	 * Método encargado de consultar un espacio físico por su identificador
	 * único<br>
	 * -Utilizado en la pantalla de Gestionar espacios físicos<br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
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
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
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
			+ " WHERE h.curso.idCurso = :idCurso")
	public List<String> consultarEspacioFisicoHorarioPorIdCurso(Long idCurso);	

	/**
	 * Método encargado de consultar todos los edificios de los espacios físicos
	 * <br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @return Nombres de los edificios
	 */
	@Query("SELECT DISTINCT e.edificio FROM EspacioFisicoEntity e")
	public List<String> consultarEdificios();

	/**
	 * Método encargado de consultar todos las ubicaciones de los espacios físicos
	 * <br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @return Nombres de las ubicaciones
	 */
	@Query("SELECT DISTINCT e.ubicacion FROM EspacioFisicoEntity e")
	public List<String> consultarUbicaciones();
	
	
	/**
	 * Método encargado de consultar los edificios de los espacios físicos por
	 * ubicación <br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @return Nombres de los edificios
	 */
	@Query("SELECT DISTINCT e.edificio FROM EspacioFisicoEntity e WHERE e.ubicacion IN (:lstUbicacion)")
	public List<String> consultarEdificiosPorUbicacion(List<String> lstUbicacion);

}
