package co.edu.unicauca.sgph.gestionplanificacion.labordocencia.infrastructure.input.controller;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.sgph.common.domain.model.CommonEJB;
import co.edu.unicauca.sgph.gestionplanificacion.labordocencia.aplication.input.GestionarLaborDocenciaCUIntPort;
import co.edu.unicauca.sgph.gestionplanificacion.labordocencia.infrastructure.input.mapper.LaborDocenciaRestMapper;
import co.edu.unicauca.sgph.gestionplanificacion.labordocencia.infrastructure.output.persistence.entity.LaborDocenciaEntity;

@RestController
@RequestMapping("/LaborDocencia")
@Service
public class LaborDocenciaController extends CommonEJB{

	private GestionarLaborDocenciaCUIntPort gestionarLaborDocenciaCUIntPort;
	private LaborDocenciaRestMapper laborDocenciaRestMapper;

	@PersistenceContext
	private EntityManager entityManager;

	public LaborDocenciaController(GestionarLaborDocenciaCUIntPort gestionarLaborDocenciaCUIntPort,
			LaborDocenciaRestMapper laborDocenciaRestMapper) {
		this.gestionarLaborDocenciaCUIntPort = gestionarLaborDocenciaCUIntPort;
		this.laborDocenciaRestMapper = laborDocenciaRestMapper;
	}

	@PostMapping("/cargarLaboresDocente")
	@Transactional
	public void cargarLaboresDocente(LaborDocenciaEntity laborDocenciaEntity) {
		entityManager.persist(laborDocenciaEntity);
	}

}