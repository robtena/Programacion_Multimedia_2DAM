package com.example.lista;

import java.io.Serializable;

// Al implementar 'Serializable', esta clase permite que sus objetos puedan ser
// convertidos a una secuencia de bytes para transmitirse entre distintas Activitys mediante un Intent.
public class Libro implements Serializable {
    /* Serializable: Muy fácil de implementar (ideal para clases sencillas y proyectos educativos),
    pero un pelo más lento en rendimiento. */
    /* Parcelable: Mucho más rápido y eficiente (recomendado por Android para rendimiento puro),
    pero obliga a escribir una cantidad enorme de código repetitivo (boilerplate) en Java. (Nota: En
    Kotlin con la anotación @Parcelize esto se reduce a una sola línea igual que Serializable, pero en Java es manual). /*
     */
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