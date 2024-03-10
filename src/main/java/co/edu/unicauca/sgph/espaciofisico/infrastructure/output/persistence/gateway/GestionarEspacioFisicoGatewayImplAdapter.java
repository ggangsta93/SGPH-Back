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

import co.edu.unicauca.sgph.espaciofisico.aplication.output.GestionarEspacioFisicoGatewayIntPort;
import co.edu.unicauca.sgph.espaciofisico.domain.model.EspacioFisico;
import co.edu.unicauca.sgph.espaciofisico.domain.model.TipoEspacioFisico;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTORequest.FiltroEspacioFisicoDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.EspacioFisicoDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.EspacioFisicoEntity;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.TipoEspacioFisicoEntity;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.repository.EspacioFisicoRepositoryInt;

@Service
public class GestionarEspacioFisicoGatewayImplAdapter implements GestionarEspacioFisicoGatewayIntPort {

	@PersistenceContext
	private EntityManager entityManager;

	private EspacioFisicoRepositoryInt espacioFisicoRepositoryInt;
	private ModelMapper modelMapper;

	public GestionarEspacioFisicoGatewayImplAdapter(EspacioFisicoRepositoryInt espacioFisicoRepositoryInt,
			ModelMapper modelMapper) {
		this.espacioFisicoRepositoryInt = espacioFisicoRepositoryInt;
		this.modelMapper = modelMapper;
	}

	@Override
	public EspacioFisico consultarEspacioFisicoPorIdEspacioFisico(Long idEspacioFisico) {
		return this.modelMapper.map(this.espacioFisicoRepositoryInt.findByIdEspacioFisico(idEspacioFisico),
				EspacioFisico.class);
	}

	@Override
	public EspacioFisico guardarEspacioFisico(EspacioFisico espacioFisico) {
		EspacioFisicoEntity espacioFisicoEntity = this.espacioFisicoRepositoryInt
				.save(this.modelMapper.map(espacioFisico, EspacioFisicoEntity.class));
		return this.modelMapper.map(espacioFisicoEntity, EspacioFisico.class);
	}

	@Override
	public List<String> consultarEspacioFisicoHorarioPorIdCurso(Long idCurso) {
		return this.espacioFisicoRepositoryInt.consultarEspacioFisicoHorarioPorIdCurso(idCurso);
	}

	@Override
	public Page<EspacioFisicoDTO> consultarEspaciosFisicos(FiltroEspacioFisicoDTO filtroEspacioFisicoDTO) {
		PageRequest pageable = null;
		if (Objects.nonNull(filtroEspacioFisicoDTO.getPagina())
				&& Objects.nonNull(filtroEspacioFisicoDTO.getRegistrosPorPagina())) {
			// Configuración de la paginación
			pageable = PageRequest.of(filtroEspacioFisicoDTO.getPagina(),
					filtroEspacioFisicoDTO.getRegistrosPorPagina());
		}

		// Construcción de la consulta con StringBuilder
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(" SELECT NEW co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.EspacioFisicoDTO(");
		queryBuilder.append(" facultad.abreviatura, edificio.nombre, ");
		queryBuilder.append(" tipoEspacioFisico.tipo, espacioFisico.numeroEspacioFisico,");
		queryBuilder.append(" espacioFisico.capacidad, espacioFisico.estado, espacioFisico.idEspacioFisico)");
		queryBuilder.append(" FROM EspacioFisicoEntity espacioFisico");
		queryBuilder.append(" JOIN espacioFisico.edificio edificio");
		queryBuilder.append(" JOIN edificio.facultad facultad");
		queryBuilder.append(" LEFT JOIN espacioFisico.tipoEspacioFisico tipoEspacioFisico");
		queryBuilder.append(" WHERE 1=1");

		Map<String, Object> parametros = new HashMap<>();

		if (Objects.nonNull(filtroEspacioFisicoDTO.getListaIdFacultad())
				&& !filtroEspacioFisicoDTO.getListaIdFacultad().isEmpty()) {
			queryBuilder.append(" AND facultad.idFacultad IN (:listaIdFacultad)");
			parametros.put("listaIdFacultad", filtroEspacioFisicoDTO.getListaIdFacultad());
		}
		if (Objects.nonNull(filtroEspacioFisicoDTO.getListaIdEdificio())
				&& !filtroEspacioFisicoDTO.getListaIdEdificio().isEmpty()) {
			queryBuilder.append(" AND edificio.idEdificio IN (:listaIdEdificio)");
			parametros.put("listaIdEdificio", filtroEspacioFisicoDTO.getListaIdEdificio());
		}
		if (Objects.nonNull(filtroEspacioFisicoDTO.getListaIdTipoEspacioFisico())
				&& !filtroEspacioFisicoDTO.getListaIdTipoEspacioFisico().isEmpty()) {
			queryBuilder.append(" AND tipoEspacioFisico.idTipoEspacioFisico IN (:listaIdTipoEspacioFisico)");
			parametros.put("listaIdTipoEspacioFisico", filtroEspacioFisicoDTO.getListaIdTipoEspacioFisico());
		}
		if (Objects.nonNull(filtroEspacioFisicoDTO.getNumeroEspacioFisico())) {
			queryBuilder.append(" AND espacioFisico.numeroEspacioFisico =:numeroEspacioFisico");
			parametros.put("numeroEspacioFisico", filtroEspacioFisicoDTO.getNumeroEspacioFisico());
		}
		if (Objects.nonNull(filtroEspacioFisicoDTO.getEstado())) {
			queryBuilder.append(" AND espacioFisico.estado =:estado");
			parametros.put("estado", filtroEspacioFisicoDTO.getEstado());
		}
		if (Objects.nonNull(filtroEspacioFisicoDTO.getCapacidad())) {
			queryBuilder.append(" AND espacioFisico.capacidad =:capacidad");
			parametros.put("capacidad", filtroEspacioFisicoDTO.getCapacidad());
		}
		queryBuilder.append(" ORDER BY facultad.abreviatura asc, edificio.nombre asc ");

		// Realiza la consulta paginada
		TypedQuery<EspacioFisicoDTO> typedQuery = entityManager.createQuery(queryBuilder.toString(),
				EspacioFisicoDTO.class);

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
			return new PageImpl<>(typedQuery.getResultList(), pageable,
					contarEspaciosFisicosConsultadas(filtroEspacioFisicoDTO));
		}
	}

	private Long contarEspaciosFisicosConsultadas(FiltroEspacioFisicoDTO filtroEspacioFisicoDTO) {

		// Construcción de la consulta con StringBuilder
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(" SELECT COUNT(espacioFisico)");
		queryBuilder.append(" FROM EspacioFisicoEntity espacioFisico");
		queryBuilder.append(" JOIN espacioFisico.edificio edificio");
		queryBuilder.append(" JOIN edificio.facultad facultad");
		queryBuilder.append(" LEFT JOIN espacioFisico.tipoEspacioFisico tipoEspacioFisico");
		queryBuilder.append(" WHERE 1=1");

		Map<String, Object> parametros = new HashMap<>();

		if (Objects.nonNull(filtroEspacioFisicoDTO.getListaIdFacultad())
				&& !filtroEspacioFisicoDTO.getListaIdFacultad().isEmpty()) {
			queryBuilder.append(" AND facultad.idFacultad IN (:listaIdFacultad)");
			parametros.put("listaIdFacultad", filtroEspacioFisicoDTO.getListaIdFacultad());
		}
		if (Objects.nonNull(filtroEspacioFisicoDTO.getListaIdEdificio())
				&& !filtroEspacioFisicoDTO.getListaIdEdificio().isEmpty()) {
			queryBuilder.append(" AND edificio.idEdificio IN (:listaIdEdificio)");
			parametros.put("listaIdEdificio", filtroEspacioFisicoDTO.getListaIdEdificio());
		}
		if (Objects.nonNull(filtroEspacioFisicoDTO.getListaIdTipoEspacioFisico())
				&& !filtroEspacioFisicoDTO.getListaIdTipoEspacioFisico().isEmpty()) {
			queryBuilder.append(" AND tipoEspacioFisico.idTipoEspacioFisico IN (:listaIdTipoEspacioFisico)");
			parametros.put("listaIdTipoEspacioFisico", filtroEspacioFisicoDTO.getListaIdTipoEspacioFisico());
		}
		if (Objects.nonNull(filtroEspacioFisicoDTO.getNumeroEspacioFisico())) {
			queryBuilder.append(" AND espacioFisico.numeroEspacioFisico =:numeroEspacioFisico");
			parametros.put("numeroEspacioFisico", filtroEspacioFisicoDTO.getNumeroEspacioFisico());
		}
		if (Objects.nonNull(filtroEspacioFisicoDTO.getEstado())) {
			queryBuilder.append(" AND espacioFisico.estado =:estado");
			parametros.put("estado", filtroEspacioFisicoDTO.getEstado());
		}
		if (Objects.nonNull(filtroEspacioFisicoDTO.getCapacidad())) {
			queryBuilder.append(" AND espacioFisico.capacidad =:capacidad");
			parametros.put("capacidad", filtroEspacioFisicoDTO.getCapacidad());
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
	public List<TipoEspacioFisico> consultarTiposEspaciosFisicosPorIdEdificio(List<Long> lstIdEdificio) {
		List<TipoEspacioFisicoEntity> lstTipoEspacioFisicoEntity = this.espacioFisicoRepositoryInt
				.consultarTiposEspaciosFisicosPorIdEdificio(lstIdEdificio);
		return this.modelMapper.map(lstTipoEspacioFisicoEntity, new TypeToken<List<TipoEspacioFisico>>() {
		}.getType());
	}

}