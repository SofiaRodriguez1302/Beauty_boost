package Servlet;

import Controlador.UsuarioDAO;
import Modelo.Usuario;
import Util.EmailService;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "RegistroServlet", urlPatterns = {"/RegistroServlet"})
public class RegistroServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/Vista/Registro.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");

        try {
            String nombre = request.getParameter("txtNombre");
            String apellido = request.getParameter("txtApellido");
            String idTipoDocStr = request.getParameter("cboTipoDoc");
            String numDoc = request.getParameter("txtIdentificacion");
            String telefono = request.getParameter("txtTelefono");
            String fechaNacStr = request.getParameter("txtFechaNacimiento");
            String correo = request.getParameter("txtCorreo");
            String clave = request.getParameter("txtClave");
            String autorizacion = request.getParameter("chkAutorizacion");

            if (autorizacion == null) {
                autorizacion = "NO";
            }

            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setNombre(nombre != null ? nombre.trim() : "");
            nuevoUsuario.setApellido(apellido != null ? apellido.trim() : "");
            nuevoUsuario.setNumeroIdentificacion(numDoc != null ? numDoc.trim() : "");
            nuevoUsuario.setTelefono(telefono != null ? telefono.trim() : "");
            nuevoUsuario.setCorreo(correo != null ? correo.trim() : "");
            nuevoUsuario.setClave(clave != null ? clave.trim() : "");
            nuevoUsuario.setAutorizacionDatos(autorizacion);

            int idTipoDoc = 1;
            if (idTipoDocStr != null && !idTipoDocStr.trim().isEmpty()) {
                idTipoDoc = Integer.parseInt(idTipoDocStr);
            }
            nuevoUsuario.setTipoDocumentoIdTipoDocumento(idTipoDoc);
            nuevoUsuario.setTipoUsuarioIdTipoUsuario(4); 

            if (fechaNacStr != null && !fechaNacStr.trim().isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date parsedDate = sdf.parse(fechaNacStr);
                nuevoUsuario.setFechaNacimiento(new Timestamp(parsedDate.getTime()));
            } else {
                nuevoUsuario.setFechaNacimiento(new Timestamp(System.currentTimeMillis()));
            }

            long dosAnios = System.currentTimeMillis() + (365L * 2 * 24 * 60 * 60 * 1000);
            nuevoUsuario.setFechaVencimientoClave(new java.sql.Date(dosAnios));

            UsuarioDAO dao = new UsuarioDAO();
            boolean insertado = dao.insertarUsuario(nuevoUsuario);

            if (insertado) {
                // Correo de bienvenida (no bloquea el registro si el envío falla)
                EmailService.enviarCorreoBienvenida(nuevoUsuario.getCorreo(), nuevoUsuario.getNombre());

                request.setAttribute("success", "¡Cuenta creada con éxito! Ya puedes iniciar sesión.");
                request.getRequestDispatcher("/Vista/Login.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Error al guardar en base de datos. Verifique que la cédula o correo no estén registrados previamente.");
                request.getRequestDispatcher("/Vista/Registro.jsp").forward(request, response);
            }

        } catch (Exception e) {
            System.out.println("Error detallado en Registro Servlet: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error inesperado: " + e.getMessage());
            request.getRequestDispatcher("/Vista/Registro.jsp").forward(request, response);
        }
    }
}