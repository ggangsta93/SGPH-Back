package co.edu.unicauca.sgph.asignatura.infrastructure.input.DTORequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FiltroAsignaturaInDTO {
    private Integer semestre;
    private Integer pageNumber;
    private Integer pageSize;
    private List<Long> idFacultades;
    private List<Long> idProgramas;
    public Integer getSemestre() {
        return semestre;
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<Long> getIdFacultades() {
        return idFacultades;
    }

    public void setIdFacultades(List<Long> idFacultades) {
        this.idFacultades = idFacultades;
    }

    public List<Long> getIdProgramas() {
        return idProgramas;
    }

    public void setIdProgramas(List<Long> idProgramas) {
        this.idProgramas = idProgramas;
    }
}

