package co.edu.unicauca.sgph.usuario.infrastructure.input.DTOResponse;

import java.util.List;

import co.edu.unicauca.sgph.programa.infrastructure.input.DTOResponse.ProgramaReservasDTO;

public class UsuarioReservasDTO {

	private Long oidTercero; // ID del usuario
    private String usuario; // Nombre de usuario
    private Long oidTipoIdentificacion; // ID del tipo de identificación
    private String tipoIdentificacion; // Tipo de identificación
    private String identificacion; // Número de identificación
    private String primerApellido; // Primer apellido
    private String segundoApellido; // Segundo apellido
    private String primerNombre; // Primer nombre
    private String segundoNombre; // Segundo nombre
    private String correo; // Correo electrónico
    private String celular; // Teléfono o celular
    private List<ProgramaDTO> programa; // Lista de programas
	public Long getOidTercero() {
		return oidTercero;
	}
	public void setOidTercero(Long oidTercero) {
		this.oidTercero = oidTercero;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public Long getOidTipoIdentificacion() {
		return oidTipoIdentificacion;
	}
	public void setOidTipoIdentificacion(Long oidTipoIdentificacion) {
		this.oidTipoIdentificacion = oidTipoIdentificacion;
	}
	public String getTipoIdentificacion() {
		return tipoIdentificacion;
	}
	public void setTipoIdentificacion(String tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}
	public String getIdentificacion() {
		return identificacion;
	}
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}
	public String getPrimerApellido() {
		return primerApellido;
	}
	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}
	public String getSegundoApellido() {
		return segundoApellido;
	}
	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}
	public String getPrimerNombre() {
		return primerNombre;
	}
	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}
	public String getSegundoNombre() {
		return segundoNombre;
	}
	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
    
	public List<ProgramaDTO> getPrograma() {
		return programa;
	}
	public void setPrograma(List<ProgramaDTO> programa) {
		this.programa = programa;
	}

	public static class ProgramaDTO {
        private String rol;
        private String programaDepartamento;
        private String codigo;
        private String nombre;
        private String estado;
		public String getRol() {
			return rol;
		}
		public void setRol(String rol) {
			this.rol = rol;
		}
		public String getProgramaDepartamento() {
			return programaDepartamento;
		}
		public void setProgramaDepartamento(String programaDepartamento) {
			this.programaDepartamento = programaDepartamento;
		}
		public String getCodigo() {
			return codigo;
		}
		public void setCodigo(String codigo) {
			this.codigo = codigo;
		}
		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		public String getEstado() {
			return estado;
		}
		public void setEstado(String estado) {
			this.estado = estado;
		}

        
    }
}
