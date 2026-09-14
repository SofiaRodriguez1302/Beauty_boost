# BEAUTY BOOST - Corrección botón EXPLORAR TIENDA

## Corrección principal
Se corrigió el error HTTP 500 que ocurría al entrar al catálogo. El problema estaba en `web/Vista/Producto.jsp`: el bloque Java/JSP que convertía los datos de MySQL a JavaScript tenía cadenas Java mal escapadas, provocando:

- `illegal character: '\\'`
- `unclosed string literal`

## Cambios
1. `web/Vista/Producto.jsp`
   - Se reemplazó el bloque de serialización de productos por uno con escape correcto de `\\`, comillas y saltos de línea.
   - Los productos recibidos desde `ProductoServlet` se renderizan correctamente en `window.products`.

2. `web/index.jsp`
   - El botón **EXPLORAR TIENDA** ahora entra directamente a `/ProductoServlet`.
   - Así el Servlet consulta MySQL antes de mostrar el catálogo.

3. `src/java/Servlet/indexServlet.java`
   - Los enlaces antiguos que todavía usen `/indexServlet` ahora se redirigen a `/ProductoServlet`.
   - Se evita abrir `Producto.jsp` sin los atributos necesarios.

4. `src/java/Controlador/ProductoDAO.java`
   - Los productos nuevos se registran como `Activo`.
   - Se conserva compatibilidad con bases antiguas que todavía no tengan `imagen_url`.

## Flujo corregido
Portada -> EXPLORAR TIENDA -> ProductoServlet -> ProductoDAO -> MySQL -> Producto.jsp -> catálogo dinámico.

## Importante
La conexión del proyecto continúa configurada para MySQL en el puerto **3307**.
