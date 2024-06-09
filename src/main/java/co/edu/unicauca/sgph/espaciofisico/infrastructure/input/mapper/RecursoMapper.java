package co.edu.unicauca.sgph.espaciofisico.infrastructure.input.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import co.edu.unicauca.sgph.espaciofisico.domain.model.RecursoEspacioFisico;
import co.edu.unicauca.sgph.espaciofisico.infrastructure.input.DTOResponse.RecursoOutDTO;

@Mapper(componentModel = "spring")
public interface RecursoMapper {
    RecursoMapper INSTANCE = Mappers.getMapper(RecursoMapper.class);

    @Mapping(target = "idRecurso", source = "recursoFisico.idRecursoFisico")
    @Mapping(target = "nombre", source = "recursoFisico.nombre")
    @Mapping(target = "descripcion", source = "recursoFisico.descripcion")
    @Mapping(target = "cantidad", source = "cantidad")
    RecursoOutDTO toRecursoOutDTO(RecursoEspacioFisico recursoEspacioFisico);
}
