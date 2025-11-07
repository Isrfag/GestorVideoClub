package com.empresa.curso.ejercicio17.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.empresa.curso.ejercicio17.dto.UsuarioCreateDto;
import com.empresa.curso.ejercicio17.dto.UsuarioDto;
import com.empresa.curso.ejercicio17.entity.Usuario;
import com.empresa.curso.ejercicio17.exception.UsuarioNotFoundException;
import com.empresa.curso.ejercicio17.mapper.UsuarioMapper;
import com.empresa.curso.ejercicio17.service.UsuarioService;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController (UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

        // Listar todos los usuarios
    @GetMapping
    public String listarUsuarios(Model model) {
        List<UsuarioDto> usuarios = usuarioService.listar()
                .stream()
                .map(UsuarioMapper::toDto)
                .collect(Collectors.toList());
        model.addAttribute("usuarios", usuarios); // coincide con th:each="u : ${usuarios}"
        return "usuarios/lista";
    }

    // Mostrar formulario de creación
    @GetMapping("/nuevo")
    public String mostrarFormularioCreacion(Model model) {
        model.addAttribute("usuarioCreateDto", new UsuarioCreateDto());
        return "usuarios/formulario";
    }

    // Procesar creación
    @PostMapping("/nuevo")
    public String crearUsuario(@ModelAttribute UsuarioCreateDto usuarioCreateDto,
                            RedirectAttributes redirectAttributes) {
        usuarioService.crear(
                usuarioCreateDto.getUsername(),
                usuarioCreateDto.getPassword(),
                usuarioCreateDto.getRol()
        );
        redirectAttributes.addFlashAttribute("exito", "Usuario creado correctamente");
        return "redirect:/usuarios";
    }


    // Mostrar formulario de edición
    @GetMapping("/{id}/editar")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Usuario usuario = usuarioService.buscarPorId(id);
            model.addAttribute("usuarioDto", UsuarioMapper.toDto(usuario));
            return "usuarios/formulario-edit";
        } catch (UsuarioNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "Usuario no encontrado");
            return "redirect:/usuarios";
        }
    }

    // Procesar edición
    @PostMapping("/{id}/editar")
    public String actualizarUsuario(@PathVariable Long id,
                                    @ModelAttribute UsuarioDto usuarioDto,
                                    RedirectAttributes redirectAttributes) {
        try {
            usuarioService.actualizar(id, usuarioDto.getUsername(), usuarioDto.getRol().name());
            redirectAttributes.addFlashAttribute("exito", "Usuario actualizado correctamente");
        } catch (UsuarioNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "Usuario no encontrado");
        }
        return "redirect:/usuarios";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminarUsuario(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            usuarioService.eliminar(id);
            redirectAttributes.addFlashAttribute("exito", "Usuario eliminado correctamente");
        } catch (UsuarioNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "Usuario no encontrado");
        }
        return "redirect:/usuarios";
    }
}
