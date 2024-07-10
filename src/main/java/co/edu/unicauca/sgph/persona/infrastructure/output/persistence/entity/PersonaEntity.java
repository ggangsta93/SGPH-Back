package co.edu.unicauca.sgph.persona.infrastructure.output.persistence.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "PERSONA", uniqueConstraints = {
	    @UniqueConstraint(columnNames = {"ID_TIPO_IDENTIFICACION", "NUMERO_IDENTIFICACION"}),
	    @UniqueConstraint(columnNames = {"EMAIL"})
})
public class PersonaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_PERSONA", nullable = false)
	private Long idPersona;

	@ManyToOne
	@JoinColumn(name = "ID_TIPO_IDENTIFICACION")
	private TipoIdentificacionEntity tipoIdentificacion;

	@Column(name = "NUMERO_IDENTIFICACION")
	private String numeroIdentificacion;

	@Column(name = "PRIMER_NOMBRE")
	private String primerNombre;

	@Column(name = "SEGUNDO_NOMBRE")
	private String segundoNombre;

	@Column(name = "PRIMER_APELLIDO")
	private String primerApellido;

	@Column(name = "SEGUNDO_APELLIDO")
	private String segundoApellido;

	@Column(name = "EMAIL")
	private String email;
	
	/**
	 * Atributo no mapeado en la tabla que sirve para conocer que personas no están
	 * referenciadas en las tablas USUARIO y DOCENTE
	 */
	@Transient
	private Boolean sinReferencia;

	public PersonaEntity() {
		super();
	}

	public Long getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

	public TipoIdentificacionEntity getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	public void setTipoIdentificacion(TipoIdentificacionEntity tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getSinReferencia() {
		return sinReferencia;
	}

	public void setSinReferencia(Boolean sinReferencia) {
		this.sinReferencia = sinReferencia;
	}
}