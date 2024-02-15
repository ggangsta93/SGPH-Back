package co.edu.unicauca.sgph.aula.infrastructure.output.persistence.gateway;

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

import co.edu.unicauca.sgph.aula.aplication.output.GestionarTipoAulaGatewayIntPort;
import co.edu.unicauca.sgph.aula.domain.model.TipoAula;
import co.edu.unicauca.sgph.aula.infrastructure.output.persistence.entity.TipoAulaEntity;
import co.edu.unicauca.sgph.aula.infrastructure.output.persistence.repository.TipoAulaRepositoryInt;

@Service
public class GestionarTipoAulaGatewayImplAdapter implements GestionarTipoAulaGatewayIntPort {

	@PersistenceContext
	private EntityManager entityManager;

	private TipoAulaRepositoryInt tipoAulaRepositoryInt;
	private ModelMapper modelMapper;

	public GestionarTipoAulaGatewayImplAdapter(TipoAulaRepositoryInt tipoAulaRepositoryInt, ModelMapper modelMapper) {
		this.tipoAulaRepositoryInt = tipoAulaRepositoryInt;
		this.modelMapper = modelMapper;
	}

	/** 
	 * @see co.edu.unicauca.sgph.aplication.output.GestionarTipoAulaGatewayIntPort#consultarTipoAulasPorIdFacultad(java.util.List)
	 */
	@Override
	public List<TipoAula> consultarTipoAulasPorIdFacultad(List<Long> lstIdFacultad) {

		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT DISTINCT tipoAula FROM TipoAulaEntity tipoAula WHERE 1=1 ");

		Map<String, Object> parametros = new HashMap<>();

		if (Objects.nonNull(lstIdFacultad) && !lstIdFacultad.isEmpty()) {
			queryBuilder.append(" AND tipoAula IN ( ");
			queryBuilder.append(" SELECT DISTINCT aula.tipoAula ");
			queryBuilder.append(" FROM AulaEntity aula ");
			queryBuilder.append(" JOIN aula.edificio edi ");
			queryBuilder.append(" JOIN edi.facultad fac ");
			queryBuilder.append(" WHERE fac.idFacultad IN :lstIdFacultad) ");
			parametros.put("lstIdFacultad", lstIdFacultad);
		}

		TypedQuery<TipoAulaEntity> typedQuery = entityManager.createQuery(queryBuilder.toString(),
				TipoAulaEntity.class);

		for (Map.Entry<String, Object> entry : parametros.entrySet()) {
			typedQuery.setParameter(entry.getKey(), entry.getValue());
		}

		return this.modelMapper.map(typedQuery.getResultList(), new TypeToken<List<TipoAula>>() {
		}.getType());
	}
}