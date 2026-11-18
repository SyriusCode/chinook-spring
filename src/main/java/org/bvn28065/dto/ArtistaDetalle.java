package org.bvn28065.dto;

import java.util.List;

public record ArtistaDetalle(Integer id, String nombre, List<AlbumResumen> albums) {

}
