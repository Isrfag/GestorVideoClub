package com.empresa.curso.ejercicio17.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.empresa.curso.ejercicio17.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    Optional<Usuario> findByUsername(String username);
    
}