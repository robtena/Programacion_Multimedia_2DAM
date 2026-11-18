package com.example.incidenciasapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/*
 * CLASE CONTROLADOR / VISTA: MainActivity
 * 
 * Esta clase representa la pantalla de inicio de sesión (Login).
 * En el patrón MVC, actúa como Controlador porque:
 * 1. Recoge las interacciones del usuario en la Vista (XML).
 * 2. Llama al Modelo (BaseDatosHelper) para validar los datos.
 * 3. Decide qué hacer a continuación en función de la respuesta (mostrar error o dar la bienvenida).
 */
public class MainActivity extends AppCompatActivity {

    // Declaramos los componentes de la interfaz (Vistas) que vamos a controlar
    private EditText etUsername, etPassword;
    private Button btnLogin;
    
    // Declaramos nuestro ayudante de base de datos (Modelo)
    private BaseDatosHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Forzamos el modo claro (Light Mode) para que coincida con el diseño de Android Studio
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 1. Inicializamos el BaseDatosHelper (conectamos con la BD)
        dbHelper = new BaseDatosHelper(this);

        // 2. Insertamos usuarios de prueba por defecto para poder probar el login inmediatamente:
        //    - Admin: username "admin", password "1234"
        //    - Empleado: username "juan", password "1234"
        dbHelper.insertarUsuario("Administrador", "admin", "1234", "ADMIN");
        dbHelper.insertarUsuario("Empleado Juan", "user", "1234", "EMPLEADO");

        // 3. Vinculamos las variables Java con los elementos visuales definidos en el XML (`activity_main.xml`)
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        // 4. Programamos el evento de clic en el botón "Iniciar Sesión"
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Capturamos el texto introducido por el usuario y eliminamos espacios en blanco con .trim()
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                // Validación de campos vacíos (Cumple con el requisito 'a' del profesor: validar datos y mostrar mensajes)
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                    return; // Detenemos la ejecución aquí si hay campos vacíos
                }

                // 5. Llamamos al Modelo (BaseDatosHelper) para validar el login de forma segura.
                // ¿Qué hace validarLogin por dentro? Consulta la tabla "usuarios" de SQLite 
                // y devuelve un texto: si el usuario es el admin, devuelve literalmente "ADMIN"; 
                // si es un empleado (como Juan), devuelve "EMPLEADO". Si no existe, devuelve null.
                String rol = dbHelper.validarLogin(username, password);
                // El objeto dbHelper contiene en su interior el "rol" IMPORTANTÍSIMO EN NUESTRA APP.

                if (rol != null) {
                    // Si el rol no es nulo, las credenciales son correctas
                    Toast.makeText(MainActivity.this, "¡Bienvenido! Rol detectado: " + rol, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                    intent.putExtra("EXTRA_ROL", rol); // Adjuntamos el rol detectado clave,valor
                    // "EXTRA_ROL" es la clave que le damos al objeto intent para recuperarlo en DashboardActivity
                    startActivity(intent);

                } else {
                    // Si el rol es nulo, usuario o contraseña incorrectos
                    Toast.makeText(MainActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
