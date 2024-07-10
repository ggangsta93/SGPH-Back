package co.edu.unicauca.sgph.usuario.aplication.output;

import java.util.List;

import org.springframework.data.domain.Page;

import co.edu.unicauca.sgph.persona.domain.model.TipoIdentificacion;
import co.edu.unicauca.sgph.usuario.domain.model.Rol;
import co.edu.unicauca.sgph.usuario.domain.model.Usuario;
import co.edu.unicauca.sgph.usuario.infrastructure.input.DTORequest.FiltroUsuarioDTO;
import co.edu.unicauca.sgph.usuario.infrastructure.input.DTOResponse.UsuarioOutDTO;

public interface GestionarUsuarioGatewayIntPort {

	/**
	 * Método encargado de guardar o actualizar un usuario <br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param usuario
	 * @return
	 */
	public Usuario guardarUsuario(Usuario usuario);

	/**
	 * Método encargado de consultar los usuarios por diferentes criterios de
	 * busqueda y retornarlos de manera paginada <br>
	 * 
	 * @param filtroUsuarioDTO DTO con los criterios de busqueda
	 * @return
	 */
	public Page<UsuarioOutDTO> consultarUsuariosPorFiltro(FiltroUsuarioDTO filtroUsuarioDTO);

	/**
	 * Método encargado de consultar todos los tipos de identificación de
	 * persona<br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @return
	 */
	public List<TipoIdentificacion> consultarTiposIdentificacion();

	/**
	 * Método encargado de consultar todos los roles de usuario<br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @return
	 */
	public List<Rol> consultarRoles();

	/**
	 * Método encargado de consultar todos los estados de usuario<br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @return
	 */
	public List<String> consultarEstadosUsuario();

	/**
	 * Método encargado de validar si existe un nombre de usuario. Este es utilizado
	 * por la anotación @existeUsuarioPorNombreUsuario<br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param nombreUsuario Nombre de usuario
	 * @param idUsuario     Identificador único usuario (Es requerido para
	 *                      actualización)
	 * @return
	 */
	public Boolean existeUsuarioPorNombreUsuario(String nombreUsuario, Long idUsuario);

	/**
	 * Método encargado de validar si ya existe un usuario con el idPersona. Este es
	 * utilizado por la anotación @existeIdPersona<br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param idPersona Identificador único persona
	 * @param idUsuario Identificador único usuario (Es requerido para
	 *                  actualización)
	 * @return
	 */
	public Boolean existeUsuarioPorIdPersona(Long idPersona, Long idUsuario);
}
