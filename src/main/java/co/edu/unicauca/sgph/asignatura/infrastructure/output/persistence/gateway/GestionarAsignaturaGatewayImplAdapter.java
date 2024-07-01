package co.edu.unicauca.sgph.asignatura.infrastructure.output.persistence.gateway;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import co.edu.unicauca.sgph.asignatura.infrastructure.input.DTORequest.AsignaturaInDTO;
import co.edu.unicauca.sgph.asignatura.infrastructure.input.DTORequest.FiltroAsignaturaInDTO;
import co.edu.unicauca.sgph.asignatura.infrastructure.input.DTOResponse.AgrupadorEspacioFisicoDTO;
import co.edu.unicauca.sgph.asignatura.infrastructure.input.DTOResponse.AsignaturaOutDTO;
import co.edu.unicauca.sgph.asignatura.infrastructure.input.mapper.AsignaturaRestMapper;
import co.edu.unicauca.sgph.asignatura.infrastructure.output.persistence.entity.EstadoAsignaturaEnum;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.MensajeOutDTO;
import co.edu.unicauca.sgph.facultad.infrastructure.output.persistence.entity.FacultadEntity;
import co.edu.unicauca.sgph.programa.infrastructure.output.persistence.entity.ProgramaEntity;
import co.edu.unicauca.sgph.programa.infrastructure.output.persistence.repository.ProgramaRepositoryInt;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import co.edu.unicauca.sgph.agrupador.infrastructure.output.persistence.entity.AgrupadorEspacioFisicoEntity;
import co.edu.unicauca.sgph.asignatura.aplication.output.GestionarAsignaturaGatewayIntPort;
import co.edu.unicauca.sgph.asignatura.domain.model.Asignatura;
import co.edu.unicauca.sgph.asignatura.infrastructure.output.persistence.entity.AsignaturaEntity;
import co.edu.unicauca.sgph.asignatura.infrastructure.output.persistence.repository.AsignaturaRepositoryInt;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Service
public class GestionarAsignaturaGatewayImplAdapter implements GestionarAsignaturaGatewayIntPort {

	private final AsignaturaRepositoryInt asignaturaRepositoryInt;
	private final ModelMapper asignaturaMapper;
	private final ProgramaRepositoryInt programaRepositoryInt;
	private AsignaturaRestMapper asignaturaRestMapper;

	@PersistenceContext
	EntityManager em;

	public GestionarAsignaturaGatewayImplAdapter(AsignaturaRepositoryInt asignaturaRepositoryInt,
												 @Qualifier("asignaturaMapper") ModelMapper asignaturaMapper, ProgramaRepositoryInt programaRepositoryInt, AsignaturaRestMapper asignaturaRestMapper) {
		this.asignaturaRepositoryInt = asignaturaRepositoryInt;
		this.asignaturaMapper = asignaturaMapper;
		this.programaRepositoryInt = programaRepositoryInt;
		this.asignaturaRestMapper = asignaturaRestMapper;
	}

	/**
	 * @see co.edu.unicauca.sgph.asignatura.aplication.output.GestionarAsignaturaGatewayIntPort#guardarAsignatura(co.edu.unicauca.sgph.asignatura.domain.model.Asignatura)
	 */
	@Override
	public Asignatura guardarAsignatura(Asignatura asignatura) {
		Optional<AsignaturaEntity> entidad=Optional.empty();
		AsignaturaEntity entidadGuardar=null;
		if(Objects.nonNull(asignatura.getIdAsignatura())) {
			entidad = this.asignaturaRepositoryInt.findById(asignatura.getIdAsignatura());
			entidadGuardar = entidad.get();
		}
		if (entidad.isPresent()) {
			AsignaturaEntity entidadGuardarMaper = this.asignaturaMapper.map(asignatura, AsignaturaEntity.class);
			entidadGuardarMaper.setIdAsignatura(entidadGuardar.getIdAsignatura());
			entidadGuardarMaper.setEstado(entidadGuardar.getEstado());
			entidadGuardar = entidadGuardarMaper;
		} else {
			entidadGuardar = this.asignaturaMapper.map(asignatura, AsignaturaEntity.class);
			entidadGuardar.setEstado(EstadoAsignaturaEnum.ACTIVO);
		}
		return this.asignaturaMapper.map(
				this.asignaturaRepositoryInt.save(
						entidadGuardar
				),
				Asignatura.class);

	}

	/**
	 * @see co.edu.unicauca.sgph.asignatura.aplication.output.GestionarAsignaturaGatewayIntPort#consultarAsignaturasPorIdPrograma(java.lang.Long)
	 */
	@Override
	public List<Asignatura> consultarAsignaturasPorIdPrograma(Long idPrograma) {
		List<AsignaturaEntity> lstAsignaturaEntity = this.asignaturaRepositoryInt
				.consultarAsignaturasPorIdPrograma(idPrograma);
		if (lstAsignaturaEntity.isEmpty()) {
			return new ArrayList<>();
		} else {
			return this.asignaturaMapper.map(lstAsignaturaEntity, new TypeToken<List<Asignatura>>() {
			}.getType());
		}
	}

	@Override
	public AsignaturaOutDTO obtenerAsignaturaPorId(Long idAsignatura) {
		Optional<AsignaturaEntity> optionalAsignatura = this.asignaturaRepositoryInt.findById(idAsignatura);
		return optionalAsignatura.map(asignatura -> this.mapearDTO(asignatura)).orElse(null);
	}

	@Override
	public Page<AsignaturaOutDTO> filtrarAsignaturas(FiltroAsignaturaInDTO filtro) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		Long totalCount = this.contarAsignaturas(filtro, criteriaBuilder);
		CriteriaQuery<AsignaturaEntity> criteriaQuery = criteriaBuilder.createQuery(AsignaturaEntity.class);
		Root<AsignaturaEntity> root = criteriaQuery.from(AsignaturaEntity.class);
		Join<AsignaturaEntity, ProgramaEntity> programaJoin = root.join("programa");
		Join<ProgramaEntity, FacultadEntity> facultadJoin = programaJoin.join("facultad");
		Predicate predicate = criteriaBuilder.conjunction();
		if (filtro.getIdFacultades() != null) {
			List<Expression<Boolean>> expressions = new ArrayList<>();
			for (Long idFacultad : filtro.getIdFacultades()) {
				expressions.add(criteriaBuilder.equal(facultadJoin.get("idFacultad"), idFacultad));
			}
			predicate = criteriaBuilder.or(expressions.toArray(new Predicate[0]));
		}
		if (filtro.getIdProgramas() != null) {
			Predicate predicateProgramas = criteriaBuilder.conjunction();
			List<Expression<Boolean>> expressions = new ArrayList<>();
			for (Long idPrograma : filtro.getIdProgramas()) {
				expressions.add(criteriaBuilder.equal(programaJoin.get("idPrograma"), idPrograma));
			}
			predicateProgramas = criteriaBuilder.or(expressions.toArray(new Predicate[0]));
			predicate = criteriaBuilder.and(predicateProgramas, predicate);
		}
		if (filtro.getEstado() != null) {
			predicate = criteriaBuilder.and(criteriaBuilder.equal(root.get("estado"), filtro.getEstado()), predicate);
		}
		if (filtro.getSemestre() != null) {
			predicate = criteriaBuilder.and(criteriaBuilder.equal(root.get("semestre"), filtro.getSemestre()), predicate);
		}
		criteriaQuery.where(predicate);

		List<AsignaturaEntity> resultList = em.createQuery(criteriaQuery)
				.setFirstResult(filtro.getPageNumber() * filtro.getPageSize())
				.setMaxResults(filtro.getPageSize())
				.getResultList();
		Pageable pageable = PageRequest.of(filtro.getPageNumber(), filtro.getPageSize());

		Page<AsignaturaEntity> resultado = new PageImpl<>(resultList, pageable, totalCount);
		return resultado.map(this::mapearDTO);
	}

	@Override
	public Asignatura inactivarAsignaturaPorId(Long idAsignatura) {
		Optional<AsignaturaEntity> asignatura = this.asignaturaRepositoryInt.findById(idAsignatura);
		if (asignatura.isPresent()) {
			AsignaturaEntity asignaturaEntity = asignatura.get();
			if (asignaturaEntity.getEstado() != null && asignaturaEntity.getEstado().equals(EstadoAsignaturaEnum.ACTIVO)) {
				asignaturaEntity.setEstado(EstadoAsignaturaEnum.INACTIVO);
			} else {
				asignaturaEntity.setEstado(EstadoAsignaturaEnum.ACTIVO);
			}
			return this.asignaturaMapper.map(this.asignaturaRepositoryInt.save(asignaturaEntity), Asignatura.class);
		}
		return null;
	}

	@Override
	public MensajeOutDTO cargaMasivaAsignaturas(AsignaturaInDTO dto) {
		String base64Excel = dto.getBase64();
		byte[] decodedBytes = Base64.getDecoder().decode(base64Excel);
		List<AsignaturaInDTO> asignaturas = new ArrayList<>();
		try {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(decodedBytes);
			Workbook workbook = new XSSFWorkbook(inputStream);
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();
			rowIterator.next();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				if (row.getCell(0) == null) {
					break;
				}
				String programa = row.getCell(0).getStringCellValue();
				int semestre = (int) row.getCell(1).getNumericCellValue();
				String pensum = getCellValueAsString(row.getCell(2));
				String oid = getCellValueAsString(row.getCell(3));
				String codigoAsignatura = getCellValueAsString(row.getCell(4));
				String nombre = getCellValueAsString(row.getCell(5));
				int horasSemana = (int) row.getCell(6).getNumericCellValue();
				String estado = getCellValueAsString(row.getCell(7));
				int aplicaEspSec = (int) row.getCell(8).getNumericCellValue();
				Optional<ProgramaEntity> programaEntidad = this.programaRepositoryInt.findByNombre(programa);
				if (programaEntidad.isPresent()) {
					AsignaturaInDTO asignatura = new AsignaturaInDTO(nombre, codigoAsignatura, oid, semestre, pensum, horasSemana, programaEntidad.get().getIdPrograma(), estado, aplicaEspSec);
					asignatura.setAplicaEspacioSecundario(asignatura.getAplicaEspSec() == 1 ? true : false);
					asignaturas.add(asignatura);
				} else {
					MensajeOutDTO mensaje = new MensajeOutDTO();
					mensaje.setError(Boolean.TRUE);
					mensaje.setDescripcion("No existe el programa académico " + programa);
					return mensaje;
				}
			}
			workbook.close();
			inputStream.close();
		} catch (IOException e) {
			MensajeOutDTO mensaje = new MensajeOutDTO();
			mensaje.setError(Boolean.TRUE);
			mensaje.setDescripcion("Error al leer el archivo");
		}
		MensajeOutDTO mensaje = this.validarAsignaturas(asignaturas);
		if (mensaje.getError()) {
			return mensaje;
		}
		// Guardar
		for (AsignaturaInDTO asignatura : asignaturas) {
			this.guardarAsignatura(this.asignaturaRestMapper.toAsignatura(asignatura));
		}
		return mensaje;
	}

	private static String getCellValueAsString(Cell cell) {
		if (cell == null) {
			return "";
		}

		switch (cell.getCellType()) {
			case STRING:
				return cell.getStringCellValue();
			case NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					return cell.getDateCellValue().toString();
				} else {
					return String.valueOf((int) cell.getNumericCellValue());
				}
			case BOOLEAN:
				return String.valueOf(cell.getBooleanCellValue());
			case FORMULA:
				return cell.getCellFormula();
			default:
				return "";
		}
	}

	private Long contarAsignaturas(FiltroAsignaturaInDTO filtro, CriteriaBuilder criteriaBuilder) {
		// Contar el total de resultados
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		Root<AsignaturaEntity> root2 = countQuery.from(AsignaturaEntity.class);
		Join<AsignaturaEntity, ProgramaEntity> programaJoin2 = root2.join("programa");
		Join<ProgramaEntity, FacultadEntity> facultadJoin = programaJoin2.join("facultad");
		countQuery.select(criteriaBuilder.countDistinct(root2));

		Predicate predicate2 = criteriaBuilder.conjunction();
		if (filtro.getIdFacultades() != null) {
			List<Expression<Boolean>> expressions = new ArrayList<>();
			Predicate predicateFacultades = criteriaBuilder.conjunction();
			for (Long idFacultad : filtro.getIdFacultades()) {
				expressions.add(criteriaBuilder.equal(facultadJoin.get("idFacultad"), idFacultad));
			}
			predicateFacultades = criteriaBuilder.or(expressions.toArray(new Predicate[0]));
			predicate2 = criteriaBuilder.and(predicateFacultades, predicate2);
		}
		if (filtro.getSemestre() != null) {
			predicate2 = criteriaBuilder.and(criteriaBuilder.equal(root2.get("semestre"), filtro.getSemestre()), predicate2);
		}
		if (filtro.getEstado() != null) {
			predicate2 = criteriaBuilder.and(criteriaBuilder.equal(root2.get("estado"), filtro.getSemestre()), predicate2);
		}
		if (filtro.getIdProgramas() != null) {
			List<Expression<Boolean>> expressions = new ArrayList<>();
			Predicate predicatePrograma = criteriaBuilder.conjunction();
			for (Long idPrograma : filtro.getIdProgramas()) {
				expressions.add(criteriaBuilder.equal(programaJoin2.get("idPrograma"), idPrograma));
			}
			predicatePrograma = criteriaBuilder.or(expressions.toArray(new Predicate[0]));
			predicate2 = criteriaBuilder.and(predicatePrograma, predicate2);
		}
		if (filtro.getSemestre() != null) {
			predicate2 = criteriaBuilder.and(criteriaBuilder.equal(root2.get("semestre"), filtro.getSemestre()), predicate2);
		}
		countQuery.where(predicate2);
		return em.createQuery(countQuery).getSingleResult();
	}

	private AsignaturaOutDTO mapearDTO(AsignaturaEntity entidad) {
		AsignaturaOutDTO asignaturaOutDTO = new AsignaturaOutDTO();
		asignaturaOutDTO.setIdAsignatura(entidad.getIdAsignatura());
		asignaturaOutDTO.setCodigoAsignatura(entidad.getCodigoAsignatura());
		asignaturaOutDTO.setNombre(entidad.getNombre());
		asignaturaOutDTO.setOID(entidad.getOid());
		asignaturaOutDTO.setPensum(entidad.getPensum());
		asignaturaOutDTO.setHorasSemana(entidad.getHorasSemana());
		asignaturaOutDTO.setIdPrograma(entidad.getPrograma().getIdPrograma());
		asignaturaOutDTO.setNombrePrograma(entidad.getPrograma().getNombre());
		asignaturaOutDTO.setNombreFacultad(entidad.getPrograma().getFacultad().getNombre());
		asignaturaOutDTO.setSemestre(entidad.getSemestre());
		asignaturaOutDTO.setAgrupadores(entidad.getAgrupadores().stream().map(this::mapAgrupador).collect(Collectors.toList()));
		asignaturaOutDTO.setIdFacultad(entidad.getPrograma().getFacultad().getIdFacultad());
		if (entidad.getEstado() != null) {
			asignaturaOutDTO.setEstado(entidad.getEstado().getDescripcionEstado());
		}
		return asignaturaOutDTO;
	}

	private AgrupadorEspacioFisicoDTO mapAgrupador(AgrupadorEspacioFisicoEntity entidad) {
		AgrupadorEspacioFisicoDTO dto = new AgrupadorEspacioFisicoDTO();
		dto.setIdFacultad(entidad.getFacultad().getIdFacultad());
		dto.setNombre(entidad.getNombre());
		dto.setNombreFacultad(entidad.getFacultad().getNombre());
		dto.setIdAgrupadorEspacioFisico(entidad.getIdAgrupadorEspacioFisico());
		return dto;
	}

	public MensajeOutDTO validarAsignaturas(List<AsignaturaInDTO> asignaturas) {
		Set<String> codigosAsignatura = new HashSet<>();
		Set<String> OIDs = new HashSet<>();
		MensajeOutDTO mensaje = new MensajeOutDTO();
		mensaje.setError(Boolean.FALSE);
		for (AsignaturaInDTO asignatura : asignaturas) {
			// Validar que ningún atributo sea vacío
			if (asignatura.getNombre() == null || asignatura.getNombre().isEmpty() ||
					asignatura.getCodigoAsignatura() == null || asignatura.getCodigoAsignatura().isEmpty() ||
					asignatura.getOID() == null || asignatura.getOID().isEmpty() ||
					asignatura.getSemestre() == null ||
					asignatura.getPensum() == null || asignatura.getPensum().isEmpty() ||
					asignatura.getHorasSemana() == null ||
					asignatura.getIdPrograma() == null
					|| asignatura.getEstado() == null || asignatura.getEstado().isEmpty()
					|| asignatura.getAplicaEspSec() == null) {
				mensaje.setDescripcion("Todos los campos deben estar llenos y no ser nulos");
				mensaje.setError(Boolean.TRUE);
				return mensaje;
			}
			if (!asignatura.getEstado().equals("ACTIVO")
					&& !asignatura.getEstado().equals("INACTIVO") ) {
				mensaje.setDescripcion("El estado permitido debe ser ACTIVO O INACTIVO");
				mensaje.setError(Boolean.TRUE);
				return mensaje;
			}
			Optional<AsignaturaEntity> asignaturaExistenteCodigo = this.asignaturaRepositoryInt.findByCodigoAsignatura(asignatura.getCodigoAsignatura());

				if (asignaturaExistenteCodigo.isPresent() || !codigosAsignatura.add(asignatura.getCodigoAsignatura())) {
					mensaje.setDescripcion("El código de asignatura " + asignatura.getCodigoAsignatura() + " está duplicado");
					mensaje.setError(Boolean.TRUE);
					return mensaje;
				}
				Optional<AsignaturaEntity> asignaturaExistente = this.asignaturaRepositoryInt.findByOid(asignatura.getOID());
			if (asignaturaExistente.isPresent() || !OIDs.add(asignatura.getOID())) {
				mensaje.setDescripcion("El OID " + asignatura.getOID() + " está duplicado");
				mensaje.setError(Boolean.TRUE);
				return mensaje;
			}
		}
		mensaje.setDescripcion("Asignaturas cargadas correctamente");
		return mensaje;
	}


}