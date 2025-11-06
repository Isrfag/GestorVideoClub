package com.empresa.curso.ejercicio17.entity.builder;

import java.time.LocalDate;
import java.util.Objects;

import com.empresa.curso.ejercicio17.entity.Alquiler;
import com.empresa.curso.ejercicio17.entity.Cliente;
import com.empresa.curso.ejercicio17.entity.Copia;

public class AlquilerBuilder {

    private Long id;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private boolean devuelto;
    private Cliente cliente;
    private Copia copia;

    public AlquilerBuilder() {
    }

    public AlquilerBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public AlquilerBuilder fechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
        return this;
    }

    public AlquilerBuilder fechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
        return this;
    }

    public AlquilerBuilder devuelto(boolean devuelto) {
        this.devuelto = devuelto;
        return this;
    }

    public AlquilerBuilder cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public AlquilerBuilder copia(Copia copia) {
        this.copia = copia;
        return this;
    }

    public Alquiler build() {
        return Objects.requireNonNull(new Alquiler(
            this.id,
            this.fechaInicio,
            this.fechaFin,
            this.devuelto,
            this.cliente,
            this.copia
        ));
    }
    
}
