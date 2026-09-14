# BEAUTY BOOST — Base de datos unificada

Esta versión usa **una sola base de datos**: `script_beauty_boost`.

## Incluye

- Esquema original de BEAUTY BOOST.
- Usuarios, roles y estados.
- Categorías.
- Productos.
- Favoritos.
- Carrito.
- Pedidos y detalle de pedidos.
- Métodos de pago y pagos.
- Empresas y control de envío.
- `usuario.estado_usuario`.
- `producto.imagen_url`.
- `producto.estado_producto` con valor predeterminado `Activo`.
- Los 47 productos de maquillaje adicionales con sus rutas de imagen.

## Archivo SQL único

Importa únicamente:

`BEAUTY_BOOST_BASE_DATOS_UNIFICADA.sql`

No necesitas ejecutar otros SQL de migración o productos.

## Imágenes

Las imágenes están incluidas en:

`web/img/`

La base de datos guarda las rutas relativas, por ejemplo:

`img/BaseMattePro.png`

## MySQL

El proyecto está configurado para:

- Host: `localhost`
- Puerto: `3307`
- Base de datos: `script_beauty_boost`

Si vas a importar la base desde cero, ejecuta el SQL completo en phpMyAdmin.

## Importante

La base de datos no almacena físicamente los PNG/JPG. Guarda la ruta en `imagen_url`; los archivos de imagen están dentro del proyecto web.


## Corrección Safe Updates

La normalización de `estado_producto` usa `WHERE id_producto > 0`, por lo que funciona con Safe Updates de MySQL Workbench. No es necesario desactivar Safe Updates.
