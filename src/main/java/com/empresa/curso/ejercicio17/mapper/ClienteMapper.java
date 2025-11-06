package com.empresa.curso.ejercicio17.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.empresa.curso.ejercicio17.dto.ClienteDto;
import com.empresa.curso.ejercicio17.entity.Cliente;

public class ClienteMapper {
    public static ClienteDto toDto(Cliente cliente) {
        if (cliente == null) {
            return null;
        }

        ClienteDto dto = new ClienteDto();
        dto.setId(cliente.getId());
        dto.setNombre(cliente.getNombre());
        dto.setApellido(cliente.getApellido());
        dto.setEmail(cliente.getEmail());
        dto.setTelefono(cliente.getTelefono());
        return dto;
    }

    public static Cliente toEntity(ClienteDto dto) {
        if (dto == null) {
            return null;
        }

        Cliente cliente = new Cliente();
        cliente.setId(dto.getId());
        cliente.setNombre(dto.getNombre());
        cliente.setApellido(dto.getApellido());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefono(dto.getTelefono());
        return cliente;
    }

    public static List<ClienteDto> toDtoList(List<Cliente> clientes) {
        if (clientes == null) {
            return List.of();
        }
        return clientes.stream()
                .map(ClienteMapper::toDto)
                .collect(Collectors.toList());
    }

    public static List<Cliente> toEntityList(List<ClienteDto> dtos) {
        if (dtos == null) {
            return List.of();
        }
        return dtos.stream()
                .map(ClienteMapper::toEntity)
                .collect(Collectors.toList());
    }
}
