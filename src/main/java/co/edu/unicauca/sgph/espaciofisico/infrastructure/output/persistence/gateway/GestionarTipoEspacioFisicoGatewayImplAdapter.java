package co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.gateway;

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

	public GestionarTipoEspacioFisicoGatewayImplAdapter(TipoEspacioFisicoRepositoryInt tipoEspacioFisicoRepositoryInt, ModelMapper modelMapper) {
		this.tipoEspacioFisicoRepositoryInt = tipoEspacioFisicoRepositoryInt;
		this.modelMapper = modelMapper;
	}

	/** 
	 * @see co.edu.unicauca.sgph.espaciofisico.aplication.output.GestionarTipoEspacioFisicoGatewayIntPort#consultarTiposEspaciosFisicosPorIdFacultad(java.util.List)
	 */
	@Override
	public List<TipoEspacioFisico> consultarTiposEspaciosFisicosPorIdFacultad(List<Long> lstIdFacultad) {

		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT DISTINCT tipoEspacioFisico FROM TipoEspacioFisicoEntity tipoEspacioFisico WHERE 1=1 ");

		Map<String, Object> parametros = new HashMap<>();

		if (Objects.nonNull(lstIdFacultad) && !lstIdFacultad.isEmpty()) {
			queryBuilder.append(" AND tipoEspacioFisico IN ( ");
			queryBuilder.append(" SELECT DISTINCT espacioFisico.tipoEspacioFisico ");
			queryBuilder.append(" FROM EspacioFisicoEntity espacioFisico ");
			queryBuilder.append(" JOIN espacioFisico.edificio edi ");
			queryBuilder.append(" JOIN edi.facultad fac ");
			queryBuilder.append(" WHERE fac.idFacultad IN :lstIdFacultad) ");
			parametros.put("lstIdFacultad", lstIdFacultad);
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