package com.empresa.curso.ejercicio17.service.impl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.empresa.curso.ejercicio17.entity.Usuario;
import com.empresa.curso.ejercicio17.repository.UsuarioRepository;
import com.empresa.curso.ejercicio17.service.UsuarioDetailsService;

@Service
public class UsuarioDetailsServiceImpl implements UsuarioDetailsService {

    private final UsuarioRepository repository;

    public UsuarioDetailsServiceImpl (UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Usuario u = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return User.builder()
                .username(u.getUsername())
                .password(u.getPassword())
                .roles(u.getRol().name())
                .build();
    }
    
}
