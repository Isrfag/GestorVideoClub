package com.empresa.curso.ejercicio17.web.restcontroller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.empresa.curso.ejercicio17.dto.PeliculaDto;
import com.empresa.curso.ejercicio17.entity.Copia;
import com.empresa.curso.ejercicio17.entity.Pelicula;
import com.empresa.curso.ejercicio17.exception.FilmNotFoundException;
import com.empresa.curso.ejercicio17.mapper.CopiaMapper;
import com.empresa.curso.ejercicio17.mapper.PeliculaMapper;
import com.empresa.curso.ejercicio17.service.PeliculasService;

@RestController
@RequestMapping("/api/peliculas")
public class PeliculaApiController {

    private final PeliculasService peliculasService;

    public PeliculaApiController(PeliculasService peliculasService) {
        this.peliculasService = peliculasService;
    }

    // LISTAR TODAS LAS PELÍCULAS
    @GetMapping
    public List<PeliculaDto> listarPeliculas() {
        return peliculasService.obtenerPeliculas()
                               .stream()
                               .map(PeliculaMapper::toDto)
                               .collect(Collectors.toList());
    }

    // OBTENER POR ID
    @GetMapping("/{id}")
    public ResponseEntity<PeliculaDto> obtenerPeliculaPorId(@PathVariable Long id) {
        try {
            Pelicula pelicula = peliculasService.buscarPeliculaPorId(id);
            return new ResponseEntity<>(PeliculaMapper.toDto(pelicula), HttpStatus.OK);

        } catch (FilmNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // CREAR UNA NUEVA PELÍCULA
    @PostMapping
    public ResponseEntity<PeliculaDto> crearPelicula(@RequestBody PeliculaDto peliculaDto) {

        Pelicula pelicula = PeliculaMapper.toEntity(peliculaDto);

        // Enlazar copias con película si vienen en el DTO
        if (pelicula.getCopias() != null) {
            pelicula.getCopias().forEach(c -> c.setPelicula(pelicula));
        }

        Pelicula guardada = peliculasService.guardarPelicula(pelicula);
        return new ResponseEntity<>(PeliculaMapper.toDto(guardada), HttpStatus.CREATED);
    }

    // ACTUALIZAR UNA PELÍCULA (solo género y copias)
    @PutMapping("/{id}")
    public ResponseEntity<PeliculaDto> actualizarPelicula(
            @PathVariable Long id,
            @RequestBody PeliculaDto peliculaDto) {

        try {
            Pelicula existente = peliculasService.buscarPeliculaPorId(id);

            existente.setGenero(peliculaDto.getGenero());

            // Eliminamos todas y volvemos a crearlas desde el DTO
            existente.getCopias().clear();

            if (peliculaDto.getCopias() != null) {
                peliculaDto.getCopias().forEach(copiaDto -> {
                    Copia copia = CopiaMapper.toEntity(copiaDto);
                    copia.setPelicula(existente);
                    existente.getCopias().add(copia);
                });
            }
            Pelicula actualizada = peliculasService.guardarPelicula(existente);

            return ResponseEntity.ok(PeliculaMapper.toDto(actualizada));

        } catch (FilmNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ELIMINAR PELÍCULA
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPelicula(@PathVariable Long id) {
        try {
            peliculasService.eliminarPelicula(id);
            return ResponseEntity.ok().build();

        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}

