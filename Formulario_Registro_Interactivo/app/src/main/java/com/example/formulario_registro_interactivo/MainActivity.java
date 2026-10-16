package com.example.formulario_registro_interactivo;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
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

public class MainActivity extends AppCompatActivity {

    // 1. DECLARACIÓN DE VARIABLES DE LA INTERFAZ (Vistas) EN ESPAÑOL
    // Las declaramos a nivel de clase (atributos) para poder usarlas desde cualquier método de esta Activity.
    EditText campoTextoNombre;
    RadioGroup grupoRadioGenero;
    CheckBox casillaTerminos;
    ToggleButton botonConmutadorBoletin;
    Switch interruptorNotificaciones;
    Spinner desplegablePais;
    ImageButton botonImagen;
    Button botonEnviar;
    TextView etiquetaEstado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*
         * Llamamos a la implementación base de Android (super.onCreate) para que restaure el estado previo
         * de la pantalla (por ejemplo, si el móvil se giró o el sistema cerró la app en segundo plano).
         */
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main); // Infla el archivo XML y convierte los elementos en objetos visuales en memoria.

        // Ajustamos los márgenes (padding) para que las barras del sistema no tapen nuestra interfaz.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 2. VINCULACIÓN / REFERENCIAS GRÁFICAS (findViewById)
        // Conectamos las variables en Java con sus IDs correspondientes definidos en activity_main.xml.
        campoTextoNombre = findViewById(R.id.editText_Name);
        grupoRadioGenero = findViewById(R.id.RadioGroup_Gender);
        casillaTerminos = findViewById(R.id.checkBox);
        botonConmutadorBoletin = findViewById(R.id.toggleButton);
        interruptorNotificaciones = findViewById(R.id.switch1);
        desplegablePais = findViewById(R.id.spinner);
        botonImagen = findViewById(R.id.imageButton);
        botonEnviar = findViewById(R.id.buttonSend);
        etiquetaEstado = findViewById(R.id.textView);

        // 3. CONFIGURACIÓN DE EVENTOS E INTERACCIONES (Listeners)

        // Evento de clic para el botón con imagen: muestra un mensaje emergente (Toast) al pulsar sobre él.
        botonImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, R.string.msg_image_clicked, Toast.LENGTH_SHORT).show();
            }
        });

        /*
         * Listener de tipo TextWatcher para el cuadro de texto del nombre.
         * Nos permite monitorear y validar lo que escribe el usuario en tiempo real.
         */
        campoTextoNombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Metodo obligatorio de la interfaz TextWatcher, pero no lo necesitamos para nuestra lógica.
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Cada vez que el usuario escribe o borra un carácter, comprobamos las reglas para activar/desactivar el botón.
                validarFormulario();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Mostramos una alerta visual directa en el cuadro de texto si el nombre es corto o está vacío.
                String textoNombre = s.toString().trim();
                if (textoNombre.isEmpty()) {
                    campoTextoNombre.setError(getString(R.string.error_name_empty));
                } else if (textoNombre.length() < 3) {
                    campoTextoNombre.setError(getString(R.string.error_name_short));
                } else {
                    campoTextoNombre.setError(null); // Quita el mensaje de error si el nombre ya es correcto.
                }
            }
        });

        // Evento de cambio para la casilla de términos y condiciones (CheckBox).
        casillaTerminos.setOnCheckedChangeListener((buttonView, isChecked) -> validarFormulario());

        // Evento de cambio para el grupo de botones de opción de género (RadioGroup).
        grupoRadioGenero.setOnCheckedChangeListener((group, checkedId) -> validarFormulario());

        // Evento de selección para el menú desplegable de país (Spinner).
        desplegablePais.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                validarFormulario();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                validarFormulario();
            }
        });

        /*
         * Evento de clic para el botón de Enviar:
         * Reúne toda la información ingresada en el formulario, arma la cadena de texto
         * resumen de forma directa y la publica en la etiqueta de texto (TextView).
         */
        botonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtenemos el texto ingresado en el campo del nombre.
                String nombre = campoTextoNombre.getText().toString().trim();

                // Averiguamos cuál botón de opción de género fue seleccionado.
                int idRadioSeleccionado = grupoRadioGenero.getCheckedRadioButtonId();
                String textoGenero = "";
                if (idRadioSeleccionado != -1) {
                    RadioButton radioSeleccionado = findViewById(idRadioSeleccionado);
                    textoGenero = radioSeleccionado.getText().toString();
                }

                // Consultamos el estado de activación del boletín informativo y notificaciones.
                boolean aceptaBoletin = botonConmutadorBoletin.isChecked();
                boolean aceptaNotificaciones = interruptorNotificaciones.isChecked();

                // Leemos el país seleccionado en el menú desplegable.
                String pais = desplegablePais.getSelectedItem().toString();

                // Armamos el texto resumen de forma directa y legible.
                String resumen = "RESUMEN DE REGISTRO:\n"
                        + "• Nombre: " + nombre + "\n"
                        + "• Género: " + textoGenero + "\n" +
                        "• País: " + pais + "\n" +
                        "• Newsletter: " + (aceptaBoletin ? "Activado" : "Desactivado") + "\n" +
                        "• Notificaciones: " + (aceptaNotificaciones ? "Activadas" : "Desactivadas");

                // Mostramos el resumen final en el TextView.
                etiquetaEstado.setText(resumen);

                // Desplazamos la pantalla hacia abajo para que el resumen sea visible inmediatamente.
                View vistaRaiz = findViewById(R.id.main);
                if (vistaRaiz instanceof ScrollView) {
                    ScrollView scrollView = (ScrollView) vistaRaiz;
                    scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
                }

                // Mostramos el aviso flotante (Toast) de confirmación de envío.
                Toast.makeText(MainActivity.this, R.string.toast_form_sent, Toast.LENGTH_SHORT).show();
            }
        });

        // Validación inicial al abrir la pantalla para mantener el botón de envío deshabilitado al principio.
        validarFormulario();
    }

    /**
     * Metodo auxiliar para validar si el formulario está completo y correcto.
     * Evalúa 4 condiciones indispensables:
     * 1. Nombre no vacío y de al menos 3 caracteres.
     * 2. Género seleccionado en el RadioGroup.
     * 3. Casilla de términos y condiciones marcada.
     * 4. País válido seleccionado en el Spinner (índice mayor a 0).
     */
    private void validarFormulario() {
        String nombre = campoTextoNombre.getText().toString().trim();
        boolean esNombreValido = !nombre.isEmpty() && nombre.length() >= 3;
        boolean esGeneroSeleccionado = grupoRadioGenero.getCheckedRadioButtonId() != -1;
        boolean sonTerminosAceptados = casillaTerminos.isChecked();
        boolean esPaisSeleccionado = desplegablePais.getSelectedItemPosition() > 0;

        // El botón solo se habilita (setEnabled(true)) si TODAS las condiciones son verdaderas.
        boolean esFormularioValido = esNombreValido && esGeneroSeleccionado && sonTerminosAceptados && esPaisSeleccionado;
        botonEnviar.setEnabled(esFormularioValido);
    }
}
