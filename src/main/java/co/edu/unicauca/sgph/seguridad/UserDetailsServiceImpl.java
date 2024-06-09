package co.edu.unicauca.sgph.seguridad;

import co.edu.unicauca.sgph.usuario.infrastructure.output.persistence.entity.UsuarioEntity;
import co.edu.unicauca.sgph.usuario.infrastructure.output.persistence.repository.UsuarioRepositoryInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepositoryInt usuarioRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioEntity usuario = usuarioRepository.findByNombreUsuario(username).orElseThrow(() -> new UsernameNotFoundException("Usuario no existe"));
        return new UserDetailsImpl(usuario);
    }
}
