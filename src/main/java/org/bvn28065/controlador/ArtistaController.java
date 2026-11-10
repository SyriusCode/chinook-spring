package org.bvn28065.controlador;

import org.bvn28065.modelo.Album;
import org.bvn28065.modelo.Artista;
import org.bvn28065.repositorio.AlbumRepository;
import org.bvn28065.repositorio.ArtistaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;


@Controller
@RequestMapping("/artistas")
public class ArtistaController {
    private final ArtistaRepository artistaRepository;
    private final AlbumRepository albumRepository;

    public ArtistaController(ArtistaRepository artistaRepository, AlbumRepository albumRepository) {
        this.artistaRepository = artistaRepository;
        this.albumRepository = albumRepository;
    }

    // GET /artistas/{id}   Versión provisional: el controlador habla directamente con los repositorios
    @GetMapping("/{id}")
    @ResponseBody
    public String ficha(@PathVariable Integer id) {
        Optional<Artista> artista = artistaRepository.findById(id);
        if (artista.isEmpty()) {
            return "<h1>No existe el artista " + id + "</h1>";
        }
        StringBuilder html = new StringBuilder();
        html.append("<h1>").append(artista.get().getNombre()).append("</h1>");
        html.append("<ul>");
        for (Album album : albumRepository.findByArtistaIdOrderByTitulo(id)) {
            html.append("<li>").append(album.getTitulo()).append("</li>");
        }
        html.append("</ul>");
        return html.toString();
    }

    // GET /artistas   Sin @ResponseBody: "redirect:" hace que el navegador pida otra ruta (302)
    @GetMapping
    public String inicio() {
        return "redirect:/artistas/buscar";
    }
}
