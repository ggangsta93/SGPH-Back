package co.edu.unicauca.sgph.seguridad.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import co.edu.unicauca.sgph.seguridad.entity.UsuarioPrincipal;
import co.edu.unicauca.sgph.usuario.domain.model.Usuario;
import co.edu.unicauca.sgph.usuario.infrastructure.output.persistence.gateway.GestionarUsuarioGatewayImplAdapter;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	@Lazy
	private GestionarUsuarioGatewayImplAdapter gestionarUsuarioGatewayImplAdapter;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = this.gestionarUsuarioGatewayImplAdapter.consultarUsuarioPorNombreUsuario(username);
		return UsuarioPrincipal.build(usuario);
	}

}
