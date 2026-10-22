package com.example.lista;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetailActivity extends AppCompatActivity {


    TextView txtResultadoDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String provincia = getIntent().getStringExtra("prov"); /* Con esto recogemos el dato que hemos puesto en el
        MainActivity.java filtrándolo por la clave "prov" y lo guardamos en la variable provincia */
        if(provincia != null){
            txtResultadoDetail = findViewById(R.id.txtResultadoDetail); // Inicializo/vinculo el TextView con su ID del layout XML
            txtResultadoDetail.setText(getString(R.string.result) + " " + provincia); // Asigno el texto concatenando el recurso de texto (R.string.result) con el nombre de la provincia recibida
        }

    }
}