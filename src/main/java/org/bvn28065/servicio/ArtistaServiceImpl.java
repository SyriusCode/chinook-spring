package org.bvn28065.servicio;

import org.bvn28065.dto.AlbumResumen;
import org.bvn28065.dto.ArtistaDetalle;
import org.bvn28065.dto.ArtistaResumen;
import org.bvn28065.modelo.Album;
import org.bvn28065.modelo.Artista;
import org.bvn28065.repositorio.AlbumRepository;
import org.bvn28065.repositorio.ArtistaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArtistaServiceImpl implements ArtistaService {

    private final ArtistaRepository artistaRepository;
    private final AlbumRepository albumRepository;

    // Inyección por constructor: Spring llama a este constructor y le pasa el repositorio
    public ArtistaServiceImpl(ArtistaRepository artistaRepository, AlbumRepository albumRepository) {
        this.artistaRepository = artistaRepository;
        this.albumRepository = albumRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArtistaResumen> buscarPorNombre(String texto) {
        List<ArtistaResumen> resultado = new ArrayList<>();

        for (Artista artista : artistaRepository.findByNombreContainingIgnoreCaseOrderByNombre(texto)) {
            resultado.add(new ArtistaResumen(artista.getId(), artista.getNombre()));
        }

        return resultado;
    }

    @Override
    @Transactional(readOnly = true)
    public ArtistaDetalle buscarPorId(Integer id) {
        Optional<Artista> artista = artistaRepository.findById(id);
        if (artista.isEmpty()) {
            throw new NoEncontradoException("No existe el artista " + id);
        }

        List<AlbumResumen> albums = new ArrayList<>();
        for (Album album : albumRepository.findByArtistaIdOrderByTitulo(id)) {
            albums.add(new AlbumResumen(album.getId(), album.getTitulo()));
        }

        return new ArtistaDetalle(artista.get().getId(), artista.get().getNombre(), albums);
    }
}