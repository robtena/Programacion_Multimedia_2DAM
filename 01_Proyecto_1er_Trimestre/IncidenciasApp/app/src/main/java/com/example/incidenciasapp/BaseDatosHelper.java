package com.example.incidenciasapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;

/*
 * CAPA DE BASE DE DATOS (SQLite)
 *
 * ¿Por qué esta clase?
 * En Android NO se utiliza JDBC. En su lugar, heredamos de 'SQLiteOpenHelper',
 * una clase nativa de Android diseñada para crear y gestionar la BD SQLite de forma eficiente.
 *
 * Esta clase NO maneja diseño (XML) ni pantallas; su ÚNICA función es hablar con la BD.
 */
public class BaseDatosHelper extends SQLiteOpenHelper {

    // CONSTANTES: Nombre del archivo .db y número de versión.
    private static final String NOMBRE_BD = "PapuaIncidencias.db";
    private static final int VERSION_BD = 1;

    /*
     * CONSTRUCTOR
     * El 'Context' es la pantalla (Activity) que pide conectar a la BD.
     * Mediante 'super(...)' le pasamos los datos a la clase padre de Android.
     */
    public BaseDatosHelper(Context context) {
        super(context, NOMBRE_BD, null, VERSION_BD);
    }

    /*
     * METODO onCreate
     * Se ejecuta AUTOMÁTICAMENTE una sola vez, cuando el teléfono detecta
     * que el archivo 'PapuaIncidencias.db' todavía no existe.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Consulta SQL para crear la tabla de Usuarios (Roles: ADMIN o EMPLEADO)
        String crearTablaUsuarios = "CREATE TABLE usuarios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " + // ID único auto-incremental
                "nombre TEXT, " +                          // Nombre real del trabajador
                "username TEXT UNIQUE, " +                 // Nombre de usuario único para el login
                "password TEXT, " +                        // Contraseña
                "rol TEXT)";                               // "ADMIN" o "EMPLEADO"

        // Consulta SQL para crear la tabla de Incidencias (Tickets)
        String crearTablaIncidencias = "CREATE TABLE incidencias (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "titulo TEXT, " +                          // Descripción del fallo
                "area TEXT, " +                            // Ej: "Barra VIP", "Pista 2"
                "prioridad TEXT, " +                       // "Alta", "Media", "Baja"
                "estado TEXT, " +                          // "Pendiente", "En proceso", "Resuelta"
                "creadoPorId INTEGER, " +                  // ID del empleado que creó la incidencia
                "asignadoAId INTEGER, " +                  // ID del empleado asignado a resolverla
                "vistaPorAdmin INTEGER, " +                // 0 = No vista por el Jefe, 1 = Vista
                "FOREIGN KEY(creadoPorId) REFERENCES usuarios(id), " +
                "FOREIGN KEY(asignadoAId) REFERENCES usuarios(id))";

        // Ejecutamos las consultas SQL para crear físicamente las tablas en el móvil
        db.execSQL(crearTablaUsuarios);
        db.execSQL(crearTablaIncidencias);
    }

    /*
     * METODO onUpgrade
     * Se ejecuta solo si en el futuro cambias VERSION_BD a un número superior (ej: de 1 a 2).
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS incidencias");
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        onCreate(db);
    }

    /*
     * - C - OPERACIÓN CREAR (CREATE en el CRUD)
     * Metodo para insertar un nuevo usuario en la base de datos.
     * Devuelve 'true' si se guardó con éxito o 'false' si falló (ej: username duplicado).
     */
    public boolean insertarUsuario(String nombre, String username, String password, String rol) {
        // Pedimos permisos para ESCRIBIR en la base de datos
        SQLiteDatabase db = this.getWritableDatabase();

        /*
         * ContentValues es una "caja" de tipo clave-valor donde guardamos los datos recopilados
         * en la pantalla antes de enviarlos a la base de datos.
         * Clave = Nombre exacto de la columna en SQLite.
         * Valor = La información ingresada.
         */
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("username", username);
        values.put("password", password);
        values.put("rol", rol);

        /*
         * Explicación de los 3 parámetros de db.insert():
         * 1. "usuarios" -> Nombre de la tabla destino.
         * 2. null       -> Parámetro de seguridad para registros vacíos (siempre va 'null' cuando enviamos datos).
         * 3. values     -> La caja 'ContentValues' que contiene toda la información a guardar.
         *
         * Devuelve el número de fila insertada, o -1 si hubo un error.
         */
        long resultado = db.insert("usuarios", null, values);

        // Cerramos la conexión para liberar memoria en el dispositivo
        db.close();

        // Si el resultado no es -1 ("true"), significa que el usuario se guardó correctamente.
        // Si devuelve -1 ("false"), falló (generalmente porque el 'username' ya existe al ser UNIQUE) y devolverá 'false'.
        return resultado != -1; // Escribir esta línea de código sería lo mismo que escribir esta otra...
        /*if (resultado != -1) {
                return true;
        } else {
                return false;
        } */
    }

    /*
     * - R - OPERACIÓN LEER / CONSULTAR (READ en el CRUD)
     * Metodo para comprobar si las credenciales de Login son correctas.
     * Devuelve el rol del usuario ("ADMIN" o "EMPLEADO") si existe, o null si las credenciales son incorrectas.
     */
    public String validarLogin(String username, String password) {
        // Pedimos permisos de SOLO LECTURA a la base de datos
        SQLiteDatabase db = this.getReadableDatabase();

        // Definimos qué columnas queremos que nos devuelva la consulta
        String[] columnas = {"rol"};

        // Definimos la condición de búsqueda (El "WHERE" de SQL).
        // ¿Qué son las interrogaciones (?)?
        // Son "huecos" que se rellenarán después de forma segura. En lugar de meter el
        // texto del usuario directamente en la frase, dejamos marcadores '?'.
        String condicion = "username = ? AND password = ?";

        // Esta es la lista ordenada de datos que van a rellenar los huecos '?'.
        // El primer elemento (username) rellenará el primer '?', y el segundo (password) el segundo '?'.
        // Hacerlo así (con ? y un array aparte) es una medida de seguridad MUY IMPORTANTE para
        // evitar un hackeo llamado "Inyección SQL" (SQL Injection).
        /*
         * Sin ?: El ordenador confunde la información del usuario con órdenes de programación.
         * Con ?: El ordenador trata lo que escribe el usuario como simple texto inofensivo, jamás como código
         * que se pueda ejecutar.*/
        String[] argumentos = {username, password};

        /*
         * Cursor Es un objeto de Android que almacena las filas devueltas por una consulta SQL (SELECT).
         * Funciona como un "puntero" que recorre los resultados fila por fila.
         */
        android.database.Cursor cursor = db.query(
                "usuarios",// Tabla donde buscar
                columnas,       // Columnas a devolver (solo necesitamos el rol)
                condicion,      // Condición WHERE
                argumentos,     // Datos que sustituyen a los signos '?' (evita Inyección SQL)
                null,           // GROUP BY
                null,           // HAVING
                null            // ORDER BY
        );

        String rolEncontrado = null;

        // Comprobamos si el cursor encontró al menos una fila
        if (cursor != null && cursor.moveToFirst()) {
            /* Si entra aquí, significa que el usuario y la contraseña existen y obtenemos el texto de la
            primera columna (índice 0, que es "rol") */
            rolEncontrado = cursor.getString(0);
        }

        // Cerramos el cursor y la base de datos para liberar memoria
        if (cursor != null) {
            cursor.close();
            db.close();
        }

        // Devuelve "ADMIN", "EMPLEADO" o null (si los datos eran incorrectos)
        return rolEncontrado;
    }
}