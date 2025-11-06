package com.empresa.curso.ejercicio17.service;

import java.util.List;

import com.empresa.curso.ejercicio17.entity.Alquiler;
import com.empresa.curso.ejercicio17.exception.AlquilerNotFoundException;

public interface AlquilerService {

    boolean alquilarPelicula(Long peliculaId, Long clienteId);

    boolean devolverPelicula(Long alquilerId);

    List<Alquiler> listarAlquileres();

    long contarAlquileresActivos();

    List<Alquiler> listarPorCliente(Long clienteId);
  
    Alquiler obtenerAlquilerPorId (Long alquilerId)throws AlquilerNotFoundException ;
} 