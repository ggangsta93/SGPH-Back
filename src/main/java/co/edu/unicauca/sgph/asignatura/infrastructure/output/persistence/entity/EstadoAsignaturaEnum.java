package co.edu.unicauca.sgph.asignatura.infrastructure.output.persistence.entity;

public enum EstadoAsignaturaEnum {
	/** INACTIVO */
	INACTIVO("EstadoAsignaturaEnum.INACTIVO"),
	/** ACTIVO */
	ACTIVO("EstadoAsignaturaEnum.ACTIVO");

	/**
	 * Atributo que determina la descripción del tipo de estado
	 */
    private final String descripcionEstado;

    private EstadoAsignaturaEnum(String descripcionEstado) {
        this.descripcionEstado = descripcionEstado;
    }


    public String getDescripcionEstado() {
        return descripcionEstado;
    }
}