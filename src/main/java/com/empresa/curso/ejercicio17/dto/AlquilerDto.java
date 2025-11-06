package com.empresa.curso.ejercicio17.dto;

import java.time.LocalDate;

public class AlquilerDto {
    private Long id;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private boolean devuelto;
    private Long clienteId;
    private String clienteNombre;
    private Long copiaId;
    private String peliculaTitulo;

    public AlquilerDto() {
    }

    public AlquilerDto(Long id, LocalDate fechaInicio, LocalDate fechaFin, boolean devuelto,
                       Long clienteId, String clienteNombre,
                       Long copiaId, String peliculaTitulo) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.devuelto = devuelto;
        this.clienteId = clienteId;
        this.clienteNombre = clienteNombre;
        this.copiaId = copiaId;
        this.peliculaTitulo = peliculaTitulo;
    }

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

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public Long getCopiaId() {
        return copiaId;
    }

    public void setCopiaId(Long copiaId) {
        this.copiaId = copiaId;
    }

    public String getPeliculaTitulo() {
        return peliculaTitulo;
    }

    public void setPeliculaTitulo(String peliculaTitulo) {
        this.peliculaTitulo = peliculaTitulo;
    }
}
