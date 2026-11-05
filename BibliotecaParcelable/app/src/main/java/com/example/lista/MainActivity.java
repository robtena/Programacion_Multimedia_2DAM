package com.example.lista;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

        // Evento que activamos al pulsar sobre el boton "Clasificar libro"
        btnClasificar.setOnClickListener(v -> {
            // Recogemos el texto introducido por el usuario en cada campo
            // Hacemos uso del operador ternario => condicion ? valor_si_es_verdadero : valor_si_es_falso;
            // ? (el interrogante): Separa la condición del resultado que se devolverá si la condición se cumple
            // : (los dos puntos): Separa el valor verdadero del valor falso.
            /* En caso de que alguno de los edt...estén vacíos les asignaremos "" una cadena vacía para evitar
            el error falta de NullPointerException (la app petaría y se cerraría inesperadamente) */

            String titulo = edtTitulo.getText() != null ? edtTitulo.getText().toString().trim() : "";
            String autor = edtAutor.getText() != null ? edtAutor.getText().toString().trim() : "";
            String edadStr = edtEdad.getText() != null ? edtEdad.getText().toString().trim() : "";

            // Validamos que el título no esté vacío
            if (titulo.isEmpty()) {
                edtTitulo.setError(getString(R.string.error_título_vacio));
                edtTitulo.requestFocus();
                return;
            }

            // Validamos que el autor no esté vacío
            if (autor.isEmpty()) {
                edtAutor.setError(getString(R.string.error_autor_vacio));
                edtAutor.requestFocus();
                return;
            }

            // Validamos que la edad no esté vacía
            if (edadStr.isEmpty()) {
                edtEdad.setError(getString(R.string.error_edad_vacia));
                edtEdad.requestFocus();
                return;
            }

            int edad;
            /* Intentamos convertir el texto (String) introducido en edadStr a un número entero (int).
             Si escribimos letras  o otros caracteres en vez de números, Integer.parseInt() lanza un error llamado NumberFormatException.
             El catch atrapa ese error para evitar que la aplicación se cierre de golpe (crashee), mostrando un mensaje de error
             en la caja de texto y deteniendo la ejecución con return.
            */
            try {
                /* Convertimos (parseamos) el String a un número entero (int) utilizando Integer.parseInt(),
                ya que en Java no se puede hacer un casting directo de String a int. */
                edad = Integer.parseInt(edadStr);
            } catch (NumberFormatException e) {
                edtEdad.setError(getString(R.string.error_generico));
                edtEdad.requestFocus();
                return;
            }

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
