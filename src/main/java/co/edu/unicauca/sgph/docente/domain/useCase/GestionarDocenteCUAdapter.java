package co.edu.unicauca.sgph.docente.domain.useCase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import co.edu.unicauca.sgph.asignatura.aplication.input.GestionarAsignaturaCUIntPort;
import co.edu.unicauca.sgph.asignatura.domain.model.Asignatura;
import co.edu.unicauca.sgph.curso.aplication.input.GestionarCursoCUIntPort;
import co.edu.unicauca.sgph.curso.domain.model.Curso;
import co.edu.unicauca.sgph.docente.aplication.input.GestionarDocenteCUIntPort;
import co.edu.unicauca.sgph.docente.aplication.output.DocenteFormatterResultsIntPort;
import co.edu.unicauca.sgph.docente.aplication.output.GestionarDocenteGatewayIntPort;
import co.edu.unicauca.sgph.docente.domain.model.Docente;
import co.edu.unicauca.sgph.docente.infrastructure.input.DTORequest.DocenteLaborDTO;
import co.edu.unicauca.sgph.docente.infrastructure.input.DTORequest.FiltroDocenteDTO;
import co.edu.unicauca.sgph.docente.infrastructure.input.DTOResponse.DocenteOutDTO;
import co.edu.unicauca.sgph.docente.infrastructure.output.persistence.entity.EstadoDocenteEnum;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.MensajeOutDTO;
import co.edu.unicauca.sgph.periodoacademico.aplication.output.GestionarPeriodoAcademicoGatewayIntPort;
import co.edu.unicauca.sgph.periodoacademico.domain.model.PeriodoAcademico;
import co.edu.unicauca.sgph.persona.aplication.input.GestionarPersonaCUIntPort;
import co.edu.unicauca.sgph.persona.domain.model.Persona;
import co.edu.unicauca.sgph.persona.domain.model.TipoIdentificacion;
import co.edu.unicauca.sgph.programa.aplication.output.GestionarProgramaGatewayIntPort;
import co.edu.unicauca.sgph.programa.domain.model.Programa;
import co.edu.unicauca.sgph.reporte.infraestructure.input.DTO.ReporteDocenteDTO;

public class GestionarDocenteCUAdapter implements GestionarDocenteCUIntPort {

	private final GestionarDocenteGatewayIntPort gestionarDocenteGatewayIntPort;
	private final DocenteFormatterResultsIntPort docenteFormatterResultsIntPort;
	private final GestionarPeriodoAcademicoGatewayIntPort gestionarPeriodoAcademicoGatewayIntPort;
	private final GestionarCursoCUIntPort gestionarCursoCUIntPort;
	private final GestionarProgramaGatewayIntPort gestionarProgramaGatewayIntPort;
	private final GestionarAsignaturaCUIntPort gestionarAsignaturaCUIntPort;
	private final GestionarPersonaCUIntPort gestionarPersonaCUIntPort;

	public GestionarDocenteCUAdapter(
			GestionarDocenteGatewayIntPort gestionarDocenteGatewayIntPort,
			DocenteFormatterResultsIntPort docenteFormatterResultsIntPort,
			GestionarPeriodoAcademicoGatewayIntPort gestionarPeriodoAcademicoGatewayIntPort,
			GestionarCursoCUIntPort gestionarCursoCUIntPort,
			GestionarProgramaGatewayIntPort gestionarProgramaGatewayIntPort,
			GestionarAsignaturaCUIntPort gestionarAsignaturaCUIntPort,
			GestionarPersonaCUIntPort gestionarPersonaCUIntPort) {
		this.gestionarDocenteGatewayIntPort = gestionarDocenteGatewayIntPort;
		this.docenteFormatterResultsIntPort = docenteFormatterResultsIntPort;
		this.gestionarPeriodoAcademicoGatewayIntPort = gestionarPeriodoAcademicoGatewayIntPort;
		this.gestionarCursoCUIntPort = gestionarCursoCUIntPort;
		this.gestionarProgramaGatewayIntPort = gestionarProgramaGatewayIntPort;
		this.gestionarAsignaturaCUIntPort = gestionarAsignaturaCUIntPort;
		this.gestionarPersonaCUIntPort = gestionarPersonaCUIntPort;
	}

	@Override
	public Docente guardarDocente(Docente docente) {
		return this.gestionarDocenteGatewayIntPort.guardarDocente(docente);
	}

	/**
	 * @see co.edu.unicauca.sgph.docente.aplication.input.GestionarDocenteCUIntPort#consultarDocentePorIdentificacion(java.lang.Long,
	 *      java.lang.String)
	 */
	@Override
	public Docente consultarDocentePorIdentificacion(Long idTipoIdentificacion, String numeroIdentificacion) {
		return this.gestionarDocenteGatewayIntPort.consultarDocentePorIdentificacion(idTipoIdentificacion,
				numeroIdentificacion);
	}

	/**
	 * @see co.edu.unicauca.sgph.docente.aplication.input.GestionarDocenteCUIntPort#consultarDocentePorIdPersona(java.lang.Long)
	 */
	@Override
	public Docente consultarDocentePorIdPersona(Long idPersona) {
		Docente docente = this.gestionarDocenteGatewayIntPort.consultarDocentePorIdPersona(idPersona);
		return Objects.nonNull(docente) ? docente
				: this.docenteFormatterResultsIntPort
				.prepararRespuestaFallida("No se encontró docente con ese idPersona");
	}

	/**
	 * @see co.edu.unicauca.sgph.docente.aplication.input.GestionarDocenteCUIntPort#consultarNombresDocentesPorIdCurso(java.lang.Long)
	 */
	@Override
	public List<String> consultarNombresDocentesPorIdCurso(Long idCurso) {
		return this.gestionarDocenteGatewayIntPort.consultarNombresDocentesPorIdCurso(idCurso);
	}

	/**
	 * @see co.edu.unicauca.sgph.docente.aplication.input.GestionarDocenteCUIntPort#consultarDocentes(co.edu.unicauca.sgph.docente.infrastructure.input.DTORequest.FiltroDocenteDTO)
	 */
	@Override
	public Page<DocenteOutDTO> consultarDocentes(FiltroDocenteDTO filtroDocenteDTO) {
		return this.gestionarDocenteGatewayIntPort.consultarDocentes(filtroDocenteDTO);
	}

	/**
	 * @see co.edu.unicauca.sgph.docente.aplication.input.GestionarDocenteCUIntPort#consultarDocentePorIdCurso(java.lang.Long)
	 */
	@Override
	public List<Docente> consultarDocentePorIdCurso(Long idCurso) {
		return this.gestionarDocenteGatewayIntPort.consultarDocentePorIdCurso(idCurso);
	}

	@Override
	public MensajeOutDTO cargarLaborDocente(ReporteDocenteDTO archivoDocente) {		
		MensajeOutDTO mensaje = new MensajeOutDTO();
		mensaje.setError(true);

		// Se valida periodo académico vigente
		PeriodoAcademico periodoVigente = this.obtenerPeriodoVigente();
		if (periodoVigente == null) {
			mensaje.setDescripcion("No existe periodo vigente");
			return mensaje;
		}
		// Se valida que no existan cursos cargados para el programa consultado
		String  periodoFormato = periodoVigente.getAnio() + "-"+ periodoVigente.getPeriodo();
		Programa programa = gestionarProgramaGatewayIntPort.consultarProgramaPorId(archivoDocente.getIdPrograma());
		List<Curso> cursos = this.gestionarCursoCUIntPort.consultarCursosPorIdPeriodoYIdPrograma(periodoVigente.getIdPeriodoAcademico(), archivoDocente.getIdPrograma());
		if (cursos != null && cursos.size() > 0) {
			mensaje.setDescripcion("Ya existe una carga de cursos para el programa "+programa.getNombre());
			return mensaje;
		}
		// Se consulta la labor académica del programa
		List<DocenteLaborDTO> laborAcademica;
		try {
			// Se lee el excel, simulando la consulta al servicio de SIMCA Labor
			laborAcademica = this.gestionarDocenteGatewayIntPort.cargarLaborDocente(programa.getNombre(),periodoFormato);
			if(laborAcademica.isEmpty()) {
				mensaje.setDescripcion("No existe labor académica para el programa consultado");
				return mensaje;
			}
				
			//TODO: Aquí se debe validar que la información esté completa y no haya inconsistencias
			for (DocenteLaborDTO docenteLaborDTO : laborAcademica) {
				//TODO: Cada inconsistencia debe almacenarse en una lista			
			}			
			
		} catch (IOException e) {
			mensaje.setDescripcion("Error lectura del archivo");
			return mensaje;
		}		
		
		// Se valida las restricciones de las asignaturas del sistema contra las del cargue
		
		//TODO: Falta validar que asignaturas OID no se encuentran en el sistema
		//TODO: Falta validar que todas las asignaturas esten en estado ACTIVO
		List<String> oidAsignaturas = laborAcademica.stream().map(d -> d.getOid()).distinct().collect(Collectors.toList());
		List<Asignatura> asignaturas = this.gestionarAsignaturaCUIntPort.obtenerAsignaturasPorOids(oidAsignaturas);
		Boolean valido = asignaturas.size() == oidAsignaturas.size();
		if (!valido) {			
			List<String> OIDDiferencia = oidAsignaturas.stream().filter(OID -> !(asignaturas.stream().map(asig -> asig.getOID()).collect(Collectors.toList()).contains(OID))).collect(Collectors.toList());
			mensaje.setDescripcion(
					"Hay asignaturas pendientes por registrar o activar en el sistema:\nEn la consulta hay "
							+ oidAsignaturas.size() + " -> OID: " + String.join(", ", oidAsignaturas)
							+ "\nEn el sistema hay " + asignaturas.size() + " -> OID: "
							+ String.join(", ",
									asignaturas.stream().map(asig -> asig.getOID()).collect(Collectors.toList()))
							+ "\nDiferencia OID: "+String.join(", ", OIDDiferencia));
			return mensaje;
		}
		// Se crean cursos, docentes y su asociación
		this.guardarLaborDocente(laborAcademica, asignaturas, periodoVigente);
		mensaje.setError(false);
		mensaje.setDescripcion("Cargue labor docente exitoso");
		return mensaje;
	}

	@Override
	public ReporteDocenteDTO consultaLaborDocente(ReporteDocenteDTO filtro) {
		ReporteDocenteDTO reporte = new ReporteDocenteDTO();
		try {
			// Se valida periodo académico vigente
			PeriodoAcademico periodoVigente = this.obtenerPeriodoVigente();
			if (periodoVigente == null) {
				return null;
			}
			Programa programa = gestionarProgramaGatewayIntPort.consultarProgramaPorId(filtro.getIdPrograma());
			String periodoString = periodoVigente.getAnio() + "-"+ periodoVigente.getPeriodo();
			reporte.setArchivoBase64(this.gestionarDocenteGatewayIntPort.obtenerBase64ArchivoFiltrado(programa.getNombre(),periodoString));
			return reporte;
		} catch (IOException e) {
			return null;
		}
	}

	private void crearCurso(DocenteLaborDTO docenteLaborDTO, Docente docenteNuevo, List<Asignatura> asignatura, PeriodoAcademico periodoAcademico) {
		Curso curso = new Curso();
		curso.setDocentes(Arrays.asList(docenteNuevo));
		Asignatura asignaturaExistente = asignatura.stream().filter(a -> a.getOID() == docenteLaborDTO.getOid()).collect(Collectors.toList()).get(0);
		curso.setAsignatura(asignaturaExistente);
		curso.setCupo(40); // TODO CUPO
		curso.setGrupo(docenteLaborDTO.getGrupo());
		curso.setHorarios(null); // TODO HORARIOS.
		curso.setPeriodoAcademico(periodoAcademico);
		this.gestionarCursoCUIntPort.guardarCurso(curso);
	}
	private void guardarLaborDocente(List<DocenteLaborDTO> docentes, List<Asignatura> asignaturas, PeriodoAcademico periodoAcademico) {
		List<Docente> docentesNuevos = new ArrayList<>();
		docentes.forEach(docenteLabor -> {
			Docente docenteNuevo = this.gestionarDocenteGatewayIntPort.consultarDocentePorNumeroIdentificacion(docenteLabor.getIdentificacion());
			if (docenteNuevo == null) {
				docenteNuevo = this.gestionarDocenteGatewayIntPort.guardarDocente(this.mapearDocenteLaborPorDocente(docenteLabor));
			}
			this.crearCurso(docenteLabor, docenteNuevo, asignaturas, periodoAcademico);
		});
	}
	private Docente mapearDocenteLaborPorDocente(DocenteLaborDTO dto) {
		Docente nuevo = new Docente();
		nuevo.setCodigo(dto.getIdentificacion()); // TODO verificar que codigo colocarle
		nuevo.setEstado(EstadoDocenteEnum.ACTIVO);
		Persona persona = new Persona();
		persona.setEmail(dto.getCorreo());
		persona.setNumeroIdentificacion(dto.getIdentificacion());
		persona.setPrimerNombre(dto.getPrimerNombre());
		persona.setSegundoNombre(dto.getSegundoNombre());
		persona.setPrimerApellido(dto.getPrimerApellido());
		persona.setSegundoApellido(dto.getSegundoApellido());
		TipoIdentificacion tipoIdentificacion = new TipoIdentificacion();
		tipoIdentificacion.setIdTipoIdentificacion(1L); // TODO verificar tipo identificacion
		persona.setTipoIdentificacion(tipoIdentificacion);
		Persona personaNueva = this.gestionarPersonaCUIntPort.guardarPersona(persona);
		nuevo.setPersona(personaNueva);
		return nuevo;
	}
	private PeriodoAcademico obtenerPeriodoVigente() {
		return this.gestionarPeriodoAcademicoGatewayIntPort.consultarPeriodoAcademicoVigente();
	}

}