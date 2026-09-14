package Controlador;

import Conexion.Conexion;
import Modelo.TipoUsuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TipoUsuarioDAO {

    private final Conexion conect = new Conexion();

    public TipoUsuario consultarTipoUsuario(int id) {
        TipoUsuario tu = null;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM tipo_usuario WHERE id_tipo_usuario = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                tu = new TipoUsuario();
                tu.setIdTipoUsuario(rs.getInt("id_tipo_usuario"));
                tu.setNombreTipoUsuario(rs.getString("nombre_tipo_usuario"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al consultar tipo de usuario: " + e.getMessage());
        }
        return tu;
    }

    public boolean insertarTipoUsuario(TipoUsuario tu) {
        boolean exito = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO tipo_usuario (nombre_tipo_usuario) VALUES (?)");
            ps.setString(1, tu.getNombreTipoUsuario());
            ps.executeUpdate();
            ps.close();
            exito = true;
        } catch (SQLException e) {
            System.out.println("Error al insertar tipo de usuario: " + e.getMessage());
        }
        return exito;
    }

    public boolean actualizarTipoUsuario(TipoUsuario tu) {
        boolean actualizado = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE tipo_usuario SET nombre_tipo_usuario = ? WHERE id_tipo_usuario = ?");
            ps.setString(1, tu.getNombreTipoUsuario());
            ps.setInt(2, tu.getIdTipoUsuario());

            if (ps.executeUpdate() > 0) {
                actualizado = true;
                System.out.println("Tipo de usuario actualizado exitosamente");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al actualizar tipo de usuario: " + e.getMessage());
        }
        return actualizado;
    }

    public boolean inactivarTipoUsuario(int id) {
        boolean inactivado = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE tipo_usuario SET estado = 'INACTIVO' WHERE id_tipo_usuario = ?");
            ps.setInt(1, id);

            if (ps.executeUpdate() > 0) {
                inactivado = true;
                System.out.println("Tipo de usuario inactivado exitosamente");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al inactivar tipo de usuario: " + e.getMessage());
        }
        return inactivado;
    }

    public boolean activarTipoUsuario(int id) {
        boolean activado = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE tipo_usuario SET estado = 'ACTIVO' WHERE id_tipo_usuario = ?");
            ps.setInt(1, id);

            if (ps.executeUpdate() > 0) {
                activado = true;
                System.out.println("Tipo de usuario activado exitosamente");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al activar tipo de usuario: " + e.getMessage());
        }
        return activado;
    }

    public boolean eliminarTipoUsuario(int id) {
        boolean eliminado = false;
        Connection conn = conect.getConn();
        try {
            conn.prepareStatement("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM tipo_usuario WHERE id_tipo_usuario = ?");
            ps.setInt(1, id);
            if (ps.executeUpdate() > 0) {
                eliminado = true;
            }
            ps.close();
            conn.prepareStatement("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar tipo de usuario: " + e.getMessage());
        }
        return eliminado;
    }
}