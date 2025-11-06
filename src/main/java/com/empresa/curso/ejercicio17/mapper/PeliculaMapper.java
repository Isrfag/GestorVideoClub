package com.empresa.curso.ejercicio17.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.empresa.curso.ejercicio17.dto.CopiaDto;
import com.empresa.curso.ejercicio17.dto.PeliculaDto;
import com.empresa.curso.ejercicio17.entity.Copia;
import com.empresa.curso.ejercicio17.entity.Pelicula;

public class PeliculaMapper {
     public static PeliculaDto toDto(Pelicula pelicula) {
        if (pelicula == null) return null;

        PeliculaDto dto = new PeliculaDto();
        dto.setId(pelicula.getId());
        dto.setTitulo(pelicula.getTitulo());
        dto.setGenero(pelicula.getGenero());
        dto.setFechaEstreno(pelicula.getFechaEstreno());
        dto.setDirector(pelicula.getDirector());
        dto.setDuracion(pelicula.getDuracion());

        List<CopiaDto> copiasDto = pelicula.getCopias().stream()
                .map(CopiaMapper::toDto)  
                .collect(Collectors.toList());
        dto.setCopias(copiasDto);

        return dto;
    }

    public static Pelicula toEntity(PeliculaDto dto) {
        if (dto == null) return null;

        Pelicula pelicula = new Pelicula();
        pelicula.setId(dto.getId());
        pelicula.setTitulo(dto.getTitulo());
        pelicula.setGenero(dto.getGenero());
        pelicula.setFechaEstreno(dto.getFechaEstreno());
        pelicula.setDirector(dto.getDirector());
        pelicula.setDuracion(dto.getDuracion());

        List<Copia> copias = dto.getCopias().stream()
                .map(CopiaMapper::toEntity)
                .collect(Collectors.toList());
        pelicula.setCopias(copias);

        return pelicula;
    }

    public static List<PeliculaDto> toDtoList(List<Pelicula> peliculas) {
        return peliculas.stream().map(PeliculaMapper::toDto).collect(Collectors.toList());
    }

    public static List<Pelicula> toEntityList(List<PeliculaDto> dtos) {
        return dtos.stream().map(PeliculaMapper::toEntity).collect(Collectors.toList());
    }
}
