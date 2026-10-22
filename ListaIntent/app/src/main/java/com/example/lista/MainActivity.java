package com.example.lista;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    ListView listProvincias;
    TextView txtResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

       listProvincias = findViewById(R.id.listProvincias);
       String[] provincias = { "Huelva", "Sevilla", "Cádiz",
               "Córdoba", "Jaén", "Granada", "Málaga", "Almería"};

       ArrayAdapter<String> adapter = new ArrayAdapter<>( // creado un arrayAdapter de tipo string al que hemos llamado adapter
               this,
               android.R.layout.simple_list_item_1, // Esto es un formato que tiene android que hace que cada elemento del array de lon ponga en una sola linea
               provincias);
       listProvincias.setAdapter(adapter); // a mi listView le estoy asignando en el adaptador "adapter" para que lo muestre



        listProvincias.setOnItemClickListener((parent, view, position, id) -> { // Cuando clico en un item va a coger el array de provincias(la posicion)º
            String provinciaSeleccionada = provincias[position]; // guardamos en la variable pronvinciaSeleccionada la posicion del array de provincias que hemos clicado anteriormente
            txtResultado = findViewById(R.id.txtResultado); // busco el textView "txtResultado" donde voy a mostrar el resultado
            txtResultado.setText(getString(R.string.result)+ " " + provinciaSeleccionada); // muestro el resultado en el textView "txtResultado"

            // la clase Intent es un objeto que sirve tanto para conectar de una pantalla antigua a una nueva como para conectar un dato simple
            Intent intent = new Intent(MainActivity.this, DetailActivity.class); // Recibe como parámetro el archivo de origen y archivo de destino
            // con la línea anterior hemos conectado y .java con otro y posteriormente vamos a añadirle el dato

            intent.putExtra("prov", provinciaSeleccionada); /* A una clave que yo le llamo "provincia" le voy a meter el valor que tiene provinciaSeleccionada (q es la que yo
            he pulsado previamente y la que se guarda en dicha variable) */

            startActivity(intent);// Inicio la actividad (Oncreate() de DetailActivity.java)

        });

    }
}