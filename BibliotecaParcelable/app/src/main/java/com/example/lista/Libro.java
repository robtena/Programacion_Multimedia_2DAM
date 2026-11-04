package com.example.lista;

import java.io.Serializable;

// Al implementar 'Serializable', esta clase permite que sus objetos puedan ser
// convertidos a una secuencia de bytes para transmitirse entre distintas Activitys mediante un Intent.
public class Libro implements Serializable {
    private String titulo;
    private String autor;
    private int edad;

    public Libro(String titulo, String autor, int edad) {
        this.titulo = titulo;
        this.autor = autor;
        this.edad = edad;
    }

    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public int getEdad() { return edad; }
}