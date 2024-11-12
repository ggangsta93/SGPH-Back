package co.edu.unicauca.sgph.docente.domain.useCase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.edu.unicauca.sgph.asignatura.aplication.input.GestionarAsignaturaCUIntPort;
import co.edu.unicauca.sgph.asignatura.domain.model.Asignatura;
import co.edu.unicauca.sgph.curso.aplication.input.GestionarCursoCUIntPort;
import co.edu.unicauca.sgph.curso.domain.model.Curso;
import co.edu.unicauca.sgph.departamento.aplication.input.GestionarDepartamentoCUIntPort;
import co.edu.unicauca.sgph.departamento.domain.model.Departamento;
import co.edu.unicauca.sgph.docente.aplication.input.GestionarDocenteCUIntPort;
import co.edu.unicauca.sgph.docente.aplication.output.DocenteFormatterResultsIntPort;
import co.edu.unicauca.sgph.docente.aplication.output.GestionarDocenteGatewayIntPort;
import co.edu.unicauca.sgph.docente.domain.model.Docente;
import co.edu.unicauca.sgph.docente.infrastructure.input.DTORequest.DocenteLaborDTO;
import co.edu.unicauca.sgph.docente.infrastructure.input.DTORequest.FiltroDocenteDTO;
import co.edu.unicauca.sgph.docente.infrastructure.input.DTOResponse.DocenteOutDTO;
import co.edu.unicauca.sgph.docente.infrastructure.output.persistence.entity.EstadoDocenteEnum;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.MensajeOutDTO;
import co.edu.unicauca.sgph.facultad.aplication.input.GestionarFacultadCUIntPort;
import co.edu.unicauca.sgph.facultad.domain.model.Facultad;
import co.edu.unicauca.sgph.periodoacademico.aplication.output.GestionarPeriodoAcademicoGatewayIntPort;
import co.edu.unicauca.sgph.periodoacademico.domain.model.PeriodoAcademico;
import co.edu.unicauca.sgph.periodoacademico.infrastructure.output.persistence.entity.PeriodoAcademicoEntity;
import co.edu.unicauca.sgph.persona.aplication.input.GestionarPersonaCUIntPort;
import co.edu.unicauca.sgph.persona.domain.model.Persona;
import co.edu.unicauca.sgph.persona.domain.model.TipoIdentificacion;
import co.edu.unicauca.sgph.programa.aplication.input.GestionarProgramaCUIntPort;
import co.edu.unicauca.sgph.programa.aplication.output.GestionarProgramaGatewayIntPort;
import co.edu.unicauca.sgph.programa.domain.model.Programa;
import co.edu.unicauca.sgph.reporte.infraestructure.input.DTO.ReporteDocenteDTO;
import co.edu.unicauca.sgph.seguridad.jwt.JwtProvider;

public class GestionarDocenteCUAdapter implements GestionarDocenteCUIntPort {

	private final GestionarDocenteGatewayIntPort gestionarDocenteGatewayIntPort;
	private final DocenteFormatterResultsIntPort docenteFormatterResultsIntPort;
	private final GestionarPeriodoAcademicoGatewayIntPort gestionarPeriodoAcademicoGatewayIntPort;
	private final GestionarCursoCUIntPort gestionarCursoCUIntPort;
	private final GestionarProgramaGatewayIntPort gestionarProgramaGatewayIntPort;
	private final GestionarAsignaturaCUIntPort gestionarAsignaturaCUIntPort;
	private final GestionarPersonaCUIntPort gestionarPersonaCUIntPort;
	private final GestionarDepartamentoCUIntPort gestionarDepartamentoCUIntPort;
	private final GestionarProgramaCUIntPort gestionarProgramaCUIntPort;
	private final GestionarFacultadCUIntPort gestionarFacultadCUIntPort;
	private final ObjectMapper objectMapper;
	private final RestTemplate restTemplate;
	private final ModelMapper modelMapper;
	
	@Autowired
	private JwtProvider jwtProvider; 
	
	public GestionarDocenteCUAdapter(
			GestionarDocenteGatewayIntPort gestionarDocenteGatewayIntPort,
			DocenteFormatterResultsIntPort docenteFormatterResultsIntPort,
			GestionarPeriodoAcademicoGatewayIntPort gestionarPeriodoAcademicoGatewayIntPort,
			GestionarCursoCUIntPort gestionarCursoCUIntPort,
			GestionarProgramaGatewayIntPort gestionarProgramaGatewayIntPort,
			GestionarAsignaturaCUIntPort gestionarAsignaturaCUIntPort,
			GestionarPersonaCUIntPort gestionarPersonaCUIntPort, 
			GestionarDepartamentoCUIntPort gestionarDepartamentoCUIntPort, 
			GestionarProgramaCUIntPort gestionarProgramaCUIntPort,
			GestionarFacultadCUIntPort gestionarFacultadCUIntPort,
			RestTemplate restTemplate,
            ObjectMapper objectMapper,
            ModelMapper modelMapper) {
		this.gestionarDocenteGatewayIntPort = gestionarDocenteGatewayIntPort;
		this.docenteFormatterResultsIntPort = docenteFormatterResultsIntPort;
		this.gestionarPeriodoAcademicoGatewayIntPort = gestionarPeriodoAcademicoGatewayIntPort;
		this.gestionarCursoCUIntPort = gestionarCursoCUIntPort;
		this.gestionarProgramaGatewayIntPort = gestionarProgramaGatewayIntPort;
		this.gestionarAsignaturaCUIntPort = gestionarAsignaturaCUIntPort;
		this.gestionarPersonaCUIntPort = gestionarPersonaCUIntPort;
		this.gestionarDepartamentoCUIntPort = gestionarDepartamentoCUIntPort;
		this.gestionarProgramaCUIntPort = gestionarProgramaCUIntPort;
		this.gestionarFacultadCUIntPort = gestionarFacultadCUIntPort;
		this.restTemplate = restTemplate; 
        this.objectMapper = objectMapper;
        this.modelMapper = modelMapper;
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

	@Override
	public List<String> procesarLaborDocenteDesdeJson(List<DocenteLaborDTO> docenteLaborDTOList, Long idFacultad, Long idPrograma) throws IOException {
		List<String> mensajes = new ArrayList<>();

	    // Crear una instancia de Validator
	    Validator validator = javax.validation.Validation.buildDefaultValidatorFactory().getValidator();

	    // Validar cada DocenteLaborDTO en la lista
	    for (DocenteLaborDTO docenteLaborDTO : docenteLaborDTOList) {
	        Set<ConstraintViolation<DocenteLaborDTO>> violations = validator.validate(docenteLaborDTO);

	        if (!violations.isEmpty()) {
	            // Si hay violaciones de validación, lanzar una excepción con los detalles
	            StringBuilder errorMessages = new StringBuilder("Errores de validación en DocenteLaborDTO: ");
	            for (ConstraintViolation<DocenteLaborDTO> violation : violations) {
	                errorMessages.append(String.format("%s: %s; ", violation.getPropertyPath(), violation.getMessage()));
	            }
	            throw new ConstraintViolationException(errorMessages.toString(), violations);
	        }
	    }

	    // Consultar el programa específico para validar que los datos pertenezcan a este programa
	    Programa programa = gestionarProgramaCUIntPort.consultarProgramaPorId(idPrograma);
	    if (programa == null) {
	        mensajes.add("Programa no encontrado para idPrograma: " + idPrograma);
	        return mensajes;
	    }

	    Set<String> programasNombres = Collections.singleton(programa.getNombre());

	    int cursosCreados = 0;
	    int asignaturasCreadas = 0;
	    int docentesCreados = 0;

	    for (DocenteLaborDTO docenteLaborDTO : docenteLaborDTOList) {
	        String programaNombre = docenteLaborDTO.getNombrePrograma();
	        
	        // Verificar si el nombre del programa coincide con el programa especificado por `idPrograma`
	        if (programasNombres.contains(programaNombre)) {
	            guardarPersona(docenteLaborDTO);

	            if (guardarDocente(docenteLaborDTO, idFacultad)) {
	                docentesCreados++;
	            } 

	            if (guardarAsignatura(docenteLaborDTO, mensajes)) {
	                asignaturasCreadas++;
	            } 

	            if (guardarCurso(docenteLaborDTO, mensajes, asignaturasCreadas > 0)) {
	                cursosCreados++;
	            } 
	        }
	    }

	    Long totalDocentesActualmente = this.contarDocentes();
	    Long totalAsignaturasActualmente = gestionarAsignaturaCUIntPort.contarAsignaturas();

	    mensajes.add(String.format("Cantidad de nuevos docentes creados: %d", docentesCreados));
	    mensajes.add(String.format("Total docentes registrados actualmente: %d", totalDocentesActualmente));
	    mensajes.add(String.format("Cantidad de nuevas asignaturas creadas: %d", asignaturasCreadas));
	    mensajes.add(String.format("Total asignaturas registradas actualmente: %d", totalAsignaturasActualmente));
	    mensajes.add(String.format("Cantidad de nuevos cursos creados: %d", cursosCreados));

	    return mensajes;
	}

	private boolean guardarPersona(DocenteLaborDTO docenteLaborDTO) {
	    Persona personaConsulta = gestionarPersonaCUIntPort.consultarPersonaPorIdentificacion(1L, docenteLaborDTO.getIdentificacion());
	    if (personaConsulta == null) {
	        Persona persona = new Persona();
	        TipoIdentificacion tipoIdentificacion = new TipoIdentificacion();
	        if ("Cédula".equals(docenteLaborDTO.getTipoIdentificacion())) {
	            tipoIdentificacion.setIdTipoIdentificacion(1L);  
	            persona.setTipoIdentificacion(tipoIdentificacion);
	        }
	        persona.setNumeroIdentificacion(docenteLaborDTO.getIdentificacion());
	        persona.setPrimerNombre(docenteLaborDTO.getPrimerNombre());
	        persona.setSegundoNombre(docenteLaborDTO.getSegundoNombre());
	        persona.setPrimerApellido(docenteLaborDTO.getPrimerApellido());
	        persona.setSegundoApellido(docenteLaborDTO.getSegundoApellido());
	        persona.setEmail(docenteLaborDTO.getCorreo());

	        this.gestionarPersonaCUIntPort.guardarPersona(persona);
	        return true; // Persona creada
	    }   
	    return false; // Persona ya existente
	}



	private boolean guardarDocente(DocenteLaborDTO docenteLaborDTO, Long idFacultad) {
	    Docente docenteConsultar = this.consultarDocentePorIdentificacion(1L, docenteLaborDTO.getIdentificacion());

	    if (docenteConsultar == null) {
	        Docente docente = new Docente();
	        
	        // Obtener el departamento si existe
	        Departamento departamento = gestionarDepartamentoCUIntPort.consultarDepartamentoPorNombre(docenteLaborDTO.getDepartamento());

	        if (departamento == null) {
	            // Crear un nuevo departamento solo si pertenece a la facultad especificada
	            departamento = new Departamento();
	            departamento.setNombre(docenteLaborDTO.getDepartamento());
	            departamento.setOid(docenteLaborDTO.getOidDepartamento());

	            // Buscar la facultad específica que coincide con el `idFacultad`
	            Facultad facultad = gestionarFacultadCUIntPort.consultarFacultadPorId(idFacultad);
	            if (facultad != null) {
	                departamento.setFacultad(facultad);  // Asignar la facultad al departamento
	                departamento = gestionarDepartamentoCUIntPort.guardarDepartamento(departamento);  // Guardar el departamento
	            } else {
	                System.out.println("Facultad no encontrada para idFacultad: " + idFacultad);
	                return false; // No se guarda el departamento ni el docente
	            }
	        }

	        // Asignar el departamento y otros detalles al docente
	        Persona persona = gestionarPersonaCUIntPort.consultarPersonaPorIdentificacion(1L, docenteLaborDTO.getIdentificacion());
	        docente.setPersona(persona);
	        docente.setEstado(EstadoDocenteEnum.ACTIVO);
	        docente.setDepartamento(departamento);

	        // Guardar el docente en la base de datos
	        this.guardarDocente(docente);
	        return true; 
	    }
	    return false;
	}

	private boolean guardarAsignatura(DocenteLaborDTO docenteLaborDTO, List<String> mensajes) {
	    List<Asignatura> asignaturasExistentes = gestionarAsignaturaCUIntPort.obtenerAsignaturaPorCodigo(docenteLaborDTO.getCodigo());
	    if (asignaturasExistentes.isEmpty()) {
	        Asignatura asignatura = new Asignatura();
	        asignatura.setNombre(docenteLaborDTO.getNombreMateria());
	        asignatura.setCodigoAsignatura(docenteLaborDTO.getCodigo());
	        asignatura.setSemestre(docenteLaborDTO.getSemestre());
	        asignatura.setHorasSemana(docenteLaborDTO.getHorasSemanales());

	        Programa programa = gestionarProgramaCUIntPort.consultarProgramaPorNombre(docenteLaborDTO.getNombrePrograma());
	        if (programa == null) {
	            mensajes.add("Programa no encontrado: " + docenteLaborDTO.getNombrePrograma());
	            return false;
	        }
	        asignatura.setPrograma(programa);

	        gestionarAsignaturaCUIntPort.guardarAsignatura(asignatura);
	        return true;
	    } else {
	        return false;
	    }
	}

	@Transactional
	private boolean guardarCurso(DocenteLaborDTO docenteLaborDTO, List<String> mensajes, boolean asignaturaCreada) {
	    Asignatura asignatura = gestionarAsignaturaCUIntPort.obtenerAsignaturaPorCodigo(docenteLaborDTO.getCodigo()).get(0);
	    
	    PeriodoAcademico periodoAcademicoVigente = gestionarPeriodoAcademicoGatewayIntPort.consultarPeriodoAcademicoVigente();
	    
	    if (periodoAcademicoVigente == null) {
	        mensajes.add("No hay un periodo académico vigente.");
	        return false;
	    }

	    PeriodoAcademicoEntity periodoAcademicoEntity = modelMapper.map(periodoAcademicoVigente, PeriodoAcademicoEntity.class);

	    int rowsUpdated = gestionarCursoCUIntPort.actualizarCurso(
	        periodoAcademicoEntity,
	        docenteLaborDTO.getCantidadEstudiantes(),
	        docenteLaborDTO.getGrupo(),
	        asignatura.getIdAsignatura()
	    );

	    if (rowsUpdated > 0) {
	        return true;
	    } else {
	        Curso curso = new Curso();
	        curso.setGrupo(docenteLaborDTO.getGrupo());
	        curso.setPeriodoAcademico(periodoAcademicoVigente);
	        curso.setAsignatura(asignatura);
	        curso.setCupo(docenteLaborDTO.getCantidadEstudiantes());

	        gestionarCursoCUIntPort.guardarCurso(curso);
	        return true;
	    }
	}




	@Override
	public Long contarDocentes() {
		return this.gestionarDocenteGatewayIntPort.contarDocente();
	}

}