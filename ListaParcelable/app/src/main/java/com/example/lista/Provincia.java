package com.example.lista;

import android.os.Parcel;
import android.os.Parcelable;

public class Provincia implements Parcelable {
    /* Parcelable: Indica que esta clase puede convertirse
                   a un Parcel (una especie de contenedor rápido de datos).
     Android usa Parcelable para pasar objetos entre Activities de forma eficiente.
    */
    /* Los atributos Son los datos que queremos transportar
       String y int son tipos compatibles con Parcel
    */
    private String nombre;
    private int indice;

    /* Constructor normal
        Se usa cuando creas el objeto normalmente en tu app
        Ejemplo: como se crea el objeto Provincia en el MainActivity:
     */
    public Provincia(String nombre, int indice) {
        this.nombre = nombre;
        this.indice = indice;
    }

    /* Constructor que recibe un Parcel
        Este constructor es CLAVE en Parcelable
        Android lo usa para reconstruir el objeto cuando llega a la otra Activity
        Lee los datos en el mismo orden en el que fueron escritos
        El orden debe coincidir con writeToParcel(). Si cambias el orden → datos corruptos.
     */
    protected Provincia(Parcel in) {
        nombre = in.readString();
        indice = in.readInt();
    }

    /* CREATOR obligatorio
        public static final Creator<Provincia> CREATOR = new Creator<Provincia>()
            Le dice a Android cómo crear el objeto desde un Parcel
            Es obligatorio para cualquier clase Parcelable
     */
    public static final Creator<Provincia> CREATOR = new Creator<Provincia>() {

        /* createFromParcel
            Llama al constructor que lee desde Parcel
            Convierte datos parcelables → objeto Provincia
        */
        @Override
        public Provincia createFromParcel(Parcel in) {
            return new Provincia(in);
        }

        /* newArray
            Se usa cuando Android necesita crear arrays de Provincias
            (la mayoría de veces no lo usas directamente)
         */
        @Override
        public Provincia[] newArray(int size) {
            return new Provincia[size];
        }
    };

    /* Getters
        Permiten acceder a los datos
        Buena práctica para mantener encapsulación
     */
    public String getNombre() {
        return nombre;
    }

    public int getIndice() {
        return indice;
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
        dest.writeString(nombre);
        dest.writeInt(indice);
    }
}
