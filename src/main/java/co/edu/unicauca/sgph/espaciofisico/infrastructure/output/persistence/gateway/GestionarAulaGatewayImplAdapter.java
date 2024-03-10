package co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.gateway;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import co.edu.unicauca.sgph.espaciofisico.aplication.output.GestionarAulaGatewayIntPort;
import co.edu.unicauca.sgph.espaciofisico.domain.model.Aula;
import co.edu.unicauca.sgph.espaciofisico.domain.model.TipoAula;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTORequest.FiltroAulaDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.AulaDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.AulaEntity;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.TipoAulaEntity;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.repository.AulaRepositoryInt;

@Service
public class GestionarAulaGatewayImplAdapter implements GestionarAulaGatewayIntPort {

	@PersistenceContext
	private EntityManager entityManager;

	private AulaRepositoryInt aulaRepositoryInt;
	private ModelMapper modelMapper;

	public GestionarAulaGatewayImplAdapter(AulaRepositoryInt aulaRepositoryInt, ModelMapper modelMapper) {
		this.aulaRepositoryInt = aulaRepositoryInt;
		this.modelMapper = modelMapper;
	}

	@Override
	public Aula consultarAulaPorIdAula(Long idAula) {
		return this.modelMapper.map(this.aulaRepositoryInt.findByIdAula(idAula), Aula.class);
	}

	@Override
	public Aula guardarAula(Aula aula) {
		AulaEntity aulaEntity = this.aulaRepositoryInt.save(this.modelMapper.map(aula, AulaEntity.class));
		return this.modelMapper.map(aulaEntity, Aula.class);
	}

	@Override
	public List<String> consultarAulaHorarioPorIdCurso(Long idCurso) {
		return this.aulaRepositoryInt.consultarAulaHorarioPorIdCurso(idCurso);
	}

	@Override
	public Page<AulaDTO> consultarAulas(FiltroAulaDTO filtroAulaDTO) {
		PageRequest pageable = null;
		if (Objects.nonNull(filtroAulaDTO.getPagina()) && Objects.nonNull(filtroAulaDTO.getRegistrosPorPagina())) {
			// Configuración de la paginación
			pageable = PageRequest.of(filtroAulaDTO.getPagina(), filtroAulaDTO.getRegistrosPorPagina());
		}

		// Construcción de la consulta con StringBuilder
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(" SELECT NEW co.edu.unicauca.sgph.aula.infrastructure.input.DTOResponse.AulaDTO(");
		queryBuilder.append(" facultad.abreviatura, edificio.nombre, ");
		queryBuilder.append(" tipoAula.tipo, ");
		queryBuilder.append(" aula.numeroAula, aula.capacidad, aula.estado, aula.idAula)");
		queryBuilder.append(" FROM AulaEntity aula");
		queryBuilder.append(" JOIN aula.edificio edificio");
		queryBuilder.append(" JOIN edificio.facultad facultad");
		queryBuilder.append(" LEFT JOIN aula.tipoAula tipoAula");
		queryBuilder.append(" WHERE 1=1");

		Map<String, Object> parametros = new HashMap<>();

		if (Objects.nonNull(filtroAulaDTO.getListaIdFacultad()) && !filtroAulaDTO.getListaIdFacultad().isEmpty()) {
			queryBuilder.append(" AND facultad.idFacultad IN (:listaIdFacultad)");
			parametros.put("listaIdFacultad", filtroAulaDTO.getListaIdFacultad());
		}
		if (Objects.nonNull(filtroAulaDTO.getListaIdEdificio()) && !filtroAulaDTO.getListaIdEdificio().isEmpty()) {
			queryBuilder.append(" AND edificio.idEdificio IN (:listaIdEdificio)");
			parametros.put("listaIdEdificio", filtroAulaDTO.getListaIdEdificio());
		}
		if (Objects.nonNull(filtroAulaDTO.getListaIdTipoAula()) && !filtroAulaDTO.getListaIdTipoAula().isEmpty()) {
			queryBuilder.append(" AND tipoAula.idTipoAula IN (:listaIdTipoAula)");
			parametros.put("listaIdTipoAula", filtroAulaDTO.getListaIdTipoAula());
		}
		if (Objects.nonNull(filtroAulaDTO.getNumeroAula())) {
			queryBuilder.append(" AND aula.numeroAula =:numeroAula");
			parametros.put("numeroAula", filtroAulaDTO.getNumeroAula());
		}
		if (Objects.nonNull(filtroAulaDTO.getEstado())) {
			queryBuilder.append(" AND aula.estado =:estado");
			parametros.put("estado", filtroAulaDTO.getEstado());
		}
		if (Objects.nonNull(filtroAulaDTO.getCapacidad())) {
			queryBuilder.append(" AND aula.capacidad =:capacidad");
			parametros.put("capacidad", filtroAulaDTO.getCapacidad());
		}
		queryBuilder.append(" ORDER BY facultad.abreviatura asc, edificio.nombre asc ");

		// Realiza la consulta paginada
		TypedQuery<AulaDTO> typedQuery = entityManager.createQuery(queryBuilder.toString(), AulaDTO.class);

		if (Objects.nonNull(pageable)) {
			typedQuery.setFirstResult((int) pageable.getOffset());
			typedQuery.setMaxResults(pageable.getPageSize());
		}

		// Establece parámetros en la consulta
		for (Map.Entry<String, Object> entry : parametros.entrySet()) {
			typedQuery.setParameter(entry.getKey(), entry.getValue());
		}

		// Si pageable es nulo, entonces retornar todos los resultados sin paginación
		if (Objects.isNull(pageable)) {
			return new PageImpl<>(typedQuery.getResultList());
		} else {
			return new PageImpl<>(typedQuery.getResultList(), pageable, contarAulasConsultadas(filtroAulaDTO));
		}
	}

	private Long contarAulasConsultadas(FiltroAulaDTO filtroAulaDTO) {

		// Construcción de la consulta con StringBuilder
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(" SELECT COUNT(aula)");
		queryBuilder.append(" FROM AulaEntity aula");
		queryBuilder.append(" JOIN aula.edificio edificio");
		queryBuilder.append(" JOIN edificio.facultad facultad");
		queryBuilder.append(" LEFT JOIN aula.tipoAula tipoAula");
		queryBuilder.append(" WHERE 1=1");

		Map<String, Object> parametros = new HashMap<>();

		if (Objects.nonNull(filtroAulaDTO.getListaIdFacultad()) && !filtroAulaDTO.getListaIdFacultad().isEmpty()) {
			queryBuilder.append(" AND facultad.idFacultad IN (:listaIdFacultad)");
			parametros.put("listaIdFacultad", filtroAulaDTO.getListaIdFacultad());
		}
		if (Objects.nonNull(filtroAulaDTO.getListaIdEdificio()) && !filtroAulaDTO.getListaIdEdificio().isEmpty()) {
			queryBuilder.append(" AND edificio.idEdificio IN (:listaIdEdificio)");
			parametros.put("listaIdEdificio", filtroAulaDTO.getListaIdEdificio());
		}
		if (Objects.nonNull(filtroAulaDTO.getListaIdTipoAula()) && !filtroAulaDTO.getListaIdTipoAula().isEmpty()) {
			queryBuilder.append(" AND tipoAula.idTipoAula IN (:listaIdTipoAula)");
			parametros.put("listaIdTipoAula", filtroAulaDTO.getListaIdTipoAula());
		}
		if (Objects.nonNull(filtroAulaDTO.getNumeroAula())) {
			queryBuilder.append(" AND aula.numeroAula =:numeroAula");
			parametros.put("numeroAula", filtroAulaDTO.getNumeroAula());
		}
		if (Objects.nonNull(filtroAulaDTO.getEstado())) {
			queryBuilder.append(" AND aula.estado =:estado");
			parametros.put("estado", filtroAulaDTO.getEstado());
		}
		if (Objects.nonNull(filtroAulaDTO.getCapacidad())) {
			queryBuilder.append(" AND aula.capacidad =:capacidad");
			parametros.put("capacidad", filtroAulaDTO.getCapacidad());
		}
		queryBuilder.append(" ORDER BY facultad.abreviatura asc, edificio.nombre asc ");

		// Realiza la consulta para contar
		TypedQuery<Long> countQuery = entityManager.createQuery(queryBuilder.toString(), Long.class);

		// Establece parámetros en la consulta
		for (Map.Entry<String, Object> entry : parametros.entrySet()) {
			countQuery.setParameter(entry.getKey(), entry.getValue());
		}

		return countQuery.getSingleResult();
	}

	@Override
	public List<TipoAula> consultarTipoAulasPorIdEdificio(List<Long> lstIdEdificio) {		
		List<TipoAulaEntity> tipoAulaEntities = this.aulaRepositoryInt.consultarTipoAulasPorIdEdificio(lstIdEdificio);
		return this.modelMapper.map(tipoAulaEntities, new TypeToken<List<TipoAula>>() {
		}.getType());
	}
}