package Controlador;

import Conexion.Conexion;
import Modelo.Pago;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PagoDAO {

    private final Conexion conect = new Conexion();

    public Pago consultarPago(int id) {
        Pago pago = null;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM pago WHERE id_pago = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                pago = new Pago();
                pago.setIdPago(rs.getInt("id_pago"));
                pago.setFechaPago(rs.getDate("fecha_pago"));
                pago.setMontoPagado(rs.getDouble("monto_pagado"));
                pago.setMetodoDePagoIdMetodoPago(rs.getInt("metodo_de_pago_id_metodo_pago"));
                pago.setPedidoIdPedido(rs.getInt("pedido_id_pedido"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return pago;
    }

    public boolean insertarPago(Pago pago) {
        boolean exito = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO pago (fecha_pago, monto_pagado, metodo_de_pago_id_metodo_pago, pedido_id_pedido) VALUES (?,?,?,?)");
            ps.setDate(1, new java.sql.Date(pago.getFechaPago().getTime()));
            ps.setDouble(2, pago.getMontoPagado());
            ps.setInt(3, pago.getMetodoDePagoIdMetodoPago());
            ps.setInt(4, pago.getPedidoIdPedido());
            ps.executeUpdate();
            ps.close();
            exito = true;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return exito;
    }

    public boolean actualizarPago(Pago pago) {
        boolean actualizado = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE pago SET fecha_pago = ?, monto_pagado = ?, metodo_de_pago_id_metodo_pago = ?, pedido_id_pedido = ? WHERE id_pago = ?");
            ps.setDate(1, new java.sql.Date(pago.getFechaPago().getTime()));
            ps.setDouble(2, pago.getMontoPagado());
            ps.setInt(3, pago.getMetodoDePagoIdMetodoPago());
            ps.setInt(4, pago.getPedidoIdPedido());
            ps.setInt(5, pago.getIdPago());

            if (ps.executeUpdate() > 0) {
                actualizado = true;
                System.out.println("Pago actualizado exitosamente");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return actualizado;
    }

    public boolean inactivarPago(int id) {
        boolean inactivado = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE pago SET autorizacion_datos = 'NO' WHERE id_pago = ?");
            ps.setInt(1, id);

            if (ps.executeUpdate() > 0) {
                inactivado = true;
                System.out.println("Pago inactivado exitosamente");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return inactivado;
    }

    public boolean activarPago(int id) {
        boolean activado = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE pago SET autorizacion_datos = 'SI' WHERE id_pago = ?");
            ps.setInt(1, id);

            if (ps.executeUpdate() > 0) {
                activado = true;
                System.out.println("Pago activado exitosamente");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return activado;
    }

    public boolean eliminarPago(int id) {
        boolean eliminado = false;
        Connection conn = conect.getConn();
        try {
            conn.prepareStatement("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM pago WHERE id_pago = ?");
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