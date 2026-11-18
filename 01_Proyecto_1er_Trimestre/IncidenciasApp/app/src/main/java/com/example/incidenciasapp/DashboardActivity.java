package com.example.incidenciasapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.identity.cbor.Simple;

public class DashboardActivity extends AppCompatActivity {

    TextView tvWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 1. Vinculamos el TextView del diseño XML
                tvWelcome = findViewById(R.id.tvWelcome);

        // 2. Recogemos el rol que viajaba en el Intent
                String rol = getIntent().getStringExtra("EXTRA_ROL");

        // 3. Cambiamos el texto de bienvenida según el rol detectado
                if ("ADMIN".equals(rol)) {
                    tvWelcome.setText("Panel de Administrador");
                } else {
                    tvWelcome.setText("Panel de Empleado");
                }

        // 4. Inicializamos nuestro Modelo (BaseDatosHelper)
        BaseDatosHelper dbHelper = new BaseDatosHelper(this);

        // 5. Leemos las incidencias de la base de datos (Operación READ del CRUD)
        // ¿Qué es un CURSOR? Es un objeto que actúa como puntero para recorrer las filas
        // de la base de datos de una en una, evitando saturar la memoria RAM del dispositivo.
        android.database.Cursor cursor = dbHelper.obtenerTodasIncidencias();

        // ¿Qué es un ADAPTER (Adaptador)?
        // Es el puente o traductor entre los datos en bruto (el Cursor) y la interfaz visual (el ListView).
        // Coge la información de la base de datos y la "pinta" fila por fila en la lista de la pantalla.
        String[] desdeColumnas = {"titulo"}; // Columna de la tabla "incidencias" que queremos mostrar...
        //"Saca el texto de la columna 'titulo' de la base de datos."
        int[] hastaVistas = {android.R.id.text1}; // Vista predeterminada de Android para mostrar texto en una celda...
        //"Y mete ese texto dentro de la cajita de texto (TextView) que tiene el ID text1."

        android.widget.SimpleCursorAdapter adapter = new android.widget.SimpleCursorAdapter(
                this,                            // 1. Contexto
                android.R.layout.simple_list_item_1,    // 2. Plantilla visual de la fila
                cursor,                                 // 3. Origen de los datos (Cursor)
                desdeColumnas,                          // 4. Qué columnas leer
                hastaVistas,                            // 5. Dónde pintar los datos
                0                                       // 6. Flags / Opciones adicionales
        );

        // 6. Vinculamos y asignamos el adaptador al ListView de nuestra vista (activity_dashboard.xml)
        android.widget.ListView lvIncidencias = findViewById(R.id.lvIncidencias);
        lvIncidencias.setAdapter(adapter);// El ListView (lvIncidencias) es el que "adopta" al adaptador ya creado
        // lvIncidencias usa la información que contiene adapter para "pintar" todas las incidencias en la lista



    }
}