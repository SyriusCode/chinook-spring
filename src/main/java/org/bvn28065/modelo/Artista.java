package org.bvn28065.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Artist")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Artista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ArtistId")
    private Integer id;

    @Column(name = "Name")
    private String nombre;

    // Lado inverso (opcional) de la relación 1:N: la clave foránea la tiene Album (atributo "artista").
    // Solo sirve para poder escribir artista.getAlbumes(). Es LAZY por defecto.
    @OneToMany(mappedBy = "artista")
    private Set<Album> albums = new HashSet<>();
}
