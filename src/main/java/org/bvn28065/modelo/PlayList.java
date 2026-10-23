package org.bvn28065.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PlayList")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PlayList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PlayListId")
    private Integer id;

    @Column(name = "Name")
    private String nombre;

    @ManyToMany
    @JoinTable(
            name = "PlayListTrack",
            joinColumns = @JoinColumn(name = "PlaylistId"),
            inverseJoinColumns = @JoinColumn(name = "TrackId"))
    private Set<Pista> pistas = new HashSet<>();

}
