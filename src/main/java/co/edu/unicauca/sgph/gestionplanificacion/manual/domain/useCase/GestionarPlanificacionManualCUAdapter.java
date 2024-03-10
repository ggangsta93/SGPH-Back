package co.edu.unicauca.sgph.gestionplanificacion.manual.domain.useCase;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import co.edu.unicauca.sgph.common.enums.DiaSemanaEnum;
import co.edu.unicauca.sgph.curso.aplication.output.CursoFormatterResultsIntPort;
import co.edu.unicauca.sgph.curso.aplication.output.GestionarCursoGatewayIntPort;
import co.edu.unicauca.sgph.curso.domain.model.Curso;
import co.edu.unicauca.sgph.docente.aplication.output.GestionarDocenteGatewayIntPort;
import co.edu.unicauca.sgph.docente.domain.model.Docente;
import co.edu.unicauca.sgph.espaciofisico.aplication.output.GestionarAulaGatewayIntPort;
import co.edu.unicauca.sgph.espaciofisico.domain.model.Aula;
import co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.input.GestionarPlanificacionManualCUIntPort;
import co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.output.GestionarPlanificacionManualGatewayIntPort;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTORequest.FiltroCursoPlanificacionDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.CursoPlanificacionOutDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.FormatoPresentacionFranjaHorariaCursoDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.FranjaHorariaAulaDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.FranjaHorariaCursoDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.FranjaHorariaDocenteDTO;
import co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTOResponse.InfoGeneralCursosPorProgramaDTO;
import co.edu.unicauca.sgph.horario.aplication.output.GestionarHorarioGatewayIntPort;
import co.edu.unicauca.sgph.horario.domain.model.Horario;
import co.edu.unicauca.sgph.horario.infrastructure.input.DTORequest.CrearActualizarDocentesCursoInDTO;
import co.edu.unicauca.sgph.horario.infrastructure.input.DTORequest.CrearActualizarHorarioCursoInDTO;
import co.edu.unicauca.sgph.horario.infrastructure.input.DTORequest.FiltroFranjaHorariaDisponibleCursoDTO;
import co.edu.unicauca.sgph.horario.infrastructure.input.DTORequest.FranjaHorariaCursoAsociarInDTO;
import co.edu.unicauca.sgph.horario.infrastructure.input.DTOResponse.CrearActualizarDocentesCursoOutDTO;
import co.edu.unicauca.sgph.horario.infrastructure.input.DTOResponse.CrearActualizarHorarioCursoOutDTO;

public class GestionarPlanificacionManualCUAdapter implements GestionarPlanificacionManualCUIntPort {

	private GestionarPlanificacionManualGatewayIntPort gestionarPlanificacionManualGatewayIntPort;
	private CursoFormatterResultsIntPort cursoFormatterResultsIntPort;
	private GestionarDocenteGatewayIntPort gestionarDocenteGatewayIntPort;
	private GestionarAulaGatewayIntPort gestionarAulaGatewayIntPort;
	private GestionarCursoGatewayIntPort gestionarCursoGatewayIntPort;
	private GestionarHorarioGatewayIntPort gestionarHorarioGatewayIntPort;

	@Autowired
	@Lazy
	private GestionarPlanificacionManualCUAdapter gestionarPlanificacionManualCUAdapter;

	public GestionarPlanificacionManualCUAdapter(
			GestionarPlanificacionManualGatewayIntPort gestionarPlanificacionManualGatewayIntPort,
			CursoFormatterResultsIntPort cursoFormatterResultsIntPort,
			GestionarDocenteGatewayIntPort gestionarDocenteGatewayIntPort,
			GestionarAulaGatewayIntPort gestionarAulaGatewayIntPort,
			GestionarCursoGatewayIntPort gestionarCursoGatewayIntPort,
			GestionarHorarioGatewayIntPort gestionarHorarioGatewayIntPort) {
		this.gestionarPlanificacionManualGatewayIntPort = gestionarPlanificacionManualGatewayIntPort;
		this.cursoFormatterResultsIntPort = cursoFormatterResultsIntPort;
		this.gestionarDocenteGatewayIntPort = gestionarDocenteGatewayIntPort;
		this.gestionarAulaGatewayIntPort = gestionarAulaGatewayIntPort;
		this.gestionarCursoGatewayIntPort = gestionarCursoGatewayIntPort;
		this.gestionarHorarioGatewayIntPort = gestionarHorarioGatewayIntPort;
	}

	/**
	 * @see co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.input.GestionarPlanificacionManualCUIntPort#consultarCursosPlanificacionPorFiltro(co.edu.unicauca.sgph.gestionplanificacion.manual.infrastructure.input.DTORequest.FiltroCursoPlanificacionDTO)
	 */
	@Override
	public Page<CursoPlanificacionOutDTO> consultarCursosPlanificacionPorFiltro(
			FiltroCursoPlanificacionDTO filtroCursoPlanificacionDTO) {
		Page<CursoPlanificacionOutDTO> listaCursosDTO = this.gestionarPlanificacionManualGatewayIntPort
				.consultarCursosPlanificacionPorFiltro(filtroCursoPlanificacionDTO);
		for (CursoPlanificacionOutDTO cursoDTO : listaCursosDTO) {
			cursoDTO.setDocentes(
					this.gestionarDocenteGatewayIntPort.consultarNombresDocentesPorIdCurso(cursoDTO.getIdCurso()));
			cursoDTO.setHorarios(gestionarAulaGatewayIntPort.consultarAulaHorarioPorIdCurso(cursoDTO.getIdCurso()));
		}
		return listaCursosDTO;
	}

	/**
	 * @see co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.input.GestionarPlanificacionManualCUIntPort#consultarInfoGeneralCursosPorPrograma(java.lang.Long)
	 */
	@Override
	public InfoGeneralCursosPorProgramaDTO consultarInfoGeneralCursosPorPrograma(Long idPrograma) {
		return this.gestionarPlanificacionManualGatewayIntPort.consultarInfoGeneralCursosPorPrograma(idPrograma);
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

		// Se consulta el curso
		Curso curso = this.gestionarCursoGatewayIntPort
				.consultarCursoPorIdCurso(crearActualizarDocentesCursoInDTO.getIdCurso());

		List<Docente> docentesEliminar = curso.getDocentes().stream()
				.filter(doc -> !crearActualizarDocentesCursoInDTO.getListaIdPersona().contains(doc.getIdPersona()))
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
									horario.getHoraInicio(), horario.getHoraFin());
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

		return crearActualizarDocentesCursoOutDTO;
	}

	/**
	 * @see co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.input.GestionarPlanificacionManualCUIntPort#crearActualizarHorarioCurso(co.edu.unicauca.sgph.horario.infrastructure.input.DTORequest.CrearActualizarHorarioCursoInDTO)
	 */
	@Override
	public CrearActualizarHorarioCursoOutDTO crearActualizarHorarioCurso(
			CrearActualizarHorarioCursoInDTO crearActualizarHorarioCursoInDTO) {
		CrearActualizarHorarioCursoOutDTO crearActualizarHorarioCursoOutDTO = new CrearActualizarHorarioCursoOutDTO();

		try {
			this.gestionarPlanificacionManualCUAdapter
					.validarYCrearActualizarHorarioCurso(crearActualizarHorarioCursoInDTO);
			crearActualizarHorarioCursoOutDTO.setEsExitoso(Boolean.TRUE);
		} catch (RuntimeException e) {
			crearActualizarHorarioCursoOutDTO.setLstMensajesSolapamientos(Arrays.asList(e.getMessage()));
			crearActualizarHorarioCursoOutDTO.setEsExitoso(Boolean.FALSE);
		}

		return crearActualizarHorarioCursoOutDTO;
	}

	@Transactional(rollbackFor = RuntimeException.class)
	public void validarYCrearActualizarHorarioCurso(CrearActualizarHorarioCursoInDTO crearActualizarHorarioCursoInDTO)
			throws RuntimeException {
		Long idCurso = crearActualizarHorarioCursoInDTO.getIdCurso();

		// Se consulta los horarios del curso
		List<Horario> listaHorarioCurso = this.gestionarHorarioGatewayIntPort
				.consultarHorarioPorCurso(new Curso(idCurso));
		// Se excluyen aquellos cursos que tengan coincidencia con el idHorario
		List<Horario> listaHorarioExcluir = listaHorarioCurso.stream()
				.filter(horario -> crearActualizarHorarioCursoInDTO.getListaFranjaHorariaCursoAsociarInDTO().stream()
						.anyMatch(fraHorCurso -> Objects.equals(fraHorCurso.getIdHorario(), horario.getIdHorario())))
				.collect(Collectors.toList());
		// Se elimina la diferencia de cursos listaHorarioCurso-listaHorarioExcluir
		listaHorarioCurso.removeAll(listaHorarioExcluir);
		List<Horario> listaHorarioEliminar = listaHorarioCurso;

		for (Horario horario : listaHorarioEliminar) {
			this.gestionarHorarioGatewayIntPort.eliminarHorario(horario);
		}

		// Se validan cruces
		for (FranjaHorariaCursoAsociarInDTO franjaHorariaCursoAsociarInDTO : crearActualizarHorarioCursoInDTO
				.getListaFranjaHorariaCursoAsociarInDTO()) {

			if (Objects.isNull(franjaHorariaCursoAsociarInDTO.getIdHorario())) {
				Long idAula = franjaHorariaCursoAsociarInDTO.getIdAula();

				DiaSemanaEnum dia = franjaHorariaCursoAsociarInDTO.getDia();
				LocalTime horaInicio = LocalTime.of(
						Integer.parseInt(franjaHorariaCursoAsociarInDTO.getHoraInicio().split(":")[0]),
						Integer.parseInt(franjaHorariaCursoAsociarInDTO.getHoraInicio().split(":")[1]));
				LocalTime horaFin = LocalTime.of(
						Integer.parseInt(franjaHorariaCursoAsociarInDTO.getHoraFin().split(":")[0]),
						Integer.parseInt(franjaHorariaCursoAsociarInDTO.getHoraFin().split(":")[1]));

				List<Horario> listaCruceHorarioAula = this.gestionarPlanificacionManualGatewayIntPort
						.consultarCruceHorarioAula(Arrays.asList(idAula), dia, horaInicio, horaFin);
				List<Object[]> listaCruceHorarioDocente = this.gestionarPlanificacionManualGatewayIntPort
						.consultarCruceHorarioDocente(idCurso, dia, horaInicio, horaFin);

				if (!listaCruceHorarioAula.isEmpty()) {
					throw new RuntimeException("Existe cruce de aula");

				}

				if (!listaCruceHorarioDocente.isEmpty()) {
					throw new RuntimeException("Existe cruce con docentes");
				}

				Horario horario = new Horario();
				horario.setDia(dia);
				horario.setHoraInicio(horaInicio);
				horario.setHoraFin(horaFin);
				horario.setCurso(new Curso(idCurso));
				horario.setAulas(Arrays.asList(new Aula(idAula)));
				this.gestionarHorarioGatewayIntPort.guardarHorario(horario);
			}
		}
	}

	/**
	 * @see co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.input.GestionarPlanificacionManualCUIntPort#consultarFranjasHorariasDisponiblesPorCurso(co.edu.unicauca.sgph.horario.infrastructure.input.DTORequest.FiltroFranjaHorariaDisponibleCursoDTO)
	 */
	@Override
	public List<FranjaHorariaCursoDTO> consultarFranjasHorariasDisponiblesPorCurso(
			FiltroFranjaHorariaDisponibleCursoDTO filtroFranjaHorariaDisponibleCursoDTO) {
		return this.gestionarPlanificacionManualGatewayIntPort
				.consultarFranjasHorariasDisponiblesPorCurso(filtroFranjaHorariaDisponibleCursoDTO);
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
		return this.gestionarPlanificacionManualGatewayIntPort.consultarFranjasDocentePorIdPersona(idPersona);
	}

	/** 
	 * @see co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.input.GestionarPlanificacionManualCUIntPort#consultarFranjasAulaPorIdAula(java.lang.Long)
	 */
	@Override
	public List<FranjaHorariaAulaDTO> consultarFranjasAulaPorIdAula(Long idAula) {
		 return this.gestionarPlanificacionManualGatewayIntPort.consultarFranjasAulaPorIdAula(idAula);
	}

	@Override
	public List<FormatoPresentacionFranjaHorariaCursoDTO> consultarFormatoPresentacionFranjaHorariaCurso() {
		return this.gestionarPlanificacionManualGatewayIntPort.consultarFormatoPresentacionFranjaHorariaCurso();
	}
	
	

}