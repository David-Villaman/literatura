package com.example.literatura.model;

public record DatosAutor(int id, String nombre, Integer birthYear, Integer deathYear) {

    @Override
    public String toString() {
        String nacimiento = (birthYear != null && birthYear > 0) ? String.valueOf(birthYear) : "¿?";
        String fallecimiento = (deathYear != null && deathYear > 0) ? String.valueOf(deathYear) : "¿?";

        return nombre + " (" + nacimiento + " - " + fallecimiento + ")";
    }

}

