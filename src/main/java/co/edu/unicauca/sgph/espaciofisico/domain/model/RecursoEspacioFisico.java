package co.edu.unicauca.sgph.espaciofisico.domain.model;

public class RecursoEspacioFisico {

    private Long idRecursoEspacioFisico;
	
	private EspacioFisico espacioFisico;

	private RecursoFisico recursoFisico;

	private Long cantidad;

	public Long getIdRecursoEspacioFisico() {
		return idRecursoEspacioFisico;
	}

	public void setIdRecursoEspacioFisico(Long idRecursoEspacioFisico) {
		this.idRecursoEspacioFisico = idRecursoEspacioFisico;
	}

	public EspacioFisico getEspacioFisico() {
		return espacioFisico;
	}

	public void setEspacioFisico(EspacioFisico espacioFisico) {
		this.espacioFisico = espacioFisico;
	}

	public RecursoFisico getRecursoFisico() {
		return recursoFisico;
	}

	public void setRecursoFisico(RecursoFisico recursoFisico) {
		this.recursoFisico = recursoFisico;
	}

	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}
}