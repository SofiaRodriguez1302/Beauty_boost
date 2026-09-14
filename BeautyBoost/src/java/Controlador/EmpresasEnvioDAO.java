package Controlador;

import Conexion.Conexion;
import Modelo.EmpresasEnvio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmpresasEnvioDAO {

    private final Conexion conect = new Conexion();

    public EmpresasEnvio consultarEmpresa(int id) {
        EmpresasEnvio emp = null;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM empresas_envio WHERE id_empresa_envio = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                emp = new EmpresasEnvio();
                emp.setIdEmpresaEnvio(rs.getInt("id_empresa_envio"));
                emp.setNombreEmpresa(rs.getString("nombre_empresa"));
                emp.setNit(rs.getString("nit"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al consultar empresa de envío: " + e.getMessage());
        }
        return emp;
    }

    public boolean insertarEmpresa(EmpresasEnvio emp) {
        boolean exito = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO empresas_envio (nombre_empresa, nit) VALUES (?,?)");
            ps.setString(1, emp.getNombreEmpresa());
            ps.setString(2, emp.getNit());
            ps.executeUpdate();
            ps.close();
            exito = true;
        } catch (SQLException e) {
            System.out.println("Error al insertar empresa de envío: " + e.getMessage());
        }
        return exito;
    }

    public boolean actualizarEmpresa(EmpresasEnvio emp) {
        boolean actualizado = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE empresas_envio SET nombre_empresa = ?, nit = ? WHERE id_empresa_envio = ?");
            ps.setString(1, emp.getNombreEmpresa());
            ps.setString(2, emp.getNit());
            ps.setInt(3, emp.getIdEmpresaEnvio());

            if (ps.executeUpdate() > 0) {
                actualizado = true;
                System.out.println("Empresa de envío actualizada exitosamente");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al actualizar empresa de envío: " + e.getMessage());
        }
        return actualizado;
    }

    public boolean inactivarEmpresa(int id) {
        boolean inactivado = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE empresas_envio SET estado = 'INACTIVO' WHERE id_empresa_envio = ?");
            ps.setInt(1, id);

            if (ps.executeUpdate() > 0) {
                inactivado = true;
                System.out.println("Empresa de envío inactivada exitosamente");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al inactivar empresa de envío: " + e.getMessage());
        }
        return inactivado;
    }

    public boolean activarEmpresa(int id) {
        boolean activado = false;
        Connection conn = conect.getConn();
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE empresas_envio SET estado = 'ACTIVO' WHERE id_empresa_envio = ?");
            ps.setInt(1, id);

            if (ps.executeUpdate() > 0) {
                activado = true;
                System.out.println("Empresa de envío activada exitosamente");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al activar empresa de envío: " + e.getMessage());
        }
        return activado;
    }

    public boolean eliminarEmpresa(int id) {
        boolean eliminado = false;
        Connection conn = conect.getConn();
        try {
            conn.prepareStatement("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM empresas_envio WHERE id_empresa_envio = ?");
            ps.setInt(1, id);
            if (ps.executeUpdate() > 0) {
                eliminado = true;
            }
            ps.close();
            conn.prepareStatement("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar empresa de envío: " + e.getMessage());
        }
        return eliminado;
    }
}