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
            Intent intent = new Intent(MainActivity.this, DetalleActivity.class);
                Crea un Intent para cambiar de pantalla.
                    MainActivity.this → Activity actual (origen).
                    DetalleActivity.class → Activity destino.
                Se prepara la navegación a DetalleActivity.
            */
            Provincia provinciaObj = new Provincia(provinciaSeleccionada, position);

            Intent intent = new Intent(MainActivity.this, DetalleActivity.class);
            intent.putExtra("provincia", provinciaObj); // <- objeto Serializable

            startActivity(intent);
        });
    }




}