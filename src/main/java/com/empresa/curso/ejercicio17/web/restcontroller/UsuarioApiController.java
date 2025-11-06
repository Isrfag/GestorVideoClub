package com.empresa.curso.ejercicio17.web.restcontroller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.empresa.curso.ejercicio17.dto.UsuarioCreateDto;
import com.empresa.curso.ejercicio17.dto.UsuarioDto;
import com.empresa.curso.ejercicio17.entity.Usuario;
import com.empresa.curso.ejercicio17.exception.UsuarioNotFoundException;
import com.empresa.curso.ejercicio17.mapper.UsuarioMapper;
import com.empresa.curso.ejercicio17.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
@PreAuthorize("hasRole('ADMIN')")
public class UsuarioApiController {
    private final UsuarioService usuarioService;

    public UsuarioApiController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<UsuarioDto> listarUsuarios() {
        return usuarioService.listar()
                .stream()
                .map(UsuarioMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> obtenerPorId(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.buscarPorId(id);
            return ResponseEntity.ok(UsuarioMapper.toDto(usuario));
        } catch (UsuarioNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<UsuarioDto> crear(@RequestBody UsuarioCreateDto dto) {
        Usuario nuevo = usuarioService.crear(
                dto.getUsername(),
                dto.getPassword(),
                dto.getRol()
        );

        return new ResponseEntity<>(UsuarioMapper.toDto(nuevo), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDto> actualizar(
            @PathVariable Long id,
            @RequestBody UsuarioDto dto) {

        try {
            Usuario actualizado = usuarioService.actualizar(
                    id,
                    dto.getUsername(),
                    dto.getRol().name()
            );

            return ResponseEntity.ok(UsuarioMapper.toDto(actualizado));

        } catch (UsuarioNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<Void> actualizarPassword(
            @PathVariable Long id,
            @RequestBody String nuevaPassword) {

        try {
            usuarioService.actualizarPassword(id, nuevaPassword);
            return ResponseEntity.ok().build();
        } catch (UsuarioNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            usuarioService.eliminar(id);
            return ResponseEntity.ok().build();
        } catch (UsuarioNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }    
}
