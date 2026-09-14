package Controlador;

import Conexion.Conexion;
import Modelo.DetalleCarrito;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DetalleCarritoDAO {

    private final Conexion conect = new Conexion();

    public DetalleCarrito consultarDetalle(int id) {
        DetalleCarrito dc = null;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM detalle_carrito WHERE id_detalle_carrito = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                dc = new DetalleCarrito();
                dc.setIdDetalleCarrito(rs.getInt("id_detalle_carrito"));
                dc.setCantidad(rs.getInt("cantidad"));
                dc.setDescripcion(rs.getString("descripcion"));
                dc.setCarritoIdCarrito(rs.getInt("carrito_id_carrito"));
                dc.setProductoIdProducto(rs.getInt("producto_id_producto"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return dc;
    }

    public boolean insertarDetalle(DetalleCarrito dc) {
        boolean exito = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO detalle_carrito (cantidad, descripcion, carrito_id_carrito, producto_id_producto) VALUES (?,?,?,?)");
            ps.setInt(1, dc.getCantidad());
            ps.setString(2, dc.getDescripcion());
            ps.setInt(3, dc.getCarritoIdCarrito());
            ps.setInt(4, dc.getProductoIdProducto());
            ps.executeUpdate();
            ps.close();
            exito = true;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return exito;
    }

    public boolean actualizarDetalle(DetalleCarrito dc) {
        boolean actualizado = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE detalle_carrito SET cantidad = ?, descripcion = ?, carrito_id_carrito = ?, producto_id_producto = ? WHERE id_detalle_carrito = ?");
            ps.setInt(1, dc.getCantidad());
            ps.setString(2, dc.getDescripcion());
            ps.setInt(3, dc.getCarritoIdCarrito());
            ps.setInt(4, dc.getProductoIdProducto());
            ps.setInt(5, dc.getIdDetalleCarrito());

            if (ps.executeUpdate() > 0) {
                actualizado = true;
                System.out.println("Detalle de carrito actualizado exitosamente");
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
            PreparedStatement ps = conn.prepareStatement("UPDATE detalle_carrito SET estado = 'INACTIVO' WHERE id_detalle_carrito = ?");
            ps.setInt(1, id);

            if (ps.executeUpdate() > 0) {
                inactivado = true;
                System.out.println("Detalle de carrito inactivado exitosamente");
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
            PreparedStatement ps = conn.prepareStatement("UPDATE detalle_carrito SET estado = 'ACTIVO' WHERE id_detalle_carrito = ?");
            ps.setInt(1, id);

            if (ps.executeUpdate() > 0) {
                activado = true;
                System.out.println("Detalle de carrito activado exitosamente");
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
            PreparedStatement ps = conn.prepareStatement("DELETE FROM detalle_carrito WHERE id_detalle_carrito = ?");
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