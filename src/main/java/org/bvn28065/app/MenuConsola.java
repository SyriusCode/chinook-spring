package org.bvn28065.app;

import org.bvn28065.modelo.*;
import org.bvn28065.repositorio.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.Scanner;

// CommandLineRunner: Spring llama a run() cuando termina de arrancar.
// @Component: Spring crea un objeto de esta clase y lo gestiona (un "bean").
@Component
@RequiredArgsConstructor
public class MenuConsola implements CommandLineRunner {

    private final Scanner teclado = new Scanner(System.in);

    private final GeneroRepository generoRepository;
    private final ArtistaRepository artistaRepository;
    private final AlbumRepository albumRepository;
    private final PistaRepository pistaRepository;
    private final PlayListRepository playlistRepository;

    // TEMPORAL: para llamarlo, escribe pruebaEntidades(); como primera línea de run()
    private void pruebaEntidades() {
        System.out.println("--- Paso 3: comprobar las entidades ---");

        // findById devuelve un Optional (una caja); get() saca su contenido. Lo verás con calma en el paso 4
        Album album = albumRepository.findById(1).get();
        // @ManyToOne es EAGER: el artista ya viene cargado con el álbum
        System.out.println("Álbum: " + album.getTitulo() + " de " + album.getArtista().getNombre());

        Pista pista = pistaRepository.findById(1).get();
        System.out.println("Pista: " + pista.getNombre() + " | " + pista.getDuracion() + " | " + pista.getGenero().getNombre());

        System.out.println("Playlists: " + playlistRepository.count());
    }

    private void pruebaRecursion() {
        Artista artista = new Artista();
        artista.setNombre("Banda de prueba");

        Album album = new Album();
        album.setTitulo("Disco de prueba");
        album.setArtista(artista);

        // Mantener los dos lados sincronizados: si no, el objeto en memoria queda incoherente
        artista.getAlbums().add(album);

        System.out.println(artista);
    }

    private void pruebaLazy() {
        Artista artista = artistaRepository.findById(1).get();
        System.out.println("Artista: " + artista.getNombre());
        System.out.println("Álbumes: " + artista.getAlbums().size());
    }

    // Spring ve que necesitamos un GeneroRepository y nos lo entrega (inyección de dependencias)
    // ** Comento el constructor como si estuviese BORRADO **
    /*public MenuConsola(GeneroRepository generoRepository) {
        this.generoRepository = generoRepository;
    }*/

    @Override
    public void run(String... args) {
        pruebaLazy(); //Temporal
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Elige una opción: ");
            try {
                switch (opcion) {
                    case 1 -> listarGeneros();
                    case 0 -> System.out.println("Hasta pronto.");
                    default -> System.out.println("Esa opción no existe.");
                }
            } catch (IllegalArgumentException e) {
                // Aquí llegarán los mensajes de las excepciones que lancemos nosotros
                System.out.println(e.getMessage());
            }
        } while (opcion != 0);
    }

    private void mostrarMenu() {
        System.out.println();
        System.out.println("========= CHINOOK =========");
        System.out.println(" 1. Listar géneros");
        System.out.println(" 2. Géneros con su número de pistas");
        System.out.println(" 3. Buscar artistas");
        System.out.println(" 4. Artistas más vendidos");
        System.out.println(" 5. Álbumes de un artista");
        System.out.println(" 6. Pistas de un género");
        System.out.println(" 7. Pistas de un álbum");
        System.out.println(" 8. Añadir un álbum");
        System.out.println(" 9. Cambiar el título de un álbum");
        System.out.println("10. Borrar un álbum");
        System.out.println("11. Playlists con su número de pistas");
        System.out.println("12. Añadir una pista a una playlist");
        System.out.println("13. Títulos de los álbumes de un artista (colección LAZY)");
        System.out.println(" 0. Salir");
        System.out.println("===========================");
    }

    // ---------- Opciones ----------

    private void listarGeneros() {
        for (Genero genero : generoRepository.findAll()) {
            System.out.println(genero);
        }
    }

    // ---------- Utilidades ----------

    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return teclado.nextLine().trim();
    }

    private int leerEntero(String mensaje) {
        while (true) {
            try {
                return Integer.parseInt(leerTexto(mensaje));
            } catch (NumberFormatException e) {
                System.out.println("Escribe un número entero.");
            }
        }
    }

    public ArtistaRepository getArtistaRepository() {
        return artistaRepository;
    }
}