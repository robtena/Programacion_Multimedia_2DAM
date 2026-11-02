package com.example.editoralias;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView txtAlias;
    private Button btnEditar;

    // Declaración del lanzador para recibir el resultado de la segunda actividad (reemplaza a startActivityForResult)
    // Se declara como atributo de la clase, FUERA de onCreate()
    private final ActivityResultLauncher<Intent> editor =
            registerForActivityResult(
                    // Contrato que indica que iniciaremos una actividad esperando un resultado con un Intent
                    new ActivityResultContracts.StartActivityForResult(),
                    // Callback (función que se ejecuta automáticamente cuando EditarActivity termina y regresa)
                    result -> {
                        // Comprobamos si el resultado fue exitoso (RESULT_OK es una constante entera de Android, valor -1,
                        // que indica que la segunda actividad terminó correctamente al pulsar Guardar)
                        // y si el Intent de respuesta no es nulo
                        if (result.getResultCode() == RESULT_OK
                                && result.getData() != null) {
                            
                            // Extraemos el nuevo alias enviado desde EditarActivity usando la clave "alias_editado"
                            String alias = result.getData()
                                    .getStringExtra("alias_editado");
                            
                            // Validamos que el alias recibido no sea nulo ni esté vacío (eliminando espacios)
                            if (alias != null && !alias.trim().isEmpty()) {
                                // Actualizamos el TextView de la pantalla principal con el nuevo alias
                                txtAlias.setText(alias);
                            }
                        }
                    });

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

        // txtAlias debe ser un atributo y se inicializa en onCreate(), después de setContentView()
        txtAlias = findViewById(R.id.txtAlias);
        btnEditar = findViewById(R.id.btnEditar);

        // Asignamos el escuchador al botón Editar
        btnEditar.setOnClickListener(v -> {
            // Creamos un Intent explícito para navegar desde MainActivity hacia EditarActivity
            Intent intent = new Intent(MainActivity.this, EditarActivity.class);
            
            // Añadimos el alias actual como extra usando la clave "alias_actual" para que el editor lo reciba
            intent.putExtra("alias_actual", txtAlias.getText().toString());
            
            // Lanzamos EditarActivity utilizando el lanzador (editor) en lugar de startActivity()
            editor.launch(intent);
        });
    }
}
