package Servlet;

import Controlador.UsuarioDAO;
import Modelo.Usuario;
import Util.EmailService;
import java.io.IOException;
import java.util.Random;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "RecuperarPasswordServlet", urlPatterns = {"/RecuperarPasswordServlet"})
public class RecuperarPasswordServlet extends HttpServlet {

    // Método para generar una clave aleatoria segura de 8 caracteres
    private String generarClaveAleatoria() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String correo = request.getParameter("txtCorreo");

        if (correo != null && !correo.trim().isEmpty()) {
            UsuarioDAO dao = new UsuarioDAO();
            Usuario usuario = dao.consultarUsuario(correo.trim());

            if (usuario != null) {
                // Generar nueva contraseña temporal
                String nuevaClave = generarClaveAleatoria();

                // Actualizar la contraseña en la base de datos MySQL
                boolean actualizado = dao.actualizarPassword(correo.trim(), nuevaClave);

                if (actualizado) {
                    try {
                        // 1. Correo al usuario con la contraseña temporal
                        EmailService.enviarCorreoRecuperacion(correo.trim(), usuario.getNombre(), nuevaClave);

                        // 2. Notificación al correo institucional de la tienda
                        EmailService.enviarNotificacionAdminRecuperacion(usuario.getNombre(), correo.trim());

                        request.setAttribute("mensaje", "¡Nueva contraseña generada y enviada con éxito! Revisa tu correo.");
                    } catch (Exception e) {
                        e.printStackTrace();
                        request.setAttribute("error", "Error técnico al enviar el correo: " + e.toString());
                    }
                } else {
                    request.setAttribute("error", "No se pudo actualizar la contraseña en la base de datos.");
                }
            } else {
                request.setAttribute("error", "El correo electrónico ingresado no se encuentra registrado en el sistema.");
            }
        } else {
            request.setAttribute("error", "Por favor ingresa un correo válido.");
        }

        request.getRequestDispatcher("/Vista/RecuperarPassword.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/Vista/RecuperarPassword.jsp").forward(request, response);
    }
}
