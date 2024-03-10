package co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.EspacioFisicoEntity;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.TipoEspacioFisicoEntity;

public interface EspacioFisicoRepositoryInt extends JpaRepository<EspacioFisicoEntity, Long> {
	
	
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
			+ " COALESCE(tef.tipo, 'Sin asignar'),' ',ef.numeroEspacioFisico,'-',edi.nombre))"
			+ " FROM HorarioEntity h "
			+ " JOIN h.espaciosFisicos ef "
			+ " LEFT JOIN ef.tipoEspacioFisico tef"
			+ " LEFT JOIN ef.edificio edi"
			+ " JOIN edi.facultad fac"
			+ " WHERE h.curso.idCurso = :idCurso")
	public List<String> consultarEspacioFisicoHorarioPorIdCurso(Long idCurso);	
	
	/**
	 * Método encargado de consultar los tipo de espacios físicos
	 * asociados a una lista de edificios
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param lstIdEdificio
	 * @return
	 */
	@Query("SELECT DISTINCT espacioFisico.tipoEspacioFisico FROM EspacioFisicoEntity espacioFisico WHERE espacioFisico.edificio.idEdificio IN (:lstIdEdificio)")
	public List<TipoEspacioFisicoEntity> consultarTiposEspaciosFisicosPorIdEdificio(@Param("lstIdEdificio")List<Long> lstIdEdificio);
}
