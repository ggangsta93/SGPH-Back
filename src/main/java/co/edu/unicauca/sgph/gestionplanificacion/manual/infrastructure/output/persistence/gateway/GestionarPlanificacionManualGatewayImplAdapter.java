package co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.output.persistence.gateway;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import co.edu.unicauca.sgph.agrupador.aplication.output.GestionarAgrupadorEspacioFisicoGatewayIntPort;
import co.edu.unicauca.sgph.agrupador.domain.model.AgrupadorEspacioFisico;
import co.edu.unicauca.sgph.common.enums.DiaSemanaEnum;
import co.edu.unicauca.sgph.common.enums.EstadoCursoHorarioEnum;
import co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.output.GestionarPlanificacionManualGatewayIntPort;
import co.edu.unicauca.sgph.gestionplanificacion.manual.domain.model.FranjaHorariaBasicaDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTORequest.EliminarHorarioInDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTORequest.FiltroCursoPlanificacionDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.CursoPlanificacionOutDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.FormatoPresentacionFranjaHorariaCursoDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.FranjaHorariaCursoDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.FranjaHorariaDocenteDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.FranjaHorariaEspacioFisicoDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.InfoGeneralCursosPorProgramaDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.output.persistence.repository.PlanificacionManualRepositoryInt;
import co.edu.unicauca.sgph.horario.domain.model.Horario;
import co.edu.unicauca.sgph.periodoacademico.aplication.output.GestionarPeriodoAcademicoGatewayIntPort;
import co.edu.unicauca.sgph.seguridad.entity.UsuarioPrincipal;

@Service
public class GestionarPlanificacionManualGatewayImplAdapter implements GestionarPlanificacionManualGatewayIntPort {

	@PersistenceContext
	private EntityManager entityManager;

	private GestionarPeriodoAcademicoGatewayIntPort gestionarPeriodoAcademicoGatewayIntPort;
	private GestionarAgrupadorEspacioFisicoGatewayIntPort gestionarAgrupadorEspacioFisicoGatewayIntPort;
	
	private PlanificacionManualRepositoryInt planificacionManualRepositoryInt;
	private ModelMapper modelMapper;

	public GestionarPlanificacionManualGatewayImplAdapter(
			PlanificacionManualRepositoryInt planificacionManualRepositoryInt, ModelMapper modelMapper,
			GestionarPeriodoAcademicoGatewayIntPort gestionarPeriodoAcademicoGatewayIntPort,
			GestionarAgrupadorEspacioFisicoGatewayIntPort gestionarAgrupadorEspacioFisicoGatewayIntPort) {
		this.planificacionManualRepositoryInt = planificacionManualRepositoryInt;
		this.modelMapper = modelMapper;
		this.gestionarPeriodoAcademicoGatewayIntPort = gestionarPeriodoAcademicoGatewayIntPort;
		this.gestionarAgrupadorEspacioFisicoGatewayIntPort = gestionarAgrupadorEspacioFisicoGatewayIntPort;
	}
	
	/** 
	 * @see co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.output.GestionarPlanificacionManualGatewayIntPort#consultarCantidadHorasActualCurso(java.lang.Long)
	 */
	@Override
	public Long consultarCantidadHorasActualCurso(Long idCurso) {	
		Long cantidadHoras = this.planificacionManualRepositoryInt.consultarCantidadHorasActualCurso(idCurso);
		return Objects.isNull(cantidadHoras)? 0L:cantidadHoras;
	}	

	/**
	 * @see co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.output.GestionarPlanificacionManualGatewayIntPort#consultarCursosPlanificacionPorFiltro(co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTORequest.FiltroCursoPlanificacionDTO,
	 *      java.lang.Long)
	 */
	@Override
	public Page<CursoPlanificacionOutDTO> consultarCursosPlanificacionPorFiltro(
			FiltroCursoPlanificacionDTO filtroCursoPlanificacionDTO, Long idPeriodoAcademicoVigente) {
		// Configuración de la paginación
		PageRequest pageable = PageRequest.of(filtroCursoPlanificacionDTO.getPagina(),
				filtroCursoPlanificacionDTO.getRegistrosPorPagina());

		// Construcción de la consulta con StringBuilder
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(
				" SELECT NEW co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.CursoPlanificacionOutDTO(");
		queryBuilder.append(" c.idCurso, pr.abreviatura, a.semestre, a.nombre, c.grupo, ");
		queryBuilder.append(" (SELECT SUM(FUNCTION('TIME_TO_SEC', ");
		queryBuilder.append(" FUNCTION('TIMEDIFF', ");
		queryBuilder.append(" CONCAT(CURRENT_DATE, ' ', h.horaFin), ");
		queryBuilder.append(" CONCAT(CURRENT_DATE, ' ', h.horaInicio))) / 3600) ");
		queryBuilder.append(" FROM HorarioEntity h ");
		queryBuilder.append(" WHERE h.curso.idCurso = c.idCurso");
		queryBuilder.append(" GROUP BY h.curso.idCurso) AS tiempo, a.horasSemana, c.cupo, a.idAsignatura, a.aplicaEspacioSecundario");
		queryBuilder.append(" )");
		queryBuilder.append(" FROM CursoEntity c");
		queryBuilder.append(" JOIN c.asignatura a");
		queryBuilder.append(" JOIN a.programa pr");
		queryBuilder.append(" WHERE 1=1");
		queryBuilder.append(" AND pr.idPrograma IN (SELECT programas.idPrograma FROM UsuarioEntity usu JOIN usu.programas programas WHERE usu.nombreUsuario = :nombreUsuario ) ");

		
		Map<String, Object> parametros = new HashMap<>();
		
		// Se consulta el usuario del contexto y se filtra sobre los programas que tiene permitidos
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UsuarioPrincipal usuarioPrincipal = (UsuarioPrincipal) authentication.getPrincipal();		
		parametros.put("nombreUsuario", usuarioPrincipal.getUsername());	
		

		if (Objects.nonNull(idPeriodoAcademicoVigente)) {
			queryBuilder.append(" AND c.periodoAcademico.idPeriodoAcademico =:idPeriodoAcademico");
			parametros.put("idPeriodoAcademico", idPeriodoAcademicoVigente);
		} else {
			queryBuilder.append(" AND c.periodoAcademico.idPeriodoAcademico IS NULL");
		}

		if (Objects.nonNull(filtroCursoPlanificacionDTO.getListaIdFacultad())
				&& !filtroCursoPlanificacionDTO.getListaIdFacultad().isEmpty()) {
			queryBuilder.append(" AND pr.facultad.idFacultad IN (:listaIdFacultad)");
			parametros.put("listaIdFacultad", filtroCursoPlanificacionDTO.getListaIdFacultad());
		}

		if (Objects.nonNull(filtroCursoPlanificacionDTO.getListaIdPrograma())
				&& !filtroCursoPlanificacionDTO.getListaIdPrograma().isEmpty()) {
			queryBuilder.append(" AND pr.idPrograma IN (:listaIdPograma)");
			parametros.put("listaIdPograma", filtroCursoPlanificacionDTO.getListaIdPrograma());
		}

		if (Objects.nonNull(filtroCursoPlanificacionDTO.getListaIdAsignatura())
				&& !filtroCursoPlanificacionDTO.getListaIdAsignatura().isEmpty()) {
			queryBuilder.append(" AND a.idAsignatura IN (:listaIdAsignatura)");
			parametros.put("listaIdAsignatura", filtroCursoPlanificacionDTO.getListaIdAsignatura());
		}

		if (Objects.nonNull(filtroCursoPlanificacionDTO.getSemestre())) {
			queryBuilder.append(" AND a.semestre =:semestre");
			parametros.put("semestre", filtroCursoPlanificacionDTO.getSemestre());
		}

		if (Objects.nonNull(filtroCursoPlanificacionDTO.getEstadoCursoHorario())) {
			queryBuilder.append(" AND (SELECT SUM(FUNCTION('TIME_TO_SEC', ");
			queryBuilder.append(" FUNCTION('TIMEDIFF', ");
			queryBuilder.append(" CONCAT(CURRENT_DATE, ' ', h.horaFin), ");
			queryBuilder.append(" CONCAT(CURRENT_DATE, ' ', h.horaInicio))) / 3600) ");
			queryBuilder.append(" FROM HorarioEntity h ");
			queryBuilder.append(" WHERE h.curso.idCurso = c.idCurso");

			if (filtroCursoPlanificacionDTO.getEstadoCursoHorario().equals(EstadoCursoHorarioEnum.PARCIALMENTE)) {
				queryBuilder.append(" GROUP BY h.curso.idCurso) != a.horasSemana");
			} else if (filtroCursoPlanificacionDTO.getEstadoCursoHorario().equals(EstadoCursoHorarioEnum.SIN_ASIGNAR)) {
				queryBuilder.append(" GROUP BY h.curso.idCurso) is null");
			} else if (filtroCursoPlanificacionDTO.getEstadoCursoHorario().equals(EstadoCursoHorarioEnum.ASIGNADO)) {
				queryBuilder.append(" GROUP BY h.curso.idCurso) = a.horasSemana");
			}
		}

		if (Objects.nonNull(filtroCursoPlanificacionDTO.getCantidadDocentes())) {
			queryBuilder.append(" AND (SELECT COUNT(*)");
			queryBuilder.append(" FROM CursoEntity cur");
			queryBuilder.append(" JOIN cur.docentes doc");
			queryBuilder.append(" WHERE cur.idCurso = c.idCurso) = :cantidadDocentes");
			parametros.put("cantidadDocentes", filtroCursoPlanificacionDTO.getCantidadDocentes().longValue());
		}

		queryBuilder.append(" ORDER BY pr.nombre asc, a.semestre asc, a.nombre asc, c.grupo asc");

		// Realiza la consulta paginada
		TypedQuery<CursoPlanificacionOutDTO> typedQuery = entityManager.createQuery(queryBuilder.toString(),
				CursoPlanificacionOutDTO.class);
		typedQuery.setFirstResult((int) pageable.getOffset());
		typedQuery.setMaxResults(pageable.getPageSize());

		// Establece parámetros en la consulta
		for (Map.Entry<String, Object> entry : parametros.entrySet()) {
			typedQuery.setParameter(entry.getKey(), entry.getValue());
		}

		return new PageImpl<>(typedQuery.getResultList(), pageable, contarCursosConsultados(
				filtroCursoPlanificacionDTO.getListaIdFacultad(), filtroCursoPlanificacionDTO.getListaIdPrograma(),
				filtroCursoPlanificacionDTO.getListaIdAsignatura(), filtroCursoPlanificacionDTO.getSemestre(),
				filtroCursoPlanificacionDTO.getEstadoCursoHorario(), filtroCursoPlanificacionDTO.getCantidadDocentes(),
				idPeriodoAcademicoVigente, usuarioPrincipal.getUsername()));
	}

	private Long contarCursosConsultados(List<Long> listaIdFacultad, List<Long> listaIdPograma,
			List<Long> listaIdAsignatura, Integer semestre, EstadoCursoHorarioEnum estadoCursoHorario,
			Integer cantidadDocentes, Long idPeriodoAcademicoVigente, String nombreUsuario) {

		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(" SELECT COUNT(c)");
		queryBuilder.append(" FROM CursoEntity c");
		queryBuilder.append(" JOIN c.asignatura a");
		queryBuilder.append(" JOIN a.programa pr");
		queryBuilder.append(" WHERE 1=1");
		queryBuilder.append(" AND pr.idPrograma IN (SELECT programas.idPrograma FROM UsuarioEntity usu JOIN usu.programas programas WHERE usu.nombreUsuario = :nombreUsuario ) ");

		Map<String, Object> parametros = new HashMap<>();
		parametros.put("nombreUsuario", nombreUsuario);	

		if (Objects.nonNull(idPeriodoAcademicoVigente)) {
			queryBuilder.append(" AND c.periodoAcademico.idPeriodoAcademico =:idPeriodoAcademico");
			parametros.put("idPeriodoAcademico", idPeriodoAcademicoVigente);
		} else {
			queryBuilder.append(" AND c.periodoAcademico.idPeriodoAcademico IS NULL");
		}

		if (Objects.nonNull(listaIdFacultad) && !listaIdFacultad.isEmpty()) {
			queryBuilder.append(" AND pr.facultad.idFacultad IN (:listaIdFacultad)");
			parametros.put("listaIdFacultad", listaIdFacultad);
		}

		if (Objects.nonNull(listaIdPograma) && !listaIdPograma.isEmpty()) {
			queryBuilder.append(" AND pr.idPrograma IN (:listaIdPograma)");
			parametros.put("listaIdPograma", listaIdPograma);
		}

		if (Objects.nonNull(listaIdAsignatura) && !listaIdAsignatura.isEmpty()) {
			queryBuilder.append(" AND a.idAsignatura IN (:listaIdAsignatura)");
			parametros.put("listaIdAsignatura", listaIdAsignatura);
		}

		if (Objects.nonNull(semestre)) {
			queryBuilder.append(" AND a.semestre =:semestre");
			parametros.put("semestre", semestre);
		}

		if (Objects.nonNull(estadoCursoHorario)) {
			queryBuilder.append(" AND (SELECT SUM(FUNCTION('TIME_TO_SEC', ");
			queryBuilder.append(" FUNCTION('TIMEDIFF', ");
			queryBuilder.append(" CONCAT(CURRENT_DATE, ' ', h.horaFin), ");
			queryBuilder.append(" CONCAT(CURRENT_DATE, ' ', h.horaInicio))) / 3600) ");
			queryBuilder.append(" FROM HorarioEntity h ");
			queryBuilder.append(" WHERE h.curso.idCurso = c.idCurso");

			if (estadoCursoHorario.equals(EstadoCursoHorarioEnum.PARCIALMENTE)) {
				queryBuilder.append(" GROUP BY h.curso.idCurso) != a.horasSemana");
			} else if (estadoCursoHorario.equals(EstadoCursoHorarioEnum.SIN_ASIGNAR)) {
				queryBuilder.append(" GROUP BY h.curso.idCurso) is null");
			} else if (estadoCursoHorario.equals(EstadoCursoHorarioEnum.ASIGNADO)) {
				queryBuilder.append(" GROUP BY h.curso.idCurso) = a.horasSemana");
			}
		}

		if (Objects.nonNull(cantidadDocentes)) {
			queryBuilder.append(" AND (SELECT COUNT(*)");
			queryBuilder.append(" FROM CursoEntity cur");
			queryBuilder.append(" JOIN cur.docentes doc");
			queryBuilder.append(" WHERE cur.idCurso = c.idCurso) = :cantidadDocentes");
			parametros.put("cantidadDocentes", cantidadDocentes.longValue());
		}

		queryBuilder.append(" ORDER BY pr.nombre asc, a.semestre asc, a.nombre asc, c.grupo asc");

		// Realiza la consulta para contar
		TypedQuery<Long> countQuery = entityManager.createQuery(queryBuilder.toString(), Long.class);

		// Establece parámetros en la consulta
		for (Map.Entry<String, Object> entry : parametros.entrySet()) {
			countQuery.setParameter(entry.getKey(), entry.getValue());
		}

		return countQuery.getSingleResult();
	}

	/**
	 * @see co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.output.GestionarPlanificacionManualGatewayIntPort#consultarInfoGeneralCursosPorPrograma(java.lang.Long)
	 */
	@Override
	public InfoGeneralCursosPorProgramaDTO consultarInfoGeneralCursosPorPrograma(Long idPrograma,
			Long idPeriodoAcademicoVigente) {
		/*
		 * Se consulta el usuario del contexto y se filtra sobre los programas que tiene
		 * permitidos
		 */
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UsuarioPrincipal usuarioPrincipal = (UsuarioPrincipal) authentication.getPrincipal();

		InfoGeneralCursosPorProgramaDTO infoGeneralCursosPorProgramaDTO = new InfoGeneralCursosPorProgramaDTO();
		infoGeneralCursosPorProgramaDTO.setCantidadCursosHorarioParcial(this.contarCursosConsultados(null,
				Arrays.asList(idPrograma), null, null, EstadoCursoHorarioEnum.PARCIALMENTE, null,
				idPeriodoAcademicoVigente, usuarioPrincipal.getUsername()));
		infoGeneralCursosPorProgramaDTO.setCantidadCursosSinHorario(this.contarCursosConsultados(null,
				Arrays.asList(idPrograma), null, null, EstadoCursoHorarioEnum.SIN_ASIGNAR, null,
				idPeriodoAcademicoVigente, usuarioPrincipal.getUsername()));
		infoGeneralCursosPorProgramaDTO.setCantidadCursosConHorario(this.contarCursosConsultados(null,
				Arrays.asList(idPrograma), null, null, EstadoCursoHorarioEnum.ASIGNADO, null, idPeriodoAcademicoVigente,
				usuarioPrincipal.getUsername()));
		infoGeneralCursosPorProgramaDTO.setTotalCursos(this.contarCursosConsultados(null, Arrays.asList(idPrograma),
				null, null, null, null, idPeriodoAcademicoVigente, usuarioPrincipal.getUsername()));
		infoGeneralCursosPorProgramaDTO
				.setCantidadCursosSinDocente(this.contarCursosConsultados(null, Arrays.asList(idPrograma), null, null,
						null, 0, idPeriodoAcademicoVigente, usuarioPrincipal.getUsername()));
		return infoGeneralCursosPorProgramaDTO;
	}

	/**
	 * @see co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.output.GestionarPlanificacionManualGatewayIntPort#consultarCruceHorarioDocente(java.lang.Long,
	 *      co.edu.unicauca.sgph.common.enums.DiaSemanaEnum, java.time.LocalTime,
	 *      java.time.LocalTime, java.lang.Long)
	 */
	@Override
	public List<Object[]> consultarCruceHorarioDocente(Long idCurso, DiaSemanaEnum dia, LocalTime horaInicio,
			LocalTime horaFin, Long idPeriodoAcademicoVigente) {
		return this.planificacionManualRepositoryInt.consultarCruceHorarioDocente(idCurso, dia, horaInicio, horaFin,
				idPeriodoAcademicoVigente);
	}

	/**
	 * @see co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.output.GestionarPlanificacionManualGatewayIntPort#consultarCruceHorarioEspacioFisico(java.util.List,
	 *      co.edu.unicauca.sgph.common.enums.DiaSemanaEnum, java.time.LocalTime,
	 *      java.time.LocalTime, java.lang.Long)
	 */
	@Override
	public List<Horario> consultarCruceHorarioEspacioFisico(List<Long> lstIdEspacioFisico, DiaSemanaEnum dia,
			LocalTime horaInicio, LocalTime horaFin, Long idPeriodoAcademicoVigente) {
		return this.modelMapper
				.map(this.planificacionManualRepositoryInt.consultarCruceHorarioEspacioFisico(lstIdEspacioFisico, dia,
						horaInicio, horaFin, idPeriodoAcademicoVigente), new TypeToken<List<Horario>>() {
						}.getType());
	}

	/**
	 * @see co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.output.GestionarPlanificacionManualGatewayIntPort#consultarCruceHorarioDocentePorIdPersona(java.lang.Long,
	 *      co.edu.unicauca.sgph.common.enums.DiaSemanaEnum, java.time.LocalTime,
	 *      java.time.LocalTime, java.lang.Long)
	 */
	@Override
	public List<Object[]> consultarCruceHorarioDocentePorIdPersona(Long idPersona, DiaSemanaEnum dia,
			LocalTime horaInicio, LocalTime horaFin, Long idPeriodoAcademicoVigente) {
		return this.planificacionManualRepositoryInt.consultarCruceHorarioDocentePorIdPersona(idPersona, dia,
				horaInicio, horaFin, idPeriodoAcademicoVigente);
	}

	/**
	 * @see co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.output.GestionarPlanificacionManualGatewayIntPort#consultarFormatoPresentacionFranjaHorariaCurso()
	 */
	@Override
	public List<FormatoPresentacionFranjaHorariaCursoDTO> consultarFormatoPresentacionFranjaHorariaCurso() {
		return this.planificacionManualRepositoryInt.consultarFormatoPresentacionFranjaHorariaCurso();
	}
	
	/**
	 * @see co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.output.GestionarPlanificacionManualGatewayIntPort#consultarFranjasHorariaCursoPorIdCurso(java.lang.Long,
	 *      java.lang.Boolean)
	 */
	@Override
	public List<FranjaHorariaCursoDTO> consultarFranjasHorariaCursoPorIdCurso(Long idCurso, Boolean esPrincipal) {
		return this.planificacionManualRepositoryInt.consultarFranjasHorariaCursoPorIdCurso(idCurso, esPrincipal);
	}

	/**
	 * @see co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.output.GestionarPlanificacionManualGatewayIntPort#consultarFranjasDocentePorIdPersona(java.lang.Long,
	 *      java.lang.Long)
	 */
	@Override
	public List<FranjaHorariaDocenteDTO> consultarFranjasDocentePorIdPersona(Long idPersona,
			Long idPeriodoAcademicoVigente) {
		return this.planificacionManualRepositoryInt.consultarFranjasDocentePorIdPersona(idPersona,
				idPeriodoAcademicoVigente);
	}

	/**
	 * @see co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.output.GestionarPlanificacionManualGatewayIntPort#consultarFranjasEspacioFisicoPorIdEspacioFisico(java.lang.Long,
	 *      java.lang.Long)
	 */
	@Override
	public List<FranjaHorariaEspacioFisicoDTO> consultarFranjasEspacioFisicoPorIdEspacioFisico(Long idEspacioFisico,
			Long idPeriodoAcademicoVigente) {
		return this.planificacionManualRepositoryInt.consultarFranjasEspacioFisicoPorIdEspacioFisico(idEspacioFisico,
				idPeriodoAcademicoVigente);
	}

	/**
	 * @see co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.output.GestionarPlanificacionManualGatewayIntPort#consultarFranjasHorariasDeEspaciosFisicosPorCursoYCriterios(java.lang.Long,
	 *      java.util.List, java.util.List, java.util.List, java.lang.String,
	 *      java.lang.Long)
	 */
	@Override
	public Map<Long, List<FranjaHorariaBasicaDTO>> consultarFranjasHorariasDeEspaciosFisicosPorCursoYCriterios(
			Long idCurso, List<Long> listaIdUbicacion, List<Long> listaIdTipoEspacioFisico,
			List<Long> listaIdAgrupadorEspacioFisico, String salon, Long idPeriodoAcademicoVigente) {

		/*
		 * Se consulta los espacios físicos adecuados para el curso.
		 */
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT DISTINCT espacioFisico.idEspacioFisico ");
		queryBuilder.append("FROM EspacioFisicoEntity espacioFisico ");
		queryBuilder.append("LEFT JOIN espacioFisico.agrupadores agrupadores ");
		queryBuilder.append("WHERE 1=1 ");

		// Se consulta los grupos del curso
		List<AgrupadorEspacioFisico> listaAgrupadoresCurso = this.gestionarAgrupadorEspacioFisicoGatewayIntPort
				.consultarAgrupadoresEspaciosFisicosAsociadosACursoPorIdCurso(idCurso);

		Map<String, Object> parametros = new HashMap<>();

		/*
		 * Filtros opcionales ingresados por el usuario
		 */
		if (Objects.nonNull(listaIdUbicacion) && !listaIdUbicacion.isEmpty()) {
			queryBuilder.append("AND espacioFisico.ubicacion.idUbicacion IN (:listaIdUbicacion) ");
			parametros.put("listaIdUbicacion", listaIdUbicacion);
		}
		if (Objects.nonNull(listaIdTipoEspacioFisico) && !listaIdTipoEspacioFisico.isEmpty()) {
			queryBuilder
					.append("AND espacioFisico.tipoEspacioFisico.idTipoEspacioFisico IN (:listaIdTipoEspacioFisico) ");
			parametros.put("listaIdTipoEspacioFisico", listaIdTipoEspacioFisico);
		}
		if (Objects.nonNull(listaIdAgrupadorEspacioFisico) && !listaIdAgrupadorEspacioFisico.isEmpty()) {
			queryBuilder.append("AND agrupadores.idAgrupadorEspacioFisico IN (:listaIdAgrupadorEspacioFisico) ");
			parametros.put("listaIdAgrupadorEspacioFisico", listaIdAgrupadorEspacioFisico);
		}
		if (Objects.nonNull(salon) && !salon.isEmpty()) {
			queryBuilder.append("AND espacioFisico.salon LIKE :salon ");
			parametros.put("salon", "%" + salon.replaceAll("\\s+", " ").trim() + "%");
		}

		/*
		 * Filtros obligatorios para todos los curso
		 */

		// La capacidad del espacio físico debe ser mayor o igual al cupo del curso
		queryBuilder.append(
				"AND espacioFisico.capacidad >= (SELECT curso.cupo FROM CursoEntity curso WHERE curso.idCurso = :idCurso) ");
		parametros.put("idCurso", idCurso);
		// Solo espacios físicos en estado ACTIVO
		queryBuilder.append("AND espacioFisico.estado = 'ACTIVO' ");

		if (!listaAgrupadoresCurso.isEmpty()) {
			/*
			 * Se filtra grupos del curso si tiene. Esta opción es requerida para cuando un
			 * curso que tiene grupos asociados y el usuario no ha filtrado por ningún
			 * grupo, entonces por defecto debe cargar todos y sólo los espacios físicos de
			 * esos grupos
			 */
			queryBuilder.append("AND agrupadores.idAgrupadorEspacioFisico IN (:listaIdAgrupadorEspacioFisicoDefault) ");
			parametros.put("listaIdAgrupadorEspacioFisicoDefault", listaAgrupadoresCurso.stream()
					.map(AgrupadorEspacioFisico::getIdAgrupadorEspacioFisico).collect(Collectors.toList()));
		}

		// Crea la consulta
		TypedQuery<Long> typedQueryEspaciosFisicos = entityManager.createQuery(queryBuilder.toString(), Long.class);

		// Asigna los parámetros a la consulta
		for (Map.Entry<String, Object> entry : parametros.entrySet()) {
			typedQueryEspaciosFisicos.setParameter(entry.getKey(), entry.getValue());
		}

		// Lista de espacios físicos permitidos para el curso
		List<Long> listaIdEspacioFisico = typedQueryEspaciosFisicos.getResultList();

		// Se consulta los horarios los espacios físicos y se agrupan por espacio físico
		if (!listaIdEspacioFisico.isEmpty()) {
			queryBuilder = new StringBuilder();
			queryBuilder.append(
					"SELECT NEW co.edu.unicauca.sgph.gestionplanificacion.manual.domain.model.FranjaHorariaBasicaDTO( ");
			queryBuilder.append("horario.idHorario, espacioFisico.idEspacioFisico, ");
			queryBuilder.append("horario.dia, horario.horaInicio, horario.horaFin ) ");
			queryBuilder.append("FROM HorarioEntity horario ");
			queryBuilder.append("JOIN horario.curso curso ");
			queryBuilder.append("JOIN horario.espaciosFisicos espacioFisico ");
			queryBuilder.append("WHERE espacioFisico.idEspacioFisico IN (:listaIdEspacioFisico) ");
			queryBuilder.append("AND curso.periodoAcademico.idPeriodoAcademico = :idPeriodoAcademicoVigente ");

			parametros = new HashMap<>();
			parametros.put("listaIdEspacioFisico", listaIdEspacioFisico);
			parametros.put("idPeriodoAcademicoVigente", idPeriodoAcademicoVigente);

			// Crea la consulta
			TypedQuery<FranjaHorariaBasicaDTO> typedQueryHorarios = entityManager.createQuery(queryBuilder.toString(),
					FranjaHorariaBasicaDTO.class);

			// Asigna los parámetros a la consulta
			for (Map.Entry<String, Object> entry : parametros.entrySet()) {
				typedQueryHorarios.setParameter(entry.getKey(), entry.getValue());
			}

			List<FranjaHorariaBasicaDTO> listaFranjaHorariaBasicaDTO = typedQueryHorarios.getResultList();

			// Se agrupa los horarios por espacio físico
			Map<Long, List<FranjaHorariaBasicaDTO>> mapaFranjasHorariasPorEspacioFisico = listaFranjaHorariaBasicaDTO
					.stream().collect(Collectors.groupingBy(FranjaHorariaBasicaDTO::getIdEspacioFisico));

			for (Long idEspacioFisico : listaIdEspacioFisico) {
				if (!mapaFranjasHorariasPorEspacioFisico.containsKey(idEspacioFisico)) {
					// Para los espacios físicos que no tienen horario se les establece lista vacía
					mapaFranjasHorariasPorEspacioFisico.put(idEspacioFisico, new ArrayList<>());
				}
			}
			return mapaFranjasHorariasPorEspacioFisico;
		} else {
			return new HashMap<>();
		}
	}

	/**
	 * @see co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.output.GestionarPlanificacionManualGatewayIntPort#consultarFranjasHorariasDeDocentesAsociadosACurso(java.lang.Long,
	 *      java.lang.Long)
	 */
	@Override
	public List<FranjaHorariaBasicaDTO> consultarFranjasHorariasDeDocentesAsociadosACurso(Long idCurso,
			Long idPeriodoAcademicoVigente) {
		StringBuilder queryBuilder = new StringBuilder();

		queryBuilder.append(
				"SELECT DISTINCT NEW co.edu.unicauca.sgph.gestionplanificacion.manual.domain.model.FranjaHorariaBasicaDTO( ");
		queryBuilder.append("horarios.idHorario, horarios.dia, horarios.horaInicio, horarios.horaFin ) ");
		queryBuilder.append("FROM CursoEntity curso ");
		queryBuilder.append("JOIN curso.docentes docentes ");
		queryBuilder.append("JOIN curso.horarios horarios ");
		queryBuilder.append("WHERE docentes.persona.idPersona IN (");
		queryBuilder.append(
				"SELECT docente.persona.idPersona FROM DocenteEntity docente JOIN docente.cursos cursos WHERE cursos.idCurso = :idCurso ");
		queryBuilder.append(") ");
		queryBuilder.append("AND curso.periodoAcademico.idPeriodoAcademico = :idPeriodoAcademicoVigente ");

		Map<String, Object> parametros = new HashMap<>();

		parametros.put("idCurso", idCurso);
		parametros.put("idPeriodoAcademicoVigente", idPeriodoAcademicoVigente);

		// Crea la consulta
		TypedQuery<FranjaHorariaBasicaDTO> typedQuery = entityManager.createQuery(queryBuilder.toString(),
				FranjaHorariaBasicaDTO.class);

		// Asigna los parámetros a la consulta
		for (Map.Entry<String, Object> entry : parametros.entrySet()) {
			typedQuery.setParameter(entry.getKey(), entry.getValue());
		}

		return typedQuery.getResultList();
	}

	/**
	 * @see co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.output.GestionarPlanificacionManualGatewayIntPort#consultarFranjasHorariasDeSemestrePorCurso(java.lang.Long,
	 *      java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<FranjaHorariaBasicaDTO> consultarFranjasHorariasDeSemestrePorCurso(Long idCurso, Long idAsignatura,
			Long idPeriodoAcademicoVigente) {
		StringBuilder queryBuilder = new StringBuilder();

		queryBuilder.append(
				"SELECT DISTINCT NEW co.edu.unicauca.sgph.gestionplanificacion.manual.domain.model.FranjaHorariaBasicaDTO( ");
		queryBuilder.append("horarios.dia, horarios.horaInicio, horarios.horaFin, asignatura.idAsignatura) ");
		queryBuilder.append("FROM CursoEntity curso ");
		queryBuilder.append("JOIN curso.asignatura asignatura ");
		queryBuilder.append("JOIN curso.horarios horarios ");
		queryBuilder.append(
				"WHERE asignatura.semestre = (SELECT asig.semestre FROM CursoEntity cur JOIN cur.asignatura asig WHERE cur.idCurso = :idCurso) ");
		queryBuilder.append(
				"AND asignatura.semestre != -1 ");
		queryBuilder.append(
				"AND asignatura.programa.idPrograma = (SELECT asig.programa.idPrograma FROM CursoEntity cur JOIN cur.asignatura asig WHERE cur.idCurso = :idCurso) ");
		queryBuilder.append("AND curso.periodoAcademico.idPeriodoAcademico = :idPeriodoAcademicoVigente ");

		Map<String, Object> parametros = new HashMap<>();

		parametros.put("idCurso", idCurso);
		parametros.put("idPeriodoAcademicoVigente", idPeriodoAcademicoVigente);

		// Crea la consulta
		TypedQuery<FranjaHorariaBasicaDTO> typedQuery = entityManager.createQuery(queryBuilder.toString(),
				FranjaHorariaBasicaDTO.class);

		// Asigna los parámetros a la consulta
		for (Map.Entry<String, Object> entry : parametros.entrySet()) {
			typedQuery.setParameter(entry.getKey(), entry.getValue());
		}
		// Se realiza la consulta
		List<FranjaHorariaBasicaDTO> lstFranjaHorariaBasicaDTO = typedQuery.getResultList();

		/*
		 * Este paso es necesario para poder admitir el solapamiento de franjas horarias
		 * de cursos que pertenecen a una misma asignatura. Por ejemplo. Cálculo 1 B se
		 * puede impartir en la misma franja horaria que Cálculo 1 A
		 */
		Map<String, FranjaHorariaBasicaDTO> mapaFiltrado = lstFranjaHorariaBasicaDTO.stream()
				.collect(Collectors.toMap(obj -> obj.getDia() + "-" + obj.getHoraInicio() + "-" + obj.getHoraFin(),
						Function.identity(), (obj1, obj2) -> {
							if (obj1.getIdAsignatura().equals(idAsignatura)) {
								return obj1;
							} else if (obj2.getIdAsignatura().equals(idAsignatura)) {
								return obj2;
							} else {
								return obj1;
							}
						}));

		return new ArrayList<>(mapaFiltrado.values());
	}

	/**
	 * @see co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.output.GestionarPlanificacionManualGatewayIntPort#consultarFranjasHorariasDeCursoPorCurso(java.lang.Long)
	 */
	@Override
	public List<FranjaHorariaBasicaDTO> consultarFranjasHorariasDeCursoPorCurso(Long idCurso) {
		StringBuilder queryBuilder = new StringBuilder();

		queryBuilder.append(
				"SELECT DISTINCT NEW co.edu.unicauca.sgph.gestionplanificacion.manual.domain.model.FranjaHorariaBasicaDTO( ");
		queryBuilder.append("horarios.dia, horarios.horaInicio, horarios.horaFin) ");
		queryBuilder.append("FROM CursoEntity curso ");
		queryBuilder.append("JOIN curso.horarios horarios ");
		queryBuilder.append("WHERE curso.idCurso = :idCurso ");

		Map<String, Object> parametros = new HashMap<>();
		parametros.put("idCurso", idCurso);

		// Crea la consulta
		TypedQuery<FranjaHorariaBasicaDTO> typedQuery = entityManager.createQuery(queryBuilder.toString(),
				FranjaHorariaBasicaDTO.class);

		// Asigna los parámetros a la consulta
		for (Map.Entry<String, Object> entry : parametros.entrySet()) {
			typedQuery.setParameter(entry.getKey(), entry.getValue());
		}
		// Se realiza la consulta
		return typedQuery.getResultList();
	}

	/**
	 * @see co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.output.GestionarPlanificacionManualGatewayIntPort#consultarIdAsignaturaCupoYCantidadHorasDeCusoPorCurso(java.lang.Long)
	 */
	@Override
	public Object consultarIdAsignaturaCupoYCantidadHorasDeCusoPorCurso(Long idCurso) {
		return this.planificacionManualRepositoryInt.consultarIdAsignaturaCupoYCantidadHorasDeCusoPorCurso(idCurso);
	}

	
	/**
	 * @see co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.output.GestionarPlanificacionManualGatewayIntPort#eliminarHorarioPrograma(co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTORequest.EliminarHorarioInDTO,
	 *      java.lang.Long)
	 */
	@Override
	public void eliminarHorarioPrograma(EliminarHorarioInDTO eliminarHorarioInDTO, Long idPeriodoAcademicoVigente) {
		// Se elimina asociaciones entre horarios y espacios físicos
		this.planificacionManualRepositoryInt.eliminarRegistrosHorarioEspacioEntityPorProgramaYPeriodoAcademico(
				eliminarHorarioInDTO.getIdPrograma(), idPeriodoAcademicoVigente);
		// Se elimina horarios
		this.planificacionManualRepositoryInt.eliminarRegistrosHorarioEntityPorProgramaYPeriodoAcademico(
				eliminarHorarioInDTO.getIdPrograma(), idPeriodoAcademicoVigente);
	}

	/**
	 * @see co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.output.GestionarPlanificacionManualGatewayIntPort#consultarFranjasHorariaPrincipalProgramaPoridProgramaYPeriodoAcademico(java.lang.Long,
	 *      java.lang.Long)
	 */
	@Override
	public List<FranjaHorariaCursoDTO> consultarFranjasHorariaPrincipalProgramaPoridProgramaYPeriodoAcademico(
			Long idPrograma, Long idPeriodoAcademico) {
		return this.planificacionManualRepositoryInt.consultarFranjasHorariaPrincipalProgramaPoridProgramaYPeriodoAcademico(idPrograma, idPeriodoAcademico);
	}
}