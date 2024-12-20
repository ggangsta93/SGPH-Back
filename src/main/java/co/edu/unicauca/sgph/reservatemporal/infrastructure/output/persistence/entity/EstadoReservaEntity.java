package co.edu.unicauca.sgph.reservatemporal.infrastructure.output.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ESTADO_RESERVA")
public class EstadoReservaEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ESTADO", nullable = false)
    private Long idEstado;

    @Column(name = "DESCRIPCION", nullable = false, unique = true)
    private String descripcion;
}
