package com.empresa.curso.ejercicio17.service.impl;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.empresa.curso.ejercicio17.entity.Alquiler;
import com.empresa.curso.ejercicio17.entity.Cliente;
import com.empresa.curso.ejercicio17.entity.Copia;
import com.empresa.curso.ejercicio17.entity.Pelicula;
import com.empresa.curso.ejercicio17.entity.builder.AlquilerBuilder;
import com.empresa.curso.ejercicio17.exception.AlquilerNotFoundException;
import com.empresa.curso.ejercicio17.repository.AlquilerRepository;
import com.empresa.curso.ejercicio17.repository.ClienteRepository;
import com.empresa.curso.ejercicio17.repository.CopiaRepository;
import com.empresa.curso.ejercicio17.repository.PeliculaRepository;
import com.empresa.curso.ejercicio17.service.AlquilerService;

@Service
public class AlquilerServiceImpl implements AlquilerService {

    private final PeliculaRepository peliculaRepository;
    private final CopiaRepository copiaRepository;
    private final AlquilerRepository alquilerRepository;
    private final ClienteRepository clienteRepository;

    public AlquilerServiceImpl(PeliculaRepository peliculaRepository,
                                CopiaRepository copiaRepository,
                                AlquilerRepository alquilerRepository,
                                ClienteRepository clienteRepository) {
        this.peliculaRepository = peliculaRepository;
        this.copiaRepository = copiaRepository;
        this.alquilerRepository = alquilerRepository;
        this.clienteRepository = clienteRepository;
    }

    @Override
    public boolean alquilarPelicula(Long peliculaId, Long clienteId) {
        System.out.println("ESTAMOS AQUI");
        Objects.requireNonNull(peliculaId, "El id de la pelicula no puede ser null");
        Objects.requireNonNull(clienteId, "El id del cliente no puede ser null");
        
        Optional<Pelicula> peliculaOpt = peliculaRepository.findByIdWithCopias(peliculaId);
        Optional<Cliente> cOptional = clienteRepository.findById(clienteId);

        if (peliculaOpt.isEmpty() || cOptional.isEmpty()) return false;

        Pelicula pelicula = peliculaOpt.get();      
        Cliente cliente = cOptional.get();

        Optional<Copia> copiaDisponible = pelicula.getCopias().stream()
                .filter(Copia::isDisponible)
                .findFirst();


        if (copiaDisponible.isEmpty()) return false;

        Copia copia = copiaDisponible.get();

        copia.setDisponible(false);
        copiaRepository.save(copia);

        Alquiler alquiler = new AlquilerBuilder()
            .copia(copia)
            .cliente(cliente)
            .fechaInicio(LocalDate.now())
            .devuelto(false)
            .build();

        alquilerRepository.save(alquiler);
        return true;
    }

    @Override
    public boolean devolverPelicula(Long alquilerId) {
        Objects.requireNonNull(alquilerId, "El id del alquiler no puede ser null");

        Optional<Alquiler> alquilerOpt = alquilerRepository.findById(alquilerId);

        if (alquilerOpt.isEmpty()) {
            return false;
        }

        Alquiler alquiler = alquilerOpt.get();
        if (alquiler.isDevuelto()) {
            return false; // ya devuelto anteriormente
        }
        alquiler.setDevuelto(true);
        alquiler.setFechaFin(LocalDate.now());

        Copia copia = alquiler.getCopia();
        copia.setDisponible(true);

        copiaRepository.save(copia);
        alquilerRepository.save(alquiler);

        return true;
    }

    @Override
    public List<Alquiler> listarAlquileres() {
        List<Alquiler> alquileres = alquilerRepository.findAll();
        return null != alquileres ? alquileres : new ArrayList<>();
    }

    @Override
    public long contarAlquileresActivos() {
    return alquilerRepository.findAll().stream()
            .filter(a -> !a.isDevuelto())
            .count();
}

    @Override
    public List<Alquiler> listarPorCliente(Long clienteId)  {
         return alquilerRepository.findAll().stream()
            .filter(a -> a.getCliente() != null && a.getCliente().getId().equals(clienteId))
            .toList();
    }

    @Override
    public Alquiler obtenerAlquilerPorId(Long alquilerId) throws AlquilerNotFoundException {
        Optional<Alquiler> alquilerOpt = alquilerRepository.findById(alquilerId);
        if (alquilerOpt.isEmpty()) {
            throw new AlquilerNotFoundException("Alquiler con id: " + alquilerId +" no encontrado");
        }
        return alquilerOpt.get();

    }
} 