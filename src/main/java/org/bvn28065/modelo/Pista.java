package org.bvn28065.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Table(name = "Track")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Pista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TrackId")
    private Integer id;

    @Column(name = "Name")
    private String nombre;

    @Column(name = "Composer")
    private String compositor;

    @Column(name = "Milliseconds")
    private Integer milisegundos;

    @Column(name = "UnitPrice")
    private BigDecimal precioUnitario;

    @ManyToOne
    @JoinColumn(name = "GenreId")
    private Genero genero;

    @ManyToOne
    @JoinColumn(name = "AlbumId")
    private Album album;

    // Un metodo normal en una entidad: no es una columna, solo da la duración como m:ss
    public String getDuracion() {
        int segundos = milisegundos / 1000;
        return String.format("%d:%02d", segundos / 60, segundos % 60);
    }
}
