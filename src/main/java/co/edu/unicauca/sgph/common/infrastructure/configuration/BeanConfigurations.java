package co.edu.unicauca.sgph.common.infrastructure.configuration;

import co.edu.unicauca.sgph.asignatura.aplication.input.GestionarAsignaturaCUIntPort;
import co.edu.unicauca.sgph.curso.aplication.input.GestionarCursoCUIntPort;
import co.edu.unicauca.sgph.persona.aplication.input.GestionarPersonaCUIntPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.edu.unicauca.sgph.agrupador.aplication.output.AgrupadorEspacioFisicoFormatterResultsIntPort;
import co.edu.unicauca.sgph.agrupador.aplication.output.GestionarAgrupadorEspacioFisicoGatewayIntPort;
import co.edu.unicauca.sgph.agrupador.domain.useCase.GestionarAgrupadorEspacioFisicoCUAdapter;
import co.edu.unicauca.sgph.asignatura.aplication.output.AsignaturaFormatterResultsIntPort;
import co.edu.unicauca.sgph.asignatura.aplication.output.GestionarAsignaturaGatewayIntPort;
import co.edu.unicauca.sgph.asignatura.domain.useCase.GestionarAsignaturaCUAdapter;
import co.edu.unicauca.sgph.curso.aplication.output.CursoFormatterResultsIntPort;
import co.edu.unicauca.sgph.curso.aplication.output.GestionarCursoGatewayIntPort;
import co.edu.unicauca.sgph.curso.domain.useCase.GestionarCursoCUAdapter;
import co.edu.unicauca.sgph.departamento.aplication.input.GestionarDepartamentoCUIntPort;
import co.edu.unicauca.sgph.departamento.aplication.output.GestionarDepartamentoGatewayIntPort;
import co.edu.unicauca.sgph.departamento.domain.useCase.GestionarDepartamentoCUAdapter;
import co.edu.unicauca.sgph.docente.aplication.output.DocenteFormatterResultsIntPort;
import co.edu.unicauca.sgph.docente.aplication.output.GestionarDocenteGatewayIntPort;
import co.edu.unicauca.sgph.docente.domain.useCase.GestionarDocenteCUAdapter;
import co.edu.unicauca.sgph.espaciofisico.aplication.output.EspacioFisicoFormatterResultsIntPort;
import co.edu.unicauca.sgph.espaciofisico.aplication.output.GestionarEdificioGatewayIntPort;
import co.edu.unicauca.sgph.espaciofisico.aplication.output.GestionarEspacioFisicoGatewayIntPort;
import co.edu.unicauca.sgph.espaciofisico.aplication.output.GestionarTipoEspacioFisicoGatewayIntPort;
import co.edu.unicauca.sgph.espaciofisico.aplication.output.GestionarUbicacionGatewayIntPort;
import co.edu.unicauca.sgph.espaciofisico.aplication.output.TipoEspacioFisicoFormatterResultsIntPort;
import co.edu.unicauca.sgph.espaciofisico.domain.useCase.GestionarEdificioCUAdapter;
import co.edu.unicauca.sgph.espaciofisico.domain.useCase.GestionarEspacioFisicoCUAdapter;
import co.edu.unicauca.sgph.espaciofisico.domain.useCase.GestionarTipoEspacioFisicoCUAdapter;
import co.edu.unicauca.sgph.espaciofisico.domain.useCase.GestionarUbicacionCUAdapter;
import co.edu.unicauca.sgph.facultad.aplication.input.GestionarFacultadCUIntPort;
import co.edu.unicauca.sgph.facultad.aplication.output.FacultadFormatterResultsIntPort;
import co.edu.unicauca.sgph.facultad.aplication.output.GestionarFacultadGatewayIntPort;
import co.edu.unicauca.sgph.facultad.domain.useCase.GestionarFacultadCUAdapter;
import co.edu.unicauca.sgph.gestionplanificacion.labordocencia.aplication.output.GestionarLaborDocenciaGatewayIntPort;
import co.edu.unicauca.sgph.gestionplanificacion.labordocencia.domain.useCase.GestionarLaborDocenciaCUAdapter;
import co.edu.unicauca.sgph.gestionplanificacion.manual.aplication.output.GestionarPlanificacionManualGatewayIntPort;
import co.edu.unicauca.sgph.gestionplanificacion.manual.domain.useCase.GestionarPlanificacionManualCUAdapter;
import co.edu.unicauca.sgph.horario.aplication.output.GestionarHorarioGatewayIntPort;
import co.edu.unicauca.sgph.horario.aplication.output.HorarioFormatterResultsIntPort;
import co.edu.unicauca.sgph.horario.domain.useCase.GestionarHorarioCUAdapter;
import co.edu.unicauca.sgph.periodoacademico.aplication.input.GestionarPeriodoAcademicoCUIntPort;
import co.edu.unicauca.sgph.periodoacademico.aplication.output.GestionarPeriodoAcademicoGatewayIntPort;
import co.edu.unicauca.sgph.periodoacademico.aplication.output.PeriodoAcademicoFormatterResultsIntPort;
import co.edu.unicauca.sgph.periodoacademico.domain.useCase.GestionarPeriodoAcademicoCUAdapter;
import co.edu.unicauca.sgph.persona.aplication.output.GestionarPersonaGatewayIntPort;
import co.edu.unicauca.sgph.persona.aplication.output.PersonaFormatterResultsIntPort;
import co.edu.unicauca.sgph.persona.domain.useCase.GestionarPersonaCUAdapter;
import co.edu.unicauca.sgph.programa.aplication.input.GestionarProgramaCUIntPort;
import co.edu.unicauca.sgph.programa.aplication.output.GestionarProgramaGatewayIntPort;
import co.edu.unicauca.sgph.programa.aplication.output.ProgramaFormatterResultsIntPort;
import co.edu.unicauca.sgph.programa.domain.useCase.GestionarProgramaCUAdapter;
import co.edu.unicauca.sgph.usuario.aplication.output.GestionarUsuarioGatewayIntPort;
import co.edu.unicauca.sgph.usuario.aplication.output.UsuarioFormatterResultsIntPort;
import co.edu.unicauca.sgph.usuario.domain.useCase.GestionarUsuarioCUAdapter;

@Configuration
public class BeanConfigurations {

	@Bean
	GestionarFacultadCUAdapter crearGestionarFacultadCUInt(
			GestionarFacultadGatewayIntPort gestionarFacultadGatewayIntPort,
			FacultadFormatterResultsIntPort facultadFormatterResultsIntPort) {
		return new GestionarFacultadCUAdapter(gestionarFacultadGatewayIntPort, facultadFormatterResultsIntPort);
	}

	@Bean
	GestionarDepartamentoCUAdapter crearGestionarDepartamentoCUInt(
			GestionarDepartamentoGatewayIntPort gestionarDepartamentoGatewayIntPort) {
		return new GestionarDepartamentoCUAdapter(gestionarDepartamentoGatewayIntPort);
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
	GestionarPersonaCUAdapter crearGestionarPersonaCUInt(GestionarPersonaGatewayIntPort gestionarPersonaGatewayIntPort,
			PersonaFormatterResultsIntPort personaFormatterResultsIntPort) {
		return new GestionarPersonaCUAdapter(gestionarPersonaGatewayIntPort, personaFormatterResultsIntPort);
	}

	@Bean
	GestionarDocenteCUAdapter crearGestionarDocenteCUInt(
			GestionarDocenteGatewayIntPort gestionarDocenteGatewayIntPort,
			DocenteFormatterResultsIntPort docenteFormatterResultsIntPort,
			GestionarPeriodoAcademicoGatewayIntPort gestionarPeriodoAcademicoGatewayIntPort,
			GestionarCursoCUIntPort gestionarCursoCUIntPort,
			GestionarProgramaGatewayIntPort gestionarProgramaGatewayIntPort,
			GestionarAsignaturaCUIntPort gestionarAsignaturaCUIntPort,
			GestionarPersonaCUIntPort gestionarPersonaCUIntPort, 
			GestionarDepartamentoCUIntPort gestionarDepartamentoCUIntPort,
			GestionarProgramaCUIntPort gestionarProgramaCUIntPort,
			GestionarFacultadCUIntPort gestionarFacultadCUIntPort,
			RestTemplate restTemplate,  
            ObjectMapper objectMapper) {
		return new GestionarDocenteCUAdapter(
				gestionarDocenteGatewayIntPort,
				docenteFormatterResultsIntPort,
				gestionarPeriodoAcademicoGatewayIntPort,
				gestionarCursoCUIntPort,
				gestionarProgramaGatewayIntPort,
				gestionarAsignaturaCUIntPort,
				gestionarPersonaCUIntPort,
				gestionarDepartamentoCUIntPort,
				gestionarProgramaCUIntPort,
				gestionarFacultadCUIntPort,
				restTemplate, 
		        objectMapper);
	}

	@Bean
	GestionarUsuarioCUAdapter crearGestionarUsuarioCUInt(GestionarUsuarioGatewayIntPort gestionarUsuarioGatewayIntPort,
			UsuarioFormatterResultsIntPort usuarioFormatterResultsIntPort) {
		return new GestionarUsuarioCUAdapter(gestionarUsuarioGatewayIntPort, usuarioFormatterResultsIntPort);
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
	GestionarEspacioFisicoCUAdapter crearGestionarEspacioFisicoCUInt(
			GestionarEspacioFisicoGatewayIntPort gestionarEspacioFisicoGatewayIntPort,
			EspacioFisicoFormatterResultsIntPort espacioFisicoFormatterResultsIntPort) {
		return new GestionarEspacioFisicoCUAdapter(gestionarEspacioFisicoGatewayIntPort,
				espacioFisicoFormatterResultsIntPort);
	}

	@Bean
	GestionarTipoEspacioFisicoCUAdapter crearGestionarTipoEspacioFisicoCUInt(
			GestionarTipoEspacioFisicoGatewayIntPort gestionarTipoEspacioFisicoGatewayIntPort,
			TipoEspacioFisicoFormatterResultsIntPort tipoEspacioFisicoFormatterResultsIntPort) {
		return new GestionarTipoEspacioFisicoCUAdapter(gestionarTipoEspacioFisicoGatewayIntPort,
				tipoEspacioFisicoFormatterResultsIntPort);
	}

	@Bean
	GestionarEdificioCUAdapter crearGestionarEdificioCUInt(
			GestionarEdificioGatewayIntPort gestionarEdificioGatewayIntPort) {
		return new GestionarEdificioCUAdapter(gestionarEdificioGatewayIntPort);
	}

	@Bean
	GestionarUbicacionCUAdapter crearGestionarUbicacionCUInt(
			GestionarUbicacionGatewayIntPort gestionarUbicacionGatewayIntPort) {
		return new GestionarUbicacionCUAdapter(gestionarUbicacionGatewayIntPort);
	}

	@Bean
	GestionarAgrupadorEspacioFisicoCUAdapter crearGestionarAgrupadorEspacioFisicoCUInt(
			GestionarAgrupadorEspacioFisicoGatewayIntPort gestionarAgrupadorEspacioFisicoGatewayIntPort,
			AgrupadorEspacioFisicoFormatterResultsIntPort agrupadorEspacioFisicoFormatterResultsIntPort) {
		return new GestionarAgrupadorEspacioFisicoCUAdapter(gestionarAgrupadorEspacioFisicoGatewayIntPort,
				agrupadorEspacioFisicoFormatterResultsIntPort);
	}

	@Bean
	GestionarPlanificacionManualCUAdapter crearGestionarPlanificacionManualCUInt(
			GestionarPlanificacionManualGatewayIntPort gestionarPlanificacionManualGatewayIntPort,
			CursoFormatterResultsIntPort cursoFormatterResultsIntPort,
			GestionarDocenteGatewayIntPort gestionarDocenteGatewayIntPort,
			GestionarEspacioFisicoGatewayIntPort gestionarEspacioFisicoGatewayIntPort,
			GestionarCursoGatewayIntPort gestionarCursoGatewayIntPort,
			GestionarHorarioGatewayIntPort gestionarHorarioGatewayIntPort,
			GestionarPeriodoAcademicoGatewayIntPort gestionarPeriodoAcademicoGatewayIntPort,
			GestionarProgramaGatewayIntPort gestionarProgramaGatewayIntPort) {
		return new GestionarPlanificacionManualCUAdapter(gestionarPlanificacionManualGatewayIntPort,
				cursoFormatterResultsIntPort, gestionarDocenteGatewayIntPort, gestionarEspacioFisicoGatewayIntPort,
				gestionarCursoGatewayIntPort, gestionarHorarioGatewayIntPort, gestionarPeriodoAcademicoGatewayIntPort,
				gestionarProgramaGatewayIntPort);
	}

	@Bean
	GestionarPeriodoAcademicoCUAdapter crearGestionarPeriodoAcademicoCUInt(
			GestionarPeriodoAcademicoGatewayIntPort gestionarPeriodoAcademicoGatewayIntPort,
			PeriodoAcademicoFormatterResultsIntPort periodoAcademicoFormatterResultsIntPort) {
		return new GestionarPeriodoAcademicoCUAdapter(gestionarPeriodoAcademicoGatewayIntPort,
				periodoAcademicoFormatterResultsIntPort);
	}

	@Bean
	GestionarLaborDocenciaCUAdapter crearGestionarLaborDocenciaCUInt(
			GestionarLaborDocenciaGatewayIntPort gestionarLaborDocenciaGatewayIntPort,
			GestionarDocenteGatewayIntPort gestionarDocenteGatewayIntPort,
			GestionarCursoGatewayIntPort gestionarCursoGatewayIntPort,
			GestionarAsignaturaGatewayIntPort gestionarAsignaturaGatewayIntPort) {
		return new GestionarLaborDocenciaCUAdapter(gestionarLaborDocenciaGatewayIntPort, gestionarDocenteGatewayIntPort,
				gestionarCursoGatewayIntPort, gestionarAsignaturaGatewayIntPort);
	}

	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}

	
}