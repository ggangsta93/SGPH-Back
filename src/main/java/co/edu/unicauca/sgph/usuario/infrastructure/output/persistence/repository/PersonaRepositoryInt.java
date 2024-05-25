package co.edu.unicauca.sgph.usuario.infrastructure.output.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unicauca.sgph.common.infrastructure.output.persistence.entities.PersonaEntity;
import co.edu.unicauca.sgph.common.infrastructure.output.persistence.entities.TipoIdentificacionEntity;

public interface PersonaRepositoryInt extends JpaRepository<PersonaEntity, Long> {

    PersonaEntity findByTipoIdentificacionAndNumeroIdentificacion(TipoIdentificacionEntity tipoIdentificacion, String numeroIdentificacion);

}
