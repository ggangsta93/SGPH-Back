package co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.gateway;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import co.edu.unicauca.sgph.espaciofisico.aplication.output.GestionarAgrupadorEspacioFisicoGatewayIntPort;
import co.edu.unicauca.sgph.espaciofisico.domain.model.AgrupadorEspacioFisico;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.AgrupadorEspacioFisicoEntity;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.repository.AgrupadorEspacioFisicoRepositoryInt;

@Service
public class GestionarAgrupadorEspacioFisicoGatewayImplAdapter
		implements GestionarAgrupadorEspacioFisicoGatewayIntPort {

	@PersistenceContext
	private EntityManager entityManager;

	private AgrupadorEspacioFisicoRepositoryInt agrupadorEspacioFisicoRepositoryInt;
	private ModelMapper modelMapper;

	public GestionarAgrupadorEspacioFisicoGatewayImplAdapter(
			AgrupadorEspacioFisicoRepositoryInt agrupadorEspacioFisicoRepositoryInt, ModelMapper modelMapper) {
		this.agrupadorEspacioFisicoRepositoryInt = agrupadorEspacioFisicoRepositoryInt;
		this.modelMapper = modelMapper;
	}

	/**
	 * @see co.edu.unicauca.sgph.espaciofisico.aplication.output.GestionarAgrupadorEspacioFisicoGatewayIntPort#consultarAgrupadoresEspaciosFisicosPorIdAgrupadorEspacioFisico(java.util.List)
	 */
	@Override
	public List<AgrupadorEspacioFisico> consultarAgrupadoresEspaciosFisicosPorIdAgrupadorEspacioFisico(
			List<Long> idAgrupadorEspacioFisico) {
		List<AgrupadorEspacioFisicoEntity> lstAgrupadorEspacioFisicoEntity = this.agrupadorEspacioFisicoRepositoryInt
				.consultarAgrupadoresEspaciosFisicosPorIdAgrupadorEspacioFisico(idAgrupadorEspacioFisico);
		return this.modelMapper.map(lstAgrupadorEspacioFisicoEntity, new TypeToken<List<AgrupadorEspacioFisico>>() {
		}.getType());
	}

	/**
	 * @see co.edu.unicauca.sgph.espaciofisico.aplication.output.GestionarAgrupadorEspacioFisicoGatewayIntPort#consultarAgrupadoresEspaciosFisicosPorIdFacultad(java.util.List)
	 */
	@Override
	public List<AgrupadorEspacioFisico> consultarAgrupadoresEspaciosFisicosPorIdFacultad(List<Long> idFacultad) {
		List<AgrupadorEspacioFisicoEntity> lstAgrupadorEspacioFisicoEntity = this.agrupadorEspacioFisicoRepositoryInt
				.consultarAgrupadoresEspaciosFisicosPorIdFacultad(idFacultad);
		return this.modelMapper.map(lstAgrupadorEspacioFisicoEntity, new TypeToken<List<AgrupadorEspacioFisico>>() {
		}.getType());
	}

	/** 
	 * @see co.edu.unicauca.sgph.espaciofisico.aplication.output.GestionarAgrupadorEspacioFisicoGatewayIntPort#consultarAgrupadoresEspaciosFisicosAsociadosACursoPorIdCurso(java.lang.Long)
	 */
	@Override
	public List<AgrupadorEspacioFisico> consultarAgrupadoresEspaciosFisicosAsociadosACursoPorIdCurso(Long idCurso) {
		List<AgrupadorEspacioFisicoEntity> lstAgrupadorEspacioFisicoEntity = this.agrupadorEspacioFisicoRepositoryInt
				.consultarAgrupadoresEspaciosFisicosAsociadosACursoPorIdCurso(idCurso);
		return this.modelMapper.map(lstAgrupadorEspacioFisicoEntity, new TypeToken<List<AgrupadorEspacioFisico>>() {
		}.getType());
	}

}