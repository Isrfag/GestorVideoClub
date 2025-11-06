package com.empresa.curso.ejercicio17.service;

import java.util.List;

import com.empresa.curso.ejercicio17.entity.Cliente;
import com.empresa.curso.ejercicio17.exception.ClienteNotFoundException;

public interface ClienteService {

  List<Cliente> listarClientes();  

  Cliente buscarClientePorId(Long id) throws ClienteNotFoundException;

  Cliente guardarCliente(Cliente cliente);

  void eliminarCliente (Long clienteId) throws IllegalArgumentException ;
} 
