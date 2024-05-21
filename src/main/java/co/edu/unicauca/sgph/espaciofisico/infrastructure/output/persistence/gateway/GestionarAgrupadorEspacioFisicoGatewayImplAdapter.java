package co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.gateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import co.edu.unicauca.sgph.asignatura.infrastructure.output.persistence.entity.AsignaturaEntity;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTORequest.AsignacionEspacioFisicoDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTORequest.FiltroGrupoDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.AgrupadorEspacioFisicoOutDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.MensajeOutDTO;
import co.edu.unicauca.sgph.facultad.domain.model.Facultad;
import co.edu.unicauca.sgph.facultad.infrastructure.output.persistence.entity.FacultadEntity;
import co.edu.unicauca.sgph.programa.infrastructure.output.persistence.entity.ProgramaEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import co.edu.unicauca.sgph.espaciofisico.aplication.output.GestionarAgrupadorEspacioFisicoGatewayIntPort;
import co.edu.unicauca.sgph.espaciofisico.domain.model.AgrupadorEspacioFisico;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.AgrupadorEspacioFisicoEntity;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.repository.AgrupadorEspacioFisicoRepositoryInt;

@Service
public class GestionarAgrupadorEspacioFisicoGatewayImplAdapter
		implements GestionarAgrupadorEspacioFisicoGatewayIntPort {

	@PersistenceContext
	private EntityManager em;

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

	@Override
	public Page<AgrupadorEspacioFisicoOutDTO> filtrarGrupos(FiltroGrupoDTO filtro) {
		Pageable pageable = PageRequest.of(filtro.getPageNumber(), filtro.getPageSize());
		Page<AgrupadorEspacioFisicoEntity> resultado = this.agrupadorEspacioFisicoRepositoryInt.findByFacultadIdFacultadIn(filtro.getListaIdFacultades(), pageable);
		return resultado.map(this::mapGrupo);
	}

	@Override
	public AgrupadorEspacioFisicoOutDTO guardarGrupo(AgrupadorEspacioFisicoOutDTO agrupador) {
		AgrupadorEspacioFisicoEntity entidad = this.agrupadorEspacioFisicoRepositoryInt.save(this.mapGrupoEntidad(agrupador));
		return this.mapGrupo(entidad);
	}
	private AgrupadorEspacioFisicoEntity mapGrupoEntidad(AgrupadorEspacioFisicoOutDTO dto) {
		AgrupadorEspacioFisicoEntity entidad = new AgrupadorEspacioFisicoEntity();
		if (dto.getIdAgrupadorEspacioFisico() != null) {
			entidad.setIdAgrupadorEspacioFisico(dto.getIdAgrupadorEspacioFisico());
		}
		FacultadEntity facultad = new FacultadEntity();
		facultad.setIdFacultad(dto.getIdFacultad());
		entidad.setFacultad(facultad);
		entidad.setNombre(dto.getNombre());
		entidad.setObservacion(dto.getObservacion());
		return entidad;
	}
	private AgrupadorEspacioFisicoOutDTO mapGrupo(AgrupadorEspacioFisicoEntity entidad) {
		AgrupadorEspacioFisicoOutDTO dto = new AgrupadorEspacioFisicoOutDTO();
		dto.setIdAgrupadorEspacioFisico(entidad.getIdAgrupadorEspacioFisico());
		dto.setNombre(entidad.getNombre());
		dto.setIdFacultad(entidad.getFacultad().getIdFacultad());
		dto.setNombreFacultad(entidad.getFacultad().getNombre());
		dto.setObservacion(entidad.getObservacion());
		return dto;
	}
}