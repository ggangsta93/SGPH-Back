package co.edu.unicauca.sgph.persona.infrastructure.output.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.edu.unicauca.sgph.persona.infrastructure.output.persistence.entity.PersonaEntity;
import co.edu.unicauca.sgph.persona.infrastructure.output.persistence.entity.TipoIdentificacionEntity;

public interface PersonaRepositoryInt extends JpaRepository<PersonaEntity, Long> {
        
	@Query("SELECT per "
			+ " FROM PersonaEntity per "
			+ " WHERE per.email = :email "
			+ " AND (:idPersona IS NULL OR per.idPersona != :idPersona) "
			+ "")
    public PersonaEntity consultarPersonaPorEmail(String email, Long idPersona);
	
	@Query("SELECT per "
			+ " FROM PersonaEntity per "
			+ " WHERE per.tipoIdentificacion.idTipoIdentificacion = :idTipoIdentificacion "
			+ " AND per.numeroIdentificacion = :numeroIdentificacion "
			+ " AND (:idPersona IS NULL OR per.idPersona != :idPersona) "
			+ "")
    public PersonaEntity consultarPersonaPorTipoYNumeroIdentificacion(Long idTipoIdentificacion, String numeroIdentificacion, Long idPersona);
	
	@Query("SELECT per "
			+ " FROM PersonaEntity per "
			+ " WHERE per.idPersona = :idPersona "
			+ "")
    public PersonaEntity consultarPersonaPorIdPersona(Long idPersona);
	
	/**
	 * Método encargado de consultar todos los tipos de identificación persona <br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @return
	 */
	@Query("SELECT ti FROM TipoIdentificacionEntity ti")
	public List<TipoIdentificacionEntity> consultarTiposIdentificacion();
}
