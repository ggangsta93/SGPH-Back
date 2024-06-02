package co.edu.unicauca.sgph.reporte.infraestructure.input.DTO;

import co.edu.unicauca.sgph.horario.infrastructure.input.DTORequest.HorarioInDTO;

import java.util.List;

public class ReporteSimcaDTO {
    private String periodo;
    private String abreviaturaPrograma;
    private Integer semestre;
    private String OIDAsignatura;
    private String CodigoAsignatura;
    private String nombreAsignatura;
    private String nombreGrupo;
    private Integer cupo;
    private Long matriculados;
    private List<HorarioDTO> horarios;
    private String archivoBase64;
    private String nombreDocente;
    private Long idPeriodo;
    private Long idPrograma;
    private Long idFacultad;

    public Integer getCupo() {
        return cupo;
    }

    public void setCupo(Integer cupo) {
        this.cupo = cupo;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getAbreviaturaPrograma() {
        return abreviaturaPrograma;
    }

    public void setAbreviaturaPrograma(String abreviaturaPrograma) {
        this.abreviaturaPrograma = abreviaturaPrograma;
    }


    public String getOIDAsignatura() {
        return OIDAsignatura;
    }

    public void setOIDAsignatura(String OIDAsignatura) {
        this.OIDAsignatura = OIDAsignatura;
    }

    public String getCodigoAsignatura() {
        return CodigoAsignatura;
    }

    public void setCodigoAsignatura(String codigoAsignatura) {
        CodigoAsignatura = codigoAsignatura;
    }

    public String getNombreAsignatura() {
        return nombreAsignatura;
    }

    public void setNombreAsignatura(String nombreAsignatura) {
        this.nombreAsignatura = nombreAsignatura;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public Long getMatriculados() {
        return matriculados;
    }

    public void setMatriculados(Long matriculados) {
        this.matriculados = matriculados;
    }

    public List<HorarioDTO> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<HorarioDTO> horarios) {
        this.horarios = horarios;
    }

    public Integer getSemestre() {
        return semestre;
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }

    public String getArchivoBase64() {
        return archivoBase64;
    }

    public void setArchivoBase64(String archivoBase64) {
        this.archivoBase64 = archivoBase64;
    }

    public Long getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(Long idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public Long getIdPrograma() {
        return idPrograma;
    }

    public void setIdPrograma(Long idPrograma) {
        this.idPrograma = idPrograma;
    }

    public Long getIdFacultad() {
        return idFacultad;
    }

    public void setIdFacultad(Long idFacultad) {
        this.idFacultad = idFacultad;
    }

    public String getNombreDocente() {
        return nombreDocente;
    }

    public void setNombreDocente(String nombreDocente) {
        this.nombreDocente = nombreDocente;
    }
}
