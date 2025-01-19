package co.edu.unicauca.sgph.reporte.infraestructure.output;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import co.edu.unicauca.sgph.asignatura.infrastructure.output.persistence.entity.AsignaturaEntity;
import co.edu.unicauca.sgph.common.enums.DiaSemanaEnum;
import co.edu.unicauca.sgph.curso.infrastructure.output.persistence.entity.CursoEntity;
import co.edu.unicauca.sgph.docente.infrastructure.output.persistence.entity.DocenteEntity;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.EspacioFisicoEntity;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.EstadoEspacioFisicoEnum;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.UbicacionEntity;
import co.edu.unicauca.sgph.facultad.infrastructure.output.persistence.entity.FacultadEntity;
import co.edu.unicauca.sgph.horario.infrastructure.output.persistence.entity.HorarioEntity;
import co.edu.unicauca.sgph.periodoacademico.infrastructure.output.persistence.entity.PeriodoAcademicoEntity;
import co.edu.unicauca.sgph.programa.infrastructure.output.persistence.entity.ProgramaEntity;
import co.edu.unicauca.sgph.reporte.aplication.output.GestionarReporteGatewayIntPort;
import co.edu.unicauca.sgph.reporte.infraestructure.input.DTO.CursoDTO;
import co.edu.unicauca.sgph.reporte.infraestructure.input.DTO.HorarioDTO;
import co.edu.unicauca.sgph.reporte.infraestructure.input.DTO.ReporteDocenteDTO;
import co.edu.unicauca.sgph.reporte.infraestructure.input.DTO.ReporteEspacioFisicoDTO;
import co.edu.unicauca.sgph.reporte.infraestructure.input.DTO.ReporteFranjasLibresDTO;
import co.edu.unicauca.sgph.reporte.infraestructure.input.DTO.ReporteSimcaDTO;

@Service
public class GestionarReporteGatewayImplAdapter implements GestionarReporteGatewayIntPort {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public ReporteSimcaDTO generarReporteSimca(ReporteSimcaDTO reporteSimcaDTO) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<CursoEntity> criteriaQuery = cb.createQuery(CursoEntity.class);
		Root<CursoEntity> root = criteriaQuery.from(CursoEntity.class);
		Join<CursoEntity,AsignaturaEntity> joinCurso = root.join("asignatura", JoinType.LEFT);
		Join<AsignaturaEntity, ProgramaEntity> joinPrograma = joinCurso.join("programa", JoinType.LEFT);
		Join<ProgramaEntity, FacultadEntity> joinFacultad = joinPrograma.join("facultad", JoinType.LEFT);
		Join<CursoEntity, HorarioEntity> joinHorario = root.join("horarios", JoinType.LEFT);
		Join<CursoEntity, PeriodoAcademicoEntity> joinPeriodo = root.join("periodoAcademico", JoinType.LEFT);
		Join<CursoEntity, DocenteEntity> joinDocente =  root.join("docentes", JoinType.LEFT);
		Join<HorarioEntity, EspacioFisicoEntity> joinEspacio = joinHorario.join("espaciosFisicos", JoinType.LEFT);

		Predicate predicate = cb.conjunction();
		if (reporteSimcaDTO.getIdPeriodo() != null) {
			Predicate predicatePeriodo = cb.and(predicate, cb.equal(joinPeriodo.get("idPeriodoAcademico"), reporteSimcaDTO.getIdPeriodo()));
			predicate = predicatePeriodo;
		}
		if (reporteSimcaDTO.getIdPrograma() != null) {
			Predicate predicatePrograma = cb.and(predicate, cb.equal(joinPrograma.get("idPrograma"), reporteSimcaDTO.getIdPrograma()));
			predicate = predicatePrograma;
		}
		if (reporteSimcaDTO.getIdFacultad() != null) {
			Predicate predicateFacultad = cb.and(predicate, cb.equal(joinFacultad.get("idFacultad"), reporteSimcaDTO.getIdFacultad()));
			predicate = predicateFacultad;
		}

		criteriaQuery.where(predicate);
		criteriaQuery.distinct(true);
		List<CursoEntity> resultList = entityManager.createQuery(criteriaQuery).getResultList();
		List<ReporteSimcaDTO> datos = resultList.stream().map(this::mapCursoEntityReporteDTO).collect(Collectors.toList());
		try {
			return this.crearExcel(datos);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public ReporteDocenteDTO obtenerReporteLaborDocente(ReporteDocenteDTO filtro) {
		Workbook workbook = new XSSFWorkbook();
	    Sheet sheet = workbook.createSheet("Horario Docente");

	    // Estilo para encabezados
	    CellStyle headerCellStyle = workbook.createCellStyle();
	    Font headerFont = workbook.createFont();
	    headerFont.setBold(true);
	    headerCellStyle.setFont(headerFont);
	    headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
	    headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
	    headerCellStyle.setBorderBottom(BorderStyle.THIN);
	    headerCellStyle.setBorderTop(BorderStyle.THIN);
	    headerCellStyle.setBorderLeft(BorderStyle.THIN);
	    headerCellStyle.setBorderRight(BorderStyle.THIN);

	    // Estilo para celdas de contenido
	    CellStyle contentCellStyle = workbook.createCellStyle();
	    contentCellStyle.setAlignment(HorizontalAlignment.CENTER);
	    contentCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
	    contentCellStyle.setWrapText(true); // Ajustar texto automáticamente
	    contentCellStyle.setBorderBottom(BorderStyle.THIN);
	    contentCellStyle.setBorderTop(BorderStyle.THIN);
	    contentCellStyle.setBorderLeft(BorderStyle.THIN);
	    contentCellStyle.setBorderRight(BorderStyle.THIN);

	    // Encabezados de columnas
	    String[] columnHeaders = {"HORAS", "LUNES", "MARTES", "MIÉRCOLES", "JUEVES", "VIERNES", "SÁBADO", "DOMINGO"};
	    Row headerRow = sheet.createRow(0);
	    for (int i = 0; i < columnHeaders.length; i++) {
	        Cell cell = headerRow.createCell(i);
	        cell.setCellValue(columnHeaders[i]);
	        cell.setCellStyle(headerCellStyle);
	    }

	    // Ajustar ancho de columnas (en unidades de Excel, no píxeles)
	    sheet.setColumnWidth(0, 256 * 12); // Columna de horas (aproximadamente 60 píxeles)
	    for (int i = 1; i < columnHeaders.length; i++) {
	        sheet.setColumnWidth(i, 256 * 30); // Columnas de días (aproximadamente 246 píxeles)
	    }

	 // Crear la tabla completa con bordes (de 07:00 a 22:00 para todos los días)
	    for (int hora = 7; hora <= 22; hora++) {
	        Row row = sheet.createRow(hora - 6);
	        // Crear celda de la columna de horas
	        Cell hourCell = row.createCell(0);
	        hourCell.setCellValue(String.format("%02d:00", hora));
	        hourCell.setCellStyle(contentCellStyle);
	        row.setHeight((short) (60 * 15)); // Altura de fila

	        // Crear celdas vacías con bordes para cada día
	        for (int col = 1; col <= 7; col++) {
	            Cell cell = row.createCell(col);
	            cell.setCellStyle(contentCellStyle);
	        }
	    }

	    // Mapeo de días a columnas
	    Map<String, Integer> dayColumnMap = Map.of(
	        "LUNES", 1,
	        "MARTES", 2,
	        "MIERCOLES", 3,
	        "JUEVES", 4,
	        "VIERNES", 5,
	        "SABADO", 6,
	        "DOMINGO", 7
	    );

	    // Agregar los datos al Excel desde el parámetro
	    for (CursoDTO curso : filtro.getCursos()) {
	        if (curso.getHorarios() != null && !curso.getHorarios().isEmpty()) {
	            for (HorarioDTO horario : curso.getHorarios()) {
	                try {
	                    int startHour = horario.getHorarioInicio().getHour();
	                    int endHour = horario.getHoraFin().getHour();
	                    int rowIndexStart = startHour - 7 + 1; // Ajustar al índice de la fila (7:00 AM es la fila 1)
	                    int rowIndexEnd = endHour - 7 + 1;

	                    // Determinar columna por día
	                    String dia = horario.getDia().name().toUpperCase();
	                    if (!dayColumnMap.containsKey(dia)) {
	                        continue; // Saltar si el día no está mapeado
	                    }
	                    int columnIndex = dayColumnMap.get(dia);

	                    // Crear contenido para la celda
	                    String docente = filtro.getPrimerNombre() + " " +
	                                     (filtro.getSegundoNombre() != null ? filtro.getSegundoNombre() + " " : "") +
	                                     filtro.getPrimerApellido() + " " +
	                                     (filtro.getSegundoApellido() != null ? filtro.getSegundoApellido() : "");
	                    String cellValue = docente + "\n" + curso.getNombreAsignatura() + "\n" + horario.getSalon();

	                    // Colocar el valor en la celda y fusionar filas si es necesario
	                    Row dataRow = sheet.getRow(rowIndexStart);
	                    if (dataRow == null) {
	                        dataRow = sheet.createRow(rowIndexStart);
	                    }
	                    Cell cell = dataRow.createCell(columnIndex);
	                    cell.setCellValue(cellValue);
	                    cell.setCellStyle(contentCellStyle);

	                    // Fusionar celdas si la duración es mayor a 1 hora
	                    if (rowIndexEnd > rowIndexStart) {
	                        sheet.addMergedRegion(new CellRangeAddress(rowIndexStart, rowIndexEnd - 1, columnIndex, columnIndex));
	                    }
	                } catch (Exception e) {
	                    System.err.println("Error procesando horario: " + e.getMessage());
	                }
	            }
	        }
	    }

	    // Generar archivo Excel en base64
	    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	    try {
	        workbook.write(byteArrayOutputStream);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    String base64EncodedExcel = Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
	    try {
	        byteArrayOutputStream.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    try {
	        workbook.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    // Retornar el archivo en el DTO
	    ReporteDocenteDTO reporteFinal = new ReporteDocenteDTO();
	    reporteFinal.setArchivoBase64(base64EncodedExcel);
	    return reporteFinal;
	}

	private ReporteSimcaDTO mapCursoEntityReporteDTO(CursoEntity curso) {
		ReporteSimcaDTO dto = new ReporteSimcaDTO();
		dto.setCupo(curso.getCupo());
		//dto.setMatriculados(curso.getCupo().longValue());
		dto.setAbreviaturaPrograma(curso.getAsignatura().getPrograma().getAbreviatura());
		dto.setCodigoAsignatura(curso.getAsignatura().getCodigoAsignatura());
		dto.setNombreAsignatura(curso.getAsignatura().getNombre());
		dto.setNombreGrupo(curso.getGrupo());
		dto.setSemestre(String.valueOf(curso.getAsignatura().getSemestre()));
		dto.setOIDAsignatura(curso.getAsignatura().getOid());
		dto.setPeriodo(curso.getPeriodoAcademico().getAnio().toString() + "-" + curso.getPeriodoAcademico().getPeriodo().toString());
		dto.setNombreDocente(this.nombreDocentes(curso.getDocentes()));
		List<HorarioEntity> horario = curso.getHorarios();
		if (horario != null) {
			dto.setHorarios(curso.getHorarios().stream().map(this::mapHorarioEnityToDTO).collect(Collectors.toList()));
		}
		return dto;
	}
	private String nombreDocentes(List<DocenteEntity> docentes) {
		List<String> nombres = docentes.stream().map(this::nombreCompletoDocente).collect(Collectors.toList());
		return String.join(", ", nombres);
	}
	private String nombreCompletoDocente(DocenteEntity docente) {
		String nombreCompleto = docente.getPersona().getPrimerNombre() + " " + docente.getPersona().getSegundoNombre() + " " + docente.getPersona().getPrimerApellido() + " " + docente.getPersona().getSegundoApellido();
		return nombreCompleto;
	}
	private HorarioDTO mapHorarioEnityToDTO(HorarioEntity entidad) {
		HorarioDTO dto = new HorarioDTO();
		dto.setDia(entidad.getDia());
		dto.setHorarioInicio(entidad.getHoraInicio());
		dto.setHoraFin(entidad.getHoraFin());
		dto.setSalon(this.obtenerNombreSalon(entidad.getEspaciosFisicos().get(0)));
		return dto;
	}
	private String obtenerNombreSalon(EspacioFisicoEntity espacioFisico) {
		return espacioFisico != null ? espacioFisico.getSalon() : "";
	}
	public ReporteSimcaDTO crearExcel(List<ReporteSimcaDTO> reporteSimcaList) throws IOException {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Reporte Simca");
		CellStyle headerCellStyle = workbook.createCellStyle();
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerCellStyle.setFont(headerFont);
		headerCellStyle.setBorderBottom(BorderStyle.THIN);
		headerCellStyle.setBorderTop(BorderStyle.THIN);
		headerCellStyle.setBorderLeft(BorderStyle.THIN);
		headerCellStyle.setBorderRight(BorderStyle.THIN);
		String[] columnHeaders = {
				"PER", "PROGRAMA", "SEM", "OID_MATERIA", "CODIGO_MATERIA",
				"MATERIA", "GRUPO", "CUPO", "LUNES", "MARTES",
				"MIERCOLES", "JUEVES", "VIERNES", "SABADO", "DOCENTES"
		};
		Row headerRow = sheet.createRow(0);
		for (int i = 0; i < columnHeaders.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columnHeaders[i]);
			cell.setCellStyle(headerCellStyle);
		}
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
		int rowNum = 1;
		for (ReporteSimcaDTO reporteSimca : reporteSimcaList) {
			Row dataRow = sheet.createRow(rowNum++);
			dataRow.createCell(0).setCellValue(reporteSimca.getPeriodo() != null ? reporteSimca.getPeriodo() : "");
			dataRow.createCell(1).setCellValue(reporteSimca.getAbreviaturaPrograma() != null ? reporteSimca.getAbreviaturaPrograma() : "");
			dataRow.createCell(2).setCellValue(reporteSimca.getSemestre() != null ? reporteSimca.getSemestre() : "");
			dataRow.createCell(3).setCellValue(reporteSimca.getOIDAsignatura() != null ? reporteSimca.getOIDAsignatura() : "");
			dataRow.createCell(4).setCellValue(reporteSimca.getCodigoAsignatura() != null ? reporteSimca.getCodigoAsignatura() : "");
			dataRow.createCell(5).setCellValue(reporteSimca.getNombreAsignatura() != null ? reporteSimca.getNombreAsignatura() : "");
			dataRow.createCell(6).setCellValue(reporteSimca.getNombreGrupo() != null ? reporteSimca.getNombreGrupo() : "");
			dataRow.createCell(7).setCellValue(reporteSimca.getCupo() != null ? reporteSimca.getCupo() : 0);
			//dataRow.createCell(8).setCellValue(reporteSimca.getMatriculados() != null ? reporteSimca.getMatriculados() : 0L);
			Map<DiaSemanaEnum, StringBuilder> horariosPorDia = new EnumMap<>(DiaSemanaEnum.class);
			for (DiaSemanaEnum dia : DiaSemanaEnum.values()) {
				horariosPorDia.put(dia, new StringBuilder());
			}
			List<HorarioDTO> horarios = reporteSimca.getHorarios();
			if (horarios != null) {
				for (HorarioDTO horario : horarios) {
					DiaSemanaEnum dia = horario.getDia();
					String horarioStr = horario.getHorarioInicio().format(timeFormatter) + "-" + horario.getHoraFin().format(timeFormatter)  + " " + horario.getSalon();
					horariosPorDia.get(dia).append(horarioStr).append(", ");
				}
			}
			int colIndex = 8;
			for (DiaSemanaEnum dia : DiaSemanaEnum.values()) {
				StringBuilder horariosStr = horariosPorDia.get(dia);
				if (horariosStr.length() > 0) {
					horariosStr.setLength(horariosStr.length() - 2);
				}
				dataRow.createCell(colIndex++).setCellValue(horariosStr.toString());
			}
			dataRow.createCell(14).setCellValue(reporteSimca.getNombreDocente() != null ? reporteSimca.getNombreDocente() : "");
		}

		for (int i = 0; i < columnHeaders.length; i++) {
			sheet.autoSizeColumn(i);
		}
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		workbook.write(byteArrayOutputStream);

		String base64EncodedExcel = Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
		byteArrayOutputStream.close();
		workbook.close();
		ReporteSimcaDTO reporte = new ReporteSimcaDTO();
		reporte.setArchivoBase64(base64EncodedExcel);
		return reporte;
	}

	public ReporteDocenteDTO crearExcelDocente(List<ReporteDocenteDTO> listaReporte) throws IOException {
		Workbook workbook = new XSSFWorkbook();
	    Sheet sheet = workbook.createSheet("Horario Docente");

	    // Estilo para encabezados
	    CellStyle headerCellStyle = workbook.createCellStyle();
	    Font headerFont = workbook.createFont();
	    headerFont.setBold(true);
	    headerCellStyle.setFont(headerFont);
	    headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
	    headerCellStyle.setBorderBottom(BorderStyle.THIN);
	    headerCellStyle.setBorderTop(BorderStyle.THIN);
	    headerCellStyle.setBorderLeft(BorderStyle.THIN);
	    headerCellStyle.setBorderRight(BorderStyle.THIN);

	    // Encabezados de columnas
	    String[] columnHeaders = {"HORAS", "LUNES", "MARTES", "MIÉRCOLES", "JUEVES", "VIERNES", "SÁBADO", "DOMINGO"};
	    Row headerRow = sheet.createRow(0);
	    for (int i = 0; i < columnHeaders.length; i++) {
	        Cell cell = headerRow.createCell(i);
	        cell.setCellValue(columnHeaders[i]);
	        cell.setCellStyle(headerCellStyle);
	    }

	    // Filas de horas (de 07:00 a 22:00)
	    for (int hora = 7; hora <= 22; hora++) {
	        Row row = sheet.createRow(hora - 6); // Inicia en la fila 1
	        Cell cell = row.createCell(0);
	        cell.setCellValue(String.format("%02d:00", hora));
	    }

	    // Mapeo de días a columnas
	    Map<String, Integer> dayColumnMap = Map.of(
	        "LUNES", 1,
	        "MARTES", 2,
	        "MIERCOLES", 3,
	        "JUEVES", 4,
	        "VIERNES", 5,
	        "SABADO", 6,
	        "DOMINGO", 7
	    );

	    // Agregar los datos al Excel
	 // Agregar los datos al Excel
	    for (ReporteDocenteDTO reporte : listaReporte) {
	        for (CursoDTO curso : reporte.getCursos()) {
	            if (curso.getHorarios() == null || curso.getHorarios().isEmpty()) {
	                continue; // Saltar si no hay horarios
	            }
	            for (HorarioDTO horario : curso.getHorarios()) {
	                try {
	                    // Determinar fila por hora de inicio
	                    int startHour = horario.getHorarioInicio().getHour();
	                    int rowIndex = startHour - 7 + 1; // Ajustar al índice de la fila (7:00 AM es la fila 1)

	                    // Determinar columna por día
	                    DiaSemanaEnum dia = horario.getDia();
	                    if (!dayColumnMap.containsKey(dia)) {
	                        continue; // Saltar si el día no está mapeado
	                    }
	                    int columnIndex = dayColumnMap.get(dia);

	                    // Crear contenido para la celda
	                    String cellValue = curso.getNombreAsignatura() + " " + horario.getSalon();

	                    // Colocar el valor en la celda
	                    Row dataRow = sheet.getRow(rowIndex);
	                    if (dataRow == null) {
	                        dataRow = sheet.createRow(rowIndex);
	                    }
	                    Cell cell = dataRow.getCell(columnIndex);
	                    if (cell == null) {
	                        cell = dataRow.createCell(columnIndex);
	                    }
	                    cell.setCellValue(cellValue);
	                } catch (Exception e) {
	                    // Log o manejo de errores para datos inválidos
	                    System.err.println("Error procesando horario: " + e.getMessage());
	                }
	            }
	        }
	    }


	    // Ajustar tamaño de las columnas
	    for (int i = 0; i < columnHeaders.length; i++) {
	        sheet.autoSizeColumn(i);
	    }

	    // Generar archivo Excel en base64
	    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	    workbook.write(byteArrayOutputStream);
	    String base64EncodedExcel = Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
	    byteArrayOutputStream.close();
	    workbook.close();

	    // Retornar el archivo en el DTO
	    ReporteDocenteDTO reporteFinal = new ReporteDocenteDTO();
	    reporteFinal.setArchivoBase64(base64EncodedExcel);
	    return reporteFinal;
	}



	@Override
	public ReporteEspacioFisicoDTO obtenerReporteHorarioEspaciosFisicos(ReporteEspacioFisicoDTO filtro) {
		Workbook workbook = new XSSFWorkbook();
	    Sheet sheet = workbook.createSheet("Horario Espacios Físicos");

	    // Estilo para encabezados
	    CellStyle headerCellStyle = workbook.createCellStyle();
	    Font headerFont = workbook.createFont();
	    headerFont.setBold(true);
	    headerCellStyle.setFont(headerFont);
	    headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
	    headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
	    headerCellStyle.setBorderBottom(BorderStyle.THIN);
	    headerCellStyle.setBorderTop(BorderStyle.THIN);
	    headerCellStyle.setBorderLeft(BorderStyle.THIN);
	    headerCellStyle.setBorderRight(BorderStyle.THIN);

	    // Estilo para celdas de contenido
	    CellStyle contentCellStyle = workbook.createCellStyle();
	    contentCellStyle.setAlignment(HorizontalAlignment.CENTER);
	    contentCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
	    contentCellStyle.setWrapText(true);
	    contentCellStyle.setBorderBottom(BorderStyle.THIN);
	    contentCellStyle.setBorderTop(BorderStyle.THIN);
	    contentCellStyle.setBorderLeft(BorderStyle.THIN);
	    contentCellStyle.setBorderRight(BorderStyle.THIN);

	    // Encabezados de columnas
	    String[] columnHeaders = {"HORAS", "LUNES", "MARTES", "MIÉRCOLES", "JUEVES", "VIERNES", "SÁBADO", "DOMINGO"};
	    Row headerRow = sheet.createRow(0);
	    for (int i = 0; i < columnHeaders.length; i++) {
	        Cell cell = headerRow.createCell(i);
	        cell.setCellValue(columnHeaders[i]);
	        cell.setCellStyle(headerCellStyle);
	    }

	    // Ajustar ancho de columnas
	    sheet.setColumnWidth(0, 256 * 12); // Columna de horas
	    for (int i = 1; i < columnHeaders.length; i++) {
	        sheet.setColumnWidth(i, 256 * 30);
	    }

	 // Crear la tabla completa con bordes (de 07:00 a 22:00 para todos los días)
	    for (int hora = 7; hora <= 22; hora++) {
	        Row row = sheet.createRow(hora - 6);
	        // Crear celda de la columna de horas
	        Cell hourCell = row.createCell(0);
	        hourCell.setCellValue(String.format("%02d:00", hora));
	        hourCell.setCellStyle(contentCellStyle);
	        row.setHeight((short) (60 * 15)); // Altura de fila

	        // Crear celdas vacías con bordes para cada día
	        for (int col = 1; col <= 7; col++) {
	            Cell cell = row.createCell(col);
	            cell.setCellStyle(contentCellStyle);
	        }
	    }

	    // Mapeo de días a columnas
	    Map<String, Integer> dayColumnMap = Map.of(
	        "LUNES", 1,
	        "MARTES", 2,
	        "MIERCOLES", 3,
	        "JUEVES", 4,
	        "VIERNES", 5,
	        "SABADO", 6,
	        "DOMINGO", 7
	    );

	    // Agregar los datos al Excel desde el parámetro
	    if (filtro.getHorarios() != null && !filtro.getHorarios().isEmpty()) {
	        for (HorarioDTO horario : filtro.getHorarios()) {
	            try {
	                int startHour = horario.getHorarioInicio().getHour();
	                int endHour = horario.getHoraFin().getHour();
	                int rowIndexStart = startHour - 7 + 1;
	                int rowIndexEnd = endHour - 7 + 1;

	                // Determinar columna por día
	                String dia = horario.getDia().name().toUpperCase();
	                if (!dayColumnMap.containsKey(dia)) {
	                    continue; // Saltar si el día no está mapeado
	                }
	                int columnIndex = dayColumnMap.get(dia);

	                // Crear contenido para la celda
	                String cellValue = filtro.getNombreEspacio() + "\n" + 
	                                   horario.getSalon();

	                // Colocar el valor en la celda y fusionar filas si es necesario
	                Row dataRow = sheet.getRow(rowIndexStart);
	                if (dataRow == null) {
	                    dataRow = sheet.createRow(rowIndexStart);
	                }
	                Cell cell = dataRow.createCell(columnIndex);
	                cell.setCellValue(cellValue);
	                cell.setCellStyle(contentCellStyle);

	                // Fusionar celdas si la duración es mayor a 1 hora
	                if (rowIndexEnd > rowIndexStart) {
	                    sheet.addMergedRegion(new CellRangeAddress(rowIndexStart, rowIndexEnd - 1, columnIndex, columnIndex));
	                }
	            } catch (Exception e) {
	                System.err.println("Error procesando horario: " + e.getMessage());
	            }
	        }
	    }

	    // Generar archivo Excel en base64
	    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	    try {
	        workbook.write(byteArrayOutputStream);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    String base64EncodedExcel = Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
	    try {
	        byteArrayOutputStream.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    try {
	        workbook.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    // Retornar el archivo en el DTO
	    ReporteEspacioFisicoDTO reporteFinal = new ReporteEspacioFisicoDTO();
	    reporteFinal.setArchivoBase64(base64EncodedExcel);
	    return reporteFinal;
	}


	@Override
	public ReporteFranjasLibresDTO obtenerReporteHorarioFranjasLibres(List<ReporteFranjasLibresDTO> franjasLibres) {
		Workbook workbook = new XSSFWorkbook();
	    Sheet sheet = workbook.createSheet("Franjas Libres");

	    // Estilo para encabezados
	    CellStyle headerCellStyle = workbook.createCellStyle();
	    Font headerFont = workbook.createFont();
	    headerFont.setBold(true);
	    headerCellStyle.setFont(headerFont);
	    headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
	    headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
	    headerCellStyle.setBorderBottom(BorderStyle.THIN);
	    headerCellStyle.setBorderTop(BorderStyle.THIN);
	    headerCellStyle.setBorderLeft(BorderStyle.THIN);
	    headerCellStyle.setBorderRight(BorderStyle.THIN);

	    // Estilo para celdas de contenido
	    CellStyle contentCellStyle = workbook.createCellStyle();
	    contentCellStyle.setAlignment(HorizontalAlignment.CENTER);
	    contentCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
	    contentCellStyle.setWrapText(true);
	    contentCellStyle.setBorderBottom(BorderStyle.THIN);
	    contentCellStyle.setBorderTop(BorderStyle.THIN);
	    contentCellStyle.setBorderLeft(BorderStyle.THIN);
	    contentCellStyle.setBorderRight(BorderStyle.THIN);

	    // Encabezados de columnas
	    String[] columnHeaders = {"HORAS", "LUNES", "MARTES", "MIÉRCOLES", "JUEVES", "VIERNES", "SÁBADO", "DOMINGO"};
	    Row headerRow = sheet.createRow(0);
	    for (int i = 0; i < columnHeaders.length; i++) {
	        Cell cell = headerRow.createCell(i);
	        cell.setCellValue(columnHeaders[i]);
	        cell.setCellStyle(headerCellStyle);
	    }

	    // Ajustar ancho de columnas
	    sheet.setColumnWidth(0, 256 * 12); // Columna de horas
	    for (int i = 1; i < columnHeaders.length; i++) {
	        sheet.setColumnWidth(i, 256 * 50);
	    }

	    // Rango de horas de 07:00 a 22:00
	    List<String> horas = IntStream.rangeClosed(7, 22)
	            .mapToObj(h -> String.format("%02d:00-%02d:00", h, h + 1))
	            .collect(Collectors.toList());

	    // Mapeo de días a columnas
	    Map<String, Integer> dayColumnMap = Map.of(
	            "LUNES", 1,
	            "MARTES", 2,
	            "MIERCOLES", 3,
	            "JUEVES", 4,
	            "VIERNES", 5,
	            "SABADO", 6,
	            "DOMINGO", 7
	    );

	    // Agrupar franjas libres por hora y día
	    Map<String, Map<Integer, List<String>>> franjasAgrupadas = new HashMap<>();
	    for (ReporteFranjasLibresDTO franja : franjasLibres) {
	        int col = dayColumnMap.getOrDefault(franja.getDia().name().toUpperCase(), -1);
	        if (col < 0) continue;

	        int horaInicio = franja.getHoraInicio().getHour();
	        int horaFin = franja.getHoraFin().getHour();
	        for (int h = horaInicio; h < horaFin; h++) {
	            String horaClave = String.format("%02d:00-%02d:00", h, h + 1);
	            franjasAgrupadas.putIfAbsent(horaClave, new HashMap<>());
	            franjasAgrupadas.get(horaClave).putIfAbsent(col, new ArrayList<>());
	            franjasAgrupadas.get(horaClave).get(col).add(franja.getSalon());
	        }
	    }

	    // Llenar la hoja con franjas agrupadas, asegurando que todas las celdas tengan bordes
	    int rowIndex = 1;
	    for (String hora : horas) {
	        Row row = sheet.createRow(rowIndex++);
	        // Crear celda para la hora
	        Cell hourCell = row.createCell(0);
	        hourCell.setCellValue(hora);
	        hourCell.setCellStyle(contentCellStyle);

	        // Crear celdas vacías con bordes para cada día
	        for (int col = 1; col <= 7; col++) {
	            Cell cell = row.createCell(col);
	            cell.setCellStyle(contentCellStyle);
	        }

	        // Llenar las celdas con los salones disponibles
	        Map<Integer, List<String>> salonesPorDia = franjasAgrupadas.getOrDefault(hora, new HashMap<>());
	        for (Map.Entry<Integer, List<String>> entry : salonesPorDia.entrySet()) {
	            int col = entry.getKey();
	            List<String> salones = entry.getValue();
	            Cell cell = row.getCell(col);
	            if (cell == null) {
	                cell = row.createCell(col);
	            }
	         // Concatenar "Franja libre" al nombre del salón
	            List<String> salonesConEtiqueta = salones.stream()
	                .map(salon -> "Franja libre " + salon )
	                .collect(Collectors.toList());
	            cell.setCellValue(String.join(", ", salonesConEtiqueta));
	            cell.setCellStyle(contentCellStyle);
	        }
	        row.setHeight((short) (60 * 15)); // Altura de fila
	    }

	    // Generar archivo Excel en base64
	    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	    try {
	        workbook.write(byteArrayOutputStream);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    String base64EncodedExcel = Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
	    try {
	        byteArrayOutputStream.close();
	        workbook.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    // Retornar el archivo en el DTO
	    ReporteFranjasLibresDTO reporteFinal = new ReporteFranjasLibresDTO();
	    reporteFinal.setArchivoBase64(base64EncodedExcel);
	    return reporteFinal;
	}


}