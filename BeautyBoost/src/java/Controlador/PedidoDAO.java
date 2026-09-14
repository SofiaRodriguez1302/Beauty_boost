package Controlador;

import Conexion.Conexion;
import Modelo.Pedido;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PedidoDAO {

    private final Conexion conect = new Conexion();

    // Listar todos los pedidos para el panel de administración de forma segura y robusta
    public List<Map<String, Object>> consultarPedidosRecientes() {
        List<Map<String, Object>> lista = new ArrayList<>();
        Connection conn = conect.getConn();
        if (conn == null) return lista;

        String sql = "SELECT p.id_pedido, p.fecha_pedido, p.numero_pedido, p.total, p.estado_pedido, " +
                     "u.nombre AS nombre_cliente, u.apellido AS apellido_cliente, " +
                     "p.estado_pedido_id_estado_pedido " +
                     "FROM pedido p " +
                     "INNER JOIN usuario u ON p.usuario_id_usuario = u.id_usuario " +
                     "ORDER BY p.id_pedido DESC";

        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> ped = new HashMap<>();
                ped.put("idPedido", rs.getInt("id_pedido"));
                ped.put("fecha", rs.getTimestamp("fecha_pedido") != null ? rs.getTimestamp("fecha_pedido") : rs.getDate("fecha_pedido"));
                ped.put("total", rs.getDouble("total"));
                ped.put("cliente", rs.getString("nombre_cliente") + " " + rs.getString("apellido_cliente"));
                ped.put("estado", rs.getString("estado_pedido") != null ? rs.getString("estado_pedido") : "Pendiente");
                
                int estadoId = rs.getInt("estado_pedido_id_estado_pedido");
                ped.put("idEstado", estadoId > 0 ? estadoId : 1);
                
                ped.put("codigoSeguimiento", "ENV" + rs.getInt("id_pedido") + "A");
                ped.put("empresaEnvio", "Servientrega");
                
                lista.add(ped);
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar pedidos recientes: " + e.getMessage());
        }
        return lista;
    }

    // Método adaptado para que el perfil del usuario reciba mapas compatibles con el JSP
    public List<Map<String, Object>> listarPedidosMapaPorUsuario(int usuarioId) {
        List<Map<String, Object>> lista = new ArrayList<>();
        Connection conn = conect.getConn();
        if (conn == null) return lista;

        String sql = "SELECT id_pedido, fecha_pedido, numero_pedido, total, estado_pedido " +
                     "FROM pedido WHERE usuario_id_usuario = ? ORDER BY id_pedido DESC";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> ped = new HashMap<>();
                    ped.put("idPedido", rs.getInt("id_pedido"));
                    ped.put("numPedido", rs.getInt("numero_pedido"));
                    ped.put("fecha", rs.getTimestamp("fecha_pedido") != null ? rs.getTimestamp("fecha_pedido") : rs.getDate("fecha_pedido"));
                    ped.put("total", rs.getDouble("total"));
                    ped.put("estado", rs.getString("estado_pedido") != null ? rs.getString("estado_pedido") : "Pendiente");
                    
                    // Buscar productos del pedido de forma segura
                    List<Map<String, Object>> productos = new ArrayList<>();
                    try (PreparedStatement psDet = conn.prepareStatement(
                            "SELECT pr.nombre_prod AS nombre, dp.cantidad, (dp.cantidad * dp.precio_unitario) AS subtotal " +
                            "FROM detalle_pedido dp " +
                            "INNER JOIN producto pr ON dp.producto_id_producto = pr.id_producto " +
                            "WHERE dp.pedido_id_pedido = ?")) {
                        psDet.setInt(1, rs.getInt("id_pedido"));
                        try (ResultSet rsDet = psDet.executeQuery()) {
                            while (rsDet.next()) {
                                Map<String, Object> prod = new HashMap<>();
                                prod.put("nombre", rsDet.getString("nombre"));
                                prod.put("cantidad", rsDet.getInt("cantidad"));
                                prod.put("subtotal", rsDet.getDouble("subtotal"));
                                productos.add(prod);
                            }
                        }
                    } catch (Exception e) {
                        // Si falla el detalle, el JSP usará el respaldo por defecto
                    }
                    ped.put("productos", productos);
                    lista.add(ped);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar pedidos mapa por usuario: " + e.getMessage());
        }
        return lista;
    }

    // Actualizar el estado del pedido sincronizando el ID y el texto descriptivo
    public boolean actualizarEstadoPedido(int idPedido, int nuevoEstadoId) {
        boolean actualizado = false;
        Connection conn = conect.getConn();
        if (conn == null) return false;

        String textoEstado = "Pendiente";
        if (nuevoEstadoId == 2) textoEstado = "En camino";
        else if (nuevoEstadoId == 3) textoEstado = "Entregado";

        String sql = "UPDATE pedido SET estado_pedido_id_estado_pedido = ?, estado_pedido = ? WHERE id_pedido = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, nuevoEstadoId);
            ps.setString(2, textoEstado);
            ps.setInt(3, idPedido);
            if (ps.executeUpdate() > 0) {
                actualizado = true;
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al actualizar estado del pedido: " + e.getMessage());
        }
        return actualizado;
    }

    public List<Pedido> listarPedidosPorUsuario(int usuarioId) {
        List<Pedido> listaPedidos = new ArrayList<>();
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM pedido WHERE usuario_id_usuario = ?");
            ps.setInt(1, usuarioId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Pedido ped = new Pedido();
                ped.setIdPedido(rs.getInt("id_pedido"));
                ped.setFechaPedido(rs.getDate("fecha_pedido"));
                ped.setNumeroPedido(rs.getInt("numero_pedido"));
                ped.setTotal(rs.getDouble("total"));
                ped.setEstadoPedido(rs.getString("estado_pedido"));
                ped.setUsuarioIdUsuario(rs.getInt("usuario_id_usuario"));
                listaPedidos.add(ped);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al listar pedidos: " + e.getMessage());
        }
        return listaPedidos;
    }

    public Pedido consultarPedido(int id) {
        Pedido ped = null;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM pedido WHERE id_pedido = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ped = new Pedido();
                ped.setIdPedido(rs.getInt("id_pedido"));
                ped.setFechaPedido(rs.getDate("fecha_pedido"));
                ped.setNumeroPedido(rs.getInt("numero_pedido"));
                ped.setTotal(rs.getDouble("total"));
                ped.setEstadoPedido(rs.getString("estado_pedido"));
                ped.setUsuarioIdUsuario(rs.getInt("usuario_id_usuario"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return ped;
    }

    public boolean insertarPedido(Pedido ped) {
        boolean exito = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO pedido (fecha_pedido, numero_pedido, total, usuario_id_usuario, estado_pedido, estado_pedido_id_estado_pedido) VALUES (?,?,?,?, 'Pendiente', 1)");
            if (ped.getFechaPedido() != null) {
                ps.setDate(1, new java.sql.Date(ped.getFechaPedido().getTime()));
            } else {
                ps.setDate(1, new java.sql.Date(System.currentTimeMillis()));
            }
            ps.setInt(2, ped.getNumeroPedido());
            ps.setDouble(3, ped.getTotal());
            ps.setInt(4, ped.getUsuarioIdUsuario());
            ps.executeUpdate();
            ps.close();
            exito = true;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return exito;
    }

    public boolean actualizarPedido(Pedido ped) {
        boolean actualizado = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE pedido SET fecha_pedido = ?, numero_pedido = ?, total = ?, usuario_id_usuario = ? WHERE id_pedido = ?");
            if (ped.getFechaPedido() != null) {
                ps.setDate(1, new java.sql.Date(ped.getFechaPedido().getTime()));
            } else {
                ps.setDate(1, new java.sql.Date(System.currentTimeMillis()));
            }
            ps.setInt(2, ped.getNumeroPedido());
            ps.setDouble(3, ped.getTotal());
            ps.setInt(4, ped.getUsuarioIdUsuario());
            ps.setInt(5, ped.getIdPedido());

            if (ps.executeUpdate() > 0) {
                actualizado = true;
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return actualizado;
    }

    public boolean inactivarPedido(int id) {
        boolean inactivado = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE pedido SET estado_pedido = 'Inactivo' WHERE id_pedido = ?");
            ps.setInt(1, id);

            if (ps.executeUpdate() > 0) {
                inactivado = true;
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return inactivado;
    }

    public boolean activarPedido(int id) {
        boolean activado = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE pedido SET estado_pedido = 'Activo' WHERE id_pedido = ?");
            ps.setInt(1, id);

            if (ps.executeUpdate() > 0) {
                activado = true;
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return activado;
    }

    public boolean eliminarPedido(int id) {
        boolean eliminado = false;
        Connection conn = conect.getConn();
        try {
            conn.prepareStatement("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM pedido WHERE id_pedido = ?");
            ps.setInt(1, id);
            if (ps.executeUpdate() > 0) {
                eliminado = true;
            }
            ps.close();
            conn.prepareStatement("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return eliminado;
    }
}