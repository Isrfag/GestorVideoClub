package com.empresa.curso.ejercicio17.dto;

import com.empresa.curso.ejercicio17.entity.enums.Rol;

public class UsuarioDto {
    private Long id;
    private String username;
    private Rol rol;

    public UsuarioDto() {}

    public UsuarioDto(Long id, String username, Rol rol) {
        this.id = id;
        this.username = username;
        this.rol = rol;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }
}
