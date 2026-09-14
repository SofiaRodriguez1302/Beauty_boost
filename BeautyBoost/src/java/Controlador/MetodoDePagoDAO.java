package Controlador;

import Conexion.Conexion;
import Modelo.MetodoDePago;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MetodoDePagoDAO {

    private final Conexion conect = new Conexion();

    public MetodoDePago consultarMetodo(int id) {
        MetodoDePago mp = null;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM metodo_de_pago WHERE id_metodo_de_pago = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                mp = new MetodoDePago();
                mp.setIdMetodoDePago(rs.getInt("id_metodo_de_pago"));
                mp.setDescripcionMetodoDePago(rs.getString("descripcion_metodo_de_pago"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al consultar método de pago: " + e.getMessage());
        }
        return mp;
    }

    public boolean insertarMetodo(MetodoDePago mp) {
        boolean exito = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO metodo_de_pago (descripcion_metodo_de_pago) VALUES (?)");
            ps.setString(1, mp.getDescripcionMetodoDePago());
            ps.executeUpdate();
            ps.close();
            exito = true;
        } catch (SQLException e) {
            System.out.println("Error al insertar método de pago: " + e.getMessage());
        }
        return exito;
    }

    public boolean actualizarMetodo(MetodoDePago mp) {
        boolean actualizado = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE metodo_de_pago SET descripcion_metodo_de_pago = ? WHERE id_metodo_de_pago = ?");
            ps.setString(1, mp.getDescripcionMetodoDePago());
            ps.setInt(2, mp.getIdMetodoDePago());

            if (ps.executeUpdate() > 0) {
                actualizado = true;
                System.out.println("Método de pago actualizado exitosamente");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al actualizar método de pago: " + e.getMessage());
        }
        return actualizado;
    }

    public boolean inactivarMetodo(int id) {
        boolean inactivado = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE metodo_de_pago SET autorizacion_datos = 'NO' WHERE id_metodo_de_pago = ?");
            ps.setInt(1, id);

            if (ps.executeUpdate() > 0) {
                inactivado = true;
                System.out.println("Método de pago inactivado exitosamente");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al inactivar método de pago: " + e.getMessage());
        }
        return inactivado;
    }

    public boolean activarMetodo(int id) {
        boolean activado = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE metodo_de_pago SET autorizacion_datos = 'SI' WHERE id_metodo_de_pago = ?");
            ps.setInt(1, id);

            if (ps.executeUpdate() > 0) {
                activado = true;
                System.out.println("Método de pago activado exitosamente");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al activar método de pago: " + e.getMessage());
        }
        return activado;
    }

    public boolean eliminarMetodo(int id) {
        boolean eliminado = false;
        Connection conn = conect.getConn();
        try {
            conn.prepareStatement("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM metodo_de_pago WHERE id_metodo_de_pago = ?");
            ps.setInt(1, id);
            if (ps.executeUpdate() > 0) {
                eliminado = true;
            }
            ps.close();
            conn.prepareStatement("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar método de pago: " + e.getMessage());
        }
        return eliminado;
    }
}