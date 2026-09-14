# BEAUTY BOOST — Dashboard administrativo

## Base de datos
La aplicación mantiene la conexión MySQL en `localhost:3307`, base `script_beauty_boost`.

Antes de probar la carga de imágenes, ejecuta `SQL_MIGRACION_ADMIN.sql` en phpMyAdmin si `producto.imagen_url` todavía no existe.

## Funciones
- Usuarios: listado real desde MySQL, creación, cambio de rol/estado y eliminación.
- Roles: `1 = Administrador`, `2 = Cliente`.
- Productos: registro, actualización, eliminación y carga de imágenes.
- Las imágenes se guardan en `web/img/productos` durante el despliegue y la ruta se persiste en `producto.imagen_url`.
- El panel exige `roles_id_rol = 1`.

## Nota de compatibilidad
La gestión administrativa de productos se encuentra en `adminProductos.jsp`, que es el componente real de inventario del proyecto. `pantalla_Producto.jsp` es un fragmento JavaScript del catálogo público y no se reemplaza por el panel administrativo para no romper la navegación del e-commerce.
