package co.edu.unicauca.sgph.periodoacademico.infrastructure.output.persistence.gateway;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import co.edu.unicauca.sgph.periodoacademico.aplication.output.GestionarPeriodoAcademicoGatewayIntPort;
import co.edu.unicauca.sgph.periodoacademico.domain.model.PeriodoAcademico;
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
		return this.modelMapper.map(this.periodoAcademicoRepositoryInt.consultarPeriodoAcademicoVigente(),
				PeriodoAcademico.class);
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
}