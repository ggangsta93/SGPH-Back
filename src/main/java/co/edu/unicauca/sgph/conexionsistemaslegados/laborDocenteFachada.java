package co.edu.unicauca.sgph.conexionsistemaslegados;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import co.edu.unicauca.sgph.docente.infrastructure.input.DTORequest.DocenteLaborDTO;
import co.edu.unicauca.sgph.gestionplanificacion.labordocencia.aplication.output.GestionarLaborDocenciaGatewayIntPort;

@Service
public class laborDocenteFachada implements  GestionarLaborDocenciaGatewayIntPort {

	
	private static final String API_URL_AUTENTICACION= "https://unicauca.typicode.com/posts";
    private static final String API_URL_LABOR_DOCENTE = "https://unicauca.typicode.com/posts";
	
	private final RestTemplate restTemplate;		
	
	public laborDocenteFachada(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}


	@Override
	public List<DocenteLaborDTO> consultarLaborDocente(String nombrePrograma, String periodoVigente) throws IOException {
		 
	    
	    
	    
	    return this.cargarLaborDocente(nombrePrograma, periodoVigente);
	}
	
	
	
	public List<DocenteLaborDTO> cargarLaborDocente(String nombrePrograma, String periodoVigente) throws IOException {
		String filePath = "Y:/ARCHIVOS DE LAS TICs/LaborAcademica2023-2024.xlsx";

        FileInputStream fileInputStream = new FileInputStream(filePath);
        Workbook workbook = null;

        // Determina el tipo de archivo
        if (filePath.toLowerCase().endsWith("xlsx") || filePath.toLowerCase().endsWith("xls")) {
            workbook = new XSSFWorkbook(fileInputStream);
        } 
        if (workbook == null) {
            throw new IOException("El archivo no es un archivo Excel válido.");
        }
		Sheet sheet = workbook.getSheetAt(0);
		List<DocenteLaborDTO> docenteLaborDTOList = new ArrayList<>();

		for (Row row : sheet) {
			try {
				
				if (row.getRowNum() == 0) continue; // Skip header row
				DocenteLaborDTO docente = new DocenteLaborDTO();
				
				if(nombrePrograma.equalsIgnoreCase(getStringCellValue(row, 9)) && periodoVigente.equals(getStringCellValue(row, 1))) {
					docente.setNombrePrograma(getStringCellValue(row, 9));
					docente.setOidPeriodo((int) row.getCell(0).getNumericCellValue());
					docente.setPeriodo(getStringCellValue(row, 1));
					docente.setIdentificacion(String.valueOf(row.getCell(2).getNumericCellValue()));
					docente.setPrimerApellido(getStringCellValue(row, 3));
					docente.setSegundoApellido(getStringCellValue(row, 4));
					docente.setPrimerNombre(getStringCellValue(row, 5));
					docente.setSegundoNombre(getStringCellValue(row, 6));
					docente.setCorreo(getStringCellValue(row, 7));
					docente.setNombreMateria(getStringCellValue(row, 8));					
					docente.setOid(String.valueOf(row.getCell(10)==null? null:row.getCell(10)));
					//Se quitan las comas a los OID
					docente.setOid(docente.getOid()==null? null: docente.getOid().replace(",","") );					
					docente.setCodigo(String.valueOf(row.getCell(11)==null? null:row.getCell(11)));
					docente.setTipoMateria(String.valueOf(getIntCellValue(row, 12)));
					docente.setGrupo(String.valueOf(row.getCell(13)==null? null:row.getCell(13)));

					//docente.setHorasTeoricas(String.valueOf(getIntCellValue(row, 14))); //La hora teorica lo tiene la asignatura
					docenteLaborDTOList.add(docente);
					
					
				}
			} catch (Exception e) {
				int i = row.getRowNum();
			}
		}

		workbook.close();
		return docenteLaborDTOList;
	}
	
	private static String getStringCellValue(Row row, int cellIndex) {
		Cell cell = row.getCell(cellIndex);
		return cell != null ? cell.getStringCellValue() : "";
	}
	
	private static int getIntCellValue(Row row, int cellIndex) {
		Cell cell = row.getCell(cellIndex);
		return cell != null ? (int) cell.getNumericCellValue() : 0;
	}

}
