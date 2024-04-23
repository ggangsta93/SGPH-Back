package co.edu.unicauca.sgph.usuario.infrastructure.input.mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.edu.unicauca.sgph.common.domain.model.TipoIdentificacion;
import co.edu.unicauca.sgph.programa.domain.model.Programa;
import co.edu.unicauca.sgph.usuario.domain.model.Rol;
import co.edu.unicauca.sgph.usuario.domain.model.Usuario;
import co.edu.unicauca.sgph.usuario.infrastructure.input.DTORequest.UsuarioInDTO;
import co.edu.unicauca.sgph.usuario.infrastructure.input.DTOResponse.RolOutDTO;
import co.edu.unicauca.sgph.usuario.infrastructure.input.DTOResponse.TipoIdentificacionOutDTO;
import co.edu.unicauca.sgph.usuario.infrastructure.input.DTOResponse.UsuarioOutDTO;

@Mapper(componentModel = "spring")
public interface UsuarioRestMapper {
	
	RolOutDTO toRolOutDTO(Rol rol);
	
	List<RolOutDTO> toLstRolOutDTO(List<Rol> rol);
	
	TipoIdentificacionOutDTO toTipoIdentificacionOutDTO(TipoIdentificacion tipoIdentificacion);
	
	List<TipoIdentificacionOutDTO> toLstTipoIdentificacionOutDTO(List<TipoIdentificacion> lstTipoIdentificacion);

	@Mapping(target = "idTipoIdentificacion", expression = "java(usuario.getTipoIdentificacion().getIdTipoIdentificacion())")
	@Mapping(target = "codigoTipoIdentificacion", expression = "java(usuario.getTipoIdentificacion().getCodigoTipoIdentificacion())")
    @Mapping(target = "lstIdRol", source = "usuario.roles")
	@Mapping(target = "lstIdPrograma", source = "usuario.programas")	
	UsuarioOutDTO toUsuarioOutDTO(Usuario usuario);

	@Mapping(target = "tipoIdentificacion", expression = "java(new TipoIdentificacion(usuarioInDTO.getIdTipoIdentificacion()))")
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