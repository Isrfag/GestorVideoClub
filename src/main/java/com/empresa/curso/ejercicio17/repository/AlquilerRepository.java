package com.empresa.curso.ejercicio17.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.empresa.curso.ejercicio17.entity.Alquiler;

public interface AlquilerRepository extends JpaRepository<Alquiler, Long> {
    
}
