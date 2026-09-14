package Controlador;

import Conexion.Conexion;
import Modelo.EstadoPedido;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EstadoPedidoDAO {

    private final Conexion conect = new Conexion();

    public EstadoPedido consultarEstado(int id) {
        EstadoPedido est = null;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM estado_pedido WHERE id_estado_pedio = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                est = new EstadoPedido();
                est.setIdEstadoPedido(rs.getInt("id_estado_pedio"));
                est.setDescripcionEstado(rs.getString("descripcion_estado"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al consultar estado de pedido: " + e.getMessage());
        }
        return est;
    }

    public boolean insertarEstado(EstadoPedido est) {
        boolean exito = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO estado_pedido (descripcion_estado) VALUES (?)");
            ps.setString(1, est.getDescripcionEstado());
            ps.executeUpdate();
            ps.close();
            exito = true;
        } catch (SQLException e) {
            System.out.println("Error al insertar estado de pedido: " + e.getMessage());
        }
        return exito;
    }

    public boolean actualizarEstado(EstadoPedido est) {
        boolean actualizado = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE estado_pedido SET descripcion_estado = ? WHERE id_estado_pedio = ?");
            ps.setString(1, est.getDescripcionEstado());
            ps.setInt(2, est.getIdEstadoPedido());

            if (ps.executeUpdate() > 0) {
                actualizado = true;
                System.out.println("Estado de pedido actualizado exitosamente");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al actualizar estado de pedido: " + e.getMessage());
        }
        return actualizado;
    }

    public boolean inactivarEstado(int id) {
        boolean inactivado = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE estado_pedido SET autorizacion_datos = 'NO' WHERE id_estado_pedio = ?");
            ps.setInt(1, id);

            if (ps.executeUpdate() > 0) {
                inactivado = true;
                System.out.println("Estado de pedido inactivado exitosamente");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al inactivar estado de pedido: " + e.getMessage());
        }
        return inactivado;
    }

    public boolean activarEstado(int id) {
        boolean activado = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE estado_pedido SET autorizacion_datos = 'SI' WHERE id_estado_pedio = ?");
            ps.setInt(1, id);

            if (ps.executeUpdate() > 0) {
                activado = true;
                System.out.println("Estado de pedido activado exitosamente");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al activar estado de pedido: " + e.getMessage());
        }
        return activado;
    }

    public boolean eliminarEstado(int id) {
        boolean eliminado = false;
        Connection conn = conect.getConn();
        try {
            conn.prepareStatement("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM estado_pedido WHERE id_estado_pedio = ?");
            ps.setInt(1, id);
            if (ps.executeUpdate() > 0) {
                eliminado = true;
            }
            ps.close();
            conn.prepareStatement("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar estado de pedido: " + e.getMessage());
        }
        return eliminado;
    }
}