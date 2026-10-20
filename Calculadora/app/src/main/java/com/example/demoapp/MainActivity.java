package com.example.demoapp;

import com.example.demoapp.R.*;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * ARCHIVO DE LÓGICA Y CONTROLADOR - MainActivity.java
 * Esta clase representa la Actividad Principal de la aplicación (la pantalla).
 * Su función principal es:
 * 1. Cargar el diseño visual definido en activity_main.xml.
 * 2. Enlazar los componentes visuales (Botones y TextView) con las variables en Java usando findViewById().
 * 3. Escuchar las acciones del usuario mediante el evento OnClickListener.
 * 4. Gestionar la lógica de la calculadora (variables de estado, operaciones matemáticas y actualización del visor).
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Declaracion de variables
    TextView txtVisor;

    // Variables para memorizar el estado de la calculadora
    double primerNumero = 0;
    String operacion = "";

    Button btn0;
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
    Button btn7;
    Button btn8;
    Button btn9;
    Button btnDividir;
    Button btnMultiplicar;
    Button btnRestar;
    Button btnSumar;
    Button btnIgual;
    Button btnComa;
    Button btnReiniciar;

    /**
     * METODO DEL CICLO DE VIDA - onCreate
     * Se ejecuta una sola vez cuando la pantalla/actividad se crea por primera vez.
     * Es el lugar ideal para:
     * 1. Cargar el diseño visual desde XML (setContentView).
     * 2. Enlazar componentes de la vista con las variables Java (findViewById).
     * 3. Registrar los eventos de clic para responder a las pulsaciones de los botones (setOnClickListener).
     */
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

        txtVisor = findViewById(R.id.txtVisor);

        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btnDividir = findViewById(R.id.btnDividir);
        btnMultiplicar = findViewById(R.id.btnMultiplicar);
        btnRestar = findViewById(R.id.btnRestar);
        btnSumar = findViewById(R.id.btnSumar);
        btnIgual = findViewById(R.id.btnIgual);
        btnComa = findViewById(R.id.btnComa);
        btnReiniciar = findViewById(R.id.btnReiniciar);

        // Registro del evento de escucha (OnClickListener) en cada botón de la calculadora
        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);

        btnDividir.setOnClickListener(this);
        btnMultiplicar.setOnClickListener(this);
        btnRestar.setOnClickListener(this);
        btnSumar.setOnClickListener(this);
        btnComa.setOnClickListener(this);
        btnReiniciar.setOnClickListener(this);
        btnIgual.setOnClickListener(this);
    }

    /**
     * METODOS DE CONTROLADOR DE EVENTOS - onClick
     * Se ejecuta automáticamente cada vez que el usuario pulsa un botón que esté escuchando (setOnClickListener).
     * 
     * @param view La vista/componente visual (en este caso el Botón) que ha recibido el clic del usuario.
     */
    @Override
    public void onClick(View view) {
        // Obtenemos el identificador único (ID) del botón específico pulsado por el usuario
        int id = view.getId();

        // Evaluamos el ID pulsado y llamamos a la función correspondiente
        if (id == R.id.btn1) {
            agregarNumero("1");
        } else if (id == R.id.btn2) {
            agregarNumero("2");
        } else if (id == R.id.btn3) {
            agregarNumero("3");
        } else if (id == R.id.btn4) {
            agregarNumero("4");
        } else if (id == R.id.btn5) {
            agregarNumero("5");
        } else if (id == R.id.btn6) {
            agregarNumero("6");
        } else if (id == R.id.btn7) {
            agregarNumero("7");
        } else if (id == R.id.btn8) {
            agregarNumero("8");
        } else if (id == R.id.btn9) {
            agregarNumero("9");
        } else if (id == R.id.btn0) {
            agregarNumero("0");
        } else if (id == R.id.btnReiniciar) {
            // Reiniciamos el visor a 0 y limpiamos las variables de estado
            txtVisor.setText("0");
            primerNumero = 0;
            operacion = "";
        } else if (id == R.id.btnSumar) {
            seleccionarOperacion("+");
        } else if (id == R.id.btnRestar) {
            seleccionarOperacion("-");
        } else if (id == R.id.btnDividir) { //
            seleccionarOperacion("/");
        } else if (id == R.id.btnMultiplicar) { //
            seleccionarOperacion("*");
        } else if (id == R.id.btnIgual) {
            calcularResultado();
        } else if (id == R.id.btnComa) {
            agregarComa();
        }
    }

    // --- MÉTODOS DE CLASE ---

    // Metodo que recibe el nº pulsado y lo compara con el mostrado por la pantalla de la calculadora,
    // si es igual reemplaza ese nº por el 0 sino lo concatena al que ya hubiera.
    private void agregarNumero(String numero) {
        String textoActual = txtVisor.getText().toString();

        if (textoActual.equals("0")) {
            txtVisor.setText(numero);
        } else {
            txtVisor.setText(textoActual + numero);
        }
    }

    // Metodo que obtiene el texto como cadena "" y lo guarda en la variable "textoActual"
    private void agregarComa() {
        String textoActual = txtVisor.getText().toString();

        // Si NO contiene un punto (coma en Java), se lo añadimos
        if (!textoActual.contains(".")) {
            txtVisor.setText(textoActual + ".");
        }
    }

    /* Metodo que lee el nº que está en pantalla, lo convierte en decimal y vuele a poner el visor en 0
    (esperando que se pulse = o un nuevo nº + otro nuevo operador (+,-,/,*) */
    private void seleccionarOperacion(String op) {
        primerNumero = Double.parseDouble(txtVisor.getText().toString());
        operacion = op; /* Guardamos el contenido de la variable temporal op en la variable de clase operacion
        para que, más adelante cuando el usuario pulse el botón =, la app sepa qué cálculo matemático realizar. */
        txtVisor.setText("0");
    }

    /* Metodo sin parametros ya que hace uso de 2 variables de clase (operacion y primerNumero),
     Contiene un switch para contemplar la posibiidad de pulsar cualquiera de los operadores (+,-,/,*) */
    private void calcularResultado() {
        double segundoNumero = Double.parseDouble(txtVisor.getText().toString());
        double resultado = 0;

        switch (operacion) {
            case "+":
                resultado = primerNumero + segundoNumero;
                break;
            case "-":
                resultado = primerNumero - segundoNumero;
                break;
            case "*":
                resultado = primerNumero * segundoNumero;
                break;
            case "/":
                if (segundoNumero == 0) {
                    txtVisor.setText("Error");
                    return; // Detiene el metodo aquí para que se quede escrito "Error"
                } else {
                    resultado = primerNumero / segundoNumero;
                    break; // Sale del switch y continúa para mostrar el resultado
                }
        }
        // Mostramos el resultado en pantalla
        txtVisor.setText(String.valueOf(resultado)); /* Convertimos a String usando el metodo String.valueOf
        (la variable temporal "resultado") para mostrarla por la pantalla de la calculadora (Visor) */
    }
}