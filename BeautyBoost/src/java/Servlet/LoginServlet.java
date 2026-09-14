package Servlet;

import Controlador.UsuarioDAO;
import Controlador.FavoritosDAO;
import Modelo.Producto;
import java.util.LinkedHashSet;
import java.util.Set;
import Modelo.Usuario;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        // Manejo del cierre de sesión y redirección al catálogo público de productos
        if ("logout".equals(action)) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            response.sendRedirect(request.getContextPath() + "/ProductoServlet");
            return;
        }

        request.getRequestDispatcher("/Vista/Login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        
        String correo = request.getParameter("txtCorreo");
        String password = request.getParameter("txtPassword");

        if (correo != null && !correo.trim().isEmpty() && password != null && !password.trim().isEmpty()) {
            
            UsuarioDAO dao = new UsuarioDAO();
            Usuario usuarioValido = dao.validarLogin(correo.trim(), password.trim());

            if (usuarioValido != null) {
                HttpSession session = request.getSession();
                // Guardamos el objeto Usuario completo para que el perfil y las vistas lo reconozcan sin conflictos
                session.setAttribute("usuarioLogueado", usuarioValido);
                session.setAttribute("usuario", usuarioValido.getNombre());
                // Cargar favoritos persistidos para que catálogo, contador y perfil comiencen sincronizados.
                Set<Integer> favoritosIds = new LinkedHashSet<>();
                for (Producto producto : new FavoritosDAO().obtenerPorUsuario(usuarioValido.getIdUsuario())) {
                    favoritosIds.add(producto.getIdProducto());
                }
                session.setAttribute("favoritos", favoritosIds);
                
                // VALIDACIÓN DIRECTA POR CORREO O TIPO DE USUARIO / ROL
                if ("admin@beautyboost.com".equalsIgnoreCase(usuarioValido.getCorreo()) || usuarioValido.getTipoUsuarioIdTipoUsuario() == 1 || usuarioValido.getRolesIdRol() == 1) {
                    response.sendRedirect(request.getContextPath() + "/AdminDashboardServlet");
                } else {
                    response.sendRedirect(request.getContextPath() + "/ProductoServlet");
                }
            } else {
                request.setAttribute("error", "Acceso denegado. Credenciales incorrectas o cuenta inactiva.");
                request.getRequestDispatcher("/Vista/Login.jsp").forward(request, response);
            }

        } else {
            request.setAttribute("error", "Por favor complete todos los campos de acceso.");
            request.getRequestDispatcher("/Vista/Login.jsp").forward(request, response);
        }
    }
}