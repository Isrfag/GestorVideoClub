package com.empresa.curso.ejercicio17.web.controller;

import java.util.ArrayList;
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
import com.empresa.curso.ejercicio17.dto.CopiaDto;
import com.empresa.curso.ejercicio17.dto.PeliculaDto;
import com.empresa.curso.ejercicio17.entity.Pelicula;
import com.empresa.curso.ejercicio17.entity.enums.Formato;
import com.empresa.curso.ejercicio17.exception.FilmNotFoundException;
import com.empresa.curso.ejercicio17.mapper.AlquilerMapper;
import com.empresa.curso.ejercicio17.mapper.ClienteMapper;
import com.empresa.curso.ejercicio17.mapper.PeliculaMapper;
import com.empresa.curso.ejercicio17.service.AlquilerService;
import com.empresa.curso.ejercicio17.service.ClienteService;
import com.empresa.curso.ejercicio17.service.PeliculasService;

@Controller
@RequestMapping("/peliculas")
public class PeliculaController {

    private final PeliculasService peliculasService;
    private final AlquilerService  alquilerService;
    private final ClienteService clienteService;

    public PeliculaController(PeliculasService peliculasService, 
                            AlquilerService alquilerService,
                            ClienteService clienteService) {
        this.peliculasService = peliculasService;
        this.alquilerService = alquilerService;
        this.clienteService = clienteService;
    }

    @GetMapping
    public String listar(@RequestParam(required = false) Long clienteId,
                         Model model,
                         @ModelAttribute("mensaje") String mensaje,
                         @ModelAttribute("error") String error) {

        List<PeliculaDto> peliculas = peliculasService.obtenerPeliculas()
                .stream()
                .map(PeliculaMapper::toDto) // <-- entity -> dto
                .collect(Collectors.toList());
        model.addAttribute("peliculas", peliculas);

        List<AlquilerDto> alquileres;
        if (clienteId != null) {
            alquileres = alquilerService.listarPorCliente(clienteId)
                        .stream()
                        .map(AlquilerMapper::toDto) // <-- entity -> dto
                        .collect(Collectors.toList());
            model.addAttribute("clienteSeleccionado", clienteId);
        } else {
            alquileres = alquilerService.listarAlquileres()
                        .stream()
                        .map(AlquilerMapper::toDto) // <-- entity -> dto
                        .collect(Collectors.toList());
        }
        model.addAttribute("alquileres", alquileres);

        // Lista de clientes -> Entity -> DTO
        model.addAttribute("clientes", clienteService.listarClientes()
                .stream()
                .map(ClienteMapper::toDto) // <-- entity -> dto
                .collect(Collectors.toList()));

        if (!mensaje.isEmpty()) {
            model.addAttribute("mensaje", mensaje);
        }
        if (!error.isEmpty()) {
            model.addAttribute("error", error);
        }
        model.addAttribute("totalAlquileres", alquileres.size());

        return "peliculas/lista";
    }

    @GetMapping("/nueva")
    public String nuevaPelicula(Model model) {
        PeliculaDto peliculaDto = new PeliculaDto();
        peliculaDto.getCopias().add(new CopiaDto());
        model.addAttribute("pelicula", peliculaDto);
        model.addAttribute("formatos", Formato.values());
        return "peliculas/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute PeliculaDto peliculaDto,
                          RedirectAttributes redirectAttributes) {

        boolean esNueva = (peliculaDto.getId() == null);

        // DTO -> Entity antes de guardar
        Pelicula peliculaEntity = PeliculaMapper.toEntity(peliculaDto);

        // Enlazar copias con película
        if (peliculaEntity.getCopias() != null) {
            peliculaEntity.getCopias().forEach(c -> c.setPelicula(peliculaEntity));
        }

        Pelicula guardada = peliculasService.guardarPelicula(peliculaEntity);

        // Entity -> DTO para mensaje
        PeliculaDto guardadaDto = PeliculaMapper.toDto(guardada);

        String mensaje = esNueva
                ? "✅ Película '" + guardadaDto.getTitulo() + "' creada con éxito."
                : "✅ Película '" + guardadaDto.getTitulo() + "' editada con éxito.";

        redirectAttributes.addFlashAttribute("mensaje", mensaje);
        return "redirect:/peliculas";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        try {
            Pelicula pelicula = peliculasService.buscarPeliculaPorId(id);

            if (pelicula.getCopias() == null) {
                pelicula.setCopias(new ArrayList<>());
            }

            pelicula.getCopias().forEach(c -> {
                if (c.getFormato() == null) {
                    c.setFormato(Formato.DIGITAL);
                }
            });

            PeliculaDto peliculaDto = PeliculaMapper.toDto(pelicula);
            model.addAttribute("pelicula", peliculaDto);
            model.addAttribute("formatos", Formato.values());

            return "peliculas/formulario";
        } catch (FilmNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "❌ Película no encontrada.");
            return "redirect:/peliculas";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        peliculasService.eliminarPelicula(id);
        redirectAttributes.addFlashAttribute("mensaje", "🗑️ Película eliminada correctamente.");
        return "redirect:/peliculas";
    }
    
}
