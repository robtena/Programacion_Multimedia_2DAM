package com.example.wifipermission;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
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

    private ActivityResultLauncher<String> requestPermissionLauncher;

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

        // Registrar el lanzador para gestionar la respuesta del usuario a la solicitud de permiso
        requestPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        Toast.makeText(this, "Permiso concedido por el usuario", Toast.LENGTH_SHORT).show();
                        checkWifi();
                    } else {
                        Toast.makeText(this, "Permiso denegado por el usuario", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Comprobar y solicitar el permiso al iniciar la Activity
        checkAndRequestWifiPermission();
    }

    private void checkAndRequestWifiPermission() {
        // En Android 13+ (API 33+) se usa NEARBY_WIFI_DEVICES, en versiones anteriores se usa ACCESS_FINE_LOCATION
        // Operador ternario: (condicion ? valor_si_true : valor_si_false)
        // Comprueba si la versión de Android del dispositivo es Android 13 (API 33) o superior:
        // - Si es Android 13+: solicita NEARBY_WIFI_DEVICES (permiso específico para Wi-Fi).
        // - Si es Android 12 o inferior: solicita ACCESS_FINE_LOCATION (requerido en versiones anteriores).
        String permission = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                ? Manifest.permission.NEARBY_WIFI_DEVICES
                : Manifest.permission.ACCESS_FINE_LOCATION;

        /*Es importante hacer esta comprobación porque si el usuario ya aceptó el permiso una vez, no debes volver a
        mostrarle el cuadro emergente cada vez que abra la aplicación.*/
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
            // 🟢 SI YA TIENE PERMISO:
            // No hace falta mostrar la ventana emergente, ejecuta directamente la acción.
            checkWifi();
        } else {
            // 🔴 SI NO TIENE PERMISO:
            // Muestra la ventana flotante pidiendo permiso al usuario.
            requestPermissionLauncher.launch(permission);
        }
    }

    public void checkWifi() {
        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        if (wifi != null && wifi.isWifiEnabled()) {
            Toast.makeText(this, "Se pueden realizar operaciones con wifi", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "NO se pueden realizar operaciones con wifi", Toast.LENGTH_SHORT).show();
        }
    }
}
