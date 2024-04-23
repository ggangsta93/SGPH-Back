package co.edu.unicauca.sgph.usuario.infrastructure.output.persistence.entity;

public enum EstadoUsuarioEnum {
	/** ACTIVO */
	ACTIVO("EstadoUsuarioEnum.ACTIVO"),
	/** INACTIVO */
	INACTIVO("EstadoUsuarioEnum.INACTIVO");

	/**
	 * Atributo que determina la descripción del tipo de estado
	 */
	private final String descripcionEstado;

	private EstadoUsuarioEnum(String descripcionEstado) {
		this.descripcionEstado = descripcionEstado;
	}

	public String getDescripcionEstado() {
		return descripcionEstado;
	}
}