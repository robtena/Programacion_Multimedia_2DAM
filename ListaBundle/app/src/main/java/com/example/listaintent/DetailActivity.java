package com.example.listaintent;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetailActivity extends AppCompatActivity {

    TextView txtResultadoDetail;
    TextView txtIndiceDetail;

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

        txtResultadoDetail = findViewById(R.id.txtResultadoDetail);
        txtIndiceDetail = findViewById(R.id.txtIndiceDetail);


        /* APUNTES
        Bundle bundle = getIntent().getExtras();

        getIntent() obtienes el Intent con el que se abrió este Activity
        getExtras() extraes todos los datos enviados en forma de Bundle
        El resultado se guarda en la variable tipo Bundle.
        */
        Bundle bundle = getIntent().getExtras();

        /* APUNTES
        if(bundle != null)
            Comprueba que el Bundle no venga nulo
            Evita errores si la Activity actual se abrió sin datos
            Es una buena práctica para evitar la excepción NullPointerException.

            String provincia = bundle.getString("prov");
            int indice = bundle.getInt("indice");

            txtResultadoDetail.setText("Provincia: " + provincia);
            txtIndiceDetail.setText("Indice en la lista: " + indice);
        }

         */
        if(bundle != null){
            String provincia = bundle.getString("prov");
            int indice = bundle.getInt("indice");

            txtResultadoDetail.setText("Provincia: " + provincia);
            txtIndiceDetail.setText("Indice en la lista: " + indice);
        }

    }
}