package co.edu.unicauca.sgph.asignatura.infrastructure.output.persistence.gateway;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import co.edu.unicauca.sgph.asignatura.aplication.output.GestionarAsignaturaGatewayIntPort;
import co.edu.unicauca.sgph.asignatura.domain.model.Asignatura;
import co.edu.unicauca.sgph.asignatura.infrastructure.input.DTORequest.FiltroAsignaturaDTO;
import co.edu.unicauca.sgph.asignatura.infrastructure.input.DTOResponse.AsignaturaOutDTO;
import co.edu.unicauca.sgph.asignatura.infrastructure.output.persistence.entity.AsignaturaEntity;
import co.edu.unicauca.sgph.asignatura.infrastructure.output.persistence.repository.AsignaturaRepositoryInt;

@Service
public class GestionarAsignaturaGatewayImplAdapter implements GestionarAsignaturaGatewayIntPort {

	@PersistenceContext
	private EntityManager entityManager;
	
	private final AsignaturaRepositoryInt asignaturaRepositoryInt;
	private final ModelMapper asignaturaMapper;

	public GestionarAsignaturaGatewayImplAdapter(AsignaturaRepositoryInt asignaturaRepositoryInt,
			@Qualifier("asignaturaMapper") ModelMapper asignaturaMapper) {
		this.asignaturaRepositoryInt = asignaturaRepositoryInt;
		this.asignaturaMapper = asignaturaMapper;
	}

	/**
	 * @see co.edu.unicauca.sgph.asignatura.aplication.output.GestionarAsignaturaGatewayIntPort#guardarAsignatura(co.edu.unicauca.sgph.asignatura.domain.model.Asignatura)
	 */
	@Override
	public Asignatura guardarAsignatura(Asignatura asignatura) {
		return this.asignaturaMapper.map(
				this.asignaturaRepositoryInt.save(this.asignaturaMapper.map(asignatura, AsignaturaEntity.class)),
				Asignatura.class);
	}

	/**
	 * @see co.edu.unicauca.sgph.asignatura.aplication.output.GestionarAsignaturaGatewayIntPort#consultarAsignaturasPorIdPrograma(java.lang.Long)
	 */
	@Override
	public List<Asignatura> consultarAsignaturasPorIdPrograma(Long idPrograma) {
		List<AsignaturaEntity> lstAsignaturaEntity = this.asignaturaRepositoryInt
				.consultarAsignaturasPorIdPrograma(idPrograma);
		if (lstAsignaturaEntity.isEmpty()) {
			return new ArrayList<>();
		} else {
			return this.asignaturaMapper.map(lstAsignaturaEntity, new TypeToken<List<Asignatura>>() {
			}.getType());
		}
	}

	@Override
	public Page<AsignaturaOutDTO> consultarAsignaturasPorFiltro(FiltroAsignaturaDTO filtroAsignaturaDTO) {
		// Configuración de la paginación
				PageRequest pageable = PageRequest.of(filtroAsignaturaDTO.getPagina(),
						filtroAsignaturaDTO.getRegistrosPorPagina());

				// Construcción de la consulta con StringBuilder
				StringBuilder queryBuilder = new StringBuilder();
				queryBuilder.append(" SELECT NEW co.edu.unicauca.sgph.asignatura.infrastructure.input.DTOResponse.AsignaturaOutDTO(");
				queryBuilder.append(" pr.abreviatura, a.semestre, a.nombre, a.codigoAsignatura, a.OID, a.horasSemana, a.pensum) ");		
				queryBuilder.append(" FROM AsignaturaEntity a");
				queryBuilder.append(" JOIN a.programa pr");
				queryBuilder.append(" WHERE 1=1");

				Map<String, Object> parametros = new HashMap<>();

				if (Objects.nonNull(filtroAsignaturaDTO.getListaIdFacultad())
						&& !filtroAsignaturaDTO.getListaIdFacultad().isEmpty()) {
					queryBuilder.append(" AND pr.facultad.idFacultad IN (:listaIdFacultad)");
					parametros.put("listaIdFacultad", filtroAsignaturaDTO.getListaIdFacultad());
				}

				if (Objects.nonNull(filtroAsignaturaDTO.getListaIdPrograma())
						&& !filtroAsignaturaDTO.getListaIdPrograma().isEmpty()) {
					queryBuilder.append(" AND pr.idPrograma IN (:listaIdPograma)");
					parametros.put("listaIdPograma", filtroAsignaturaDTO.getListaIdPrograma());
				}

				if (Objects.nonNull(filtroAsignaturaDTO.getSemestre())) {
					queryBuilder.append(" AND a.semestre =:semestre");
					parametros.put("semestre", filtroAsignaturaDTO.getSemestre());
				}
				
				if (Objects.nonNull(filtroAsignaturaDTO.getListaIdAsignatura())
						&& !filtroAsignaturaDTO.getListaIdAsignatura().isEmpty()) {
					queryBuilder.append(" AND a.idAsignatura IN (:listaIdAsignatura)");
					parametros.put("listaIdAsignatura", filtroAsignaturaDTO.getListaIdAsignatura());
				}

				if (Objects.nonNull(filtroAsignaturaDTO.getPensum())) {
					queryBuilder.append(" AND a.pensum =:pensum");
					parametros.put("pensum", filtroAsignaturaDTO.getPensum());
				}

				queryBuilder.append(" ORDER BY pr.nombre asc, a.semestre asc, a.nombre asc");

				// Realiza la consulta paginada
				TypedQuery<AsignaturaOutDTO> typedQuery = entityManager.createQuery(queryBuilder.toString(),
						AsignaturaOutDTO.class);
				typedQuery.setFirstResult((int) pageable.getOffset());
				typedQuery.setMaxResults(pageable.getPageSize());

				// Establece parámetros en la consulta
				for (Map.Entry<String, Object> entry : parametros.entrySet()) {
					typedQuery.setParameter(entry.getKey(), entry.getValue());
				}

				return new PageImpl<>(typedQuery.getResultList(), pageable,
						contarAsignaturasConsultadas(filtroAsignaturaDTO.getListaIdFacultad(),
								filtroAsignaturaDTO.getListaIdPrograma(), filtroAsignaturaDTO.getSemestre(), 
								filtroAsignaturaDTO.getListaIdAsignatura(), filtroAsignaturaDTO.getPensum()));
	}
	
	private Long contarAsignaturasConsultadas(List<Long> listaIdFacultad, List<Long> listaIdPograma,
			Integer semestre, List<Long> listaIdAsignatura, String pensum) {

		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(" SELECT COUNT(a)");
		queryBuilder.append(" FROM AsignaturaEntity a");
		queryBuilder.append(" JOIN a.programa pr");
		queryBuilder.append(" WHERE 1=1");

		Map<String, Object> parametros = new HashMap<>();
		
		if (Objects.nonNull(listaIdFacultad) && !listaIdFacultad.isEmpty()) {
			queryBuilder.append(" AND pr.facultad.idFacultad IN (:listaIdFacultad)");
			parametros.put("listaIdFacultad", listaIdFacultad);
		}	

		if (Objects.nonNull(listaIdPograma) && !listaIdPograma.isEmpty()) {
			queryBuilder.append(" AND pr.idPrograma IN (:listaIdPograma)");
			parametros.put("listaIdPograma", listaIdPograma);
		}
		
		if (Objects.nonNull(semestre)) {
			queryBuilder.append(" AND a.semestre =:semestre");
			parametros.put("semestre", semestre);
		}
		
		if (Objects.nonNull(listaIdAsignatura) && !listaIdAsignatura.isEmpty()) {
			queryBuilder.append(" AND a.idAsignatura IN (:listaIdAsignatura)");
			parametros.put("listaIdAsignatura", listaIdAsignatura);
		}
		
		if (Objects.nonNull(pensum)) {
			queryBuilder.append(" AND a.pensum =:pensum");
			parametros.put("pensum", pensum);
		}			
		
		queryBuilder.append(" ORDER BY pr.nombre asc, a.semestre asc, a.nombre asc");

		// Realiza la consulta para contar
		TypedQuery<Long> countQuery = entityManager.createQuery(queryBuilder.toString(), Long.class);

		// Establece parámetros en la consulta
		for (Map.Entry<String, Object> entry : parametros.entrySet()) {
			countQuery.setParameter(entry.getKey(), entry.getValue());
		}

		return countQuery.getSingleResult();
	}

}