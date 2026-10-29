package com.example.lista;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetalleActivity extends AppCompatActivity {

    TextView txtProvincia;
    TextView txtIndice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalle);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtProvincia = findViewById(R.id.txtProvincia);
        txtIndice = findViewById(R.id.txtIndice);

        /*
        Provincia provincia = getIntent().getParcelableExtra("provincia");
            1- Lee el Parcel
            2- Usa CREATOR.createFromParcel()
            3- Llama al constructor: protected Provincia(Parcel in)
            4- Reconstruye el objeto Provincia
        El objeto llega idéntico al original.
         */
        Provincia provincia = getIntent().getParcelableExtra("provincia");

        if (provincia != null) {
            String provNombre = provincia.getNombre();
            int provIndice = provincia.getIndice()+1;

            txtProvincia.setText("Provincia: " + provNombre);
            txtIndice.setText("Índice en la lista: " + provIndice);
        }
    }
}