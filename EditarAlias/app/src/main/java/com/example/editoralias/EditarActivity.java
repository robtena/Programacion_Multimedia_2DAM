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

        // Al guardar, valida que el nuevo alias no esté vacío.
        btnGuardar.setOnClickListener(v -> {
            String nuevoAlias = edtAlias.getText() != null ? edtAlias.getText().toString().trim() : "";
            if (nuevoAlias.isEmpty()) {
                edtAlias.setError(getString(R.string.error_alias_vacio));
                edtAlias.requestFocus();
                return;
            }

            Intent respuesta = new Intent();
            respuesta.putExtra("alias_editado", nuevoAlias);
            // setResult establece el código de resultado (RESULT_OK = éxito/-1) y el Intent con los datos de respuesta
            setResult(RESULT_OK, respuesta);
            finish();
        });

        // Al cancelar, ejecuta setResult(RESULT_CANCELED) (código de cancelación/0) y finish().
        btnCancelar.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }
}
