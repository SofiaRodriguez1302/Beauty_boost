package Servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;
import Controlador.ProductoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import Modelo.Usuario;

@WebServlet(name = "ProductoServlet", urlPatterns = {"/ProductoServlet"})
@MultipartConfig(maxFileSize = 5 * 1024 * 1024, maxRequestSize = 7 * 1024 * 1024)
public class ProductoServlet extends HttpServlet {

    private final String jdbcURL = "jdbc:mysql://localhost:3307/script_beauty_boost?useSSL=false&serverTimezone=UTC";
    private final String jdbcUsername = "root";
    private final String jdbcPassword = ""; // Coloca aquí la contraseña de MySQL si usas una

    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        
        if ("carrito".equals(action)) {
            request.getRequestDispatcher("/Vista/pantalla_Producto_Carrito.jsp").forward(request, response);
        } else if ("checkout".equals(action)) {
            HttpSession session = request.getSession(false);
            boolean usuarioLogueado = (session != null && session.getAttribute("usuarioLogueado") != null);
            
            if (!usuarioLogueado) {
                response.sendRedirect(request.getContextPath() + "/LoginServlet");
                return;
            }
            
            request.getRequestDispatcher("/Vista/pantalla_Checkout.jsp").forward(request, response);
        } else {
            Integer categoria = parseCategoria(request.getParameter("categoria"));
            if (categoria == null) categoria = parseCategoria(request.getParameter("id_categoria"));

            String busqueda = firstNonBlank(
                    request.getParameter("busqueda"),
                    request.getParameter("buscar"),
                    request.getParameter("q")
            );

            // IMPORTANTE: siempre cargamos el catálogo COMPLETO desde MySQL.
            // Los filtros de la interfaz se aplican sobre esta lista y nunca se
            // guarda un subconjunto en sesión. Así, "Todo" puede restaurar todos
            // los productos sin perder los que se mostraron inicialmente.
            ProductoDAO dao = new ProductoDAO();
            java.util.List<Modelo.Producto> productos = dao.listarProductosActivos();

            request.setAttribute("productosCatalogo", productos);
            request.setAttribute("categoriaSeleccionada", categoria == null ? "todo" : categoria.toString());
            request.setAttribute("busquedaInicial", busqueda == null ? "" : busqueda);
            request.getRequestDispatcher("/Vista/Producto.jsp").forward(request, response);
        }
    }

    private Integer parseCategoria(String value) {
        if (value == null) return null;
        String v = value.trim();
        if (v.isEmpty() || v.equalsIgnoreCase("todo") || v.equalsIgnoreCase("todas") || v.equals("0")) return null;
        try {
            int id = Integer.parseInt(v);
            return id > 0 ? id : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private String firstNonBlank(String... values) {
        for (String value : values) {
            if (value != null && !value.trim().isEmpty()) return value.trim();
        }
        return "";
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        if ("guardarImagenProducto".equals(action)) {
            HttpSession session = request.getSession(false);
            Usuario admin = session == null ? null : (Usuario) session.getAttribute("usuarioLogueado");
            if (admin == null || admin.getRolesIdRol() != 1) {
                responderError(response, HttpServletResponse.SC_FORBIDDEN, "Se requiere rol administrador.");
                return;
            }
            try {
                int idProducto = Integer.parseInt(request.getParameter("idProducto"));
                Part imagen = request.getPart("imagen");
                String ruta = guardarImagen(request, imagen);
                boolean ok = new ProductoDAO().actualizarImagen(idProducto, ruta);
                if (!ok) throw new IllegalStateException("No se pudo guardar la ruta de imagen en MySQL.");
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"status\":\"success\",\"imagen\":\"" + ruta.replace("\\", "\\\\").replace("\"", "\\\"") + "\"}");
            } catch (Exception e) {
                responderError(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            }
            return;
        }

        if ("procesarCompra".equals(action)) {
            HttpSession session = request.getSession(false);
            Usuario user = (session != null) ? (Usuario) session.getAttribute("usuarioLogueado") : null;

            if (user == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"status\":\"error\",\"message\":\"Sesión expirada o no autorizada.\"}");
                return;
            }

            String metodoPago = request.getParameter("metodoPago");
            if (!validarDatosPago(request, metodoPago)) {
                responderError(response, HttpServletResponse.SC_BAD_REQUEST, "Completa únicamente los datos requeridos para el método de pago seleccionado.");
                return;
            }

            // Lectura flexible de parámetros para evitar errores 500 por nombres de campos
            String numPedidoStr = request.getParameter("numeroPedido");
            if (numPedidoStr == null || numPedidoStr.trim().isEmpty()) {
                numPedidoStr = request.getParameter("numPedido");
            }
            if (numPedidoStr == null || numPedidoStr.trim().isEmpty()) {
                numPedidoStr = String.valueOf((int) (Math.random() * 90000) + 10000);
            }

            String totalStr = request.getParameter("total");
            if (totalStr == null || totalStr.trim().isEmpty()) {
                totalStr = request.getParameter("monto");
            }
            if (totalStr == null || totalStr.trim().isEmpty()) {
                totalStr = "0.0";
            }

            String carritoJson = request.getParameter("carritoJson");
            if (carritoJson == null || carritoJson.trim().isEmpty()) {
                carritoJson = request.getParameter("carrito");
            }

            // El checkout no puede crear una orden sin ítems válidos.
            if (carritoJson == null || carritoJson.trim().isEmpty() || "[]".equals(carritoJson.trim())) {
                responderError(response, HttpServletResponse.SC_BAD_REQUEST, "El carrito está vacío.");
                return;
            }

            Connection conn = null;
            try {
                int numeroPedido = Integer.parseInt(numPedidoStr.replaceAll("[^0-9]", ""));
                if (numeroPedido <= 0) numeroPedido = (int) (System.currentTimeMillis() % 90000) + 10000;

                // El total recibido del navegador es solo informativo: el servidor calcula el total real.
                conn = getConnection();
                conn.setAutoCommit(false);

                String sqlPedido = "INSERT INTO pedido (numero_pedido, fecha_pedido, total, usuario_id_usuario, estado_pedido, estado_pedido_id_estado_pedido) VALUES (?, NOW(), ?, ?, 'Pendiente', 1)";
                int idPedidoGenerado;
                double totalServidor = 0.0;

                // Extraer cada objeto del JSON sin depender de nombres/descripciones que puedan contener comas.
                java.util.regex.Matcher objetos = java.util.regex.Pattern.compile("\\{(.*?)\\}", java.util.regex.Pattern.DOTALL).matcher(carritoJson);
                java.util.List<int[]> items = new java.util.ArrayList<>();
                while (objetos.find()) {
                    String obj = objetos.group(1);
                    java.util.regex.Matcher mid = java.util.regex.Pattern.compile("\\\"id\\\"\\s*:\\s*(\\d+)").matcher(obj);
                    java.util.regex.Matcher mc = java.util.regex.Pattern.compile("\\\"cantidad\\\"\\s*:\\s*(\\d+)").matcher(obj);
                    if (mid.find() && mc.find()) {
                        int idProducto = Integer.parseInt(mid.group(1));
                        int cantidad = Integer.parseInt(mc.group(1));
                        if (idProducto > 0 && cantidad > 0) items.add(new int[]{idProducto, cantidad});
                    }
                }
                if (items.isEmpty()) {
                    throw new IllegalArgumentException("No se encontraron productos válidos en el carrito.");
                }

                String sqlProducto = "SELECT precio, stock FROM producto WHERE id_producto = ?";
                java.util.List<double[]> detalle = new java.util.ArrayList<>();
                try (PreparedStatement psProd = conn.prepareStatement(sqlProducto)) {
                    for (int[] item : items) {
                        psProd.setInt(1, item[0]);
                        try (ResultSet rsProd = psProd.executeQuery()) {
                            if (!rsProd.next()) throw new IllegalArgumentException("El producto #" + item[0] + " ya no está disponible.");
                            double precio = rsProd.getDouble("precio");
                            int stock = rsProd.getInt("stock");
                            if (precio <= 0) throw new IllegalArgumentException("El producto #" + item[0] + " tiene un precio inválido.");
                            if (stock >= 0 && item[1] > stock) throw new IllegalArgumentException("La cantidad solicitada del producto #" + item[0] + " supera el stock disponible.");
                            double subtotal = precio * item[1];
                            totalServidor += subtotal;
                            detalle.add(new double[]{item[0], item[1], precio, subtotal});
                        }
                    }
                }

                try (PreparedStatement psPedido = conn.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS)) {
                    psPedido.setInt(1, numeroPedido);
                    psPedido.setDouble(2, totalServidor);
                    psPedido.setInt(3, user.getIdUsuario());
                    psPedido.executeUpdate();
                    try (ResultSet rsKeys = psPedido.getGeneratedKeys()) {
                        if (!rsKeys.next()) throw new IllegalStateException("No fue posible obtener el ID de la orden.");
                        idPedidoGenerado = rsKeys.getInt(1);
                    }
                }

                // La tabla detalle_pedido usa precio_unitario (no subtotal).
                String sqlDetalle = "INSERT INTO detalle_pedido (cantidad, precio_unitario, producto_id_producto, pedido_id_pedido) VALUES (?, ?, ?, ?)";
                try (PreparedStatement psDetalle = conn.prepareStatement(sqlDetalle)) {
                    for (double[] d : detalle) {
                        psDetalle.setInt(1, (int) d[1]);
                        psDetalle.setDouble(2, d[2]);
                        psDetalle.setInt(3, (int) d[0]);
                        psDetalle.setInt(4, idPedidoGenerado);
                        psDetalle.addBatch();
                    }
                    psDetalle.executeBatch();
                }

                conn.commit();
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"status\":\"success\",\"totalServidor\":" + totalServidor + "}");
                return;

            } catch (Exception e) {
                if (conn != null) {
                    try { conn.rollback(); } catch (Exception ignored) {}
                }
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                String msg = e.getMessage() == null ? "No fue posible procesar la compra." : e.getMessage().replace("\"", "\\\"");
                response.getWriter().write("{\"status\":\"error\",\"message\":\"" + msg + "\"}");
                return;
            } finally {
                if (conn != null) {
                    try { conn.close(); } catch (Exception ex) { ex.printStackTrace(); }
                }
            }
        }

        doGet(request, response);
    }


    private boolean validarDatosPago(HttpServletRequest request, String metodo) {
        if (metodo == null) return false;
        java.util.function.Predicate<String> filled = name -> {
            String v = request.getParameter(name);
            return v != null && !v.trim().isEmpty();
        };
        switch (metodo.toLowerCase()) {
            case "tarjeta":
                return filled.test("txtNombreTitular") && filled.test("txtNumeroTarjeta")
                        && filled.test("txtFechaVencimiento") && filled.test("txtCvv") && filled.test("selectCuotas");
            case "nequi":
                return filled.test("nequiTelefono");
            case "daviplata":
                return filled.test("daviplataTipoDocumento") && filled.test("daviplataDocumento") && filled.test("daviplataTelefono");
            case "pse":
                return filled.test("pseTipoPersona") && filled.test("pseBanco") && filled.test("pseCorreo");
            case "contraentrega":
                return filled.test("contraNombrePedido") && filled.test("contraDocumento") && filled.test("contraDireccion")
                        && filled.test("contraTelefono") && filled.test("contraRecibe") && filled.test("contraRecibeTelefono");
            default:
                return false;
        }
    }

    private String guardarImagen(HttpServletRequest request, Part part) throws IOException {
        if (part == null || part.getSize() == 0) throw new IOException("Selecciona una imagen.");
        String type = part.getContentType();
        if (type == null || !type.toLowerCase().startsWith("image/")) throw new IOException("El archivo no es una imagen válida.");
        String original = part.getSubmittedFileName();
        String ext = ".jpg";
        if (original != null && original.lastIndexOf('.') >= 0) {
            String candidate = original.substring(original.lastIndexOf('.')).toLowerCase();
            if (candidate.matches("\\.(jpg|jpeg|png|webp|gif)")) ext = candidate;
        }
        String name = "producto_" + UUID.randomUUID().toString().replace("-", "") + ext;
        String realPath = getServletContext().getRealPath("/img/productos");
        if (realPath == null) throw new IOException("No se pudo resolver la carpeta de imágenes.");
        File dir = new File(realPath);
        if (!dir.exists() && !dir.mkdirs()) throw new IOException("No se pudo crear la carpeta de imágenes.");
        File target = new File(dir, name);
        try (InputStream in = part.getInputStream()) { Files.copy(in, target.toPath(), StandardCopyOption.REPLACE_EXISTING); }
        return "img/productos/" + name;
    }

    private void responderError(HttpServletResponse response, int status, String mensaje) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String seguro = mensaje == null ? "Error" : mensaje.replace("\\", "\\\\").replace("\"", "\\\"");
        response.getWriter().write("{\"status\":\"error\",\"message\":\"" + seguro + "\"}");
    }
}