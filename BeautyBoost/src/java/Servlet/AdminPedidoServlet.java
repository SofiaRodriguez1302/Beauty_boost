package Servlet;

import Controlador.PedidoDAO;
import Modelo.Usuario;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "AdminPedidoServlet", urlPatterns = {"/AdminPedidoServlet"})
public class AdminPedidoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Usuario admin = (Usuario) session.getAttribute("usuarioLogueado");

        if (admin == null) {
            response.sendRedirect(request.getContextPath() + "/Vista/Login.jsp");
            return;
        }

        PedidoDAO pedidoDAO = new PedidoDAO();
        List<Map<String, Object>> listaPedidos = pedidoDAO.consultarPedidosRecientes();

        request.setAttribute("listaPedidos", listaPedidos);
        request.getRequestDispatcher("/Vista/adminPedidos.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        if ("actualizarEstado".equals(action)) {
            try {
                int idPedido = Integer.parseInt(request.getParameter("idPedido"));
                int nuevoEstadoId = Integer.parseInt(request.getParameter("nuevoEstadoId"));

                PedidoDAO pedidoDAO = new PedidoDAO();
                boolean ok = pedidoDAO.actualizarEstadoPedido(idPedido, nuevoEstadoId);

                if (ok) {
                    request.getSession().setAttribute("mensajeExito", "¡Estado del pedido actualizado correctamente!");
                } else {
                    request.getSession().setAttribute("mensajeExito", "Error al actualizar el estado del pedido.");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        response.sendRedirect(request.getContextPath() + "/AdminPedidoServlet");
    }
}