package com.example.listaintent;

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
    TextView txtResult;



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

        listProvincias = findViewById(R.id.lstProvincias);

        String[] provincias = {"Huelva", "Sevilla", "Cádiz",
                "Córdoba", "Jaén", "Granada","Málaga", "Almería"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                provincias);

        listProvincias.setAdapter(adapter);



        listProvincias.setOnItemClickListener((
                parent, view, position, id) -> {

            String provinciaSeleccionada = provincias[position];
            txtResult = findViewById(R.id.txtResultado);
            txtResult.setText(getString(R.string.result)+ provinciaSeleccionada);

            /* APUNTES:
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);

            Intent es una clase u objeto que sirve para:
            - Iniciar otra actividad y/o enviar información
            MainActivity.this indica desde dónde se envia el Intent
            DetailActivity.class indica a qué Activity se quiere ir
            En este caso: Se crea un Intent para ir desde MainActivity hacia DetailActivity

             */
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);

            /* APUNTES:
            Bundle bundle  = new Bundle();

            Un Bundle es como un empaquetado que permite guardar varios datos simples juntos.
             */

            Bundle bundle  = new Bundle();

            /* APUNTES
            bundle.putString("prov", provinciaSeleccionada);
            Guarda un texto (String) en el Bundle.
            "prov" es la clave
            provinciaSeleccionada es el valor
             */

            bundle.putString("prov", provinciaSeleccionada);

             /* APUNTES
            bundle.putInt("indice", position);
            Guarda un número entero (int) en el Bundle.
            "indice" es la clave
            position es el valor
             */
            bundle.putInt("indice", position);

             /* APUNTES
            intent.putExtras(bundle);

            Ahora mismo, el Bundle contiene 2 (un String y un int) par de datos ("key", valor)
             */
            intent.putExtras(bundle);

            /* APUNTES
            startActivity(intent);

            Inicia la nueva Activity usando el Intent creado.
            Android abre DetailActivity
             */

            startActivity(intent);
        });


    }
}