package co.edu.unicauca.sgph.gestionplanificacion.labordocencia.domain.useCase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import co.edu.unicauca.sgph.asignatura.aplication.output.GestionarAsignaturaGatewayIntPort;
import co.edu.unicauca.sgph.asignatura.domain.model.Asignatura;
import co.edu.unicauca.sgph.curso.aplication.output.GestionarCursoGatewayIntPort;
import co.edu.unicauca.sgph.curso.domain.model.Curso;
import co.edu.unicauca.sgph.docente.aplication.output.GestionarDocenteGatewayIntPort;
import co.edu.unicauca.sgph.docente.domain.model.Docente;
import co.edu.unicauca.sgph.docente.infrastructure.input.DTORequest.DocenteLaborDTO;
import co.edu.unicauca.sgph.docente.infrastructure.output.persistence.entity.EstadoDocenteEnum;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.MensajeOutDTO;
import co.edu.unicauca.sgph.gestionplanificacion.labordocencia.aplication.input.GestionarLaborDocenciaCUIntPort;
import co.edu.unicauca.sgph.gestionplanificacion.labordocencia.aplication.output.GestionarLaborDocenciaGatewayIntPort;
import co.edu.unicauca.sgph.periodoacademico.aplication.output.GestionarPeriodoAcademicoGatewayIntPort;
import co.edu.unicauca.sgph.periodoacademico.domain.model.PeriodoAcademico;
import co.edu.unicauca.sgph.persona.aplication.output.GestionarPersonaGatewayIntPort;
import co.edu.unicauca.sgph.persona.domain.model.Persona;
import co.edu.unicauca.sgph.persona.domain.model.TipoIdentificacion;
import co.edu.unicauca.sgph.programa.aplication.output.GestionarProgramaGatewayIntPort;
import co.edu.unicauca.sgph.programa.domain.model.Programa;
import co.edu.unicauca.sgph.reporte.infraestructure.input.DTO.ReporteDocenteDTO;

public class GestionarLaborDocenciaCUAdapter implements GestionarLaborDocenciaCUIntPort {

	//Fachada
	private GestionarLaborDocenciaGatewayIntPort gestionarLaborDocenciaGatewayIntPort;
	
	private GestionarDocenteGatewayIntPort gestionarDocenteGatewayIntPort;
	private GestionarCursoGatewayIntPort gestionarCursoGatewayIntPort;
	private GestionarAsignaturaGatewayIntPort gestionarAsignaturaGatewayIntPort;
	private GestionarProgramaGatewayIntPort gestionarProgramaGatewayIntPort;
	private GestionarPeriodoAcademicoGatewayIntPort gestionarPeriodoAcademicoGatewayIntPort;
	private GestionarPersonaGatewayIntPort gestionarPersonaGatewayIntPort;

	public GestionarLaborDocenciaCUAdapter(GestionarLaborDocenciaGatewayIntPort gestionarLaborDocenciaGatewayIntPort,
			GestionarDocenteGatewayIntPort gestionarDocenteGatewayIntPort,
			GestionarCursoGatewayIntPort gestionarCursoGatewayIntPort,
			GestionarAsignaturaGatewayIntPort gestionarAsignaturaGatewayIntPort,
			GestionarProgramaGatewayIntPort gestionarProgramaGatewayIntPort,
			GestionarPeriodoAcademicoGatewayIntPort gestionarPeriodoAcademicoGatewayIntPort,
			GestionarPersonaGatewayIntPort gestionarPersonaGatewayIntPort) {
		this.gestionarLaborDocenciaGatewayIntPort = gestionarLaborDocenciaGatewayIntPort;
		this.gestionarDocenteGatewayIntPort = gestionarDocenteGatewayIntPort;
		this.gestionarCursoGatewayIntPort = gestionarCursoGatewayIntPort;
		this.gestionarAsignaturaGatewayIntPort = gestionarAsignaturaGatewayIntPort;
		this.gestionarProgramaGatewayIntPort = gestionarProgramaGatewayIntPort;
		this.gestionarPeriodoAcademicoGatewayIntPort = gestionarPeriodoAcademicoGatewayIntPort;
		this.gestionarPersonaGatewayIntPort = gestionarPersonaGatewayIntPort;
	}

	@Override
	public MensajeOutDTO cargarLaborDocente(ReporteDocenteDTO reporteDocenteDTO) {
		MensajeOutDTO mensajeOutDTO = new MensajeOutDTO();
		mensajeOutDTO.setError(true);

		// Se valida periodo académico vigente
		PeriodoAcademico periodoVigente = this.gestionarPeriodoAcademicoGatewayIntPort
				.consultarPeriodoAcademicoVigente();
		if (periodoVigente == null) {
			mensajeOutDTO.setDescripcion("No existe periodo vigente");
			return mensajeOutDTO;
		}
		// Se valida que no existan cursos cargados para el programa consultado
		String periodoFormato = periodoVigente.getAnio() + "-" + periodoVigente.getPeriodo();
		Programa programa = gestionarProgramaGatewayIntPort.consultarProgramaPorId(reporteDocenteDTO.getIdPrograma());

		List<DocenteLaborDTO> laborAcademica;

		try {
			// Se lee el excel, simulando la consulta al servicio de SIMCA Labor
			laborAcademica = this.gestionarLaborDocenciaGatewayIntPort
					.consultarLaborDocente(reporteDocenteDTO.getNombrePrograma(), periodoFormato);
			if (laborAcademica.isEmpty()) {
				mensajeOutDTO.setDescripcion("No existe labor académica para el programa consultado");
				return mensajeOutDTO;
			}

			// TODO: Aquí se debe validar que la información esté completa y no haya
			// inconsistencias
			for (DocenteLaborDTO docenteLaborDTO : laborAcademica) {
				// TODO: Cada inconsistencia debe almacenarse en una lista
			}

		} catch (IOException e) {
			mensajeOutDTO.setDescripcion("Error lectura del archivo");
			return mensajeOutDTO;
		}

		// Se valida las restricciones de las asignaturas del sistema contra las del
		// cargue

		// TODO: Falta validar que asignaturas OID no se encuentran en el sistema
		// TODO: Falta validar que todas las asignaturas esten en estado ACTIVO
		List<String> oidAsignaturas = laborAcademica.stream()
			    .map(d -> String.valueOf(d.getOidAsignatura())) // (Long -> String)
			    .distinct()
			    .collect(Collectors.toList());
		List<Asignatura> asignaturas = this.gestionarAsignaturaGatewayIntPort
				.obtenerAsignaturasPorListaOids(oidAsignaturas);
		Boolean valido = asignaturas.size() == oidAsignaturas.size();
		if (!valido) {
			List<String> OIDDiferencia = oidAsignaturas.stream().filter(OID -> !(asignaturas.stream()
					.map(asig -> asig.getOID()).collect(Collectors.toList()).contains(OID)))
					.collect(Collectors.toList());
			mensajeOutDTO.setDescripcion(
					"Hay asignaturas pendientes por registrar o activar en el sistema:\nEn la consulta hay "
							+ oidAsignaturas.size() + " -> OID: " + String.join(", ", oidAsignaturas)
							+ "\nEn el sistema hay " + asignaturas.size() + " -> OID: "
							+ String.join(", ",
									asignaturas.stream().map(asig -> asig.getOID()).collect(Collectors.toList()))
							+ "\nDiferencia OID: " + String.join(", ", OIDDiferencia));
			return mensajeOutDTO;
		}
		// Se crean cursos, docentes y su asociación
		this.guardarLaborDocente(laborAcademica, asignaturas, periodoVigente);
		mensajeOutDTO.setError(false);
		mensajeOutDTO.setDescripcion("Cargue labor docente exitoso");
		return mensajeOutDTO;
	}

	private void guardarLaborDocente(List<DocenteLaborDTO> docentes, List<Asignatura> asignaturas,
			PeriodoAcademico periodoAcademico) {
		docentes.forEach(docenteLabor -> {
			Docente docenteNuevo = this.gestionarDocenteGatewayIntPort
					.consultarDocentePorNumeroIdentificacion(docenteLabor.getIdentificacion());
			if (docenteNuevo == null) {
				docenteNuevo = this.gestionarDocenteGatewayIntPort
						.guardarDocente(this.mapearDocenteLaborPorDocente(docenteLabor));
			}
			this.crearCurso(docenteLabor, docenteNuevo, asignaturas, periodoAcademico);
		});
	}

	private void crearCurso(DocenteLaborDTO docenteLaborDTO, Docente docenteNuevo, List<Asignatura> asignatura,
			PeriodoAcademico periodoAcademico) {
		Curso curso = new Curso();
		curso.setDocentes(Arrays.asList(docenteNuevo));
		curso.setCupo(40); // TODO CUPO
		curso.setGrupo(docenteLaborDTO.getGrupo());
		curso.setHorarios(null); // TODO HORARIOS.
		curso.setPeriodoAcademico(periodoAcademico);
		this.gestionarCursoGatewayIntPort.guardarCurso(curso);
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
		Persona personaNueva = this.gestionarPersonaGatewayIntPort.guardarPersona(persona);
		nuevo.setPersona(personaNueva);
		return nuevo;
	}

	@Override
	@SuppressWarnings("static-access")
	public ReporteDocenteDTO consultaLaborDocente(ReporteDocenteDTO reporteDocenteDTO) {
		ReporteDocenteDTO reporte = new ReporteDocenteDTO();
		try {
			// Se valida periodo académico vigente
			PeriodoAcademico periodoVigente = this.gestionarPeriodoAcademicoGatewayIntPort
					.consultarPeriodoAcademicoVigente();
			if (periodoVigente == null) {
				return null;
			}
			Programa programa = gestionarProgramaGatewayIntPort
					.consultarProgramaPorId(reporteDocenteDTO.getIdPrograma());
			String periodoString = periodoVigente.getAnio() + "-" + periodoVigente.getPeriodo();
			reporte.setArchivoBase64(this.generarExcelBase64(this.gestionarLaborDocenciaGatewayIntPort
					.consultarLaborDocente(programa.getNombre(), periodoString)));
			return reporte;
		} catch (IOException e) {
			return null;
		}
	}
		
	public static String generarExcelBase64(List<DocenteLaborDTO> docenteLaborDTOList) throws IOException {
		Workbook workbook = new XSSFWorkbook(); // o HSSFWorkbook() si necesitas XLS
		Sheet sheet = workbook.createSheet("Docentes");

		// Crear encabezados
		Row headerRow = sheet.createRow(0);
		String[] headers = {"OID Período", "Período", "Identificación", "Primer Apellido", "Segundo Apellido",
				"Primer Nombre", "Segundo Nombre", "Correo", "Nombre Materia", "Nombre Programa",
				"OID", "Código", "Tipo Materia", "Grupo", "Horas Teóricas"};
		for (int i = 0; i < headers.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(headers[i]);
		}

		// Agregar datos
		int rowNum = 1;
		for (DocenteLaborDTO docente : docenteLaborDTOList) {
			Row row = sheet.createRow(rowNum++);

			row.createCell(1).setCellValue(docente.getPeriodo());
			row.createCell(2).setCellValue(docente.getIdentificacion());
			row.createCell(3).setCellValue(docente.getPrimerApellido());
			row.createCell(4).setCellValue(docente.getSegundoApellido());
			row.createCell(5).setCellValue(docente.getPrimerNombre());
			row.createCell(6).setCellValue(docente.getSegundoNombre());
			row.createCell(7).setCellValue(docente.getCorreo());
			row.createCell(8).setCellValue(docente.getNombreMateria());
			row.createCell(9).setCellValue(docente.getNombrePrograma());
			row.createCell(13).setCellValue(docente.getGrupo());
			row.createCell(14).setCellValue(docente.getHorasTeoricas());
		}

		// Convertir a Base64
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		workbook.write(outputStream);
		workbook.close();

		byte[] excelBytes = outputStream.toByteArray();
		return Base64.encodeBase64String(excelBytes);
	}

}