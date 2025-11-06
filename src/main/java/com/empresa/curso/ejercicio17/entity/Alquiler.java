package com.empresa.curso.ejercicio17.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Alquiler {

    public Alquiler(Long id, LocalDate fechaInicio, LocalDate fechaFin, boolean devuelto, Cliente cliente,
            Copia copia) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.devuelto = devuelto;
        this.cliente = cliente;
        this.copia = copia;
    }

    public Alquiler() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private boolean devuelto;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    //Un cliente puede tener muchos alquileres cada alquiler pertenece a un solo cliente

    @ManyToOne
    @JoinColumn(name = "copia_id")
    private Copia copia;
    //Una copia puede estar en muchos alquileres a lo largo del tiempo, pero cada alquiler se refiere a una sola copia


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }
    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    public LocalDate getFechaFin() {
        return fechaFin;
    }
    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
    public boolean isDevuelto() {
        return devuelto;
    }
    public void setDevuelto(boolean devuelto) {
        this.devuelto = devuelto;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public Copia getCopia() {
        return copia;
    }
    public void setCopia(Copia copia) {
        this.copia = copia;
    }
    
}
