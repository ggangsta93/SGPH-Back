package co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.gateway;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import co.edu.unicauca.sgph.espaciofisico.aplication.output.GestionarTipoEspacioFisicoGatewayIntPort;
import co.edu.unicauca.sgph.espaciofisico.domain.model.TipoEspacioFisico;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.TipoEspacioFisicoEntity;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.repository.TipoEspacioFisicoRepositoryInt;

@Service
public class GestionarTipoEspacioFisicoGatewayImplAdapter implements GestionarTipoEspacioFisicoGatewayIntPort {

	@PersistenceContext
	private EntityManager entityManager;

	private TipoEspacioFisicoRepositoryInt tipoEspacioFisicoRepositoryInt;
	private ModelMapper modelMapper;

	public GestionarTipoEspacioFisicoGatewayImplAdapter(TipoEspacioFisicoRepositoryInt tipoEspacioFisicoRepositoryInt,
			ModelMapper modelMapper) {
		this.tipoEspacioFisicoRepositoryInt = tipoEspacioFisicoRepositoryInt;
		this.modelMapper = modelMapper;
	}

	/**
	 * @see co.edu.unicauca.sgph.espaciofisico.aplication.output.GestionarTipoEspacioFisicoGatewayIntPort#consultarTiposEspaciosFisicosPorUbicaciones(java.util.List)
	 */
	@Override
	public List<TipoEspacioFisico> consultarTiposEspaciosFisicosPorUbicaciones(List<String> lstUbicaciones) {
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(
				"SELECT DISTINCT tipoEspacioFisico FROM EspacioFisicoEntity espacioFisico JOIN espacioFisico.tipoEspacioFisico tipoEspacioFisico WHERE 1=1 ");

		Map<String, Object> parametros = new HashMap<>();

		if (Objects.nonNull(lstUbicaciones) && !lstUbicaciones.isEmpty()) {
			queryBuilder.append(" AND espacioFisico.ubicacion IN (:lstUbicaciones) ");
			parametros.put("lstUbicaciones", lstUbicaciones);
		}

		TypedQuery<TipoEspacioFisicoEntity> typedQuery = entityManager.createQuery(queryBuilder.toString(),
				TipoEspacioFisicoEntity.class);

		for (Map.Entry<String, Object> entry : parametros.entrySet()) {
			typedQuery.setParameter(entry.getKey(), entry.getValue());
		}

		return this.modelMapper.map(typedQuery.getResultList(), new TypeToken<List<TipoEspacioFisico>>() {
		}.getType());
	}
}