package org.bvn28065.servicio;

import org.bvn28065.dto.ArtistaDetalle;
import org.bvn28065.dto.ArtistaResumen;

import java.util.List;

public interface ArtistaService {
    List<ArtistaResumen> buscarPorNombre(String texto);

    ArtistaDetalle buscarPorId(Integer id);
}
