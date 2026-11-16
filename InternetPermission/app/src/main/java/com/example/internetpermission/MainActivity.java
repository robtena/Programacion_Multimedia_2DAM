package com.example.internetpermission;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private WebView enlace;

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

        // Vincula la vista del XML
        enlace = findViewById(R.id.webView);

        // Habilita JavaScript (necesario para la mayoría de webs)
        /* Esta línea es muy importante porque activa la ejecución de JavaScript en ese navegador integrado. Páginas
        como Google y la mayoría de webs actuales lo necesitan para funcionar y mostrarse correctamente. */
        enlace.getSettings().setJavaScriptEnabled(true);

        // Abre enlaces dentro de la app (evita que se abra Chrome externamente)
        /*Por defecto, si un WebView intenta abrir una URL o haces clic en un enlace dentro de él, Android abrirá el
        navegador predeterminado del sistema (como Google Chrome) sacando al usuario de tu aplicación. Al asignarle un
        nuevo WebViewClient, le estás diciendo a Android que maneje las redirecciones y la carga de la página directamente
        dentro de tu propia app, evitando que se abra una aplicación externa.*/
        enlace.setWebViewClient(new WebViewClient());

        // Carga la página web
        enlace.loadUrl("https://www.google.com");
    }
}