package co.edu.unicauca.sgph.common.enums;

public enum TipoAulaEnum {
	SALON(1L), 
	AUDITORIO(2L),
	LABORATORIO_FISICA(3L),
	LABORATORIO_ELECTRONICA(4L), 
	SALA_TRANSMISION(5L),
	SALA_INFORMATICA_SX(6L),
	SALA_DIGITALES_TELEMATICA(7L);	

    private final Long idTipoAula;

    TipoAulaEnum(Long idTipoAula) {
        this.idTipoAula = idTipoAula;
    }

    public Long getIdTipoAula() {
        return idTipoAula;
    }
}
