package Controlador;

import Conexion.Conexion;
import Modelo.ControlEnvio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ControlEnvioDAO {

    private final Conexion conect = new Conexion();

    public ControlEnvio consultarControl(int id) {
        ControlEnvio ce = null;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM control_envio WHERE id_control = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ce = new ControlEnvio();
                ce.setIdControl(rs.getInt("id_control"));
                ce.setFechaEntrega(rs.getDate("fecha_entrega"));
                ce.setCodigoSeguimiento(rs.getString("codigo_seguimiento"));
                ce.setEstadoPedidoIdEstadoPedido(rs.getInt("estado_pedio_id_estado_pedio"));
                ce.setEmpresasEnvioIdEmpresaEnvio(rs.getInt("empresas_envio_id_empresa_envio"));
                ce.setPedidoIdPedido(rs.getInt("pedido_id_pedido"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return ce;
    }

    public boolean insertarControl(ControlEnvio ce) {
        boolean exito = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO control_envio (fecha_entrega, codigo_seguimiento, estado_pedio_id_estado_pedio, empresas_envio_id_empresa_envio, pedido_id_pedido) VALUES (?,?,?,?,?)");
            if (ce.getFechaEntrega() != null) {
                ps.setDate(1, new java.sql.Date(ce.getFechaEntrega().getTime()));
            } else {
                ps.setNull(1, java.sql.Types.DATE);
            }
            ps.setString(2, ce.getCodigoSeguimiento());
            ps.setInt(3, ce.getEstadoPedidoIdEstadoPedido());
            ps.setInt(4, ce.getEmpresasEnvioIdEmpresaEnvio());
            ps.setInt(5, ce.getPedidoIdPedido());
            ps.executeUpdate();
            ps.close();
            exito = true;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return exito;
    }

    public boolean actualizarControl(ControlEnvio ce) {
        boolean actualizado = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE control_envio SET fecha_entrega = ?, codigo_seguimiento = ?, estado_pedio_id_estado_pedio = ?, empresas_envio_id_empresa_envio = ?, pedido_id_pedido = ? WHERE id_control = ?");
            if (ce.getFechaEntrega() != null) {
                ps.setDate(1, new java.sql.Date(ce.getFechaEntrega().getTime()));
            } else {
                ps.setNull(1, java.sql.Types.DATE);
            }
            ps.setString(2, ce.getCodigoSeguimiento());
            ps.setInt(3, ce.getEstadoPedidoIdEstadoPedido());
            ps.setInt(4, ce.getEmpresasEnvioIdEmpresaEnvio());
            ps.setInt(5, ce.getPedidoIdPedido());
            ps.setInt(6, ce.getIdControl());

            if (ps.executeUpdate() > 0) {
                actualizado = true;
                System.out.println("Control de envío actualizado exitosamente");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return actualizado;
    }

    public boolean inactivarControl(int id) {
        boolean inactivado = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE control_envio SET estado = 'INACTIVO' WHERE id_control = ?");
            ps.setInt(1, id);

            if (ps.executeUpdate() > 0) {
                inactivado = true;
                System.out.println("Control de envío inactivado exitosamente");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return inactivado;
    }

    public boolean activarControl(int id) {
        boolean activado = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE control_envio SET estado = 'ACTIVO' WHERE id_control = ?");
            ps.setInt(1, id);

            if (ps.executeUpdate() > 0) {
                activado = true;
                System.out.println("Control de envío activado exitosamente");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return activado;
    }

    public boolean eliminarControl(int id) {
        boolean eliminado = false;
        Connection conn = conect.getConn();
        try {
            conn.prepareStatement("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM control_envio WHERE id_control = ?");
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