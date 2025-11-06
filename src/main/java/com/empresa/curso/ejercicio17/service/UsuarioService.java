package com.empresa.curso.ejercicio17.service;

import java.util.List;

import com.empresa.curso.ejercicio17.entity.Usuario;
import com.empresa.curso.ejercicio17.exception.UsuarioNotFoundException;

public interface UsuarioService {

    List<Usuario> listar();

    Usuario buscarPorId(Long id) throws UsuarioNotFoundException;

    Usuario crear(String username, String password, String rol);

    Usuario actualizar(Long id, String username, String rol) throws UsuarioNotFoundException;

    Usuario actualizarPassword(Long id, String nuevaPassword) throws UsuarioNotFoundException;

    void eliminar(Long id) throws UsuarioNotFoundException;
}
