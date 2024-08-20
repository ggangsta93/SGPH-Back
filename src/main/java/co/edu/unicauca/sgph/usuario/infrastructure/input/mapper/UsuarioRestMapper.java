package co.edu.unicauca.sgph.usuario.infrastructure.input.mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import co.edu.unicauca.sgph.usuario.infrastructure.output.persistence.entity.RolUsuarioEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.edu.unicauca.sgph.programa.domain.model.Programa;
import co.edu.unicauca.sgph.usuario.domain.model.Rol;
import co.edu.unicauca.sgph.usuario.domain.model.Usuario;
import co.edu.unicauca.sgph.usuario.infrastructure.input.DTORequest.UsuarioInDTO;
import co.edu.unicauca.sgph.usuario.infrastructure.input.DTOResponse.RolOutDTO;
import co.edu.unicauca.sgph.usuario.infrastructure.input.DTOResponse.UsuarioOutDTO;

@Mapper(componentModel = "spring")
public interface UsuarioRestMapper {
	
	RolOutDTO toRolOutDTO(Rol rol);
	
	List<RolOutDTO> toLstRolOutDTO(List<Rol> rol);
			
	@Mapping(target = "idPersona", source = "usuario.persona.idPersona")
	@Mapping(target = "idTipoIdentificacion", source = "usuario.persona.tipoIdentificacion.idTipoIdentificacion")
	@Mapping(target = "codigoTipoIdentificacion", source = "usuario.persona.tipoIdentificacion.codigoTipoIdentificacion")
	@Mapping(target = "numeroIdentificacion", source = "usuario.persona.numeroIdentificacion")
	@Mapping(target = "primerNombre", source = "usuario.persona.primerNombre")	
	@Mapping(target = "segundoNombre", source = "usuario.persona.segundoNombre")	
	@Mapping(target = "primerApellido", source = "usuario.persona.primerApellido")	
	@Mapping(target = "segundoApellido", source = "usuario.persona.segundoApellido")	
	@Mapping(target = "email", source = "usuario.persona.email")
    @Mapping(target = "lstIdRol", source = "usuario.roles")
    @Mapping(target = "lstRol", source = "usuario.roles")
	@Mapping(target = "lstIdPrograma", source = "usuario.programas")	
	UsuarioOutDTO toUsuarioOutDTO(Usuario usuario);

	@Mapping(target = "persona", expression = "java(new Persona(usuarioInDTO.getIdPersona()))")
    @Mapping(target = "roles", source = "usuarioInDTO.lstIdRol")
	@Mapping(target = "programas", source = "usuarioInDTO.lstIdPrograma")
	Usuario toUsuario(UsuarioInDTO usuarioInDTO);
	
	List<UsuarioOutDTO> toLstUsuarioOutDTO(List<Usuario> lstUsuario);
		
    default Set<Rol> toRol(List<Long> lstIdRol) {
        Set<Rol> roles = new HashSet<>();
        for (Long idRol : lstIdRol) {
            Rol rol = new Rol();
            rol.setIdRol(idRol); 
            roles.add(rol);
        }
        return roles;
    }
    
    default List<Long> tolstIdRol(Set<Rol> roles ) {
        List<Long> lstIdRol = new ArrayList<>();
        for (Rol rol : roles) {
        	lstIdRol.add(rol.getIdRol());
        }
        return lstIdRol;
    }
    default List<RolUsuarioEnum> mapRolesToRolUsuario(Set<Rol> roles) {
        return roles.stream()
                .map(Rol::getRolUsuario) // Ensure Rol has a method getRolUsuario()
                .collect(Collectors.toList());
    }
    
    default List<Programa> toPrograma(List<Long> lstIdPrograma) {
        List<Programa> programas = new ArrayList<>();
        for (Long idPrograma : lstIdPrograma) {
            Programa programa = new Programa();
            programa.setIdPrograma(idPrograma); 
            programas.add(programa);
        }
        return programas;
    }
    
	default List<Long> tolstIdPrograma(List<Programa> programas) {
		List<Long> lstIdPrograma = new ArrayList<>();
		for (Programa programa : programas) {
			lstIdPrograma.add(programa.getIdPrograma());
		}
		return lstIdPrograma;
	}
    
}