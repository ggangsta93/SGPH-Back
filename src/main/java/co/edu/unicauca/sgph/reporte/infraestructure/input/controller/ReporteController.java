package co.edu.unicauca.sgph.reporte.infraestructure.input.controller;

import co.edu.unicauca.sgph.reporte.infraestructure.input.DTO.ReporteDocenteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.sgph.reporte.aplication.input.GestionarReporteIntPort;
import co.edu.unicauca.sgph.reporte.infraestructure.input.DTO.ReporteSimcaDTO;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/reporte")
public class ReporteController {
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
}
