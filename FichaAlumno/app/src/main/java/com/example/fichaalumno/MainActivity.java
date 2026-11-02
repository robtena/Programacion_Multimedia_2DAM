package com.example.fichaalumno;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // Declaración de las referencias a las variables Java para los componentes del layout XML
    EditText edtNombre;
    EditText edtEdad;
    EditText edtCurso;
    CheckBox chkRepetidor;
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

        // Enlazamos cada variable Java con su elemento gráfico correspondiente en el layout usando su ID
        edtNombre = findViewById(R.id.edtNombre);
        edtEdad = findViewById(R.id.edtEdad);
        edtCurso = findViewById(R.id.edtCurso);
        chkRepetidor = findViewById(R.id.chkRepetidor);
        btnVerFicha = findViewById(R.id.btnVerFicha);

        // Asignamos el escuchador de eventos (listener) al botón btnVerFicha
        btnVerFicha.setOnClickListener(v -> {

            // 1. Validamos el campo nombre: leemos el texto, quitamos espacios con trim() y comprobamos que no esté vacío
            String nombre = edtNombre.getText().toString().trim();
            if (nombre.isEmpty()) {
                edtNombre.setError(getString(R.string.error_nombre_vacio));
                edtNombre.requestFocus();
                return; // Si está vacío, mostramos error y no continuamos
            }

            // 2. Validamos la edad utilizando el bloque de la ayuda (conversión a entero y rango entre 16 y 100)
            int edad;
            try {
                // Intentamos convertir la cadena de texto ingresada en un número entero
                edad = Integer.parseInt(edtEdad.getText().toString().trim());
            } catch (NumberFormatException e) {
                // Si el formato no es numérico entero válido, mostramos mensaje de error y no continuamos
                edtEdad.setError("Introduce una edad entera válida");
                edtEdad.requestFocus();
                return;
            }

            // Comprobamos si la edad ingresada está fuera del rango permitido (entre 16 y 100 años)
            if (edad < 16 || edad > 100) {
                edtEdad.setError("La edad debe estar entre 16 y 100");
                edtEdad.requestFocus();
                return; // Si no cumple el rango, mostramos error y detenemos la ejecución
            }

            // Validamos el campo curso: leemos el texto, quitamos espacios con trim() y comprobamos que no esté vacío
            String curso = edtCurso.getText().toString().trim();
            if (curso.isEmpty()) {
                edtCurso.setError(getString(R.string.error_curso_vacio));
                edtCurso.requestFocus();
                return; // Si está vacío, mostramos error y no continuamos
            }

            // Obtenemos el estado del CheckBox de repetidor (true si está marcado, false si no lo está)
            boolean esRepetidor = chkRepetidor.isChecked();

            // Creamos un objeto Bundle para guardar y empaquetar los cuatro datos solicitados (String, int, String, boolean)
            Bundle bundle = new Bundle();
            bundle.putString("EXTRA_NOMBRE", nombre);
            bundle.putInt("EXTRA_EDAD", edad);
            bundle.putString("EXTRA_CURSO", curso);
            bundle.putBoolean("EXTRA_REPETIDOR", esRepetidor);

            // Creamos un Intent explícito para navegar desde MainActivity hacia FichaActivity
            Intent intent = new Intent(MainActivity.this, FichaActivity.class);

            // Añadimos el Bundle agrupado al Intent mediante el metodo putExtras()
            intent.putExtras(bundle);

            // Solicitamos al sistema operativo iniciar la pantalla FichaActivity
            startActivity(intent);
        });
    }
}