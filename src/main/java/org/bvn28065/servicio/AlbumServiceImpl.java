package org.bvn28065.servicio;

import org.bvn28065.dto.AlbumDetalle;
import org.bvn28065.dto.ArtistaResumen;
import org.bvn28065.dto.PistaResumen;
import org.bvn28065.modelo.Album;
import org.bvn28065.modelo.Pista;
import org.bvn28065.repositorio.AlbumRepository;
import org.bvn28065.repositorio.PistaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AlbumServiceImpl implements AlbumService {
    private final AlbumRepository albumRepository;
    private final PistaRepository pistaRepository;

    public AlbumServiceImpl(AlbumRepository albumRepository, PistaRepository pistaRepository) {
        this.albumRepository = albumRepository;
        this.pistaRepository = pistaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public AlbumDetalle buscarPorId(Integer id) {
        Optional<Album> encontrado = albumRepository.findById(id);
        // TODO 1: si no existe, lanza NoEncontradoException con el mensaje "No existe el álbum " + id
        if (encontrado.isEmpty()) {
            throw new NoEncontradoException("No existe el albúm con id " + id);
        }

        Album album = encontrado.get();
        // TODO 2: crea el ArtistaResumen del álbum con album.getArtista() (relación LAZY: aquí, dentro de la transacción, se puede)

        ArtistaResumen artista = new ArtistaResumen(album.getArtista().getId(), album.getArtista().getNombre());
        List<PistaResumen> pistas = new ArrayList<>();
        for (Pista pista : pistaRepository.findByAlbumIdOrderById(id)) {
            // TODO 3: añade a la lista un PistaResumen con id, nombre, duración, id del álbum y título del álbum
            pistas.add(new PistaResumen(pista.getId(), pista.getNombre(), pista.getDuracion(), album.getId(), album.getTitulo()));

        }
        return new AlbumDetalle(album.getId(), album.getTitulo(), artista, pistas);
    }
}
