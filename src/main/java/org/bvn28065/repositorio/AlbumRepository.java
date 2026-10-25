package org.bvn28065.repositorio;

import org.bvn28065.modelo.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Integer> {

    // "ArtistaId" = el atributo id del atributo artista: a.artista.id
    List<Album> findByArtistaIdOrderByTitulo(Integer artistaId);
}
