package co.edu.unicauca.sgph.asignatura.infrastructure.output.persistence.entity;

public enum EstadoAsignaturaEnum {
	ACTIVO(1, "ACTIVO"),
	/** INACTIVO */
	INACTIVO(0, "INACTIVO");

	/**
	 * Atributo que determina el valor numérico del estado
	 */
	private final int valor;

	/**
	 * Atributo que determina la descripción del tipo de estado
	 */
	private final String descripcionEstado;

	private EstadoAsignaturaEnum(int valor, String descripcionEstado) {
		this.valor = valor;
		this.descripcionEstado = descripcionEstado;
	}

	public int getValor() {
		return valor;
	}

	public String getDescripcionEstado() {
		return descripcionEstado;
	}
}