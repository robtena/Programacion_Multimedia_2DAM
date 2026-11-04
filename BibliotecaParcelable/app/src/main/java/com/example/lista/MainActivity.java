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

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    // Declaración de las referencias a las variables Java para los componentes del layout XML
    TextInputEditText edtTitulo;
    TextInputEditText edtAutor;
    TextInputEditText edtEdad;
    MaterialButton btnClasificar;


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

        // Referencias que recogemos del layout XML
        TextInputEditText edtTitulo = findViewById(R.id.edtTitulo);
        TextInputEditText edtAutor = findViewById(R.id.edtAutor);
        TextInputEditText edtEdad = findViewById(R.id.edtEdad);
        MaterialButton btnClasificar = findViewById(R.id.btnClasificar);

        // Evento que se dispara al pulsar sobre el boton "Clasificar libro"
        btnClasificar.setOnClickListener(v -> {
            // Recogemos el texto introducido por el usuario en cada campo
            String titulo = edtTitulo.getText().toString().trim();
            String autor = edtAutor.getText().toString().trim();
            String edadStr = edtEdad.getText().toString().trim();
            // Recogemos el texto introducido en el campo de edad y eliminamos espacios en blanco

            /* Convertimos (parseamos) el String a un número entero (int) utilizando Integer.parseInt(),
            ya que en Java no se puede hacer un casting directo de String a int. */
            int edad = Integer.parseInt(edadStr);

            // Instanciamos el objeto Libro (que implementa Parcelable) con los datos introducidos
            Libro miLibro = new Libro(titulo, autor, edad);

            // Decidimos qué Activity abrir según los datos introducidos.
            Intent intent; // Declaramos una variable intent de tipo Intent para usarla en la validación...
            if (edad < 12) {
                // En este caso abrimos intanfilActivity xq edad < 12
                intent = new Intent(MainActivity.this, InfantilActivity.class);
            } else {
                // En cualquier otro edad será > 12 así que abrimos generalActivity
                intent = new Intent(MainActivity.this, GeneralActivity.class);
            }

            // Enviamos el objeto parcelable al Intent con su "clave" y su valor
            intent.putExtra("libro", miLibro);

            // Lanzamos la nueva Activity
            startActivity(intent);
        });
    }
}
