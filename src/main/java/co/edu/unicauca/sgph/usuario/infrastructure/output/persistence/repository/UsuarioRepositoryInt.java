package co.edu.unicauca.sgph.usuario.infrastructure.output.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.edu.unicauca.sgph.usuario.infrastructure.output.persistence.entity.RolEntity;
import co.edu.unicauca.sgph.usuario.infrastructure.output.persistence.entity.UsuarioEntity;

public interface UsuarioRepositoryInt extends JpaRepository<UsuarioEntity, Long> {

	/**
	 * Método encargado de consultar los identificadores de programas asociados a un
	 * usuario <br>
	 * 
	 * @param idPersona
	 * @return
	 */
	@Query("SELECT pro.idPrograma FROM UsuarioEntity usu JOIN usu.programas pro WHERE usu.persona.idPersona IN (:idPersona)")
	public List<Long> consultarIdentificadoresProgramasUsuarioPorPersona(Long idPersona);

	/**
	 * Método encargado de consultar los identificadores de roles asociados a un
	 * usuario <br>
	 * 
	 * @param idPersona
	 * @return
	 */
	@Query("SELECT rol.idRol FROM UsuarioEntity usu JOIN usu.roles rol WHERE usu.persona.idPersona IN (:idPersona)")
	public List<Long> consultarIdentificadoresRolesUsuarioPorPersona(Long idPersona);
		
	/**
	 * Método encargado de consultar todos los roles de usuario<br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @return
	 */
	@Query("SELECT rol FROM RolEntity rol")
	public List<RolEntity> consultarRoles();

	public Optional<UsuarioEntity> findByNombreUsuario(String nombreUsuario);
	

	@Query("SELECT usu "
		+ " FROM UsuarioEntity usu "
		+ " WHERE usu.nombreUsuario = :nombreUsuario "
		+ " AND (:idUsuario IS NULL OR usu.idUsuario != :idUsuario) "
		+ "")
	public UsuarioEntity consultarUsuarioPorNombreUsuario(String nombreUsuario, Long idUsuario);
	
	@Query("SELECT usu "
			+ " FROM UsuarioEntity usu "
			+ " WHERE usu.persona.idPersona = :idPersona "
			+ " AND (:idUsuario IS NULL OR usu.idUsuario != :idUsuario) "
			+ "")
	public UsuarioEntity consultarUsuarioPorIdPersona(Long idPersona, Long idUsuario);	
}