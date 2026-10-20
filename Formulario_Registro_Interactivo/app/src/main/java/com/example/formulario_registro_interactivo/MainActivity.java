package com.example.formulario_registro_interactivo;

// Importaciones de clases estándar de Android y Java necesarias para el funcionamiento de la interfaz y sus componentes.
import android.os.Bundle; // Objeto utilizado para guardar y recuperar datos de estado de la actividad (por ejemplo al girar la pantalla).
import android.text.Editable; // Interfaz para modificar y manipular el contenido editable de los campos de texto.
import android.text.TextWatcher; // Interfaz que escucha los eventos de cambio de texto en tiempo real en los EditText.
import android.view.View; // Clase base para todos los componentes visuales (vistas) de la interfaz de usuario.
import android.widget.AdapterView; // Listener para manejar selecciones en componentes como el Spinner.
import android.widget.Button; // Componente visual de botón estándar.
import android.widget.CheckBox; // Componente de casilla de verificación (marcado/desmarcado).
import android.widget.EditText; // Campo de entrada de texto interactivo.
import android.widget.ImageButton; // Botón con una imagen gráfica en su interior.
import android.widget.RadioButton; // Botón de opción circular dentro de un RadioGroup.
import android.widget.RadioGroup; // Contenedor que agrupa varios RadioButton asegurando que solo se seleccione uno.
import android.widget.ScrollView; // Contenedor que permite el desplazamiento vertical cuando el contenido supera la pantalla.
import android.widget.Spinner; // Lista desplegable de opciones.
import android.widget.Switch; // Interruptor deslizable de activación/desactivación.
import android.widget.TextView; // Componente de etiqueta de texto para mostrar información en pantalla.
import android.widget.Toast; // Mensaje emergente breve y flotante que aparece temporalmente en la pantalla.
import android.widget.ToggleButton; // Botón conmutador con dos estados visibles (encendido/apagado).

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// Declaración de la clase principal MainActivity que hereda de AppCompatActivity (ciclo de vida de una pantalla de Android).
public class MainActivity extends AppCompatActivity {

    // 1. DECLARACIÓN DE VARIABLES DE LA INTERFAZ (Vistas) EN ESPAÑOL
    // Las declaramos a nivel de clase (atributos globales) para acceder a ellas desde cualquier metodo de esta Activity.
    EditText campoTextoNombre; // Guardará la referencia del cuadro de entrada de texto del nombre.
    RadioGroup grupoRadioGenero; // Guardará la referencia del grupo de opciones de género.
    CheckBox casillaTerminos; // Guardará la referencia de la casilla de verificación de términos y condiciones.
    ToggleButton botonConmutadorBoletin; // Guardará la referencia del botón conmutador de suscripción al boletín.
    Switch interruptorNotificaciones; // Guardará la referencia del interruptor deslizable de notificaciones.
    Spinner desplegablePais; // Guardará la referencia del menú desplegable de selección de país.
    ImageButton botonImagen; // Guardará la referencia del botón con imagen interactiva.
    Button botonEnviar; // Guardará la referencia del botón principal de envío del formulario.
    TextView etiquetaEstado; // Guardará la referencia del texto de resultado donde se mostrará el resumen.

    // Método principal del ciclo de vida de la Activity que se ejecuta cuando la pantalla se crea por primera vez.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*
         * Llamamos a la implementación base de Android (super.onCreate) para que restaure el estado previo
         * de la pantalla (por ejemplo, si el móvil se giró o el sistema cerró la app en segundo plano).
         */
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // Asocia el archivo de diseño XML (activity_main.xml) con esta clase Java para dibujarlo en pantalla.
        setContentView(R.layout.activity_main);

        // Ajustamos los márgenes (padding) para que las barras del sistema (estado y navegación) no tapen la interfaz.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars()); // Obtiene la altura de las barras del sistema.
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom); // Aplica los márgenes de seguridad.
            return insets; // Devuelve los insets procesados.
        });

        // 2. VINCULACIÓN / REFERENCIAS GRÁFICAS (findViewById)
        // Conectamos cada variable Java con su ID único definido dentro del archivo de diseño XML.
        campoTextoNombre = findViewById(R.id.editText_Name); // Vincula la variable con el campo de texto del nombre.
        grupoRadioGenero = findViewById(R.id.RadioGroup_Gender); // Vincula la variable con el grupo de botones de género.
        casillaTerminos = findViewById(R.id.checkBox); // Vincula la variable con la casilla de términos y condiciones.
        botonConmutadorBoletin = findViewById(R.id.toggleButton); // Vincula la variable con el ToggleButton de boletín.
        interruptorNotificaciones = findViewById(R.id.switch1); // Vincula la variable con el Switch de notificaciones.
        desplegablePais = findViewById(R.id.spinner); // Vincula la variable con la lista desplegable de país.
        botonImagen = findViewById(R.id.imageButton); // Vincula la variable con el ImageButton interactivo.
        botonEnviar = findViewById(R.id.buttonSend); // Vincula la variable con el botón de envío.
        etiquetaEstado = findViewById(R.id.textView); // Vincula la variable con el TextView de resultado.

        // 3. CONFIGURACIÓN DE EVENTOS E INTERACCIONES (Listeners)

        // Evento de clic para el botón con imagen: muestra un mensaje emergente (Toast) al pulsar sobre él.
        botonImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Despliega un mensaje emergente corto (Toast) con el texto almacenado en R.string.msg_image_clicked.
                Toast.makeText(MainActivity.this, R.string.msg_image_clicked, Toast.LENGTH_SHORT).show();
            }
        });

        /*
         * Listener de tipo TextWatcher para el cuadro de texto del nombre.
         * Nos permite monitorear y validar lo que escribe el usuario en tiempo real.
         */
        campoTextoNombre.addTextChangedListener(new TextWatcher() {
            // Creamos una Clase Anónima que implementa la interfaz TextWatcher
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                /* Metodo obligatorio de la interfaz TextWatcher,  obliga a escribir 3 momentos del tiempo:
                Antes (beforeTextChanged), Durante (onTextChanged) y Después (afterTextChanged) del cambio de texto. */
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Cada vez que el usuario escribe o borra un carácter, comprobamos las reglas para activar/desactivar el botón.
                validarFormulario();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Extrae el texto escrito eliminando espacios en blanco innecesarios al inicio o al final.
                String textoNombre = s.toString().trim();

                // Evalúa si el texto está vacío o no cumple con el mínimo de 3 caracteres.
                if (textoNombre.isEmpty()) {
                    campoTextoNombre.setError(getString(R.string.error_name_empty)); // Muestra error si está vacío.
                } else if (textoNombre.length() < 3) {
                    campoTextoNombre.setError(getString(R.string.error_name_short)); // Muestra error si tiene menos de 3 letras.
                } else {
                    campoTextoNombre.setError(null); // Elimina el mensaje de error si el contenido es válido.
                }
            }
        });

        // Evento de cambio de estado para la casilla de verificación (CheckBox) de términos y condiciones.
        casillaTerminos.setOnCheckedChangeListener((buttonView, isChecked) -> validarFormulario());

        // Evento de cambio de opción seleccionada para el grupo de radio botones (RadioGroup) de género.
        grupoRadioGenero.setOnCheckedChangeListener((group, checkedId) -> validarFormulario());

        // Evento de selección de ítem para el menú desplegable (Spinner) de países.
        desplegablePais.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                validarFormulario(); // Revalida el formulario cuando el usuario selecciona un ítem de la lista.
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                validarFormulario(); // Revalida el formulario si no hay ítem seleccionado.
            }
        });

        /*
         * Evento de clic para el botón de Enviar:
         * Reúne toda la información ingresada en el formulario, arma la cadena de texto
         * resume de forma directa y la publica en la etiqueta de texto (TextView).
         */
        botonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtiene y limpia el texto del cuadro de entrada del nombre.
                String nombre = campoTextoNombre.getText().toString().trim();

                // Obtiene el ID del RadioButton que esté actualmente seleccionado en el RadioGroup.
                int idRadioSeleccionado = grupoRadioGenero.getCheckedRadioButtonId();
                String textoGenero = ""; // Variable para almacenar el texto del género elegido.

                if (idRadioSeleccionado != -1) { // Verifica si hay algún botón de género seleccionado.
                    RadioButton radioSeleccionado = findViewById(idRadioSeleccionado); // Busca la referencia del RadioButton seleccionado.
                    textoGenero = radioSeleccionado.getText().toString(); // Extrae su texto ("Hombre" o "Mujer").
                }

                // Consulta el estado booleano (true/false) del ToggleButton de boletín y del Switch de notificaciones.
                boolean aceptaBoletin = botonConmutadorBoletin.isChecked();
                boolean aceptaNotificaciones = interruptorNotificaciones.isChecked();

                // Obtiene en formato texto el nombre del país seleccionado en la posición actual del Spinner.
                String pais = desplegablePais.getSelectedItem().toString();

                // Construye la cadena de texto del resumen concatenando las variables recolectadas.
                String resumen = "RESUMEN DE REGISTRO:\n"
                        + "• Nombre: " + nombre + "\n"
                        + "• Género: " + textoGenero + "\n"
                        + "• País: " + pais + "\n"
                        + "• Newsletter: " + (aceptaBoletin ? "Activado" : "Desactivado") + "\n"
                        + "• Notificaciones: " + (aceptaNotificaciones ? "Activadas" : "Desactivadas");

                // Asigna el texto formateado al TextView para que el resultado sea visible en pantalla.
                etiquetaEstado.setText(resumen);

                // Busca la vista raíz ScrollView para realizar un desplazamiento automático suave hacia abajo.
                View vistaRaiz = findViewById(R.id.main);
                if (vistaRaiz instanceof ScrollView) { // Verifica si el contenedor raíz es efectivamente un ScrollView.
                    ScrollView scrollView = (ScrollView) vistaRaiz; // Realiza el casteo a ScrollView.
                    scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN)); // Realiza el desplazamiento hacia la parte inferior.
                }

                // Despliega un mensaje flotante Toast confirmando que el formulario se envió correctamente.
                Toast.makeText(MainActivity.this, R.string.toast_form_sent, Toast.LENGTH_SHORT).show();
            }
        });

        // Ejecuta la validación inicial al arrancar para garantizar que el botón Enviar empiece deshabilitado.
        validarFormulario();
    }

    /**
     * Metodo auxiliar que valida si se cumplen los 4 requisitos indispensables del formulario:
     * 1. Nombre no vacío y de al menos 3 caracteres.
     * 2. Género seleccionado en el RadioGroup.
     * 3. Casilla de términos y condiciones marcada.
     * 4. País válido seleccionado en el Spinner (índice mayor a 0).
     */
    private void validarFormulario() {
        // Lee el contenido actual del cuadro de texto del nombre.
        String nombre = campoTextoNombre.getText().toString().trim();

        // Evalúa si el nombre no está vacío Y contiene al menos 3 caracteres.
        boolean esNombreValido = !nombre.isEmpty() && nombre.length() >= 3;

        // Evalúa si se ha marcado alguna opción dentro del RadioGroup de género (ID distinto de -1).
        boolean esGeneroSeleccionado = grupoRadioGenero.getCheckedRadioButtonId() != -1;
        /* Si es diferente de -1, significa que el usuario ya ha pulsado al menos un
        género (Verdadero). Si sigue siendo -1, no ha seleccionado ninguno (Falso), ya que en Android
        VIEW_NO_ID devuelve valor -1"*/

        // Evalúa si la casilla de términos y condiciones está marcada (true).
        boolean sonTerminosAceptados = casillaTerminos.isChecked();

        // Evalúa si la opción elegida en el Spinner es mayor a 0 (omite la opción 0 "Selecciona un país").
        boolean esPaisSeleccionado = desplegablePais.getSelectedItemPosition() > 0;

        // El botón solo se habilita (setEnabled(true)) si TODAS las condiciones son verdaderas.
        boolean esFormularioValido = esNombreValido && esGeneroSeleccionado && sonTerminosAceptados && esPaisSeleccionado;

        // Habilita (true) o inhabilita (false) el botón Enviar según el resultado de la validación.
        botonEnviar.setEnabled(esFormularioValido);
    }
}
