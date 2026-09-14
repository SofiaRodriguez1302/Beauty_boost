package Controlador;

import Conexion.Conexion;
import Modelo.DetallePedido;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DetallePedidoDAO {

    private final Conexion conect = new Conexion();

    public DetallePedido consultarDetalle(int id) {
        DetallePedido dp = null;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM detalle_pedido WHERE id_detalle_pedido = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                dp = new DetallePedido();
                dp.setIdDetallePedido(rs.getInt("id_detalle_pedido"));
                dp.setCantidad(rs.getInt("cantidad"));
                dp.setPrecioUnitario(rs.getDouble("precio_unitario"));
                dp.setProductoIdProducto(rs.getInt("producto_id_producto"));
                dp.setPedidoIdPedido(rs.getInt("pedido_id_pedido"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return dp;
    }

    public boolean insertarDetalle(DetallePedido dp) {
        boolean exito = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO detalle_pedido (cantidad, precio_unitario, producto_id_producto, pedido_id_pedido) VALUES (?,?,?,?)");
            ps.setInt(1, dp.getCantidad());
            ps.setDouble(2, dp.getPrecioUnitario());
            ps.setInt(3, dp.getProductoIdProducto());
            ps.setInt(4, dp.getPedidoIdPedido());
            ps.executeUpdate();
            ps.close();
            exito = true;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return exito;
    }

    public boolean actualizarDetalle(DetallePedido dp) {
        boolean actualizado = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE detalle_pedido SET cantidad = ?, precio_unitario = ?, producto_id_producto = ?, pedido_id_pedido = ? WHERE id_detalle_pedido = ?");
            ps.setInt(1, dp.getCantidad());
            ps.setDouble(2, dp.getPrecioUnitario());
            ps.setInt(3, dp.getProductoIdProducto());
            ps.setInt(4, dp.getPedidoIdPedido());
            ps.setInt(5, dp.getIdDetallePedido());

            if (ps.executeUpdate() > 0) {
                actualizado = true;
                System.out.println("Detalle de pedido actualizado exitosamente");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return actualizado;
    }

    public boolean inactivarDetalle(int id) {
        boolean inactivado = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE detalle_pedido SET estado = 'INACTIVO' WHERE id_detalle_pedido = ?");
            ps.setInt(1, id);

            if (ps.executeUpdate() > 0) {
                inactivado = true;
                System.out.println("Detalle de pedido inactivado exitosamente");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return inactivado;
    }

    public boolean activarDetalle(int id) {
        boolean activado = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE detalle_pedido SET estado = 'ACTIVO' WHERE id_detalle_pedido = ?");
            ps.setInt(1, id);

            if (ps.executeUpdate() > 0) {
                activado = true;
                System.out.println("Detalle de pedido activado exitosamente");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return activado;
    }

    public boolean eliminarDetalle(int id) {
        boolean eliminado = false;
        Connection conn = conect.getConn();
        try {
            conn.prepareStatement("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM detalle_pedido WHERE id_detalle_pedido = ?");
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