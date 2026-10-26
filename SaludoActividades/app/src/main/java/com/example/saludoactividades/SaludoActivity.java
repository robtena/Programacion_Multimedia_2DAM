package com.example.saludoactividades;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SaludoActivity extends AppCompatActivity {

    // Declaración de variables de clase
    TextView txtSaludo;
    Button btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_saludo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainSaludo), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Conectamos el diseño XML a Java para poder manipularlo o asignarle eventos desde el código
        txtSaludo = findViewById(R.id.txtSaludo);
        btnVolver = findViewById(R.id.btnVolver);

        // Recuperamos el dato enviado por el intent (Nombre que pedíamos en la pantalla anterior)
        String nombre = getIntent().getStringExtra("EXTRA_NOMBRE");// Lo encontramos por su clave, la que le dimos en el MainActivity

        if (nombre != null) {
            txtSaludo.setText("Hola " + nombre);
        }else{
            txtSaludo.setText("Se ha producido un ERROR (reinténtalo de nuevo");
        }

        // Asigna un "escuchador" (listener) al botón btnVolver que se queda atento a cuando el usuario lo presione.
        btnVolver.setOnClickListener(v -> finish()); //v -> finish(); Es la función (lambda) que se ejecuta cuando ocurre el clic.
        /* finish(): Le indica al sistema operativo Android que destruya/cierre la actividad actual (SaludoActivity). Al cerrarse esta pantalla,
        el sistema muestra automáticamente la pantalla que estaba debajo en la pila (MainActivity). */

        }
}