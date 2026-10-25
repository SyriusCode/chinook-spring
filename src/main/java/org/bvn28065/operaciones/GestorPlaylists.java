package org.bvn28065.operaciones;

import lombok.RequiredArgsConstructor;
import org.bvn28065.modelo.Pista;
import org.bvn28065.modelo.PlayList;
import org.bvn28065.repositorio.PistaRepository;
import org.bvn28065.repositorio.PlayListRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// Clase aparte para las operaciones transaccionales: @Transactional solo actúa cuando
// el metodo se llama desde OTRO bean (el menú), no desde la propia clase.
@Component
@RequiredArgsConstructor
public class GestorPlaylists {

    private final PlayListRepository playlistRepository;
    private final PistaRepository pistaRepository;

    // Crea la playlist y le añade las pistas. Si una pista no existe, lanza una excepción.
    // @Transactional                     <-- de momento, comentada
    public PlayList crearConPistas(String nombre, List<Integer> idsPistas) {
        PlayList playlist = new PlayList();
        playlist.setNombre(nombre);
        playlist = playlistRepository.save(playlist);              // INSERT en Playlist

        for (Integer id : idsPistas) {
            Optional<Pista> pista = pistaRepository.findById(id);
            if (pista.isEmpty()) {
                throw new IllegalArgumentException("No existe la pista " + id);
            }
            playlist.getPistas().add(pista.get());
        }
        return playlistRepository.save(playlist);                  // INSERT en PlaylistTrack
    }

    // Añade una pista a una playlist que ya existe. Devuelve false si la pista ya estaba.
    // Necesita @Transactional porque getPistas() es una colección LAZY: la sesión debe seguir abierta.
    @Transactional
    public boolean anadirPista(Integer playlistId, Integer pistaId) {
        Optional<PlayList> playlist = playlistRepository.findById(playlistId);
        if (playlist.isEmpty()) {
            throw new IllegalArgumentException("No existe la playlist " + playlistId);
        }
        Optional<Pista> pista = pistaRepository.findById(pistaId);
        if (pista.isEmpty()) {
            throw new IllegalArgumentException("No existe la pista " + pistaId);
        }
        // Al ser un Set, add() devuelve false si la pista ya estaba.
        // Hibernate inserta la fila en PlaylistTrack al confirmar la transacción.
        return playlist.get().getPistas().add(pista.get());
    }
}