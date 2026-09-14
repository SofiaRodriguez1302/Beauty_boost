package Servlet;

import Conexion.Conexion;
import Controlador.CategoriaDAO;
import Controlador.ProductoDAO;
import Modelo.Categoria;
import Modelo.Producto;
import Modelo.Usuario;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet(name = "AdminProductoServlet", urlPatterns = {"/AdminProductoServlet"})
@MultipartConfig(maxFileSize = 5 * 1024 * 1024, maxRequestSize = 7 * 1024 * 1024)
public class AdminProductoServlet extends HttpServlet {

    private boolean esAdministrador(HttpSession session) {
        Usuario usuario = session == null ? null : (Usuario) session.getAttribute("usuarioLogueado");
        return usuario != null && usuario.getRolesIdRol() == 1;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !esAdministrador(session)) {
            response.sendRedirect(request.getContextPath() + "/Vista/Login.jsp");
            return;
        }

        List<Map<String, Object>> listaProductos = cargarProductos();
        List<Map<String, Object>> listaCategorias = new ArrayList<>();
        for (Categoria cat : new CategoriaDAO().consultarCategorias()) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", cat.getIdCategoria());
            map.put("nombre", cat.getNombreCategoria());
            listaCategorias.add(map);
        }
        request.setAttribute("listaProductos", listaProductos);
        request.setAttribute("listaCategorias", listaCategorias);
        request.getRequestDispatcher("/Vista/adminProductos.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);
        if (session == null || !esAdministrador(session)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acceso administrativo requerido.");
            return;
        }

        String action = request.getParameter("action");
        ProductoDAO dao = new ProductoDAO();
        boolean ok = false;
        String mensaje;

        try {
            if ("crear".equals(action)) {
                Producto p = productoDesdeRequest(request, false);
                String imagen = guardarImagen(request.getPart("imagen"));
                p.setImagen(imagen);
                ok = dao.insertarProducto(p);
                mensaje = ok ? "Producto registrado correctamente." : "No se pudo registrar el producto.";

            } else if ("actualizar".equals(action)) {
                Producto p = productoDesdeRequest(request, true);
                Part parte = request.getPart("imagen");
                if (parte != null && parte.getSize() > 0) p.setImagen(guardarImagen(parte));
                else p.setImagen(request.getParameter("imagenActual"));
                ok = dao.actualizarProducto(p);
                mensaje = ok ? "Producto actualizado correctamente." : "No se pudo actualizar el producto.";

            } else if ("eliminar".equals(action)) {
                int id = Integer.parseInt(request.getParameter("idProducto"));
                ok = dao.eliminarProducto(id);
                mensaje = ok ? "Producto eliminado correctamente." : "No se pudo eliminar el producto. Puede tener registros relacionados.";
            } else {
                mensaje = "Acción no reconocida.";
            }
        } catch (Exception e) {
            mensaje = "No se pudo completar la operación: " + e.getMessage();
        }

        session.setAttribute(ok ? "mensajeExito" : "mensajeError", mensaje);
        response.sendRedirect(request.getContextPath() + "/AdminProductoServlet");
    }

    private Producto productoDesdeRequest(HttpServletRequest request, boolean actualizar) {
        Producto p = new Producto();
        if (actualizar) p.setIdProducto(Integer.parseInt(request.getParameter("idProducto")));
        p.setNombreProd(valor(request.getParameter("nombre")));
        p.setDescripcionProd(valor(request.getParameter("descripcion")));
        if (p.getDescripcionProd().isEmpty()) p.setDescripcionProd("Producto de Beauty Boost");
        p.setPrecio(Double.parseDouble(request.getParameter("precio")));
        p.setStock(Integer.parseInt(request.getParameter("stock")));
        p.setCategoriaIdCategoria(Integer.parseInt(request.getParameter("categoriaId")));
        p.setEstadoProducto("Inactivo".equalsIgnoreCase(request.getParameter("estado")) ? "Inactivo" : "Activo");
        return p;
    }

    private String guardarImagen(Part part) throws IOException {
        if (part == null || part.getSize() == 0) return null;
        String contentType = part.getContentType();
        if (contentType == null || !contentType.toLowerCase().startsWith("image/")) {
            throw new IOException("El archivo seleccionado no es una imagen válida.");
        }
        String original = part.getSubmittedFileName();
        String extension = ".jpg";
        if (original != null && original.lastIndexOf('.') >= 0) {
            String ext = original.substring(original.lastIndexOf('.')).toLowerCase();
            if (ext.matches("\\.(jpg|jpeg|png|webp|gif)")) extension = ext;
        }
        String nombre = "producto_" + UUID.randomUUID().toString().replace("-", "") + extension;
        String rutaReal = getServletContext().getRealPath("/img/productos");
        if (rutaReal == null) throw new IOException("No fue posible resolver la carpeta de imágenes del servidor.");
        File carpeta = new File(rutaReal);
        if (!carpeta.exists() && !carpeta.mkdirs()) throw new IOException("No fue posible crear la carpeta de imágenes.");
        File destino = new File(carpeta, nombre);
        try (InputStream in = part.getInputStream()) {
            Files.copy(in, destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
        return "img/productos/" + nombre;
    }

    private List<Map<String, Object>> cargarProductos() {
        List<Map<String, Object>> lista = new ArrayList<>();
        String sql = "SELECT p.id_producto, p.nombre_prod, p.precio, p.stock, p.estado_producto, p.imagen_url, "
                + "c.id_categoria, c.nombre_categoria FROM producto p LEFT JOIN categoria c "
                + "ON p.categoria_id_categoria = c.id_categoria ORDER BY p.id_producto DESC";
        try (java.sql.Connection conn = new Conexion().getConn();
             java.sql.PreparedStatement ps = conn.prepareStatement(sql);
             java.sql.ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> prod = new HashMap<>();
                prod.put("id", rs.getInt("id_producto"));
                prod.put("nombre", rs.getString("nombre_prod"));
                prod.put("precio", rs.getDouble("precio"));
                prod.put("stock", rs.getInt("stock"));
                prod.put("estado", rs.getString("estado_producto"));
                prod.put("imagen", rs.getString("imagen_url"));
                prod.put("idCategoria", rs.getInt("id_categoria"));
                prod.put("nombreCategoria", rs.getString("nombre_categoria"));
                lista.add(prod);
            }
        } catch (Exception e) {
            System.out.println("Aviso: imagen_url no disponible o consulta de inventario falló. Se intenta consulta compatible: " + e.getMessage());
            String legacy = "SELECT p.id_producto, p.nombre_prod, p.precio, p.stock, p.estado_producto, c.id_categoria, c.nombre_categoria "
                    + "FROM producto p LEFT JOIN categoria c ON p.categoria_id_categoria = c.id_categoria ORDER BY p.id_producto DESC";
            try (java.sql.Connection conn = new Conexion().getConn();
                 java.sql.PreparedStatement ps = conn.prepareStatement(legacy);
                 java.sql.ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String,Object> prod = new HashMap<>();
                    prod.put("id", rs.getInt("id_producto"));
                    prod.put("nombre", rs.getString("nombre_prod"));
                    prod.put("precio", rs.getDouble("precio"));
                    prod.put("stock", rs.getInt("stock"));
                    prod.put("estado", rs.getString("estado_producto"));
                    prod.put("imagen", null);
                    prod.put("idCategoria", rs.getInt("id_categoria"));
                    prod.put("nombreCategoria", rs.getString("nombre_categoria"));
                    lista.add(prod);
                }
            } catch (Exception legacyError) {
                System.out.println("Error cargando inventario compatible: " + legacyError.getMessage());
            }
        }
        return lista;
    }

    private String valor(String value) { return value == null ? "" : value.trim(); }
}
