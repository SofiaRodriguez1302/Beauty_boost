package Controlador;

import Conexion.Conexion;
import Modelo.DireccionEnvio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DireccionEnvioDAO {

    private final Conexion conect = new Conexion();

    // Método clave para listar las direcciones en el perfil del usuario con JOIN a ciudades
    public List<Map<String, Object>> listarDireccionesPorUsuario(int usuarioId) {
        List<Map<String, Object>> lista = new ArrayList<>();
        Connection conn = conect.getConn();
        if (conn == null) return lista;

        // CORRECCIÓN: La llave primaria es id_direccion, y el texto es direccion_envio
        String sql = "SELECT d.id_direccion, d.direccion_envio, c.descripcion_ciudad AS ciudad, c.codigo_postal AS cp " +
                     "FROM direccion_envio d " +
                     "INNER JOIN ciudades c ON d.ciudades_id_ciudades = c.id_ciudades " +
                     "WHERE d.usuario_id_usuario = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> dir = new HashMap<>();
                    dir.put("id", rs.getInt("id_direccion"));
                    // Lo guardamos en el mapa como "direccion" porque así lo lee tu JSP
                    dir.put("direccion", rs.getString("direccion_envio"));
                    dir.put("ciudad", rs.getString("ciudad"));
                    dir.put("cp", rs.getString("cp"));
                    lista.add(dir);
                }
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al listar direcciones por usuario: " + e.getMessage());
        }
        return lista;
    }

    public DireccionEnvio consultarDireccion(int id) {
        DireccionEnvio de = null;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM direccion_envio WHERE id_direccion = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                de = new DireccionEnvio();
                de.setIdDireccion(rs.getInt("id_direccion"));
                de.setDireccionEnvio(rs.getString("direccion_envio"));
                de.setUsuarioIdUsuario(rs.getInt("usuario_id_usuario"));
                de.setCiudadesIdCiudades(rs.getInt("ciudades_id_ciudades"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return de;
    }

    public boolean insertarDireccion(DireccionEnvio de) {
        boolean exito = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO direccion_envio (direccion_envio, usuario_id_usuario, ciudades_id_ciudades) VALUES (?,?,?)");
            ps.setString(1, de.getDireccionEnvio());
            ps.setInt(2, de.getUsuarioIdUsuario());
            ps.setInt(3, de.getCiudadesIdCiudades());
            ps.executeUpdate();
            ps.close();
            exito = true;
        } catch (SQLException e) {
            System.out.println("Error al insertar dirección: " + e.getMessage());
        }
        return exito;
    }

    public boolean actualizarDireccion(DireccionEnvio de) {
        boolean actualizado = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE direccion_envio SET direccion_envio = ?, usuario_id_usuario = ?, ciudades_id_ciudades = ? WHERE id_direccion = ?");
            ps.setString(1, de.getDireccionEnvio());
            ps.setInt(2, de.getUsuarioIdUsuario());
            ps.setInt(3, de.getCiudadesIdCiudades());
            ps.setInt(4, de.getIdDireccion());

            if (ps.executeUpdate() > 0) {
                actualizado = true;
                System.out.println("Dirección de envío actualizada exitosamente");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return actualizado;
    }

    public boolean inactivarDireccion(int id) {
        boolean inactivado = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE direccion_envio SET autorizacion_datos = 'NO' WHERE id_direccion = ?");
            ps.setInt(1, id);

            if (ps.executeUpdate() > 0) {
                inactivado = true;
                System.out.println("Dirección de envío inactivada exitosamente");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return inactivado;
    }

    public boolean activarDireccion(int id) {
        boolean activado = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE direccion_envio SET autorizacion_datos = 'SI' WHERE id_direccion = ?");
            ps.setInt(1, id);

            if (ps.executeUpdate() > 0) {
                activado = true;
                System.out.println("Dirección de envío activada exitosamente");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return activado;
    }

    public boolean eliminarDireccion(int id) {
        boolean eliminado = false;
        Connection conn = conect.getConn();
        try {
            conn.prepareStatement("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM direccion_envio WHERE id_direccion = ?");
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