package com.empresa.curso.ejercicio17.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "pelicula")
public class Pelicula {

    public Pelicula(Long id, String titulo, String genero, LocalDate fechaEstreno, String director, int duracion,
            List<Copia> copias) {
        this.id = id;
        this.titulo = titulo;
        this.genero = genero;
        this.fechaEstreno = fechaEstreno;
        this.director = director;
        this.duracion = duracion;
        this.copias = copias;
    }
    
    public Pelicula() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String genero;
    private LocalDate fechaEstreno;
    private String director;
    private int duracion;

    @OneToMany(mappedBy = "pelicula", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Copia> copias = new ArrayList<>();
    //Una película puede tener muchas copias disponibles para alquilar

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
    public List<Copia> getCopias() {
        return copias;
    }
    public void setCopias(List<Copia> copias) {
        this.copias = copias;
    }   
}
