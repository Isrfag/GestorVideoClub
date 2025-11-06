package com.empresa.curso.ejercicio17.service;

import java.util.List;

import com.empresa.curso.ejercicio17.entity.Pelicula;
import com.empresa.curso.ejercicio17.exception.FilmNotFoundException;

public interface PeliculasService {

    List<Pelicula> obtenerPeliculas();

    Pelicula buscarPeliculaPorId(Long id) throws FilmNotFoundException;

    Pelicula guardarPelicula(Pelicula pelicula);

    void eliminarPelicula(Long id) throws IllegalArgumentException;
}