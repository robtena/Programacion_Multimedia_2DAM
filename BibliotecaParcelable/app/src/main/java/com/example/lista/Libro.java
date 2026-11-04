package com.example.lista;

import android.os.Parcel;
import android.os.Parcelable;

public class Libro implements Parcelable {
    /* Parcelable: Indica que esta clase puede convertirse
                   a un Parcel (una especie de contenedor rápido de datos).
     Android usa Parcelable para pasar objetos entre Activities de forma eficiente.
    */
    /* Los atributos Son los datos que queremos transportar
       String y int son tipos compatibles con Parcel
    */
    private String titulo;
    private String autor;
    private int edad;

    /* Constructor normal
        Se usa cuando creas el objeto normalmente en tu app
        Ejemplo: como se crea el objeto Libro en el MainActivity:
     */
    public Libro(String titulo, String autor, int edad) {
        this.titulo = titulo;
        this.autor = autor;
        this.edad = edad;
    }

    /* Constructor que recibe un Parcel
        Este constructor es CLAVE en Parcelable
        Android lo usa para reconstruir el objeto cuando llega a la otra Activity
        Lee los datos en el mismo orden en el que fueron escritos
        El orden debe coincidir con writeToParcel(). Si cambias el orden → datos corruptos.
     */
    protected Libro(Parcel in) {
        titulo = in.readString();
        autor = in.readString();
        edad = in.readInt();
    }

    /* CREATOR obligatorio
        public static final Creator<Libro> CREATOR = new Creator<Libro>()
            Le dice a Android cómo crear el objeto desde un Parcel
            Es obligatorio para cualquier clase Parcelable
     */
    public static final Creator<Libro> CREATOR = new Creator<Libro>() {

        /* createFromParcel
            Llama al constructor que lee desde Parcel
            Convierte datos parcelables → objeto Libro
        */
        @Override
        public Libro createFromParcel(Parcel in) {
            return new Libro(in);
        }

        /* newArray
            Se usa cuando Android necesita crear arrays de Libros
            (la mayoría de veces no lo usas directamente)
         */
        @Override
        public Libro[] newArray(int size) {
            return new Libro[size];
        }
    };

    /* Getters
        Permiten acceder a los datos
        Buena práctica para mantener encapsulación
     */
    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public int getEdad() {
        return edad;
    }

    /* describeContents
        Normalmente devuelve 0
        Solo se usa si el objeto contiene FileDescriptor
        En el 99% de casos → 0
    */
    @Override
    public int describeContents() {
        return 0;
    }

    /* writeToParcel
        Aquí empaquetas el objeto para enviarlo:
            dest → el Parcel donde se guardan los datos
            writeString() y writeInt() escriben los valores
            El orden importa. Debe coincidir con el constructor que lee
    */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(titulo);
        dest.writeString(autor);
        dest.writeInt(edad);
    }
}
