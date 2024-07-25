package co.edu.unicauca.sgph.docente.infrastructure.output.persistence.gateway;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import co.edu.unicauca.sgph.docente.infrastructure.input.DTORequest.DocenteLaborDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.MensajeOutDTO;
import co.edu.unicauca.sgph.reporte.infraestructure.input.DTO.ReporteDocenteDTO;
import org.apache.commons.codec.binary.Base64;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import co.edu.unicauca.sgph.docente.aplication.output.GestionarDocenteGatewayIntPort;
import co.edu.unicauca.sgph.docente.domain.model.Docente;
import co.edu.unicauca.sgph.docente.infrastructure.input.DTORequest.FiltroDocenteDTO;
import co.edu.unicauca.sgph.docente.infrastructure.input.DTOResponse.DocenteOutDTO;
import co.edu.unicauca.sgph.docente.infrastructure.output.persistence.entity.DocenteEntity;
import co.edu.unicauca.sgph.docente.infrastructure.output.persistence.repository.DocenteRepositoryInt;

@Service
public class GestionarDocenteGatewayImplAdapter implements GestionarDocenteGatewayIntPort {

	@PersistenceContext
	private EntityManager entityManager;

	private DocenteRepositoryInt docenteRepositoryInt;
	private ModelMapper modelMapper;

	public GestionarDocenteGatewayImplAdapter(DocenteRepositoryInt docenteRepository, ModelMapper modelMapper) {
		this.docenteRepositoryInt = docenteRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public Docente guardarDocente(Docente docente) {
		return this.modelMapper.map(this.docenteRepositoryInt.save(this.modelMapper.map(docente, DocenteEntity.class)),
				Docente.class);
	}

	/** 
	 * @see co.edu.unicauca.sgph.docente.aplication.output.GestionarDocenteGatewayIntPort#consultarDocentePorIdentificacion(java.lang.Long, java.lang.String)
	 */
	@Override
	public Docente consultarDocentePorIdentificacion(Long idTipoIdentificacion, String numeroIdentificacion) {		
		DocenteEntity docenteEntity = this.docenteRepositoryInt
				.consultarDocentePorIdentificacion(idTipoIdentificacion, numeroIdentificacion);
		if (Objects.isNull(docenteEntity)) {
			return null;
		}	
		return this.modelMapper.map(docenteEntity, Docente.class);
	}

	/** 
	 * @see co.edu.unicauca.sgph.docente.aplication.output.GestionarDocenteGatewayIntPort#consultarDocentePorIdPersona(java.lang.Long)
	 */
	@Override
	public Docente consultarDocentePorIdPersona(Long idPersona) {
		DocenteEntity docenteEntity = this.docenteRepositoryInt.consultarDocentePorIdPersona(idPersona);
		if (Objects.isNull(docenteEntity)) {
			return null;
		} else {
			return this.modelMapper.map(docenteEntity, Docente.class);
		}
	}

	/** 
	 * @see co.edu.unicauca.sgph.docente.aplication.output.GestionarDocenteGatewayIntPort#consultarNombresDocentesPorIdCurso(java.lang.Long)
	 */
	@Override
	public List<String> consultarNombresDocentesPorIdCurso(Long idCurso) {
		return this.docenteRepositoryInt.consultarNombresDocentesPorIdCurso(idCurso);
	}

	/** 
	 * @see co.edu.unicauca.sgph.docente.aplication.output.GestionarDocenteGatewayIntPort#consultarDocentes(co.edu.unicauca.sgph.docente.infrastructure.input.DTORequest.FiltroDocenteDTO)
	 */
	@Override
	public Page<DocenteOutDTO> consultarDocentes(FiltroDocenteDTO filtroDocenteDTO) {
		PageRequest pageable = null;
		if (Objects.nonNull(filtroDocenteDTO.getPagina())
				&& Objects.nonNull(filtroDocenteDTO.getRegistrosPorPagina())) {
			// Configuración de la paginación
			pageable = PageRequest.of(filtroDocenteDTO.getPagina(), filtroDocenteDTO.getRegistrosPorPagina());
		}

		// Construcción de la consulta con StringBuilder
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(" SELECT NEW co.edu.unicauca.sgph.docente.infrastructure.input.DTOResponse.DocenteOutDTO(");
		queryBuilder.append(" doc.idDocente, per.idPersona, ti.idTipoIdentificacion, per.numeroIdentificacion, ");
		queryBuilder.append(" ti.codigoTipoIdentificacion, per.primerNombre, per.segundoNombre, per.primerApellido, ");
		queryBuilder.append(" per.segundoApellido, per.email, doc.codigo, doc.estado, doc.departamento.idDepartamento)");
		queryBuilder.append(" FROM DocenteEntity doc ");
		queryBuilder.append(" JOIN doc.persona per ");
		queryBuilder.append(" JOIN per.tipoIdentificacion ti ");
		queryBuilder.append(" WHERE 1=1");

		Map<String, Object> parametros = new HashMap<>();

		if (Objects.nonNull(filtroDocenteDTO.getNombre())) {
			queryBuilder.append(" AND UPPER(TRIM(BOTH ' ' FROM CONCAT( ");
			queryBuilder.append(" per.primerNombre,");
			queryBuilder.append(" CASE WHEN per.segundoNombre IS NOT NULL THEN CONCAT(' ', per.segundoNombre) ELSE '' END,");
			queryBuilder.append(" CONCAT(' ',per.primerApellido),");
			queryBuilder.append(" CASE WHEN per.segundoApellido IS NOT NULL THEN CONCAT(' ', per.segundoApellido) ELSE '' END )))");
			queryBuilder.append(" LIKE UPPER(:nombre)");
			parametros.put("nombre", "%"+filtroDocenteDTO.getNombre().replaceAll("\\s+", " ").trim()+"%");
		}		
		if (Objects.nonNull(filtroDocenteDTO.getNumeroIdentificacion())
				&& !filtroDocenteDTO.getNumeroIdentificacion().isEmpty()) {
			queryBuilder.append(" AND per.numeroIdentificacion LIKE :numeroIdentificacion");
			parametros.put("numeroIdentificacion",
					"%" + filtroDocenteDTO.getNumeroIdentificacion().replaceAll("\\s+", " ").trim() + "%");
		}
		if (Objects.nonNull(filtroDocenteDTO.getCodigo()) && !filtroDocenteDTO.getCodigo().isEmpty()) {
			queryBuilder.append(" AND doc.codigo LIKE :codigo");
			parametros.put("codigo", "%" + filtroDocenteDTO.getCodigo().replaceAll("\\s+", " ").trim() + "%");
		}
		if (Objects.nonNull(filtroDocenteDTO.getEstado())) {
			queryBuilder.append(" AND doc.estado = :estado");
			parametros.put("estado", filtroDocenteDTO.getEstado());
		}

		queryBuilder.append(" ORDER BY per.primerNombre asc");

		// Realiza la consulta paginada
		TypedQuery<DocenteOutDTO> typedQuery = entityManager.createQuery(queryBuilder.toString(), DocenteOutDTO.class);
		
		if (Objects.nonNull(pageable)) {
	        typedQuery.setFirstResult((int) pageable.getOffset());
	        typedQuery.setMaxResults(pageable.getPageSize());
	    }

		// Establece parámetros en la consulta
		for (Map.Entry<String, Object> entry : parametros.entrySet()) {
			typedQuery.setParameter(entry.getKey(), entry.getValue());
		}

		 // Si pageable es nulo, entonces retornar todos los resultados sin paginación
	    if (Objects.isNull(pageable)) {
	        return new PageImpl<>(typedQuery.getResultList());
	    }else {
	    	return new PageImpl<>(typedQuery.getResultList(), pageable, contarDocentesConsultados(filtroDocenteDTO));
	    }
	}

	/**
	 * Método encargado de contabilizar los docentes por filtro<br>
	 * 
	 * @author apedro
	 * 
	 * @param filtroDocenteDTO
	 * @return
	 */
	private Long contarDocentesConsultados(FiltroDocenteDTO filtroDocenteDTO) {

		// Construcción de la consulta con StringBuilder
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(" SELECT COUNT(doc)");
		queryBuilder.append(" FROM DocenteEntity doc ");
		queryBuilder.append(" JOIN doc.persona per ");
		queryBuilder.append(" JOIN per.tipoIdentificacion ti ");
		queryBuilder.append(" WHERE 1=1");

		Map<String, Object> parametros = new HashMap<>();

		if (Objects.nonNull(filtroDocenteDTO.getNombre())) {
			queryBuilder.append(" AND UPPER(TRIM(BOTH ' ' FROM CONCAT( ");
			queryBuilder.append(" per.primerNombre,");
			queryBuilder.append(" CASE WHEN per.segundoNombre IS NOT NULL THEN CONCAT(' ', per.segundoNombre) ELSE '' END,");
			queryBuilder.append(" per.primerApellido,");
			queryBuilder.append(" CASE WHEN per.segundoApellido IS NOT NULL THEN CONCAT(' ', per.segundoApellido) ELSE '' END )))");
			queryBuilder.append(" LIKE UPPER(:nombre)");
			parametros.put("nombre", "%"+filtroDocenteDTO.getNombre().replaceAll("\\s+", " ").trim()+"%");
		}
		if (Objects.nonNull(filtroDocenteDTO.getNumeroIdentificacion())
				&& !filtroDocenteDTO.getNumeroIdentificacion().isEmpty()) {
			queryBuilder.append(" AND per.numeroIdentificacion LIKE :numeroIdentificacion");
			parametros.put("numeroIdentificacion",
					"%" + filtroDocenteDTO.getNumeroIdentificacion().replaceAll("\\s+", " ").trim() + "%");
		}
		if (Objects.nonNull(filtroDocenteDTO.getCodigo()) && !filtroDocenteDTO.getCodigo().isEmpty()) {
			queryBuilder.append(" AND doc.codigo LIKE :codigo");
			parametros.put("codigo", "%" + filtroDocenteDTO.getCodigo().replaceAll("\\s+", " ").trim() + "%");
		}
		if (Objects.nonNull(filtroDocenteDTO.getEstado())) {
			queryBuilder.append(" AND doc.estado = :estado");
			parametros.put("estado", filtroDocenteDTO.getEstado());
		}

		queryBuilder.append(" ORDER BY per.primerNombre asc");

		// Realiza la consulta para contar
		TypedQuery<Long> countQuery = entityManager.createQuery(queryBuilder.toString(), Long.class);

		// Establece parámetros en la consulta
		for (Map.Entry<String, Object> entry : parametros.entrySet()) {
			countQuery.setParameter(entry.getKey(), entry.getValue());
		}

		return countQuery.getSingleResult();
	}

	/** 
	 * @see co.edu.unicauca.sgph.docente.aplication.output.GestionarDocenteGatewayIntPort#consultarDocentePorIdCurso(java.lang.Long)
	 */
	@Override
	public List<Docente> consultarDocentePorIdCurso(Long idCurso) {
		List<DocenteEntity> docenteEntities = this.docenteRepositoryInt.consultarDocentePorIdCurso(idCurso);
		return this.modelMapper.map(docenteEntities, new TypeToken<List<Docente>>() {
		}.getType());
	}

	/** 
	 * @see co.edu.unicauca.sgph.docente.aplication.output.GestionarDocenteGatewayIntPort#existsDocenteByCodigo(java.lang.String, java.lang.Long)
	 */
	@Override
	public Boolean existsDocenteByCodigo(String codigo, Long idDocente) {
		DocenteEntity docenteEntity = this.docenteRepositoryInt.consultarDocentePorCodigo(codigo, idDocente);
		if (Objects.nonNull(docenteEntity)) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	/** 
	 * @see co.edu.unicauca.sgph.docente.aplication.output.GestionarDocenteGatewayIntPort#existeDocentePorIdPersona(java.lang.Long, java.lang.Long)
	 */
	@Override
	public Boolean existeDocentePorIdPersona(Long idPersona, Long idDocente) {
		DocenteEntity docenteEntity = this.docenteRepositoryInt.consultarUsuarioPorIdPersona(idPersona, idDocente);
		if (Objects.nonNull(docenteEntity)) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	@Override
	public List<DocenteLaborDTO> cargarLaborDocente(ReporteDocenteDTO archivoDocente) {
		try {
			return mockLeerExcelBase64(archivoDocente.getArchivoBase64());
		} catch (IOException e) {
			return null;
		}

	}

	@Override
	public Docente consultarDocentePorNumeroIdentificacion(String numeroIdentificacion) {
		DocenteEntity docenteEntity = this.docenteRepositoryInt.consultarDocentePorIdentificacion(numeroIdentificacion);
		if (Objects.isNull(docenteEntity)) {
			return null;
		}
		return this.modelMapper.map(docenteEntity, Docente.class);
	}
	private static boolean isXlsxFile(byte[] fileContent) {
		return fileContent[0] == 0x50 && fileContent[1] == 0x4B;
	}
	private static boolean isXlsFile(byte[] fileContent) {
		return fileContent[0] == (byte) 0xD0 && fileContent[1] == (byte) 0xCF;
	}
	public static List<DocenteLaborDTO> mockLeerExcelBase64(String base64Excel) throws IOException {
		byte[] decodedBytes = Base64.decodeBase64(base64Excel);
		ByteArrayInputStream inputStream = new ByteArrayInputStream(decodedBytes);
		Workbook workbook = null;
		// Determina el tipo de archivo
		if (isXlsxFile(decodedBytes)) {
			workbook = new XSSFWorkbook(inputStream);
		} else if (isXlsFile(decodedBytes)) {
			workbook = new HSSFWorkbook(inputStream);
		}
		if (workbook == null) {
			throw new IOException("El archivo no es un archivo Excel válido.");
		}
		Sheet sheet = workbook.getSheetAt(0);
		List<DocenteLaborDTO> docenteLaborDTOList = new ArrayList<>();

		for (Row row : sheet) {
			if (row.getRowNum() == 0) continue; // Skip header row
			DocenteLaborDTO docente = new DocenteLaborDTO();
			docente.setOidPeriodo((int) row.getCell(0).getNumericCellValue());
			docente.setPeriodo(getStringCellValue(row, 1));
			docente.setIdentificacion(String.valueOf(row.getCell(2).getNumericCellValue()));
			docente.setPrimerApellido(getStringCellValue(row, 3));
			docente.setSegundoApellido(getStringCellValue(row, 4));
			docente.setPrimerNombre(getStringCellValue(row, 5));
			docente.setSegundoNombre(getStringCellValue(row, 6));
			docente.setCorreo(getStringCellValue(row, 7));
			docente.setNombreMateria(getStringCellValue(row, 8));
			docente.setNombrePrograma(getStringCellValue(row, 9));
			int valorOid = getIntCellValue(row, 10);
			docente.setOid(String.valueOf(valorOid));
			Object cellValue = row.getCell(11);
			if (cellValue == null) {
				docente.setCodigo("");
			} else if (cellValue instanceof Number) {
				docente.setCodigo(String.valueOf(((Number) cellValue).intValue()));
			} else if (cellValue instanceof String) {
				docente.setCodigo((String) cellValue);
			} else {
				// En caso de otro tipo de dato, manejarlo según sea necesario
				docente.setCodigo(cellValue.toString());
			}
			docente.setTipoMateria(String.valueOf(getIntCellValue(row, 12)));
			Object cellValueGrupo = row.getCell(13);
			if (cellValueGrupo == null) {
				docente.setGrupo("");
			} else if (cellValueGrupo instanceof Number) {
				docente.setGrupo(String.valueOf(((Number) cellValue).intValue()));
			} else if (cellValueGrupo instanceof String) {
				docente.setGrupo((String) cellValue);
			} else {
				docente.setGrupo(cellValueGrupo.toString());
			}
			docente.setHorasTeoricas(String.valueOf(getIntCellValue(row, 14)));
			docenteLaborDTOList.add(docente);
			if (!validarGrupoYDocente(docente)) {
				throw new IOException("Información faltante");
			}
		}

		workbook.close();
		return docenteLaborDTOList;
	}
	private static String getStringCellValue(Row row, int cellIndex) {
		Cell cell = row.getCell(cellIndex);
		return cell != null ? cell.getStringCellValue() : "";
	}

	private static int getIntCellValue(Row row, int cellIndex) {
		Cell cell = row.getCell(cellIndex);
		return cell != null ? (int) cell.getNumericCellValue() : 0;
	}
	private static boolean validarGrupoYDocente(DocenteLaborDTO docenteLaborDTO) {
		// TODO   DADO que el excel compartido tiene casillas con grupos faltantes
		if (docenteLaborDTO.getGrupo() == null || docenteLaborDTO.getIdentificacion() == null
		|| docenteLaborDTO.getPrimerNombre() == null || docenteLaborDTO.getPrimerApellido() == null) {
			return false;
		}
		return true;
	}
}