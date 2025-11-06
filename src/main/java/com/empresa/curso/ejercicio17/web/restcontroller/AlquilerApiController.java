package com.empresa.curso.ejercicio17.web.restcontroller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.empresa.curso.ejercicio17.dto.AlquilerDto;
import com.empresa.curso.ejercicio17.entity.Alquiler;
import com.empresa.curso.ejercicio17.exception.AlquilerNotFoundException;
import com.empresa.curso.ejercicio17.mapper.AlquilerMapper;
import com.empresa.curso.ejercicio17.service.AlquilerService;


@RestController
@RequestMapping("/api/alquileres")
public class AlquilerApiController {

    private final AlquilerService alquilerService;

    public AlquilerApiController(AlquilerService alquilerService) {
        this.alquilerService = alquilerService;
    }

    // Listar todos los alquileres (opcional filtrar por clienteId)
    @GetMapping
    public List<AlquilerDto> listar(@RequestParam(required = false) Long clienteId) {
        List<Alquiler> alquileres = (clienteId != null) ?
                alquilerService.listarPorCliente(clienteId) :
                alquilerService.listarAlquileres();

        return alquileres.stream()
                         .map(AlquilerMapper::toDto)
                         .collect(Collectors.toList());
    }

    // Obtener un alquiler por id
    @GetMapping("/{id}")
    public ResponseEntity<AlquilerDto> obtenerPorId(@PathVariable Long id) {
        try {
            return new ResponseEntity<AlquilerDto>(AlquilerMapper.toDto( alquilerService.obtenerAlquilerPorId(id)),HttpStatus.OK);
        }catch(AlquilerNotFoundException e) {
            return ResponseEntity.notFound().build();
        }                     
    }

    // Crear un alquiler
    @PostMapping
    public ResponseEntity<String> alquilar(@RequestParam Long peliculaId, @RequestParam Long clienteId) {
        boolean alquilada = alquilerService.alquilarPelicula(peliculaId, clienteId);
        if (alquilada) {
            return ResponseEntity.ok("Película alquilada con éxito");
        } else {
            return ResponseEntity.badRequest().body("No se pudo realizar el alquiler");
        }
    }

    // Devolver película
    @PostMapping("/devolver/{id}")
    public ResponseEntity<String> devolver(@PathVariable Long id) {
        boolean devuelto = alquilerService.devolverPelicula(id);
        if (devuelto) {
            return ResponseEntity.ok("Película devuelta correctamente");
        } else {
            return ResponseEntity.badRequest().body("Alquiler no encontrado o ya devuelto");
        }
    }
    
}
