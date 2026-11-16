package com.example.smspermission;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_SEND_SMS = 1001;
    //REQ_SEND_SMS → código para identificar la solicitud de permiso
    private static final String DESTINO = "626128614";
    //DESTINO → número de teléfono al que se enviará el SMS
    private static final String MENSAJE = "¿Quedamos para comer?";
    //MENSAJE → texto del SMS


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

        /*
        Comprobación del permiso SEND_SMS
            Aquí pasa lo importante:
                -- Android no permite enviar SMS sin permiso
                -- Este código comprueba si el permiso ya fue concedido
         */
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) !=
                PackageManager.PERMISSION_GRANTED) {
            // 1) Si no hay permiso
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.SEND_SMS},
                    REQ_SEND_SMS
            );

        } else {
            // 2) Ya hay permiso, enviar
            enviarSms();
        }

    }

    private void enviarSms() {
        try {
            // En la mayoría de móviles funciona así:
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(DESTINO, null, MENSAJE, null, null);
            Toast.makeText(this, "SMS enviado", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error enviando SMS: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // Este metodo se ejecuta cuando el usuario responde al dialogo del permiso.
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQ_SEND_SMS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enviarSms();
            } else {
                Toast.makeText(this, "Permiso SEND_SMS denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}