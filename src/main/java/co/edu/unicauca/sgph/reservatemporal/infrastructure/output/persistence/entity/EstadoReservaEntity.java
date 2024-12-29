package co.edu.unicauca.sgph.reservatemporal.infrastructure.output.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ESTADO_RESERVA")
public class EstadoReservaEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ESTADO", nullable = false)
    private Long idEstado;

	@NotNull
    @Column(name = "DESCRIPCION")
    @Enumerated(EnumType.STRING)
    private EstadoReservaEnum descripcion;

	public Long getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(Long idEstado) {
		this.idEstado = idEstado;
	}

	public EstadoReservaEnum getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(EstadoReservaEnum descripcion) {
		this.descripcion = descripcion;
	}
	
	
}
