package com.empresa.curso.ejercicio17.mapper;

import com.empresa.curso.ejercicio17.dto.UsuarioDto;
import com.empresa.curso.ejercicio17.entity.Usuario;

public class UsuarioMapper {
    public static UsuarioDto toDto(Usuario u) {
        return new UsuarioDto(
            u.getId(),
            u.getUsername(),
            u.getRol()
        );
    }

    public static Usuario toEntity(UsuarioDto dto) {
        Usuario u = new Usuario();
        u.setId(dto.getId());
        u.setUsername(dto.getUsername());
        u.setRol(dto.getRol());
        return u;
    }
}
