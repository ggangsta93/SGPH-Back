package co.edu.unicauca.sgph.aula.domain.model;

public class RecursoAula {

    private Long idRecursoAula;
	
	private Aula aula;

	private RecursoFisico recursoFisico;

	private Long cantidad;

	public Long getIdRecursoAula() {
		return idRecursoAula;
	}

	public void setIdRecursoAula(Long idRecursoAula) {
		this.idRecursoAula = idRecursoAula;
	}

	public Aula getAula() {
		return aula;
	}

	public void setAula(Aula aula) {
		this.aula = aula;
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