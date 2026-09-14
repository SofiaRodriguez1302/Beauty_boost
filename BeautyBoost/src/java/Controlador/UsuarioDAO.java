package Controlador;

import Conexion.Conexion;
import Modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    /**
     * Genera un hash BCrypt para nuevas contraseñas.
     * El costo 12 se mantiene explícito para una configuración segura y reproducible.
     */
    public static String hashClave(String clave) {
        if (clave == null || clave.isEmpty()) return "";
        return BCrypt.hashpw(clave, BCrypt.gensalt(12));
    }

    /** Compatibilidad temporal con registros antiguos que usaban SHA-256. */
    private static String hashSha256Legacy(String clave) {
        if (clave == null) return "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(clave.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder();
            for (byte b : digest) hex.append(String.format("%02x", b));
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 no está disponible", e);
        }
    }


    private final Conexion conect = new Conexion();
    private String querySql;

    public List<Usuario> consultarUsuario() {
        List<Usuario> listaUsuarios = new ArrayList<>();
        Connection conn = conect.getConn();
        if (conn == null) {
            System.out.println("Error crítico: No se pudo obtener conexión en consultarUsuario.");
            return listaUsuarios;
        }
        try {
            querySql = "SELECT id_usuario, nombre, apellido, numero_identificacion, telefono, correo, clave, "
                    + "fecha_nacimiento, fecha_vencimiento_clave, autorizacion_datos, "
                    + "tipo_documento_id_tipo_documento, tipo_usuario_id_tipo_usuario, roles_id_rol, estado_usuario FROM usuario";

            PreparedStatement ps = conn.prepareStatement(querySql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Usuario miUsuario = new Usuario();
                miUsuario.setIdUsuario(rs.getInt("id_usuario"));
                miUsuario.setNombre(rs.getString("nombre"));
                miUsuario.setApellido(rs.getString("apellido"));
                miUsuario.setNumeroIdentificacion(rs.getString("numero_identificacion"));
                miUsuario.setTelefono(rs.getString("telefono"));
                miUsuario.setCorreo(rs.getString("correo"));
                miUsuario.setClave(rs.getString("clave"));
                miUsuario.setFechaNacimiento(rs.getTimestamp("fecha_nacimiento"));
                miUsuario.setFechaVencimientoClave(rs.getDate("fecha_vencimiento_clave"));
                miUsuario.setAutorizacionDatos(rs.getString("autorizacion_datos"));
                miUsuario.setTipoDocumentoIdTipoDocumento(rs.getInt("tipo_documento_id_tipo_documento"));
                miUsuario.setTipoUsuarioIdTipoUsuario(rs.getInt("tipo_usuario_id_tipo_usuario"));
                
                try {
                    miUsuario.setRolesIdRol(rs.getInt("roles_id_rol"));
                    miUsuario.setEstadoUsuario(rs.getString("estado_usuario"));
                } catch (Exception e) {
                }
                
                listaUsuarios.add(miUsuario);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al consultar todos los usuarios: " + e.getMessage());
        }
        return listaUsuarios;
    }

    public Usuario consultarUsuario(String correo) {
        Usuario miUsuario = null;
        Connection conn = conect.getConn();
        if (conn == null) {
            System.out.println("Error crítico: No se pudo obtener conexión en consultarUsuario por correo.");
            return null;
        }
        try {
            querySql = "SELECT id_usuario, nombre, apellido, numero_identificacion, telefono, correo, clave, "
                    + "fecha_nacimiento, fecha_vencimiento_clave, autorizacion_datos, "
                    + "tipo_documento_id_tipo_documento, tipo_usuario_id_tipo_usuario, roles_id_rol, estado_usuario FROM usuario WHERE correo = ?";

            PreparedStatement ps = conn.prepareStatement(querySql);
            ps.setString(1, correo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                miUsuario = new Usuario();
                miUsuario.setIdUsuario(rs.getInt("id_usuario"));
                miUsuario.setNombre(rs.getString("nombre"));
                miUsuario.setApellido(rs.getString("apellido"));
                miUsuario.setNumeroIdentificacion(rs.getString("numero_identificacion"));
                miUsuario.setTelefono(rs.getString("telefono"));
                miUsuario.setCorreo(rs.getString("correo"));
                miUsuario.setClave(rs.getString("clave"));
                miUsuario.setFechaNacimiento(rs.getTimestamp("fecha_nacimiento"));
                miUsuario.setFechaVencimientoClave(rs.getDate("fecha_vencimiento_clave"));
                miUsuario.setAutorizacionDatos(rs.getString("autorizacion_datos"));
                miUsuario.setTipoDocumentoIdTipoDocumento(rs.getInt("tipo_documento_id_tipo_documento"));
                miUsuario.setTipoUsuarioIdTipoUsuario(rs.getInt("tipo_usuario_id_tipo_usuario"));
                try {
                    miUsuario.setRolesIdRol(rs.getInt("roles_id_rol"));
                    miUsuario.setEstadoUsuario(rs.getString("estado_usuario"));
                } catch (Exception e) {}
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al consultar usuario por correo: " + e.getMessage());
        }
        return miUsuario;
    }

    public Usuario consultarUsuario(int id) {
        Usuario miUsuario = null;
        Connection conn = conect.getConn();
        if (conn == null) {
            System.out.println("Error crítico: No se pudo obtener conexión en consultarUsuario por ID.");
            return null;
        }
        try {
            querySql = "SELECT id_usuario, nombre, apellido, numero_identificacion, telefono, correo, clave, "
                    + "fecha_nacimiento, fecha_vencimiento_clave, autorizacion_datos, "
                    + "tipo_documento_id_tipo_documento, tipo_usuario_id_tipo_usuario, roles_id_rol, estado_usuario FROM usuario WHERE id_usuario = ?";

            PreparedStatement ps = conn.prepareStatement(querySql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                miUsuario = new Usuario();
                miUsuario.setIdUsuario(rs.getInt("id_usuario"));
                miUsuario.setNombre(rs.getString("nombre"));
                miUsuario.setApellido(rs.getString("apellido"));
                miUsuario.setNumeroIdentificacion(rs.getString("numero_identificacion"));
                miUsuario.setTelefono(rs.getString("telefono"));
                miUsuario.setCorreo(rs.getString("correo"));
                miUsuario.setClave(rs.getString("clave"));
                miUsuario.setFechaNacimiento(rs.getTimestamp("fecha_nacimiento"));
                miUsuario.setFechaVencimientoClave(rs.getDate("fecha_vencimiento_clave"));
                miUsuario.setAutorizacionDatos(rs.getString("autorizacion_datos"));
                miUsuario.setTipoDocumentoIdTipoDocumento(rs.getInt("tipo_documento_id_tipo_documento"));
                miUsuario.setTipoUsuarioIdTipoUsuario(rs.getInt("tipo_usuario_id_tipo_usuario"));
                try {
                    miUsuario.setRolesIdRol(rs.getInt("roles_id_rol"));
                    miUsuario.setEstadoUsuario(rs.getString("estado_usuario"));
                } catch (Exception e) {}
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al consultar usuario por ID: " + e.getMessage());
        }
        return miUsuario;
    }

    public boolean insertarUsuario(Usuario miUsuario) {
        boolean insertar = false;
        Connection conn = conect.getConn();
        
        if (conn == null) {
            System.out.println("Error crítico: No se pudo obtener conexión a la base de datos.");
            return false;
        }

        try {
            querySql = "INSERT INTO usuario (nombre, apellido, numero_identificacion, telefono, correo, clave, "
                    + "fecha_nacimiento, fecha_vencimiento_clave, autorizacion_datos, "
                    + "tipo_documento_id_tipo_documento, tipo_usuario_id_tipo_usuario, roles_id_rol, estado_usuario) VALUES (?,?,?,?,?,?,?,?,?,?,?,2, 'Activo')";

            PreparedStatement ps = conn.prepareStatement(querySql);
            ps.setString(1, miUsuario.getNombre());
            ps.setString(2, miUsuario.getApellido());
            ps.setString(3, miUsuario.getNumeroIdentificacion());
            ps.setString(4, miUsuario.getTelefono());
            ps.setString(5, miUsuario.getCorreo());
            ps.setString(6, hashClave(miUsuario.getClave()));
            ps.setTimestamp(7, new Timestamp(miUsuario.getFechaNacimiento().getTime()));
            ps.setDate(8, new java.sql.Date(miUsuario.getFechaVencimientoClave().getTime()));
            ps.setString(9, miUsuario.getAutorizacionDatos());
            ps.setInt(10, miUsuario.getTipoDocumentoIdTipoDocumento());
            ps.setInt(11, miUsuario.getTipoUsuarioIdTipoUsuario());

            ps.executeUpdate();
            ps.close();
            insertar = true;
            System.out.println("Dato insertado en BD correctamente");
        } catch (SQLException e) {
            System.out.println("Error al Insertar Usuario: " + e.getMessage());
        }
        return insertar;
    }

    public boolean actualizarUsuario(Usuario miUsuario) {
        boolean actualizar = false;
        Connection conn = conect.getConn();
        if (conn == null) {
            System.out.println("Error crítico: No se pudo obtener conexión en actualizarUsuario.");
            return false;
        }
        try {
            querySql = "UPDATE usuario SET nombre = ?, apellido = ?, numero_identificacion = ?, telefono = ?, "
                    + "correo = ?, clave = ?, fecha_nacimiento = ?, fecha_vencimiento_clave = ?, autorizacion_datos = ?, "
                    + "tipo_documento_id_tipo_documento = ?, tipo_usuario_id_tipo_usuario = ? WHERE id_usuario = ?";

            PreparedStatement ps = conn.prepareStatement(querySql);
            ps.setString(1, miUsuario.getNombre());
            ps.setString(2, miUsuario.getApellido());
            ps.setString(3, miUsuario.getNumeroIdentificacion());
            ps.setString(4, miUsuario.getTelefono());
            ps.setString(5, miUsuario.getCorreo());
            ps.setString(6, miUsuario.getClave());
            ps.setTimestamp(7, new Timestamp(miUsuario.getFechaNacimiento().getTime()));
            ps.setDate(8, new java.sql.Date(miUsuario.getFechaVencimientoClave().getTime()));
            ps.setString(9, miUsuario.getAutorizacionDatos());
            ps.setInt(10, miUsuario.getTipoDocumentoIdTipoDocumento());
            ps.setInt(11, miUsuario.getTipoUsuarioIdTipoUsuario());
            ps.setInt(12, miUsuario.getIdUsuario());

            if (ps.executeUpdate() > 0) {
                actualizar = true;
                System.out.println("Dato actualizado");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al Actualizar el Usuario: " + e.getMessage());
        }
        return actualizar;
    }

    public boolean actualizarPassword(String correo, String nuevaClave) {
        boolean actualizado = false;
        Connection conn = conect.getConn();
        if (conn == null) {
            System.out.println("Error crítico: No se pudo obtener conexión en actualizarPassword.");
            return false;
        }
        try {
            querySql = "UPDATE usuario SET clave = ? WHERE correo = ?";
            PreparedStatement ps = conn.prepareStatement(querySql);
            ps.setString(1, hashClave(nuevaClave));
            ps.setString(2, correo);

            if (ps.executeUpdate() > 0) {
                actualizado = true;
                System.out.println("Contraseña actualizada exitosamente en la BD");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al actualizar la contraseña: " + e.getMessage());
        }
        return actualizado;
    }

    public boolean inactivarUsuario(int id) {
        boolean inactivado = false;
        Connection conn = conect.getConn();
        if (conn == null) {
            System.out.println("Error crítico: No se pudo obtener conexión en inactivarUsuario.");
            return false;
        }
        try {
            querySql = "UPDATE usuario SET estado_usuario = 'Inactivo' WHERE id_usuario = ?";
            PreparedStatement ps = conn.prepareStatement(querySql);
            ps.setInt(1, id);

            if (ps.executeUpdate() > 0) {
                inactivado = true;
                System.out.println("Usuario inactivado");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al inactivar usuario: " + e.getMessage());
        }
        return inactivado;
    }

    public boolean activarUsuario(int id) {
        boolean activado = false;
        Connection conn = conect.getConn();
        if (conn == null) {
            System.out.println("Error crítico: No se pudo obtener conexión en activarUsuario.");
            return false;
        }
        try {
            querySql = "UPDATE usuario SET estado_usuario = 'Activo' WHERE id_usuario = ?";
            PreparedStatement ps = conn.prepareStatement(querySql);
            ps.setInt(1, id);

            if (ps.executeUpdate() > 0) {
                activado = true;
                System.out.println("Usuario activado");
            }
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al activar usuario: " + e.getMessage());
        }
        return activado;
    }

    public boolean eliminarUsuario(int id) {
        boolean eliminado = false;
        Connection conn = conect.getConn();
        if (conn == null) {
            System.out.println("Error crítico: No se pudo obtener conexión en eliminarUsuario.");
            return false;
        }
        try {
            conn.prepareStatement("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM usuario WHERE id_usuario = ?");
            ps.setInt(1, id);
            if (ps.executeUpdate() > 0) {
                eliminado = true;
            }
            ps.close();
            conn.prepareStatement("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar usuario: " + e.getMessage());
        }
        return eliminado;
    }

    // VALIDACIÓN DE LOGIN: compara contra el hash almacenado y permite migrar registros antiguos.
    public Usuario validarLogin(String correo, String clave) {
        Usuario miUsuario = null;
        Connection conn = conect.getConn();
        if (conn == null) {
            System.out.println("Error crítico: No se pudo obtener conexión en validarLogin.");
            return null;
        }
        try {
            querySql = "SELECT id_usuario, nombre, apellido, numero_identificacion, telefono, correo, clave, "
                    + "fecha_nacimiento, fecha_vencimiento_clave, autorizacion_datos, "
                    + "tipo_documento_id_tipo_documento, tipo_usuario_id_tipo_usuario, roles_id_rol, estado_usuario "
                    + "FROM usuario WHERE correo = ? AND (estado_usuario != 'Inactivo' OR estado_usuario IS NULL)";

            PreparedStatement ps = conn.prepareStatement(querySql);
            ps.setString(1, correo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String claveGuardada = rs.getString("clave");
                boolean coincide = false;

                // Nuevos registros: BCrypt. Registros antiguos SHA-256: se migran
                // automáticamente al primer inicio de sesión correcto.
                if (claveGuardada != null && claveGuardada.matches("^\\$2[aby]\\$\\d{2}\\$.*")) {
                    try {
                        coincide = BCrypt.checkpw(clave, claveGuardada);
                    } catch (IllegalArgumentException ex) {
                        coincide = false;
                    }
                } else if (claveGuardada != null) {
                    String legacyHash = hashSha256Legacy(clave);
                    coincide = legacyHash.equalsIgnoreCase(claveGuardada);
                    if (!coincide) {
                        // Compatibilidad puntual con claves antiguas almacenadas en texto plano.
                        coincide = clave.equals(claveGuardada);
                    }
                    if (coincide) {
                        try (PreparedStatement migrar = conn.prepareStatement(
                                "UPDATE usuario SET clave = ? WHERE id_usuario = ?")) {
                            migrar.setString(1, hashClave(clave));
                            migrar.setInt(2, rs.getInt("id_usuario"));
                            migrar.executeUpdate();
                        }
                    }
                }

                if (coincide) {
                    miUsuario = new Usuario();
                    miUsuario.setIdUsuario(rs.getInt("id_usuario"));
                    miUsuario.setNombre(rs.getString("nombre"));
                    miUsuario.setApellido(rs.getString("apellido"));
                    miUsuario.setNumeroIdentificacion(rs.getString("numero_identificacion"));
                    miUsuario.setTelefono(rs.getString("telefono"));
                    miUsuario.setCorreo(rs.getString("correo"));
                    miUsuario.setClave(claveGuardada);
                    miUsuario.setFechaNacimiento(rs.getTimestamp("fecha_nacimiento"));
                    miUsuario.setFechaVencimientoClave(rs.getDate("fecha_vencimiento_clave"));
                    miUsuario.setAutorizacionDatos(rs.getString("autorizacion_datos"));
                    miUsuario.setTipoDocumentoIdTipoDocumento(rs.getInt("tipo_documento_id_tipo_documento"));
                    miUsuario.setTipoUsuarioIdTipoUsuario(rs.getInt("tipo_usuario_id_tipo_usuario"));
                    try {
                        miUsuario.setRolesIdRol(rs.getInt("roles_id_rol"));
                    } catch (Exception e) {}
                }
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al validar login de usuario: " + e.getMessage());
        }
        return miUsuario;
    }
    public boolean actualizarAutorizacionDatos(int idUsuario, String autorizacion) {
        String sql = "UPDATE usuario SET autorizacion_datos = ? WHERE id_usuario = ?";
        try (Connection conn = conect.getConn()) {
            if (conn == null) return false;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, autorizacion);
                ps.setInt(2, idUsuario);
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar autorización de datos: " + e.getMessage());
            return false;
        }
    }

    /** Lista todos los usuarios para el panel administrativo. */
    public List<Usuario> listarTodos() {
        return consultarUsuario();
    }

    /** Actualiza únicamente el rol permitido: 1 = Admin, 2 = Cliente. */
    public boolean actualizarRol(int idUsuario, int rolesIdRol) {
        if (rolesIdRol != 1 && rolesIdRol != 2) return false;
        String sql = "UPDATE usuario SET roles_id_rol = ? WHERE id_usuario = ?";
        try (Connection conn = conect.getConn(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, rolesIdRol);
            ps.setInt(2, idUsuario);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar rol: " + e.getMessage());
            return false;
        }
    }

    /** Actualiza rol y estado desde el panel. */
    public boolean actualizarRolYEstado(int idUsuario, int rolesIdRol, String estado) {
        if (rolesIdRol != 1 && rolesIdRol != 2) return false;
        if (!"Activo".equalsIgnoreCase(estado) && !"Inactivo".equalsIgnoreCase(estado)) return false;
        String sql = "UPDATE usuario SET roles_id_rol = ?, estado_usuario = ? WHERE id_usuario = ?";
        try (Connection conn = conect.getConn(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, rolesIdRol);
            ps.setString(2, estado);
            ps.setInt(3, idUsuario);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar usuario administrativo: " + e.getMessage());
            return false;
        }
    }

    /** Crea un usuario desde el panel administrativo. */
    public boolean crearUsuarioDesdeAdmin(Usuario usuario, int rolesIdRol) {
        if (rolesIdRol != 1 && rolesIdRol != 2 || usuario == null) return false;
        String sql = "INSERT INTO usuario (nombre, apellido, numero_identificacion, telefono, correo, clave, "
                + "fecha_nacimiento, fecha_vencimiento_clave, autorizacion_datos, tipo_documento_id_tipo_documento, "
                + "tipo_usuario_id_tipo_usuario, roles_id_rol, estado_usuario) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = conect.getConn(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getNumeroIdentificacion());
            ps.setString(4, usuario.getTelefono());
            ps.setString(5, usuario.getCorreo());
            ps.setString(6, hashClave(usuario.getClave()));
            ps.setTimestamp(7, usuario.getFechaNacimiento() == null ? new Timestamp(System.currentTimeMillis()) : new Timestamp(usuario.getFechaNacimiento().getTime()));
            ps.setDate(8, usuario.getFechaVencimientoClave() == null ? new java.sql.Date(System.currentTimeMillis()) : new java.sql.Date(usuario.getFechaVencimientoClave().getTime()));
            ps.setString(9, usuario.getAutorizacionDatos() == null ? "SI" : usuario.getAutorizacionDatos());
            ps.setInt(10, usuario.getTipoDocumentoIdTipoDocumento() > 0 ? usuario.getTipoDocumentoIdTipoDocumento() : 1);
            ps.setInt(11, usuario.getTipoUsuarioIdTipoUsuario() > 0 ? usuario.getTipoUsuarioIdTipoUsuario() : (rolesIdRol == 1 ? 1 : 4));
            ps.setInt(12, rolesIdRol);
            ps.setString(13, "Activo");
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al crear usuario desde administración: " + e.getMessage());
            return false;
        }
    }

}