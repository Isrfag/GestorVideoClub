package com.empresa.curso.ejercicio17.service.impl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.empresa.curso.ejercicio17.entity.Usuario;
import com.empresa.curso.ejercicio17.entity.enums.Rol;
import com.empresa.curso.ejercicio17.exception.UsuarioNotFoundException;
import com.empresa.curso.ejercicio17.repository.UsuarioRepository;
import com.empresa.curso.ejercicio17.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }    

    @Override
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario buscarPorId(Long id) throws UsuarioNotFoundException {
       return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario con id:" + String.valueOf(id) + " no encontrado"));
    }

    @Override
    public Usuario crear(String username, String password, String rol) {
        Usuario u = new Usuario();
        u.setUsername(username);
        u.setPassword(passwordEncoder.encode(password));
        u.setRol(Rol.valueOf(rol));

        return usuarioRepository.save(u);
    }

    @Override
    public Usuario actualizar(Long id, String username, String rol) throws UsuarioNotFoundException {
        Usuario u = buscarPorId(id);
        u.setUsername(username);
        u.setRol(Rol.valueOf(rol));
        return usuarioRepository.save(u);
    }

    @Override
    public Usuario actualizarPassword(Long id, String nuevaPassword) throws UsuarioNotFoundException {
        Usuario u = buscarPorId(id);
        u.setPassword(passwordEncoder.encode(nuevaPassword));
        return usuarioRepository.save(u);
    }

    @Override
    public void eliminar(Long id) throws UsuarioNotFoundException  {
        Usuario u = buscarPorId(id);
        usuarioRepository.delete(u);
    }
    
}
