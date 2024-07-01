package co.edu.unicauca.sgph.common.infrastructure.output.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.edu.unicauca.sgph.common.infrastructure.output.persistence.entities.PersonaEntity;
import co.edu.unicauca.sgph.common.infrastructure.output.persistence.entities.TipoIdentificacionEntity;

public interface PersonaRepositoryInt extends JpaRepository<PersonaEntity, Long> {

    PersonaEntity findByTipoIdentificacionAndNumeroIdentificacion(TipoIdentificacionEntity tipoIdentificacion, String numeroIdentificacion);
        
	@Query("SELECT per "
			+ " FROM PersonaEntity per "
			+ " WHERE per.email = :email "
			+ " AND (:idPersona IS NULL OR per.idPersona != :idPersona) "
			+ "")
    PersonaEntity consultarPersonaPorEmail(String email, Long idPersona);
	
	@Query("SELECT per "
			+ " FROM PersonaEntity per "
			+ " WHERE per.tipoIdentificacion.idTipoIdentificacion = :idTipoIdentificacion "
			+ " AND per.numeroIdentificacion = :numeroIdentificacion "
			+ " AND (:idPersona IS NULL OR per.idPersona != :idPersona) "
			+ "")
    PersonaEntity consultarPersonaPorTipoYNumeroIdentificacion(Long idTipoIdentificacion, String numeroIdentificacion, Long idPersona);
	
}
