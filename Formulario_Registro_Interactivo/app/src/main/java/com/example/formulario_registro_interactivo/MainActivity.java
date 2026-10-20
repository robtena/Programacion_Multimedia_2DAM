package com.example.formulario_registro_interactivo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// Metodo MAIN

public class MainActivity extends AppCompatActivity {

    // Declaracion variables

    ImageButton imageButton;
    EditText etNombre;
    RadioGroup rgGenero;
    RadioButton rbHombre;
    RadioButton rbMujer;
    CheckBox cbTerminos;
    ToggleButton toggleBoletin;
    Switch switchNotificaciones;
    Spinner spinnerPais;
    Button btnEnviar;
    TextView tvResultado;

    // Se ejecuta la lógica dentro de Oncreate
    /* El metodo onCreate() se llama solo 1 vez cuando la pantalla se crea para que configuremos la interfaz visual
    setContentView(), inicialicemos las variables y preparemos los botones, Una vez que onCreate() termina,
    la pantalla se queda visible esperando a que el usuario interactúe con ella (pulsando botones, escribiendo en visores, etc.).
     También vinculamos/conectamos los componentes visuales con nuestro código mediante findViewById(...)*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main); // Cargamos el diseño visual, nos lo traemos al main desde el activity_main.xml
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 1. Vincular el ImageButton del XML
        imageButton = findViewById(R.id.imageButton);
        etNombre = findViewById(R.id.etNombre);
        rgGenero = findViewById(R.id.rgGenero);
        rbHombre = findViewById(R.id.rbHombre);
        rbMujer = findViewById(R.id.rbMujer);
        cbTerminos = findViewById(R.id.cbTerminos);
        toggleBoletin = findViewById(R.id.toggleBoletin);
        switchNotificaciones = findViewById(R.id.switchNotificaciones);
        spinnerPais = findViewById(R.id.spinnerPais);
        btnEnviar = findViewById(R.id.btnEnviar);
        tvResultado = findViewById(R.id.tvResultado);

        // Deshabilitar el botón "Enviar" hasta que se complete la validación
        btnEnviar.setEnabled(false);


        // 2. Asignar la acción al hacer clic
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Muestra un mensaje emergente (Toast)
                Toast.makeText(MainActivity.this, R.string.msg_image_clicked, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void validarFormulario() {
        // 1. Validar el campo de texto del Nombre
        String nombre = etNombre.getText().toString().trim(); // .trim() elimina los espacios en blanco al inicio y al final
        
        boolean esNombreValido = true;
        if (nombre.isEmpty()) {
            etNombre.setError(getString(R.string.error_name_empty)); // Muestra un globo rojo de error si está vacío
            esNombreValido = false;
        } else if (nombre.length() < 3) {
            etNombre.setError(getString(R.string.error_name_short)); // Muestra error si tiene menos de 3 caracteres
            esNombreValido = false;
        } else {
            etNombre.setError(null); // Borra el mensaje de error cuando el nombre ya es correcto
        }

        // 2. Validar que se haya seleccionado algún género en el RadioGroup
        // getCheckedRadioButtonId() devuelve -1 si no hay ningún RadioButton marcado
        boolean esGeneroValido = rgGenero.getCheckedRadioButtonId() != -1;

        // 3. Validar que la casilla de Términos y Condiciones esté marcada
        boolean sonTerminosValidos = cbTerminos.isChecked();

        // 4. Validar que se haya seleccionado un país distinto del por defecto
        // La posición 0 corresponde a "Selecciona un país", por lo que debe ser mayor que 0
        boolean esPaisValido = spinnerPais.getSelectedItemPosition() > 0;

        // El botón "Enviar" solo se habilita si TODAS las validaciones son verdaderas (true)
        boolean esFormularioValido = esNombreValido && esGeneroValido && sonTerminosValidos && esPaisValido;
        btnEnviar.setEnabled(esFormularioValido);
    }
}