package com.example.saludoactividades;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // Declaración de las referencias a los componentes visuales del layout XML
    EditText edtNombre;
    EditText edtEdad;
    Button btnEnviar;

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

        // Enlaza la variable Java 'edtNombre' con el elemento gráfico <TextInputEditText android:id="@+id/edtNombre"> del XML
        edtNombre = findViewById(R.id.edtNombre);

        // Enlaza la variable Java 'edtNombre' con el elemento gráfico <TextInputEditText android:id="@+id/edtNombre"> del XML
        edtEdad = findViewById(R.id.edtEdad);
        
        // Enlaza la variable Java 'btnEnviar' con el elemento gráfico <Button android:id="@+id/btnEnviar"> del XML
        btnEnviar = findViewById(R.id.btnEnviar);

        // Asigna el evento de clic al botón 'btnEnviar'
        btnEnviar.setOnClickListener(v -> {
            // Lee el texto ingresado en 'edtNombre', lo convierte a String y elimina los espacios en blanco al inicio y final
            String nombre = edtNombre.getText().toString().trim();

            // Comprueba si la cadena de texto está vacía
            if (nombre.isEmpty()) {
                // Muestra un mensaje flotante de error sobre la caja de texto cargando la cadena desde strings.xml
                edtNombre.setError(getString(R.string.error_nombre_vacio));
            } else {
                // Crea un Intent explícito para navegar desde MainActivity hacia SaludoActivity
                Intent intent = new Intent(MainActivity.this, SaludoActivity.class);

                // Adjunta el valor del nombre al Intent usando la etiqueta clave "EXTRA_NOMBRE"
                intent.putExtra("EXTRA_NOMBRE", nombre);

                // Solicita al sistema operativo iniciar la pantalla SaludoActivity
                startActivity(intent);
            }
        });

    }



}