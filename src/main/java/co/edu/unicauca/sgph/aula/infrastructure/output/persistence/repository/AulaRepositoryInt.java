package co.edu.unicauca.sgph.aula.infrastructure.output.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.edu.unicauca.sgph.aula.infrastructure.output.persistence.entity.AulaEntity;
import co.edu.unicauca.sgph.aula.infrastructure.output.persistence.entity.TipoAulaEntity;

public interface AulaRepositoryInt extends JpaRepository<AulaEntity, Long> {
	
	public AulaEntity findByIdAula(Long idAula);
	
	
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
			+ " COALESCE(tau.tipo, 'Sin asignar'),' ',au.numeroAula,'-',edi.nombre))"
			+ " FROM HorarioEntity h "
			+ " JOIN h.aulas au "
			+ " LEFT JOIN au.tipoAula tau"
			+ " LEFT JOIN au.edificio edi"
			+ " JOIN edi.facultad fac"
			+ " WHERE h.curso.idCurso = :idCurso")
	public List<String> consultarAulaHorarioPorIdCurso(Long idCurso);
			
	
	
	
	
	/**
	 * Método encargado de consultar los tipo de aula asociados a una lista de
	 * edificios
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param lstIdEdificio
	 * @return
	 */
	@Query("SELECT DISTINCT aula.tipoAula FROM AulaEntity aula WHERE aula.edificio.idEdificio IN (:lstIdEdificio)")
	public List<TipoAulaEntity> consultarTipoAulasPorIdEdificio(@Param("lstIdEdificio")List<Long> lstIdEdificio);
}
