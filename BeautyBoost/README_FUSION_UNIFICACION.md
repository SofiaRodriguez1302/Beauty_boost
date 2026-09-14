# Beauty Boost — Proyecto Unificado

Este proyecto es el resultado de fusionar los dos entregables recibidos:

- **Proyecto A** (`Beauty_Boost_Sr`): módulo de correos (JavaMail/Jakarta Mail) y lógica de recuperación de contraseña.
- **Proyecto B** (`Beauty_Boost_BASE_DATOS_UNIFICADA`): estructura principal, diseño UI (JSPs, CSS, JS, imágenes), dashboards de administración, módulo de favoritos y DAOs más recientes.

## Hallazgos durante el análisis

1. **Recuperación de contraseña**: `RecuperarPasswordServlet.java` era **idéntico** en ambos proyectos (Proyecto B ya incluía la lógica de correo de recuperación con JavaMail/Jakarta Mail funcionando contra Gmail SMTP). No fue necesario portar nada desde el Proyecto A en este punto; sí se **refactorizó**.
2. **Correo de bienvenida**: no existía implementado como tal en ninguno de los dos proyectos (ni en `RegistroServlet.java` ni en `UsuarioDAO.java` de A o B). Se **implementó de nuevo**, reutilizando la configuración SMTP ya validada del módulo de recuperación de contraseña.
3. Proyecto B es una base de NetBeans completa y más avanzada (incluye `nbproject/`, favoritos, dashboards de admin, `admin_usuarios.jsp`, script SQL unificado `script_beauty_boost`), por lo que se usó como núcleo del proyecto final, tal como se solicitó.

## Cambios realizados

- **Nueva clase `Util/EmailService.java`**: centraliza toda la configuración SMTP (antes duplicada dentro de los Servlets) y expone:
  - `enviarCorreoBienvenida(correo, nombre)` — nuevo, usado en `RegistroServlet`. No lanza excepciones: si el envío falla, solo se registra en consola y el registro del usuario continúa con normalidad.
  - `enviarCorreoRecuperacion(correo, nombre, nuevaClave)` — lógica ya existente, extraída del Servlet.
  - `enviarNotificacionAdminRecuperacion(nombre, correo)` — lógica ya existente, extraída del Servlet.
- **`RegistroServlet.java`**: se agregó la llamada a `EmailService.enviarCorreoBienvenida(...)` justo después de un registro exitoso, sin modificar el formulario ni los campos de `Registro.jsp`.
- **`RecuperarPasswordServlet.java`**: se refactorizó para delegar el envío de correos a `EmailService`, manteniendo exactamente el mismo comportamiento, mensajes y diseño HTML de los correos.
- No se detectaron Servlets ni mapeos `@WebServlet` duplicados: `AdminUsuarioServlet` es una subclase de compatibilidad de `AdminUsuariosServlet` sin anotación propia, por lo que no genera conflicto de rutas.
- No existe `web.xml` clásico (el proyecto usa anotaciones `@WebServlet` + `glassfish-web.xml`), por lo que no hay riesgo de doble mapeo.

## Configuración de correo

Las credenciales SMTP (cuenta de Gmail + contraseña de aplicación) usadas por `EmailService` son las mismas que ya venían configuradas y funcionando en el proyecto original. Si necesitas cambiarlas, edítalas en:

```
src/java/Util/EmailService.java
```

## Base de datos

- Esquema: `script_beauty_boost` (ver `BEAUTY_BOOST_BASE_DATOS_UNIFICADA.sql` en la raíz del proyecto).
- Configuración de conexión en `src/java/Conexion/Conexion.java` (host `localhost:3307` por defecto — ajusta el puerto/usuario/clave a tu entorno local de MySQL).

## Requisitos de entorno y librerías

- JDK 17.
- GlassFish Server (idealmente **Full Profile**; el "Web Profile" no trae Jakarta Mail).
- **Importante — corrección de portabilidad**: el proyecto original apuntaba a rutas absolutas de una máquina Windows (`C:\Users\Aprendiz\Downloads\...`) para JSTL y el conector MySQL. Esto se corrigió: ahora `nbproject/project.properties` y `nbproject/project.xml` apuntan a una carpeta **`lib/` dentro del propio proyecto** (rutas relativas), y esos `.jar` se empaquetan automáticamente en `WEB-INF/lib` al compilar.
- Los `.jar` en sí **no se distribuyen dentro de este zip** (para no inflarlo innecesariamente). Ver `lib/README_LIBRERIAS.md` para la lista completa de 6 conectores necesarios, sus enlaces directos de descarga y un script (`lib/descargar_librerias.sh`) que los descarga automáticamente desde Maven Central.

## Cómo importar en NetBeans

1. Descarga los 6 `.jar` indicados en `lib/README_LIBRERIAS.md` (o ejecuta `lib/descargar_librerias.sh`) y colócalos dentro de la carpeta `lib/` del proyecto.
2. Abrir NetBeans → File → Open Project → seleccionar la carpeta `Beauty_Boost_Final` (o el nombre con el que descomprimas el zip).
3. Configurar el servidor GlassFish en las propiedades del proyecto.
4. Ejecutar el script `BEAUTY_BOOST_BASE_DATOS_UNIFICADA.sql` en tu MySQL local.
5. Ajustar host/puerto/usuario/clave en `Conexion.java` si difieren de tu entorno.
6. Clean and Build → Run Project.
