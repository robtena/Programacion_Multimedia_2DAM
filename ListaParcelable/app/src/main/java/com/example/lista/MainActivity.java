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

        ListView listProvincias = findViewById(R.id.lstProvincias);

        String[] provincias = {
                "Huelva", "Sevilla", "Cádiz", "Córdoba", "Jaén", "Granada",
                "Málaga", "Almería"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                provincias
        );

        listProvincias.setAdapter(adapter);


        listProvincias.setOnItemClickListener((parent, view, position, id) -> {
            String provinciaSeleccionada = provincias[position];
            txtResultado = findViewById(R.id.txtResultado);
            txtResultado.setText("Se ha pulsado "+ provinciaSeleccionada);

            /* APUNTES
            Provincia provincia = new Provincia(provinciaSeleccionada, position);
                Se crea un objeto Java normal en memoria
                Se llama al constructor de la clase Provincia
            */
            Provincia provincia = new Provincia(provinciaSeleccionada, position);

            /*
            Intent intent = new Intent(MainActivity.this, DetalleActivity.class);
                Se crea el intent: sirve para abrir una Activity y enviar datos
                Parámetros: MainActivity.this → Activity origen
                            DetalleActivity.class → Activity destino
            Android ya sabe a qué pantalla tiene que ir.
             */
            Intent intent = new Intent(MainActivity.this, DetalleActivity.class);

            /*
            intent.putExtra("provincia", provincia);
                Añade datos extra al Intent:
                        "provincia" → clave
                        provincia → valor (objeto Parcelable)
            ¿Por qué funciona?
                Porque Provincia implementa Parcelable
                Internamente Android hace:
                    Provincia → Parcel → bytes → Intent → otra Activity
            El metodo que se llama automaticamente:
                provincia.writeToParcel(dest, flags);
            Android empaqueta el objeto usando writeToParcel().
             */
            intent.putExtra("provincia", provincia); // Parcelable

            /*
            startActivity(intent);
                    1- Android lee el Intent
                    2- Crea DetalleActivity
                    3- Pasa el Intent con sus extras
                    4- Llama a onCreate() de DetalleActivity
             */
            startActivity(intent);
        });
    }
}