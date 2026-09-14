package Controlador;

import Conexion.Conexion;
import Modelo.ResenaUsuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ResenaUsuarioDAO {

    private final Conexion conect = new Conexion();

    public ResenaUsuario consultarResena(int id) {
        ResenaUsuario ru = null;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM resena_usuario WHERE id_resena = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ru = new ResenaUsuario();
                ru.setIdResena(rs.getInt("id_resena"));
                ru.setObservacion(rs.getString("observacion"));
                ru.setCalificacion(rs.getInt("calificacion"));
                ru.setFecha(rs.getTimestamp("fecha"));
                ru.setUsuarioIdUsuario(rs.getInt("usuario_id_usuario"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al consultar reseña: " + e.getMessage());
        }
        return ru;
    }

    public boolean insertarResena(ResenaUsuario ru) {
        boolean exito = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO resena_usuario (observacion, calificacion, fecha, usuario_id_usuario) VALUES (?,?,?,?)");
            ps.setString(1, ru.getObservacion());
            ps.setInt(2, ru.getCalificacion());
            ps.setTimestamp(3, new Timestamp(ru.getFecha().getTime()));
            ps.setInt(4, ru.getUsuarioIdUsuario());
            ps.executeUpdate();
            ps.close();
            exito = true;
        } catch (SQLException e) {
            System.out.println("Error al insertar reseña: " + e.getMessage());
        }
        return exito;
    }

    public boolean actualizarResena(ResenaUsuario ru) {
        boolean actualizado = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE resena_usuario SET observacion = ?, calificacion = ?, fecha = ?, usuario_id_usuario = ? WHERE id_resena = ?");
            ps.setString(1, ru.getObservacion());
            ps.setInt(2, ru.getCalificacion());
            ps.setTimestamp(3, new Timestamp(ru.getFecha().getTime()));
            ps.setInt(4, ru.getUsuarioIdUsuario());
            ps.setInt(5, ru.getIdResena());

            if (ps.executeUpdate() > 0) {
                actualizado = true;
                System.out.println("Reseña actualizada exitosamente");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al actualizar reseña: " + e.getMessage());
        }
        return actualizado;
    }

    public boolean inactivarResena(int id) {
        boolean inactivado = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE resena_usuario SET estado = 'INACTIVO' WHERE id_resena = ?");
            ps.setInt(1, id);

            if (ps.executeUpdate() > 0) {
                inactivado = true;
                System.out.println("Reseña inactivada exitosamente");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al inactivar reseña: " + e.getMessage());
        }
        return inactivado;
    }

    public boolean activarResena(int id) {
        boolean activado = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE resena_usuario SET estado = 'ACTIVO' WHERE id_resena = ?");
            ps.setInt(1, id);

            if (ps.executeUpdate() > 0) {
                activado = true;
                System.out.println("Reseña activada exitosamente");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al activar reseña: " + e.getMessage());
        }
        return activado;
    }

    public boolean eliminarResena(int id) {
        boolean eliminado = false;
        Connection conn = conect.getConn();
        try {
            conn.prepareStatement("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM resena_usuario WHERE id_resena = ?");
            ps.setInt(1, id);
            if (ps.executeUpdate() > 0) {
                eliminado = true;
            }
            ps.close();
            conn.prepareStatement("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar reseña: " + e.getMessage());
        }
        return eliminado;
    }
}