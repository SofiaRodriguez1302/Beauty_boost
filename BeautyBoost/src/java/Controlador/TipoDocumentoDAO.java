package Controlador;

import Conexion.Conexion;
import Modelo.TipoDocumento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TipoDocumentoDAO {

    private final Conexion conect = new Conexion();

    public TipoDocumento consultarTipoDoc(int id) {
        TipoDocumento td = null;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM tipo_documento WHERE id_tipo_documento = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                td = new TipoDocumento();
                td.setIdTipoDocumento(rs.getInt("id_tipo_documento"));
                td.setDescripcionTipoDocumento(rs.getString("descripcion_tipo_documento"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al consultar tipo de documento: " + e.getMessage());
        }
        return td;
    }

    public boolean insertarTipoDoc(TipoDocumento td) {
        boolean exito = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO tipo_documento (descripcion_tipo_documento) VALUES (?)");
            ps.setString(1, td.getDescripcionTipoDocumento());
            ps.executeUpdate();
            ps.close();
            exito = true;
        } catch (SQLException e) {
            System.out.println("Error al insertar tipo de documento: " + e.getMessage());
        }
        return exito;
    }

    public boolean actualizarTipoDoc(TipoDocumento td) {
        boolean actualizado = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE tipo_documento SET descripcion_tipo_documento = ? WHERE id_tipo_documento = ?");
            ps.setString(1, td.getDescripcionTipoDocumento());
            ps.setInt(2, td.getIdTipoDocumento());

            if (ps.executeUpdate() > 0) {
                actualizado = true;
                System.out.println("Tipo de documento actualizado exitosamente");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al actualizar tipo de documento: " + e.getMessage());
        }
        return actualizado;
    }

    public boolean inactivarTipoDoc(int id) {
        boolean inactivado = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE tipo_documento SET estado = 'INACTIVO' WHERE id_tipo_documento = ?");
            ps.setInt(1, id);

            if (ps.executeUpdate() > 0) {
                inactivado = true;
                System.out.println("Tipo de documento inactivado exitosamente");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al inactivar tipo de documento: " + e.getMessage());
        }
        return inactivado;
    }

    public boolean activarTipoDoc(int id) {
        boolean activado = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE tipo_documento SET estado = 'ACTIVO' WHERE id_tipo_documento = ?");
            ps.setInt(1, id);

            if (ps.executeUpdate() > 0) {
                activado = true;
                System.out.println("Tipo de documento activado exitosamente");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al activar tipo de documento: " + e.getMessage());
        }
        return activado;
    }

    public boolean eliminarTipoDoc(int id) {
        boolean eliminado = false;
        Connection conn = conect.getConn();
        try {
            conn.prepareStatement("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM tipo_documento WHERE id_tipo_documento = ?");
            ps.setInt(1, id);
            if (ps.executeUpdate() > 0) {
                eliminado = true;
            }
            ps.close();
            conn.prepareStatement("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar tipo de documento: " + e.getMessage());
        }
        return eliminado;
    }
}