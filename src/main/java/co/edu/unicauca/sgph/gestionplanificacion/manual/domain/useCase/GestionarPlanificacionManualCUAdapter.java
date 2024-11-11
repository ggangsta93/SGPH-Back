package co.edu.unicauca.sgph.gestionplanificacion.manual.domain.useCase;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unicauca.sgph.common.enums.DiaSemanaEnum;
import co.edu.unicauca.sgph.curso.aplication.output.CursoFormatterResultsIntPort;
import co.edu.unicauca.sgph.curso.aplication.output.GestionarCursoGatewayIntPort;
import co.edu.unicauca.sgph.curso.domain.model.Curso;
import co.edu.unicauca.sgph.docente.aplication.output.GestionarDocenteGatewayIntPort;
import co.edu.unicauca.sgph.docente.domain.model.Docente;
import co.edu.unicauca.sgph.docente.infrastructure.output.persistence.entity.EstadoDocenteEnum;
import co.edu.unicauca.sgph.espaciofisico.aplication.output.GestionarEspacioFisicoGatewayIntPort;
import co.edu.unicauca.sgph.espaciofisico.domain.model.EspacioFisico;
import co.edu.unicauca.sgph.espaciofisico.domain.model.HorarioEspacio;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.EstadoEspacioFisicoEnum;
import co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.input.GestionarPlanificacionManualCUIntPort;
import co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.output.GestionarPlanificacionManualGatewayIntPort;
import co.edu.unicauca.sgph.gestionplanificacion.manual.domain.model.FranjaHorariaBasicaDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTORequest.EliminarHorarioInDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTORequest.FiltroCursoPlanificacionDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTORequest.GenerarHorarioBaseInDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.CursoPlanificacionOutDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.FormatoPresentacionFranjaHorariaCursoDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.FranjaHorariaCursoDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.FranjaHorariaDocenteDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.FranjaHorariaEspacioFisicoDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.GenerarHorarioBaseOutDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.InfoGeneralCursosPorProgramaDTO;
import co.edu.unicauca.sgph.horario.aplication.output.GestionarHorarioGatewayIntPort;
import co.edu.unicauca.sgph.horario.domain.model.Horario;
import co.edu.unicauca.sgph.horario.infrastructure.input.DTORequest.CrearActualizarDocentesCursoInDTO;
import co.edu.unicauca.sgph.horario.infrastructure.input.DTORequest.CrearActualizarHorarioCursoInDTO;
import co.edu.unicauca.sgph.horario.infrastructure.input.DTORequest.FiltroFranjaHorariaDisponibleCursoDTO;
import co.edu.unicauca.sgph.horario.infrastructure.input.DTORequest.FranjaHorariaCursoAsociarInDTO;
import co.edu.unicauca.sgph.horario.infrastructure.input.DTOResponse.CrearActualizarDocentesCursoOutDTO;
import co.edu.unicauca.sgph.horario.infrastructure.input.DTOResponse.CrearActualizarHorarioCursoOutDTO;
import co.edu.unicauca.sgph.periodoacademico.aplication.output.GestionarPeriodoAcademicoGatewayIntPort;
import co.edu.unicauca.sgph.periodoacademico.domain.model.PeriodoAcademico;
import co.edu.unicauca.sgph.programa.aplication.output.GestionarProgramaGatewayIntPort;
import co.edu.unicauca.sgph.programa.domain.model.Programa;

public class GestionarPlanificacionManualCUAdapter implements GestionarPlanificacionManualCUIntPort {

	private static final String PROGRAMA_NO_PERMITIDO_PARA_EL_USUARIO = "Programa no permitido para el usuario";
	private static final String NO_EXISTE_PERIODO_ACADEMICO_VIGENTE = "No existe periodo académico vigente";
	private static final String FRANJA_REPETIDA = "Franja repetida";
	private static final String CRUCE_CON_HORARIO_DEL_DOCENTE = "Cruce con horario del docente: ";
	private static final String CRUCE_CON_HORARIO_DEL_ESPACIO_FISICO = "Cruce con horario del espacio físico: ";
	private static final String CURSO_NO_PERTENECE_A_PERIODO_ACADEMICO_VIGENTE = "El curso no pertenece al periodo académico vigente";

	private static final Logger logger = LoggerFactory.getLogger(GestionarPlanificacionManualCUAdapter.class);

	private GestionarPlanificacionManualGatewayIntPort gestionarPlanificacionManualGatewayIntPort;
	private CursoFormatterResultsIntPort cursoFormatterResultsIntPort;
	private GestionarDocenteGatewayIntPort gestionarDocenteGatewayIntPort;
	private GestionarEspacioFisicoGatewayIntPort gestionarEspacioFisicoGatewayIntPort;
	private GestionarCursoGatewayIntPort gestionarCursoGatewayIntPort;
	private GestionarHorarioGatewayIntPort gestionarHorarioGatewayIntPort;
	private GestionarPeriodoAcademicoGatewayIntPort gestionarPeriodoAcademicoGatewayIntPort;
	private GestionarProgramaGatewayIntPort gestionarProgramaGatewayIntPort;

	@Lazy
	@Autowired
	private GestionarPlanificacionManualCUAdapter gestionarPlanificacionManualCUAdapterEJB;

	@PersistenceContext
	EntityManager entityManager;

	public GestionarPlanificacionManualCUAdapter(
			GestionarPlanificacionManualGatewayIntPort gestionarPlanificacionManualGatewayIntPort,
			CursoFormatterResultsIntPort cursoFormatterResultsIntPort,
			GestionarDocenteGatewayIntPort gestionarDocenteGatewayIntPort,
			GestionarEspacioFisicoGatewayIntPort gestionarEspacioFisicoGatewayIntPort,
			GestionarCursoGatewayIntPort gestionarCursoGatewayIntPort,
			GestionarHorarioGatewayIntPort gestionarHorarioGatewayIntPort,
			GestionarPeriodoAcademicoGatewayIntPort gestionarPeriodoAcademicoGatewayIntPort,
			GestionarProgramaGatewayIntPort gestionarProgramaGatewayIntPort) {
		this.gestionarPlanificacionManualGatewayIntPort = gestionarPlanificacionManualGatewayIntPort;
		this.cursoFormatterResultsIntPort = cursoFormatterResultsIntPort;
		this.gestionarDocenteGatewayIntPort = gestionarDocenteGatewayIntPort;
		this.gestionarEspacioFisicoGatewayIntPort = gestionarEspacioFisicoGatewayIntPort;
		this.gestionarCursoGatewayIntPort = gestionarCursoGatewayIntPort;
		this.gestionarHorarioGatewayIntPort = gestionarHorarioGatewayIntPort;
		this.gestionarPeriodoAcademicoGatewayIntPort = gestionarPeriodoAcademicoGatewayIntPort;
		this.gestionarProgramaGatewayIntPort = gestionarProgramaGatewayIntPort;
	}

	
	/*************************************************
	 * Planificación manual - Gestionar horario principal
	 *************************************************/	
	/**
	 * @see co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.input.GestionarPlanificacionManualCUIntPort#consultarCursosPlanificacionPorFiltro(co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTORequest.FiltroCursoPlanificacionDTO)
	 */
	@Override
	public Page<CursoPlanificacionOutDTO> consultarCursosPlanificacionPorFiltro(
			FiltroCursoPlanificacionDTO filtroCursoPlanificacionDTO) {

		// Se consulta el periodo académico que está 'ABIERTO'
		PeriodoAcademico periodoAcademicoVigente = gestionarPeriodoAcademicoGatewayIntPort
				.consultarPeriodoAcademicoVigente();

		// Se valida que exista periodo académico vigente
		if (Objects.isNull(periodoAcademicoVigente)) {
			throw new RuntimeException(NO_EXISTE_PERIODO_ACADEMICO_VIGENTE);

		}

		Page<CursoPlanificacionOutDTO> listaCursosDTO = this.gestionarPlanificacionManualGatewayIntPort
				.consultarCursosPlanificacionPorFiltro(filtroCursoPlanificacionDTO,
						periodoAcademicoVigente.getIdPeriodoAcademico());

		for (CursoPlanificacionOutDTO cursoDTO : listaCursosDTO) {
			cursoDTO.setDocentes(
					this.gestionarDocenteGatewayIntPort.consultarNombresDocentesPorIdCurso(cursoDTO.getIdCurso()));
			cursoDTO.setHorarios(gestionarEspacioFisicoGatewayIntPort
					.consultarEspacioFisicoHorarioPorIdCurso(cursoDTO.getIdCurso(), Boolean.TRUE));
			cursoDTO.setHorariosSecundarios(gestionarEspacioFisicoGatewayIntPort
					.consultarEspacioFisicoHorarioPorIdCurso(cursoDTO.getIdCurso(), Boolean.FALSE));
		}
		return listaCursosDTO;
	}

	/**
	 * @see co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.input.GestionarPlanificacionManualCUIntPort#consultarInfoGeneralCursosPorPrograma(java.lang.Long)
	 */
	@Override
	public InfoGeneralCursosPorProgramaDTO consultarInfoGeneralCursosPorPrograma(Long idPrograma) {
		// Se consulta el periodo académico que está 'ABIERTO'
		PeriodoAcademico periodoAcademicoVigente = gestionarPeriodoAcademicoGatewayIntPort
				.consultarPeriodoAcademicoVigente();
		// Se valida que exista periodo académico vigente
		if (Objects.isNull(periodoAcademicoVigente)) {
			throw new RuntimeException(NO_EXISTE_PERIODO_ACADEMICO_VIGENTE);
		}
		return this.gestionarPlanificacionManualGatewayIntPort.consultarInfoGeneralCursosPorPrograma(idPrograma,
				periodoAcademicoVigente.getIdPeriodoAcademico());
	}

	/**
	 * @see co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.input.GestionarPlanificacionManualCUIntPort#crearActualizarDocentesCursoDTO(co.edu.unicauca.sgph.horario.infrastructure.input.DTORequest.CrearActualizarDocentesCursoInDTO)
	 */
	@Override
	@Transactional
	public CrearActualizarDocentesCursoOutDTO crearActualizarDocentesCursoDTO(
			CrearActualizarDocentesCursoInDTO crearActualizarDocentesCursoInDTO) {
		List<String> mensajesCruces = new ArrayList<>();
		CrearActualizarDocentesCursoOutDTO crearActualizarDocentesCursoOutDTO = new CrearActualizarDocentesCursoOutDTO();
		crearActualizarDocentesCursoOutDTO.setEsExitoso(Boolean.FALSE);
		crearActualizarDocentesCursoOutDTO.setLstMensajesSolapamientos(mensajesCruces);

		// Se consulta periodo académico vigente
		PeriodoAcademico periodoAcademicoVigente = gestionarPeriodoAcademicoGatewayIntPort
				.consultarPeriodoAcademicoVigente();

		/*
		 * Se valida que exista periodo académico vigente y que el curso a vincular
		 * pertenezca al mismo
		 */
		if (Objects.isNull(periodoAcademicoVigente)) {
			throw new RuntimeException(NO_EXISTE_PERIODO_ACADEMICO_VIGENTE);
		}

		if (Boolean.FALSE.equals(this.cursoPerteneceAlPeriodoAcademicoVigente(
				crearActualizarDocentesCursoInDTO.getIdCurso(), periodoAcademicoVigente.getIdPeriodoAcademico()))) {
			throw new RuntimeException(CURSO_NO_PERTENECE_A_PERIODO_ACADEMICO_VIGENTE);

		}

		// Se validan que los docentes existan y estén en estado ACTIVO
		this.validarExistenciaYEstadoActivoDocentes(crearActualizarDocentesCursoInDTO);

		// Se consulta el curso
		Curso curso = this.gestionarCursoGatewayIntPort
				.consultarCursoPorIdCurso(crearActualizarDocentesCursoInDTO.getIdCurso());

		List<Docente> docentesEliminar = curso.getDocentes().stream().filter(
				doc -> !crearActualizarDocentesCursoInDTO.getListaIdPersona().contains(doc.getPersona().getIdPersona()))
				.toList();

		List<Docente> docentesActualizar = new ArrayList<>();

		// Se consulta los horarios del curso
		List<Horario> listaHorarioCurso = this.gestionarHorarioGatewayIntPort
				.consultarHorarioPorCurso(new Curso(crearActualizarDocentesCursoInDTO.getIdCurso()));

		for (Long idPersona : crearActualizarDocentesCursoInDTO.getListaIdPersona()) {
			Docente docente = curso.getDocentes().stream()
					.filter(doc -> doc.getPersona().getIdPersona().equals(idPersona)).findFirst().orElse(null);

			if (Objects.isNull(docente)) {
				docente = this.gestionarDocenteGatewayIntPort.consultarDocentePorIdPersona(idPersona);
				docente.getCursos().add(curso);

				for (Horario horario : listaHorarioCurso) {
					List<Object[]> lstCruces = this.gestionarPlanificacionManualGatewayIntPort
							.consultarCruceHorarioDocentePorIdPersona(idPersona, horario.getDia(),
									horario.getHoraInicio(), horario.getHoraFin(),
									periodoAcademicoVigente.getIdPeriodoAcademico());
					if (!lstCruces.isEmpty()) {
						mensajesCruces.add(String.format(
								"El docente con identificación %s%s se solapa con la franja %s del curso %s",
								docente.getPersona().getTipoIdentificacion().getCodigoTipoIdentificacion(),
								docente.getPersona().getNumeroIdentificacion(),
								horario.getDia().toString() + " " + horario.getHoraInicio().toString() + "-"
										+ horario.getHoraFin().toString(),
								curso.getAsignatura().getNombre() + " " + curso.getGrupo()));
					}
				}
			}
			docentesActualizar.add(docente);
		}

		if (mensajesCruces.isEmpty()) {
			for (Docente docente : docentesActualizar) {
				this.gestionarDocenteGatewayIntPort.guardarDocente(docente);
			}
			for (Docente docente : docentesEliminar) {
				List<Curso> lstCursos = docente.getCursos().stream()
						.filter(cur -> !cur.getIdCurso().equals(crearActualizarDocentesCursoInDTO.getIdCurso()))
						.toList();
				docente.setCursos(lstCursos);
				this.gestionarDocenteGatewayIntPort.guardarDocente(docente);
			}
			curso.setDocentes(docentesActualizar);
			this.gestionarCursoGatewayIntPort.guardarCurso(curso);
			crearActualizarDocentesCursoOutDTO.setEsExitoso(Boolean.TRUE);
		}		

		return crearActualizarDocentesCursoOutDTO;
	}

	private void validarExistenciaYEstadoActivoDocentes(
			CrearActualizarDocentesCursoInDTO crearActualizarDocentesCursoInDTO) {
		List<String> docentesDiferenteDeActivo = new ArrayList<>();

		for (Long idPersona : crearActualizarDocentesCursoInDTO.getListaIdPersona()) {
			Docente docente = this.gestionarDocenteGatewayIntPort.consultarDocentePorIdPersona(idPersona);
			if (Objects.isNull(docente)) {
				throw new RuntimeException("No existe docente con idPersona: " + idPersona.toString());
			} else {
				if (!docente.getEstado().equals(EstadoDocenteEnum.ACTIVO)) {
					docentesDiferenteDeActivo.add(idPersona.toString());
				}
			}

		}
		if (!docentesDiferenteDeActivo.isEmpty()) {
			throw new RuntimeException("Estado no permitido para los docentes con idPersona: "
					+ String.join(", ", docentesDiferenteDeActivo));
		}
	}

	/**
	 * @see co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.input.GestionarPlanificacionManualCUIntPort#crearActualizarHorarioCurso(co.edu.unicauca.sgph.horario.infrastructure.input.DTORequest.CrearActualizarHorarioCursoInDTO)
	 */
	@Override
	public CrearActualizarHorarioCursoOutDTO crearActualizarHorarioCurso(
			CrearActualizarHorarioCursoInDTO crearActualizarHorarioCursoInDTO) {
		CrearActualizarHorarioCursoOutDTO crearActualizarHorarioCursoOutDTO = new CrearActualizarHorarioCursoOutDTO();

		try {
			this.gestionarPlanificacionManualCUAdapterEJB
					.validarYCrearActualizarHorarioCurso(crearActualizarHorarioCursoInDTO, Boolean.TRUE);
			crearActualizarHorarioCursoOutDTO.setEsExitoso(Boolean.TRUE);
		} catch (RuntimeException e) {
			crearActualizarHorarioCursoOutDTO.setLstMensajesSolapamientos(Arrays.asList(e.getMessage()));
			crearActualizarHorarioCursoOutDTO.setEsExitoso(Boolean.FALSE);
		}

		return crearActualizarHorarioCursoOutDTO;
	}

	@Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
	public void validarYCrearActualizarHorarioCurso(CrearActualizarHorarioCursoInDTO crearActualizarHorarioCursoInDTO,
			Boolean eliminarFranjas) throws RuntimeException {
		Long idCurso = crearActualizarHorarioCursoInDTO.getIdCurso();

		// Se consulta periodo académico vigente
		PeriodoAcademico periodoAcademicoVigente = gestionarPeriodoAcademicoGatewayIntPort
				.consultarPeriodoAcademicoVigente();

		/*
		 * Se valida que exista periodo académico vigente y que el curso a consultar
		 * pertenezca al mismo
		 */
		if (Objects.isNull(periodoAcademicoVigente)) {
			throw new RuntimeException(NO_EXISTE_PERIODO_ACADEMICO_VIGENTE);
		}

		if (Boolean.FALSE.equals(this.cursoPerteneceAlPeriodoAcademicoVigente(idCurso,
				periodoAcademicoVigente.getIdPeriodoAcademico()))) {
			throw new RuntimeException(CURSO_NO_PERTENECE_A_PERIODO_ACADEMICO_VIGENTE);
		}

		Object[] infoCurso = new Object[4];

		// Se validan las franjas que ingresan por parámetro
		this.validarRestriccionesInicialesYConsultarInfoBasicaCurso(crearActualizarHorarioCursoInDTO, infoCurso,
				eliminarFranjas);
		/*
		 * Se elimina de la base de datos las franjas horarias del curso que no están en
		 * las franjas a actualizar
		 */
		this.eliminarFranjasDelCurso(crearActualizarHorarioCursoInDTO, eliminarFranjas);
		/*
		 * Se validan que las franjas a actualizar no se solapen con las franjas de los
		 * cursos del mismo semestre
		 */
		this.validarSolapamientoFranjasAActualizarConCursosDelMismoSemestre(crearActualizarHorarioCursoInDTO,
				(Long) infoCurso[0], periodoAcademicoVigente.getIdPeriodoAcademico(), eliminarFranjas, (Integer) infoCurso[3]);

		this.validarRestriccionesEspacioFisico(crearActualizarHorarioCursoInDTO, (Integer) infoCurso[1]);

		// Se validan cruces
		for (FranjaHorariaCursoAsociarInDTO franja : crearActualizarHorarioCursoInDTO
				.getListaFranjaHorariaCursoAsociarInDTO()) {

			if (Objects.isNull(franja.getIdHorario())) {

				Long idEspacioFisico = franja.getIdEspacioFisico();
				DiaSemanaEnum dia = franja.getDia();
				LocalTime horaInicio = this.convertirToLocalTime(franja.getHoraInicio());
				LocalTime horaFin = this.convertirToLocalTime(franja.getHoraFin());

				// Se valida que la franja no se cruce con el espacio físico
				this.validarCruceHorarioEspacioFisico(idEspacioFisico, dia, horaInicio, horaFin,
						periodoAcademicoVigente, franja, idCurso, eliminarFranjas);

				// Se valida que la franja no se cruce con los horarios de los docentes
				this.validarCruceHorarioDocente(idEspacioFisico, dia, horaInicio, horaFin, periodoAcademicoVigente,
						idCurso);

				// Se crea horario principal
				Horario horario = new Horario(dia, horaInicio, horaFin, new Curso(idCurso),
						Arrays.asList(new EspacioFisico(idEspacioFisico)));
				this.gestionarHorarioGatewayIntPort.crearHorarioPrincipal(horario);

			}
		} // Cierre del for	
	}

	/**
	 * Método encargado de realizar las validaciones iniciales </br>
	 * 
	 * @author apedro
	 * 
	 * @param crearActualizarHorarioCursoInDTO Información recibida por parámetro
	 */
	private void validarRestriccionesInicialesYConsultarInfoBasicaCurso(CrearActualizarHorarioCursoInDTO crearActualizarHorarioCursoInDTO, Object[] infoBasicaCurso, Boolean eliminarFranjas) {
		// Se validan que las franjas a actualizar no se solapen entre ellas
		this.validarSolapamientoEntreFranjasAActualizar(crearActualizarHorarioCursoInDTO);
		// Se validan que las franjas a actualizar no se encuentren repetidas
		this.validarFranjasHorariasAActualizarNoSeanRepetidas(crearActualizarHorarioCursoInDTO);

		// Se consulta información básica curso por referencia
		this.consultarInformacionBasicaCurso(crearActualizarHorarioCursoInDTO, infoBasicaCurso);

		// Se valida la cantidad de horas de las franjas a actualizar
		this.validarCantidadHorasPermitidasParaCurso(crearActualizarHorarioCursoInDTO, (Integer) infoBasicaCurso[2], eliminarFranjas);			
	}
	
	private void validarRestriccionesEspacioFisico(CrearActualizarHorarioCursoInDTO crearActualizarHorarioCursoInDTO,
			Integer capacidadCurso) {
		List<EspacioFisico> lstEspacioFisicoAActualizar = this.gestionarEspacioFisicoGatewayIntPort
				.consultarCapacidadEstadoYSalonPorListaIdEspacioFisico(
						crearActualizarHorarioCursoInDTO.getListaFranjaHorariaCursoAsociarInDTO().stream()
								.map(FranjaHorariaCursoAsociarInDTO::getIdEspacioFisico).toList());

		// Se valida que el estado sea ACTIVO de los espacios físicos
		this.validarEstadoActivoEspaciosFisicos(crearActualizarHorarioCursoInDTO, lstEspacioFisicoAActualizar);
		// Se valida capacidad de espacios físicos contra cupo del curso
		this.validarCapacidadEspaciosFisicosContraCupoDelCurso(capacidadCurso, lstEspacioFisicoAActualizar);
	}
	
	private void consultarInformacionBasicaCurso(CrearActualizarHorarioCursoInDTO crearActualizarHorarioCursoInDTO,
			Object[] infoBasicaCurso) {
		// Se consulta información inicial: idAsignatura, cupo, cantidad horas semana, semestre
		Object[] informacionCurso = (Object[]) this.gestionarPlanificacionManualGatewayIntPort
				.consultarIdAsignaturaCupoYCantidadHorasDeCusoPorCurso(crearActualizarHorarioCursoInDTO.getIdCurso());
		// Asigna la información consultada al parámetro infoCurso
		System.arraycopy(informacionCurso, 0, infoBasicaCurso, 0, informacionCurso.length);
	}
	
	/**
	 * Método encargado de convertir una franja en formato {día}
	 * {horaInicio}-{horaFin}. Ej. Lunes 07:00-09:00 </br>
	 * 
	 * @author apedro
	 * 
	 * @param franja
	 * @return
	 */
	private String obtenerFranjaFormatoEstandar(Object franja) {
		if (franja instanceof FranjaHorariaCursoAsociarInDTO) {
			FranjaHorariaCursoAsociarInDTO dto = (FranjaHorariaCursoAsociarInDTO) franja;
			return dto.getDia() + " " + dto.getHoraInicio() + "-" + dto.getHoraFin();
		} else if (franja instanceof FranjaHorariaCursoDTO) {
			FranjaHorariaCursoDTO dto = (FranjaHorariaCursoDTO) franja;
			return dto.getDia() + " " + dto.getHoraInicio() + "-" + dto.getHoraFin();
		} else if (franja instanceof Horario) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
			Horario dto = (Horario) franja;
			return dto.getDia() + " " + dto.getHoraInicio().format(formatter) + "-"
					+ dto.getHoraFin().format(formatter);
		} else {
			return null;
		}
	}

	private void validarCruceHorarioEspacioFisico(Long idEspacioFisico, DiaSemanaEnum dia, LocalTime horaInicio,
			LocalTime horaFin, PeriodoAcademico periodoAcademicoVigente,
			FranjaHorariaCursoAsociarInDTO franjaHorariaCursoAsociarInDTO, Long idCurso, Boolean eliminarFranjas) {
		List<Horario> listaCruceHorarioEspacioFisico = this.gestionarPlanificacionManualGatewayIntPort
				.consultarCruceHorarioEspacioFisico(Arrays.asList(idEspacioFisico), dia, horaInicio, horaFin,
						periodoAcademicoVigente.getIdPeriodoAcademico());
		
		if (!listaCruceHorarioEspacioFisico.isEmpty()) {

			List<Horario> listaCruceHorarioEspacioFisicoMismoCurso= listaCruceHorarioEspacioFisico.stream()
					.filter(horario -> franjaHorariaCursoAsociarInDTO.getDia().equals(horario.getDia())
							&& franjaHorariaCursoAsociarInDTO.getHoraInicio()
									.equals(horario.getHoraInicio().format(DateTimeFormatter.ofPattern("HH:mm")))
							&& franjaHorariaCursoAsociarInDTO.getHoraFin()
									.equals(horario.getHoraFin().format(DateTimeFormatter.ofPattern("HH:mm")))
							&& idCurso.equals(horario.getCurso().getIdCurso()))
					.toList();
			
			if (Boolean.FALSE.equals(eliminarFranjas) && !listaCruceHorarioEspacioFisicoMismoCurso.isEmpty()) {
				throw new RuntimeException(FRANJA_REPETIDA);
			} else {
				throw new RuntimeException(CRUCE_CON_HORARIO_DEL_ESPACIO_FISICO + String.join(", ",
						listaCruceHorarioEspacioFisico.stream()
								.map(horario -> horario.getEspaciosFisicos().get(0).getSalon())
								.toList()));
			}

		}
	}

	private void validarCruceHorarioDocente(Long idEspacioFisico, DiaSemanaEnum dia, LocalTime horaInicio,
			LocalTime horaFin, PeriodoAcademico periodoAcademicoVigente, Long idCurso) {
		List<Object[]> listaCruceHorarioDocente = this.gestionarPlanificacionManualGatewayIntPort
				.consultarCruceHorarioDocente(idCurso, dia, horaInicio, horaFin,
						periodoAcademicoVigente.getIdPeriodoAcademico());

		if (!listaCruceHorarioDocente.isEmpty()) {
			throw new RuntimeException(CRUCE_CON_HORARIO_DEL_DOCENTE + String.join(", ", listaCruceHorarioDocente
					.stream().map(obj -> String.valueOf(obj[2])).distinct().toList()));
		}
	}

	private LocalTime convertirToLocalTime(String hora) {
		return LocalTime.of(Integer.parseInt(hora.split(":")[0]), Integer.parseInt(hora.split(":")[1]));
	}

	private void eliminarFranjasDelCurso(CrearActualizarHorarioCursoInDTO crearActualizarHorarioCursoInDTO,
			Boolean eliminarFranjas) {
		if (Boolean.TRUE.equals(eliminarFranjas)) {
			// Se consulta las franjas horarias del curso
			List<Horario> listaFranjasHoriasActualDelCurso = this.gestionarHorarioGatewayIntPort
					.consultarHorarioPorCurso(new Curso(crearActualizarHorarioCursoInDTO.getIdCurso()));
			// Se filtran aquellas franjas que aún se mantienen
			List<Horario> listaFranjasHorariasQueSeMantienen = listaFranjasHoriasActualDelCurso.stream()
					.filter(horario -> crearActualizarHorarioCursoInDTO.getListaFranjaHorariaCursoAsociarInDTO()
							.stream().anyMatch(
									fraHorCurso -> Objects.equals(fraHorCurso.getIdHorario(), horario.getIdHorario())))
					.toList();
			// Se eliminan las franjas horarias del curso que ya no se mantienen
			listaFranjasHoriasActualDelCurso.removeAll(listaFranjasHorariasQueSeMantienen);
			List<Horario> listaHorarioEliminar = listaFranjasHoriasActualDelCurso;

			for (Horario horario : listaHorarioEliminar) {
				this.gestionarHorarioGatewayIntPort.eliminarHorario(horario);
			}
			
			/*
			 * Se hace flush para reflejar los cambios cuando se invoca posteriormente
			 * consultarFranjasHorariasDeSemestrePorCurso
			 */
			entityManager.flush();
		}
	}

	private void validarEstadoActivoEspaciosFisicos(CrearActualizarHorarioCursoInDTO crearActualizarHorarioCursoInDTO,
			List<EspacioFisico> lstEspacioFisicoAActualizar) {
		List<String> espaciosFisicosDiferenteDeActivo = new ArrayList<>();
		for (EspacioFisico espacioFisico : lstEspacioFisicoAActualizar) {
			if (!espacioFisico.getEstado().equals(EstadoEspacioFisicoEnum.ACTIVO)) {
				espaciosFisicosDiferenteDeActivo.add(espacioFisico.getSalon());
			}
		}
		if (!espaciosFisicosDiferenteDeActivo.isEmpty()) {
			throw new RuntimeException("Espacio físico INACTIVO: "
					+ String.join(", ", espaciosFisicosDiferenteDeActivo));
		}
	}

	private void validarCapacidadEspaciosFisicosContraCupoDelCurso(Integer capacidadCurso,
			List<EspacioFisico> lstEspacioFisicoAActualizar) {

		List<String> espaciosFisicosInsuficienteCapacidad = new ArrayList<>();
		for (EspacioFisico espacioFisico : lstEspacioFisicoAActualizar) {
			if (espacioFisico.getCapacidad() < capacidadCurso) {
				espaciosFisicosInsuficienteCapacidad.add(espacioFisico.getSalon());
			}
		}
		if (!espaciosFisicosInsuficienteCapacidad.isEmpty()) {
			throw new RuntimeException("Capacidad insuficiente para los espacios físicos: "
					+ String.join(", ", espaciosFisicosInsuficienteCapacidad));
		}
	}

	private void validarSolapamientoFranjasAActualizarConCursosDelMismoSemestre(
			CrearActualizarHorarioCursoInDTO crearActualizarHorarioCursoInDTO, Long idAsignatura,
			Long idPeriodoAcademicoVigente,Boolean eliminarFranjas, Integer semestre) {
		
		// Si el semestre es -1 es porque son electivas y esta restricción no aplica
		if(semestre.equals(-1)) {
			return ;			
		}		
		
		// Se consultan las franjas horarias de cursos del semestre
		List<FranjaHorariaBasicaDTO> lstFranjasHorariasCursosSemestre = this.gestionarPlanificacionManualGatewayIntPort
				.consultarFranjasHorariasDeSemestrePorCurso(crearActualizarHorarioCursoInDTO.getIdCurso(), idAsignatura,
						idPeriodoAcademicoVigente);

		List<String> franjasQueSolapan = new ArrayList<>();
		for (FranjaHorariaCursoAsociarInDTO franjaActualizar : crearActualizarHorarioCursoInDTO
				.getListaFranjaHorariaCursoAsociarInDTO()) {
			FranjaHorariaBasicaDTO franjaHorariaBasicaDTO = new FranjaHorariaBasicaDTO(franjaActualizar.getDia(),
					this.convertirToLocalTime(franjaActualizar.getHoraInicio()),
					this.convertirToLocalTime(franjaActualizar.getHoraFin()));

			if (this.seSuperponeConFranjasCursosSemestre(franjaHorariaBasicaDTO, lstFranjasHorariasCursosSemestre,
					idAsignatura)) {
				franjasQueSolapan.add(franjaHorariaBasicaDTO.getDia() + " " + franjaHorariaBasicaDTO.getHoraInicio()
						+ "-" + franjaHorariaBasicaDTO.getHoraFin());
			}

		}

		if (!franjasQueSolapan.isEmpty()) {
			if (Boolean.TRUE.equals(eliminarFranjas)) {
				throw new RuntimeException("Las siguientes franjas solapan franjas de cursos del mismo semestre: "
						+ String.join(", ", franjasQueSolapan));
			} else {
				throw new RuntimeException("La franja solapa franjas de cursos del mismo semestre.");
			}
		}
	}

	private void validarFranjasHorariasAActualizarNoSeanRepetidas(
			CrearActualizarHorarioCursoInDTO crearActualizarHorarioCursoInDTO) {
		// Se validan que las franjas a actualizar no se repitan entre ellas

		// Se agrupan por franja
		Map<String, List<FranjaHorariaCursoAsociarInDTO>> mapaFranjaHorariasAGrupadas = crearActualizarHorarioCursoInDTO
				.getListaFranjaHorariaCursoAsociarInDTO().stream()
				.collect(Collectors.groupingBy(this::obtenerFranjaFormatoEstandar));

		List<String> franjasRepetidas = new ArrayList<>();
		for (Map.Entry<String, List<FranjaHorariaCursoAsociarInDTO>> entry : mapaFranjaHorariasAGrupadas.entrySet()) {
			// Si existe una llave que tenga más de un franja en la lista es porque repite
			if (entry.getValue().size() > 1) {
				franjasRepetidas.add(this.obtenerFranjaFormatoEstandar(entry.getValue().get(0)));
			}
		}

		if (!franjasRepetidas.isEmpty()) {
			throw new RuntimeException(
					"Las siguientes franjas están repetidas: " + String.join(", ", franjasRepetidas));
		}
	}

	private void validarSolapamientoEntreFranjasAActualizar(
			CrearActualizarHorarioCursoInDTO crearActualizarHorarioCursoInDTO) {

		/*
		 * Se convierte de lista FranjaHorariaCursoAsociarInDTO a lista
		 * FranjaHorariaBasicaDTO
		 */
		List<FranjaHorariaBasicaDTO> listaFranjasAActualizar = new ArrayList<>();
		for (FranjaHorariaCursoAsociarInDTO franjaActualizar : crearActualizarHorarioCursoInDTO
				.getListaFranjaHorariaCursoAsociarInDTO()) {
			listaFranjasAActualizar.add(new FranjaHorariaBasicaDTO(franjaActualizar.getDia(),
					this.convertirToLocalTime(franjaActualizar.getHoraInicio()),
					this.convertirToLocalTime(franjaActualizar.getHoraFin())));
		}

		for (FranjaHorariaBasicaDTO franjaActualizar : listaFranjasAActualizar) {

			// Se filtra la misma franja a evaluar para evitar un falso solapamiento
			List<FranjaHorariaBasicaDTO> listaFranjasFiltradas = listaFranjasAActualizar.stream()
					.filter(franja -> !(franja.getDia().equals(franjaActualizar.getDia())
							&& franja.getHoraInicio().equals(franjaActualizar.getHoraInicio())
							&& franja.getHoraFin().equals(franjaActualizar.getHoraFin())))
					.toList();

			if (this.seSuperponeConListaFranjas(franjaActualizar, listaFranjasFiltradas)) {
				throw new RuntimeException("Las franjas a actualizar se están solapando");
			}
		}
	}

	private void validarCantidadHorasPermitidasParaCurso(
			CrearActualizarHorarioCursoInDTO crearActualizarHorarioCursoInDTO, Integer horasSemana,
			Boolean eliminarFranjas) {
		int sumaHorasFranjasAActualizar = 0;
		for (FranjaHorariaCursoAsociarInDTO franjaHorariaCursoAsociarInDTO : crearActualizarHorarioCursoInDTO
				.getListaFranjaHorariaCursoAsociarInDTO()) {
			sumaHorasFranjasAActualizar += Integer.parseInt(franjaHorariaCursoAsociarInDTO.getHoraFin().split(":")[0])
					- Integer.parseInt(franjaHorariaCursoAsociarInDTO.getHoraInicio().split(":")[0]);
		}
		
		if (Boolean.FALSE.equals(eliminarFranjas)) {
			Long cantidadHoras = this.gestionarPlanificacionManualGatewayIntPort
					.consultarCantidadHorasActualCurso(crearActualizarHorarioCursoInDTO.getIdCurso());
			if (cantidadHoras + sumaHorasFranjasAActualizar > horasSemana) {
				throw new RuntimeException(
						"La franja a actualizar supera la cantidad de horas permitidas para el curso");
			}
		} else {
			if (sumaHorasFranjasAActualizar > horasSemana) {
				throw new RuntimeException(
						"Las franjas a actualizar superan la cantidad de horas permitidas para el curso");
			}
		}
	}

	/**
	 * @see co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.input.GestionarPlanificacionManualCUIntPort#consultarFranjasHorariasDisponiblesPorCurso(co.edu.unicauca.sgph.horario.infrastructure.input.DTORequest.FiltroFranjaHorariaDisponibleCursoDTO)
	 */
	@Override
	public List<FranjaHorariaCursoDTO> consultarFranjasHorariasDisponiblesPorCurso(
			FiltroFranjaHorariaDisponibleCursoDTO filtroFranjaHorariaDisponibleCursoDTO) {

		// Se consulta periodo académico vigente
		PeriodoAcademico periodoAcademicoVigente = gestionarPeriodoAcademicoGatewayIntPort
				.consultarPeriodoAcademicoVigente();
		/*
		 * Se valida que exista periodo académico vigente y que el curso a consultar
		 * pertenezca al mismo
		 */
		if (Objects.isNull(periodoAcademicoVigente)) {
			throw new RuntimeException(NO_EXISTE_PERIODO_ACADEMICO_VIGENTE);
		}

		if (Boolean.FALSE.equals(this.cursoPerteneceAlPeriodoAcademicoVigente(
				filtroFranjaHorariaDisponibleCursoDTO.getIdCurso(), periodoAcademicoVigente.getIdPeriodoAcademico()))) {
			throw new RuntimeException(CURSO_NO_PERTENECE_A_PERIODO_ACADEMICO_VIGENTE);
		}

		// Se consulta las franjas horarias actuales del curso
		List<FranjaHorariaBasicaDTO> lstFranjasHorariasCurso = this.gestionarPlanificacionManualGatewayIntPort
				.consultarFranjasHorariasDeCursoPorCurso(filtroFranjaHorariaDisponibleCursoDTO.getIdCurso());

		// Se consultan las franjas horarias de los espacios físicos
		Map<Long, List<FranjaHorariaBasicaDTO>> mapaFranjasHorariasPorEspacioFisico = this.gestionarPlanificacionManualGatewayIntPort
				.consultarFranjasHorariasDeEspaciosFisicosPorCursoYCriterios(
						filtroFranjaHorariaDisponibleCursoDTO.getIdCurso(),
						filtroFranjaHorariaDisponibleCursoDTO.getListaIdUbicacion(),
						filtroFranjaHorariaDisponibleCursoDTO.getListaIdTipoEspacioFisico(),
						filtroFranjaHorariaDisponibleCursoDTO.getListaIdAgrupadorEspacioFisico(),
						filtroFranjaHorariaDisponibleCursoDTO.getSalon(),
						periodoAcademicoVigente.getIdPeriodoAcademico());

		// Se consultan las franjas horarias de cursos del semestre
		List<FranjaHorariaBasicaDTO> lstFranjasHorariasCursosSemestre = this.gestionarPlanificacionManualGatewayIntPort
				.consultarFranjasHorariasDeSemestrePorCurso(filtroFranjaHorariaDisponibleCursoDTO.getIdCurso(),
						filtroFranjaHorariaDisponibleCursoDTO.getIdAsignatura(),
						periodoAcademicoVigente.getIdPeriodoAcademico());

		// Se consultan las franjas horarias de los docentes asociados al curso
		List<FranjaHorariaBasicaDTO> lstFranjasHorariasDocentes = this.gestionarPlanificacionManualGatewayIntPort
				.consultarFranjasHorariasDeDocentesAsociadosACurso(filtroFranjaHorariaDisponibleCursoDTO.getIdCurso(),
						periodoAcademicoVigente.getIdPeriodoAcademico());

		if (Boolean.FALSE.equals(filtroFranjaHorariaDisponibleCursoDTO.getEsPrincipal())) {
			return this.consultarYValidarFranjasDisponiblesParaHorarioSecundario(filtroFranjaHorariaDisponibleCursoDTO,
					mapaFranjasHorariasPorEspacioFisico);
		} else {
			return this.consultarYValidarFranjasDisponiblesParaHorarioPrincipal(filtroFranjaHorariaDisponibleCursoDTO,
					mapaFranjasHorariasPorEspacioFisico, lstFranjasHorariasDocentes, lstFranjasHorariasCursosSemestre,
					lstFranjasHorariasCurso);
		}		
	}
	
	
	private List<FranjaHorariaCursoDTO> consultarYValidarFranjasDisponiblesParaHorarioPrincipal(
			FiltroFranjaHorariaDisponibleCursoDTO filtroFranjaHorariaDisponibleCursoDTO,
			Map<Long, List<FranjaHorariaBasicaDTO>> mapaFranjasHorariasPorEspacioFisico,
			List<FranjaHorariaBasicaDTO> lstFranjasHorariasDocentes,
			List<FranjaHorariaBasicaDTO> lstFranjasHorariasCursosSemestre,
			List<FranjaHorariaBasicaDTO> lstFranjasHorariasCurso) {

		// Lista de las franjas disponibles para el curso
		List<FranjaHorariaCursoDTO> listaDeFranjasDisponibles = new ArrayList<>();

		List<FranjaHorariaBasicaDTO> listaFranjasCandidatas = this
				.consultarFranjasCandidatas(filtroFranjaHorariaDisponibleCursoDTO);

		// Se recorre cada espacio físico para evaluarle las franjas candidatas
		for (Map.Entry<Long, List<FranjaHorariaBasicaDTO>> entry : mapaFranjasHorariasPorEspacioFisico.entrySet()) {

			// Se recorre cada franja candidata para validarla contra las restricciones
			for (FranjaHorariaBasicaDTO franjaCandidata : listaFranjasCandidatas) {

				// Restricciones
				Boolean noseSuperponeConFranjasEspaciosFisicos = !this
						.seSuperponeConFranjasEspaciosFisicos(franjaCandidata, entry.getValue());
				Boolean noseSuperponeConFranjasDocentes = !this.seSuperponeConFranjasDocentes(franjaCandidata,
						lstFranjasHorariasDocentes);
				Boolean noseSuperponeConFranjasCursosSemestre = !this.seSuperponeConFranjasCursosSemestre(
						franjaCandidata, lstFranjasHorariasCursosSemestre,
						filtroFranjaHorariaDisponibleCursoDTO.getIdAsignatura());
				Boolean noseSuperponeConFranjasDelMismoCurso = !this.seSuperponeConFranjasCurso(franjaCandidata,
						lstFranjasHorariasCurso);

				// Se validan las restricciones
				if (Boolean.TRUE.equals(noseSuperponeConFranjasEspaciosFisicos && noseSuperponeConFranjasDocentes
						&& noseSuperponeConFranjasCursosSemestre && noseSuperponeConFranjasDelMismoCurso)) {
					// Se adiciona la franja candidata como franja posible
					listaDeFranjasDisponibles.add(new FranjaHorariaCursoDTO(entry.getKey(), franjaCandidata.getDia(),
							franjaCandidata.getHoraInicio(), franjaCandidata.getHoraFin()));
				}
			}
		}
		return listaDeFranjasDisponibles;
	}
	
	private List<FranjaHorariaCursoDTO> consultarYValidarFranjasDisponiblesParaHorarioSecundario(
			FiltroFranjaHorariaDisponibleCursoDTO filtroFranjaHorariaDisponibleCursoDTO,
			Map<Long, List<FranjaHorariaBasicaDTO>> mapaFranjasHorariasPorEspacioFisico) {

		// Lista de las franjas disponibles para el curso
		List<FranjaHorariaCursoDTO> listaDeFranjasDisponibles = new ArrayList<>();

		List<FranjaHorariaCursoDTO> horarioPrincipalActual = this.gestionarPlanificacionManualGatewayIntPort
				.consultarFranjasHorariaCursoPorIdCurso(filtroFranjaHorariaDisponibleCursoDTO.getIdCurso(),
						Boolean.TRUE);
		// Si no existe horario principal se retorna lista vacía como respuesta
		if (horarioPrincipalActual.isEmpty()) {
			return listaDeFranjasDisponibles;
		}
		
		List<FranjaHorariaCursoDTO> horarioSecundario = this.gestionarPlanificacionManualGatewayIntPort
				.consultarFranjasHorariaCursoPorIdCurso(filtroFranjaHorariaDisponibleCursoDTO.getIdCurso(),
						Boolean.FALSE);
			
		horarioPrincipalActual = horarioPrincipalActual.stream()
				.filter(frPr -> horarioSecundario.stream()
						.noneMatch(frSec -> (frPr.getDia().equals(frSec.getDia())
								&& frPr.getHoraInicio().equals(frSec.getHoraInicio())
								&& frPr.getHoraFin().equals(frSec.getHoraFin()))))
				.toList();

		List<FranjaHorariaBasicaDTO> listaFranjasCandidatas = horarioPrincipalActual.stream().map(fr -> {
			return new FranjaHorariaBasicaDTO(fr.getDia(),
					LocalTime.parse(fr.getHoraInicio(), DateTimeFormatter.ofPattern("HH:mm")),
					LocalTime.parse(fr.getHoraFin(), DateTimeFormatter.ofPattern("HH:mm")));
		}).toList();

		if(!listaFranjasCandidatas.isEmpty()) {
			// Se recorre cada espacio físico para evaluarle las franjas candidatas
			for (Map.Entry<Long, List<FranjaHorariaBasicaDTO>> entry : mapaFranjasHorariasPorEspacioFisico.entrySet()) {
				
				// Se recorre cada franja candidata para validarla contra las restricciones
				for (FranjaHorariaBasicaDTO franjaCandidata : listaFranjasCandidatas) {
					
					// Restricciones
					Boolean noseSuperponeConFranjasEspaciosFisicos = !this
							.seSuperponeConFranjasEspaciosFisicos(franjaCandidata, entry.getValue());
					
					// Se validan las restricciones
					if (Boolean.TRUE.equals(noseSuperponeConFranjasEspaciosFisicos)) {
						// Se adiciona la franja candidata como franja posible
						listaDeFranjasDisponibles.add(new FranjaHorariaCursoDTO(entry.getKey(), franjaCandidata.getDia(),
								franjaCandidata.getHoraInicio(), franjaCandidata.getHoraFin()));
					}
				}
			}			
		}
	

		return listaDeFranjasDisponibles;
	}

	private Boolean cursoPerteneceAlPeriodoAcademicoVigente(Long idCurso, Long idPeriodoAcademicoVigente) {
		// Se consulta el curso
		Curso curso = this.gestionarCursoGatewayIntPort.consultarCursoPorIdCurso(idCurso);
		if (Objects.nonNull(curso)
				&& curso.getPeriodoAcademico().getIdPeriodoAcademico().equals(idPeriodoAcademicoVigente)) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	/**
	 * Método encargado de generar las franjas horarias candidatas teniendo en
	 * cuenta las franjas restrictivas (Tiempo de descanso o almuerzo de los
	 * docentes y estudiantes) y los criterios de busqueda ingresados por el
	 * usuario. Los criterios de busqueda del usuario pueden ser: Filtrar por días
	 * de la semana (Opcional), cantidad de horas por franja (Obligatorio), y por
	 * hora de inicio(Opcional).<br>
	 * 
	 * @author apedro
	 * 
	 * @param filtroFranjaHorariaDisponibleCursoDTO
	 * @return
	 */
	private List<FranjaHorariaBasicaDTO> consultarFranjasCandidatas(
			FiltroFranjaHorariaDisponibleCursoDTO filtroFranjaHorariaDisponibleCursoDTO) {
		// Frajas restrictivas de LUNES a SABADO de 13:00 a 14:00
		List<FranjaHorariaBasicaDTO> lstFranjasRestringidas = new ArrayList<>();
		lstFranjasRestringidas
				.add(new FranjaHorariaBasicaDTO(DiaSemanaEnum.LUNES, LocalTime.of(13, 0), LocalTime.of(14, 0)));
		lstFranjasRestringidas
				.add(new FranjaHorariaBasicaDTO(DiaSemanaEnum.MARTES, LocalTime.of(13, 0), LocalTime.of(14, 0)));
		lstFranjasRestringidas
				.add(new FranjaHorariaBasicaDTO(DiaSemanaEnum.MIERCOLES, LocalTime.of(13, 0), LocalTime.of(14, 0)));
		lstFranjasRestringidas
				.add(new FranjaHorariaBasicaDTO(DiaSemanaEnum.JUEVES, LocalTime.of(13, 0), LocalTime.of(14, 0)));
		lstFranjasRestringidas
				.add(new FranjaHorariaBasicaDTO(DiaSemanaEnum.VIERNES, LocalTime.of(13, 0), LocalTime.of(14, 0)));
		lstFranjasRestringidas
				.add(new FranjaHorariaBasicaDTO(DiaSemanaEnum.SABADO, LocalTime.of(13, 0), LocalTime.of(14, 0)));

		// Lista que almacena las franjas candidas a evaluar
		List<FranjaHorariaBasicaDTO> listaFranjasCandidatas = new ArrayList<>();

		// El usuario debe enviar al menos la duración de la franja, siempre obligatorio
		int duracionFranja = filtroFranjaHorariaDisponibleCursoDTO.getDuracion().intValue();

		// Si el usuario no filtra por días, por defecto se filtra de Lunes a Sabado
		List<DiaSemanaEnum> diasPosibles = Arrays.asList(DiaSemanaEnum.LUNES, DiaSemanaEnum.MARTES,
				DiaSemanaEnum.MIERCOLES, DiaSemanaEnum.JUEVES, DiaSemanaEnum.VIERNES, DiaSemanaEnum.SABADO);

		if (Objects.nonNull(filtroFranjaHorariaDisponibleCursoDTO.getListaDiaSemanaEnum())
				&& !filtroFranjaHorariaDisponibleCursoDTO.getListaDiaSemanaEnum().isEmpty()) {
			diasPosibles = filtroFranjaHorariaDisponibleCursoDTO.getListaDiaSemanaEnum();
		}

		// Se crean las franjas candidatas que van desde el rango de 7:00 a 22:00
		for (DiaSemanaEnum dia : DiaSemanaEnum.values()) {
			if (diasPosibles.contains(dia)) {
				// Franjas posibles de 07:00 a 22:00
				for (int hora = 7; hora <= 22; hora++) {
					// La franja final no debe superar las 22:00
					if (hora + duracionFranja <= 22) {
						LocalTime horaInicio = LocalTime.of(hora, 0);
						LocalTime horaFin = horaInicio.plusHours(duracionFranja);
						/*
						 * Si el usuario no filtra por hora inicio, por defecto muestra todas las
						 * franjas
						 */
						if ((Objects.isNull(filtroFranjaHorariaDisponibleCursoDTO.getHoraInicio()) ? Boolean.TRUE
								: horaInicio.toString().equals(filtroFranjaHorariaDisponibleCursoDTO.getHoraInicio()))
								&& !seSuperponeConListaFranjasRestringidas(dia, horaInicio, horaFin,
										lstFranjasRestringidas)) {
							listaFranjasCandidatas.add(new FranjaHorariaBasicaDTO(dia, horaInicio, horaFin));
						}
					}
				}
			}
		}
		return listaFranjasCandidatas;
	}

	/**
	 * Método encargado de validar si una franja horaria se superpone en una lista
	 * de franjas restringidas. Una franja restringida es aquella en la que no se
	 * puede establecer un horario, por ejemplo. la franja de 13:00-14:00 de lunes a
	 * sabado es restringida porque es reservada para el almuerzo de los estudiantes
	 * y docentes.<br>
	 * 
	 * 
	 * @author apedro
	 * 
	 * @param dia
	 * @param horaInicio
	 * @param horaFin
	 * @param lstFranjasRestringidas
	 * @return
	 */
	private boolean seSuperponeConListaFranjasRestringidas(DiaSemanaEnum dia, LocalTime horaInicio, LocalTime horaFin,
			List<FranjaHorariaBasicaDTO> lstFranjasRestringidas) {
		for (FranjaHorariaBasicaDTO franja : lstFranjasRestringidas) {
			if (dia.equals(franja.getDia()) && horaFin.toSecondOfDay() > franja.getHoraInicio().toSecondOfDay()
					&& horaInicio.toSecondOfDay() < franja.getHoraFin().toSecondOfDay()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Método encargado de validar la restricción: Los cursos de un mismo semestre
	 * no deben sobreponerse, excepto los mismos cursos que pertenecen a la misma
	 * asignatura. <br>
	 * 
	 * @author apedro
	 * 
	 * @param franjaCandidata
	 * @param lstFranjasDeCursosSemestre
	 * @param idAsignatura
	 * @return
	 */
	private boolean seSuperponeConFranjasCursosSemestre(FranjaHorariaBasicaDTO franjaCandidata,
			List<FranjaHorariaBasicaDTO> lstFranjasDeCursosSemestre, Long idAsignatura) {
		for (FranjaHorariaBasicaDTO franja : lstFranjasDeCursosSemestre) {
			if (franjaCandidata.getDia().equals(franja.getDia())
					&& franjaCandidata.getHoraFin().toSecondOfDay() > franja.getHoraInicio().toSecondOfDay()
					&& franjaCandidata.getHoraInicio().toSecondOfDay() < franja.getHoraFin().toSecondOfDay()
					&& !franja.getIdAsignatura().equals(idAsignatura)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Método encargado de validar que una franja candidata no se sobreponga con el
	 * horario de un espacio<br>
	 * 
	 * @author apedro
	 * 
	 * @param franjaCandidata
	 * @param lstFranjasDeEspacioFisico
	 * @return
	 */
	private boolean seSuperponeConFranjasEspaciosFisicos(FranjaHorariaBasicaDTO franjaCandidata,
			List<FranjaHorariaBasicaDTO> lstFranjasDeEspacioFisico) {
		return this.seSuperponeConListaFranjas(franjaCandidata, lstFranjasDeEspacioFisico);
	}

	/**
	 * Método encargado de validar que una franja candidata no se sobreponga con los
	 * horarios de los docentes que orientan el curso<br>
	 * 
	 * @author apedro
	 * 
	 * @param franjaCandidata
	 * @param lstFranjasDeEspacioFisico
	 * @return
	 */
	private boolean seSuperponeConFranjasDocentes(FranjaHorariaBasicaDTO franjaCandidata,
			List<FranjaHorariaBasicaDTO> lstFranjasDeDocentes) {
		return this.seSuperponeConListaFranjas(franjaCandidata, lstFranjasDeDocentes);
	}

	/**
	 * Método encargado de validar que una franja candidata no se sobreponga con los
	 * horarios del mismo curso<br>
	 * 
	 * @author apedro
	 * 
	 * @param franjaCandidata
	 * @param lstFranjasDeEspacioFisico
	 * @return
	 */
	private boolean seSuperponeConFranjasCurso(FranjaHorariaBasicaDTO franjaCandidata,
			List<FranjaHorariaBasicaDTO> lstFranjasDeDocentes) {
		return this.seSuperponeConListaFranjas(franjaCandidata, lstFranjasDeDocentes);
	}

	/**
	 * Método que refactoriza parte de la lógica en común para validar el
	 * solapamiento de una franja candidata en una lista de franjas <br>
	 * 
	 * @author apedro
	 * 
	 * @param franjaCandidata
	 * @param lstFranjasDeEspacioFisico
	 * @return
	 */
	private boolean seSuperponeConListaFranjas(FranjaHorariaBasicaDTO franjaCandidata,
			List<FranjaHorariaBasicaDTO> lstFranjaHoraria) {
		for (FranjaHorariaBasicaDTO franja : lstFranjaHoraria) {
			if (franjaCandidata.getDia().equals(franja.getDia())
					&& franjaCandidata.getHoraFin().toSecondOfDay() > franja.getHoraInicio().toSecondOfDay()
					&& franjaCandidata.getHoraInicio().toSecondOfDay() < franja.getHoraFin().toSecondOfDay()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @see co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.input.GestionarPlanificacionManualCUIntPort#consultarFranjasHorariaCursoPorIdCurso(java.lang.Long,
	 *      java.lang.Boolean)
	 */
	@Override
	public List<FranjaHorariaCursoDTO> consultarFranjasHorariaCursoPorIdCurso(Long idCurso, Boolean esPrincipal) {
		return this.gestionarPlanificacionManualGatewayIntPort.consultarFranjasHorariaCursoPorIdCurso(idCurso,
				esPrincipal);
	}

	/**
	 * @see co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.input.GestionarPlanificacionManualCUIntPort#consultarFranjasDocentePorIdPersona(java.lang.Long)
	 */
	@Override
	public List<FranjaHorariaDocenteDTO> consultarFranjasDocentePorIdPersona(Long idPersona) {
		// Se consulta periodo académico vigente
		PeriodoAcademico periodoAcademicoVigente = gestionarPeriodoAcademicoGatewayIntPort
				.consultarPeriodoAcademicoVigente();
		/*
		 * Se valida que exista periodo académico vigente
		 */
		if (Objects.isNull(periodoAcademicoVigente)) {
			throw new RuntimeException(NO_EXISTE_PERIODO_ACADEMICO_VIGENTE);
		}
		List<FranjaHorariaDocenteDTO> lstHorarioDocente = this.gestionarPlanificacionManualGatewayIntPort
				.consultarFranjasDocentePorIdPersona(idPersona, periodoAcademicoVigente.getIdPeriodoAcademico());

		// Se consulta el nombre del salón principal de la franja
		lstHorarioDocente.forEach(franja -> franja.setSalon(this.gestionarEspacioFisicoGatewayIntPort
				.consultarEspacioFisicoCursoPorIdHorario(franja.getIdHorario(), Boolean.TRUE).getSalon()));

		// Se consulta el nombre del salón secundario de la franja
		lstHorarioDocente.forEach(franja -> franja.setSalonSecundario(this.gestionarEspacioFisicoGatewayIntPort
				.consultarEspacioFisicoCursoPorIdHorario(franja.getIdHorario(), Boolean.FALSE).getSalon()));

		return lstHorarioDocente;
	}

	/**
	 * @see co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.input.GestionarPlanificacionManualCUIntPort#consultarFranjasEspacioFisicoPorIdEspacioFisico(java.lang.Long)
	 */
	@Override
	public List<FranjaHorariaEspacioFisicoDTO> consultarFranjasEspacioFisicoPorIdEspacioFisico(Long idEspacioFisico) {
		// Se consulta periodo académico vigente
		PeriodoAcademico periodoAcademicoVigente = gestionarPeriodoAcademicoGatewayIntPort
				.consultarPeriodoAcademicoVigente();
		/*
		 * Se valida que exista periodo académico vigente
		 */
		if (Objects.isNull(periodoAcademicoVigente)) {
			throw new RuntimeException(NO_EXISTE_PERIODO_ACADEMICO_VIGENTE);
		}
		return this.gestionarPlanificacionManualGatewayIntPort.consultarFranjasEspacioFisicoPorIdEspacioFisico(
				idEspacioFisico, periodoAcademicoVigente.getIdPeriodoAcademico());
	}

	@Override
	public List<FormatoPresentacionFranjaHorariaCursoDTO> consultarFormatoPresentacionFranjaHorariaCurso() {
		return this.gestionarPlanificacionManualGatewayIntPort.consultarFormatoPresentacionFranjaHorariaCurso();
	}

	/*************************************************
	 * Planificación manual - Gestionar horario secundario
	 *************************************************/

	/** 
	 * @see co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.input.GestionarPlanificacionManualCUIntPort#crearActualizarHorarioSecundarioCurso(co.edu.unicauca.sgph.horario.infrastructure.input.DTORequest.CrearActualizarHorarioCursoInDTO)
	 */
	@Override
	public CrearActualizarHorarioCursoOutDTO crearActualizarHorarioSecundarioCurso(
			CrearActualizarHorarioCursoInDTO crearActualizarHorarioCursoInDTO) {
		CrearActualizarHorarioCursoOutDTO crearActualizarHorarioCursoOutDTO = new CrearActualizarHorarioCursoOutDTO();
		try {
			this.gestionarPlanificacionManualCUAdapterEJB
					.validarYCrearActualizarHorarioSecundarioCurso(crearActualizarHorarioCursoInDTO, Boolean.TRUE);
			crearActualizarHorarioCursoOutDTO.setEsExitoso(Boolean.TRUE);
		} catch (RuntimeException e) {
			crearActualizarHorarioCursoOutDTO.setLstMensajesSolapamientos(Arrays.asList(e.getMessage()));
			crearActualizarHorarioCursoOutDTO.setEsExitoso(Boolean.FALSE);
		}

		return crearActualizarHorarioCursoOutDTO;
	}

	@Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
	public void validarYCrearActualizarHorarioSecundarioCurso(
			CrearActualizarHorarioCursoInDTO crearActualizarHorarioCursoInDTO, Boolean eliminarFranjas) {
		Long idCurso = crearActualizarHorarioCursoInDTO.getIdCurso();

		// Se consulta periodo académico vigente
		PeriodoAcademico periodoAcademicoVigente = gestionarPeriodoAcademicoGatewayIntPort
				.consultarPeriodoAcademicoVigente();
		/*
		 * Se valida que exista periodo académico vigente y que el curso a consultar
		 * pertenezca al mismo
		 */
		if (Objects.isNull(periodoAcademicoVigente)) {
			throw new RuntimeException(NO_EXISTE_PERIODO_ACADEMICO_VIGENTE);
		}

		if (Boolean.TRUE.equals(this.cursoPerteneceAlPeriodoAcademicoVigente(idCurso,
				periodoAcademicoVigente.getIdPeriodoAcademico()))) {
			throw new RuntimeException(CURSO_NO_PERTENECE_A_PERIODO_ACADEMICO_VIGENTE);
		}
				
		Object[] infoBasicaCurso = new Object[3];

		this.validarRestriccionesInicialesYConsultarInfoBasicaCurso(crearActualizarHorarioCursoInDTO, infoBasicaCurso,
				eliminarFranjas);
		/*
		 * Se valida que el curso tenga al menos una franja principal y que las franjas
		 * a actualizar sea iguales a las principales
		 */
		this.validarExistenciaHorarioPrincipalYFranjasAActualizarSeanValidas(crearActualizarHorarioCursoInDTO, idCurso);
		/*
		 * Se elimina de la base de datos las franjas horarias secundarias del curso que
		 * no están en las franjas a actualizar
		 */
		this.eliminarFranjasSecundariasDelCurso(crearActualizarHorarioCursoInDTO, eliminarFranjas);

		this.validarRestriccionesEspacioFisico(crearActualizarHorarioCursoInDTO, (Integer) infoBasicaCurso[1]);

		// Se validan cruces
		for (FranjaHorariaCursoAsociarInDTO franja : crearActualizarHorarioCursoInDTO
				.getListaFranjaHorariaCursoAsociarInDTO()) {

			if (Objects.isNull(this.gestionarHorarioGatewayIntPort.consultaHorarioEspacioPorIdHorarioYIdEspacioFisico(
					franja.getIdHorario(), franja.getIdEspacioFisico()))) {

				Long idEspacioFisico = franja.getIdEspacioFisico();
				DiaSemanaEnum dia = franja.getDia();
				LocalTime horaInicio = this.convertirToLocalTime(franja.getHoraInicio());
				LocalTime horaFin = this.convertirToLocalTime(franja.getHoraFin());

				List<Horario> listaFranjasHoriasActualDelCurso = this.gestionarHorarioGatewayIntPort
						.consultarHorarioPorCurso(new Curso(crearActualizarHorarioCursoInDTO.getIdCurso()));

				Long idHorario = listaFranjasHoriasActualDelCurso.stream()
						.filter(hor -> hor.getDia().equals(dia) && hor.getHoraInicio().equals(horaInicio)
								&& hor.getHoraFin().equals(horaFin))
						.findFirst().map(Horario::getIdHorario).orElse(null);

				// Se valida que la franja no se cruce con el espacio físico
				this.validarCruceHorarioEspacioFisico(idEspacioFisico, dia, horaInicio, horaFin,
						periodoAcademicoVigente, franja, idCurso, eliminarFranjas);

				// Se crea horario secundario
				this.gestionarHorarioGatewayIntPort.crearHorarioSecundario(
						new HorarioEspacio(idHorario, franja.getIdEspacioFisico(), Boolean.FALSE));
			}
		} // Cierre del for
	}

	private void validarExistenciaHorarioPrincipalYFranjasAActualizarSeanValidas(
			CrearActualizarHorarioCursoInDTO crearActualizarHorarioCursoInDTO, Long idCurso) {
		// Se consulta franjas primarias
		List<FranjaHorariaCursoDTO> lstFranjasPrimarias = this.gestionarPlanificacionManualGatewayIntPort
				.consultarFranjasHorariaCursoPorIdCurso(idCurso, Boolean.TRUE);

		if (lstFranjasPrimarias.isEmpty()) {
			throw new RuntimeException("No existe un horario principal para el curso");
		}

		Map<String, FranjaHorariaCursoDTO> mapaFranjasPrimarias = lstFranjasPrimarias.stream()
				.collect(Collectors.toMap(this::obtenerFranjaFormatoEstandar, franjaH -> franjaH));

		Map<String, FranjaHorariaCursoAsociarInDTO> mapaFranjasSecundariasAActualizar = crearActualizarHorarioCursoInDTO
				.getListaFranjaHorariaCursoAsociarInDTO().stream()
				.collect(Collectors.toMap(this::obtenerFranjaFormatoEstandar, franjaH -> franjaH));

		// Se valida que cada franja a actualizar sea igual a alguna franja primaria
		List<String> franjasInvalidas = new ArrayList<>();
		for (Map.Entry<String, FranjaHorariaCursoAsociarInDTO> entry : mapaFranjasSecundariasAActualizar.entrySet()) {
			if (!mapaFranjasPrimarias.containsKey(this.obtenerFranjaFormatoEstandar(entry.getValue()))) {
				franjasInvalidas.add(this.obtenerFranjaFormatoEstandar(entry.getValue()));
			}
		}

		if (!franjasInvalidas.isEmpty()) {
			throw new RuntimeException("Las siguientes franjas son invalidas para un horario secundario: "
					+ String.join(", ", franjasInvalidas));
		}
	}

	private void eliminarFranjasSecundariasDelCurso(CrearActualizarHorarioCursoInDTO crearActualizarHorarioCursoInDTO,
			Boolean eliminarFranjas) {
		if (Boolean.TRUE.equals(eliminarFranjas)) {

			// Se consulta las franjas secundarias
			List<FranjaHorariaCursoDTO> lstFranjasSecundariasActualDelCurso = this.gestionarPlanificacionManualGatewayIntPort
					.consultarFranjasHorariaCursoPorIdCurso(crearActualizarHorarioCursoInDTO.getIdCurso(),
							Boolean.FALSE);

			List<FranjaHorariaCursoDTO> lstFranjasSecundariasQueSeMantienen = lstFranjasSecundariasActualDelCurso
					.stream()
					.filter(frActual -> crearActualizarHorarioCursoInDTO.getListaFranjaHorariaCursoAsociarInDTO()
							.stream()
							.anyMatch(frNueva -> Objects.equals(frNueva.getIdHorario(), frActual.getIdHorario())
									&& Objects.equals(frNueva.getIdEspacioFisico(), frActual.getIdEspacioFisico())))
					.toList();

			// Se eliminan las franjas horarias secuandarias que ya no se mantienen
			lstFranjasSecundariasActualDelCurso.removeAll(lstFranjasSecundariasQueSeMantienen);

			for (FranjaHorariaCursoDTO frActual : lstFranjasSecundariasActualDelCurso) {

				this.gestionarHorarioGatewayIntPort.eliminarHorarioSecundario(
						new HorarioEspacio(frActual.getIdHorario(), frActual.getIdEspacioFisico(), Boolean.FALSE));
			}
			
			entityManager.flush();
		}
	}

	/*************************************************
	 * Eliminar horario por programa
	 *************************************************/

	/**
	 * @see co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.input.GestionarPlanificacionManualCUIntPort#eliminarHorarioPrograma(co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTORequest.EliminarHorarioInDTO)
	 */
	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public Boolean eliminarHorarioPrograma(EliminarHorarioInDTO eliminarHorarioInDTO) {

		if (!this.gestionarProgramaGatewayIntPort.consultarProgramasPermitidosPorUsuario().stream()
				.map(Programa::getIdPrograma).toList()
				.contains(eliminarHorarioInDTO.getIdPrograma())) {
			throw new RuntimeException(PROGRAMA_NO_PERMITIDO_PARA_EL_USUARIO);
		}

		// Se consulta periodo académico vigente
		PeriodoAcademico periodoAcademicoVigente = gestionarPeriodoAcademicoGatewayIntPort
				.consultarPeriodoAcademicoVigente();
		/*
		 * Se valida que exista periodo académico vigente
		 */
		if (Objects.isNull(periodoAcademicoVigente)) {
			throw new RuntimeException(NO_EXISTE_PERIODO_ACADEMICO_VIGENTE);
		}

		// Se eliminan registros de HorarioEspacioEntity y HorarioEntity
		this.gestionarPlanificacionManualGatewayIntPort.eliminarHorarioPrograma(eliminarHorarioInDTO,
				periodoAcademicoVigente.getIdPeriodoAcademico());

		return Boolean.TRUE;
	}

	/*************************************************
	 * Generación basada en semestre anterior por programa
	 *************************************************/

	/**
	 * @see co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.input.GestionarPlanificacionManualCUIntPort#generarHorarioBasadoEnSemestreAnteriorPorPrograma(co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTORequest.GenerarHorarioBaseInDTO)
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public GenerarHorarioBaseOutDTO generarHorarioBasadoEnSemestreAnteriorPorPrograma(
			GenerarHorarioBaseInDTO generarHorarioBaseInDTO) {
		
		// Se valida que el programa si pueda ser gestionado por el usuario
		if (!this.gestionarProgramaGatewayIntPort.consultarProgramasPermitidosPorUsuario().stream()
				.map(Programa::getIdPrograma).toList()
				.contains(generarHorarioBaseInDTO.getIdPrograma())) {
			throw new RuntimeException(PROGRAMA_NO_PERMITIDO_PARA_EL_USUARIO);
		}
		
		// Se consulta periodo académico vigente
		PeriodoAcademico periodoAcademicoVigente = gestionarPeriodoAcademicoGatewayIntPort
				.consultarPeriodoAcademicoVigente();
		/*
		 * Se valida que exista periodo académico vigente
		 */
		if (Objects.isNull(periodoAcademicoVigente)) {			
			throw new RuntimeException(NO_EXISTE_PERIODO_ACADEMICO_VIGENTE);
		}
		GenerarHorarioBaseOutDTO generarHorarioBaseOutDTO = new GenerarHorarioBaseOutDTO();
		generarHorarioBaseOutDTO.setIdPrograma(generarHorarioBaseInDTO.getIdPrograma());
		generarHorarioBaseOutDTO.setLstMensajesDelProceso(new ArrayList<>());

		// Se consulta todos los cursos del periodo vigente
		List<Curso> lstCursosActuales = this.gestionarCursoGatewayIntPort.consultarCursosPorProgramaYPeriodoAcademico(
				generarHorarioBaseInDTO.getIdPrograma(), periodoAcademicoVigente.getIdPeriodoAcademico());
		// Se valida que existan cursos para el periodo vigente
		if (lstCursosActuales.isEmpty()) {
			Programa programa = this.gestionarProgramaGatewayIntPort
					.consultarProgramaPorId(generarHorarioBaseInDTO.getIdPrograma());
			throw new RuntimeException("No existen cursos del periodo " + periodoAcademicoVigente.getAnio() + "-"
					+ periodoAcademicoVigente.getPeriodo() + " para el programa " + programa.getNombre());
		}

		// Se filtran asignaturas a excluir, si aplica.
		if (Objects.nonNull(generarHorarioBaseInDTO.getLstIdAsignaturaExcluir())
				&& !generarHorarioBaseInDTO.getLstIdAsignaturaExcluir().isEmpty()) {
			lstCursosActuales = lstCursosActuales.stream().filter(curso -> !generarHorarioBaseInDTO
					.getLstIdAsignaturaExcluir().contains(curso.getAsignatura().getIdAsignatura()))
					.toList();
		}

		// Se consulta los cursos del periodo base
		List<Curso> lstCursosBase = this.gestionarCursoGatewayIntPort.consultarCursosPorProgramaYPeriodoAcademico(
				generarHorarioBaseInDTO.getIdPrograma(), generarHorarioBaseInDTO.getIdPeriodoAcademicoBase());
		// Se valida que existan cursos para el periodo vigente
		if (lstCursosBase.isEmpty()) {
			PeriodoAcademico periodoAcademicoBase = this.gestionarPeriodoAcademicoGatewayIntPort
					.consultarPeriodoAcademicoPorId(generarHorarioBaseInDTO.getIdPeriodoAcademicoBase());
			Programa programa = this.gestionarProgramaGatewayIntPort
					.consultarProgramaPorId(generarHorarioBaseInDTO.getIdPrograma());
			throw new RuntimeException("No existen cursos del periodo " + periodoAcademicoBase.getAnio() + "-"
					+ periodoAcademicoBase.getPeriodo() + " para el programa " + programa.getNombre());
		}
		Map<String, Curso> mapaCursosBase = lstCursosBase.stream().collect(Collectors
				.toMap(curso -> curso.getAsignatura().getIdAsignatura() + "-" + curso.getGrupo(), curso -> curso));

		int cantidadCursosHorarioCompleto = 0;
		int cantidadCursosHorarioParcial = 0;
		int cantidadCursosSinHorario = 0;

		// Contador de cursos que no mapearon con ningún curso
		int cantidadCursosNoCorrelacionados = 0;

		/*
		 * Se consultan las franjas principales para obtener el idEspacioFisico
		 * correspondiente por el identificador de horario
		 */
		Map<Long, Long> mapaIdEspacioFisicoPrincipalPorIdHorario = this
				.consultarEspacioFisicoPrincipalPorHorario(generarHorarioBaseInDTO);

		for (Curso cursoActual : lstCursosActuales) {

			// Se valida que exista el mismo curso y grupo
			if (mapaCursosBase
					.containsKey(cursoActual.getAsignatura().getIdAsignatura() + "-" + cursoActual.getGrupo())) {

				// Se obtiene el curso
				Curso cursoBase = mapaCursosBase
						.get(cursoActual.getAsignatura().getIdAsignatura() + "-" + cursoActual.getGrupo());

				// Se constuye el DTO de entrada
				CrearActualizarHorarioCursoInDTO crearActualizarHorarioCursoInDTO = new CrearActualizarHorarioCursoInDTO();
				crearActualizarHorarioCursoInDTO.setIdCurso(cursoActual.getIdCurso());
				// Contador auxiliar de cantidad de franajs actualizadas por curso
				int contadorAuxFranjasActualizadas = 0;
				// Por cada franja horaria del curso se realiza la actualización
				for (Horario horario : cursoBase.getHorarios()) {
					// Se construye franja horaria
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
					String horaInicio = horario.getHoraInicio().format(formatter);
					String horaFin = horario.getHoraFin().format(formatter);

					FranjaHorariaCursoAsociarInDTO franjaHorariaCursoAsociarInDTO = new FranjaHorariaCursoAsociarInDTO(
							horario.getDia(), horaInicio, horaFin,
							mapaIdEspacioFisicoPrincipalPorIdHorario.get(horario.getIdHorario()));
					crearActualizarHorarioCursoInDTO
							.setListaFranjaHorariaCursoAsociarInDTO(Arrays.asList(franjaHorariaCursoAsociarInDTO));

					try {
						this.gestionarPlanificacionManualCUAdapterEJB
								.validarYCrearActualizarHorarioCurso(crearActualizarHorarioCursoInDTO, false);
						contadorAuxFranjasActualizadas++;
					} catch (Exception e) {

						if (FRANJA_REPETIDA.equals(e.getMessage())) {
							contadorAuxFranjasActualizadas++;
						} else {
							String[] registro = new String[3];
							registro[0] = cursoActual.getAsignatura().getNombre() + " " + cursoActual.getGrupo();
							registro[1] = this.obtenerFranjaFormatoEstandar(horario) + " "
									+ horario.getEspaciosFisicos().get(0).getSalon();
							registro[2] = e.getMessage();
							// Si se produce algún error técnico o salta alguna restricción
							generarHorarioBaseOutDTO.getLstMensajesDelProceso().add(registro);
						}
					}
				}
				// Se contabiliza cursos actualizados
				if (contadorAuxFranjasActualizadas == cursoBase.getHorarios().size()) {
					cantidadCursosHorarioCompleto++;
				} else if (contadorAuxFranjasActualizadas > 0
						&& contadorAuxFranjasActualizadas < cursoBase.getHorarios().size()) {
					cantidadCursosHorarioParcial++;
				} else {
					cantidadCursosSinHorario++;
				}

			} else {
				// Cursos que no mapearon con ningún curso del semestre base
				cantidadCursosNoCorrelacionados++;
				String[] registro = new String[3];
				registro[0] = cursoActual.getAsignatura().getNombre() + " " + cursoActual.getGrupo();
				registro[1] = "";
				registro[2] = "Curso nuevo";
				generarHorarioBaseOutDTO.getLstMensajesDelProceso().add(registro);
			}
		}
		generarHorarioBaseOutDTO.setCantidadCursosHorarioCompleto(Long.valueOf(cantidadCursosHorarioCompleto));
		generarHorarioBaseOutDTO.setCantidadCursosHorarioParcial(Long.valueOf(cantidadCursosHorarioParcial));
		generarHorarioBaseOutDTO
				.setCantidadCursosSinHorario(Long.valueOf(cantidadCursosSinHorario + cantidadCursosNoCorrelacionados));
		generarHorarioBaseOutDTO.setCantidadCursosNoCorrelacionados(Long.valueOf(cantidadCursosNoCorrelacionados));
		return generarHorarioBaseOutDTO;
	}
	
	private Map<Long, Long> consultarEspacioFisicoPrincipalPorHorario(GenerarHorarioBaseInDTO generarHorarioBaseInDTO) {
		List<FranjaHorariaCursoDTO> lstFranjasPricipales = this.gestionarPlanificacionManualGatewayIntPort
				.consultarFranjasHorariaPrincipalProgramaPoridProgramaYPeriodoAcademico(
						generarHorarioBaseInDTO.getIdPrograma(), generarHorarioBaseInDTO.getIdPeriodoAcademicoBase());
		return lstFranjasPricipales.stream().collect(
				Collectors.toMap(franjaPri -> franjaPri.getIdHorario(), franjaPri -> franjaPri.getIdEspacioFisico()));
	}

	/**
	 * @see co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.input.GestionarPlanificacionManualCUIntPort#simularCargueLabor(java.lang.Long)
	 */
	@Override
	@Transactional
	public void simularCargueLabor(Long idPrograma) {
		// Se consulta periodo académico vigente
		PeriodoAcademico periodoAcademicoVigente = gestionarPeriodoAcademicoGatewayIntPort
				.consultarPeriodoAcademicoVigente();
		/*
		 * Se valida que exista periodo académico vigente
		 */
		if (Objects.isNull(periodoAcademicoVigente)) {
			throw new RuntimeException(NO_EXISTE_PERIODO_ACADEMICO_VIGENTE);
		}
		// Se consulta todos los cursos del periodo 2023-1 con identificador 1
		List<Curso> lstCursosBase = this.gestionarCursoGatewayIntPort
				.consultarCursosPorProgramaYPeriodoAcademico(idPrograma, 1L);
		Map<String, Curso> mapaCursosBase = lstCursosBase.stream().collect(Collectors
				.toMap(curso -> curso.getAsignatura().getIdAsignatura() + "-" + curso.getGrupo(), curso -> curso));

		List<Curso> lstCursosVigente = this.gestionarCursoGatewayIntPort.consultarCursosPorProgramaYPeriodoAcademico(
				idPrograma, periodoAcademicoVigente.getIdPeriodoAcademico());
		if (lstCursosVigente.isEmpty()) {
			for (Curso curso : lstCursosBase) {
				curso.setIdCurso(null);
				curso.setPeriodoAcademico(periodoAcademicoVigente);
				curso.setHorarios(null);
				this.gestionarCursoGatewayIntPort.guardarCurso(curso);
			}
		} else {
			for (Curso curso : lstCursosVigente) {
				// Se obtiene el curso
				Curso cursoBase = mapaCursosBase.get(curso.getAsignatura().getIdAsignatura() + "-" + curso.getGrupo());

				for (Docente docente : cursoBase.getDocentes()) {
					docente.getCursos().add(curso);
					this.gestionarDocenteGatewayIntPort.guardarDocente(docente);
				}

				curso.setDocentes(cursoBase.getDocentes());
				this.gestionarCursoGatewayIntPort.guardarCurso(curso);
			}
		}
	}

}