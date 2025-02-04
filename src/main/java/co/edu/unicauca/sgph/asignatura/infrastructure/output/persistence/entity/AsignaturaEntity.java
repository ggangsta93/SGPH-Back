package co.edu.unicauca.sgph.asignatura.infrastructure.output.persistence.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import co.edu.unicauca.sgph.agrupador.infrastructure.output.persistence.entity.AgrupadorEspacioFisicoEntity;
import co.edu.unicauca.sgph.curso.infrastructure.output.persistence.entity.CursoEntity;
import co.edu.unicauca.sgph.programa.infrastructure.output.persistence.entity.ProgramaEntity;

@Entity
@Table(name = "ASIGNATURA")
public class AsignaturaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ASIGNATURA", nullable = false)
	private Long idAsignatura;
	
	@Column(name = "NOMBRE")
	private String nombre;

	@Column(name = "CODIGO_ASIGNATURA")
	private String codigoAsignatura;

	@Column(name = "OID")
	private String oid;

	@Column(name = "SEMESTRE")
	private Integer semestre;

	@Column(name = "PENSUM")
	private String pensum;

	@Column(name = "HORAS_SEMANA")
	private Integer horasSemana;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_PROGRAMA")
	private ProgramaEntity programa;

	@OneToMany(mappedBy = "asignatura", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<CursoEntity> cursos;
		
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "ASIGNATURA_AGRUPADOR_ESP_FIS", joinColumns = @JoinColumn(name = "ID_ASIGNATURA"), inverseJoinColumns = @JoinColumn(name = "ID_AGRUPADOR_ESPACIO_FISICO"))
	private List<AgrupadorEspacioFisicoEntity> agrupadores;

	@Column(name = "ESTADO")
	private EstadoAsignaturaEnum estado;
	
	@Column(name = "APLICA_ESP_SEC")
	private Boolean aplicaEspacioSecundario;

	public AsignaturaEntity() {
		this.cursos=new ArrayList<>();
		this.agrupadores=new ArrayList<>();
	}

	public AsignaturaEntity(Long idAsignatura) {
		this.idAsignatura = idAsignatura;
	}

	public AsignaturaEntity(String nombre, String codigoAsignatura, Integer semestre, ProgramaEntity programa) {
		this.nombre = nombre;
		this.codigoAsignatura = codigoAsignatura;
		this.semestre = semestre;
		this.programa = programa;
	}

	public Long getIdAsignatura() {
		return idAsignatura;
	}

	public void setIdAsignatura(Long idAsignatura) {
		this.idAsignatura = idAsignatura;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodigoAsignatura() {
		return codigoAsignatura;
	}

	public void setCodigoAsignatura(String codigoAsignatura) {
		this.codigoAsignatura = codigoAsignatura;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public Integer getSemestre() {
		return semestre;
	}

	public void setSemestre(Integer semestre) {
		this.semestre = semestre;
	}

	public String getPensum() {
		return pensum;
	}

	public void setPensum(String pensum) {
		this.pensum = pensum;
	}

	public Integer getHorasSemana() {
		return horasSemana;
	}

	public void setHorasSemana(Integer horasSemana) {
		this.horasSemana = horasSemana;
	}

	public ProgramaEntity getPrograma() {
		return programa;
	}

	public void setPrograma(ProgramaEntity programa) {
		this.programa = programa;
	}

	public List<CursoEntity> getCursos() {
		return cursos;
	}

	public void setCursos(List<CursoEntity> cursos) {
		this.cursos = cursos;
	}

	public List<AgrupadorEspacioFisicoEntity> getAgrupadores() {
		return agrupadores;
	}

	public void setAgrupadores(List<AgrupadorEspacioFisicoEntity> agrupadores) {
		this.agrupadores = agrupadores;
	}

	public EstadoAsignaturaEnum getEstado() {
		return estado;
	}

	public void setEstado(EstadoAsignaturaEnum estado) {
		this.estado = estado;
	}

	public Boolean getAplicaEspacioSecundario() {
		return aplicaEspacioSecundario;
	}

	public void setAplicaEspacioSecundario(Boolean aplicaEspacioSecundario) {
		this.aplicaEspacioSecundario = aplicaEspacioSecundario;
	}
}