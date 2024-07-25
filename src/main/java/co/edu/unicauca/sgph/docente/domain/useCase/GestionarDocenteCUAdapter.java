package co.edu.unicauca.sgph.docente.domain.useCase;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import co.edu.unicauca.sgph.asignatura.aplication.input.GestionarAsignaturaCUIntPort;
import co.edu.unicauca.sgph.curso.aplication.input.GestionarCursoCUIntPort;
import co.edu.unicauca.sgph.curso.domain.model.Curso;
import co.edu.unicauca.sgph.docente.infrastructure.input.DTORequest.DocenteLaborDTO;
import co.edu.unicauca.sgph.docente.infrastructure.output.persistence.entity.EstadoDocenteEnum;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.MensajeOutDTO;
import co.edu.unicauca.sgph.periodoacademico.aplication.output.GestionarPeriodoAcademicoGatewayIntPort;
import co.edu.unicauca.sgph.periodoacademico.domain.model.PeriodoAcademico;
import co.edu.unicauca.sgph.periodoacademico.infrastructure.output.persistence.gateway.GestionarPeriodoAcademicoGatewayImplAdapter;
import co.edu.unicauca.sgph.persona.aplication.input.GestionarPersonaCUIntPort;
import co.edu.unicauca.sgph.persona.domain.model.Persona;
import co.edu.unicauca.sgph.persona.domain.model.TipoIdentificacion;
import co.edu.unicauca.sgph.programa.aplication.output.GestionarProgramaGatewayIntPort;
import co.edu.unicauca.sgph.programa.domain.model.Programa;
import co.edu.unicauca.sgph.programa.infrastructure.output.persistence.entity.ProgramaEntity;
import co.edu.unicauca.sgph.reporte.infraestructure.input.DTO.ReporteDocenteDTO;
import org.springframework.data.domain.Page;

import co.edu.unicauca.sgph.docente.aplication.input.GestionarDocenteCUIntPort;
import co.edu.unicauca.sgph.docente.aplication.output.DocenteFormatterResultsIntPort;
import co.edu.unicauca.sgph.docente.aplication.output.GestionarDocenteGatewayIntPort;
import co.edu.unicauca.sgph.docente.domain.model.Docente;
import co.edu.unicauca.sgph.docente.infrastructure.input.DTORequest.FiltroDocenteDTO;
import co.edu.unicauca.sgph.docente.infrastructure.input.DTOResponse.DocenteOutDTO;

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

		// 1
		PeriodoAcademico periodoVigente = this.obtenerPeriodoVigente();
		if (periodoVigente == null) {
			mensaje.setDescripcion("No existe periodo vigente");
			return mensaje;
		}
		// 2
		List<Curso> cursos = this.gestionarCursoCUIntPort.consultarCursosPorIdPeriodoYIdPrograma(periodoVigente.getIdPeriodoAcademico(), archivoDocente.getIdPrograma());
		if (cursos != null && cursos.size() > 0) {
			mensaje.setDescripcion("Existen cursos para el periodo academico vigente");
			return mensaje;
		}
		// 3 Filtrar por docente y programa usando el mock del excel
		// 3.1 leer todo el excel.
		List<DocenteLaborDTO> docentes = this.gestionarDocenteGatewayIntPort.cargarLaborDocente(archivoDocente);
		if (docentes == null) {
			mensaje.setDescripcion("Error lectura");
			return mensaje;
		}
		// 3.2 filtrar por periodo y programa.
		Programa programa = gestionarProgramaGatewayIntPort.consultarProgramaPorId(archivoDocente.getIdPrograma());
		String periodoString = periodoVigente.getAnio() + "-"+ periodoVigente.getPeriodo();
		List<DocenteLaborDTO> docentesFiltrados = docentes.stream().filter(docente -> docente.getNombrePrograma().equals(programa.getNombre()) && docente.getPeriodo().equals(periodoString)).collect(Collectors.toList());
		// 3.3 VALIDAR EXISTENCIA ASIGANTURA.
		List<String> oidAsignaturas = docentesFiltrados.stream().map(d -> d.getOid()).collect(Collectors.toList());
		Boolean valido = this.gestionarAsignaturaCUIntPort.validarExistenciaAsignaturasPorOID(oidAsignaturas);
		if (valido) {
			mensaje.setDescripcion("Hay asignaturas pendientes por registrar o activar en el sistema");
			return mensaje;
		}
		// 4 CREAR DOCENTES
		this.crearDocentes(docentesFiltrados);
		mensaje.setError(false);
		mensaje.setDescripcion("Cargue labor docente exitoso");
		return mensaje;
	}

	private void crearDocentes(List<DocenteLaborDTO> docentes) {
		docentes.forEach(nuevoDocente -> {
			Docente docente = this.gestionarDocenteGatewayIntPort.consultarDocentePorNumeroIdentificacion(nuevoDocente.getIdentificacion());
			if (docente == null) {
				this.gestionarDocenteGatewayIntPort.guardarDocente(this.mapearDocenteLaborPorDocente(nuevoDocente));
			}
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