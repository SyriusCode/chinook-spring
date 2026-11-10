package org.bvn28065.servicio;

import org.bvn28065.modelo.Artista;
import org.bvn28065.repositorio.ArtistaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistaServiceImpl implements ArtistaService {

    private final ArtistaRepository artistaRepository;

    // Inyección por constructor: Spring llama a este constructor y le pasa el repositorio
    public ArtistaServiceImpl(ArtistaRepository artistaRepository) {
        this.artistaRepository = artistaRepository;
    }

    @Override
    public List<Artista> buscarPorNombre(String texto) {
        return artistaRepository.findByNombreContainingIgnoreCaseOrderByNombre(texto);
    }

    @Override
    public Artista buscarPorId(Integer id) {
        Optional<Artista> artista = artistaRepository.findById(id);
        if (artista.isEmpty()) {
            throw new NoEncontradoException("No existe el artista " + id);
        }
        return artista.get();
    }
}