package co.edu.unicauca.sgph.seguridad.dto;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

public class JwtDto {
    private String token;
    private String bearer = "Bearer";
    private String nombreUsuario;
    private List<Long> lstIdPrograma;
    private Collection<? extends GrantedAuthority> authorities;

    public JwtDto(String token, String nombreUsuario, Collection<? extends GrantedAuthority> authorities, List<Long> lstIdPrograma) {
        this.token = token;
        this.nombreUsuario = nombreUsuario;
        this.authorities = authorities;
        this.lstIdPrograma=lstIdPrograma;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBearer() {
        return bearer;
    }

    public void setBearer(String bearer) {
        this.bearer = bearer;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

	public List<Long> getLstIdPrograma() {
		return lstIdPrograma;
	}

	public void setLstIdPrograma(List<Long> lstIdPrograma) {
		this.lstIdPrograma = lstIdPrograma;
	}
}
