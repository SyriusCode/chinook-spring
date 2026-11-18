package org.bvn28065.dto;

import java.util.List;

public record AlbumDetalle(Integer id, String titulo, ArtistaResumen artista, List<PistaResumen> pistas) {

}
