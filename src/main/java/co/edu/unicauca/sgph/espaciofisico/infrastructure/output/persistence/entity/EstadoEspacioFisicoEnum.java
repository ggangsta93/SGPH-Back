package co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity;

public enum EstadoEspacioFisicoEnum {
	/** ACTIVO */
	ACTIVO("EstadoDocenteEnum.ACTIVO"),
	/** INACTIVO */
	INACTIVO("EstadoDocenteEnum.INACTIVO");

	/**
	 * Atributo que determina la descripción del tipo de estado
	 */
	private final String descripcionEstado;

	private EstadoEspacioFisicoEnum(String descripcionEstado) {
		this.descripcionEstado = descripcionEstado;
	}

	public String getDescripcionEstado() {
		return descripcionEstado;
	}
}