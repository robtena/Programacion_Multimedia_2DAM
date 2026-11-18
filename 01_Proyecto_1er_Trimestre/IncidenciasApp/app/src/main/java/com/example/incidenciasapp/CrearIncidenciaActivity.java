package com.example.incidenciasapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/*
 * CLASE CONTROLADOR: CrearIncidenciaActivity
 * 
 * Gestiona el formulario para crear nuevas incidencias (Operación CREATE del CRUD)
 * y solicita en ejecución el permiso de la cámara (Requisito c de permisos).
 */
public class CrearIncidenciaActivity extends AppCompatActivity {

    /* Código identificador para reconocer la petición del permiso de cámara, lo necesitamos para
    poder hacer uso del metodo onRequestPermissionResult y tb es buena práctica por si en el futuro
    añadieramos muchos más y necesitáramos tenerlos identificados para trabajar con ellos */
    private static final int CODIGO_PERMISO_CAMARA = 100;

    private EditText etTitulo, etArea, etPrioridad;
    private Button btnTomarFoto, btnGuardar, btnVolver;
    private BaseDatosHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_crear_incidencia);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializamos la base de datos
        dbHelper = new BaseDatosHelper(this);

        // Vinculamos las vistas
        etTitulo = findViewById(R.id.etTitulo);
        etArea = findViewById(R.id.etArea);
        etPrioridad = findViewById(R.id.etPrioridad);
        btnTomarFoto = findViewById(R.id.btnTomarFoto);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnVolver = findViewById(R.id.btnVolver);

        // Botón Volver: Cierra esta activity y regresa inmediatamente a la anterior
        btnVolver.setOnClickListener(v -> finish());

        // Botón Guardar Incidencia (CRUD: CREATE)
        btnGuardar.setOnClickListener(v -> {
            String titulo = etTitulo.getText().toString().trim();
            String area = etArea.getText().toString().trim();
            String prioridad = etPrioridad.getText().toString().trim();

            // Validación que evita rellenar una incidencia con valores null y muestra mensaje tipo Toast
            if (titulo.isEmpty() || area.isEmpty() || prioridad.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            // Insertamos la incidencia en SQLite (Estado por defecto: "Pendiente", creador ID: 1)
            boolean insertado = dbHelper.insertarIncidencia(titulo, area, prioridad, "Pendiente", 1);

            if (insertado) {
                Toast.makeText(this, "¡Incidencia creada con éxito!", Toast.LENGTH_SHORT).show();
                finish(); // Cerramos la pantalla y volvemos al dashboard
            } else {
                Toast.makeText(this, "Error al guardar la incidencia", Toast.LENGTH_SHORT).show();
            }
        });

        // Botón Tomar Foto: Solicita permisos de cámara EN EJECUCIÓN
        btnTomarFoto.setOnClickListener(v -> comprobarYSolicitarPermisoCamera());
    }

    /*
     * Comprueba si el permiso de cámara ya ha sido concedido. 
     * Si no, lo solicita al usuario en tiempo de ejecución.
     */
    private void comprobarYSolicitarPermisoCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            // El permiso ya estaba concedido previamente
            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivity(intent);
        } else {
            // El permiso no está concedido, lo solicitamos en ejecución
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CAMERA},
                    CODIGO_PERMISO_CAMARA
            );
        }
    }


     /* Metodo que alcanza la respuesta del usuario cuando acepta o rechaza el permiso y hace saber
     al usuario la elección que ha hecho y su consecuencia */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CODIGO_PERMISO_CAMARA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // El usuario ACEPTÓ el permiso
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(intent);
            } else {
                // El usuario DENEGÓ el permiso
                Toast.makeText(this, "Permiso denegado. No se podrá adjuntar foto.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
