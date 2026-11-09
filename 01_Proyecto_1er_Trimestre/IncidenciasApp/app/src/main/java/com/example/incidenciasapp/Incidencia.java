package com.example.incidenciasapp;

/*
 * CLASE MODELO: Incidencia
 * 
 * En la arquitectura MVC (Modelo-Vista-Controlador), esta clase representa un 
 * "Objeto de Dominio" o POJO (Plain Old Java Object), Un POJO es simplemente una clase que solo sirve
 * para transportar datos ordenados de un sitio a otro en tu código.
 * 
 * ¿Para qué sirve?
 * Su única responsabilidad es actuar como una "estructura de datos" que empaqueta 
 * la información de una incidencia (id, título, área, prioridad, estado y creador) 
 * para moverla cómodamente entre la Base de Datos, el Controlador y la Pantalla (Vista).
 */
public class Incidencia {

    // Atributos privados que coinciden con las columnas de nuestra tabla "incidencias" en SQLite
    private int id;
    private String titulo;
    private String area;
    private String prioridad;
    private String estado;
    private int creadoPorId;

    /*
     * Constructor: Metodo que se ejecuta al crear un objeto Incidencia en memoria.
     * Nos permite inicializar todos sus valores de golpe.
     */
    public Incidencia(int id, String titulo, String area, String prioridad, String estado, int creadoPorId) {
        this.id = id;
        this.titulo = titulo;
        this.area = area;
        this.prioridad = prioridad;
        this.estado = estado;
        this.creadoPorId = creadoPorId;
    }

    // --- GETTERS Y SETTERS ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getCreadoPorId() {
        return creadoPorId;
    }

    public void setCreadoPorId(int creadoPorId) {
        this.creadoPorId = creadoPorId;
    }
}
