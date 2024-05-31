package co.edu.unicauca.sgph.periodoacademico.infrastructure.output.persistence.gateway;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import co.edu.unicauca.sgph.periodoacademico.aplication.output.GestionarPeriodoAcademicoGatewayIntPort;
import co.edu.unicauca.sgph.periodoacademico.domain.model.PeriodoAcademico;
import co.edu.unicauca.sgph.periodoacademico.infrastructure.input.DTORequest.FiltroPeriodoAcademicoDTO;
import co.edu.unicauca.sgph.periodoacademico.infrastructure.input.DTOResponse.PeriodoAcademicoOutDTO;
import co.edu.unicauca.sgph.periodoacademico.infrastructure.output.persistence.entity.PeriodoAcademicoEntity;
import co.edu.unicauca.sgph.periodoacademico.infrastructure.output.persistence.repository.PeriodoAcademicoRepositoryInt;

@Service
public class GestionarPeriodoAcademicoGatewayImplAdapter implements GestionarPeriodoAcademicoGatewayIntPort {

	@PersistenceContext
	private EntityManager entityManager;

	private PeriodoAcademicoRepositoryInt periodoAcademicoRepositoryInt;
	private ModelMapper modelMapper;

	public GestionarPeriodoAcademicoGatewayImplAdapter(PeriodoAcademicoRepositoryInt periodoAcademicoRepositoryInt,
			ModelMapper modelMapper) {
		this.periodoAcademicoRepositoryInt = periodoAcademicoRepositoryInt;
		this.modelMapper = modelMapper;
	}

	/**
	 * @see co.edu.unicauca.sgph.periodoacademico.aplication.output.GestionarPeriodoAcademicoGatewayIntPort#guardarPeriodoAcademico(co.edu.unicauca.sgph.periodoacademico.domain.model.PeriodoAcademico)
	 */
	@Override
	public PeriodoAcademico guardarPeriodoAcademico(PeriodoAcademico periodoAcademico) {
		return this.modelMapper.map(this.periodoAcademicoRepositoryInt
				.save(this.modelMapper.map(periodoAcademico, PeriodoAcademicoEntity.class)), PeriodoAcademico.class);
	}

	/**
	 * @see co.edu.unicauca.sgph.periodoacademico.aplication.output.GestionarPeriodoAcademicoGatewayIntPort#actualizarEstadoPeriodoAcademicoAutomaticamente(java.util.Date)
	 */
	@Override
	@Transactional
	public void actualizarEstadoPeriodoAcademicoAutomaticamente(Date fechaActual) {
		this.periodoAcademicoRepositoryInt.actualizarEstadoSiFechaActualNoEnRango(fechaActual);
	}

	/**
	 * @see co.edu.unicauca.sgph.periodoacademico.aplication.output.GestionarPeriodoAcademicoGatewayIntPort#consultarPeriodoAcademicoVigente()
	 */
	@Override
	public PeriodoAcademico consultarPeriodoAcademicoVigente() {
		try {
			return this.modelMapper.map(this.periodoAcademicoRepositoryInt.consultarPeriodoAcademicoVigente(),
					PeriodoAcademico.class);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @see co.edu.unicauca.sgph.periodoacademico.aplication.output.GestionarPeriodoAcademicoGatewayIntPort#existsByAnioAndPeriodo(java.lang.Long,
	 *      java.lang.Long)
	 */
	@Override
	public PeriodoAcademico existsByAnioAndPeriodo(Long anio, Long periodo) {
		PeriodoAcademicoEntity periodoAcademicoEntity = this.periodoAcademicoRepositoryInt.existsByAnioAndPeriodo(anio,
				periodo);
		if (Objects.isNull(periodoAcademicoEntity)) {
			return null;
		}
		return this.modelMapper.map(periodoAcademicoEntity, PeriodoAcademico.class);
	}

	/**
	 * @see co.edu.unicauca.sgph.periodoacademico.aplication.output.GestionarPeriodoAcademicoGatewayIntPort#consultarPeriodosAcademicos(co.edu.unicauca.sgph.periodoacademico.infrastructure.input.DTORequest.FiltroPeriodoAcademicoDTO)
	 */
	@Override
	public Page<PeriodoAcademicoOutDTO> consultarPeriodosAcademicos(
			FiltroPeriodoAcademicoDTO filtroPeriodoAcademicoDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<PeriodoAcademicoEntity> criteriaQuery = criteriaBuilder.createQuery(PeriodoAcademicoEntity.class);
		Root<PeriodoAcademicoEntity> root = criteriaQuery.from(PeriodoAcademicoEntity.class);

		criteriaQuery.select(root);

		criteriaQuery.orderBy(
                criteriaBuilder.desc(root.get("fechaInicioPeriodo")),
                criteriaBuilder.desc(root.get("fechaFinPeriodo"))
        );
		
		TypedQuery<PeriodoAcademicoEntity> query = entityManager.createQuery(criteriaQuery);

		// Obtener datos de paginación
		int pagina = filtroPeriodoAcademicoDTO.getPagina() != null ? filtroPeriodoAcademicoDTO.getPagina() : 0;
		int registrosPorPagina = filtroPeriodoAcademicoDTO.getRegistrosPorPagina() != null
				? filtroPeriodoAcademicoDTO.getRegistrosPorPagina()
				: 10;

		// Aplicar paginación
		query.setFirstResult(pagina * registrosPorPagina);
		query.setMaxResults(registrosPorPagina);

		// Ejecutar consulta
		List<PeriodoAcademicoEntity> resultList = query.getResultList();

		// Contar total de registros
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		countQuery.select(criteriaBuilder.count(countQuery.from(PeriodoAcademicoEntity.class)));
		Long count = entityManager.createQuery(countQuery).getSingleResult();

		// Convertir entidades a DTOs
		List<PeriodoAcademicoOutDTO> dtoList = resultList.stream().map(entity -> {
			PeriodoAcademicoOutDTO dto = new PeriodoAcademicoOutDTO();
			dto.setIdPeriodoAcademico(entity.getIdPeriodoAcademico());
			dto.setAnio(entity.getAnio());
			dto.setPeriodo(entity.getPeriodo());
			dto.setFechaInicioPeriodo(entity.getFechaInicioPeriodo());
			dto.setFechaFinPeriodo(entity.getFechaFinPeriodo());
			dto.setEstado(entity.getEstado());
			return dto;
		}).collect(Collectors.toList());

		Pageable pageable = PageRequest.of(pagina, registrosPorPagina);
		return new PageImpl<>(dtoList, pageable, count);
	}
}