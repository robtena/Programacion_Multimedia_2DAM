package com.example.lista;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class GeneralActivity extends AppCompatActivity {

    // Declaramos los TextView de activity_general.xml
    TextView txtTitulo;
    TextView txtAutor;
    TextView txtEdad;
    MaterialButton btnVolver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_general);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Vinculamos con los IDs del XML de general
        txtTitulo = findViewById(R.id.txtTitulo);
        txtAutor = findViewById(R.id.txtAutor);
        txtEdad = findViewById(R.id.txtEdad);
        btnVolver = findViewById(R.id.btnVolver);

        /* APUNTES
        Bundle bundle = getIntent().getExtras();
               getIntent() obtiene el Intent con el que se abrió esta Activity.
               getExtras() extrae todos los datos enviados en forma de Bundle.
               El resultado se guarda en la variable bundle.
               Aquí se recupera la “caja” con los datos enviados desde la Activity anterior.
         */

        // Recogemos el objeto Libro que hemos enviado con el Intent
        // Se usa getSerializableExtra() y un cast explícito a (Libro) para deserializarlo.
        Libro miLibro = (Libro) getIntent().getSerializableExtra("libro");

        // Si el objeto se ha recibido correctamente, mostramos sus campos en las vistas (en pantalla)
        if (miLibro != null) {
            txtTitulo.setText("Título: " + miLibro.getTitulo());
            txtAutor.setText("Autor: " + miLibro.getAutor());
            txtEdad.setText("Edad recomendada: " + miLibro.getEdad());
        }
        // Evento para el botón Volver (cierra esta Activity y vuelve a la anterior)
        btnVolver.setOnClickListener(v -> finish());
    }
}