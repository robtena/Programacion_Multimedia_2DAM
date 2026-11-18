package com.example.incidenciasapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/*
 * CLASE CONTROLADOR: DashboardActivity
 * 
 * Panel principal de la app tras iniciar sesión. Muestra el listado de incidencias (READ)
 * y permite navegar al formulario de creación de nuevas incidencias (CREATE).
 */
public class DashboardActivity extends AppCompatActivity {

    private TextView tvWelcome;
    private Button btnNuevaIncidencia;
    private ListView lvIncidencias;
    private BaseDatosHelper dbHelper;

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

        // 1. Vinculamos las vistas del XML
        tvWelcome = findViewById(R.id.tvWelcome);
        btnNuevaIncidencia = findViewById(R.id.btnNuevaIncidencia);
        lvIncidencias = findViewById(R.id.lvIncidencias);

        // 2. Inicializamos nuestro Modelo (BaseDatosHelper)
        dbHelper = new BaseDatosHelper(this);

        // 3. Recogemos el rol que viajaba en el Intent desde MainActivity
        String rol = getIntent().getStringExtra("EXTRA_ROL");

        // 4. Cambiamos el texto de bienvenida según el rol detectado
        if ("ADMIN".equals(rol)) {
            tvWelcome.setText("Panel de Admin");
        } else {
            tvWelcome.setText("Panel de Empleado");
        }

        // 5. Botón Nueva Incidencia: Abre la pantalla de creación (CRUD: CREATE)
        btnNuevaIncidencia.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, CrearIncidenciaActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // onResume se ejecuta siempre que la pantalla vuelve a primer plano.
        // Recargamos la lista automáticamente al volver de crear una incidencia.
        cargarIncidencias();
    }

    private void cargarIncidencias() {
        // =====================================================================
        // CRUD: [READ / CONSULTAR] -> IMPLEMENTADO (Carga de incidencias en ListView)
        // CRUD: [CREATE / CREAR]   -> IMPLEMENTADO (Mediante CrearIncidenciaActivity)
        // =====================================================================
        
        // Obtenemos el Cursor con todas las incidencias de SQLite
        Cursor cursor = dbHelper.obtenerTodasIncidencias();

        // Configuramos el adaptador para pintar los títulos en la lista
        String[] desdeColumnas = {"titulo"};
        int[] hastaVistas = {android.R.id.text1};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                cursor,
                desdeColumnas,
                hastaVistas,
                0
        );

        // Asignamos el adaptador al ListView
        lvIncidencias.setAdapter(adapter);
    }
}
