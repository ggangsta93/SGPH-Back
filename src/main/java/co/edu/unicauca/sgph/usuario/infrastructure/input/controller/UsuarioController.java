package co.edu.unicauca.sgph.usuario.infrastructure.input.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.sgph.common.domain.model.CommonEJB;
import co.edu.unicauca.sgph.usuario.aplication.input.GestionarUsuarioCUIntPort;
import co.edu.unicauca.sgph.usuario.domain.model.Usuario;
import co.edu.unicauca.sgph.usuario.infrastructure.input.DTORequest.FiltroUsuarioDTO;
import co.edu.unicauca.sgph.usuario.infrastructure.input.DTORequest.UsuarioInDTO;
import co.edu.unicauca.sgph.usuario.infrastructure.input.DTOResponse.RolOutDTO;
import co.edu.unicauca.sgph.usuario.infrastructure.input.DTOResponse.UsuarioOutDTO;
import co.edu.unicauca.sgph.usuario.infrastructure.input.mapper.UsuarioRestMapper;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/AdministrarUsuario")
public class UsuarioController extends CommonEJB {

	private GestionarUsuarioCUIntPort gestionarUsuarioCUIntPort;
	private UsuarioRestMapper usuarioRestMapper;

	public UsuarioController(GestionarUsuarioCUIntPort gestionarUsuarioCUIntPort, UsuarioRestMapper usuarioRestMapper) {
		this.gestionarUsuarioCUIntPort = gestionarUsuarioCUIntPort;
		this.usuarioRestMapper = usuarioRestMapper;
	}

	/**
	 * Método encargado de guardar o actualizar un usuario <br>
	 * 
	 * @author apedro
	 * 
	 * @param usuarioInDTO
	 * @return
	 */
	@PostMapping("/guardarUsuario")
	public ResponseEntity<?> guardarUsuario(@Valid @RequestBody UsuarioInDTO usuarioInDTO, BindingResult result) {
		Set<String> validaciones = new HashSet<String>();
		validaciones.add("ExisteNombreUsuario");
		validaciones.add("ExisteAlMenosUnProgramaParaRolPlanificador");
		validaciones.add("ExisteIdPersonaUsuario");
		
		if (result.hasErrors()) {
			return validacion(result, validaciones);
		}
				
		if (Boolean.FALSE.equals(usuarioInDTO.getEsValidar())) {
			if(usuarioInDTO.getLstIdPrograma()==null) {
				usuarioInDTO.setLstIdPrograma(new ArrayList<>());
			}
			UsuarioOutDTO usuarioOutDTO = this.usuarioRestMapper.toUsuarioOutDTO(
					this.gestionarUsuarioCUIntPort.guardarUsuario(this.usuarioRestMapper.toUsuario(usuarioInDTO)));

			if (Objects.equals(usuarioInDTO.getIdPersona(), usuarioOutDTO.getIdPersona())) {
				return new ResponseEntity<UsuarioOutDTO>(usuarioOutDTO, HttpStatus.OK);
			} else {
				return new ResponseEntity<UsuarioOutDTO>(usuarioOutDTO, HttpStatus.CREATED);
			}
		} else {
			return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
		}
	}

	/**
	 * Método encargado de consultar los usuarios por diferentes criterios de
	 * busqueda y retornarlos de manera paginada<br>
	 * 
	 * @author apedro
	 * 
	 * @param filtroUsuarioDTO DTO con los criterios de busqueda
	 * @return
	 */
	@PostMapping("/consultarUsuariosPorFiltro")
	public Page<UsuarioOutDTO> consultarUsuariosPorFiltro(@RequestBody FiltroUsuarioDTO filtroUsuarioDTO) {
		Page<UsuarioOutDTO>  respuesta = this.gestionarUsuarioCUIntPort.consultarUsuariosPorFiltro(filtroUsuarioDTO);
		return respuesta;
	}

	/**
	 * Método encargado de consultar todos los roles de usuario<br>
	 * 
	 * @author apedro
	 * 
	 * @return
	 */
	@GetMapping("/consultarRoles")
	public List<RolOutDTO> consultarRoles() {
		return this.usuarioRestMapper.toLstRolOutDTO(this.gestionarUsuarioCUIntPort.consultarRoles());
	}

	/**
	 * Método encargado de consultar todos los estados de usuario<br>
	 * 
	 * @author apedro
	 * 
	 * @return
	 */
	@GetMapping("/consultarEstadosUsuario")
	public List<String> consultarEstadosUsuario() {
		return this.gestionarUsuarioCUIntPort.consultarEstadosUsuario();
	}
	
	/**
	 * Método encargado de cambiar el estado al usuario dado su identificador único</br>
	 * 
	 * @author apedro
	 * 
	 * @param idUsuario
	 * @return
	 */
	@GetMapping("/cambiarEstadoUsuarioPorIdUsuario")
	private UsuarioOutDTO cambiarEstadoUsuarioPorIdUsuario(@RequestParam Long idUsuario) {
		Usuario usuario = this.gestionarUsuarioCUIntPort.cambiarEstadoUsuarioPorIdUsuario(idUsuario);
		if (usuario != null) {
			return this.usuarioRestMapper.toUsuarioOutDTO(usuario);
		}
		return new UsuarioOutDTO();
	}
	
}