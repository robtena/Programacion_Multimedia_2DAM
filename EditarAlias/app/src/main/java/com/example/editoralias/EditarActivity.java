package com.example.editoralias;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class EditarActivity extends AppCompatActivity {

    private TextInputEditText edtAlias;
    private Button btnGuardar;
    private Button btnCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_editar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainEditar), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtAlias = findViewById(R.id.edtAlias);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnCancelar = findViewById(R.id.btnCancelar);

        // Recupera "alias_actual" y úsalo para rellenar edtAlias. Controla el valor null.
        String aliasActual = getIntent().getStringExtra("alias_actual");
        if (aliasActual != null) {
            edtAlias.setText(aliasActual);
            edtAlias.setSelection(aliasActual.length());
        }

        // Al guardar, valida que el nuevo alias no esté vacío y lo devuelve a MainActivity
        btnGuardar.setOnClickListener(v -> {
            String nuevoAlias = edtAlias.getText() != null ? edtAlias.getText().toString().trim() : "";
            if (nuevoAlias.isEmpty()) {
                edtAlias.setError(getString(R.string.error_alias_vacio));
                edtAlias.requestFocus();
                return;
            }

            // Creamos un Intent vacío para empaquetar el resultado (no para abrir otra actividad)
            Intent respuesta = new Intent();
            respuesta.putExtra("alias_editado", nuevoAlias);
            
            // setResult entrega el código RESULT_OK (-1) y el Intent de respuesta a la actividad que está esperando
            setResult(RESULT_OK, respuesta);
            
            // finish() cierra esta actividad (EditarActivity) y regresa automáticamente a MainActivity
            finish();
        });

        // Al cancelar, ejecuta setResult(RESULT_CANCELED) (código de cancelación/0) y finish().
        btnCancelar.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }
}
