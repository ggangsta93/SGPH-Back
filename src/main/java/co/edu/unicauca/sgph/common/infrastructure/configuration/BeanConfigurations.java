package co.edu.unicauca.sgph.common.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.edu.unicauca.sgph.asignatura.aplication.output.AsignaturaFormatterResultsIntPort;
import co.edu.unicauca.sgph.asignatura.aplication.output.GestionarAsignaturaGatewayIntPort;
import co.edu.unicauca.sgph.asignatura.domain.useCase.GestionarAsignaturaCUAdapter;
import co.edu.unicauca.sgph.aula.aplication.output.AulaFormatterResultsIntPort;
import co.edu.unicauca.sgph.aula.aplication.output.GestionarAulaGatewayIntPort;
import co.edu.unicauca.sgph.aula.aplication.output.GestionarTipoAulaGatewayIntPort;
import co.edu.unicauca.sgph.aula.aplication.output.TipoAulaFormatterResultsIntPort;
import co.edu.unicauca.sgph.aula.domain.useCase.GestionarAulaCUAdapter;
import co.edu.unicauca.sgph.aula.domain.useCase.GestionarTipoAulaCUAdapter;
import co.edu.unicauca.sgph.curso.aplication.output.CursoFormatterResultsIntPort;
import co.edu.unicauca.sgph.curso.aplication.output.GestionarCursoGatewayIntPort;
import co.edu.unicauca.sgph.curso.domain.useCase.GestionarCursoCUAdapter;
import co.edu.unicauca.sgph.docente.aplication.output.DocenteFormatterResultsIntPort;
import co.edu.unicauca.sgph.docente.aplication.output.GestionarDocenteGatewayIntPort;
import co.edu.unicauca.sgph.docente.domain.useCase.GestionarDocenteCUAdapter;
import co.edu.unicauca.sgph.edificio.aplication.output.EdificioFormatterResultsIntPort;
import co.edu.unicauca.sgph.edificio.aplication.output.GestionarEdificioGatewayIntPort;
import co.edu.unicauca.sgph.edificio.domain.useCase.GestionarEdificioCUAdapter;
import co.edu.unicauca.sgph.facultad.aplication.output.FacultadFormatterResultsIntPort;
import co.edu.unicauca.sgph.facultad.aplication.output.GestionarFacultadGatewayIntPort;
import co.edu.unicauca.sgph.facultad.domain.useCase.GestionarFacultadCUAdapter;
import co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.output.GestionarPlanificacionManualGatewayIntPort;
import co.edu.unicauca.sgph.gestionplanificacion.manual.domain.useCase.GestionarPlanificacionManualCUAdapter;
import co.edu.unicauca.sgph.gestionplanificacion.periodoacademico.aplication.output.GestionarPeriodoAcademicoGatewayIntPort;
import co.edu.unicauca.sgph.gestionplanificacion.periodoacademico.aplication.output.PeriodoAcademicoFormatterResultsIntPort;
import co.edu.unicauca.sgph.gestionplanificacion.periodoacademico.domain.useCase.GestionarPeriodoAcademicoCUAdapter;
import co.edu.unicauca.sgph.horario.aplication.output.GestionarHorarioGatewayIntPort;
import co.edu.unicauca.sgph.horario.aplication.output.HorarioFormatterResultsIntPort;
import co.edu.unicauca.sgph.horario.domain.useCase.GestionarHorarioCUAdapter;
import co.edu.unicauca.sgph.programa.aplication.output.GestionarProgramaGatewayIntPort;
import co.edu.unicauca.sgph.programa.aplication.output.ProgramaFormatterResultsIntPort;
import co.edu.unicauca.sgph.programa.domain.useCase.GestionarProgramaCUAdapter;
import co.edu.unicauca.sgph.seguridad.usuario.aplication.output.GestionarUsuarioGatewayIntPort;
import co.edu.unicauca.sgph.seguridad.usuario.aplication.output.UsuarioFormatterResultsIntPort;
import co.edu.unicauca.sgph.seguridad.usuario.domain.useCase.GestionarUsuarioCUAdapter;

@Configuration
public class BeanConfigurations {

	@Bean
	GestionarFacultadCUAdapter crearGestionarFacultadCUInt(
			GestionarFacultadGatewayIntPort gestionarFacultadGatewayIntPort,
			FacultadFormatterResultsIntPort facultadFormatterResultsIntPort) {
		return new GestionarFacultadCUAdapter(gestionarFacultadGatewayIntPort, facultadFormatterResultsIntPort);
	}

	@Bean
	GestionarProgramaCUAdapter crearGestionarProgramaCUInt(
			GestionarProgramaGatewayIntPort gestionarProgramaGatewayIntPort,
			ProgramaFormatterResultsIntPort programaFormatterResultsIntPort) {
		return new GestionarProgramaCUAdapter(gestionarProgramaGatewayIntPort, programaFormatterResultsIntPort);
	}

	@Bean
	GestionarAsignaturaCUAdapter crearGestionarAsignaturaCUInt(
			GestionarAsignaturaGatewayIntPort gestionarProgramaGatewayIntPort,
			AsignaturaFormatterResultsIntPort asignaturaFormatterResultsIntPort) {
		return new GestionarAsignaturaCUAdapter(gestionarProgramaGatewayIntPort, asignaturaFormatterResultsIntPort);
	}

	@Bean
	GestionarDocenteCUAdapter crearGestionarDocenteCUInt(GestionarDocenteGatewayIntPort gestionarDocenteGatewayIntPort,
			DocenteFormatterResultsIntPort docenteFormatterResultsIntPort) {
		return new GestionarDocenteCUAdapter(gestionarDocenteGatewayIntPort, docenteFormatterResultsIntPort);
	}

	@Bean
	GestionarCursoCUAdapter crearGestionarCursoCUInt(GestionarCursoGatewayIntPort gestionarCursoGatewayIntPort,
			CursoFormatterResultsIntPort cursoFormatterResultsIntPort) {
		return new GestionarCursoCUAdapter(gestionarCursoGatewayIntPort, cursoFormatterResultsIntPort);
	}

	@Bean
	GestionarHorarioCUAdapter crearGestionarHorarioCUInt(GestionarHorarioGatewayIntPort gestionarHorarioGatewayIntPort,
			HorarioFormatterResultsIntPort horarioFormatterResultsIntPort) {
		return new GestionarHorarioCUAdapter(gestionarHorarioGatewayIntPort, horarioFormatterResultsIntPort);
	}

	@Bean
	GestionarAulaCUAdapter crearGestionarAulaCUInt(GestionarAulaGatewayIntPort gestionarAulaGatewayIntPort,
			AulaFormatterResultsIntPort aulaFormatterResultsIntPort) {
		return new GestionarAulaCUAdapter(gestionarAulaGatewayIntPort, aulaFormatterResultsIntPort);
	}

	@Bean
	GestionarTipoAulaCUAdapter crearGestionarTipoAulaCUInt(
			GestionarTipoAulaGatewayIntPort gestionarTipoAulaGatewayIntPort,
			TipoAulaFormatterResultsIntPort tipoAulaFormatterResultsIntPort) {
		return new GestionarTipoAulaCUAdapter(gestionarTipoAulaGatewayIntPort, tipoAulaFormatterResultsIntPort);
	}

	@Bean
	GestionarEdificioCUAdapter crearGestionarEdificioCUInt(
			GestionarEdificioGatewayIntPort gestionarEdificioGatewayIntPort,
			EdificioFormatterResultsIntPort edificioFormatterResultsIntPort) {
		return new GestionarEdificioCUAdapter(gestionarEdificioGatewayIntPort, edificioFormatterResultsIntPort);
	}
	
	@Bean
	GestionarUsuarioCUAdapter crearGestionarUsuarioCUInt(GestionarUsuarioGatewayIntPort gestionarUsuarioGatewayIntPort,
			UsuarioFormatterResultsIntPort usuarioFormatterResultsIntPort) {
		return new GestionarUsuarioCUAdapter(gestionarUsuarioGatewayIntPort, usuarioFormatterResultsIntPort);	
	}
	
	@Bean
	GestionarPlanificacionManualCUAdapter crearGestionarPlanificacionManualCUInt(
			GestionarPlanificacionManualGatewayIntPort gestionarPlanificacionManualGatewayIntPort,
			CursoFormatterResultsIntPort cursoFormatterResultsIntPort,
			GestionarDocenteGatewayIntPort gestionarDocenteGatewayIntPort,
			GestionarAulaGatewayIntPort gestionarAulaGatewayIntPort,
			GestionarCursoGatewayIntPort gestionarCursoGatewayIntPort,
			GestionarHorarioGatewayIntPort gestionarHorarioGatewayIntPort) {
		return new GestionarPlanificacionManualCUAdapter(gestionarPlanificacionManualGatewayIntPort,
				cursoFormatterResultsIntPort, gestionarDocenteGatewayIntPort, gestionarAulaGatewayIntPort,
				gestionarCursoGatewayIntPort, gestionarHorarioGatewayIntPort);
	}
	
	@Bean
	GestionarPeriodoAcademicoCUAdapter crearGestionarPeriodoAcademicoCUInt(
			GestionarPeriodoAcademicoGatewayIntPort gestionarPeriodoAcademicoGatewayIntPort,
			PeriodoAcademicoFormatterResultsIntPort periodoAcademicoFormatterResultsIntPort) {
		return new GestionarPeriodoAcademicoCUAdapter(gestionarPeriodoAcademicoGatewayIntPort,
				periodoAcademicoFormatterResultsIntPort);
	}
	
}