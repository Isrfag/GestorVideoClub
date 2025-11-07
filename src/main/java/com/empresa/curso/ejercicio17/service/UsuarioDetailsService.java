package com.empresa.curso.ejercicio17.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsuarioDetailsService extends UserDetailsService {
    UserDetails loadUserByUsername(String username);
}
