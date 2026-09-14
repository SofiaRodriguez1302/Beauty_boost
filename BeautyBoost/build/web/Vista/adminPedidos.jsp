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

    List<Map<String, Object>> listaPedidos = (List<Map<String, Object>>) request.getAttribute("listaPedidos");
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
    <title>Beauty Boost - Gestión de Pedidos y Envíos</title>
    
</head>
<body>

    <header class="admin-top-bar">
        <div class="admin-brand"><img src="${pageContext.request.contextPath}/Vista/img/logo.png" alt="Beauty Boost"><div><strong>BEAUTY BOOST</strong><span>Pedidos y Envíos</span></div></div>
        <div>
            <span>Admin: <strong><%= admin.getNombre() %></strong></span> | 
            <a href="${pageContext.request.contextPath}/AdminDashboardServlet">Volver al Dashboard ⬅</a>
        </div>
    </header>

    <div class="admin-wrapper">

        <% if (mensajeExito != null) { %>
            <div class="alert-success"><%= mensajeExito %></div>
        <% } %>

        <a href="${pageContext.request.contextPath}/AdminDashboardServlet" class="nav-back">← Regresar al Panel Principal</a>

        <div class="content-card">
            <div class="card-header">
                <h3>Listado General de Pedidos y Envíos</h3>
                <span class="bb-inline-ccdca83d16">Actualice el estado de los pedidos de forma interactiva</span>
            </div>

            <table class="data-table">
                <thead>
                    <tr>
                        <th>ID Pedido</th>
                        <th>Fecha</th>
                        <th>Cliente</th>
                        <th>Total (COP)</th>
                        <th>Transportadora</th>
                        <th>Guía de Envíos</th>
                        <th>Estado Actual</th>
                        <th>Cambiar Estado</th>
                    </tr>
                </thead>
                <tbody>
                    <% if (listaPedidos != null && !listaPedidos.isEmpty()) {
                        for (Map<String, Object> ped : listaPedidos) {
                            double totalVal = (Double) ped.get("total");
                            String totalFormateado = String.format("%,.0f", totalVal).replace(",", ".");
                            int estadoId = ped.get("idEstado") != null ? (Integer) ped.get("idEstado") : 1;
                    %>
                        <tr>
                            <td><strong>#<%= ped.get("idPedido") %></strong></td>
                            <td><%= ped.get("fecha") %></td>
                            <td><%= ped.get("cliente") %></td>
                            <td>$ <%= totalFormateado %></td>
                            <td><%= ped.get("empresaEnvio") != null ? ped.get("empresaEnvio") : "Sin asignar" %></td>
                            <td><%= ped.get("codigoSeguimiento") != null ? ped.get("codigoSeguimiento") : "N/A" %></td>
                            <td><span class="bb-inline-90038280ae"><%= ped.get("estado") %></span></td>
                            <td>
                                <form action="${pageContext.request.contextPath}/AdminPedidoServlet" method="POST" class="bb-inline-3a8ee43064">
                                    <input type="hidden" name="action" value="actualizarEstado">
                                    <input type="hidden" name="idPedido" value="<%= ped.get("idPedido") %>">
                                    <select name="nuevoEstadoId" class="form-control" required>
                                        <option value="1" <%= estadoId == 1 ? "selected" : "" %>>1. Pendiente</option>
                                        <option value="2" <%= estadoId == 2 ? "selected" : "" %>>2. En camino</option>
                                        <option value="3" <%= estadoId == 3 ? "selected" : "" %>>3. Entregado</option>
                                    </select>
                                    <button type="submit" class="btn-sm">Guardar</button>
                                </form>
                            </td>
                        </tr>
                    <%  }
                    } else { %>
                        <tr>
                            <td colspan="8" class="bb-inline-6c9cd9926d">No hay pedidos registrados en el sistema.</td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
        </div>

    </div>

</body>
</html>