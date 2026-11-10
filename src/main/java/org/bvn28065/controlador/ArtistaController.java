package org.bvn28065.controlador;

import org.bvn28065.modelo.Album;
import org.bvn28065.modelo.Artista;
import org.bvn28065.servicio.ArtistaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/artistas")
public class ArtistaController {

    private final ArtistaService artistaService;

    // El controlador ya no conoce los repositorios: solo el servicio
    public ArtistaController(ArtistaService artistaService) {
        this.artistaService = artistaService;
    }

    @GetMapping
    public String inicio() {
        return "redirect:/artistas/buscar";
    }

    @GetMapping("/buscar")
    @ResponseBody
    public String buscar(@RequestParam(defaultValue = "") String texto) {
        StringBuilder html = new StringBuilder();
        html.append("<h1>Buscar artistas</h1>");
        html.append("<form action=\"/artistas/buscar\" method=\"get\">")
                .append("<input name=\"texto\" value=\"").append(Html.esc(texto)).append("\">")
                .append(" <button>Buscar</button></form>");
        if (!texto.isBlank()) {
            List<Artista> artistas = artistaService.buscarPorNombre(texto);
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

    @GetMapping("/{id}")
    @ResponseBody
    public String ficha(@PathVariable Integer id) {
        Artista artista = artistaService.buscarPorId(id);
        StringBuilder html = new StringBuilder();
        html.append("<h1>").append(Html.esc(artista.getNombre())).append("</h1>");
        html.append("<ul>");
        for (Album album : artista.getAlbums()) {      // relación LAZY: se carga aquí, en el controlador
            html.append("<li><a href=\"/albums/").append(album.getId()).append("\">")
                    .append(Html.esc(album.getTitulo())).append("</a></li>");
        }
        html.append("</ul>");
        return html.toString();
    }
}