package org.bvn28065.controlador;

import org.bvn28065.dto.GeneroResumen;
import org.bvn28065.servicio.GeneroService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/generos")
public class GeneroController {

    // TODO 5: atributo con el servicio (la interfaz, no la implementación) y constructor que lo recibe
    private final GeneroService generoService;

    // Constructor
    public GeneroController(GeneroService generoService) {
        this.generoService = generoService;
    }

    // GET /generos
    @GetMapping
    @ResponseBody
    public String listar() {
        StringBuilder html = new StringBuilder();
        html.append("<h1>Géneros</h1><ul>");
        // TODO 6: recorre generoService.listar() y añade un <li> con el nombre escapado de cada género
        for (GeneroResumen genero : generoService.listar()) {
            html.append("<li>").append(Html.esc(genero.nombre())).append("</li>");
        }
        html.append("</ul>");
        return html.toString();
    }
}
