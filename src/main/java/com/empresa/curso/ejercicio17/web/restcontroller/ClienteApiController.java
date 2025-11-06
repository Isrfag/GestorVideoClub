package com.empresa.curso.ejercicio17.web.restcontroller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.empresa.curso.ejercicio17.dto.ClienteDto;
import com.empresa.curso.ejercicio17.entity.Cliente;
import com.empresa.curso.ejercicio17.exception.ClienteNotFoundException;
import com.empresa.curso.ejercicio17.mapper.ClienteMapper;
import com.empresa.curso.ejercicio17.service.ClienteService;

@RestController
@RequestMapping("/api/clientes")
public class ClienteApiController {

    private final ClienteService clienteService;

    public ClienteApiController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // Listar todos los clientes
    @GetMapping
    public List<ClienteDto> listarClientes() {
        return clienteService.listarClientes()
                             .stream()
                             .map(ClienteMapper::toDto)
                             .collect(Collectors.toList());
    }

    // Obtener cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDto> obtenerClientePorId(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(ClienteMapper.toDto(clienteService.buscarClientePorId(id)),HttpStatus.OK);
        } catch (ClienteNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    // Crear un cliente nuevo
    @PostMapping
    public ResponseEntity<ClienteDto> crearCliente(@RequestBody ClienteDto clienteDto) {
        Cliente cliente = ClienteMapper.toEntity(clienteDto);
        Cliente guardado = clienteService.guardarCliente(cliente);
        return new ResponseEntity<>(ClienteMapper.toDto(guardado), HttpStatus.CREATED);
    }

    // Actualizar cliente existente
    @PutMapping("/{id}")
    public ResponseEntity<ClienteDto> actualizarCliente(@PathVariable Long id, @RequestBody ClienteDto clienteDto) {
        try {
            Cliente clienteExistente = clienteService.buscarClientePorId(id);
            clienteExistente.setNombre(clienteDto.getNombre());
            clienteExistente.setEmail(clienteDto.getEmail());
            Cliente clienteActualizado = clienteService.guardarCliente(clienteExistente);
            return ResponseEntity.ok(ClienteMapper.toDto(clienteActualizado));

        } catch (ClienteNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        try {
            clienteService.eliminarCliente(id);
            return ResponseEntity.ok().build();

        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
}
