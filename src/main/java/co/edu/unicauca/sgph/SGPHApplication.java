package co.edu.unicauca.sgph;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import co.edu.unicauca.sgph.asignatura.infrastructure.input.controller.AsignaturaController;
import co.edu.unicauca.sgph.curso.infrastructure.input.controller.CursoController;
import co.edu.unicauca.sgph.docente.infrastructure.input.controller.DocenteController;
import co.edu.unicauca.sgph.edificio.infrastructure.input.controller.EdificioController;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.controller.AulaController;
import co.edu.unicauca.sgph.facultad.infrastructure.input.controller.FacultadRestController;
import co.edu.unicauca.sgph.horario.infrastructure.input.controller.HorarioController;
import co.edu.unicauca.sgph.programa.infrastructure.input.controller.ProgramaController;


@SpringBootApplication
//@ComponentScan(basePackages = {"co.edu.unicauca.sgph"})
public class SGPHApplication implements CommandLineRunner {
	
	
	private final FacultadRestController facultadController;
	private final ProgramaController programaController;
	private final AsignaturaController asignaturaController;
	private final CursoController cursoController;
	private final DocenteController docenteController;
	private final HorarioController horarioController;
	private final AulaController aulaController;
	private final EdificioController edificioController;
	private Integer contadorCruces = 0;
	private List<String> detallesCruces = new ArrayList<String>();

	public static void main(String[] args) {
		SpringApplication.run(SGPHApplication.class, args);
		System.out.println("Application running");
	}

	public SGPHApplication(FacultadRestController facultadController, ProgramaController programaController,
			AsignaturaController asignaturaController, CursoController cursoController,
			DocenteController docenteController, HorarioController horarioController, AulaController aulaController,
			EdificioController edificioController) {
		super();
		this.facultadController = facultadController;
		this.programaController = programaController;
		this.asignaturaController = asignaturaController;
		this.cursoController = cursoController;
		this.docenteController = docenteController;
		this.horarioController = horarioController;
		this.aulaController = aulaController;
		this.edificioController = edificioController;
	}

	@Override
	public void run(String... args) throws Exception {
		//this.cargueArchivo();
		// this.procesamientoManual();

	}

}
