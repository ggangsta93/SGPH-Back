package co.edu.unicauca.sgph.horario.infrastructure.input.DTORequest;

public class QRRequestDTO {
	private String nombre;
    private String qrData;
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getQrData() {
		return qrData;
	}
	public void setQrData(String qrData) {
		this.qrData = qrData;
	}
}
