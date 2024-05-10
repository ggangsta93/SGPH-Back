package co.edu.unicauca.sgph.asignatura.infrastructure.input.DTOResponse;

import java.util.List;

public class AgrupadorEspacioFisicoDTO {
	private String nombreFacultad;
    private Long idFacultad;
    private Long idAgrupador;
    private String nombreAgrupador;

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

    public Long getIdAgrupador() {
        return idAgrupador;
    }

    public void setIdAgrupador(Long idAgrupador) {
        this.idAgrupador = idAgrupador;
    }

    public String getNombreAgrupador() {
        return nombreAgrupador;
    }

    public void setNombreAgrupador(String nombreAgrupador) {
        this.nombreAgrupador = nombreAgrupador;
    }
}