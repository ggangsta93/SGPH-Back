package co.edu.unicauca.sgph.reporte.infraestructure.input.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.sgph.common.domain.model.CommonEJB;
import co.edu.unicauca.sgph.reporte.aplication.input.GestionarReporteIntPort;
import co.edu.unicauca.sgph.reporte.infraestructure.input.DTO.ReporteDocenteDTO;
import co.edu.unicauca.sgph.reporte.infraestructure.input.DTO.ReporteEspacioFisicoDTO;
import co.edu.unicauca.sgph.reporte.infraestructure.input.DTO.ReporteFranjasLibresDTO;
import co.edu.unicauca.sgph.reporte.infraestructure.input.DTO.ReporteSimcaDTO;

@RestController
@RequestMapping("/reporte")
public class ReporteController extends CommonEJB {
    @Autowired
    private GestionarReporteIntPort gestionarReporteIntPort;
    @PostMapping("/simca")
    public ReporteSimcaDTO obtenerReporteSimca(@RequestBody ReporteSimcaDTO reporte) {
        return this.gestionarReporteIntPort.generarReporteSimca(reporte);
    }
    @PostMapping("/laborDocente")
    public ReporteDocenteDTO obtenerReporteLaborDocente(@RequestBody ReporteDocenteDTO filtro) {
        return this.gestionarReporteIntPort.obtenerReporteLaborDocente(filtro);
    }
    
    @PostMapping("/espacioFisico")
    public ReporteEspacioFisicoDTO obtenerReporteEspacioFisico(@RequestBody ReporteEspacioFisicoDTO filtro) {
        return this.gestionarReporteIntPort.obtenerReporteHorarioEspaciosFisicos(filtro);
    }
    
    @PostMapping("/franjaLibre")
    public ReporteFranjasLibresDTO obtenerReporteFranjasLibres(@RequestBody List<ReporteFranjasLibresDTO> filtro) {
        return this.gestionarReporteIntPort.obtenerReporteHorarioFranjasLibres(filtro);
    }
}
