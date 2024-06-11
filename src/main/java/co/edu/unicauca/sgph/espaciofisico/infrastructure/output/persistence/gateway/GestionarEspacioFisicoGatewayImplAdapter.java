package co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.gateway;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import co.edu.unicauca.sgph.espaciofisico.aplication.output.GestionarEspacioFisicoGatewayIntPort;
import co.edu.unicauca.sgph.espaciofisico.domain.model.Edificio;
import co.edu.unicauca.sgph.espaciofisico.domain.model.EspacioFisico;
import co.edu.unicauca.sgph.espaciofisico.domain.model.TipoEspacioFisico;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTORequest.AsignacionEspacioFisicoDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTORequest.EspacioFisicoInDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTORequest.FiltroEspacioFisicoAgrupadorDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTORequest.FiltroEspacioFisicoDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.EspacioFisicoDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.MensajeOutDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.RecursoOutDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.AgrupadorEspacioFisicoEntity;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.EspacioFisicoEntity;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.EstadoEspacioFisicoEnum;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.RecursoEspacioFisicoEntity;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.RecursoFisicoEntity;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.TipoEspacioFisicoEntity;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.UbicacionEntity;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.repository.AgrupadorEspacioFisicoRepositoryInt;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.repository.EspacioFisicoRepositoryInt;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.repository.RecursoEspacioFisicoRepositoryInt;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.repository.RecursoFisicoRepositoryInt;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.repository.TipoEspacioFisicoRepositoryInt;

@Service
public class GestionarEspacioFisicoGatewayImplAdapter implements GestionarEspacioFisicoGatewayIntPort {

	@PersistenceContext
	private EntityManager entityManager;

	private EspacioFisicoRepositoryInt espacioFisicoRepositoryInt;
	private TipoEspacioFisicoRepositoryInt tipoEspacioFisicoRepositoryInt;
	private RecursoFisicoRepositoryInt recursoFisicoRepositoryInt;
	private ModelMapper modelMapper;
	@Autowired
	private RecursoEspacioFisicoRepositoryInt recursoEspacioFisicoRepositoryInt;
	@Autowired
	private AgrupadorEspacioFisicoRepositoryInt agrupadorEspacioFisicoRepositoryInt;

	public GestionarEspacioFisicoGatewayImplAdapter(EspacioFisicoRepositoryInt espacioFisicoRepositoryInt,
													TipoEspacioFisicoRepositoryInt tipoEspacioFisicoRepositoryInt, ModelMapper modelMapper, RecursoFisicoRepositoryInt recursoFisicoRepositoryInt) {
		this.espacioFisicoRepositoryInt = espacioFisicoRepositoryInt;
		this.tipoEspacioFisicoRepositoryInt = tipoEspacioFisicoRepositoryInt;
		this.modelMapper = modelMapper;
		this.recursoFisicoRepositoryInt = recursoFisicoRepositoryInt;
	}

	@Override
	public EspacioFisico guardarEspacioFisico(EspacioFisico espacioFisico) {
		espacioFisico.setEdificio(null);
		EspacioFisicoEntity espacioFisicoEntity = this.espacioFisicoRepositoryInt
				.save(this.modelMapper.map(espacioFisico, EspacioFisicoEntity.class));
		return this.modelMapper.map(espacioFisicoEntity, EspacioFisico.class);
	}

	/**
	 * @see co.edu.unicauca.sgph.espaciofisico.aplication.output.GestionarEspacioFisicoGatewayIntPort#consultarEspacioFisicoPorIdEspacioFisico(java.lang.Long)
	 */
	@Override
	public EspacioFisico consultarEspacioFisicoPorIdEspacioFisico(Long idEspacioFisico) {
		return this.modelMapper.map(this.espacioFisicoRepositoryInt.findByIdEspacioFisico(idEspacioFisico), EspacioFisico.class);
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
		queryBuilder.append(
				" SELECT NEW co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.EspacioFisicoDTO(");
		queryBuilder.append(" espacioFisico.ubicacion.idUbicacion, espacioFisico.edificio.idEdificio, ");
		queryBuilder.append(" tipoEspacioFisico.tipo, espacioFisico.salon,");
		queryBuilder.append(" espacioFisico.capacidad, espacioFisico.estado, espacioFisico.idEspacioFisico, ");
		queryBuilder.append(" ubicacion.nombre, edificio.nombre )");
		queryBuilder.append(" FROM EspacioFisicoEntity espacioFisico");
		queryBuilder.append(" LEFT JOIN espacioFisico.tipoEspacioFisico tipoEspacioFisico");
		queryBuilder.append(" LEFT JOIN espacioFisico.ubicacion ubicacion");
		queryBuilder.append(" LEFT JOIN espacioFisico.edificio edificio");
		queryBuilder.append(" WHERE 1=1");

		Map<String, Object> parametros = new HashMap<>();

		if (Objects.nonNull(filtroEspacioFisicoDTO.getListaIdUbicacion())
				&& !filtroEspacioFisicoDTO.getListaIdUbicacion().isEmpty()) {
			queryBuilder.append(" AND espacioFisico.ubicacion.idUbicacion IN (:listaIdUbicacion)");
			parametros.put("listaIdUbicacion", filtroEspacioFisicoDTO.getListaIdUbicacion());
		}
		if (Objects.nonNull(filtroEspacioFisicoDTO.getListaIdEdificio())
				&& !filtroEspacioFisicoDTO.getListaIdEdificio().isEmpty()) {
			queryBuilder.append(" AND espacioFisico.edificio.idEdificio IN (:listaIdEdificio)");
			parametros.put("listaIdEdificio", filtroEspacioFisicoDTO.getListaIdEdificio());
		}
		if (Objects.nonNull(filtroEspacioFisicoDTO.getListaIdTipoEspacioFisico())
				&& !filtroEspacioFisicoDTO.getListaIdTipoEspacioFisico().isEmpty()) {
			queryBuilder.append(" AND tipoEspacioFisico.idTipoEspacioFisico IN (:listaIdTipoEspacioFisico)");
			parametros.put("listaIdTipoEspacioFisico", filtroEspacioFisicoDTO.getListaIdTipoEspacioFisico());
		}
		if (Objects.nonNull(filtroEspacioFisicoDTO.getSalon()) && !filtroEspacioFisicoDTO.getSalon().isEmpty()) {
			queryBuilder.append("AND espacioFisico.salon LIKE :salon ");
			parametros.put("salon", "%" + filtroEspacioFisicoDTO.getSalon().replaceAll("\\s+", " ").trim() + "%");
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

		if (Objects.nonNull(filtroEspacioFisicoDTO.getListaIdUbicacion())
				&& !filtroEspacioFisicoDTO.getListaIdUbicacion().isEmpty()) {
			queryBuilder.append(" AND espacioFisico.ubicacion.idUbicacion IN (:listaIdUbicacion)");
			parametros.put("listaIdUbicacion", filtroEspacioFisicoDTO.getListaIdUbicacion());
		}
		if (Objects.nonNull(filtroEspacioFisicoDTO.getListaIdEdificio())
				&& !filtroEspacioFisicoDTO.getListaIdEdificio().isEmpty()) {
			queryBuilder.append(" AND espacioFisico.edificio.idEdificio IN (:listaIdEdificio)");
			parametros.put("listaIdEdificio", filtroEspacioFisicoDTO.getListaIdEdificio());
		}
		if (Objects.nonNull(filtroEspacioFisicoDTO.getListaIdTipoEspacioFisico())
				&& !filtroEspacioFisicoDTO.getListaIdTipoEspacioFisico().isEmpty()) {
			queryBuilder.append(" AND tipoEspacioFisico.idTipoEspacioFisico IN (:listaIdTipoEspacioFisico)");
			parametros.put("listaIdTipoEspacioFisico", filtroEspacioFisicoDTO.getListaIdTipoEspacioFisico());
		}
		if (Objects.nonNull(filtroEspacioFisicoDTO.getSalon()) && !filtroEspacioFisicoDTO.getSalon().isEmpty()) {
			queryBuilder.append("AND espacioFisico.salon LIKE :salon ");
			parametros.put("salon", "%" + filtroEspacioFisicoDTO.getSalon().replaceAll("\\s+", " ").trim() + "%");
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
	public List<TipoEspacioFisico> consultarTiposEspaciosFisicosPorEdificio(List<Long> lstIdEdificio) {

		List<TipoEspacioFisicoEntity> lstTipoEspacioFisicoEntity = this.tipoEspacioFisicoRepositoryInt
				.consultarTiposEspaciosFisicosPorEdificio(lstIdEdificio);
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
	public List<Edificio> consultarEdificiosPorUbicacion(List<Long> lstIdUbicacion) {
		if (!lstIdUbicacion.isEmpty()) {
			return this.modelMapper.map(this.espacioFisicoRepositoryInt.consultarEdificiosPorUbicacion(lstIdUbicacion), new TypeToken<List<EspacioFisico>>() {
			}.getType());
		} else {
			return new ArrayList<>();
		}
	}

	/**
	 * @see co.edu.unicauca.sgph.espaciofisico.aplication.output.GestionarEspacioFisicoGatewayIntPort#consultarCapacidadEstadoYSalonPorListaIdEspacioFisico(java.util.List)
	 */
	@Override
	public List<EspacioFisico> consultarCapacidadEstadoYSalonPorListaIdEspacioFisico(List<Long> lstIdEspacioFisico) {
		List<EspacioFisicoEntity> lstEspacioFisicoEntity = this.espacioFisicoRepositoryInt
				.consultarCapacidadEstadoYSalonPorListaIdEspacioFisico(lstIdEspacioFisico);

		return this.modelMapper.map(lstEspacioFisicoEntity, new TypeToken<List<EspacioFisico>>() {
		}.getType());
	}
		
	/**
	 * @see co.edu.unicauca.sgph.espaciofisico.aplication.output.GestionarEspacioFisicoGatewayIntPort#consultarEspacioFisicoPrincipalFranjaPorIdHorario(java.lang.Long)
	 */
	@Override
	public EspacioFisico consultarEspacioFisicoPrincipalFranjaPorIdHorario(Long idHorario) {
		List<EspacioFisicoEntity> lstEspacioFisicoEntity = this.espacioFisicoRepositoryInt
				.consultarEspaciosFisicosCursoPorIdHorario(idHorario);
		/*
		 * TODO: Se filtrará aquel espacio físico que sea principal, por ahora se toma
		 * el primero (Está pendiente implementar el campo principal en la entidad
		 * HorarioEspacioEntity)
		 */
		EspacioFisicoEntity espacioFisicoEntity = lstEspacioFisicoEntity.get(0);

		return this.modelMapper.map(espacioFisicoEntity, EspacioFisico.class);
	}

	@Override
	public List<EspacioFisicoDTO> obtenerEspaciosFisicosPorAgrupadorId(Long idAgrupador) {
		List<EspacioFisicoEntity> espaciosFisicos = this.espacioFisicoRepositoryInt.findAll();
		List<EspacioFisicoEntity> espaciosFisicosAsignados = espaciosFisicos.stream()
				.filter(espacio -> espacio.getAgrupadores().stream()
						.anyMatch(agrupador -> agrupador.getIdAgrupadorEspacioFisico().equals(idAgrupador)))
				.collect(Collectors.toList());
		return espaciosFisicosAsignados.stream().map(this::mapEspacioFisico).collect(Collectors.toList());
	}

	@Override
	public List<EspacioFisicoDTO> obtenerEspaciosFisicosSinAsignarAAgrupadorId(Long idAgrupador) {
		List<EspacioFisicoEntity> espaciosFisicos = this.espacioFisicoRepositoryInt.findAll();
		List<EspacioFisicoEntity> espaciosFisicosSinAsignar = espaciosFisicos.stream()
				.filter(espacio -> espacio.getAgrupadores().isEmpty()).collect(Collectors.toList());
		return espaciosFisicosSinAsignar.stream().map(this::mapEspacioFisico).collect(Collectors.toList());
	}

	@Override
	public List<EspacioFisicoDTO> consultarEspaciosFisicosConFiltro(FiltroEspacioFisicoAgrupadorDTO filtro) {
		List<EspacioFisicoEntity> lista = this.espacioFisicoRepositoryInt
				.obtenerEspacioFisicoPorFiltro(filtro.getNombre(), filtro.getUbicacion(), filtro.getTipo());
		if (lista != null) {
			lista = filtrarEspaciosFisicosAgrupadosADiferenteAgrupadorId(lista, filtro.getIdAgrupador());
			return lista.stream().map(this::mapEspacioFisico).collect(Collectors.toList());
		} else {
			return null;
		}
	}

	@Override
	public MensajeOutDTO guardarAsignacion(AsignacionEspacioFisicoDTO asignacion) {
		this.eliminarAgrupadoresEspacioFisico(asignacion.getQuitados(), asignacion.getIdGrupo());
		this.agregarAgrupadosEspacioFisico(asignacion.getAgregados(), asignacion.getIdGrupo());
		MensajeOutDTO resultado = new MensajeOutDTO();
		resultado.setError(false);
		resultado.setDescripcion("Asignación guardada correctamente");
		return resultado;
	}

	@Override
	public List<RecursoOutDTO> obtenerListaRecursos() {
		return this.recursoFisicoRepositoryInt.findAll().stream().map(e -> new RecursoOutDTO(e.getIdRecursoFisico(), e.getNombre(), e.getDescripcion())).collect(Collectors.toList());
	}

	@Override
	public EspacioFisico guardarEspacioFisico(EspacioFisicoInDTO espacioFisicoInDTO) {
		EspacioFisicoEntity entidad = new EspacioFisicoEntity();
		if (espacioFisicoInDTO.getIdEspacioFisico() != null) {
			entidad.setIdEspacioFisico(espacioFisicoInDTO.getIdEspacioFisico());
		}
		entidad.setCapacidad(espacioFisicoInDTO.getCapacidad());
		entidad.setEstado(espacioFisicoInDTO.getEstado());
		entidad.setSalon(espacioFisicoInDTO.getSalon());
		UbicacionEntity ubicacion = new UbicacionEntity();
		ubicacion.setIdUbicacion(espacioFisicoInDTO.getIdUbicacion());
		entidad.setUbicacion(ubicacion);
		TipoEspacioFisicoEntity tipoEspacioFisico = new TipoEspacioFisicoEntity();
		tipoEspacioFisico.setIdTipoEspacioFisico(espacioFisicoInDTO.getIdTipoEspacioFisico());
		entidad.setTipoEspacioFisico(tipoEspacioFisico);
		entidad = this.espacioFisicoRepositoryInt.save(entidad);
		if (espacioFisicoInDTO.getSaveIdAgrupadores() != null) {
			entidad.setAgrupadores(
					espacioFisicoInDTO.getSaveIdAgrupadores().stream().map(g -> {
						AgrupadorEspacioFisicoEntity entidadG = new AgrupadorEspacioFisicoEntity();
						entidadG.setIdAgrupadorEspacioFisico(g);
						return entidadG;
					}).collect(Collectors.toList()));
		}
		this.agregarRecursosAEspacioFisico(espacioFisicoInDTO.getRecursos(), entidad);
		return this.modelMapper.map(entidad, EspacioFisico.class);
	}

	@Override
	public void activarInactivarEspacioFisico(Long id) {
		Optional<EspacioFisicoEntity> espacioFisicio = this.espacioFisicoRepositoryInt.findById(id);
		if (espacioFisicio.isPresent()) {
			EspacioFisicoEntity entidad = espacioFisicio.get();
			if (entidad.getEstado().equals(EstadoEspacioFisicoEnum.ACTIVO)) {
				entidad.setEstado(EstadoEspacioFisicoEnum.INACTIVO);
			} else {
				entidad.setEstado(EstadoEspacioFisicoEnum.ACTIVO);
			}
			this.espacioFisicoRepositoryInt.save(entidad);
		}
	}

	public void agregarRecursosAEspacioFisico(List<Long> recursos, EspacioFisicoEntity espacioFisico) {
		recursos.forEach(
				r -> {
					RecursoFisicoEntity recursoFisicoEntity = new RecursoFisicoEntity();
					recursoFisicoEntity.setIdRecursoFisico(r);
					RecursoEspacioFisicoEntity recursoEspacioFisicoEntity = new RecursoEspacioFisicoEntity();
					recursoEspacioFisicoEntity.setEspacioFisico(espacioFisico);
					recursoEspacioFisicoEntity.setRecursoFisico(recursoFisicoEntity);
					this.recursoEspacioFisicoRepositoryInt.save(recursoEspacioFisicoEntity);
				}
		);
	}

	private void eliminarAgrupadoresEspacioFisico(List<EspacioFisicoDTO> quitados, Long idGrupo) {
		quitados.forEach(q -> {
			Optional<EspacioFisicoEntity> qEntidad = this.espacioFisicoRepositoryInt.findById(q.getIdEspacioFisico());
			List<AgrupadorEspacioFisicoEntity> agrupadores = qEntidad.get().getAgrupadores();
			agrupadores = agrupadores.stream().filter(a -> !a.getIdAgrupadorEspacioFisico().equals(idGrupo))
					.collect(Collectors.toList());
			EspacioFisicoEntity espacioFisicoEntity = qEntidad.get();
			espacioFisicoEntity.setAgrupadores(agrupadores);
			this.espacioFisicoRepositoryInt.save(espacioFisicoEntity);
		});
	}

	private void agregarAgrupadosEspacioFisico(List<EspacioFisicoDTO> agregados, Long idGrupo) {
		agregados.forEach(q -> {
			Optional<EspacioFisicoEntity> qEntidad = this.espacioFisicoRepositoryInt.findById(q.getIdEspacioFisico());
			List<AgrupadorEspacioFisicoEntity> agrupadores = qEntidad.get().getAgrupadores();
			AgrupadorEspacioFisicoEntity nuevo = new AgrupadorEspacioFisicoEntity();
			nuevo.setIdAgrupadorEspacioFisico(idGrupo);
			agrupadores.add(nuevo);
			EspacioFisicoEntity espacioFisicoEntity = qEntidad.get();
			espacioFisicoEntity.setAgrupadores(agrupadores);
			this.espacioFisicoRepositoryInt.save(espacioFisicoEntity);
		});
	}

	private List<EspacioFisicoEntity> filtrarEspaciosFisicosAgrupadosADiferenteAgrupadorId(
			List<EspacioFisicoEntity> lista, Long idAgrupador) {
		return lista.stream().filter(a -> a.getAgrupadores().stream()
						.filter(b -> b.getIdAgrupadorEspacioFisico() == idAgrupador).collect(Collectors.toList()).isEmpty())
				.collect(Collectors.toList());
	}

	private EspacioFisicoDTO mapEspacioFisico(EspacioFisicoEntity entidad) {
		EspacioFisicoDTO dto = new EspacioFisicoDTO();
		dto.setIdEspacioFisico(entidad.getIdEspacioFisico());
		dto.setSalon(entidad.getSalon());
		return dto;
	}

}