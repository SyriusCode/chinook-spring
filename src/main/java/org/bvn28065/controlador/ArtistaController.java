package org.bvn28065.controlador;

import org.bvn28065.modelo.Album;
import org.bvn28065.modelo.Artista;
import org.bvn28065.repositorio.AlbumRepository;
import org.bvn28065.repositorio.ArtistaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
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

    // GET /artistas   Sin @ResponseBody: "redirect:" hace que el navegador pida otra ruta (302)
    @GetMapping
    public String inicio() {
        return "redirect:/artistas/buscar";
    }

    // GET /artistas/buscar?texto=...
    @GetMapping("/buscar")
    @ResponseBody
    public String buscar(@RequestParam(defaultValue = "") String texto) {
        StringBuilder html = new StringBuilder();
        html.append("<h1>Buscar artistas</h1>");
        html.append("<form action=\"/artistas/buscar\" method=\"get\">")
                .append("<input name=\"texto\" value=\"").append(Html.esc(texto)).append("\">")
                .append(" <button>Buscar</button></form>");
        if (!texto.isBlank()) {
            List<Artista> artistas = artistaRepository.findByNombreContainingIgnoreCaseOrderByNombre(texto);
            html.append("<p>Artistas que contienen «").append(Html.esc(texto)).append("»: ")
                    .append(artistas.size()).append("</p><ul>");
            for (Artista artista : artistas) {
                html.append("<li><a href=\"/artistas/").append(artista.getId()).append("\">")
                        .append(Html.esc(artista.getNombre())).append("</a></li>");
            }
            html.append("</ul>");
        }
        return html.toString();
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
        html.append("<h1>").append(Html.esc(artista.get().getNombre())).append("</h1>");
        html.append("<ul>");
        for (Album album : albumRepository.findByArtistaIdOrderByTitulo(id)) {
            html.append("<li><a href=\"/albumes/").append(album.getId()).append("\">")
                    .append(Html.esc(album.getTitulo())).append("</a></li>");
        }
        html.append("</ul>");
        return html.toString();
    }
}
