package org.bvn28065.servicio;

import org.bvn28065.modelo.Artista;
import java.util.List;

public interface ArtistaService {
    List<Artista> buscarPorNombre(String texto);

    Artista buscarPorId(Integer id);
}
