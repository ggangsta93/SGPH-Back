package co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.output.persistence.gateway;

import java.math.BigInteger;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import co.edu.unicauca.sgph.common.enums.DiaSemanaEnum;
import co.edu.unicauca.sgph.common.enums.EstadoCursoHorarioEnum;
import co.edu.unicauca.sgph.espaciofisico.aplication.output.GestionarAgrupadorEspacioFisicoGatewayIntPort;
import co.edu.unicauca.sgph.espaciofisico.domain.model.AgrupadorEspacioFisico;
import co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.output.GestionarPlanificacionManualGatewayIntPort;
import co.edu.unicauca.sgph.gestionplanificacion.manual.domain.model.FranjaHorariaBasicaDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTORequest.FiltroCursoPlanificacionDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.CursoPlanificacionOutDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.FormatoPresentacionFranjaHorariaCursoDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.FranjaHorariaCursoDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.FranjaHorariaDocenteDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.FranjaHorariaEspacioFisicoDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.InfoGeneralCursosPorProgramaDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.output.persistence.repository.PlanificacionManualRepositoryInt;
import co.edu.unicauca.sgph.horario.domain.model.Horario;
import co.edu.unicauca.sgph.horario.infrastructure.input.DTORequest.FiltroFranjaHorariaDisponibleCursoDTO;
import co.edu.unicauca.sgph.periodoacademico.aplication.output.GestionarPeriodoAcademicoGatewayIntPort;
import co.edu.unicauca.sgph.periodoacademico.domain.model.PeriodoAcademico;

@Service
public class GestionarPlanificacionManualGatewayImplAdapter implements GestionarPlanificacionManualGatewayIntPort {

	@PersistenceContext
	private EntityManager entityManager;

	private PlanificacionManualRepositoryInt planificacionManualRepositoryInt;
	private ModelMapper modelMapper;
	private GestionarPeriodoAcademicoGatewayIntPort gestionarPeriodoAcademicoGatewayIntPort;
	private GestionarAgrupadorEspacioFisicoGatewayIntPort gestionarAgrupadorEspacioFisicoGatewayIntPort;

	public GestionarPlanificacionManualGatewayImplAdapter(
			PlanificacionManualRepositoryInt planificacionManualRepositoryInt, ModelMapper modelMapper,
			GestionarPeriodoAcademicoGatewayIntPort gestionarPeriodoAcademicoGatewayIntPort,
			GestionarAgrupadorEspacioFisicoGatewayIntPort gestionarAgrupadorEspacioFisicoGatewayIntPort) {
		this.planificacionManualRepositoryInt = planificacionManualRepositoryInt;
		this.modelMapper = modelMapper;
		this.gestionarPeriodoAcademicoGatewayIntPort = gestionarPeriodoAcademicoGatewayIntPort;
		this.gestionarAgrupadorEspacioFisicoGatewayIntPort=gestionarAgrupadorEspacioFisicoGatewayIntPort;
	}

	/**
	 * @see co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.output.GestionarPlanificacionManualGatewayIntPort#consultarCursosPlanificacionPorFiltro(co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTORequest.FiltroCursoPlanificacionDTO)
	 */
	@Override
	public Page<CursoPlanificacionOutDTO> consultarCursosPlanificacionPorFiltro(
			FiltroCursoPlanificacionDTO filtroCursoPlanificacionDTO) {
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
		queryBuilder.append(" GROUP BY h.curso.idCurso) AS tiempo, a.horasSemana, c.cupo");
		queryBuilder.append(" )");
		queryBuilder.append(" FROM CursoEntity c");
		queryBuilder.append(" JOIN c.asignatura a");
		queryBuilder.append(" JOIN a.programa pr");
		queryBuilder.append(" WHERE 1=1");

		Map<String, Object> parametros = new HashMap<>();
		
		//Se consulta el periodo académico que está 'ABIERTO'
		PeriodoAcademico periodoAcademicoVigente = gestionarPeriodoAcademicoGatewayIntPort.consultarPeriodoAcademicoVigente();
		if (Objects.nonNull(periodoAcademicoVigente)) {
			queryBuilder.append(" AND c.periodoAcademico.idPeriodoAcademico =:idPeriodoAcademico");
			parametros.put("idPeriodoAcademico", periodoAcademicoVigente.getIdPeriodoAcademico());
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

		return new PageImpl<>(typedQuery.getResultList(), pageable,
				contarCursosConsultados(filtroCursoPlanificacionDTO.getListaIdFacultad(),
						filtroCursoPlanificacionDTO.getListaIdPrograma(),
						filtroCursoPlanificacionDTO.getListaIdAsignatura(), filtroCursoPlanificacionDTO.getSemestre(),
						filtroCursoPlanificacionDTO.getEstadoCursoHorario(),
						filtroCursoPlanificacionDTO.getCantidadDocentes(), periodoAcademicoVigente));
	}

	private Long contarCursosConsultados(List<Long> listaIdFacultad, List<Long> listaIdPograma,
			List<Long> listaIdAsignatura, Integer semestre, EstadoCursoHorarioEnum estadoCursoHorario,
			Integer cantidadDocentes, PeriodoAcademico periodoAcademicoVigente ) {

		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(" SELECT COUNT(c)");
		queryBuilder.append(" FROM CursoEntity c");
		queryBuilder.append(" JOIN c.asignatura a");
		queryBuilder.append(" JOIN a.programa pr");
		queryBuilder.append(" WHERE 1=1");

		Map<String, Object> parametros = new HashMap<>();

		if (Objects.nonNull(periodoAcademicoVigente)) {
			queryBuilder.append(" AND c.periodoAcademico.idPeriodoAcademico =:idPeriodoAcademico");
			parametros.put("idPeriodoAcademico", periodoAcademicoVigente.getIdPeriodoAcademico());
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
	public InfoGeneralCursosPorProgramaDTO consultarInfoGeneralCursosPorPrograma(Long idPrograma) {
		//Se consulta el periodo académico que está 'ABIERTO'
		PeriodoAcademico periodoAcademicoVigente = gestionarPeriodoAcademicoGatewayIntPort
				.consultarPeriodoAcademicoVigente();

		InfoGeneralCursosPorProgramaDTO infoGeneralCursosPorProgramaDTO = new InfoGeneralCursosPorProgramaDTO();
		infoGeneralCursosPorProgramaDTO.setCantidadCursosHorarioParcial(this.contarCursosConsultados(null,
				Arrays.asList(idPrograma), null, null, EstadoCursoHorarioEnum.PARCIALMENTE, null, periodoAcademicoVigente));
		infoGeneralCursosPorProgramaDTO.setCantidadCursosSinHorario(this.contarCursosConsultados(null,
				Arrays.asList(idPrograma), null, null, EstadoCursoHorarioEnum.SIN_ASIGNAR, null, periodoAcademicoVigente));
		infoGeneralCursosPorProgramaDTO.setCantidadCursosConHorario(this.contarCursosConsultados(null,
				Arrays.asList(idPrograma), null, null, EstadoCursoHorarioEnum.ASIGNADO, null, periodoAcademicoVigente));
		infoGeneralCursosPorProgramaDTO.setTotalCursos(this.contarCursosConsultados(null, Arrays.asList(idPrograma), null,
				null, null, null, periodoAcademicoVigente));
		infoGeneralCursosPorProgramaDTO.setCantidadCursosSinDocente(
				this.contarCursosConsultados(null, Arrays.asList(idPrograma), null, null, null, 0, periodoAcademicoVigente));
		return infoGeneralCursosPorProgramaDTO;
	}

	@Override
	public List<FranjaHorariaCursoDTO> consultarFranjasHorariasDisponiblesPorCurso(
			FiltroFranjaHorariaDisponibleCursoDTO filtroFranjaHorariaDisponibleCursoDTO) {
		Long idCurso = filtroFranjaHorariaDisponibleCursoDTO.getIdCurso();
		Long duracion = filtroFranjaHorariaDisponibleCursoDTO.getDuracion();

		String duracionFormateada = LocalTime.of(duracion.intValue(), 00, 00).toString();

		List<LocalTime> listaHoraInicio = null;
		if (Objects.nonNull(filtroFranjaHorariaDisponibleCursoDTO.getHoraInicio())) {
			listaHoraInicio = new ArrayList<LocalTime>();
			listaHoraInicio.add(
					LocalTime.of(Integer.parseInt(filtroFranjaHorariaDisponibleCursoDTO.getHoraInicio().split(":")[0]),
							Integer.parseInt(filtroFranjaHorariaDisponibleCursoDTO.getHoraInicio().split(":")[1])));
		}

		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("WITH FranjasPosiblesDocentes AS ( ")
				.append("    WITH FranjasPosibles AS ( ")
				.append("        SELECT dia, hora_inicio, ADDTIME(hora_inicio, :duracion) AS hora_fin ")
				.append("        FROM ( ")
				.append("            SELECT 'LUNES' AS dia, TIME('07:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'LUNES' AS dia, TIME('08:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'LUNES' AS dia, TIME('09:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'LUNES' AS dia, TIME('10:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'LUNES' AS dia, TIME('11:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'LUNES' AS dia, TIME('12:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'LUNES' AS dia, TIME('13:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'LUNES' AS dia, TIME('14:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'LUNES' AS dia, TIME('15:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'LUNES' AS dia, TIME('16:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'LUNES' AS dia, TIME('17:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'LUNES' AS dia, TIME('18:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'LUNES' AS dia, TIME('19:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'LUNES' AS dia, TIME('20:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'LUNES' AS dia, TIME('21:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'LUNES' AS dia, TIME('22:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'MARTES' AS dia, TIME('07:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'MARTES' AS dia, TIME('08:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'MARTES' AS dia, TIME('09:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'MARTES' AS dia, TIME('10:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'MARTES' AS dia, TIME('11:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'MARTES' AS dia, TIME('12:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'MARTES' AS dia, TIME('13:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'MARTES' AS dia, TIME('14:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'MARTES' AS dia, TIME('15:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'MARTES' AS dia, TIME('16:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'MARTES' AS dia, TIME('17:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'MARTES' AS dia, TIME('18:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'MARTES' AS dia, TIME('19:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'MARTES' AS dia, TIME('20:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'MARTES' AS dia, TIME('21:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'MARTES' AS dia, TIME('22:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'MIERCOLES' AS dia, TIME('07:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'MIERCOLES' AS dia, TIME('08:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'MIERCOLES' AS dia, TIME('09:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'MIERCOLES' AS dia, TIME('10:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'MIERCOLES' AS dia, TIME('11:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'MIERCOLES' AS dia, TIME('12:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'MIERCOLES' AS dia, TIME('13:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'MIERCOLES' AS dia, TIME('14:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'MIERCOLES' AS dia, TIME('15:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'MIERCOLES' AS dia, TIME('16:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'MIERCOLES' AS dia, TIME('17:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'MIERCOLES' AS dia, TIME('18:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'MIERCOLES' AS dia, TIME('19:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'MIERCOLES' AS dia, TIME('20:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'MIERCOLES' AS dia, TIME('21:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'MIERCOLES' AS dia, TIME('22:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'JUEVES' AS dia, TIME('07:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'JUEVES' AS dia, TIME('08:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'JUEVES' AS dia, TIME('09:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'JUEVES' AS dia, TIME('10:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'JUEVES' AS dia, TIME('11:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'JUEVES' AS dia, TIME('12:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'JUEVES' AS dia, TIME('13:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'JUEVES' AS dia, TIME('14:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'JUEVES' AS dia, TIME('15:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'JUEVES' AS dia, TIME('16:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'JUEVES' AS dia, TIME('17:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'JUEVES' AS dia, TIME('18:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'JUEVES' AS dia, TIME('19:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'JUEVES' AS dia, TIME('20:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'JUEVES' AS dia, TIME('21:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'JUEVES' AS dia, TIME('22:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'VIERNES' AS dia, TIME('07:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'VIERNES' AS dia, TIME('08:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'VIERNES' AS dia, TIME('09:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'VIERNES' AS dia, TIME('10:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'VIERNES' AS dia, TIME('11:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'VIERNES' AS dia, TIME('12:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'VIERNES' AS dia, TIME('13:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'VIERNES' AS dia, TIME('14:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'VIERNES' AS dia, TIME('15:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'VIERNES' AS dia, TIME('16:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'VIERNES' AS dia, TIME('17:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'VIERNES' AS dia, TIME('18:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'VIERNES' AS dia, TIME('19:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'VIERNES' AS dia, TIME('20:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'VIERNES' AS dia, TIME('21:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'VIERNES' AS dia, TIME('22:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'SABADO' AS dia, TIME('07:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'SABADO' AS dia, TIME('08:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'SABADO' AS dia, TIME('09:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'SABADO' AS dia, TIME('10:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'SABADO' AS dia, TIME('11:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'SABADO' AS dia, TIME('12:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'SABADO' AS dia, TIME('13:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'SABADO' AS dia, TIME('14:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'SABADO' AS dia, TIME('15:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'SABADO' AS dia, TIME('16:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'SABADO' AS dia, TIME('17:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'SABADO' AS dia, TIME('18:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'SABADO' AS dia, TIME('19:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'SABADO' AS dia, TIME('20:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'SABADO' AS dia, TIME('21:00:00') AS hora_inicio ")
				.append("            UNION SELECT 'SABADO' AS dia, TIME('22:00:00') AS hora_inicio ")
				.append("        ) AS franjas_posibles ")
				.append("        WHERE ")
				.append("        (ADDTIME(hora_inicio, :duracion) <= TIME('13:00:00') OR hora_inicio >= TIME('14:00:00')) ")
				.append("        AND ")
				.append("        (ADDTIME(hora_inicio, :duracion) <= TIME('22:00:00') OR hora_inicio >= TIME('24:00:00')) ")
				.append("    ), ")
				.append("    HorariosDocentes AS ( ")
				.append("        SELECT distinct h.dia, h.hora_inicio, h.hora_fin ")
				.append("        FROM horario h ")
				.append("        INNER JOIN curso c ON h.id_curso = c.id_curso ")
				.append("        INNER JOIN docente_curso dc ON dc.id_curso = c.id_curso ")
				.append("        INNER JOIN docente d ON d.id_persona = dc.id_docente ")
				.append("        INNER JOIN persona p ON p.id_persona = d.id_persona ")
				.append("        WHERE p.id_persona IN (SELECT doc.id_persona ")
				.append("                               FROM docente doc ")
				.append("                               INNER JOIN docente_curso doc_cur on doc.id_persona = doc_cur.id_docente ")
				.append("                               INNER JOIN curso cur on doc_cur.id_curso = cur.id_curso ")
				.append("                               WHERE cur.id_curso =:idCurso) ")
				.append("    ) ")
				.append("	SELECT dia, hora_inicio, hora_fin ")
				.append("	FROM ( 	SELECT franjaPosible.dia, franjaPosible.hora_inicio, franjaPosible.hora_fin ")
				.append("	    	FROM FranjasPosibles franjaPosible ")
				.append("	    	WHERE NOT EXISTS (  SELECT 1 ")
				.append("	        					FROM HorariosDocentes hd ")
				.append("	        					WHERE franjaPosible.dia = hd.dia ")
				.append("	        					AND franjaPosible.hora_fin>hd.hora_inicio ")
				.append("	        					AND franjaPosible.hora_inicio < hd.hora_fin )")
				.append("	) AS franjas_disponibles_por_dia ")
				.append("	ORDER BY dia ")
				.append("	/*Finaliza las franjas posibles de los docentes*/ ")
				.append(") ")
				.append("SELECT DISTINCT sf.id_espacio_fisico, fp.dia, fp.hora_inicio, fp.hora_fin ")
				.append("FROM espacio_fisico sf CROSS JOIN FranjasPosiblesDocentes fp ")
				.append("INNER JOIN ESPACIOFISICO_AGRUPADOR_ESP_FIS agrupador on sf.ID_ESPACIO_FISICO = agrupador.ID_ESPACIO_FISICO ")
				.append("WHERE NOT EXISTS (  SELECT 1 ")
				.append("                   FROM horario h ")
				.append("                   INNER JOIN horario_espaciofisico hsf ON h.id_horario = hsf.id_horario ")
				.append("                   WHERE sf.id_espacio_fisico = hsf.id_espacio_fisico ")
				.append("                   AND fp.dia = h.dia ")
				.append("                   AND fp.hora_fin > h.hora_inicio ")
				.append("                   AND fp.hora_inicio < h.hora_fin ) ");					
		
		
		// Se filtra espacios físicos si el curso tiene grupos asociados
		List<AgrupadorEspacioFisico> listaAgrupadoresCurso = this.gestionarAgrupadorEspacioFisicoGatewayIntPort
				.consultarAgrupadoresEspaciosFisicosAsociadosACursoPorIdCurso(idCurso);
		if (!listaAgrupadoresCurso.isEmpty()) {
			queryBuilder.append(" AND agrupador.ID_AGRUPADOR_ESPACIO_FISICO IN (:listaAgrupadoresCurso)");
		}

		// Se adicionan criterios de selección según el filtro 
		if (Objects.nonNull(filtroFranjaHorariaDisponibleCursoDTO.getListaDiaSemanaEnum())
				&& !filtroFranjaHorariaDisponibleCursoDTO.getListaDiaSemanaEnum().isEmpty()) {
			queryBuilder.append(" AND fp.dia IN (:listaDiaSemanaEnum)");
		}
		if (Objects.nonNull(listaHoraInicio) && !listaHoraInicio.isEmpty()) {
			queryBuilder.append(" AND fp.hora_inicio IN (:listaHoraInicio)");
		}
		if (Objects.nonNull(filtroFranjaHorariaDisponibleCursoDTO.getListaUbicaciones())
				&& !filtroFranjaHorariaDisponibleCursoDTO.getListaUbicaciones().isEmpty()) {
			queryBuilder.append(" AND sf.UBICACION IN (:listaUbicaciones)");
		}		
		if (Objects.nonNull(filtroFranjaHorariaDisponibleCursoDTO.getListaIdAgrupadorEspacioFisico())
				&& !filtroFranjaHorariaDisponibleCursoDTO.getListaIdAgrupadorEspacioFisico().isEmpty()) {
			queryBuilder.append(" AND agrupador.ID_AGRUPADOR_ESPACIO_FISICO IN (:listaIdAgrupadorEspacioFisico)");
		}
		if (Objects.nonNull(filtroFranjaHorariaDisponibleCursoDTO.getListaIdTipoEspacioFisico())
				&& !filtroFranjaHorariaDisponibleCursoDTO.getListaIdTipoEspacioFisico().isEmpty()) {
			queryBuilder.append(" AND sf.ID_TIPO_ESPACIO_FISICO IN (:listaIdTipoEspacioFisico)");
		}
		if (Objects.nonNull(filtroFranjaHorariaDisponibleCursoDTO.getSalon())
				&& !filtroFranjaHorariaDisponibleCursoDTO.getSalon().isEmpty()) {
			queryBuilder.append(" AND sf.salon LIKE :salon");
		}
		queryBuilder.append(" ORDER by sf.id_espacio_fisico, fp.dia, fp.hora_inicio ");

		/* Se establecen parametros */
		Query query = entityManager.createNativeQuery(queryBuilder.toString());
		query.setParameter("duracion", duracionFormateada);
		query.setParameter("idCurso", idCurso);
		
		// Se filtra espacios físicos si el curso tiene grupos asociados
		if (!listaAgrupadoresCurso.isEmpty()) {
			query.setParameter("listaAgrupadoresCurso", listaAgrupadoresCurso.stream()
					.map(AgrupadorEspacioFisico::getIdAgrupadorEspacioFisico).collect(Collectors.toList()));
		}
		
		// Se establecen parametros según el filtro 
		if (Objects.nonNull(filtroFranjaHorariaDisponibleCursoDTO.getListaDiaSemanaEnum())
				&& !filtroFranjaHorariaDisponibleCursoDTO.getListaDiaSemanaEnum().isEmpty()) {
			query.setParameter("listaDiaSemanaEnum", filtroFranjaHorariaDisponibleCursoDTO.getListaDiaSemanaEnum()
					.stream().map(DiaSemanaEnum::name).collect(Collectors.toList()));
		}
		if (Objects.nonNull(listaHoraInicio) && !listaHoraInicio.isEmpty()) {
			query.setParameter("listaHoraInicio", listaHoraInicio);
		}
		if (Objects.nonNull(filtroFranjaHorariaDisponibleCursoDTO.getListaUbicaciones())
				&& !filtroFranjaHorariaDisponibleCursoDTO.getListaUbicaciones().isEmpty()) {
			query.setParameter("listaUbicaciones",
					filtroFranjaHorariaDisponibleCursoDTO.getListaUbicaciones());
		}
		if (Objects.nonNull(filtroFranjaHorariaDisponibleCursoDTO.getListaIdAgrupadorEspacioFisico())
				&& !filtroFranjaHorariaDisponibleCursoDTO.getListaIdAgrupadorEspacioFisico().isEmpty()) {
			query.setParameter("listaIdAgrupadorEspacioFisico",
					filtroFranjaHorariaDisponibleCursoDTO.getListaIdAgrupadorEspacioFisico());
		}
		if (Objects.nonNull(filtroFranjaHorariaDisponibleCursoDTO.getListaIdTipoEspacioFisico())
				&& !filtroFranjaHorariaDisponibleCursoDTO.getListaIdTipoEspacioFisico().isEmpty()) {
			query.setParameter("listaIdTipoEspacioFisico",
					filtroFranjaHorariaDisponibleCursoDTO.getListaIdTipoEspacioFisico());
		}
		if (Objects.nonNull(filtroFranjaHorariaDisponibleCursoDTO.getSalon())
				&& !filtroFranjaHorariaDisponibleCursoDTO.getSalon().isEmpty()) {		
			query.setParameter("salon", "%"+filtroFranjaHorariaDisponibleCursoDTO.getSalon().replaceAll("\\s+", " ").trim()+"%");
		}

		List<FranjaHorariaCursoDTO> franjasDisponibles = new ArrayList<>();
		((List<Object[]>) query.getResultList()).forEach(tupla -> {
			franjasDisponibles.add(new FranjaHorariaCursoDTO(null, ((BigInteger) tupla[0]).longValue(),
					DiaSemanaEnum.valueOf((String) tupla[1]), ((Time) tupla[2]).toLocalTime(),
					((Time) tupla[3]).toLocalTime()));
		});

		return franjasDisponibles;
	}

	/**
	 * @see co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.output.GestionarPlanificacionManualGatewayIntPort#consultarCruceHorarioDocente(java.lang.Long,
	 *      co.edu.unicauca.sgph.common.enums.DiaSemanaEnum, java.time.LocalTime,
	 *      java.time.LocalTime)
	 */
	@Override
	public List<Object[]> consultarCruceHorarioDocente(Long idCurso, DiaSemanaEnum dia, LocalTime horaInicio,
			LocalTime horaFin) {
		return this.planificacionManualRepositoryInt.consultarCruceHorarioDocente(idCurso, dia, horaInicio, horaFin);
	}

	/** 
	 * @see co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.output.GestionarPlanificacionManualGatewayIntPort#consultarCruceHorarioEspacioFisico(java.util.List, co.edu.unicauca.sgph.common.enums.DiaSemanaEnum, java.time.LocalTime, java.time.LocalTime)
	 */
	@Override
	public List<Horario> consultarCruceHorarioEspacioFisico(List<Long> lstIdEspacioFisico, DiaSemanaEnum dia,
			LocalTime horaInicio, LocalTime horaFin) {
		return this.modelMapper.map(this.planificacionManualRepositoryInt.consultarCruceHorarioEspacioFisico(
				lstIdEspacioFisico, dia, horaInicio, horaFin), new TypeToken<List<Horario>>() {
				}.getType());
	}

	/**
	 * @see co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.output.GestionarPlanificacionManualGatewayIntPort#consultarCruceHorarioDocentePorIdPersona(java.lang.Long,
	 *      co.edu.unicauca.sgph.common.enums.DiaSemanaEnum, java.time.LocalTime,
	 *      java.time.LocalTime)
	 */
	@Override
	public List<Object[]> consultarCruceHorarioDocentePorIdPersona(Long idPersona, DiaSemanaEnum dia,
			LocalTime horaInicio, LocalTime horaFin) {
		return this.planificacionManualRepositoryInt.consultarCruceHorarioDocentePorIdPersona(idPersona, dia,
				horaInicio, horaFin);
	}

	/**
	 * @see co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.output.GestionarPlanificacionManualGatewayIntPort#consultarFormatoPresentacionFranjaHorariaCurso()
	 */
	@Override
	public List<FormatoPresentacionFranjaHorariaCursoDTO> consultarFormatoPresentacionFranjaHorariaCurso() {
		return this.planificacionManualRepositoryInt.consultarFormatoPresentacionFranjaHorariaCurso();
	}

	@Override
	public List<FranjaHorariaCursoDTO> consultarFranjasHorariaCursoPorIdCurso(Long idCurso) {
		return this.planificacionManualRepositoryInt.consultarFranjasHorariaCursoPorIdCurso(idCurso);
	}

	/**
	 * @see co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.output.GestionarPlanificacionManualGatewayIntPort#consultarFranjasDocentePorIdPersona(java.lang.Long)
	 */
	@Override
	public List<FranjaHorariaDocenteDTO> consultarFranjasDocentePorIdPersona(Long idPersona) {
		//Se consulta el periodo académico que está 'ABIERTO'
		PeriodoAcademico periodoAcademicoVigente = gestionarPeriodoAcademicoGatewayIntPort
				.consultarPeriodoAcademicoVigente();
		
		return this.planificacionManualRepositoryInt.consultarFranjasDocentePorIdPersona(idPersona,
				Objects.isNull(periodoAcademicoVigente) ? null : periodoAcademicoVigente.getIdPeriodoAcademico());
	}

	/** 
	 * @see co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.output.GestionarPlanificacionManualGatewayIntPort#consultarFranjasEspacioFisicoPorIdEspacioFisico(java.lang.Long)
	 */
	@Override
	public List<FranjaHorariaEspacioFisicoDTO> consultarFranjasEspacioFisicoPorIdEspacioFisico(Long idEspacioFisico) {
		//Se consulta el periodo académico que está 'ABIERTO'
		PeriodoAcademico periodoAcademicoVigente = gestionarPeriodoAcademicoGatewayIntPort
				.consultarPeriodoAcademicoVigente();
		
		return this.planificacionManualRepositoryInt.consultarFranjasEspacioFisicoPorIdEspacioFisico(idEspacioFisico,
				Objects.isNull(periodoAcademicoVigente) ? null : periodoAcademicoVigente.getIdPeriodoAcademico());
	}
	
	
	
	@Override
	public List<FranjaHorariaBasicaDTO> consultarFranjasHorariasDeEspaciosFisicosPorCursoYCriterios(Long idCurso,
			List<String> listaUbicaciones, List<Long> listaIdAgrupadorEspacioFisico, String salon) {		
		
		StringBuilder queryBuilder = new StringBuilder();
			
		queryBuilder.append("SELECT NEW co.edu.unicauca.sgph.gestionplanificacion.manual.domain.model.FranjaHorariaBasicaDTO( ");	
		queryBuilder.append("horario.idHorario, espaciosFisicos.idEspacioFisico, horario.dia, horario.horaInicio, horario.horaFin ) ");
		queryBuilder.append("FROM HorarioEntity horario ");
		queryBuilder.append("JOIN horario.espaciosFisicos espaciosFisicos ");
		queryBuilder.append("WHERE espaciosFisicos.idEspacioFisico IN  (");
		queryBuilder.append("                                            SELECT DISTINCT espacioFisico.idEspacioFisico ");
		queryBuilder.append("                                            FROM EspacioFisicoEntity espacioFisico ");
		queryBuilder.append("                                            LEFT JOIN espacioFisico.agrupadores agrupadores ");
		queryBuilder.append("                                            WHERE 1=1 ");

		Map<String, Object> parametros = new HashMap<>();

		if (Objects.nonNull(listaUbicaciones) && !listaUbicaciones.isEmpty()) {
			queryBuilder.append("AND espacioFisico.ubicacion IN (:listaUbicaciones) ");
			parametros.put("listaUbicaciones", listaUbicaciones);
		}

		if (Objects.nonNull(listaIdAgrupadorEspacioFisico) && !listaIdAgrupadorEspacioFisico.isEmpty()) {
			queryBuilder.append("AND agrupadores.idAgrupadorEspacioFisico IN (:listaIdAgrupadorEspacioFisico) ");
			parametros.put("listaIdAgrupadorEspacioFisico", listaIdAgrupadorEspacioFisico);
		}

		if (Objects.nonNull(salon) && !salon.isEmpty()) {
			queryBuilder.append("AND espacioFisico.salon LIKE :salon ");
			parametros.put("salon", "%"+salon.replaceAll("\\s+", " ").trim()+"%");
		}
		queryBuilder.append(") ");

		// Crea la consulta
		TypedQuery<FranjaHorariaBasicaDTO> typedQuery = entityManager.createQuery(queryBuilder.toString(), FranjaHorariaBasicaDTO.class);

		// Asigna los parámetros a la consulta
		for (Map.Entry<String, Object> entry : parametros.entrySet()) {
			typedQuery.setParameter(entry.getKey(), entry.getValue());
		}

		return typedQuery.getResultList();
	}

	@Override
	public List<FranjaHorariaBasicaDTO> consultarFranjasHorariasDeDocentesAsociadosACurso(Long idCurso) {
		StringBuilder queryBuilder = new StringBuilder();
				
		queryBuilder.append("SELECT DISTINCT NEW co.edu.unicauca.sgph.gestionplanificacion.manual.domain.model.FranjaHorariaBasicaDTO( ");	
		queryBuilder.append("horarios.idHorario, horarios.dia, horarios.horaInicio, horarios.horaFin ) ");
		queryBuilder.append("FROM CursoEntity curso ");		
		queryBuilder.append("JOIN curso.docentes docentes ");
		queryBuilder.append("JOIN curso.horarios horarios ");
		queryBuilder.append("WHERE docentes.idPersona IN (");		
		queryBuilder.append("SELECT docente.idPersona FROM DocenteEntity docente JOIN docente.cursos cursos WHERE cursos.idCurso = :idCurso ");
		queryBuilder.append(") ");

		Map<String, Object> parametros = new HashMap<>();

		parametros.put("idCurso", idCurso);

		// Crea la consulta
		TypedQuery<FranjaHorariaBasicaDTO> typedQuery = entityManager.createQuery(queryBuilder.toString(), FranjaHorariaBasicaDTO.class);

		// Asigna los parámetros a la consulta
		for (Map.Entry<String, Object> entry : parametros.entrySet()) {
			typedQuery.setParameter(entry.getKey(), entry.getValue());
		}

		return typedQuery.getResultList();
	}

	@Override
	public List<FranjaHorariaBasicaDTO> consultarFranjasHorariasDeSemestrePorCurso(Long idCurso) {
		StringBuilder queryBuilder = new StringBuilder();
		
		queryBuilder.append("SELECT DISTINCT NEW co.edu.unicauca.sgph.gestionplanificacion.manual.domain.model.FranjaHorariaBasicaDTO( ");	
		queryBuilder.append("horarios.idHorario, horarios.dia, horarios.horaInicio, horarios.horaFin, asignatura.idAsignatura) ");
		queryBuilder.append("FROM CursoEntity curso ");
		queryBuilder.append("JOIN curso.asignatura asignatura ");
		queryBuilder.append("JOIN curso.horarios horarios ");
		queryBuilder.append("WHERE asignatura.semestre = (SELECT asig.semestre FROM CursoEntity cur JOIN cur.asignatura asig WHERE cur.idCurso = :idCurso) ");
		
		Map<String, Object> parametros = new HashMap<>();

		parametros.put("idCurso", idCurso);

		// Crea la consulta
		TypedQuery<FranjaHorariaBasicaDTO> typedQuery = entityManager.createQuery(queryBuilder.toString(), FranjaHorariaBasicaDTO.class);

		// Asigna los parámetros a la consulta
		for (Map.Entry<String, Object> entry : parametros.entrySet()) {
			typedQuery.setParameter(entry.getKey(), entry.getValue());
		}

		return typedQuery.getResultList();
	}

}