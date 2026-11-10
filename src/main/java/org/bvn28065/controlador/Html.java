package org.bvn28065.controlador;

import org.springframework.web.util.HtmlUtils;

public class Html {
    private Html() {
        // Sólo métodos estáticos, no se crean objetos de esta clase
    }

    /**
     * Escapa los caracteres especiales de HTML (&lt;, &gt;, &amp; y las comillas)
     * para que un dato se muestre como texto y el navegador no lo interprete.
     *
     * @param texto texto que viene de la base de datos o del usuario
     * @return el texto listo para incluirlo en la página
     */
    public static String esc(String texto) {
        if (texto == null) {
            return "";
        }

        // Con "UTF-8" solo cambia los caracteres peligrosos; las tildes y la ñ se quedan como están
        return HtmlUtils.htmlEscape(texto, "UTF-8");
    }
}
