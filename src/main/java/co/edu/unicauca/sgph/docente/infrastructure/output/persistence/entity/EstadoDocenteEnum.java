package co.edu.unicauca.sgph.docente.infrastructure.output.persistence.entity;

public enum EstadoDocenteEnum {
	/** ACTIVO */
	ACTIVO("EstadoDocenteEnum.ACTIVO"),
	/** INACTIVO */
	INACTIVO("EstadoDocenteEnum.INACTIVO");

	/**
	 * Atributo que determina la descripción del tipo de estado
	 */
	private final String descripcionEstado;

	private EstadoDocenteEnum(String descripcionEstado) {
		this.descripcionEstado = descripcionEstado;
	}

	public String getDescripcionEstado() {
		return descripcionEstado;
	}
}