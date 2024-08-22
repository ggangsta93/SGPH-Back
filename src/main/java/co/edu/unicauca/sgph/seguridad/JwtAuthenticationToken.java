package co.edu.unicauca.sgph.seguridad;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private final String token;
    private final UserDetails principal;

    public JwtAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
        this.token = null;
        this.principal = null;
    }

    public JwtAuthenticationToken(String token) {
        super(null, token, Collections.emptyList());
        this.token = token;
        this.principal = null;
    }
    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) {
        if (isAuthenticated) {
            System.out.println("XXXXXXXXXXXXXXX");
            super.setAuthenticated(true);
        }
        super.setAuthenticated(false);
    }
}
