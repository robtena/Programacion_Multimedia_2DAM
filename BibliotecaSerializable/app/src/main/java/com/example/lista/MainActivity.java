package com.example.lista;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // Declaración de las referencias a las variables Java para los componentes del layout XML
    EditText edtTitulo;
    EditText edtAutor;
    EditText edtEdad;
    Button btnVerFicha;


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

        // Referencia al ListView en el layout XML
        ListView listProvincias = findViewById(R.id.lstProvincias);

        // Array con los datos de las provincias a mostrar
        String[] provincias = {
                "Huelva", "Sevilla", "Cádiz", "Córdoba", "Jaén", "Granada",
                "Málaga", "Almería"};

        // El ArrayAdapter adapta el array de Strings al formato visual de cada elemento de la lista
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                provincias
        );

        // Asignamos el adaptador al ListView
        listProvincias.setAdapter(adapter);

        // Evento que se dispara al pulsar sobre un elemento de la lista
        listProvincias.setOnItemClickListener((parent, view, position, id) -> {
            String provinciaSeleccionada = provincias[position];
            txtResultado = findViewById(R.id.txtResultado);
            txtResultado.setText("Se ha pulsado " + provinciaSeleccionada);

            /* APUNTES
            Intent intent = new Intent(MainActivity.this, DetalleActivity.class);
                Crea un Intent para cambiar de pantalla.
                    MainActivity.this → Activity actual (origen).
                    DetalleActivity.class → Activity destino.
                Se prepara la navegación a DetalleActivity.
            */
            // Instanciamos el objeto Provincia con el nombre e índice seleccionado
            Provincia provinciaObj = new Provincia(provinciaSeleccionada, position);

            // Preparamos el Intent para navegar a DetalleActivity
            Intent intent = new Intent(MainActivity.this, InfantilActivity.class);

            // Adjuntamos el objeto Provincia al Intent (funciona porque Provincia implementa Serializable)
            intent.putExtra("provincia", provinciaObj); // <-- Objeto seriezable

            // Solicitamos al sistema operativo iniciar la nueva Activity
            startActivity(intent);
        });
    }
}