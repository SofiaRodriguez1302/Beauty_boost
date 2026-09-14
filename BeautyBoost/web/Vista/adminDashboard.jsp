<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Modelo.Usuario" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%
    Usuario admin = (Usuario) session.getAttribute("usuarioLogueado");
    if (admin == null) {
        response.sendRedirect(request.getContextPath() + "/Vista/Login.jsp");
        return;
    }

    double ventasTotales = request.getAttribute("ventasTotales") != null ? (Double) request.getAttribute("ventasTotales") : 0.0;
    int pedidosPendientes = request.getAttribute("pedidosPendientes") != null ? (Integer) request.getAttribute("pedidosPendientes") : 0;
    int stockBajo = request.getAttribute("stockBajo") != null ? (Integer) request.getAttribute("stockBajo") : 0;
    int clientesRegistrados = request.getAttribute("clientesRegistrados") != null ? (Integer) request.getAttribute("clientesRegistrados") : 0;

    List<Map<String, Object>> resenasAdmin = (List<Map<String, Object>>) request.getAttribute("resenasAdmin");

    String mensajeExito = (String) session.getAttribute("mensajeExito");
    session.removeAttribute("mensajeExito");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Vista/css/style.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@500;600;700&family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet">
    <title>Beauty Boost - Panel de Control Administrativo</title>
    
</head>
<body>

    <header class="admin-top-bar">
        <div class="admin-brand"><img src="${pageContext.request.contextPath}/img/logo.png" alt="Beauty Boost"><div><strong>BEAUTY BOOST</strong><span>Panel de Control Gerencial</span></div></div>
        <div class="admin-user-info">
            <span>Control de Operaciones: <strong><%= admin.getNombre() %> <%= admin.getApellido() %></strong> (ADMIN)</span>
            <a href="${pageContext.request.contextPath}/LoginServlet?action=logout">Cerrar Sesión 🚪</a>
        </div>
    </header>

    <div class="admin-wrapper">

        <% if (mensajeExito != null) { %>
            <div class="alert-success"><%= mensajeExito %></div>
        <% } %>

        <!-- MÉTRICAS SUPERIORES -->
        <div class="metrics-grid">
            <div class="metric-box">
                <span class="metric-title">Ventas Totales</span>
                <span class="metric-number">$ <%= String.format("%,.0f", ventasTotales).replace(",", ".") %></span>
            </div>
            <div class="metric-box bb-inline-3c369bd2b8">
                <span class="metric-title">Pedidos Pendientes</span>
                <span class="metric-number bb-inline-6b7367a040"><%= pedidosPendientes %></span>
            </div>
            <div class="metric-box bb-inline-a1f5827357">
                <span class="metric-title">Stock Bajo (&lt;10)</span>
                <span class="metric-number bb-inline-2c647a1cc7"><%= stockBajo %> Prod.</span>
            </div>
            <div class="metric-box bb-inline-0074a26c6d">
                <span class="metric-title">Usuarios Registrados</span>
                <span class="metric-number bb-inline-093d160a7c"><%= clientesRegistrados %></span>
            </div>
        </div>

        <!-- ACCESOS A MÓDULOS DE GESTIÓN (CRUDs) -->
        <div class="modules-grid">
            <div class="module-box">
                <h4>📦 Catálogo e Inventario</h4>
                <p>Gestión completa de productos, precios, stock crítico por categoría y estado operativo.</p>
                <a href="${pageContext.request.contextPath}/AdminProductoServlet" class="btn-admin">Gestionar Catálogo ➔</a>
            </div>
            <div class="module-box">
                <h4>🚚 Pedidos y Envíos</h4>
                <p>Control de estados operativos, asignación de transportadoras y guías de despacho.</p>
                <a href="${pageContext.request.contextPath}/AdminPedidoServlet" class="btn-admin">Gestionar Pedidos ➔</a>
            </div>
            <div class="module-box">
                <h4>👤 Usuarios y Roles</h4>
                <p>Control de cuentas de clientes y administradores con reasignación de permisos.</p>
                <a href="${pageContext.request.contextPath}/AdminUsuariosServlet" class="btn-admin">Gestionar Usuarios ➔</a>
            </div>
        </div>

        <!-- RESEÑAS Y MONITOREO -->
        <div class="content-card">
            <div class="card-header">
                <h3>Monitoreo de Satisfacción y Feedback</h3>
                <span class="bb-inline-ccdca83d16">Calificaciones dejadas por los usuarios</span>
            </div>
            <% if (resenasAdmin != null && !resenasAdmin.isEmpty()) {
                for (Map<String, Object> res : resenasAdmin) { %>
                    <div class="bb-inline-9b8dd6ca2a">
                        <div>
                            <strong class="bb-inline-e852db80d2"><%= res.get("usuario") %></strong>
                            <p class="bb-inline-feba27e5d9"><%= res.get("observacion") %></p>
                        </div>
                        <div class="bb-inline-13cbe03b9a">
                            <span class="bb-inline-815bd8d387">
                                <% int cal = (Integer) res.get("calificacion");
                                   for(int i=0; i<cal; i++) out.print("★");
                                   for(int i=cal; i<5; i++) out.print("☆"); %>
                            </span>
                            <span class="bb-inline-2cba71e550"><%= res.get("fecha") %></span>
                        </div>
                    </div>
            <%  }
            } else { %>
                <p class="bb-inline-76e5bd5b08">Aún no hay calificaciones registradas.</p>
            <% } %>
        </div>

    </div>

</body>
</html>