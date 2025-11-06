package com.empresa.curso.ejercicio17.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.empresa.curso.ejercicio17.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    
}
