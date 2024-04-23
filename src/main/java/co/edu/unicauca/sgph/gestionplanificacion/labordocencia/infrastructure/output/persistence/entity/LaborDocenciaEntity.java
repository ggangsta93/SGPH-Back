package co.edu.unicauca.sgph.gestionplanificacion.labordocencia.infrastructure.output.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LABOR_DOCENCIA")
public class LaborDocenciaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_LABOR_DOCENCIA")
	private Long idLaborDocencia;

	@Column(name = "OID_PERIODO")
	private Long oidPeriodo;

	@Column(name = "PERIODO")
	private String periodo;

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

	@Column(name = "CORREO")
	private String correo;

	@Column(name = "NOMBRE_MATERIA")
	private String nombreMateria;

	@Column(name = "NOMBRE_PROGRAMA")
	private String nombrePrograma;

	@Column(name = "OID_MATERIA")
	private String oidMateria;

	@Column(name = "CODIGO_MATERIA")
	private String codigoMateria;

	@Column(name = "TIPO_MATERIA")
	private Long tipoMateria;

	@Column(name = "GRUPO_MATERIA")
	private String grupo;

	@Column(name = "HORAS_TEORICAS")
	private String horasTeoricas;

	public LaborDocenciaEntity() {
		// Constructor sin argumentos requerido por ModelMapper y JPA
	}

	public Long getIdLaborDocencia() {
		return idLaborDocencia;
	}

	public void setIdLaborDocencia(Long idLaborDocencia) {
		this.idLaborDocencia = idLaborDocencia;
	}

	public Long getOidPeriodo() {
		return oidPeriodo;
	}

	public void setOidPeriodo(Long oidPeriodo) {
		this.oidPeriodo = oidPeriodo;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
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

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getNombreMateria() {
		return nombreMateria;
	}

	public void setNombreMateria(String nombreMateria) {
		this.nombreMateria = nombreMateria;
	}

	public String getNombrePrograma() {
		return nombrePrograma;
	}

	public void setNombrePrograma(String nombrePrograma) {
		this.nombrePrograma = nombrePrograma;
	}

	public String getOidMateria() {
		return oidMateria;
	}

	public void setOidMateria(String oidMateria) {
		this.oidMateria = oidMateria;
	}

	public String getCodigoMateria() {
		return codigoMateria;
	}

	public void setCodigoMateria(String codigoMateria) {
		this.codigoMateria = codigoMateria;
	}

	public Long getTipoMateria() {
		return tipoMateria;
	}

	public void setTipoMateria(Long tipoMateria) {
		this.tipoMateria = tipoMateria;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getHorasTeoricas() {
		return horasTeoricas;
	}

	public void setHorasTeoricas(String horasTeoricas) {
		this.horasTeoricas = horasTeoricas;
	}
}