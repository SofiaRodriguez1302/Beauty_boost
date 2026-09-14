package Controlador;

import Conexion.Conexion;
import Modelo.CabezaCarrito;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CabezaCarritoDAO {

    private final Conexion conect = new Conexion();

    public CabezaCarrito consultarCarrito(int id) {
        CabezaCarrito cc = null;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM cabeza_carrito WHERE id_carrito = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                cc = new CabezaCarrito();
                cc.setIdCarrito(rs.getInt("id_carrito"));
                cc.setFechaCreacion(rs.getDate("fecha_creacion"));
                cc.setFechaActualizacion(rs.getDate("fecha_actualizacion"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return cc;
    }

    public boolean insertarCarrito(CabezaCarrito cc) {
        boolean exito = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO cabeza_carrito (fecha_creacion, fecha_actualizacion) VALUES (?,?)");
            ps.setDate(1, new java.sql.Date(cc.getFechaCreacion().getTime()));
            ps.setDate(2, new java.sql.Date(cc.getFechaActualizacion().getTime()));
            ps.executeUpdate();
            ps.close();
            exito = true;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return exito;
    }

    public boolean actualizarCarrito(CabezaCarrito cc) {
        boolean actualizado = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE cabeza_carrito SET fecha_creacion = ?, fecha_actualizacion = ? WHERE id_carrito = ?");
            ps.setDate(1, new java.sql.Date(cc.getFechaCreacion().getTime()));
            ps.setDate(2, new java.sql.Date(cc.getFechaActualizacion().getTime()));
            ps.setInt(3, cc.getIdCarrito());

            if (ps.executeUpdate() > 0) {
                actualizado = true;
                System.out.println("Carrito actualizado exitosamente");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al actualizar carrito: " + e.getMessage());
        }
        return actualizado;
    }

    public boolean inactivarCarrito(int id) {
        boolean inactivado = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE cabeza_carrito SET estado = 'INACTIVO' WHERE id_carrito = ?");
            ps.setInt(1, id);

            if (ps.executeUpdate() > 0) {
                inactivado = true;
                System.out.println("Carrito inactivado exitosamente");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al inactivar carrito: " + e.getMessage());
        }
        return inactivado;
    }

    public boolean activarCarrito(int id) {
        boolean activado = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE cabeza_carrito SET estado = 'ACTIVO' WHERE id_carrito = ?");
            ps.setInt(1, id);

            if (ps.executeUpdate() > 0) {
                activado = true;
                System.out.println("Carrito activado exitosamente");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al activar carrito: " + e.getMessage());
        }
        return activado;
    }

    public boolean eliminarCarrito(int id) {
        boolean eliminado = false;
        Connection conn = conect.getConn();
        try {
            conn.prepareStatement("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM cabeza_carrito WHERE id_carrito = ?");
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