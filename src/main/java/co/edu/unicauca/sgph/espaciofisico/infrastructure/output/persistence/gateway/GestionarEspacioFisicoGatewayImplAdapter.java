package co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.gateway;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.repository.TipoEspacioFisicoRepositoryInt;

@Service
public class GestionarEspacioFisicoGatewayImplAdapter implements GestionarEspacioFisicoGatewayIntPort {

	@PersistenceContext
	private EntityManager entityManager;

	private EspacioFisicoRepositoryInt espacioFisicoRepositoryInt;
	private TipoEspacioFisicoRepositoryInt tipoEspacioFisicoRepositoryInt;
	private ModelMapper modelMapper;

	public GestionarEspacioFisicoGatewayImplAdapter(EspacioFisicoRepositoryInt espacioFisicoRepositoryInt,
			TipoEspacioFisicoRepositoryInt tipoEspacioFisicoRepositoryInt, ModelMapper modelMapper) {
		this.espacioFisicoRepositoryInt = espacioFisicoRepositoryInt;
		this.tipoEspacioFisicoRepositoryInt=tipoEspacioFisicoRepositoryInt;
		this.modelMapper = modelMapper;
	}

	@Override
	public EspacioFisico guardarEspacioFisico(EspacioFisico espacioFisico) {
		EspacioFisicoEntity espacioFisicoEntity = this.espacioFisicoRepositoryInt
				.save(this.modelMapper.map(espacioFisico, EspacioFisicoEntity.class));
		return this.modelMapper.map(espacioFisicoEntity, EspacioFisico.class);
	}

	/** 
	 * @see co.edu.unicauca.sgph.espaciofisico.aplication.output.GestionarEspacioFisicoGatewayIntPort#consultarEspacioFisicoPorIdEspacioFisico(java.lang.Long)
	 */
	@Override
	public EspacioFisico consultarEspacioFisicoPorIdEspacioFisico(Long idEspacioFisico) {
		return this.modelMapper.map(this.espacioFisicoRepositoryInt.findByIdEspacioFisico(idEspacioFisico),
				EspacioFisico.class);
	}
	
	/** 
	 * @see co.edu.unicauca.sgph.espaciofisico.aplication.output.GestionarEspacioFisicoGatewayIntPort#consultarEspacioFisicoHorarioPorIdCurso(java.lang.Long)
	 */
	@Override
	public List<String> consultarEspacioFisicoHorarioPorIdCurso(Long idCurso) {
		return this.espacioFisicoRepositoryInt.consultarEspacioFisicoHorarioPorIdCurso(idCurso);
	}

	/** 
	 * @see co.edu.unicauca.sgph.espaciofisico.aplication.output.GestionarEspacioFisicoGatewayIntPort#consultarEspaciosFisicos(co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTORequest.FiltroEspacioFisicoDTO)
	 */
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
		queryBuilder.append(" espacioFisico.ubicacion, espacioFisico.edificio, ");
		queryBuilder.append(" tipoEspacioFisico.tipo, espacioFisico.salon,");
		queryBuilder.append(" espacioFisico.capacidad, espacioFisico.estado, espacioFisico.idEspacioFisico)");
		queryBuilder.append(" FROM EspacioFisicoEntity espacioFisico");
		queryBuilder.append(" LEFT JOIN espacioFisico.tipoEspacioFisico tipoEspacioFisico");
		queryBuilder.append(" WHERE 1=1");

		Map<String, Object> parametros = new HashMap<>();

		if (Objects.nonNull(filtroEspacioFisicoDTO.getListaUbicacion())
				&& !filtroEspacioFisicoDTO.getListaUbicacion().isEmpty()) {
			queryBuilder.append(" AND espacioFisico.ubicacion IN (:listaUbicacion)");
			parametros.put("listaUbicacion", filtroEspacioFisicoDTO.getListaUbicacion());
		}
		if (Objects.nonNull(filtroEspacioFisicoDTO.getListaEdificio())
				&& !filtroEspacioFisicoDTO.getListaEdificio().isEmpty()) {
			queryBuilder.append(" AND espacioFisico.edificio IN (:listaEdificio)");
			parametros.put("listaEdificio", filtroEspacioFisicoDTO.getListaEdificio());
		}
		if (Objects.nonNull(filtroEspacioFisicoDTO.getListaIdTipoEspacioFisico())
				&& !filtroEspacioFisicoDTO.getListaIdTipoEspacioFisico().isEmpty()) {
			queryBuilder.append(" AND tipoEspacioFisico.idTipoEspacioFisico IN (:listaIdTipoEspacioFisico)");
			parametros.put("listaIdTipoEspacioFisico", filtroEspacioFisicoDTO.getListaIdTipoEspacioFisico());
		}
		if (Objects.nonNull(filtroEspacioFisicoDTO.getSalon())) {
			queryBuilder.append(" AND espacioFisico.salon =:salon");
			parametros.put("salon", filtroEspacioFisicoDTO.getSalon());
		}
		if (Objects.nonNull(filtroEspacioFisicoDTO.getEstado())) {
			queryBuilder.append(" AND espacioFisico.estado =:estado");
			parametros.put("estado", filtroEspacioFisicoDTO.getEstado());
		}
		if (Objects.nonNull(filtroEspacioFisicoDTO.getCapacidad())) {
			queryBuilder.append(" AND espacioFisico.capacidad =:capacidad");
			parametros.put("capacidad", filtroEspacioFisicoDTO.getCapacidad());
		}
		queryBuilder.append(" ORDER BY espacioFisico.ubicacion asc, espacioFisico.edificio asc ");

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
		queryBuilder.append(" LEFT JOIN espacioFisico.tipoEspacioFisico tipoEspacioFisico");
		queryBuilder.append(" WHERE 1=1");

		Map<String, Object> parametros = new HashMap<>();

		if (Objects.nonNull(filtroEspacioFisicoDTO.getListaUbicacion())
				&& !filtroEspacioFisicoDTO.getListaUbicacion().isEmpty()) {
			queryBuilder.append(" AND espacioFisico.ubicacion IN (:listaUbicacion)");
			parametros.put("listaUbicacion", filtroEspacioFisicoDTO.getListaUbicacion());
		}
		if (Objects.nonNull(filtroEspacioFisicoDTO.getListaEdificio())
				&& !filtroEspacioFisicoDTO.getListaEdificio().isEmpty()) {
			queryBuilder.append(" AND espacioFisico.edificio IN (:listaEdificio)");
			parametros.put("listaEdificio", filtroEspacioFisicoDTO.getListaEdificio());
		}
		if (Objects.nonNull(filtroEspacioFisicoDTO.getListaIdTipoEspacioFisico())
				&& !filtroEspacioFisicoDTO.getListaIdTipoEspacioFisico().isEmpty()) {
			queryBuilder.append(" AND tipoEspacioFisico.idTipoEspacioFisico IN (:listaIdTipoEspacioFisico)");
			parametros.put("listaIdTipoEspacioFisico", filtroEspacioFisicoDTO.getListaIdTipoEspacioFisico());
		}
		if (Objects.nonNull(filtroEspacioFisicoDTO.getSalon())) {
			queryBuilder.append(" AND espacioFisico.salon =:salon");
			parametros.put("salon", filtroEspacioFisicoDTO.getSalon());
		}
		if (Objects.nonNull(filtroEspacioFisicoDTO.getEstado())) {
			queryBuilder.append(" AND espacioFisico.estado =:estado");
			parametros.put("estado", filtroEspacioFisicoDTO.getEstado());
		}
		if (Objects.nonNull(filtroEspacioFisicoDTO.getCapacidad())) {
			queryBuilder.append(" AND espacioFisico.capacidad =:capacidad");
			parametros.put("capacidad", filtroEspacioFisicoDTO.getCapacidad());
		}
		queryBuilder.append(" ORDER BY espacioFisico.ubicacion asc, espacioFisico.edificio asc ");

		// Realiza la consulta para contar
		TypedQuery<Long> countQuery = entityManager.createQuery(queryBuilder.toString(), Long.class);

		// Establece parámetros en la consulta
		for (Map.Entry<String, Object> entry : parametros.entrySet()) {
			countQuery.setParameter(entry.getKey(), entry.getValue());
		}

		return countQuery.getSingleResult();
	}

	
	/** 
	 * @see co.edu.unicauca.sgph.espaciofisico.aplication.output.GestionarEspacioFisicoGatewayIntPort#consultarTiposEspaciosFisicosPorEdificio(java.util.List)
	 */
	@Override
	public List<TipoEspacioFisico> consultarTiposEspaciosFisicosPorEdificio(List<String> lstEdificio) {
		List<TipoEspacioFisicoEntity> lstTipoEspacioFisicoEntity = this.tipoEspacioFisicoRepositoryInt
				.consultarTiposEspaciosFisicosPorEdificio(lstEdificio);
		return this.modelMapper.map(lstTipoEspacioFisicoEntity, new TypeToken<List<TipoEspacioFisico>>() {
		}.getType());
	}

	/** 
	 * @see co.edu.unicauca.sgph.espaciofisico.aplication.output.GestionarEspacioFisicoGatewayIntPort#consultarEdificios()
	 */
	@Override
	public List<String> consultarEdificios() {
		return this.espacioFisicoRepositoryInt.consultarEdificios();
	}

	/** 
	 * @see co.edu.unicauca.sgph.espaciofisico.aplication.output.GestionarEspacioFisicoGatewayIntPort#consultarUbicaciones()
	 */
	@Override
	public List<String> consultarUbicaciones() {
		return this.espacioFisicoRepositoryInt.consultarUbicaciones();

	}

	/** 
	 * @see co.edu.unicauca.sgph.espaciofisico.aplication.output.GestionarEspacioFisicoGatewayIntPort#consultarEdificiosPorUbicacion(java.util.List)
	 */
	@Override
	public List<String> consultarEdificiosPorUbicacion(List<String> lstUbicacion) {
		if(!lstUbicacion.isEmpty()) {
			return this.espacioFisicoRepositoryInt.consultarEdificiosPorUbicacion(lstUbicacion);
		}else {
			return new ArrayList<>();
		}
		
	}

	@Override
	public List<EspacioFisicoDTO> obtenerEspaciosFisicosPorAgrupadorId(Long idAgrupador) {
		List<EspacioFisicoEntity> espaciosFisicos = this.espacioFisicoRepositoryInt.findAll();
		List<EspacioFisicoEntity> espaciosFisicosAsignados = espaciosFisicos.stream()
				.filter(espacio -> espacio.getAgrupadores().stream()
						.anyMatch(agrupador -> agrupador.getIdAgrupadorEspacioFisico().equals(idAgrupador))).collect(Collectors.toList());
	return espaciosFisicosAsignados.stream().map(this::mapEspacioFisico).collect(Collectors.toList());
	}

	@Override
	public List<EspacioFisicoDTO> obtenerEspaciosFisicosSinAsignarAAgrupadorId(Long idAgrupador) {
		List<EspacioFisicoEntity> espaciosFisicos = this.espacioFisicoRepositoryInt.findAll();
		List<EspacioFisicoEntity> espaciosFisicosSinAsignar = espaciosFisicos.stream()
				.filter(espacio -> espacio.getAgrupadores().isEmpty())
				.collect(Collectors.toList());
		return espaciosFisicosSinAsignar.stream().map(this::mapEspacioFisico).collect(Collectors.toList());
	}

	private EspacioFisicoDTO mapEspacioFisico(EspacioFisicoEntity entidad) {
		EspacioFisicoDTO dto = new EspacioFisicoDTO();
		dto.setIdEspacioFisico(entidad.getIdEspacioFisico());
		dto.setSalon(entidad.getSalon());
		return dto;
	}
}