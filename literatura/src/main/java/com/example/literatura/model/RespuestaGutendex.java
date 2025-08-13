package com.example.literatura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RespuestaGutendex(
        int count,
        String next,
        List<ItemLibro> results
) {
}
