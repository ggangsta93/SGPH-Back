package co.edu.unicauca.sgph.aula.infrastructure.output.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "RECURSO_AULA")
public class RecursoAulaEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RECURSO_AULA")
    private Long idRecursoAula;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "ID_AULA")
	private AulaEntity aula;

	@ManyToOne
	@JoinColumn(name = "ID_RECURSO_FISICO")
	private RecursoFisicoEntity recursoFisico;

	@Column(name = "CANTIDAD")
	private Long cantidad;
	
	public AulaEntity getAula() {
		return aula;
	}

	public void setAula(AulaEntity aula) {
		this.aula = aula;
	}

	public RecursoFisicoEntity getRecursoFisico() {
		return recursoFisico;
	}

	public void setRecursoFisico(RecursoFisicoEntity recursoFisico) {
		this.recursoFisico = recursoFisico;
	}

	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}

	public Long getIdRecursoAula() {
		return idRecursoAula;
	}

	public void setIdRecursoAula(Long idRecursoAula) {
		this.idRecursoAula = idRecursoAula;
	}
}