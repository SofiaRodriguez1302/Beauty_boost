package Controlador;

import Conexion.Conexion;
import Modelo.Categoria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    private final Conexion conect = new Conexion();

    public List<Categoria> consultarCategorias() {
        List<Categoria> lista = new ArrayList<>();
        Connection conn = conect.getConn();
        if (conn == null) {
            System.out.println("Error crítico: No se pudo obtener conexión en consultarCategorias.");
            return lista;
        }
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT id_categoria, nombre_categoria FROM categoria");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Categoria cat = new Categoria();
                cat.setIdCategoria(rs.getInt("id_categoria"));
                cat.setNombreCategoria(rs.getString("nombre_categoria"));
                lista.add(cat);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al listar categorías: " + e.getMessage());
        }
        return lista;
    }

    public Categoria consultarCategoria(int id) {
        Categoria cat = null;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM categoria WHERE id_categoria = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                cat = new Categoria();
                cat.setIdCategoria(rs.getInt("id_categoria"));
                cat.setNombreCategoria(rs.getString("nombre_categoria"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al consultar categoría: " + e.getMessage());
        }
        return cat;
    }

    public boolean insertarCategoria(Categoria cat) {
        boolean exito = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO categoria (nombre_categoria) VALUES (?)");
            ps.setString(1, cat.getNombreCategoria());
            ps.executeUpdate();
            ps.close();
            exito = true;
        } catch (SQLException e) {
            System.out.println("Error al insertar categoría: " + e.getMessage());
        }
        return exito;
    }

    public boolean actualizarCategoria(Categoria cat) {
        boolean actualizado = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE categoria SET nombre_categoria = ? WHERE id_categoria = ?");
            ps.setString(1, cat.getNombreCategoria());
            ps.setInt(2, cat.getIdCategoria());

            if (ps.executeUpdate() > 0) {
                actualizado = true;
                System.out.println("Categoría actualizada exitosamente");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al actualizar categoría: " + e.getMessage());
        }
        return actualizado;
    }

    public boolean inactivarCategoria(int id) {
        boolean inactivado = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE categoria SET estado = 'INACTIVO' WHERE id_categoria = ?");
            ps.setInt(1, id);

            if (ps.executeUpdate() > 0) {
                inactivado = true;
                System.out.println("Categoría inactivada exitosamente");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al inactivar categoría: " + e.getMessage());
        }
        return inactivado;
    }

    public boolean activarCategoria(int id) {
        boolean activado = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE categoria SET estado = 'ACTIVO' WHERE id_categoria = ?");
            ps.setInt(1, id);

            if (ps.executeUpdate() > 0) {
                activado = true;
                System.out.println("Categoría activada exitosamente");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al activar categoría: " + e.getMessage());
        }
        return activado;
    }

    public boolean eliminarCategoria(int id) {
        boolean eliminado = false;
        Connection conn = conect.getConn();
        try {
            conn.prepareStatement("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM categoria WHERE id_categoria = ?");
            ps.setInt(1, id);
            if (ps.executeUpdate() > 0) {
                eliminado = true;
            }
            ps.close();
            conn.prepareStatement("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar categoría: " + e.getMessage());
        }
        return eliminado;
    }
}