package co.edu.unicauca.sgph.seguridad.usuario.infrastructure.input.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.sgph.seguridad.usuario.aplication.input.GestionarUsuarioCUIntPort;
import co.edu.unicauca.sgph.seguridad.usuario.infrastructure.input.DTORequest.FiltroUsuarioDTO;
import co.edu.unicauca.sgph.seguridad.usuario.infrastructure.input.DTORequest.UsuarioInDTO;
import co.edu.unicauca.sgph.seguridad.usuario.infrastructure.input.DTOResponse.UsuarioOutDTO;
import co.edu.unicauca.sgph.seguridad.usuario.infrastructure.input.mapper.UsuarioRestMapper;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/AdministrarUsuario")
public class UsuarioController {

	private GestionarUsuarioCUIntPort gestionarUsuarioCUIntPort;
	private UsuarioRestMapper usuarioRestMapper;

	public UsuarioController(GestionarUsuarioCUIntPort gestionarUsuarioCUIntPort, UsuarioRestMapper usuarioRestMapper) {
		this.gestionarUsuarioCUIntPort = gestionarUsuarioCUIntPort;
		this.usuarioRestMapper = usuarioRestMapper;
	}

	/**
	 * Método encargado de guardar o actualizar un usuario </br>
	 * 
	 * @author Pedro Javier Arias Lasso <apedro@unicauca.edu.co>
	 * 
	 * @param usuarioInDTO
	 * @return
	 */
	@PostMapping("/guardarUsuario")
	public UsuarioOutDTO guardarUsuario(@RequestBody UsuarioInDTO usuarioInDTO) {
		return this.usuarioRestMapper.toUsuarioOutDTO(
				this.gestionarUsuarioCUIntPort.guardarUsuario(this.usuarioRestMapper.toUsuario(usuarioInDTO)));
	}

	/**
	 * Método encargado de consultar los usuarios por diferentes criterios de
	 * busqueda y retornarlos de manera paginada
	 * 
	 * 
	 * @param filtroUsuarioDTO DTO con los criterios de busqueda
	 * @return
	 */
	@PostMapping("/consultarUsuariosPorFiltro")
	public Page<UsuarioOutDTO> consultarUsuariosPorFiltro(@RequestBody FiltroUsuarioDTO filtroUsuarioDTO) {
		return this.gestionarUsuarioCUIntPort.consultarUsuariosPorFiltro(filtroUsuarioDTO);
	}

}