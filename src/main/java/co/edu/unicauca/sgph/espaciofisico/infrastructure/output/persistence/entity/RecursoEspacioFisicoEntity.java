package co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity;

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
@Table(name = "RECURSO_ESPACIO_FISICO")
public class RecursoEspacioFisicoEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RECURSO_ESPACIO_FISICO")
    private Long idRecursoEspacioFisico;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "ID_ESPACIO_FISICO")
	private EspacioFisicoEntity espacioFisico;

	@ManyToOne
	@JoinColumn(name = "ID_RECURSO_FISICO")
	private RecursoFisicoEntity recursoFisico;

	@Column(name = "CANTIDAD")
	private Long cantidad;
	
	public EspacioFisicoEntity getEspacioFisico() {
		return espacioFisico;
	}

	public void setEspacioFisico(EspacioFisicoEntity espacioFisico) {
		this.espacioFisico = espacioFisico;
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

	public Long getIdRecursoEspacioFisico() {
		return idRecursoEspacioFisico;
	}

	public void setIdRecursoEspacioFisico(Long idRecursoEspacioFisico) {
		this.idRecursoEspacioFisico = idRecursoEspacioFisico;
	}
}