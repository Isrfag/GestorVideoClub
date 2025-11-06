package com.empresa.curso.ejercicio17.mapper;

import com.empresa.curso.ejercicio17.dto.CopiaDto;
import com.empresa.curso.ejercicio17.entity.Copia;

public class CopiaMapper {
    public static CopiaDto toDto(Copia copia) {
        if (copia == null) return null;

        CopiaDto dto = new CopiaDto();
        dto.setId(copia.getId());
        dto.setFormato(copia.getFormato());
        dto.setDisponible(copia.isDisponible());

        if (copia.getPelicula() != null) {
            dto.setPeliculaId(copia.getPelicula().getId());
            dto.setPeliculaTitulo(copia.getPelicula().getTitulo());
        }

        return dto;
    }

    public static Copia toEntity(CopiaDto dto) {
        if (dto == null) return null;

        Copia copia = new Copia();
        copia.setId(dto.getId());
        copia.setFormato(dto.getFormato());
        copia.setDisponible(dto.isDisponible());

        return copia;
    }
}
