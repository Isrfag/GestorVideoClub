package com.empresa.curso.ejercicio17.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PeliculaDto {

  private Long id;
    private String titulo;
    private String genero;
    private LocalDate fechaEstreno;
    private String director;
    private int duracion;

    private List<CopiaDto> copias = new ArrayList<>();

    public PeliculaDto() {
    }

    public PeliculaDto(Long id, String titulo, String genero, LocalDate fechaEstreno, String director, int duracion, List<CopiaDto> copias) {
        this.id = id;
        this.titulo = titulo;
        this.genero = genero;
        this.fechaEstreno = fechaEstreno;
        this.director = director;
        this.duracion = duracion;
        this.copias = copias;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public LocalDate getFechaEstreno() {
        return fechaEstreno;
    }

    public void setFechaEstreno(LocalDate fechaEstreno) {
        this.fechaEstreno = fechaEstreno;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public List<CopiaDto> getCopias() {
        return copias;
    }

    public void setCopias(List<CopiaDto> copias) {
        this.copias = copias;
    }
    
}
