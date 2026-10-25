package org.bvn28065.app;

import org.bvn28065.modelo.*;
import org.bvn28065.repositorio.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.Scanner;
import java.util.Optional;

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
    private void pruebaCrud() {
        System.out.println("--- Paso 4: CRUD ---");

        // INSERT: save() con un objeto sin id. Devuelve el objeto con el id ya asignado
        Genero nuevo = generoRepository.save(new Genero(null, "Género de prueba"));
        System.out.println("Insertado: " + nuevo);

        // UPDATE: save() con un objeto que ya tiene id
        nuevo.setNombre("Género cambiado");
        generoRepository.save(nuevo);

        // SELECT por id: devuelve un Optional, porque el id podría no existir.
        // isPresent(): ¿hay algo en la caja?   get(): saca el contenido
        Optional<Genero> leido = generoRepository.findById(nuevo.getId());
        if (leido.isPresent()) {
            System.out.println("Leído: " + leido.get());
        }

        // orElse(null): el contenido, o null si la caja está vacía
        Genero inexistente = generoRepository.findById(9999).orElse(null);
        if (inexistente == null) {
            System.out.println("Género 9999: no existe");
        }

        // isEmpty(): ¿está vacía la caja? Entonces lanzamos nosotros el error
        Optional<Genero> otro = generoRepository.findById(9999);
        try {
            if (otro.isEmpty()) {
                throw new IllegalArgumentException("No existe el género 9999");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error controlado: " + e.getMessage());
        }

        // DELETE
        generoRepository.deleteById(nuevo.getId());
        System.out.println("Géneros en total: " + generoRepository.count());
    }

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
        /*pruebaCrud();*/ //Temporal
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Elige una opción: ");
            try {
                switch (opcion) {
                    case 1 -> listarGeneros();
                    case 0 -> System.out.println("Hasta pronto.");
                    case 8 -> insertarAlbum();
                    case 9 -> cambiarTituloAlbum();
                    case 10 -> borrarAlbum();
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

    private void insertarAlbum() {
        String titulo = leerTexto("Título del álbum: ");
        int artistaId = leerEntero("Id del artista: ");
        // TODO 1: busca el artista con artistaRepository.findById(...). Devuelve un Optional<Artista>
        Optional<Artista> artista = artistaRepository.findById(artistaId);
        // TODO 2: si la caja está vacía (isEmpty()), lanza
        //         new IllegalArgumentException("No existe el artista " + artistaId)
        if (artista.isEmpty()) {
            throw new IllegalArgumentException("No existe el artista " + artistaId);
        }
        // TODO 3: guarda un álbum nuevo: albumRepository.save(new Album(null, titulo, artista.get())).
        Album album = albumRepository.save(new Album(null, titulo, artista.get()));
        //         get() saca el artista de la caja. save devuelve el álbum con su id ya asignado: guárdalo
        System.out.println("Álbum guardado con el id " + album.getId() + ".");
    }

    private void cambiarTituloAlbum() {
        int albumId = leerEntero("Id del álbum: ");
        String titulo = leerTexto("Nuevo título: ");
        // TODO 1: busca el álbum con albumRepository.findById(...). Devuelve un Optional<Album>
        Optional<Album> encontrado = albumRepository.findById(albumId);
        // TODO 2: si la caja está vacía, lanza IllegalArgumentException("No existe el álbum " + albumId)
        if (encontrado.isEmpty()) {
            throw new IllegalArgumentException("No existe el album " + albumId);
        }
        // TODO 3: saca el álbum con get() y cambia su título con setTitulo(...)
        Album album = encontrado.get();
        album.setTitulo(titulo);
        // TODO 4: guárdalo con albumRepository.save(...). Como ya tiene id, será un UPDATE
        albumRepository.save(album);
        System.out.println("Álbum actualizado.");
    }

    private void borrarAlbum() {
        int albumId = leerEntero("Id del álbum que quieres borrar: ");
        // TODO 1: si NO existe un álbum con ese id (albumRepository.existsById(...)),
        //         lanza IllegalArgumentException("No existe el álbum " + albumId)
        if (!albumRepository.existsById(albumId)) {
            throw new IllegalArgumentException("No existe el album " + albumId);
        }
        // TODO 2: bórralo con albumRepository.deleteById(...)
        albumRepository.deleteById(albumId);
        System.out.println("Álbum borrado.");
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