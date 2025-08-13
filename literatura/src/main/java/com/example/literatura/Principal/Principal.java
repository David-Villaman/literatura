package com.example.literatura.Principal;

import com.example.literatura.api.ClienteGutendex;
import com.example.literatura.model.DatosAutor;
import com.example.literatura.model.DatosLibro;
import com.example.literatura.model.ItemLibro;
import com.example.literatura.model.RespuestaGutendex;
import com.example.literatura.service.TransformadorLibro;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private List<DatosAutor> datosAutores = new ArrayList<>();
    private List<DatosLibro> datosLibros = new ArrayList<>();





    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libro por título.
                    2 - Mostrar Libros registrados
                    3 - Mostrar Autores registrados
                    4 - Mostrar autores vivos en un determinado año
                    5 - Mostrar libros por idioma
                    
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    mostrarLibrosRegistrados();
                    break;
                case 3:
                    mostrarAutoresRegistrados();
                    break;
                case 4:
                    mostrarAutoresVivosEnAnio();
                    break;
                case 5:
                    mostrarLibrosPorIdioma();
                    break;

                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

    private void buscarLibroPorTitulo() {
        System.out.print("🔍 Ingresa el título o palabra clave del libro: ");
        String palabraClave = teclado.nextLine();

        ClienteGutendex cliente = new ClienteGutendex();
        RespuestaGutendex respuesta;

        try {
            respuesta = cliente.buscarLibros(palabraClave);
        } catch (IOException | InterruptedException e) {
            System.out.println("❌ Error al consultar la API: " + e.getMessage());
            return;
        }

        if (respuesta.results().isEmpty()) {
            System.out.println("⚠️ No se encontraron libros con ese título.");
            return;
        }

        // Tomar solo el primer libro
        ItemLibro item = respuesta.results().get(0);
        TransformadorLibro transformador = new TransformadorLibro();
        DatosLibro libro = transformador.convertir(item);

        // Mostrar el libro
        System.out.println("\n📘 Libro registrado:");
        System.out.println(libro);

        // Registrar el libro si no está duplicado
        if (!datosLibros.contains(libro)) {
            datosLibros.add(libro);
        }

        // Registrar autores únicos
        for (DatosAutor autor : libro.autores()) {
            if (!datosAutores.contains(autor)) {
                datosAutores.add(autor);
            }
        }
        System.out.println("\n");
    }



    private void mostrarLibrosRegistrados() {
        if (datosLibros.isEmpty()) {
            System.out.println("\n⚠️ No hay libros registrados aún.");
            return;
        }

        System.out.println("\n📚 Libros registrados:");
        int contador = 1;
        for (DatosLibro libro : datosLibros) {
            System.out.println(contador++ + ". " + libro);
        }
        System.out.println("\n");
    }

    private void mostrarAutoresRegistrados() {
        if (datosAutores.isEmpty()) {
            System.out.println("\n⚠️ No hay autores registrados aún.");
            return;
        }

        System.out.println("\n🖋️ Autores registrados:");
        int contador = 1;
        for (DatosAutor autor : datosAutores) {
            System.out.println(contador++ + ". " + autor.nombre());
        }
        System.out.println("\n");
    }

    private void mostrarAutoresVivosEnAnio() {
        System.out.print("📅 Ingresa el año que deseas consultar: ");
        int anio;
        try {
            anio = Integer.parseInt(teclado.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("❌ Año inválido.");
            return;
        }

        List<DatosAutor> autoresVivos = datosAutores.stream()
                .filter(autor -> autor.birthYear() != null && autor.birthYear() <= anio &&
                        (autor.deathYear() == null || autor.deathYear() > anio))
                .toList();

        if (autoresVivos.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año " + anio + ".");
        } else {
            System.out.println("\n🧠 Autores vivos en el año " + anio + ":");
            autoresVivos.forEach(autor -> System.out.println("• " + autor));
        }
        System.out.println("\n");
    }

    private void mostrarLibrosPorIdioma() {
        Set<String> idiomasDisponibles = datosLibros.stream()
                .flatMap(libro -> libro.idiomas().stream())
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        if (idiomasDisponibles.isEmpty()) {
            System.out.println("⚠️ No hay idiomas registrados aún.");
            return;
        }

        System.out.println("\n🌐 Idiomas disponibles:");
        idiomasDisponibles.forEach(idioma -> System.out.println("• " + idioma));

        System.out.print("\n🔍 Ingresa el idioma que deseas consultar: ");
        String idiomaBuscado = teclado.nextLine().trim().toLowerCase();

        List<DatosLibro> librosFiltrados = datosLibros.stream()
                .filter(libro -> libro.idiomas().stream()
                        .anyMatch(idioma -> idioma.equalsIgnoreCase(idiomaBuscado)))
                .toList();

        if (librosFiltrados.isEmpty()) {
            System.out.println("❌ No se encontraron libros en el idioma '" + idiomaBuscado + "'.");
        } else {
            System.out.println("\n📚 Libros en idioma '" + idiomaBuscado + "':");
            librosFiltrados.forEach(libro -> System.out.println(libro));
        }
        System.out.println("\n");
    }


}
