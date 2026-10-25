package org.bvn28065.operaciones;

import lombok.RequiredArgsConstructor;
import org.bvn28065.modelo.Album;
import org.bvn28065.modelo.Artista;
import org.bvn28065.repositorio.ArtistaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ConsultasArtistas {

    private final ArtistaRepository artistaRepository;

    // Caso 2: recorrer una colección LAZY. Con la transacción abierta, getAlbumes() puede cargar los álbumes.
    // Devolvemos Strings ya calculados: no dependen de que la sesión siga abierta.
    @Transactional(readOnly = true)
    public List<String> titulosDeAlbumes(Integer artistaId) {
        Optional<Artista> artista = artistaRepository.findById(artistaId);
        if (artista.isEmpty()) {
            throw new IllegalArgumentException("No existe el artista " + artistaId);
        }
        List<String> titulos = new ArrayList<>();
        for (Album album : artista.get().getAlbums()) {      // aquí se cargan los álbumes (LAZY)
            titulos.add(album.getTitulo());
        }
        Collections.sort(titulos);                             // un Set no tiene orden: ordenamos
        return titulos;
    }
}