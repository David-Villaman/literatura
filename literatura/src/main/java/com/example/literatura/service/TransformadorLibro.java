package com.example.literatura.service;

import com.example.literatura.model.DatosAutor;
import com.example.literatura.model.DatosLibro;
import com.example.literatura.model.ItemAutor;
import com.example.literatura.model.ItemLibro;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TransformadorLibro {

    public DatosLibro convertir(ItemLibro item) {
        List<DatosAutor> autores = item.authors().isEmpty()
                ? List.of(new DatosAutor(0, "Desconocido", null, null))
                : item.authors().stream()
                .map(a -> new DatosAutor(
                        a.id(),
                        a.name(),
                        a.birth_year(),
                        a.death_year()
                ))
                .collect(Collectors.toList());

        List<String> idiomas = item.languages().isEmpty()
                ? List.of("Desconocido")
                : item.languages();

        Map<String, String> formatos = item.formats() != null
                ? item.formats()
                : Map.of();

        return new DatosLibro(
                item.id(),
                item.title(),
                idiomas,
                item.download_count(),
                autores,
                formatos
        );
    }


    public List<DatosLibro> convertirLista(List<ItemLibro> items) {
        return items.stream()
                .map(this::convertir)
                .toList();
    }
}
