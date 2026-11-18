package org.bvn28065.controlador;

import org.bvn28065.dto.AlbumDetalle;
import org.bvn28065.dto.PistaResumen;
import org.bvn28065.servicio.AlbumService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
@RequestMapping("/albums")
public class AlbumController {

    private final AlbumService albumService;

    // Constructor
    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    // GET /albumes/{id}   Versión provisional, con los repositorios
    // TODO 2: anota el método para que atienda GET /albumes/{id} y devuelva el texto tal cual
    @GetMapping("/{id}")
    @ResponseBody
    public String ficha(@PathVariable Integer id) {
        AlbumDetalle album = albumService.buscarPorId(id);
        StringBuilder html = new StringBuilder();
        html.append("<h1>").append(Html.esc(album.titulo())).append("</h1>");
        // TODO 4: un párrafo "De " + enlace a la ficha del artista (/artistas/{id}) con su nombre escapado
        html.append("<p>De <a href=\"/artistas/").append(album.artista().id()).append("\">").append(Html.esc(album.artista().nombre())).append("</a><p>");
        html.append("<ol>");
        // TODO 5: un <li> por cada PistaResumen de album.pistas(), con nombre escapado y duración

        for (PistaResumen pista : album.pistas()) {
            html.append("<li>").append(Html.esc(pista.nombre())).append(" - ").append(pista.duracion()).append("</li>");
        }

        html.append("</ol>");

        return html.toString();
    }
}
