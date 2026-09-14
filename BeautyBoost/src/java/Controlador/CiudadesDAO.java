package Controlador;

import Conexion.Conexion;
import Modelo.Ciudades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CiudadesDAO {

    private final Conexion conect = new Conexion();

    public Ciudades consultarCiudad(int id) {
        Ciudades cid = null;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM ciudades WHERE id_ciudades = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                cid = new Ciudades();
                cid.setIdCiudades(rs.getInt("id_ciudades"));
                cid.setDescripcionCiudad(rs.getString("descripcion_ciudad"));
                cid.setCodigoPostal(rs.getString("codigo_postal"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al consultar ciudad: " + e.getMessage());
        }
        return cid;
    }

    public List<Map<String, Object>> listarCiudades() {
        List<Map<String, Object>> lista = new ArrayList<>();
        Connection conn = conect.getConn();
        if (conn == null) return lista;

        String sql = "SELECT id_ciudades, descripcion_ciudad, codigo_postal FROM ciudades";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> c = new HashMap<>();
                c.put("id", rs.getInt("id_ciudades"));
                c.put("ciudad", rs.getString("descripcion_ciudad"));
                c.put("cp", rs.getString("codigo_postal"));
                lista.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar ciudades: " + e.getMessage());
        }
        return lista;
    }

    public boolean insertarCiudad(Ciudades cid) {
        boolean exito = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO ciudades (descripcion_ciudad, codigo_postal) VALUES (?,?)");
            ps.setString(1, cid.getDescripcionCiudad());
            ps.setString(2, cid.getCodigoPostal());
            ps.executeUpdate();
            ps.close();
            exito = true;
        } catch (SQLException e) {
            System.out.println("Error al insertar ciudad: " + e.getMessage());
        }
        return exito;
    }

    public boolean actualizarCiudad(Ciudades cid) {
        boolean actualizado = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE ciudades SET descripcion_ciudad = ?, codigo_postal = ? WHERE id_ciudades = ?");
            ps.setString(1, cid.getDescripcionCiudad());
            ps.setString(2, cid.getCodigoPostal());
            ps.setInt(3, cid.getIdCiudades());

            if (ps.executeUpdate() > 0) {
                actualizado = true;
                System.out.println("Ciudad actualizada exitosamente");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al actualizar ciudad: " + e.getMessage());
        }
        return actualizado;
    }

    public boolean inactivarCiudad(int id) {
        boolean inactivado = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE ciudades SET estado = 'INACTIVO' WHERE id_ciudades = ?");
            ps.setInt(1, id);

            if (ps.executeUpdate() > 0) {
                inactivado = true;
                System.out.println("Ciudad inactivada exitosamente");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al inactivar ciudad: " + e.getMessage());
        }
        return inactivado;
    }

    public boolean activarCiudad(int id) {
        boolean activado = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE ciudades SET estado = 'ACTIVO' WHERE id_ciudades = ?");
            ps.setInt(1, id);

            if (ps.executeUpdate() > 0) {
                activado = true;
                System.out.println("Ciudad activada exitosamente");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al activar ciudad: " + e.getMessage());
        }
        return activado;
    }

    public boolean eliminarCiudad(int id) {
        boolean eliminado = false;
        Connection conn = conect.getConn();
        try {
            conn.prepareStatement("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM ciudades WHERE id_ciudades = ?");
            ps.setInt(1, id);
            if (ps.executeUpdate() > 0) {
                eliminado = true;
            }
            ps.close();
            conn.prepareStatement("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar ciudad: " + e.getMessage());
        }
        return eliminado;
    }
}