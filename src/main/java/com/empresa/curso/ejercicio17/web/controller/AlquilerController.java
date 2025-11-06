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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.empresa.curso.ejercicio17.dto.AlquilerDto;
import com.empresa.curso.ejercicio17.dto.ClienteDto;
import com.empresa.curso.ejercicio17.dto.PeliculaDto;
import com.empresa.curso.ejercicio17.entity.Alquiler;
import com.empresa.curso.ejercicio17.entity.Copia;
import com.empresa.curso.ejercicio17.entity.Pelicula;
import com.empresa.curso.ejercicio17.exception.FilmNotFoundException;
import com.empresa.curso.ejercicio17.mapper.AlquilerMapper;
import com.empresa.curso.ejercicio17.mapper.ClienteMapper;
import com.empresa.curso.ejercicio17.mapper.PeliculaMapper;
import com.empresa.curso.ejercicio17.service.AlquilerService;
import com.empresa.curso.ejercicio17.service.ClienteService;
import com.empresa.curso.ejercicio17.service.PeliculasService;

@Controller
@RequestMapping("/alquileres")
public class AlquilerController {

    private final AlquilerService alquilerService;
    private final ClienteService clienteService;
    private final PeliculasService peliculasService;

    public AlquilerController(AlquilerService alquilerService, ClienteService clienteService,PeliculasService peliculasService) {
        this.alquilerService = alquilerService;
        this.clienteService = clienteService;
        this.peliculasService = peliculasService;
    }

    // Listado de alquileres existentes (ya tienes esto)
    @GetMapping
    public String listar(Model model,
                         @ModelAttribute("mensaje") String mensaje,
                         @ModelAttribute("error") String error,
                         @RequestParam(required = false) Long clienteId) {

        List<Alquiler> alquileres = (clienteId != null) ?
                alquilerService.listarPorCliente(clienteId).stream().filter(a -> !a.isDevuelto()).toList() :
                alquilerService.listarAlquileres().stream().filter(a -> !a.isDevuelto()).toList();

        // Convertimos a DTOs para mas wenas practicas
        List<AlquilerDto> alquileresDto = alquileres.stream()
                                                    .map(AlquilerMapper::toDto)
                                                    .collect(Collectors.toList());

        List<ClienteDto> clientesDto = clienteService.listarClientes()
                                                     .stream()
                                                     .map(ClienteMapper::toDto)
                                                     .collect(Collectors.toList());

        model.addAttribute("alquileres", alquileresDto);
        model.addAttribute("clientes", clientesDto);
        model.addAttribute("mensaje", mensaje);
        model.addAttribute("error", error);

        if (clienteId != null) {
            model.addAttribute("clienteSeleccionado", clienteId);
        }

        return "alquileres/lista";
    }

    // Mostrar formulario de alquiler
    @GetMapping("/nuevo/{peliculaId}")
    public String mostrarFormulario(@PathVariable Long peliculaId, Model model,
                                    RedirectAttributes redirectAttributes) {
        try {
            Pelicula pelicula = peliculasService.buscarPeliculaPorId(peliculaId);
            long copiasDisponibles = pelicula.getCopias().stream().filter(Copia::isDisponible).count();

            if (copiasDisponibles == 0) {
                redirectAttributes.addFlashAttribute("error", "❌ No hay copias disponibles.");
                return "redirect:/peliculas";
            }

            // Convertimos a DTO
            PeliculaDto peliculaDto = PeliculaMapper.toDto(pelicula);
            List<ClienteDto> clientesDto = clienteService.listarClientes()
                                                         .stream()
                                                         .map(ClienteMapper::toDto)
                                                         .collect(Collectors.toList());

            model.addAttribute("pelicula", peliculaDto);
            model.addAttribute("clientes", clientesDto);

            return "alquileres/formulario";

        } catch (FilmNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "❌ Película no encontrada.");
            return "redirect:/peliculas";
        }
    }

    // Crear alquiler
    @PostMapping
    public String alquilar(@RequestParam Long peliculaId,
                           @RequestParam Long clienteId,
                           RedirectAttributes redirectAttributes) {
        boolean alquilada = alquilerService.alquilarPelicula(peliculaId, clienteId);

        if (alquilada) {
            redirectAttributes.addFlashAttribute("mensaje", "🎞️ Película alquilada con éxito.");
        } else {
            redirectAttributes.addFlashAttribute("error", "❌ No se pudo realizar el alquiler.");
        }

        return "redirect:/peliculas";
    }

    // Devolver película
    @PostMapping("/devolver/{id}")
    public String devolver(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        boolean devuelto = alquilerService.devolverPelicula(id);

        if (devuelto) {
            redirectAttributes.addFlashAttribute("mensaje", "🎬 Película devuelta correctamente.");
        } else {
            redirectAttributes.addFlashAttribute("error", "❌ Error: alquiler no encontrado o ya devuelto.");
        }

        return "redirect:/alquileres";
    }
    
}
