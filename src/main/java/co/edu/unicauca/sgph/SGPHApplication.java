package co.edu.unicauca.sgph;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import co.edu.unicauca.sgph.asignatura.infrastructure.input.controller.AsignaturaController;
import co.edu.unicauca.sgph.curso.infrastructure.input.controller.CursoController;
import co.edu.unicauca.sgph.docente.infrastructure.input.controller.DocenteController;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTORequest.EspacioFisicoInDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.EspacioFisicoOutDTO;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.controller.EspacioFisicoController;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.output.persistence.entity.EspacioFisicoEntity;
import co.edu.unicauca.sgph.facultad.infrastructure.input.controller.FacultadRestController;
import co.edu.unicauca.sgph.gestionplanificacion.labordocencia.infrastructure.input.controller.LaborDocenciaController;
import co.edu.unicauca.sgph.gestionplanificacion.labordocencia.infrastructure.output.persistence.entity.LaborDocenciaEntity;
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
	private final EspacioFisicoController espacioFisicoController;
	private final LaborDocenciaController laborDocenciaController;

	private Integer contadorCruces = 0;
	private List<String> detallesCruces = new ArrayList<String>();

	public static void main(String[] args) {
		SpringApplication.run(SGPHApplication.class, args);
		System.out.println("Application running");
	}

	public SGPHApplication(FacultadRestController facultadController, ProgramaController programaController,
			AsignaturaController asignaturaController, CursoController cursoController,
			DocenteController docenteController, HorarioController horarioController,
			EspacioFisicoController espacioFisicoController,
			LaborDocenciaController laborDocenciaController) {
		super();
		this.facultadController = facultadController;
		this.programaController = programaController;
		this.asignaturaController = asignaturaController;
		this.cursoController = cursoController;
		this.docenteController = docenteController;
		this.horarioController = horarioController;
		this.espacioFisicoController = espacioFisicoController;
		this.laborDocenciaController = laborDocenciaController;
	}

	@Override
	public void run(String... args) throws Exception {
		//this.cargarLaborDocencia();
		//this.cargarEspaciosFisicos();		
	}
	
	private void cargarEspaciosFisicos() {
		String filePath = "Y:/ARCHIVOS DE LAS TICs/UbicacionSalones.xlsx";

		try (Workbook workbook = WorkbookFactory.create(new FileInputStream(new File(filePath)))) {
            Sheet sheet = workbook.getSheet("SalonesUbicacion");
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    // Saltar la primera fila que generalmente contiene los encabezados
                    continue;
                }
                
                EspacioFisicoInDTO espacioFisico=new EspacioFisicoInDTO();
                espacioFisico.setOID(String.format("%d", (long) row.getCell(0).getNumericCellValue()));
                espacioFisico.setSalon(String.valueOf(row.getCell(2)));
                espacioFisico.setCapacidad((long)row.getCell(3).getNumericCellValue());
                espacioFisico.setUbicacion(String.valueOf(row.getCell(5)));
                espacioFisico.setLstIdAgrupadorEspacioFisico(new ArrayList<>());
                espacioFisico.setIdTipoEspacioFisico(1L);
                espacioFisico.setMunicipio(String.valueOf(row.getCell(7)));
                EspacioFisicoOutDTO espacioFisicoOutDTO =  espacioFisicoController.guardarEspacioFisico(espacioFisico);
                
               /* LaborDocenciaEntity laborDocenciaEntity = new LaborDocenciaEntity();
                laborDocenciaEntity.setOidPeriodo((long) row.getCell(0).getNumericCellValue());
                laborDocenciaEntity.setPeriodo(row.getCell(1).getStringCellValue());
                laborDocenciaEntity.setNumeroIdentificacion(row.getCell(2).getStringCellValue());
                laborDocenciaEntity.setPrimerApellido(row.getCell(3).getStringCellValue());
                laborDocenciaEntity.setSegundoApellido(row.getCell(4).getStringCellValue());
                laborDocenciaEntity.setPrimerNombre(row.getCell(5).getStringCellValue());
                laborDocenciaEntity.setSegundoNombre(row.getCell(6).getStringCellValue());
                laborDocenciaEntity.setCorreo(row.getCell(7).getStringCellValue());

                if (row.getLastCellNum() > 8) {
                    laborDocenciaEntity.setNombreMateria(row.getCell(8).getStringCellValue());
                    laborDocenciaEntity.setNombrePrograma(row.getCell(9).getStringCellValue());
                    laborDocenciaEntity.setOidMateria(row.getCell(10).getStringCellValue());
                    laborDocenciaEntity.setCodigoMateria(row.getCell(11).getStringCellValue());
                    laborDocenciaEntity.setTipoMateria((long) row.getCell(12).getNumericCellValue());
                    laborDocenciaEntity.setGrupo(row.getCell(13).getStringCellValue());
                    laborDocenciaEntity.setHorasTeoricas(row.getCell(14).getStringCellValue());
                }

                laborDocenciaController.cargarLaboresDocente(laborDocenciaEntity);*/
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
	}
	
	private void cargarLaborDocencia() {
		String filePath = "C:/Users/parias/Desktop/LaborAcademica2023-2024.csv";

		try (Scanner scanner = new Scanner(new File(filePath))) {
			scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] campos = line.split(";");
                
                for (int i = 0; i < campos.length; i++) {
                    campos[i] = campos[i].replace("\"", "");
                }                

                
                LaborDocenciaEntity laborDocenciaEntity = new LaborDocenciaEntity();
                laborDocenciaEntity.setOidPeriodo(Long.parseLong(campos[0]));
                laborDocenciaEntity.setPeriodo(campos[1]);
                laborDocenciaEntity.setNumeroIdentificacion(campos[2]);
                laborDocenciaEntity.setPrimerApellido(campos[3]);
                laborDocenciaEntity.setSegundoApellido(campos[4]);
                laborDocenciaEntity.setPrimerNombre(campos[5]);
                laborDocenciaEntity.setSegundoNombre(campos[6]);
                laborDocenciaEntity.setCorreo(campos[7]);
                
                if(campos.length>8) {
                	laborDocenciaEntity.setNombreMateria(campos[8]);
                	laborDocenciaEntity.setNombrePrograma(campos[9]);
                	laborDocenciaEntity.setOidMateria(campos[10]);
                	laborDocenciaEntity.setCodigoMateria(campos[11]);
                	laborDocenciaEntity.setTipoMateria(Long.parseLong(campos[12]));
                	laborDocenciaEntity.setGrupo(campos[13]);
                	laborDocenciaEntity.setHorasTeoricas(campos[14]);                	
                }
                
                laborDocenciaController.cargarLaboresDocente(laborDocenciaEntity);
               
            }
        } catch (FileNotFoundException e) {
            System.err.println("Archivo no encontrado: " + e.getMessage());
        }
	}

}
