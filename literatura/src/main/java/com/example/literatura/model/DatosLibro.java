package com.example.literatura.model;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record DatosLibro(
        int id,
        String titulo,
        List<String> idiomas,
        int numeroDescargas,
        List<DatosAutor> autores,
        Map<String, String> formatos
) {
    @Override
    public String toString() {
        String nombresAutores = autores.stream()
                .map(DatosAutor::nombre)
                .collect(Collectors.joining(", "));
        String idiomasTexto = idiomas.isEmpty()
                ? "Desconocido"
                : String.join(", ", idiomas);

        return "Título: " + titulo +
                ", Idiomas disponibles: " + idiomasTexto +
                ", Descargas: " + numeroDescargas +
                ", Autores: " + nombresAutores;
    }
}
