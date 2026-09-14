# BeautyBoost

Proyecto Java Web para Apache NetBeans + GlassFish/Jakarta EE + JSP/Servlets + JDBC + MySQL.

## Estructura

- `database/script_beauty_boost.sql` — esquema unificado `script_beauty_boost`, DDL y DML.
- `src/java/Modelo` — JavaBeans/POJO.
- `src/java/Controlador` — DAOs.
- `src/java/Conexion` — conexión JDBC a MySQL en puerto **3307**.
- `src/java/Servlet` — controladores HTTP.
- `web/Vista` — JSP, CSS y JavaScript.
- `web/img` — imágenes estáticas y rutas persistidas en `producto.imagen_url`.
- `lib` — dependencias externas; se descargan con los scripts incluidos.

## Imágenes

La base de datos guarda únicamente rutas relativas, por ejemplo:

`img/maquillaje_base_liquida_matificante.png`

Cuando no existe imagen se utiliza:

`img/default.jpg`

En las JSP se usa el contexto de la aplicación para construir la URL:

```jsp
<img src="${pageContext.request.contextPath}/${producto.imagenUrl}" alt="${producto.nombreProd}" />
```

No se usan BLOB para imágenes.

## Base de datos

1. Inicia MySQL/MariaDB en el puerto **3307**.
2. Ejecuta `database/script_beauty_boost.sql` en MySQL Workbench o en el cliente MySQL.
3. El script crea desde cero el schema `script_beauty_boost`.
4. Los productos quedan con `estado_producto = 'Activo'`.
5. El script evita depender de `SQL_SAFE_UPDATES` para la normalización final.

## Usuarios de demostración

Los hashes de las contraseñas se almacenan con BCrypt.

- Administrador: `admin.beautyboost@mail.com` / `Admin123!`
- Cliente: `paula.cliente@mail.com` / `Beauty123!`
- Cliente adicional: `sofia.hernandez@mail.com` / `Beauty123!`

Cambia estas credenciales antes de usar el sistema en producción.

## Dependencias

NetBeans necesita estos JAR en `lib/`:

- `mysql-connector-j-8.4.0.jar`
- `jakarta.servlet.jsp.jstl-3.0.1.jar`
- `jakarta.mail-api-2.1.3.jar`
- `angus-mail-2.0.4.jar`
- `jakarta.activation-api-2.1.3.jar`
- `angus-activation-2.0.2.jar`
- `jbcrypt-0.4.jar`

En Windows puedes ejecutar `lib/descargar_librerias.bat`. En Linux/macOS/WSL:

```bash
cd lib
bash descargar_librerias.sh
```

Después abre `BeautyBoost` en Apache NetBeans, verifica GlassFish Server 7 / Jakarta EE 10 y ejecuta **Clean and Build**.

## Configuración MySQL

La clase `Conexion.java` utiliza:

`jdbc:mysql://localhost:3307/script_beauty_boost`

Usuario por defecto: `root`

Contraseña por defecto: vacía.

Si tu MySQL tiene contraseña, modifica únicamente la propiedad correspondiente en `src/java/Conexion/Conexion.java` y, si aplica, los credenciales de `ProductoServlet`.

## BCrypt

La autenticación usa BCrypt con costo 12 para nuevas contraseñas. Los registros antiguos que todavía tengan SHA-256 o texto plano se pueden validar una vez y migrar automáticamente a BCrypt.

## Notas

El proyecto utiliza anotaciones `@WebServlet` y `@MultipartConfig`, por lo que `web.xml` se mantiene para configuración general, bienvenida, sesión y errores.

Las imágenes proporcionadas en `Categorias.zip` fueron convertidas a PNG y normalizadas dentro de `web/img` para que coincidan con las rutas SQL.

## Correcciones del catálogo — septiembre 2026

Se corrigieron tres problemas del catálogo:

1. **Imágenes:** se detectaron 51 archivos con extensión `.png` cuyo contenido real era JPEG o WebP. Se convirtieron a PNG real conservando el mismo nombre para no romper las rutas. También se corrigieron nombres heredados con secuencias `#U00e1/#U00f1` y se normalizó `producto.imagen_url` a `img/...`.
2. **Filtros:** el catálogo ahora mantiene una lista completa proveniente de MySQL y aplica los filtros de categoría y búsqueda sobre esa lista, sin depender del arreglo de productos de respaldo de JavaScript.
3. **Restauración de “Todo”:** `ProductoServlet` carga siempre todos los productos activos; categoría/búsqueda se reciben como parámetros iniciales y el frontend aplica el filtro sin sobrescribir la fuente global.

Además, las imágenes renderizadas dinámicamente tienen un fallback a `img/default.jpg` si un archivo no existe.
