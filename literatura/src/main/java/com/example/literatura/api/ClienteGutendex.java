package com.example.literatura.api;

import com.example.literatura.model.RespuestaGutendex;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class ClienteGutendex {

    private static final String BASE_URL = "https://gutendex.com/books?search=";
    private final HttpClient client;
    private final ObjectMapper mapper;

    public ClienteGutendex() {
        this.client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL) // ← Esta línea es clave
                .build();
        this.mapper = new ObjectMapper();
    }


    public RespuestaGutendex buscarLibros(String palabraClave) throws IOException, InterruptedException {
        // 1. Codificar la palabra clave
        String url = BASE_URL + URLEncoder.encode(palabraClave, StandardCharsets.UTF_8);

        // 2. Mostrar la URL que se va a consultar
        System.out.println("🌐 Consultando URL: " + url);

        // 3. Construir la solicitud
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        // 4. Enviar la solicitud y obtener la respuesta
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // 5. Mostrar el código de estado HTTP
        int statusCode = response.statusCode();
        System.out.println("🔁 Código de estado HTTP: " + statusCode);

        // 6. Verificar si la respuesta fue exitosa
        if (statusCode != 200) {
            throw new IOException("La API respondió con código " + statusCode);
        }

        // 7. Obtener y mostrar el cuerpo de la respuesta
        String json = response.body();
        System.out.println("📦 Cuerpo recibido:\n" + json);

        // 8. Validar que el cuerpo no esté vacío
        if (json == null || json.isBlank()) {
            throw new IOException("La respuesta de la API está vacía.");
        }

        // 9. Mapear el JSON a RespuestaGutendex
        return mapper.readValue(json, RespuestaGutendex.class);
    }



}
