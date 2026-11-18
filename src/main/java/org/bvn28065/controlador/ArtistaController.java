package org.bvn28065.controlador;

import org.bvn28065.dto.AlbumResumen;
import org.bvn28065.dto.ArtistaResumen;
import org.bvn28065.dto.ArtistaDetalle;
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

    // Inyección por constructor: Spring pasa aquí el servicio (paso 4)
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
            List<ArtistaResumen> artistas = artistaService.buscarPorNombre(texto);
            html.append("<p>Artistas que contienen «").append(Html.esc(texto)).append("»: ").append(artistas.size()).append("</p><ul>");
            for (ArtistaResumen artista : artistas) {
                html.append("<li><a href=\"/artistas/").append(artista.id()).append("\">")
                        .append(Html.esc(artista.nombre())).append("</a></li>");
            }
            html.append("</ul>");
        }
        return html.toString();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public String ficha(@PathVariable Integer id) {
        ArtistaDetalle artista = artistaService.buscarPorId(id);
        StringBuilder html = new StringBuilder();
        html.append("<h1>").append(Html.esc(artista.nombre())).append("</h1>");
        if (artista.albums().isEmpty()) {
            html.append("<p>").append(artista.albums().size()).append(" álbums:</p>");
        } else {
            html.append("<ul>");
            for (AlbumResumen album : artista.albums()) {
                html.append("<li><a href=\"/albums/").append(album.id()).append("\">")
                        .append(Html.esc(album.titulo())).append("</a></li>");
            }
            html.append("</ul>");
        }
        return html.toString();
    }
}