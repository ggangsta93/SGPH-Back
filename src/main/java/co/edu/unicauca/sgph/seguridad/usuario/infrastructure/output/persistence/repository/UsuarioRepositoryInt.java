package co.edu.unicauca.sgph.seguridad.usuario.infrastructure.output.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.edu.unicauca.sgph.seguridad.usuario.infrastructure.output.persistence.entity.UsuarioEntity;

public interface UsuarioRepositoryInt extends JpaRepository<UsuarioEntity, Long> {

	/**
	 * Método encargado de consultar los identificadores de programas asociados a un
	 * usuario </br>
	 * 
	 * @param idPersona
	 * @return
	 */
	@Query("SELECT pro.idPrograma FROM UsuarioEntity usu JOIN usu.programas pro WHERE usu.idPersona IN (:idPersona)")
	public List<Long> consultarIdentificadoresProgramasUsuarioPorPersona(Long idPersona);

	/**
	 * Método encargado de consultar los identificadores de roles asociados a un
	 * usuario </br>
	 * 
	 * @param idPersona
	 * @return
	 */
	@Query("SELECT rol.idRol FROM UsuarioEntity usu JOIN usu.roles rol WHERE usu.idPersona IN (:idPersona)")
	public List<Long> consultarIdentificadoresRolesUsuarioPorPersona(Long idPersona);

}
