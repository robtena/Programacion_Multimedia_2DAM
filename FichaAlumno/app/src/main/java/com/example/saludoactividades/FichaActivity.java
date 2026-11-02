package com.example.saludoactividades;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FichaActivity extends AppCompatActivity {

    // Declaración de las referencias a los TextViews y al Button con los IDs requeridos
    TextView txtNombre;
    TextView txtEdad;
    TextView txtCurso;
    TextView txtRepetidor;
    Button btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ficha);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainFicha), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Enlazamos cada variable Java con su correspondiente elemento del XML
        txtNombre = findViewById(R.id.txtNombre);
        txtEdad = findViewById(R.id.txtEdad);
        txtCurso = findViewById(R.id.txtCurso);
        txtRepetidor = findViewById(R.id.txtRepetidor);
        btnVolver = findViewById(R.id.btnVolver);

        // Recuperamos el Bundle enviado desde MainActivity usando getExtras()
        Bundle bundle = getIntent().getExtras();

        // Comprobamos que el Bundle no sea null y que contenga las 4 claves esperadas antes de mostrar la ficha
        if (bundle != null
                && bundle.containsKey("EXTRA_NOMBRE")
                && bundle.containsKey("EXTRA_EDAD")
                && bundle.containsKey("EXTRA_CURSO")
                && bundle.containsKey("EXTRA_REPETIDOR")) {

            // Extraemos los datos del paquete Bundle con sus métodos específicos (getString, getInt, getBoolean)
            String nombre = bundle.getString("EXTRA_NOMBRE");
            int edad = bundle.getInt("EXTRA_EDAD");
            String curso = bundle.getString("EXTRA_CURSO");
            boolean repetidor = bundle.getBoolean("EXTRA_REPETIDOR");

            // Mostramos los datos formateados en cada TextView
            txtNombre.setText(getString(R.string.label_nombre, nombre));
            txtEdad.setText(getString(R.string.label_edad, edad)); // La edad se presenta en pantalla como texto dentro del formato
            txtCurso.setText(getString(R.string.label_curso, curso));

            // Presentamos el estado de repetidor como "Sí" o "No" según el valor del booleano
            String textoRepetidor = repetidor ? getString(R.string.text_si) : getString(R.string.text_no);
            // Hacemos uso del operador ternario si repetidor = true mostramos Sí, si repetidor = false mostrasmos No.
            txtRepetidor.setText(getString(R.string.label_repetidor, textoRepetidor));

        } else {
            // 5. Si faltan datos o el Bundle es null, mostramos un aviso comprensible para el usuario
            txtNombre.setText(getString(R.string.error_generico));
        }

        // 6. Asignamos el escuchador de clic al botón btnVolver para cerrar la ficha mediante finish()
        btnVolver.setOnClickListener(v -> finish());
    }
}