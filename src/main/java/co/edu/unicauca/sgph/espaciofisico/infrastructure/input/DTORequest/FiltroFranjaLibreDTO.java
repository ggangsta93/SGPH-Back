package co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTORequest;

public class FiltroFranjaLibreDTO {
    private String edificio;
    private String salon;
    private String dia;
    private String hora;
    private String facultad;
    private Integer capacidad;

    // Constructor vacío
    public FiltroFranjaLibreDTO() {}

    // Constructor completo
    public FiltroFranjaLibreDTO(String edificio, String salon, String dia, String hora, String facultad, Integer capacidad) {
        this.edificio = edificio;
        this.salon = salon;
        this.dia = dia;
        this.hora = hora;
        this.facultad = facultad;
        this.capacidad = capacidad;
    }

    // Getters y Setters
    public String getEdificio() {
        return edificio;
    }

    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

    public String getSalon() {
        return salon;
    }

    public void setSalon(String salon) {
        this.salon = salon;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getFacultad() {
        return facultad;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }
}

