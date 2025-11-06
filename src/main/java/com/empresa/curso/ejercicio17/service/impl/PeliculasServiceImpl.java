package com.empresa.curso.ejercicio17.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.empresa.curso.ejercicio17.entity.Copia;
import com.empresa.curso.ejercicio17.entity.Pelicula;
import com.empresa.curso.ejercicio17.exception.FilmNotFoundException;
import com.empresa.curso.ejercicio17.repository.PeliculaRepository;
import com.empresa.curso.ejercicio17.service.PeliculasService;

@Service
public class PeliculasServiceImpl implements PeliculasService {

    private final PeliculaRepository peliculaRepository;

    public PeliculasServiceImpl(PeliculaRepository peliculaRepository) {
        this.peliculaRepository = peliculaRepository;
    }

    @Override
    public List<Pelicula> obtenerPeliculas() {
        return peliculaRepository.findAllWithCopias();
    }

    @Override
    public Pelicula buscarPeliculaPorId(Long id) throws FilmNotFoundException {
        Pelicula pelicula = peliculaRepository.findByIdWithCopias(id).orElse(null);
        if (null == pelicula) {
            throw new FilmNotFoundException("La pelicula con id: " + id + " no se ha encontrado");
        }
        return pelicula;
    }

    @Override
    public Pelicula guardarPelicula(Pelicula pelicula) {
        return peliculaRepository.save(pelicula);
    }

    @Override
    public void eliminarPelicula(Long id) {
        if (null == id || id.toString().isBlank()) {
            throw new IllegalArgumentException("Pelicula id no puede ser nulo");
        }
        peliculaRepository.deleteById(id);
    }

    public long contarCopiasDisponibles(Pelicula pelicula) {
    return pelicula.getCopias().stream()
            .filter(Copia::isDisponible)
            .count();
}
    
}
