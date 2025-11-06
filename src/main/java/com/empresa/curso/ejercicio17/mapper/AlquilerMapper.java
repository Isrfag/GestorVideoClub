package com.empresa.curso.ejercicio17.mapper;

import com.empresa.curso.ejercicio17.dto.AlquilerDto;
import com.empresa.curso.ejercicio17.entity.Alquiler;
import com.empresa.curso.ejercicio17.entity.Cliente;
import com.empresa.curso.ejercicio17.entity.Copia;
import com.empresa.curso.ejercicio17.entity.Pelicula;

public class AlquilerMapper {
    public static AlquilerDto toDto(Alquiler alquiler) {
        if (alquiler == null) return null;

        AlquilerDto dto = new AlquilerDto();
        dto.setId(alquiler.getId());
        dto.setFechaInicio(alquiler.getFechaInicio());
        dto.setFechaFin(alquiler.getFechaFin());
        dto.setDevuelto(alquiler.isDevuelto());

        Cliente cliente = alquiler.getCliente();
        if (cliente != null) {
            dto.setClienteId(cliente.getId());
            dto.setClienteNombre(cliente.getNombre() + " " + cliente.getApellido());
        }

        Copia copia = alquiler.getCopia();
        if (copia != null) {
            dto.setCopiaId(copia.getId());

            Pelicula pelicula = copia.getPelicula();
            if (pelicula != null) {
                dto.setPeliculaTitulo(pelicula.getTitulo());
            }
        }

        return dto;
    }

    public static Alquiler toEntity(AlquilerDto dto) {
        if (dto == null) return null;

        Alquiler alquiler = new Alquiler();
        alquiler.setId(dto.getId());
        alquiler.setFechaInicio(dto.getFechaInicio());
        alquiler.setFechaFin(dto.getFechaFin());
        alquiler.setDevuelto(dto.isDevuelto());
        return alquiler;
    }
}
