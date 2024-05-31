package co.edu.unicauca.sgph.asignatura.infrastructure.output.persistence.gateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import co.edu.unicauca.sgph.asignatura.infrastructure.input.DTORequest.FiltroAsignaturaInDTO;
import co.edu.unicauca.sgph.asignatura.infrastructure.input.DTOResponse.AgrupadorEspacioFisicoDTO;
import co.edu.unicauca.sgph.asignatura.infrastructure.input.DTOResponse.AsignaturaOutDTO;
import co.edu.unicauca.sgph.asignatura.infrastructure.output.persistence.entity.EstadoAsignaturaEnum;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.AgrupadorEspacioFisicoEntity;
import co.edu.unicauca.sgph.facultad.infrastructure.output.persistence.entity.FacultadEntity;
import co.edu.unicauca.sgph.programa.domain.model.Programa;
import co.edu.unicauca.sgph.programa.infrastructure.output.persistence.entity.ProgramaEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

	@PersistenceContext
	EntityManager em;

	public GestionarAsignaturaGatewayImplAdapter(AsignaturaRepositoryInt asignaturaRepositoryInt,
												 @Qualifier("asignaturaMapper") ModelMapper asignaturaMapper) {
		this.asignaturaRepositoryInt = asignaturaRepositoryInt;
		this.asignaturaMapper = asignaturaMapper;
	}

	/**
	 * @see co.edu.unicauca.sgph.asignatura.aplication.output.GestionarAsignaturaGatewayIntPort#guardarAsignatura(co.edu.unicauca.sgph.asignatura.domain.model.Asignatura)
	 */
	@Override
	public Asignatura guardarAsignatura(Asignatura asignatura) {
		return this.asignaturaMapper.map(
				this.asignaturaRepositoryInt.save(this.asignaturaMapper.map(asignatura, AsignaturaEntity.class)),
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
}