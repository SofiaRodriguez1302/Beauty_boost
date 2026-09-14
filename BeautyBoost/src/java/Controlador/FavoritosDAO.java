package Controlador;

import Conexion.Conexion;
import Modelo.Favorito;
import Modelo.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/** Persistencia de favoritos por usuario. La tabla favoritos es la fuente de verdad. */
public class FavoritosDAO {

    public ArrayList<Producto> obtenerPorUsuario(int idUsuario) {
        ArrayList<Producto> lista = new ArrayList<>();
        String sql = "SELECT p.id_producto, p.nombre_prod, p.descripcion_prod, p.precio, p.stock, p.categoria_id_categoria, p.imagen_url "
                + "FROM favoritos f INNER JOIN producto p ON p.id_producto = f.producto_id_producto "
                + "WHERE f.usuario_id_usuario = ? ORDER BY f.fecha_agregado DESC, f.id_favorito DESC";
        Conexion conexion = new Conexion();
        try (Connection conn = conexion.getConn()) {
            if (conn == null) return lista;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, idUsuario);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Producto p = new Producto();
                        p.setIdProducto(rs.getInt("id_producto"));
                        p.setNombreProd(rs.getString("nombre_prod"));
                        p.setDescripcionProd(rs.getString("descripcion_prod"));
                        p.setPrecio(rs.getDouble("precio"));
                        p.setStock(rs.getInt("stock"));
                        p.setCategoriaIdCategoria(rs.getInt("categoria_id_categoria"));
                        String imagen = rs.getString("imagen_url");
                        if (imagen == null || imagen.trim().isEmpty()) imagen = "img/default.jpg";
                        else {
                            imagen = imagen.trim().replace('\\', '/');
                            imagen = imagen.replaceFirst("^(?i)Vista/img/", "");
                            imagen = imagen.replaceFirst("^(?i)web/img/", "");
                            imagen = imagen.replaceFirst("^(?i)img/", "");
                            imagen = "img/" + imagen.replaceFirst("^/+", "");
                        }
                        p.setImagen(imagen);
                        lista.add(p);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error obteniendo favoritos del usuario " + idUsuario + ": " + e.getMessage());
        }
        return lista;
    }

    public boolean existe(int idUsuario, int idProducto) {
        String sql = "SELECT 1 FROM favoritos WHERE usuario_id_usuario = ? AND producto_id_producto = ? LIMIT 1";
        Conexion conexion = new Conexion();
        try (Connection conn = conexion.getConn()) {
            if (conn == null) return false;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, idUsuario);
                ps.setInt(2, idProducto);
                try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
            }
        } catch (SQLException e) {
            System.out.println("Error comprobando favorito: " + e.getMessage());
            return false;
        }
    }

    public boolean agregar(int idUsuario, int idProducto) {
        String sql = "INSERT INTO favoritos (usuario_id_usuario, producto_id_producto, fecha_agregado) VALUES (?, ?, CURRENT_TIMESTAMP)";
        Conexion conexion = new Conexion();
        try (Connection conn = conexion.getConn()) {
            if (conn == null) return false;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, idUsuario);
                ps.setInt(2, idProducto);
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error agregando favorito: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int idUsuario, int idProducto) {
        String sql = "DELETE FROM favoritos WHERE usuario_id_usuario = ? AND producto_id_producto = ?";
        Conexion conexion = new Conexion();
        try (Connection conn = conexion.getConn()) {
            if (conn == null) return false;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, idUsuario);
                ps.setInt(2, idProducto);
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error eliminando favorito: " + e.getMessage());
            return false;
        }
    }
}
