# Sistema de Gestión de Incidencias / Acceso (Android Native)

> [!CAUTION]
> **ORDEN ABSOLUTA PARA EL AGENTE / IA EN FUTURAS SESIONES:**
> No debo hacer yo solo el código. Tengo que guiar a Roberto y darle explicaciones o pistas, pero **nunca** hacerle el código completo ni escribírselo todo. De lo contrario, ¡él no aprenderá NADA!

- **Lenguaje:** Java (Nativo)
- **Entorno / IDE:** Android Studio
- **Arquitectura:** MVC (Model-View-Controller) básica con SQLite Local.

## Arquitectura MVC
- **Modelo:** Base de datos y manipulación de datos (datos y reglas). En este proyecto se gestiona mediante [`BaseDatosHelper.java`](file:///C:/Users/Usuario/AndroidStudioProjects/Programacion_Multimedia_2DAM/01_Proyecto_1er_Trimestre/IncidenciasApp/app/src/main/java/com/example/incidenciasapp/BaseDatosHelper.java).
- **Vista:** Frontend de la aplicación (interfaz visual con la que interactúa el usuario).
- **Controlador:** Lógica de la aplicación, conecta la vista con el modelo, gestiona eventos y decide cuándo transicionar entre actividades.

### 💡 Nota Arquitectónica: Helper Local vs. API en la Nube
En este proyecto utilizamos una base de datos embebida (SQLite) gestionada mediante `BaseDatosHelper.java`, la cual actúa como una **Capa de Acceso a Datos local**. 
- En un sistema offline como este, el Helper aísla a las pantallas de los detalles de SQL.
- Si la base de datos estuviera en la nube (ej. PostgreSQL), esta capa de acceso se gestionaría mediante una **API** que realiza consultas HTTP (peticiones GET/POST) para comunicarse con el servidor remoto.

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

## 3. Estado Actual y Próximo Paso en el Desarrollo
- **Decisión de Alcance (¡Muy Importante!):** Para cumplir con los plazos de entrega de la próxima semana, **no implementaremos un CRUD de usuarios**. Los usuarios (Admin y Empleado) se quedan fijos por código (*seeding* en el login). El **único CRUD completo** de la aplicación será sobre las **Incidencias** (Crear, Consultar, Modificar, Eliminar).
- **Hitos Completados:**
  1. Maquetación del Login (`activity_main.xml` con ScrollView y diseño corporativo claro).
  2. Lógica del Login (`MainActivity.java` validando contra SQLite y pasando rol por `Intent`).
  3. Estructura de la Base de Datos y Modelo (`BaseDatosHelper.java` con métodos CRUD y `Incidencia.java` POJO).
  4. Panel Principal (`DashboardActivity.java` y `activity_dashboard.xml` recibiendo rol y listando incidencias en el `ListView` mediante `SimpleCursorAdapter` — Operación **Read / Consultar** completada).
- **Próximo Hito:** Programar el botón "Crear Nueva Incidencia" para implementar la operación **Create / Crear** del CRUD.

---

## 4. Estrategia de Testing y Calidad (QA Automation)
- **Pruebas Unitarias (JUnit):** Verificación de la lógica del Modelo de forma aislada (`BaseDatosHelper` y validaciones de login).
- **Pruebas de Interfaz / Integración (Espresso):** Automatización de la interacción con la Vista en emulador (simulación de tecleo, pulsación del botón de login y comprobación de redirección de pantallas).

---

## 5. Glosario Técnico de Exposición (Definiciones Propias de Roberto)
*Esta sección recopila los conceptos clave explicados con tus propias palabras para la defensa y exposición del proyecto ante el profesor y tus compañeros:*

- **Cursor:** Elemento del framework de Android que funciona como un puntero que contiene datos de la base de datos temporalmente para ir trabajándolos de uno en uno y evitar la saturación o el *crash* de la memoria RAM del dispositivo.
- **Clase POJO (Modelo `Incidencia`):** Objeto simple de Java que actúa como una "caja" o molde estructural para empaquetar los datos de una fila de la base de datos y transportarlos cómodamente entre el Modelo, el Controlador y la Vista.
- **Helper Local vs. API en la Nube:** El `BaseDatosHelper` actúa como la capa de acceso a datos local para una base de datos embebida (SQLite). Si la base de datos estuviera en un servidor remoto, se requeriría una API con consultas HTTP.
- **Arquitectura MVC (Modelo-Vista-Controlador):** Patrón de diseño que divide la app en tres partes: el *Modelo* (la base de datos con el Helper y las clases POJO), la *Vista* (los archivos XML con la interfaz visual que ve el usuario) y el *Controlador* (el código Java de las Activities que conecta la interfaz con los datos y gestiona los eventos).

---

## 6. Requisitos Oficiales del Profesor (Rúbrica de la Asignatura)
*Lista de comprobación oficial basada en lo exigido por el profesor para la entrega y defensa del proyecto:*

1. **Interfaz Gráfica (a):** Distintas pantallas, navegación entre ellas, controles de entrada, alertas, validación de datos y mensajes comprensibles ante errores.
2. **Estructura de Clases (b):** Código organizado en clases con separación clara entre pantallas (vistas), modelos de datos (POJOs) y acceso a datos (Helper).
3. **Persistencia de Datos y CRUD Completo (b):** Almacén local (SQLite) que conserve la información tras cerrar la app. Implementación de un **CRUD completo** (Crear, Consultar, Modificar, Eliminar).
4. **Permisos (c):** Declaración y solicitud en ejecución de permisos si se necesitan (ej. Cámara), gestionando el caso de denegación por parte del usuario.
5. **Documentación en PDF (c):** Explicación de la estructura del proyecto, funciones implementadas, capturas de pantalla y manual de uso.
6. **Entregables Finales:** Proyecto completo de Android Studio, APK instalable (comprimido en ZIP) y documento PDF de documentación.
