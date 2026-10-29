package com.example.lista;

import java.io.Serializable;

public class Provincia implements Serializable {
    private String nombre;
    private int indice;

    public Provincia(String nombre, int indice) {
        this.nombre = nombre;
        this.indice = indice;
    }

    public String getNombre() {
        return nombre;
    }

    public int getIndice() {
        return indice;
    }
}
