package com.example.formulario_registro_interactivo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class MainActivity extends AppCompatActivity {
// 1. DECLARACIÓN DE VARIABLES DE LA INTERFAZ (Vistas)
    // Se declaran a nivel de clase para poder acceder a ellas desde cualquier metodo.*/

    EditText editText_Name;
    RadioGroup radioGroup_Gender;
    CheckBox checkBox_Terms;
    ToggleButton toggleButton_Newsletter;
    Switch switch_Notifications;
    Spinner spinner_Country;
    ImageButton imageButton;
    Button buttonSend;
    TextView textView_Status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*Llama a la implementación base de Android para restaurar el estado previo (por ejemplo, si el móvil se giró
        o el sistema cerró la app en segundo plano y recupera datos temporales mediante el Bundle).*/
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main); //Infla el archivo XML y lo convierte en los objetos visuales que se renderizan en memoria.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 2. VINCULACIÓN / REFERENCIAS GRÁFICAS (findViewById)
            // Se conectan las variables creadas en Java con los IDs definidos en activity_main.xml.
            // =========================================================================
            editText_Name = findViewById(R.id.editText_Name);
            radioGroup_Gender = findViewById(R.id.RadioGroup_Gender);
            checkBox_Terms = findViewById(R.id.checkBox);
            toggleButton_Newsletter = findViewById(R.id.toggleButton);
            switch_Notifications = findViewById(R.id.switch1);
            spinner_Country = findViewById(R.id.spinner);
            imageButton = findViewById(R.id.imageButton);
            buttonSend = findViewById(R.id.buttonSend);
            textView_Status = findViewById(R.id.textView);

                // 3.  Configurar los eventos de interacción (Listeners) Asignar la acción al hacer clic en 1 botón, marcar una casilla, etc...
                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Muestra un mensaje emergente (Toast)
                        Toast.makeText(MainActivity.this, R.string.msg_image_clicked, Toast.LENGTH_SHORT).show();
            }
        });
    }
}