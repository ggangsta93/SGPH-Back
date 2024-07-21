package co.edu.unicauca.sgph.agrupador.infrastructure.output.persistence.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.edu.unicauca.sgph.agrupador.infrastructure.output.persistence.entity.AgrupadorEspacioFisicoEntity;

public interface AgrupadorEspacioFisicoRepositoryInt extends JpaRepository<AgrupadorEspacioFisicoEntity, Long> {

	/**
	 * Método encargado de consultar los agrupadores de espacios físicos dado una
	 * lista de identificadores únicos<br>
	 * 
	 * @author apedro
	 * 
	 * @param idAgrupadorEspacioFisico
	 * @return Lista de instancias de AgrupadorEspacioFisico
	 */
	@Query("SELECT agrupador" + " FROM AgrupadorEspacioFisicoEntity agrupador "
			+ " WHERE agrupador.idAgrupadorEspacioFisico IN (:idAgrupadorEspacioFisico)")
	public List<AgrupadorEspacioFisicoEntity> consultarAgrupadoresEspaciosFisicosPorIdAgrupadorEspacioFisico(
			List<Long> idAgrupadorEspacioFisico);

	/**
	 * Método encargado de consultar los agrupadores de espacios físicos dado una
	 * lista de identificadores únicos de facultades<br>
	 * 
	 * @author apedro
	 * 
	 * @param idFacultad
	 * @return Lista de instancias de AgrupadorEspacioFisico
	 */
	@Query("SELECT agrupador" + " FROM AgrupadorEspacioFisicoEntity agrupador "
			+ " WHERE agrupador.facultad.idFacultad IN (:idFacultad)")
	public List<AgrupadorEspacioFisicoEntity> consultarAgrupadoresEspaciosFisicosPorIdFacultad(List<Long> idFacultad);

	/**
	 * Método encargado de consultar los agrupadores de espacios físicos asociados a
	 * un curso<br>
	 * 
	 * @author apedro
	 * 
	 * @param idCurso
	 * @return Lista de instancias de AgrupadorEspacioFisico
	 */
	@Query("SELECT agrupadores" + " FROM CursoEntity curso " + " JOIN curso.asignatura asignatura"
			+ " JOIN asignatura.agrupadores agrupadores" + " WHERE curso.idCurso =:idCurso")
	public List<AgrupadorEspacioFisicoEntity> consultarAgrupadoresEspaciosFisicosAsociadosACursoPorIdCurso(
			Long idCurso);

	/**
	 * Método encargado de contar los espacios físicos por grupo<br>
	 * 
	 * @author apedro
	 * 
	 * @return Lista de Object[]
	 */
	@Query("SELECT a.idAgrupadorEspacioFisico, COUNT(e) FROM AgrupadorEspacioFisicoEntity a LEFT JOIN a.espaciosFisicos e GROUP BY a.idAgrupadorEspacioFisico")
	public List<Object[]> contarEspaciosFisicosPorAgrupador();

	Page<AgrupadorEspacioFisicoEntity> findByFacultadIdFacultadIn(List<Long> idFacultad, Pageable page);
	
	@Query("SELECT agrupador "
			+ " FROM AgrupadorEspacioFisicoEntity agrupador "
			+ " WHERE agrupador.nombre = :nombreAgrupador "
			+ " AND (:idAgrupadorEspacioFisico IS NULL OR agrupador.idAgrupadorEspacioFisico != :idAgrupadorEspacioFisico) "
			+ "")
	public AgrupadorEspacioFisicoEntity consultarAgrupadorPorNombreAgrupador(String nombreAgrupador, Long idAgrupadorEspacioFisico);
}
