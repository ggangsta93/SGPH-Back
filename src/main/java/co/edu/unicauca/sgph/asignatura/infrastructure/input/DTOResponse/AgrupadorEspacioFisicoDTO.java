package co.edu.unicauca.sgph.asignatura.infrastructure.input.DTOResponse;

import java.util.List;

public class AgrupadorEspacioFisicoDTO {
	private String nombreFacultad;
    private Long idFacultad;
    private Long idAgrupadorEspacioFisico;
    private String nombre;

    public AgrupadorEspacioFisicoDTO() {
    }

    public String getNombreFacultad() {
        return nombreFacultad;
    }

    public void setNombreFacultad(String nombreFacultad) {
        this.nombreFacultad = nombreFacultad;
    }

    public Long getIdFacultad() {
        return idFacultad;
    }

    public void setIdFacultad(Long idFacultad) {
        this.idFacultad = idFacultad;
    }

    public Long getIdAgrupadorEspacioFisico() {
        return idAgrupadorEspacioFisico;
    }

    public void setIdAgrupadorEspacioFisico(Long idAgrupador) {
        this.idAgrupadorEspacioFisico = idAgrupador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombreAgrupador) {
        this.nombre = nombreAgrupador;
    }
}