# BEAUTY BOOST — Correcciones Full Stack Senior

## Cambios incluidos
- Header: un solo enlace de usuario (icono + nombre) apuntando a `PerfilServlet`; logo ampliado y limpieza de franja superior.
- Perfil: estadísticas dinámicas usando `listaFavoritos.size()`, pedidos y direcciones reales; lista de favoritos renderizada server-side.
- Seguridad: checkbox interactivo de autorización Habeas Data persistido mediante `PerfilServlet` + `UsuarioDAO`; eliminado formulario de nueva contraseña de esta sección.
- Login/Registro: `togglePassword()` funcional y botón de regreso circular mejorado.
- Checkout: validación de carrito, autenticación, consulta de productos/precios/stock desde BD y transacción completa.
- Checkout: corregido el nombre de columna de detalle de pedido a `precio_unitario`, consistente con `DetallePedidoDAO` y el esquema usado por el proyecto.
- PedidoDAO: consulta de detalle usa `precio_unitario` y calcula `subtotal` como cantidad × precio unitario.
- indexServlet: inicializa la colección de favoritos en sesión para mantener una fuente de estado consistente.
- JavaScript: sincronización visual de favoritos y manejo de mostrar/ocultar contraseña.

## Configuración BD
El proyecto conserva la conexión MySQL configurada en el proyecto, incluyendo el puerto 3307 donde corresponde.

## Validación
- JavaScript validado con `node --check`.
- Estructura de llaves/paréntesis de fuentes Java revisada.
- El archivo ZIP se genera a partir de la estructura completa del proyecto NetBeans.


## Requisito de base de datos: favoritos
La versión corregida usa la tabla `favoritos` como fuente persistente. Ejecuta en MySQL (puerto 3307) si la tabla todavía no existe:

```sql
CREATE TABLE favoritos (
    id_favorito INT NOT NULL AUTO_INCREMENT,
    usuario_id_usuario INT NOT NULL,
    producto_id_producto INT NOT NULL,
    fecha_agregado TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id_favorito),
    UNIQUE KEY uq_favorito_usuario_producto (usuario_id_usuario, producto_id_producto),
    CONSTRAINT fk_favorito_usuario FOREIGN KEY (usuario_id_usuario) REFERENCES usuario(id_usuario),
    CONSTRAINT fk_favorito_producto FOREIGN KEY (producto_id_producto) REFERENCES producto(id_producto)
);
```

Si tu esquema ya contiene la tabla `favoritos`, no la recrees; verifica que conserve esos nombres de columnas y relaciones.
