package com.empresa.curso.ejercicio17.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.empresa.curso.ejercicio17.entity.Pelicula;

public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {
    // Trae todas las películas con sus copias cargadas
    @Query("SELECT DISTINCT p FROM Pelicula p LEFT JOIN FETCH p.copias")
    List<Pelicula> findAllWithCopias();

    // Trae una película por id con copias cargadas
    @Query("SELECT p FROM Pelicula p LEFT JOIN FETCH p.copias WHERE p.id = :id")
    Optional<Pelicula> findByIdWithCopias(Long id);
}
