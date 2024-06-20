package co.edu.unicauca.sgph.asignatura.infrastructure.input.DTORequest;

public class CargaMasivaAsignaturaDTO {
    private String programa;
    private int semestre;
    private String pensum;
    private int oid;
    private String codigoAsignatura;
    private String nombre;
    private int horasSemana;
    private String estado;
    private int aplicaEspSec;

    // Constructor
    public CargaMasivaAsignaturaDTO(String programa, int semestre, String pensum, int oid, String codigoAsignatura,
                         String nombre, int horasSemana, String estado, int aplicaEspSec) {
        this.programa = programa;
        this.semestre = semestre;
        this.pensum = pensum;
        this.oid = oid;
        this.codigoAsignatura = codigoAsignatura;
        this.nombre = nombre;
        this.horasSemana = horasSemana;
        this.estado = estado;
        this.aplicaEspSec = aplicaEspSec;
    }

    // Getters and Setters
    public String getPrograma() { return programa; }
    public void setPrograma(String programa) { this.programa = programa; }

    public int getSemestre() { return semestre; }
    public void setSemestre(int semestre) { this.semestre = semestre; }

    public String getPensum() { return pensum; }
    public void setPensum(String pensum) { this.pensum = pensum; }

    public int getOid() { return oid; }
    public void setOid(int oid) { this.oid = oid; }

    public String getCodigoAsignatura() { return codigoAsignatura; }
    public void setCodigoAsignatura(String codigoAsignatura) { this.codigoAsignatura = codigoAsignatura; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getHorasSemana() { return horasSemana; }
    public void setHorasSemana(int horasSemana) { this.horasSemana = horasSemana; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public int getAplicaEspSec() { return aplicaEspSec; }
    public void setAplicaEspSec(int aplicaEspSec) { this.aplicaEspSec = aplicaEspSec; }
}
