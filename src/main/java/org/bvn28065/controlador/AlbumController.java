package org.bvn28065.controlador;

import org.bvn28065.modelo.Album;
import org.bvn28065.modelo.Artista;
import org.bvn28065.modelo.Pista;
import org.bvn28065.repositorio.AlbumRepository;
import org.bvn28065.repositorio.PistaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
@RequestMapping("/albums")
public class AlbumController {

    private final AlbumRepository albumRepository;
    private final PistaRepository pistaRepository;

    // Constructor
    public AlbumController(AlbumRepository albumRepository, PistaRepository pistaRepository) {
        this.albumRepository = albumRepository;
        this.pistaRepository = pistaRepository;
    }

    // GET /albumes/{id}   Versión provisional, con los repositorios
    // TODO 2: anota el método para que atienda GET /albumes/{id} y devuelva el texto tal cual
    @GetMapping("/{id}")
    @ResponseBody
    public String ficha(/* TODO 3: el parámetro id, que viene de la ruta */@PathVariable Integer id) {
        Optional<Album> album = albumRepository.findById(id);
        if (album.isEmpty()) {
            return "<h1>No existe el álbum " + id + "</h1>";
        }
        StringBuilder html = new StringBuilder();
        // TODO 4: el título del álbum en un <h1>, escapado con Html.esc
        html.append("<h1>").append(Html.esc(album.get().getTitulo())).append("</h1>");
        html.append("<ol>");
        // TODO 5: recorre pistaRepository.findByAlbumIdOrderById(id) y añade un <li> por pista
        //         con su nombre escapado y su duración entre paréntesis: "Hells Bells (5:12)"
        for (Pista pista : pistaRepository.findByAlbumIdOrderById(id)) {
            html.append("<li><a href=\"/artistas/").append(pista.getId()).append("\">")
                    .append(Html.esc(pista.getNombre())).append("</a></li>");
        }
        html.append("</ol>");
        return html.toString();
    }
}
