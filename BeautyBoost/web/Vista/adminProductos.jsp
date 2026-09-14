<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Modelo.Usuario" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%
    Usuario admin = (Usuario) session.getAttribute("usuarioLogueado");
    if (admin == null || admin.getRolesIdRol() != 1) { response.sendRedirect(request.getContextPath()+"/Vista/Login.jsp"); return; }
    List<Map<String,Object>> listaProductos=(List<Map<String,Object>>)request.getAttribute("listaProductos");
    List<Map<String,Object>> listaCategorias=(List<Map<String,Object>>)request.getAttribute("listaCategorias");
    String mensajeExito=(String)session.getAttribute("mensajeExito"), mensajeError=(String)session.getAttribute("mensajeError");
    session.removeAttribute("mensajeExito"); session.removeAttribute("mensajeError");
%>
<!DOCTYPE html><html lang="es"><head>
        <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Vista/css/style.css">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@500;600;700&family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet"><title>Beauty Boost | Catálogo e Inventario</title></head>
    <body class="admin-page">
        <header class="admin-top-bar">
            <a class="admin-brand" href="${pageContext.request.contextPath}/AdminDashboardServlet"><img src="${pageContext.request.contextPath}/img/logo.png" alt="Beauty Boost"><span><strong>BEAUTY BOOST</strong><small>Catálogo e Inventario</small></span></a>
            <div class="admin-user-info"><span>👤 <strong><%=admin.getNombre()%> <%=admin.getApellido()%></strong></span><a class="admin-top-link" href="${pageContext.request.contextPath}/AdminDashboardServlet">Dashboard</a><a class="admin-top-link" href="${pageContext.request.contextPath}/LoginServlet?action=logout">Cerrar sesión</a></div>
        </header>
        <main class="admin-wrapper">
            <% if(mensajeExito!=null){%><div class="admin-alert success"><%=mensajeExito%></div><%}%><% if(mensajeError!=null){%><div class="admin-alert error"><%=mensajeError%></div><%}%>
            <div class="admin-page-heading"><div><span class="eyebrow">CENTRO DE CONTROL</span><h1>Catálogo e inventario</h1><p>Registra productos, administra stock y conserva la imagen asociada en MySQL.</p></div><a class="btn-admin secondary" href="${pageContext.request.contextPath}/AdminDashboardServlet">← Dashboard</a></div>
            <section class="content-card admin-form-card"><div class="card-header"><div><h3>Registrar nuevo producto</h3><span>La imagen se almacena en <strong>img/productos</strong> y su ruta en <strong>imagen_url</strong>.</span></div></div>
                <form action="${pageContext.request.contextPath}/AdminProductoServlet" method="POST" enctype="multipart/form-data" class="admin-product-create-grid">
                    <input type="hidden" name="action" value="crear">
                    <label>Nombre producto<input class="form-control" type="text" name="nombre" required></label>
                    <label>Precio (COP)<input class="form-control" type="number" step="0.01" min="0" name="precio" required></label>
                    <label>Stock<input class="form-control" type="number" min="0" name="stock" required></label>
                    <label>Categoría><select class="form-control" name="categoriaId" required><option value="">Seleccione...</option><% if(listaCategorias!=null)for(Map<String,Object> c:listaCategorias){%><option value="<%=c.get("id")%>"><%=c.get("nombre")%></option><%}%></select></label>
                    <label>Estado><select class="form-control" name="estado"><option>Activo</option><option>Inactivo</option></select></label>
                    <label class="file-field">Imagen del producto<input class="form-control" type="file" name="imagen" accept="image/png,image/jpeg,image/webp,image/gif"><small>JPG, PNG, WEBP o GIF · máximo 5 MB</small></label>
                    <div class="admin-create-action"><button class="btn-admin" type="submit">+ Registrar producto</button></div>
                </form></section>
            <section class="content-card"><div class="card-header"><div><h3>Inventario general</h3><span><%=listaProductos==null?0:listaProductos.size()%> productos encontrados.</span></div></div>
                <div class="data-table-wrap"><table class="data-table admin-products-table"><thead><tr><th>Imagen</th><th>ID</th><th>Producto</th><th>Categoría</th><th>Precio</th><th>Stock</th><th>Estado</th><th>Acciones</th></tr></thead><tbody>
                                    <% if(listaProductos!=null&&!listaProductos.isEmpty()){for(Map<String,Object> prod:listaProductos){int id=(Integer)prod.get("id");int stock=(Integer)prod.get("stock");double precio=(Double)prod.get("precio");String img=(String)prod.get("imagen");String estado=(String)prod.get("estado");%>
                            <tr><td><div class="product-admin-thumb"><%if(img!=null&&!img.isBlank()){%><img src="${pageContext.request.contextPath}/<%=img%>" alt="<%=prod.get("nombre")%>"><%}else{%><span>Sin imagen</span><%}%></div></td><td><strong>#<%=id%></strong></td>
                                <td><input form="edit-<%=id%>" class="form-control" name="nombre" value="<%=prod.get("nombre")%>" required></td><td><select form="edit-<%=id%>" class="form-control" name="categoriaId" required><%if(listaCategorias!=null)for(Map<String,Object> c:listaCategorias){int cid=(Integer)c.get("id");%><option value="<%=cid%>" <%=cid==((Integer)prod.get("idCategoria"))?"selected":""%>><%=c.get("nombre")%></option><%}%></select></td>
                                <td><input form="edit-<%=id%>" class="form-control" type="number" step="0.01" min="0" name="precio" value="<%=precio%>" required></td><td><input form="edit-<%=id%>" class="form-control <%=stock<10?"stock-low":""%>" type="number" min="0" name="stock" value="<%=stock%>" required></td><td><select form="edit-<%=id%>" class="form-control" name="estado"><option <%= "Activo".equalsIgnoreCase(estado)?"selected":""%>>Activo</option><option <%= "Inactivo".equalsIgnoreCase(estado)?"selected":""%>>Inactivo</option></select></td>
                                <td><div class="admin-row-actions"><form id="edit-<%=id%>" action="${pageContext.request.contextPath}/AdminProductoServlet" method="POST" enctype="multipart/form-data"><input type="hidden" name="action" value="actualizar"><input type="hidden" name="idProducto" value="<%=id%>"><input type="hidden" name="imagenActual" value="<%=img==null?"":img%>"><label class="mini-file">Cambiar imagen<input form="edit-<%=id%>" type="file" name="imagen" accept="image/*"></label><button class="btn-sm" type="submit">Guardar</button></form><form action="${pageContext.request.contextPath}/AdminProductoServlet" method="POST" onsubmit="return confirm('¿Eliminar este producto?');"><input type="hidden" name="action" value="eliminar"><input type="hidden" name="idProducto" value="<%=id%>"><button class="btn-sm danger" type="submit">Eliminar</button></form></div></td></tr>
                            <%}}else{%><tr><td colspan="8" class="empty-admin-state">No hay productos registrados en el inventario.</td></tr><%}%>
                        </tbody></table></div></section></main></body></html>
