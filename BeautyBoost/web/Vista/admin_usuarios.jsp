<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Modelo.Usuario" %>
<%@ page import="java.util.List" %>
<%
    Usuario admin = (Usuario) session.getAttribute("usuarioLogueado");
    if (admin == null || admin.getRolesIdRol() != 1) {
        response.sendRedirect(request.getContextPath() + "/Vista/Login.jsp");
        return;
    }
    List<Usuario> listaUsuarios = (List<Usuario>) request.getAttribute("listaUsuarios");
    String mensajeExito = (String) session.getAttribute("mensajeExito");
    String mensajeError = (String) session.getAttribute("mensajeError");
    session.removeAttribute("mensajeExito");
    session.removeAttribute("mensajeError");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Vista/css/style.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@500;600;700&family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet">
    <title>Beauty Boost | Usuarios y Roles</title>
</head>
<body class="admin-page">
<header class="admin-top-bar">
    <a class="admin-brand" href="${pageContext.request.contextPath}/AdminDashboardServlet">
        <img src="${pageContext.request.contextPath}/Vista/img/logo.png" alt="Beauty Boost">
        <span><strong>BEAUTY BOOST</strong><small>Usuarios y Roles</small></span>
    </a>
    <div class="admin-user-info">
        <span>👤 <strong><%= admin.getNombre() %> <%= admin.getApellido() %></strong></span>
        <a class="admin-top-link" href="${pageContext.request.contextPath}/AdminDashboardServlet">Dashboard</a>
        <a class="admin-top-link" href="${pageContext.request.contextPath}/LoginServlet?action=logout">Cerrar sesión</a>
    </div>
</header>
<main class="admin-wrapper">
    <% if (mensajeExito != null) { %><div class="admin-alert success"><%= mensajeExito %></div><% } %>
    <% if (mensajeError != null) { %><div class="admin-alert error"><%= mensajeError %></div><% } %>

    <div class="admin-page-heading">
        <div><span class="eyebrow">CENTRO DE CONTROL</span><h1>Usuarios y roles</h1><p>Administra cuentas, permisos y estado directamente desde MySQL.</p></div>
        <a class="btn-admin secondary" href="${pageContext.request.contextPath}/AdminDashboardServlet">← Dashboard</a>
    </div>

    <section class="content-card admin-form-card">
        <div class="card-header"><div><h3>Crear usuario</h3><span>El nuevo registro se guardará en la tabla <strong>usuario</strong>.</span></div></div>
        <form action="${pageContext.request.contextPath}/AdminUsuariosServlet" method="POST" class="admin-create-grid">
            <input type="hidden" name="action" value="crear">
            <label>Nombre<input class="form-control" name="nombre" required></label>
            <label>Apellido<input class="form-control" name="apellido" required></label>
            <label>Correo<input class="form-control" type="email" name="correo" required></label>
            <label>Teléfono<input class="form-control" name="telefono"></label>
            <label>Identificación<input class="form-control" name="identificacion"></label>
            <label>Contraseña<div class="password-field"><input class="form-control" type="password" id="adminClave" name="clave" required minlength="6"><button type="button" class="password-toggle" data-toggle-password="adminClave" aria-label="Mostrar contraseña">👁</button></div></label>
            <label>Rol
                <select class="form-control" name="rolesIdRol" required><option value="2">2 · Cliente</option><option value="1">1 · Administrador</option></select>
            </label>
            <div class="admin-create-action"><button class="btn-admin" type="submit">+ Crear usuario</button></div>
        </form>
    </section>

    <section class="content-card">
        <div class="card-header"><div><h3>Usuarios registrados</h3><span><%= listaUsuarios == null ? 0 : listaUsuarios.size() %> registros encontrados.</span></div></div>
        <div class="data-table-wrap">
        <table class="data-table admin-users-table">
            <thead><tr><th>ID</th><th>Usuario</th><th>Contacto</th><th>Rol</th><th>Estado</th><th>Acciones</th></tr></thead>
            <tbody>
            <% if (listaUsuarios != null && !listaUsuarios.isEmpty()) { for (Usuario usr : listaUsuarios) {
                String estado = usr.getEstadoUsuario(); if (estado == null || estado.isBlank()) estado = "Activo";
            %>
            <tr>
                <td><strong>#<%= usr.getIdUsuario() %></strong></td>
                <td><div class="admin-user-cell"><strong><%= usr.getNombre() %> <%= usr.getApellido() %></strong><small><%= usr.getNumeroIdentificacion() == null ? "Sin identificación" : usr.getNumeroIdentificacion() %></small></div></td>
                <td><div class="admin-user-cell"><span><%= usr.getCorreo() %></span><small><%= usr.getTelefono() == null ? "Sin teléfono" : usr.getTelefono() %></small></div></td>
                <td>
                    <form action="${pageContext.request.contextPath}/AdminUsuariosServlet" method="POST" class="inline-admin-form">
                        <input type="hidden" name="action" value="actualizarUsuario"><input type="hidden" name="idUsuario" value="<%= usr.getIdUsuario() %>">
                        <select name="rolesIdRol" class="select-status"><option value="1" <%= usr.getRolesIdRol()==1?"selected":"" %>>1 · Admin</option><option value="2" <%= usr.getRolesIdRol()!=1?"selected":"" %>>2 · Cliente</option></select>
                </td>
                <td><select name="estadoUsuario" class="select-status"><option value="Activo" <%= "Activo".equalsIgnoreCase(estado)?"selected":"" %>>Activo</option><option value="Inactivo" <%= "Inactivo".equalsIgnoreCase(estado)?"selected":"" %>>Inactivo</option></select></td>
                <td><div class="admin-row-actions"><button class="btn-sm" type="submit">Guardar</button></form>
                    <form action="${pageContext.request.contextPath}/AdminUsuariosServlet" method="POST" onsubmit="return confirm('¿Eliminar este usuario? Esta acción no se puede deshacer.');"><input type="hidden" name="action" value="eliminar"><input type="hidden" name="idUsuario" value="<%= usr.getIdUsuario() %>"><button class="btn-sm danger" type="submit">Eliminar</button></form>
                </div></td>
            </tr>
            <% }} else { %><tr><td colspan="6" class="empty-admin-state">No hay usuarios registrados en la base de datos.</td></tr><% } %>
            </tbody>
        </table></div>
    </section>
</main>
    <script src="${pageContext.request.contextPath}/Vista/js/beauty-boost.js"></script>
</body>
</html>
