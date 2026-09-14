# BEAUTY BOOST — Correcciones catálogo, contraseñas y checkout

Versión preparada para reemplazar directamente en Apache NetBeans.

## Cambios incluidos

1. **Catálogo / Maquillaje**
   - `ProductoDAO.java` incorpora `listarProductosActivos(Integer categoriaId)`.
   - Los productos nuevos se insertan siempre con `estado_producto = 'Activo'`.
   - `ProductoServlet.java` consulta productos desde MySQL y acepta `?categoria=2` para Maquillaje.
   - `Producto.jsp` recibe la lista de BD en `window.products` y la renderiza con el JavaScript existente.
   - Se mantiene un catálogo local de respaldo si la consulta no entrega productos.
   - Se corrigió la resolución de imágenes guardadas como `img/productos/...`.

2. **Mostrar / ocultar contraseña**
   - `Login.jsp`, `Registro.jsp` y `admin_usuarios.jsp` usan el patrón `data-toggle-password`.
   - Se incluye `beauty-boost.js` donde faltaba.
   - El botón cambia `👁` / `🙈`, `type=password/text`, `aria-label` y `aria-pressed`.

3. **Checkout / métodos de pago**
   - `pantalla_Checkout.jsp` fue ajustado para los cinco métodos:
     - Tarjeta: titular, número, expiración, CVV y cuotas.
     - Nequi: solo celular.
     - DaviPlata: tipo de documento, documento y celular.
     - PSE: tipo de persona, entidad bancaria y correo PSE.
     - Contra Entrega: nombre pedido, documento, dirección, teléfono comprador, nombre receptor y teléfono receptor.
   - `beauty-boost.js` habilita y marca como `required` únicamente los campos del método seleccionado y deshabilita los demás.
   - El backend valida nuevamente solo los parámetros correspondientes al método seleccionado.

## Base de datos

Ejecuta `SQL_MIGRACION_ADMIN.sql` en la base `script_beauty_boost` usando MySQL en el puerto **3307**. El script deja `estado_producto` con valor predeterminado `Activo` y corrige registros vacíos.

## Nota

La aplicación sigue usando Jakarta EE 10 / Java 17 y la conexión MySQL configurada en `localhost:3307`.
