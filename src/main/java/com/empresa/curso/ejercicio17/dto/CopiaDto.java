package com.empresa.curso.ejercicio17.dto;

import com.empresa.curso.ejercicio17.entity.enums.Formato;

public class CopiaDto {
    private Long id;
    private Formato formato = Formato.DIGITAL;
    private boolean disponible;

    private Long peliculaId;
    private String peliculaTitulo;

    public CopiaDto() {
    }

    public CopiaDto(Long id, Formato formato, boolean disponible, Long peliculaId, String peliculaTitulo) {
        this.id = id;
        this.formato = formato;
        this.disponible = disponible;
        this.peliculaId = peliculaId;
        this.peliculaTitulo = peliculaTitulo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Formato getFormato() {
        return formato;
    }

    public void setFormato(Formato formato) {
        this.formato = formato;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Long getPeliculaId() {
        return peliculaId;
    }

    public void setPeliculaId(Long peliculaId) {
        this.peliculaId = peliculaId;
    }

    public String getPeliculaTitulo() {
        return peliculaTitulo;
    }

    public void setPeliculaTitulo(String peliculaTitulo) {
        this.peliculaTitulo = peliculaTitulo;
    }
}
