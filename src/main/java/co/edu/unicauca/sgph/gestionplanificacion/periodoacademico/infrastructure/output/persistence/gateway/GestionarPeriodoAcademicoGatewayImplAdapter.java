package co.edu.unicauca.sgph.gestionplanificacion.periodoacademico.infrastructure.output.persistence.gateway;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import co.edu.unicauca.sgph.gestionplanificacion.periodoacademico.aplication.output.GestionarPeriodoAcademicoGatewayIntPort;
import co.edu.unicauca.sgph.gestionplanificacion.periodoacademico.domain.model.PeriodoAcademico;
import co.edu.unicauca.sgph.gestionplanificacion.periodoacademico.infrastructure.output.persistence.entity.PeriodoAcademicoEntity;
import co.edu.unicauca.sgph.gestionplanificacion.periodoacademico.infrastructure.output.persistence.repository.PeriodoAcademicoRepositoryInt;

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
	 * @see co.edu.unicauca.sgph.gestionplanificacion.periodoacademico.aplication.output.GestionarPeriodoAcademicoGatewayIntPort#guardarPeriodoAcademico(co.edu.unicauca.sgph.gestionplanificacion.periodoacademico.domain.model.PeriodoAcademico)
	 */
	@Override
	public PeriodoAcademico guardarPeriodoAcademico(PeriodoAcademico periodoAcademico) {
		return this.modelMapper.map(this.periodoAcademicoRepositoryInt
				.save(this.modelMapper.map(periodoAcademico, PeriodoAcademicoEntity.class)), PeriodoAcademico.class);
	}
}