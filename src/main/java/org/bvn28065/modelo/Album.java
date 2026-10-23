package org.bvn28065.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Album")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AlbumId")
    private Integer id;

    @Column(name = "Title")
    private String titulo;

    // En la tabla es un número (ArtistId); en Java es un objeto Artista.
    // Las relaciones @ManyToOne se cargan al momento (EAGER) si no decimos lo contrario.
    @ManyToOne(optional = false)
    @JoinColumn(name = "ArtistId")
    private Artista artista;
}
