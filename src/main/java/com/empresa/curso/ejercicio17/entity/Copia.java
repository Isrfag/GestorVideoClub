package com.empresa.curso.ejercicio17.entity;

import java.util.List;

import com.empresa.curso.ejercicio17.entity.enums.Formato;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "copia")
public class Copia {

    public Copia(Long id, Formato formato, boolean disponible, Pelicula pelicula, List<Alquiler> alquileres) {
        this.id = id;
        this.formato = formato;
        this.disponible = disponible;
        this.pelicula = pelicula;
        this.alquileres = alquileres;
    }

    public Copia() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Formato formato; 
    private boolean disponible;

    @ManyToOne
    @JoinColumn(name = "pelicula_id")
    private Pelicula pelicula;
    //Una copia pertenece a una sola película, pero una película puede tener muchas copias

    @OneToMany(mappedBy = "copia", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Alquiler> alquileres;
    //Una copia puede estar en muchos alquileres 

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
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
    public Pelicula getPelicula() {
        return pelicula;
    }
    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }
    public List<Alquiler> getAlquileres() {
        return alquileres;
    }
    public void setAlquileres(List<Alquiler> alquileres) {
        this.alquileres = alquileres;
    }


}
