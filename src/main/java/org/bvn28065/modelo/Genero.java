package org.bvn28065.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "Genre")
public class Genero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GenreId")
    private Integer id;

    @Column(name = "Name")
    private String nombre;

    // JPA necesita un constructor vacío para crear los objetos al leer las filas
    public Genero() {
    }

    public Genero(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return id + " - " + nombre;
    }
}