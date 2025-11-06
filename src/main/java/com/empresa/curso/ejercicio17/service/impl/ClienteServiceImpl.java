package com.empresa.curso.ejercicio17.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.empresa.curso.ejercicio17.entity.Cliente;
import com.empresa.curso.ejercicio17.exception.ClienteNotFoundException;
import com.empresa.curso.ejercicio17.repository.ClienteRepository;
import com.empresa.curso.ejercicio17.service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl (ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public List<Cliente> listarClientes() {
       return clienteRepository.findAll();
    }

    @Override
    public Cliente buscarClientePorId(Long id) throws ClienteNotFoundException {
       Optional<Cliente> cliente = clienteRepository.findById(id);
       if (cliente.isEmpty()) {
            throw new ClienteNotFoundException("Cannot find cliente with id: " + id);
       }
       return cliente.get();
    }

    @Override
    public Cliente guardarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public void eliminarCliente(Long clienteId) throws IllegalArgumentException {
        if (clienteId == null || clienteId.toString().isBlank()) {
            throw new IllegalArgumentException("clienteId no puede ser nulo");
        }
        clienteRepository.deleteById(clienteId);
    }

    
    
}
