package org.bvn28065.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HolaController {
    @GetMapping("/hola")
    @ResponseBody
    public String hola() {
        return "¡Hola desde Spring!";
    }

    @GetMapping("/hola-html")
    @ResponseBody
    public String holaHtml() {
        String nombre = "Chinook";
        return "<h1>¡Hola desde " + nombre + "!</h1><p>Esta página la ha escrito un método Java.</p>";
    }
}
