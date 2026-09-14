package Servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.LinkedHashSet;
import java.util.Set;

@WebServlet(name = "indexServlet", urlPatterns = {"/indexServlet"})
public class indexServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        if (session.getAttribute("favoritos") == null) {
            session.setAttribute("favoritos", new LinkedHashSet<Integer>());
        }
        // Delegamos en ProductoServlet para que el catálogo siempre cargue los
        // productos activos desde MySQL antes de renderizar Producto.jsp.
        response.sendRedirect(request.getContextPath() + "/ProductoServlet");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet que gestiona el acceso al catálogo de Beauty Boost";
    }
}