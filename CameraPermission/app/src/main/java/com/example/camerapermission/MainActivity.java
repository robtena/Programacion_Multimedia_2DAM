package com.example.camerapermission;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // Declaración de las referencias a las variables Java para los componentes
    Button btnCamara;

    // Lanzador para ABRIR LA CÁMARA (Usamos TakePicturePreview para obtener una miniatura)
    private final ActivityResultLauncher<Void> cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.TakePicturePreview(),
            result -> {
                if (result != null) {
                    // Mostramos el mensaje de éxito (la foto se descarta automáticamente de la memoria)
                    Toast.makeText(this, "¡Foto capturada con éxito!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Se canceló la captura", Toast.LENGTH_SHORT).show();
                }
            }
    );

    // Lanzador para PEDIR EL PERMISO
    private final ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            isGranted -> {
                if (isGranted) {
                    // El usuario acaba de conceder el permiso, abrimos la cámara
                    cameraLauncher.launch(null);
                } else {
                    // El usuario denegó el permiso
                    Toast.makeText(this, "Se necesita el permiso de cámara para continuar", Toast.LENGTH_LONG).show();
                }
            }
    );

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

        // Enlazamos la variable Java con su elemento gráfico
        btnCamara = findViewById(R.id.btnCamara);

        // Pulsamos el botón
        btnCamara.setOnClickListener(v -> {
            verificarPermisoYAbrirCamara();
        });
    }

    // Metodo auxiliar para comprobar el estado del permiso
    private void verificarPermisoYAbrirCamara() {
        // Comprobamos si el permiso ya está concedido
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // Ya tenemos permiso, abrimos la cámara directamente
            cameraLauncher.launch(null);
        } else {
            // No tenemos permiso, lanzamos la petición al usuario
            requestPermissionLauncher.launch(Manifest.permission.CAMERA);
        }
    }
}