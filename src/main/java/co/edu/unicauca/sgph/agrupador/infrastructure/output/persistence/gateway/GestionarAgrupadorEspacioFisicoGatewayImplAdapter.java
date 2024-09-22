package co.edu.unicauca.sgph.agrupador.infrastructure.output.persistence.gateway;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	public Page<AgrupadorEspacioFisicoOutDTO> filtrarGrupos(FiltroGrupoDTO filtroGrupoDTO) {
		
		PageRequest pageable = null;
		if (Objects.nonNull(filtroGrupoDTO.getPageNumber())
				&& Objects.nonNull(filtroGrupoDTO.getPageSize())) {
			// Configuración de la paginación
			pageable = PageRequest.of(filtroGrupoDTO.getPageNumber(), filtroGrupoDTO.getPageSize());
		}
		
		// Construcción de la consulta con StringBuilder
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(" SELECT NEW co.edu.unicauca.sgph.agrupador.infrastructure.input.DTOResponse.AgrupadorEspacioFisicoOutDTO(");
		queryBuilder.append(" agrupador.idAgrupadorEspacioFisico, agrupador.nombre, agrupador.observacion, ");
		queryBuilder.append(" facultad.idFacultad, facultad.nombre, SIZE(agrupador.espaciosFisicos) )");
		queryBuilder.append(" FROM AgrupadorEspacioFisicoEntity agrupador ");
		queryBuilder.append(" JOIN agrupador.facultad facultad ");
		queryBuilder.append(" WHERE 1=1");
		
		Map<String, Object> parametros = new HashMap<>();
		
		if(Objects.nonNull(filtroGrupoDTO.getListaIdFacultades()) && !filtroGrupoDTO.getListaIdFacultades().isEmpty()) {
			queryBuilder.append(" AND agrupador.facultad.idFacultad IN (:listaIdFacultad)");
			parametros.put("listaIdFacultad", filtroGrupoDTO.getListaIdFacultades());
		}
		
		if (Objects.nonNull(filtroGrupoDTO.getNombre())) {
			queryBuilder.append(" AND agrupador.nombre LIKE UPPER(:nombre)");
			parametros.put("nombre", "%"+filtroGrupoDTO.getNombre().replaceAll("\\s+", " ").trim()+"%");
		}	
				
		// Realiza la consulta paginada
		TypedQuery<AgrupadorEspacioFisicoOutDTO> typedQuery = em.createQuery(queryBuilder.toString(), AgrupadorEspacioFisicoOutDTO.class);
		
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
	    	return new PageImpl<>(typedQuery.getResultList(), pageable, contarGruposConsultados(filtroGrupoDTO));
	    }
	}
	
	
	private Long contarGruposConsultados(FiltroGrupoDTO filtroGrupoDTO) {

		// Construcción de la consulta con StringBuilder
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(" SELECT COUNT(agrupador)");
		queryBuilder.append(" FROM AgrupadorEspacioFisicoEntity agrupador ");
		queryBuilder.append(" JOIN agrupador.facultad facultad ");
		queryBuilder.append(" WHERE 1=1");

		Map<String, Object> parametros = new HashMap<>();

		if (Objects.nonNull(filtroGrupoDTO.getListaIdFacultades()) && !filtroGrupoDTO.getListaIdFacultades().isEmpty()) {
			queryBuilder.append(" AND agrupador.facultad.idFacultad IN (:listaIdFacultad)");
			parametros.put("listaIdFacultad", filtroGrupoDTO.getListaIdFacultades());
		}

		if (Objects.nonNull(filtroGrupoDTO.getNombre())) {
			queryBuilder.append(" AND agrupador.nombre LIKE UPPER(:nombre)");
			parametros.put("nombre", "%" + filtroGrupoDTO.getNombre().replaceAll("\\s+", " ").trim() + "%");
		}
		// Realiza la consulta para contar
		TypedQuery<Long> countQuery = em.createQuery(queryBuilder.toString(), Long.class);

		// Establece parámetros en la consulta
		for (Map.Entry<String, Object> entry : parametros.entrySet()) {
			countQuery.setParameter(entry.getKey(), entry.getValue());
		}

		return countQuery.getSingleResult();
	}
	

	@Override
	public AgrupadorEspacioFisico guardarGrupo(AgrupadorEspacioFisico agrupadorEspacioFisico) {
		return this.modelMapper.map(
				this.agrupadorEspacioFisicoRepositoryInt
						.save(this.modelMapper.map(agrupadorEspacioFisico, AgrupadorEspacioFisicoEntity.class)),
				AgrupadorEspacioFisico.class);
	}
		
	@Override
	@Transactional
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

	/** 
	 * @see co.edu.unicauca.sgph.agrupador.aplication.output.GestionarAgrupadorEspacioFisicoGatewayIntPort#existeAgrupadorPorNombreAgrupador(java.lang.String, java.lang.Long)
	 */
	@Override
	public Boolean existeAgrupadorPorNombreAgrupador(String nombreAgrupador, Long idAgrupadorEspacioFisico) {
		AgrupadorEspacioFisicoEntity agrupadorEspacioFisicoEntity = this.agrupadorEspacioFisicoRepositoryInt
				.consultarAgrupadorPorNombreAgrupador(nombreAgrupador, idAgrupadorEspacioFisico);
		if (Objects.nonNull(agrupadorEspacioFisicoEntity)) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}
	
}