package Servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import Modelo.Usuario;

@WebServlet(name = "AdminDashboardServlet", urlPatterns = {"/AdminDashboardServlet"})
public class AdminDashboardServlet extends HttpServlet {

    private final String jdbcURL = "jdbc:mysql://localhost:3307/script_beauty_boost?useSSL=false&serverTimezone=UTC";
    private final String jdbcUsername = "root";
    private final String jdbcPassword = "";

    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Usuario admin = (Usuario) session.getAttribute("usuarioLogueado");

        if (admin == null || admin.getRolesIdRol() != 1) {
            response.sendRedirect(request.getContextPath() + "/Vista/Login.jsp");
            return;
        }

        double ventasTotales = 0.0;
        int pedidosPendientes = 0;
        int stockBajo = 0;
        int clientesRegistrados = 0;
        List<Map<String, Object>> pedidosRecientes = new ArrayList<>();
        List<Map<String, Object>> listaUsuarios = new ArrayList<>();
        List<Map<String, Object>> resenasAdmin = new ArrayList<>();

        try (Connection conn = getConnection()) {
            
            // 1. MÉTRICAS SUPERIORES
            try (PreparedStatement ps = conn.prepareStatement("SELECT SUM(total) AS total_ventas FROM pedido");
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) ventasTotales = rs.getDouble("total_ventas");
            } catch (Exception e) { System.out.println("Error métrica ventas: " + e.getMessage()); }

            try (PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) AS total FROM pedido WHERE estado_pedido_id_estado_pedido = 1 OR estado_pedido = 'Pendiente'");
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) pedidosPendientes = rs.getInt("total");
            } catch (Exception e) { System.out.println("Error métrica pendientes: " + e.getMessage()); }

            try (PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) AS total FROM producto WHERE stock < 10");
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) stockBajo = rs.getInt("total");
            } catch (Exception e) { System.out.println("Error métrica stock: " + e.getMessage()); }

            try (PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) AS total FROM usuario");
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) clientesRegistrados = rs.getInt("total");
            } catch (Exception e) { System.out.println("Error métrica usuarios: " + e.getMessage()); }

            // 2. TABLA DE PEDIDOS Y ENVÍOS (Con control_envio y empresas_envio)
            try {
                String sqlPedidos = "SELECT p.id_pedido, p.fecha_pedido, p.numero_pedido, p.total, p.estado_pedido, p.estado_pedido_id_estado_pedido, " +
                                    "u.nombre, u.apellido, " +
                                    "ce.codigo_seguimiento, ce.fecha_entrega, ee.nombre_empresa " +
                                    "FROM pedido p " +
                                    "INNER JOIN usuario u ON p.usuario_id_usuario = u.id_usuario " +
                                    "LEFT JOIN control_envio ce ON p.id_pedido = ce.pedido_id_pedido " +
                                    "LEFT JOIN empresas_envio ee ON ce.empresas_envio_id_empresa_envio = ee.id_empresa_envio " +
                                    "ORDER BY p.id_pedido DESC LIMIT 15";
                
                try (PreparedStatement ps = conn.prepareStatement(sqlPedidos); ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("idPedido", rs.getInt("id_pedido"));
                        map.put("fecha", rs.getDate("fecha_pedido"));
                        map.put("numPedido", rs.getInt("numero_pedido"));
                        map.put("total", rs.getDouble("total"));
                        map.put("estado", rs.getString("estado_pedido") != null ? rs.getString("estado_pedido") : "Pendiente");
                        map.put("idEstado", rs.getInt("estado_pedido_id_estado_pedido"));
                        map.put("cliente", rs.getString("nombre") + " " + rs.getString("apellido"));
                        map.put("codigoSeguimiento", rs.getString("codigo_seguimiento"));
                        map.put("fechaEntrega", rs.getDate("fecha_entrega"));
                        map.put("empresaEnvio", rs.getString("nombre_empresa"));
                        pedidosRecientes.add(map);
                    }
                }
            } catch (Exception e) {
                System.out.println("Error cargando pedidos y envíos: " + e.getMessage());
            }

            // 3. TABLA DE USUARIOS REGISTRADOS
            try {
                String sqlUsuarios = "SELECT id_usuario, nombre, apellido, correo, telefono, estado_usuario, roles_id_rol, tipo_usuario_id_tipo_usuario FROM usuario ORDER BY id_usuario DESC";
                try (PreparedStatement ps = conn.prepareStatement(sqlUsuarios); ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Map<String, Object> uMap = new HashMap<>();
                        uMap.put("id", rs.getInt("id_usuario"));
                        uMap.put("nombreCompleto", rs.getString("nombre") + " " + rs.getString("apellido"));
                        uMap.put("correo", rs.getString("correo"));
                        uMap.put("telefono", rs.getString("telefono"));
                        uMap.put("estadoUsuario", rs.getString("estado_usuario") != null ? rs.getString("estado_usuario") : "Activo");
                        uMap.put("rolId", rs.getInt("roles_id_rol"));
                        uMap.put("tipoUsuarioId", rs.getInt("tipo_usuario_id_tipo_usuario"));
                        listaUsuarios.add(uMap);
                    }
                }
            } catch (Exception e) {
                System.out.println("Error cargando usuarios: " + e.getMessage());
            }

            // 4. CALIFICACIONES Y FEEDBACK
            try {
                String sqlResenas = "SELECT r.observacion, r.calificacion, r.fecha, u.nombre, u.apellido " +
                                    "FROM resena_usuario r INNER JOIN usuario u ON r.usuario_id_usuario = u.id_usuario " +
                                    "ORDER BY r.fecha DESC LIMIT 5";
                try (PreparedStatement ps = conn.prepareStatement(sqlResenas); ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Map<String, Object> rMap = new HashMap<>();
                        rMap.put("observacion", rs.getString("observacion"));
                        rMap.put("calificacion", rs.getInt("calificacion"));
                        rMap.put("fecha", rs.getTimestamp("fecha"));
                        rMap.put("usuario", rs.getString("nombre") + " " + rs.getString("apellido"));
                        resenasAdmin.add(rMap);
                    }
                }
            } catch (Exception e) {
                System.out.println("Error cargando reseñas: " + e.getMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("ventasTotales", ventasTotales);
        request.setAttribute("pedidosPendientes", pedidosPendientes);
        request.setAttribute("stockBajo", stockBajo);
        request.setAttribute("clientesRegistrados", clientesRegistrados);
        request.setAttribute("pedidosRecientes", pedidosRecientes);
        request.setAttribute("listaUsuarios", listaUsuarios);
        request.setAttribute("resenasAdmin", resenasAdmin);

        request.getRequestDispatcher("/Vista/adminDashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        try (Connection conn = getConnection()) {
            if ("actualizarEstadoPedido".equals(action) || "actualizarEstado".equals(action)) {
                int idPedido = Integer.parseInt(request.getParameter("idPedido"));
                String nuevoEstado = request.getParameter("nuevoEstado");
                int nuevoEstadoId = 1;

                if ("En camino".equalsIgnoreCase(nuevoEstado)) {
                    nuevoEstadoId = 2;
                } else if ("Entregado".equalsIgnoreCase(nuevoEstado)) {
                    nuevoEstadoId = 3;
                } else {
                    nuevoEstado = "Pendiente";
                    nuevoEstadoId = 1;
                }

                String sqlUpd = "UPDATE pedido SET estado_pedido = ?, estado_pedido_id_estado_pedido = ? WHERE id_pedido = ?";
                try (PreparedStatement ps = conn.prepareStatement(sqlUpd)) {
                    ps.setString(1, nuevoEstado);
                    ps.setInt(2, nuevoEstadoId);
                    ps.setInt(3, idPedido);
                    ps.executeUpdate();
                }
                request.getSession().setAttribute("mensajeExito", "¡Estado del pedido y envío actualizado correctamente!");
            
            } else if ("actualizarUsuario".equals(action)) {
                int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
                String estadoUsuario = request.getParameter("estadoUsuario");
                int rolesIdRol = Integer.parseInt(request.getParameter("rolesIdRol"));
                int tipoUsuarioId = Integer.parseInt(request.getParameter("tipoUsuarioId"));

                String sqlUsrUpd = "UPDATE usuario SET estado_usuario = ?, roles_id_rol = ?, tipo_usuario_id_tipo_usuario = ? WHERE id_usuario = ?";
                try (PreparedStatement ps = conn.prepareStatement(sqlUsrUpd)) {
                    ps.setString(1, estadoUsuario);
                    ps.setInt(2, rolesIdRol);
                    ps.setInt(3, tipoUsuarioId);
                    ps.setInt(4, idUsuario);
                    ps.executeUpdate();
                }
                request.getSession().setAttribute("mensajeExito", "¡Permisos y estado del usuario actualizados con éxito!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/AdminDashboardServlet");
    }
}