# Sistema de Gestión de Incidencias / Acceso (Android Native)

- **Lenguaje:** Java (Nativo)
- **Entorno / IDE:** Android Studio
- **Arquitectura:** MVC (Model-View-Controller) básica con SQLite Local.

## Arquitectura MVC
- **Modelo:** Base de datos y manipulación de datos (datos y reglas). En este proyecto se gestiona mediante [`BaseDatosHelper.java`](file:///C:/Users/Usuario/AndroidStudioProjects/Programacion_Multimedia_2DAM/01_Proyecto_1er_Trimestre/IncidenciasApp/app/src/main/java/com/example/incidenciasapp/BaseDatosHelper.java).
- **Vista:** Frontend de la aplicación (interfaz visual con la que interactúa el usuario).
- **Controlador:** Lógica de la aplicación, conecta la vista con el modelo, gestiona eventos y decide cuándo transicionar entre actividades.

---

## 1. Estructura de la Base de Datos (SQLite)

- **Clase Helper:** `BaseDatosHelper.java`
- **Nombre de la Base de Datos:** `incidencias.db` (Versión 1)

### Tabla: `usuarios`
- `id`: INTEGER PRIMARY KEY AUTOINCREMENT
- `nombre`: TEXT
- `username`: TEXT UNIQUE
- `password`: TEXT
- `rol`: TEXT ("ADMIN" o "EMPLEADO")

### Métodos implementados en `BaseDatosHelper`:
1. `onCreate(SQLiteDatabase db)`: Crea la tabla `usuarios`.
2. `onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)`: Elimina la tabla (`DROP TABLE IF EXISTS usuarios`) y la recrea.
3. `insertarUsuario(...)`: Inserta usuarios usando `ContentValues` y `getWritableDatabase()`.
4. `validarLogin(String username, String password)`: Consulta `SELECT` usando consultas parametrizadas (`?`) para prevenir SQL Injection. Devuelve `"ADMIN"`, `"EMPLEADO"` o `null`. Cierra cursor y base de datos de forma segura.

---

## 2. Control de Acceso y Roles
- **Vista ADMIN:** Acceso completo (gestión, creación y visualización total).
- **Vista EMPLEADO:** Acceso limitado.

---

## 3. Próximo Paso en el Desarrollo
- Maquetación de la pantalla de inicio de sesión en XML: `activity_main.xml`.
  - `EditText` para Usuario.
  - `EditText` para Contraseña (`inputType="textPassword"`).
  - `Button` de "Iniciar Sesión".
- Vincular con `MainActivity.java` para conectar con `dbHelper.validarLogin()`.

---

## 4. Estrategia de Testing y Calidad (QA Automation)
- **Pruebas Unitarias (JUnit):** Verificación de la lógica del Modelo de forma aislada (`BaseDatosHelper` y validaciones de login).
- **Pruebas de Interfaz / Integración (Espresso):** Automatización de la interacción con la Vista en emulador (simulación de tecleo, pulsación del botón de login y comprobación de redirección de pantallas).
