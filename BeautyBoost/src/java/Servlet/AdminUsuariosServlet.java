package Servlet;

import Conexion.Conexion;
import Controlador.UsuarioDAO;
import Modelo.Usuario;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "AdminUsuariosServlet", urlPatterns = {"/AdminUsuariosServlet", "/AdminUsuarioServlet"})
public class AdminUsuariosServlet extends HttpServlet {

    private boolean esAdministrador(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        return usuario != null && usuario.getRolesIdRol() == 1;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !esAdministrador(session)) {
            response.sendRedirect(request.getContextPath() + "/Vista/Login.jsp");
            return;
        }

        UsuarioDAO dao = new UsuarioDAO();
        List<Usuario> usuarios = dao.listarTodos();
        request.setAttribute("listaUsuarios", usuarios);
        request.getRequestDispatcher("/Vista/admin_usuarios.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);
        if (session == null || !esAdministrador(session)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acceso administrativo requerido.");
            return;
        }

        String action = request.getParameter("action");
        UsuarioDAO dao = new UsuarioDAO();
        boolean ok = false;
        String mensaje;

        try {
            if ("actualizarRol".equals(action)) {
                int id = Integer.parseInt(request.getParameter("idUsuario"));
                int rol = Integer.parseInt(request.getParameter("rolesIdRol"));
                if (rol != 1 && rol != 2) throw new IllegalArgumentException("Rol no válido.");
                ok = dao.actualizarRol(id, rol);
                mensaje = ok ? "Rol actualizado correctamente." : "No se pudo actualizar el rol.";

            } else if ("actualizarUsuario".equals(action)) {
                int id = Integer.parseInt(request.getParameter("idUsuario"));
                int rol = Integer.parseInt(request.getParameter("rolesIdRol"));
                String estado = request.getParameter("estadoUsuario");
                ok = dao.actualizarRolYEstado(id, rol, estado);
                mensaje = ok ? "Usuario actualizado correctamente." : "No se pudo actualizar el usuario.";

            } else if ("eliminar".equals(action)) {
                int id = Integer.parseInt(request.getParameter("idUsuario"));
                Usuario actual = (Usuario) session.getAttribute("usuarioLogueado");
                if (actual.getIdUsuario() == id) {
                    ok = false;
                    mensaje = "No puedes eliminar la cuenta administrativa con la que estás conectado.";
                } else {
                    ok = dao.eliminarUsuario(id);
                    mensaje = ok ? "Usuario eliminado correctamente." : "No se pudo eliminar el usuario.";
                }

            } else if ("crear".equals(action)) {
                String nombre = limpiar(request.getParameter("nombre"));
                String apellido = limpiar(request.getParameter("apellido"));
                String correo = limpiar(request.getParameter("correo"));
                String telefono = limpiar(request.getParameter("telefono"));
                String identificacion = limpiar(request.getParameter("identificacion"));
                String clave = request.getParameter("clave");
                int rol = Integer.parseInt(request.getParameter("rolesIdRol"));
                if (rol != 1 && rol != 2) throw new IllegalArgumentException("Rol no válido.");
                if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || clave == null || clave.trim().isEmpty()) {
                    throw new IllegalArgumentException("Nombre, apellido, correo y contraseña son obligatorios.");
                }

                Usuario nuevo = new Usuario();
                nuevo.setNombre(nombre);
                nuevo.setApellido(apellido);
                nuevo.setCorreo(correo);
                nuevo.setTelefono(telefono);
                nuevo.setNumeroIdentificacion(identificacion.isEmpty() ? correo : identificacion);
                nuevo.setClave(clave.trim());
                nuevo.setAutorizacionDatos("SI");
                nuevo.setTipoDocumentoIdTipoDocumento(1);
                nuevo.setTipoUsuarioIdTipoUsuario(rol == 1 ? 1 : 4);
                nuevo.setFechaNacimiento(new Timestamp(System.currentTimeMillis()));
                Date vencimiento = Date.from(LocalDate.now().plusYears(2).atStartOfDay(ZoneId.systemDefault()).toInstant());
                nuevo.setFechaVencimientoClave(vencimiento);
                ok = dao.crearUsuarioDesdeAdmin(nuevo, rol);
                mensaje = ok ? "Usuario creado correctamente." : "No se pudo crear el usuario. Verifica que el correo o identificación no estén repetidos.";
            } else {
                mensaje = "Acción no reconocida.";
            }
        } catch (Exception e) {
            mensaje = "No se pudo completar la operación: " + e.getMessage();
        }

        session.setAttribute(ok ? "mensajeExito" : "mensajeError", mensaje);
        response.sendRedirect(request.getContextPath() + "/AdminUsuariosServlet");
    }

    private String limpiar(String valor) { return valor == null ? "" : valor.trim(); }
}
