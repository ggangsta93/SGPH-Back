package co.edu.unicauca.sgph.agrupador.infrastructure.output.persistence.gateway;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import co.edu.unicauca.sgph.agrupador.aplication.output.GestionarAgrupadorEspacioFisicoGatewayIntPort;
import co.edu.unicauca.sgph.agrupador.domain.model.AgrupadorEspacioFisico;
import co.edu.unicauca.sgph.agrupador.infrastructure.input.DTORequest.AsignacionEspacioFisicoDTO;
import co.edu.unicauca.sgph.agrupador.infrastructure.input.DTORequest.FiltroGrupoDTO;
import co.edu.unicauca.sgph.agrupador.infrastructure.input.DTOResponse.AgrupadorEspacioFisicoOutDTO;
import co.edu.unicauca.sgph.agrupador.infrastructure.output.persistence.entity.AgrupadorEspacioFisicoEntity;
import co.edu.unicauca.sgph.agrupador.infrastructure.output.persistence.repository.AgrupadorEspacioFisicoRepositoryInt;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.EspacioFisicoDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.MensajeOutDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.EspacioFisicoEntity;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.repository.EspacioFisicoRepositoryInt;
import co.edu.unicauca.sgph.facultad.infrastructure.output.persistence.entity.FacultadEntity;

@Service
public class GestionarAgrupadorEspacioFisicoGatewayImplAdapter
		implements GestionarAgrupadorEspacioFisicoGatewayIntPort {

	@PersistenceContext
	private EntityManager em;
	
	private AgrupadorEspacioFisicoRepositoryInt agrupadorEspacioFisicoRepositoryInt;
	private ModelMapper modelMapper;
	
	@Autowired
	private EspacioFisicoRepositoryInt espacioFisicoRepositoryInt;

	public GestionarAgrupadorEspacioFisicoGatewayImplAdapter(
			AgrupadorEspacioFisicoRepositoryInt agrupadorEspacioFisicoRepositoryInt, ModelMapper modelMapper) {
		this.agrupadorEspacioFisicoRepositoryInt = agrupadorEspacioFisicoRepositoryInt;
		this.modelMapper = modelMapper;
	}

	/**
	 * @see co.edu.unicauca.sgph.agrupador.aplication.output.GestionarAgrupadorEspacioFisicoGatewayIntPort#consultarAgrupadoresEspaciosFisicosPorIdAgrupadorEspacioFisico(java.util.List)
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
	 * @see co.edu.unicauca.sgph.agrupador.aplication.output.GestionarAgrupadorEspacioFisicoGatewayIntPort#consultarAgrupadoresEspaciosFisicosPorIdFacultad(java.util.List)
	 */
	@Override
	public List<AgrupadorEspacioFisico> consultarAgrupadoresEspaciosFisicosPorIdFacultad(List<Long> idFacultad) {
		List<AgrupadorEspacioFisicoEntity> lstAgrupadorEspacioFisicoEntity = this.agrupadorEspacioFisicoRepositoryInt
				.consultarAgrupadoresEspaciosFisicosPorIdFacultad(idFacultad);
		return this.modelMapper.map(lstAgrupadorEspacioFisicoEntity, new TypeToken<List<AgrupadorEspacioFisico>>() {
		}.getType());
	}

	/** 
	 * @see co.edu.unicauca.sgph.agrupador.aplication.output.GestionarAgrupadorEspacioFisicoGatewayIntPort#consultarAgrupadoresEspaciosFisicosAsociadosACursoPorIdCurso(java.lang.Long)
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
		Page<AgrupadorEspacioFisicoEntity> resultado = this.agrupadorEspacioFisicoRepositoryInt
				.findByFacultadIdFacultadIn(filtro.getListaIdFacultades(), pageable);
		Page<AgrupadorEspacioFisicoOutDTO> pageAgrupadorEspacioFisicoOutDTO = resultado.map(this::mapGrupo);

		// Se contabilizan los espacio físicos por grupo
		List<Object[]> lstContEspaciosPorGrupo = this.agrupadorEspacioFisicoRepositoryInt
				.contarEspaciosFisicosPorAgrupador();

		Map<Long, Long> mapaContEspaciosPorGrupo = lstContEspaciosPorGrupo.stream()
				.collect(Collectors.toMap(obj -> (Long) obj[0], obj -> (Long) obj[1]));

		for (AgrupadorEspacioFisicoOutDTO agrupadorEspacioFisicoOutDTO : pageAgrupadorEspacioFisicoOutDTO
				.getContent()) {
			agrupadorEspacioFisicoOutDTO.setCantidadEspaciosFisicosAsignados(
					mapaContEspaciosPorGrupo.get(agrupadorEspacioFisicoOutDTO.getIdAgrupadorEspacioFisico()));
		}
		return pageAgrupadorEspacioFisicoOutDTO;
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
	
	@Override
	public MensajeOutDTO guardarAsignacion(AsignacionEspacioFisicoDTO asignacion) {
		this.eliminarAgrupadoresEspacioFisico(asignacion.getQuitados(), asignacion.getIdGrupo());
		this.agregarAgrupadosEspacioFisico(asignacion.getAgregados(), asignacion.getIdGrupo());
		MensajeOutDTO resultado = new MensajeOutDTO();
		resultado.setError(false);
		resultado.setDescripcion("Asignación guardada correctamente");
		return resultado;
	}	
	
	private void eliminarAgrupadoresEspacioFisico(List<EspacioFisicoDTO> quitados, Long idGrupo) {
		quitados.forEach(q -> {
			Optional<EspacioFisicoEntity> qEntidad = this.espacioFisicoRepositoryInt.findById(q.getIdEspacioFisico());
			List<AgrupadorEspacioFisicoEntity> agrupadores = qEntidad.get().getAgrupadores();
			agrupadores = agrupadores.stream().filter(a -> !a.getIdAgrupadorEspacioFisico().equals(idGrupo))
					.collect(Collectors.toList());
			EspacioFisicoEntity espacioFisicoEntity = qEntidad.get();
			espacioFisicoEntity.setAgrupadores(agrupadores);
			this.espacioFisicoRepositoryInt.save(espacioFisicoEntity);
		});
	}

	private void agregarAgrupadosEspacioFisico(List<EspacioFisicoDTO> agregados, Long idGrupo) {
		agregados.forEach(q -> {
			Optional<EspacioFisicoEntity> qEntidad = this.espacioFisicoRepositoryInt.findById(q.getIdEspacioFisico());
			List<AgrupadorEspacioFisicoEntity> agrupadores = qEntidad.get().getAgrupadores();
			AgrupadorEspacioFisicoEntity nuevo = new AgrupadorEspacioFisicoEntity();
			nuevo.setIdAgrupadorEspacioFisico(idGrupo);
			agrupadores.add(nuevo);
			EspacioFisicoEntity espacioFisicoEntity = qEntidad.get();
			espacioFisicoEntity.setAgrupadores(agrupadores);
			this.espacioFisicoRepositoryInt.save(espacioFisicoEntity);
		});
	}	
	
}