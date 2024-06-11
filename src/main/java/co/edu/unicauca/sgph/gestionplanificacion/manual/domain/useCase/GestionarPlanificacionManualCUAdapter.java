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

	private static final String NO_EXISTE_PERIODO_ACADEMICO_VIGENTE = "No existe periodo académico vigente";

	private GestionarPlanificacionManualGatewayIntPort gestionarPlanificacionManualGatewayIntPort;
	private CursoFormatterResultsIntPort cursoFormatterResultsIntPort;
	private GestionarDocenteGatewayIntPort gestionarDocenteGatewayIntPort;
	private GestionarEspacioFisicoGatewayIntPort gestionarEspacioFisicoGatewayIntPort;
	private GestionarCursoGatewayIntPort gestionarCursoGatewayIntPort;
	private GestionarHorarioGatewayIntPort gestionarHorarioGatewayIntPort;
	private GestionarPeriodoAcademicoGatewayIntPort gestionarPeriodoAcademicoGatewayIntPort;
	private GestionarProgramaGatewayIntPort gestionarProgramaGatewayIntPort;

	@Autowired
	@Lazy
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

	/**
	 * @see co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.input.GestionarPlanificacionManualCUIntPort#consultarCursosPlanificacionPorFiltro(co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTORequest.FiltroCursoPlanificacionDTO)
	 */
	@Override
	public Page<CursoPlanificacionOutDTO> consultarCursosPlanificacionPorFiltro(
			FiltroCursoPlanificacionDTO filtroCursoPlanificacionDTO) {

		// Se consulta el periodo académico que está 'ABIERTO'
		PeriodoAcademico periodoAcademicoVigente = gestionarPeriodoAcademicoGatewayIntPort
				.consultarPeriodoAcademicoVigente();

		/*
		 * Se valida que exista periodo académico vigente
		 */
		if (Objects.nonNull(periodoAcademicoVigente)) {
			Page<CursoPlanificacionOutDTO> listaCursosDTO = this.gestionarPlanificacionManualGatewayIntPort
					.consultarCursosPlanificacionPorFiltro(filtroCursoPlanificacionDTO,
							periodoAcademicoVigente.getIdPeriodoAcademico());

			for (CursoPlanificacionOutDTO cursoDTO : listaCursosDTO) {
				cursoDTO.setDocentes(
						this.gestionarDocenteGatewayIntPort.consultarNombresDocentesPorIdCurso(cursoDTO.getIdCurso()));
				cursoDTO.setHorarios(gestionarEspacioFisicoGatewayIntPort
						.consultarEspacioFisicoHorarioPorIdCurso(cursoDTO.getIdCurso()));
			}
			return listaCursosDTO;
		} else {
			throw new RuntimeException(NO_EXISTE_PERIODO_ACADEMICO_VIGENTE);
		}

	}

	/**
	 * @see co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.input.GestionarPlanificacionManualCUIntPort#consultarInfoGeneralCursosPorPrograma(java.lang.Long)
	 */
	@Override
	public InfoGeneralCursosPorProgramaDTO consultarInfoGeneralCursosPorPrograma(Long idPrograma) {
		// Se consulta el periodo académico que está 'ABIERTO'
		PeriodoAcademico periodoAcademicoVigente = gestionarPeriodoAcademicoGatewayIntPort
				.consultarPeriodoAcademicoVigente();
		/*
		 * Se valida que exista periodo académico vigente
		 */
		if (Objects.nonNull(periodoAcademicoVigente)) {
			return this.gestionarPlanificacionManualGatewayIntPort.consultarInfoGeneralCursosPorPrograma(idPrograma,
					periodoAcademicoVigente.getIdPeriodoAcademico());
		} else {
			throw new RuntimeException(NO_EXISTE_PERIODO_ACADEMICO_VIGENTE);
		}
	}

	/**
	 * @see co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.input.GestionarPlanificacionManualCUIntPort#crearActualizarDocentesCursoDTO(co.edu.unicauca.sgph.horario.infrastructure.input.DTORequest.CrearActualizarDocentesCursoInDTO)
	 */
	@Override
	@Transactional
	public CrearActualizarDocentesCursoOutDTO crearActualizarDocentesCursoDTO(
			CrearActualizarDocentesCursoInDTO crearActualizarDocentesCursoInDTO) {
		List<String> mensajesCruces = new ArrayList<String>();
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
		if (Objects.nonNull(periodoAcademicoVigente)) {

			if (this.cursoPerteneceAlPeriodoAcademicoVigente(crearActualizarDocentesCursoInDTO.getIdCurso(),
					periodoAcademicoVigente.getIdPeriodoAcademico())) {

				// Se validan que los docentes existan y estén en estado ACTIVO
				this.validarExistenciaYEstadoActivoDocentes(crearActualizarDocentesCursoInDTO);

				// Se consulta el curso
				Curso curso = this.gestionarCursoGatewayIntPort
						.consultarCursoPorIdCurso(crearActualizarDocentesCursoInDTO.getIdCurso());

				List<Docente> docentesEliminar = curso.getDocentes().stream().filter(
						doc -> !crearActualizarDocentesCursoInDTO.getListaIdPersona().contains(doc.getIdPersona()))
						.collect(Collectors.toList());

				List<Docente> docentesActualizar = new ArrayList<Docente>();

				// Se consulta los horarios del curso
				List<Horario> listaHorarioCurso = this.gestionarHorarioGatewayIntPort
						.consultarHorarioPorCurso(new Curso(crearActualizarDocentesCursoInDTO.getIdCurso()));

				for (Long idPersona : crearActualizarDocentesCursoInDTO.getListaIdPersona()) {
					Docente docente = curso.getDocentes().stream().filter(doc -> doc.getIdPersona().equals(idPersona))
							.findFirst().orElse(null);

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
										docente.getTipoIdentificacion().getCodigoTipoIdentificacion(),
										docente.getNumeroIdentificacion(),
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
								.collect(Collectors.toList());
						docente.setCursos(lstCursos);
						docente = this.gestionarDocenteGatewayIntPort.guardarDocente(docente);
					}
					curso.setDocentes(docentesActualizar);
					curso = this.gestionarCursoGatewayIntPort.guardarCurso(curso);
					crearActualizarDocentesCursoOutDTO.setEsExitoso(Boolean.TRUE);
				}

			} else {
				throw new RuntimeException("El curso no pertenece al periodo académico vigente");
			}

		} else {
			throw new RuntimeException(NO_EXISTE_PERIODO_ACADEMICO_VIGENTE);
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
					.validarYCrearActualizarHorarioCurso(crearActualizarHorarioCursoInDTO, false);
			crearActualizarHorarioCursoOutDTO.setEsExitoso(Boolean.TRUE);
		} catch (RuntimeException e) {
			crearActualizarHorarioCursoOutDTO.setLstMensajesSolapamientos(Arrays.asList(e.getMessage()));
			crearActualizarHorarioCursoOutDTO.setEsExitoso(Boolean.FALSE);
		}

		return crearActualizarHorarioCursoOutDTO;
	}

	@Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
	public void validarYCrearActualizarHorarioCurso(CrearActualizarHorarioCursoInDTO crearActualizarHorarioCursoInDTO, Boolean conNuevaTransaccion)
			throws RuntimeException {
		Long idCurso = crearActualizarHorarioCursoInDTO.getIdCurso();

		// Se consulta periodo académico vigente
		PeriodoAcademico periodoAcademicoVigente = gestionarPeriodoAcademicoGatewayIntPort
				.consultarPeriodoAcademicoVigente();

		/*
		 * Se valida que exista periodo académico vigente y que el curso a consultar
		 * pertenezca al mismo
		 */
		if (Objects.nonNull(periodoAcademicoVigente)) {

			if (this.cursoPerteneceAlPeriodoAcademicoVigente(idCurso,
					periodoAcademicoVigente.getIdPeriodoAcademico())) {

				// Se consulta información inicial: idAsignatura, cupo, cantidad horas semana
				Object[] infoCurso = (Object[]) this.gestionarPlanificacionManualGatewayIntPort
						.consultarIdAsignaturaCupoYCantidadHorasDeCusoPorCurso(idCurso);

				// Se valida la cantidad de horas de las franjas a actualizar
				this.validarCantidadHorasPermitidasParaCurso(crearActualizarHorarioCursoInDTO, (Integer) infoCurso[2]);

				// Se validan que las franjas a actualizar no se solapen entre elllas
				this.validarSolapamientoEntreFranjasAActualizar(crearActualizarHorarioCursoInDTO);

				// Se validan que las franjas a actualizar no se encuentren repetidas
				//this.validarFranjasHorariasAActualizarNoSeanRepetidas(crearActualizarHorarioCursoInDTO);
				/*
				 * Se elimina de la base de datos las franjas horarias del curso que no están en
				 * las franjas a actualizar
				 */
				this.eliminarFranjasDelCurso(crearActualizarHorarioCursoInDTO);
				/*
				 * Se hace flush para reflejar los cambios cuando se invoca
				 * consultarFranjasHorariasDeSemestrePorCurso
				 */
				entityManager.flush();
				/*
				 * Se validan que las franjas a actualizar no se solapen con las franjas de los
				 * cursos del mismo semestre
				 */
				this.validarSolapamientoFranjasAActualizarConCursosDelMismoSemestre(crearActualizarHorarioCursoInDTO,
						(Long) infoCurso[0], periodoAcademicoVigente.getIdPeriodoAcademico());

				List<EspacioFisico> lstEspacioFisicoAActualizar = this.gestionarEspacioFisicoGatewayIntPort
						.consultarCapacidadEstadoYSalonPorListaIdEspacioFisico(crearActualizarHorarioCursoInDTO
								.getListaFranjaHorariaCursoAsociarInDTO().stream()
								.map(FranjaHorariaCursoAsociarInDTO::getIdEspacioFisico).collect(Collectors.toList()));

				// Se valida que el estado sea ACTIVO de los espacios físicos
				this.validarEstadoActivoEspaciosFisicos(crearActualizarHorarioCursoInDTO, lstEspacioFisicoAActualizar);

				// Se valida capacidad de espacios físicos contra cupo del curso
				this.validarCapacidadEspaciosFisicosContraCupoDelCurso(crearActualizarHorarioCursoInDTO,
						(Integer) infoCurso[1], lstEspacioFisicoAActualizar);

				// Se validan cruces
				for (FranjaHorariaCursoAsociarInDTO franjaHorariaCursoAsociarInDTO : crearActualizarHorarioCursoInDTO
						.getListaFranjaHorariaCursoAsociarInDTO()) {

					if (Objects.isNull(franjaHorariaCursoAsociarInDTO.getIdHorario())) {
						Long idEspacioFisico = franjaHorariaCursoAsociarInDTO.getIdEspacioFisico();

						DiaSemanaEnum dia = franjaHorariaCursoAsociarInDTO.getDia();
						LocalTime horaInicio = this
								.convertirToLocalTime(franjaHorariaCursoAsociarInDTO.getHoraInicio());
						LocalTime horaFin = this.convertirToLocalTime(franjaHorariaCursoAsociarInDTO.getHoraFin());

						List<Horario> listaCruceHorarioEspacioFisico = this.gestionarPlanificacionManualGatewayIntPort
								.consultarCruceHorarioEspacioFisico(Arrays.asList(idEspacioFisico), dia, horaInicio,
										horaFin, periodoAcademicoVigente.getIdPeriodoAcademico());
						List<Object[]> listaCruceHorarioDocente = this.gestionarPlanificacionManualGatewayIntPort
								.consultarCruceHorarioDocente(idCurso, dia, horaInicio, horaFin,
										periodoAcademicoVigente.getIdPeriodoAcademico());

						if (!listaCruceHorarioEspacioFisico.isEmpty()) {
							throw new RuntimeException("Existe cruce de espacio físico");

						}

						if (!listaCruceHorarioDocente.isEmpty()) {
							throw new RuntimeException("Existe cruce con docentes");
						}

						Horario horario = new Horario();
						horario.setDia(dia);
						horario.setHoraInicio(horaInicio);
						horario.setHoraFin(horaFin);
						horario.setCurso(new Curso(idCurso));
						horario.setEspaciosFisicos(Arrays.asList(new EspacioFisico(idEspacioFisico)));

						if(Boolean.TRUE.equals(conNuevaTransaccion)) {
							this.gestionarHorarioGatewayIntPort.guardarHorarioConNuevaTransaccion(horario);
						}else {
							this.gestionarHorarioGatewayIntPort.guardarHorario(horario);				
						}
					}
				} // Cierre del for

			} else {
				throw new RuntimeException("El curso no pertenece al periodo académico vigente");
			}

		} else {
			throw new RuntimeException(NO_EXISTE_PERIODO_ACADEMICO_VIGENTE);
		}
	}

	private LocalTime convertirToLocalTime(String hora) {
		return LocalTime.of(Integer.parseInt(hora.split(":")[0]), Integer.parseInt(hora.split(":")[1]));
	}

	private void eliminarFranjasDelCurso(CrearActualizarHorarioCursoInDTO crearActualizarHorarioCursoInDTO) {
		// Se consulta las franjas horarias del curso
		List<Horario> listaFranjasHoriasActualDelCurso = this.gestionarHorarioGatewayIntPort
				.consultarHorarioPorCurso(new Curso(crearActualizarHorarioCursoInDTO.getIdCurso()));
		// Se filtran aquellas franjas que aún se mantienen
		List<Horario> listaFranjasHorariasQueSeMantienen = listaFranjasHoriasActualDelCurso.stream()
				.filter(horario -> crearActualizarHorarioCursoInDTO.getListaFranjaHorariaCursoAsociarInDTO().stream()
						.anyMatch(fraHorCurso -> Objects.equals(fraHorCurso.getIdHorario(), horario.getIdHorario())))
				.collect(Collectors.toList());
		// Se eliminan las franjas horarias del curso que ya no se mantienen
		listaFranjasHoriasActualDelCurso.removeAll(listaFranjasHorariasQueSeMantienen);
		List<Horario> listaHorarioEliminar = listaFranjasHoriasActualDelCurso;

		for (Horario horario : listaHorarioEliminar) {
			this.gestionarHorarioGatewayIntPort.eliminarHorario(horario);
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
			throw new RuntimeException("Estado no permitido para los espacios físicos: "
					+ String.join(", ", espaciosFisicosDiferenteDeActivo));
		}
	}

	private void validarCapacidadEspaciosFisicosContraCupoDelCurso(
			CrearActualizarHorarioCursoInDTO crearActualizarHorarioCursoInDTO, Integer capacidadCurso,
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
			Long idPeriodoAcademicoVigente) {
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
			throw new RuntimeException("Las siguientes franjas solapan franjas de cursos del mismo semestre: "
					+ String.join(", ", franjasQueSolapan));
		}
	}

	private void validarFranjasHorariasAActualizarNoSeanRepetidas(
			CrearActualizarHorarioCursoInDTO crearActualizarHorarioCursoInDTO) {
		// Se validan que las franjas a actualizar no se repitan entre ellas
		Map<String, List<FranjaHorariaCursoAsociarInDTO>> mapaFranjaHorariasAGrupadas = crearActualizarHorarioCursoInDTO
				.getListaFranjaHorariaCursoAsociarInDTO().stream().collect(Collectors
						.groupingBy(obj -> obj.getDia() + "-" + obj.getHoraInicio() + "-" + obj.getHoraFin()));

		List<String> franjasRepetidas = new ArrayList<>();
		for (Map.Entry<String, List<FranjaHorariaCursoAsociarInDTO>> entry : mapaFranjaHorariasAGrupadas.entrySet()) {
			if (entry.getValue().size() > 1) {
				franjasRepetidas.add(entry.getValue().get(0).getDia() + " " + entry.getValue().get(0).getHoraInicio()
						+ "-" + entry.getValue().get(0).getHoraFin());
			}
		}

		if (!franjasRepetidas.isEmpty()) {
			throw new RuntimeException(
					"Las siguientes franjas están repetidas: " + String.join(", ", franjasRepetidas));
		}
	}

	private void validarSolapamientoEntreFranjasAActualizar(
			CrearActualizarHorarioCursoInDTO crearActualizarHorarioCursoInDTO) {

		List<FranjaHorariaBasicaDTO> listaFranjasAActualizar = new ArrayList<>();
		for (FranjaHorariaCursoAsociarInDTO franjaActualizar : crearActualizarHorarioCursoInDTO
				.getListaFranjaHorariaCursoAsociarInDTO()) {
			listaFranjasAActualizar.add(new FranjaHorariaBasicaDTO(franjaActualizar.getDia(),
					this.convertirToLocalTime(franjaActualizar.getHoraInicio()),
					this.convertirToLocalTime(franjaActualizar.getHoraFin())));
		}

		for (FranjaHorariaBasicaDTO franjaActualizar : listaFranjasAActualizar) {

			List<FranjaHorariaBasicaDTO> listaFranjasFiltradas = listaFranjasAActualizar.stream()
					.filter(franja -> !(franja.getDia().equals(franjaActualizar.getDia())
							&& franja.getHoraInicio().equals(franjaActualizar.getHoraInicio())
							&& franja.getHoraFin().equals(franjaActualizar.getHoraFin())))
					.collect(Collectors.toList());

			if (this.seSuperponeConListaFranjas(franjaActualizar, listaFranjasFiltradas)) {
				throw new RuntimeException("Las franjas a actualizar se están solapando");
			}

		}
	}

	private void validarCantidadHorasPermitidasParaCurso(
			CrearActualizarHorarioCursoInDTO crearActualizarHorarioCursoInDTO, Integer horasSemana) {
		int sumaHorasFranjasAActualizar = 0;
		for (FranjaHorariaCursoAsociarInDTO franjaHorariaCursoAsociarInDTO : crearActualizarHorarioCursoInDTO
				.getListaFranjaHorariaCursoAsociarInDTO()) {
			sumaHorasFranjasAActualizar += Integer.parseInt(franjaHorariaCursoAsociarInDTO.getHoraFin().split(":")[0])
					- Integer.parseInt(franjaHorariaCursoAsociarInDTO.getHoraInicio().split(":")[0]);
		}

		if (sumaHorasFranjasAActualizar > horasSemana) {
			throw new RuntimeException(
					"Las franjas a actualizar superan la cantidad de horas permitidas para el curso");
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

		// Lista de las franjas disponibles para el curso
		List<FranjaHorariaCursoDTO> listaDeFranjasDisponibles = new ArrayList<>();

		/*
		 * Se valida que exista periodo académico vigente y que el curso a consultar
		 * pertenezca al mismo
		 */
		if (Objects.nonNull(periodoAcademicoVigente) && this.cursoPerteneceAlPeriodoAcademicoVigente(
				filtroFranjaHorariaDisponibleCursoDTO.getIdCurso(), periodoAcademicoVigente.getIdPeriodoAcademico())) {

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
					.consultarFranjasHorariasDeDocentesAsociadosACurso(
							filtroFranjaHorariaDisponibleCursoDTO.getIdCurso(),
							periodoAcademicoVigente.getIdPeriodoAcademico());

			// Se consultan las franjas candidatas por filtro
			List<FranjaHorariaBasicaDTO> listaFranjasCandidatas = this
					.consultarFranjasCandidatas(filtroFranjaHorariaDisponibleCursoDTO);

			// Se recorre cada espacio físico para evaluarle las franjas candidatas
			for (Map.Entry<Long, List<FranjaHorariaBasicaDTO>> entry : mapaFranjasHorariasPorEspacioFisico.entrySet()) {

				// Se recorre cada franja candidata para validarla contra las restricciones
				for (FranjaHorariaBasicaDTO franjaCandidata : listaFranjasCandidatas) {

					// Restricciones
					Boolean noseSuperponeConFranjasEspaciosFisicos = !seSuperponeConFranjasEspaciosFisicos(
							franjaCandidata, entry.getValue());
					Boolean noseSuperponeConFranjasDocentes = !seSuperponeConFranjasDocentes(franjaCandidata,
							lstFranjasHorariasDocentes);
					Boolean noseSuperponeConFranjasCursosSemestre = !seSuperponeConFranjasCursosSemestre(
							franjaCandidata, lstFranjasHorariasCursosSemestre,
							filtroFranjaHorariaDisponibleCursoDTO.getIdAsignatura());
					Boolean noseSuperponeConFranjasDelMismoCurso = !seSuperponeConFranjasCurso(franjaCandidata,
							lstFranjasHorariasCurso);

					// Se validan las restricciones
					if (noseSuperponeConFranjasEspaciosFisicos && noseSuperponeConFranjasDocentes
							&& noseSuperponeConFranjasCursosSemestre && noseSuperponeConFranjasDelMismoCurso) {
						// Se adiciona la franja candidata como franja posible
						listaDeFranjasDisponibles
								.add(new FranjaHorariaCursoDTO(entry.getKey(), franjaCandidata.getDia(),
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
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
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
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
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
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
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
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
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
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
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
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
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
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
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

	@Override
	public List<FranjaHorariaCursoDTO> consultarFranjasHorariaCursoPorIdCurso(Long idCurso) {
		return this.gestionarPlanificacionManualGatewayIntPort.consultarFranjasHorariaCursoPorIdCurso(idCurso);
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
		if (Objects.nonNull(periodoAcademicoVigente)) {
			List<FranjaHorariaDocenteDTO> lstHorarioDocente = this.gestionarPlanificacionManualGatewayIntPort
					.consultarFranjasDocentePorIdPersona(idPersona, periodoAcademicoVigente.getIdPeriodoAcademico());

			// Se consulta el nombre del salón principal de la franja
			lstHorarioDocente.forEach(franja -> franja.setSalon(this.gestionarEspacioFisicoGatewayIntPort
					.consultarEspacioFisicoPrincipalFranjaPorIdHorario(franja.getIdHorario()).getSalon()));

			return lstHorarioDocente;
		} else {
			throw new RuntimeException(NO_EXISTE_PERIODO_ACADEMICO_VIGENTE);
		}
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
		if (Objects.nonNull(periodoAcademicoVigente)) {
			return this.gestionarPlanificacionManualGatewayIntPort.consultarFranjasEspacioFisicoPorIdEspacioFisico(
					idEspacioFisico, periodoAcademicoVigente.getIdPeriodoAcademico());
		} else {
			throw new RuntimeException(NO_EXISTE_PERIODO_ACADEMICO_VIGENTE);
		}
	}

	@Override
	public List<FormatoPresentacionFranjaHorariaCursoDTO> consultarFormatoPresentacionFranjaHorariaCurso() {
		return this.gestionarPlanificacionManualGatewayIntPort.consultarFormatoPresentacionFranjaHorariaCurso();
	}

	/**
	 * @see co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.input.GestionarPlanificacionManualCUIntPort#eliminarHorarioPrograma(co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTORequest.EliminarHorarioInDTO)
	 */
	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public Boolean eliminarHorarioPrograma(EliminarHorarioInDTO eliminarHorarioInDTO) {
		// Se consulta periodo académico vigente
		PeriodoAcademico periodoAcademicoVigente = gestionarPeriodoAcademicoGatewayIntPort
				.consultarPeriodoAcademicoVigente();
		/*
		 * Se valida que exista periodo académico vigente
		 */
		if (Objects.nonNull(periodoAcademicoVigente)) {

			// Se eliminan registros de HorarioEspacioEntity y HorarioEntity
			this.gestionarPlanificacionManualGatewayIntPort.eliminarHorarioPrograma(eliminarHorarioInDTO,
					periodoAcademicoVigente.getIdPeriodoAcademico());

			return Boolean.TRUE;
		} else {
			throw new RuntimeException(NO_EXISTE_PERIODO_ACADEMICO_VIGENTE);
		}
	}

	/**
	 * @see co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.input.GestionarPlanificacionManualCUIntPort#generarHorarioBasadoEnSemestreAnteriorPorPrograma(co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTORequest.GenerarHorarioBaseInDTO)
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public GenerarHorarioBaseOutDTO generarHorarioBasadoEnSemestreAnteriorPorPrograma(
			GenerarHorarioBaseInDTO generarHorarioBaseInDTO) {
		// Se consulta periodo académico vigente
		PeriodoAcademico periodoAcademicoVigente = gestionarPeriodoAcademicoGatewayIntPort
				.consultarPeriodoAcademicoVigente();
		/*
		 * Se valida que exista periodo académico vigente
		 */
		if (Objects.nonNull(periodoAcademicoVigente)) {
			GenerarHorarioBaseOutDTO generarHorarioBaseOutDTO = new GenerarHorarioBaseOutDTO();
			generarHorarioBaseOutDTO.setIdPrograma(generarHorarioBaseInDTO.getIdPrograma());
			generarHorarioBaseOutDTO.setLstMensajesDelProceso(new ArrayList<>());

			// Se consulta todos los cursos del periodo vigente
			List<Curso> lstCursosActuales = this.gestionarCursoGatewayIntPort
					.consultarCursosPorProgramaYPeriodoAcademico(generarHorarioBaseInDTO.getIdPrograma(),
							periodoAcademicoVigente.getIdPeriodoAcademico());
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
						.collect(Collectors.toList());
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

			// Contador de cursos actualizados
			int cantidadCursosActualizados = 0;

			// Contador de cursos que no mapearon con ningún curso
			int cantidadCursosNoCorrelacionados = 0;

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

					Boolean esCursoActualizado = Boolean.FALSE;
					// Por cada franja horaria del curso se realiza la actualización
					for (Horario horario : cursoBase.getHorarios()) {
						// Se construye franja horaria

						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
						String horaInicio = horario.getHoraInicio().format(formatter);
						String horaFin = horario.getHoraFin().format(formatter);

						FranjaHorariaCursoAsociarInDTO franjaHorariaCursoAsociarInDTO = new FranjaHorariaCursoAsociarInDTO(
								horario.getDia(), horaInicio, horaFin,
								horario.getEspaciosFisicos().get(0).getIdEspacioFisico());
						crearActualizarHorarioCursoInDTO
								.setListaFranjaHorariaCursoAsociarInDTO(Arrays.asList(franjaHorariaCursoAsociarInDTO));

						try {
							this.gestionarPlanificacionManualCUAdapterEJB.validarYCrearActualizarHorarioCurso(crearActualizarHorarioCursoInDTO, true);
							esCursoActualizado = Boolean.TRUE;
						} catch (Exception e) {
							String[] registro = new String[3];
							registro[0] = cursoActual.getAsignatura().getNombre() + " " + cursoActual.getGrupo();
							registro[1] = horario.getDia() + " " + horaInicio + "-" + horaFin;
							registro[2] = e.getMessage();
							// Si se produce algún error técnico o salta alguna restricción
							generarHorarioBaseOutDTO.getLstMensajesDelProceso().add(registro);
						}
					}
					// Se considera un curso actualizado si registró al menos una franja
					if (esCursoActualizado) {
						cantidadCursosActualizados++;
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
			generarHorarioBaseOutDTO.setCantidadCursosActualizados(Long.valueOf(cantidadCursosActualizados));
			generarHorarioBaseOutDTO.setCantidadCursosNoCorrelacionados(Long.valueOf(cantidadCursosNoCorrelacionados));
			return generarHorarioBaseOutDTO;
		} else {
			throw new RuntimeException(NO_EXISTE_PERIODO_ACADEMICO_VIGENTE);
		}
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
		if (Objects.nonNull(periodoAcademicoVigente)) {

			// Se consulta todos los cursos del periodo 2023-1 con identificador 1
			List<Curso> lstCursosBase = this.gestionarCursoGatewayIntPort
					.consultarCursosPorProgramaYPeriodoAcademico(idPrograma, 1L);
			Map<String, Curso> mapaCursosBase = lstCursosBase.stream().collect(Collectors
					.toMap(curso -> curso.getAsignatura().getIdAsignatura() + "-" + curso.getGrupo(), curso -> curso));

			List<Curso> lstCursosVigente = this.gestionarCursoGatewayIntPort
					.consultarCursosPorProgramaYPeriodoAcademico(idPrograma,
							periodoAcademicoVigente.getIdPeriodoAcademico());
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
					Curso cursoBase = mapaCursosBase
							.get(curso.getAsignatura().getIdAsignatura() + "-" + curso.getGrupo());

					for (Docente docente : cursoBase.getDocentes()) {
						docente.getCursos().add(curso);
						this.gestionarDocenteGatewayIntPort.guardarDocente(docente);
					}

					curso.setDocentes(cursoBase.getDocentes());
					this.gestionarCursoGatewayIntPort.guardarCurso(curso);
				}
			}

		} else {
			throw new RuntimeException(NO_EXISTE_PERIODO_ACADEMICO_VIGENTE);
		}
	}

}