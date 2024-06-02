package co.edu.unicauca.sgph.reporte.infraestructure.input.controller;

import co.edu.unicauca.sgph.asignatura.aplication.input.GestionarAsignaturaCUIntPort;
import co.edu.unicauca.sgph.asignatura.infrastructure.input.mapper.AsignaturaRestMapper;
import co.edu.unicauca.sgph.programa.aplication.input.GestionarProgramaCUIntPort;
import co.edu.unicauca.sgph.programa.infrastructure.input.DTORequest.ProgramaInDTO;
import co.edu.unicauca.sgph.programa.infrastructure.input.DTOResponse.ProgramaOutDTO;
import co.edu.unicauca.sgph.programa.infrastructure.input.mapper.ProgramaRestMapper;
import co.edu.unicauca.sgph.reporte.aplication.input.GestionarReporteIntPort;
import co.edu.unicauca.sgph.reporte.domain.useCase.GestionarReporteCUAdapter;
import co.edu.unicauca.sgph.reporte.infraestructure.input.DTO.ReporteSimcaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
