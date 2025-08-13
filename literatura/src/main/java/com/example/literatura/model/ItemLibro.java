package com.example.literatura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ItemLibro(
        int id,
        String title,
        List<ItemAutor> authors,
        List<String> languages,
        Map<String, String> formats,
        int download_count
) { }
