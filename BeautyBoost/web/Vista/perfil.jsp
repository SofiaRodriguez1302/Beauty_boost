<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Modelo.Usuario" %>
<%@ page import="Modelo.Producto" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%
    Usuario user = (Usuario) session.getAttribute("usuarioLogueado");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/Vista/Login.jsp");
        return;
    }

    String tipoDocNombre = (String) request.getAttribute("tipoDocNombre");
    String tipoUsuarioNombre = (String) request.getAttribute("tipoUsuarioNombre");
    List<Map<String, Object>> listaTiposDoc = (List<Map<String, Object>>) request.getAttribute("listaTiposDoc");
    List<Map<String, Object>> listaCiudades = (List<Map<String, Object>>) request.getAttribute("listaCiudades");
    List<Map<String, Object>> listaDirecciones = (List<Map<String, Object>>) request.getAttribute("listaDirecciones");
    List<Map<String, Object>> listaPedidos = (List<Map<String, Object>>) request.getAttribute("listaPedidos");
    List<Map<String, Object>> listaResenas = (List<Map<String, Object>>) request.getAttribute("listaResenas");
    ArrayList<Producto> listaFavoritos = (ArrayList<Producto>) request.getAttribute("listaFavoritos");

    int totalPedidos = (listaPedidos != null) ? listaPedidos.size() : 0;
    int totalDirecciones = (listaDirecciones != null) ? listaDirecciones.size() : 0;
    Integer totalFavoritosAttr = (Integer) request.getAttribute("totalFavoritos");
    int totalFavoritos = (listaFavoritos != null) ? listaFavoritos.size() : ((totalFavoritosAttr != null) ? totalFavoritosAttr : 0);

    // Dirección predeterminada del usuario tomada dinámicamente de su lista
    String direccionDefectoUsuario = "Sin dirección registrada";
    if (listaDirecciones != null && !listaDirecciones.isEmpty()) {
        Map<String, Object> primeraDir = listaDirecciones.get(0);
        String dirTexto = primeraDir.get("direccion") != null ? primeraDir.get("direccion").toString() : "";
        String ciuTexto = primeraDir.get("ciudad") != null ? primeraDir.get("ciudad").toString() : "";
        direccionDefectoUsuario = dirTexto + (ciuTexto.isEmpty() ? "" : ", " + ciuTexto);
    }

    String mensajeExito = (String) session.getAttribute("mensajeExito");
    String mensajeError = (String) session.getAttribute("mensajeError");
    session.removeAttribute("mensajeExito");
    session.removeAttribute("mensajeError");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Beauty Boost - Mi Perfil</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Vista/css/style.css">
    
</head>
<body data-context-path="${pageContext.request.contextPath}" data-user-email="<%= user.getCorreo() %>" data-logged-in="true" data-customer-name="<%= user.getNombre() %> <%= user.getApellido() %>" data-customer-email="<%= user.getCorreo() %>" data-favorite-ids="<%= request.getAttribute("favoritosIds") %>">

    <!-- HEADER SUPERIOR -->
    <header class="custom-header">
        <div class="header-top-row">
            <a href="${pageContext.request.contextPath}/ProductoServlet" class="header-brand">
                <span class="header-logo-circle"><img src="${pageContext.request.contextPath}/img/logo.png" alt="Logo Beauty Boost"></span>
                <span class="app-name-oval"><h1>BEAUTY BOOST</h1></span>
            </a>
            <div class="bb-inline-2247a00234 profile-header-actions">
                <a href="${pageContext.request.contextPath}/ProductoServlet?action=carrito" class="bb-inline-20db35f68d">🛒 <strong>Carrito</strong></a>
                <a href="${pageContext.request.contextPath}/PerfilServlet" class="header-user navbar-user-name" title="Mi Perfil"><span class="header-user-icon">👤</span><strong><%= user.getNombre() %></strong></a>
            </div>
        </div>
    </header>

    <!-- CONTENEDOR PRINCIPAL PERFIL -->
    <main class="profile-container">
        
        <% if (mensajeExito != null) { %>
            <div class="alert-success"><%= mensajeExito %></div>
        <% } %>
        <% if (mensajeError != null) { %>
            <div class="alert-danger"><%= mensajeError %></div>
        <% } %>

        <!-- BREADCRUMB REDISEÑADO Y FUNCIONAL -->
        <div class="breadcrumb">
            <a href="${pageContext.request.contextPath}/ProductoServlet" class="profile-home-btn">🏠 <span>Inicio</span></a>
        </div>

        <!-- HERO CARD -->
        <section class="hero-profile-card">
            <div class="profile-info-left">
                <img src="${pageContext.request.contextPath}/img/logo.png" alt="Avatar" class="avatar-img">
                <div class="user-title-group">
                    <h2><%= user.getNombre() %> <%= user.getApellido() %></h2>
                    <div class="user-meta-details">
                        <span>✉ <%= user.getCorreo() %></span>
                        <span>📱 +57 <%= user.getTelefono() %></span>
                        <span>🆔 Doc: <%= user.getNumeroIdentificacion() %></span>
                    </div>
                </div>
            </div>

            <div class="stats-group">
                <div class="stat-card">
                    <span class="stat-number"><%= totalPedidos %></span>
                    <span class="stat-label">Pedidos</span>
                </div>
                <div class="stat-card">
                    <span class="stat-number"><%= totalFavoritos %></span>
                    <span class="stat-label">Favoritos</span>
                </div>
                <div class="stat-card">
                    <span class="stat-number"><%= totalDirecciones %></span>
                    <span class="stat-label">Direcciones</span>
                </div>
            </div>
        </section>

        <!-- LAYOUT GRID: SIDEBAR + PANEL PRINCIPAL -->
        <div class="profile-grid-layout">
            
            <!-- SIDEBAR MENÚ DE NAVEGACIÓN -->
            <aside>
                <div class="sidebar-card">
                    <span class="sidebar-title">Centro de Control</span>
                    <div class="menu-list">
                        <button class="menu-btn active" id="btnTab1" data-action="switch-tab" data-tab="tab1" data-title="Información Personal">
                            <span>👤 Información Personal</span>
                            <span>›</span>
                        </button>
                        <button class="menu-btn" id="btnTab2" data-action="switch-tab" data-tab="tab2" data-title="Direcciones de Envío">
                            <span>📍 Direcciones de Envío</span>
                            <span class="menu-badge"><%= totalDirecciones %></span>
                        </button>
                        <button class="menu-btn" id="btnTab3" data-action="switch-tab" data-tab="tab3" data-title="Mis Pedidos e Historial">
                            <span>📦 Mis Pedidos e Historial</span>
                            <span class="menu-badge"><%= totalPedidos %></span>
                        </button>
                        <button class="menu-btn" id="btnTab4" data-action="switch-tab" data-tab="tab4" data-title="Mis Favoritos">
                            <span>♥️ Mis Favoritos</span>
                            <span class="menu-badge" id="favoritesCountBadge"><%= totalFavoritos %></span>
                        </button>
                        <button class="menu-btn" id="btnTab5" data-action="switch-tab" data-tab="tab5" data-title="Seguridad y Privacidad">
                            <span>🛡️ Seguridad y Privacidad</span>
                            <span>›</span>
                        </button>
                    </div>

                    <a href="${pageContext.request.contextPath}/LogoutServlet" class="bb-inline-9b5bd45f51">
                        <button class="bb-inline-57ad2d5e8f" type="button">🚪 Cerrar Sesión</button>
                    </a>
                </div>
            </aside>

            <!-- PANEL PRINCIPAL -->
            <section class="content-panel">
                
                <!-- PESTAÑA 1: INFORMACIÓN PERSONAL -->
                <div class="tab-content active" id="tab1">
                    <div class="panel-header">
                        <div class="panel-title">
                            <h3>Información Personal</h3>
                            <p>Actualiza tus datos registrados en el sistema.</p>
                        </div>
                    </div>

                    <form action="${pageContext.request.contextPath}/PerfilServlet" method="POST">
                        <input type="hidden" name="action" value="actualizarDatos">
                        <div class="form-grid-2">
                            <div class="form-group">
                                <label>Tipo de Documento</label>
                                <select class="form-control" name="tipoDocumento" required>
                                    <% if (listaTiposDoc != null) {
                                        for (Map<String, Object> td : listaTiposDoc) { %>
                                            <option value="<%= td.get("id") %>" <%= td.get("descripcion").equals(tipoDocNombre) ? "selected" : "" %>><%= td.get("descripcion") %></option>
                                    <%  }
                                    } %>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>Número de Identificación</label>
                                <input type="text" class="form-control" name="numeroIdentificacion" value="<%= user.getNumeroIdentificacion() %>" required>
                            </div>
                            <div class="form-group">
                                <label>Nombres</label>
                                <input type="text" class="form-control" name="nombre" value="<%= user.getNombre() %>" required>
                            </div>
                            <div class="form-group">
                                <label>Apellidos</label>
                                <input type="text" class="form-control" name="apellido" value="<%= user.getApellido() %>" required>
                            </div>
                            <div class="form-group">
                                <label>Teléfono móvil</label>
                                <input type="text" class="form-control" name="telefono" value="<%= user.getTelefono() %>" required>
                            </div>
                            <div class="form-group">
                                <label>Correo Electrónico</label>
                                <input type="email" class="form-control" name="correo" value="<%= user.getCorreo() %>" required>
                            </div>
                            <div class="form-group">
                                <label>Fecha de Nacimiento</label>
                                <input type="date" class="form-control" name="fechaNacimiento" value="<%= request.getAttribute("fechaNacimiento") != null ? request.getAttribute("fechaNacimiento") : "" %>">
                            </div>
                        </div>

                        <div class="bb-inline-29f0b10879">
                            <button type="submit" class="btn-action-primary">Guardar Cambios</button>
                        </div>
                    </form>
                </div>

                <!-- PESTAÑA 2: DIRECCIONES DE ENVÍO -->
                <div class="tab-content" id="tab2">
                    <div class="panel-header">
                        <div class="panel-title">
                            <h3>Direcciones de Envío</h3>
                            <p>Gestión de direcciones registradas en la tabla <code>direccion_envio</code>.</p>
                        </div>
                    </div>

                    <div class="bb-inline-d36dd2c038">
                        <% if (listaDirecciones != null && !listaDirecciones.isEmpty()) {
                            for (Map<String, Object> dir : listaDirecciones) { %>
                                <div class="bb-inline-61b65c7957">
                                    <h4 class="bb-inline-d5e5fc4090"><%= user.getNombre() %> <%= user.getApellido() %></h4>
                                    <p class="bb-inline-8d6ec1ffbe">
                                        <%= dir.get("direccion") %><br>
                                        <%= dir.get("ciudad") %> • Código Postal: <%= dir.get("cp") %><br>
                                        📞 +57 <%= user.getTelefono() %>
                                    </p>
                                </div>
                        <%  }
                        } else { %>
                            <p class="bb-inline-0c4a925e0b">No tienes direcciones registradas.</p>
                        <% } %>
                    </div>

                    <!-- FORMULARIO AGREGAR DIRECCIÓN -->
                    <div class="bb-inline-3fd2d35d2d">
                        <h4 class="bb-inline-02a3b69f9b">+ Agregar Nueva Dirección</h4>
                        <form action="${pageContext.request.contextPath}/PerfilServlet" method="POST" class="bb-inline-d8152b1a39">
                            <input type="hidden" name="action" value="agregarDireccion">
                            <div class="form-group">
                                <label>Dirección de Envío</label>
                                <input type="text" class="form-control" name="direccion_envio" placeholder="Ej: Calle 100 # 15-20" required>
                            </div>
                            <div class="form-group">
                                <label>Ciudad</label>
                                <select class="form-control" name="id_ciudad" required>
                                    <option value="" disabled selected>-- Selecciona una ciudad --</option>
                                    <% if (listaCiudades != null && !listaCiudades.isEmpty()) {
                                        for (Map<String, Object> c : listaCiudades) { %>
                                            <option value="<%= c.get("id") %>"><%= c.get("ciudad") %> (CP: <%= c.get("cp") %>)</option>
                                    <%  }
                                    } else { %>
                                        <option value="" disabled>No hay ciudades registradas en la base de datos</option>
                                    <% } %>
                                </select>
                            </div>
                            <button type="submit" class="btn-action-primary bb-inline-24f2a743e8">Guardar Dirección</button>
                        </form>
                    </div>
                </div>

                <!-- PESTAÑA 3: MIS PEDIDOS E HISTORIAL -->
                <div class="tab-content" id="tab3">
                    <div class="panel-header bb-inline-b75fad0009">
                        <div class="panel-title">
                            <h3>Mis Pedidos e Historial</h3>
                            <p>Consulta tus órdenes registradas en la base de datos.</p>
                        </div>
                    </div>

                    <div class="bb-inline-efd85b92cb">
                        <% if (listaPedidos != null && !listaPedidos.isEmpty()) {
                            for (Map<String, Object> ped : listaPedidos) { 
                                String numPedido = String.valueOf(ped.get("numPedido"));
                                String fecha = String.valueOf(ped.get("fecha"));
                                String estado = String.valueOf(ped.get("estado") != null ? ped.get("estado") : "Pendiente");
                                double totalVal = ped.get("total") != null ? Double.parseDouble(ped.get("total").toString()) : 0.0;
                                String totalFormateado = String.format("%,.0f", totalVal).replace(",", ".");

                                String dirPedido = (ped.get("direccion") != null && !ped.get("direccion").toString().isEmpty()) 
                                                 ? ped.get("direccion").toString() 
                                                 : direccionDefectoUsuario;

                                List<Map<String, Object>> productosPedido = (List<Map<String, Object>>) ped.get("productos");
                                
                                StringBuilder jsonBuilder = new StringBuilder("[");
                                if (productosPedido != null && !productosPedido.isEmpty()) {
                                    for (int i = 0; i < productosPedido.size(); i++) {
                                        Map<String, Object> p = productosPedido.get(i);
                                        String pNombre = p.get("nombre") != null ? p.get("nombre").toString().replace("\"", "\\\"") : "Producto Beauty Boost";
                                        int pCantidad = p.get("cantidad") != null ? Integer.parseInt(p.get("cantidad").toString()) : 1;
                                        double pSubtotal = p.get("subtotal") != null ? Double.parseDouble(p.get("subtotal").toString()) : totalVal;
                                        String pSubtotalF = String.format("%,.0f", pSubtotal).replace(",", ".");

                                        if (i > 0) jsonBuilder.append(",");
                                        jsonBuilder.append("{\"cantidad\":").append(pCantidad)
                                                   .append(", \"nombre\":\"").append(pNombre)
                                                   .append("\", \"subtotal\":\"").append(pSubtotalF).append("\"}");
                                    }
                                } else {
                                    jsonBuilder.append("{\"cantidad\":1, \"nombre\":\"Productos Cosméticos Variados\", \"subtotal\":\"").append(totalFormateado).append("\"}");
                                }
                                jsonBuilder.append("]");
                                String productosJson = jsonBuilder.toString();

                                String badgeBg = "Entregado".equalsIgnoreCase(estado) ? "#e8f5e9" : "#fff8e1";
                                String badgeColor = "Entregado".equalsIgnoreCase(estado) ? "#2e7d32" : "#b78103";
                        %>
                            <div class="tarjeta-pedido" 
                                 data-num="<%= numPedido %>"
                                 data-fecha="<%= fecha %>"
                                 data-total="$ <%= totalFormateado %>"
                                 data-estado="<%= estado %>"
                                 data-direccion="<%= dirPedido.replace("\"", "&quot;") %>"
                                 data-productos="<%= productosJson.replace("\"", "&quot;") %>"
                                 class="bb-inline-057ed58048">
                                
                                <div class="bb-inline-5f530b0470">
                                    <div>
                                        <h4 class="bb-inline-673250bc8f">Pedido #<%= numPedido %></h4>
                                        <span class="bb-inline-7406d935cd"><%= fecha %></span>
                                    </div>
                                    <span class="status-badge <%= "Entregado".equalsIgnoreCase(estado) ? "status-delivered" : "status-pending" %>"><%= estado %></span>
                                </div>

                                <div class="bb-inline-10b6fe16cc">
                                    <% if (productosPedido != null && !productosPedido.isEmpty()) {
                                        for (Map<String, Object> p : productosPedido) {
                                            String pNom = p.get("nombre") != null ? p.get("nombre").toString() : "Producto Beauty Boost";
                                            int pCant = p.get("cantidad") != null ? Integer.parseInt(p.get("cantidad").toString()) : 1;
                                            double pSub = p.get("subtotal") != null ? Double.parseDouble(p.get("subtotal").toString()) : 0.0;
                                            String pSubF = String.format("%,.0f", pSub).replace(",", ".");
                                    %>
                                        <div class="bb-inline-6d94a70f67">
                                            <span><%= pCant %> x <%= pNom %></span>
                                            <span class="bb-inline-f895f12e34">$ <%= pSubF %></span>
                                        </div>
                                    <%  }
                                    } else { %>
                                        <div class="bb-inline-6d94a70f67">
                                            <span>1 x Productos Cosméticos Variados</span>
                                            <span class="bb-inline-f895f12e34">$ <%= totalFormateado %></span>
                                        </div>
                                    <% } %>
                                </div>

                                <div class="bb-inline-c0b11cfe84">
                                    <div>
                                        <span class="bb-inline-e9ec0c855c">Total</span>
                                        <strong class="bb-inline-14d484b5cb">$ <%= totalFormateado %></strong>
                                    </div>
                                    <div class="bb-inline-d5f7d6ccce">
                                        <button type="button" class="btn-ver-detalles bb-inline-8ee5fd6daa">Ver detalles</button>
                                        <button type="button" class="btn-descargar-factura bb-inline-8ee5fd6daa">Descargar factura</button>
                                    </div>
                                </div>

                            </div>
                        <%  }
                        } else { %>
                            <div class="bb-inline-5d6f605df5">
                                <p class="bb-inline-519a6b17b7">No has realizado pedidos aún.</p>
                            </div>
                        <% } %>
                    </div>
                </div>

                <!-- PESTAÑA 4: MIS FAVORITOS -->
                <div class="tab-content" id="tab4">
                    <div class="panel-header">
                        <div class="panel-title">
                            <h3>Mis Favoritos <span class="profile-count-pill" id="favoritesCountTitle">(<%= totalFavoritos %>)</span></h3>
                            <p>Aquí aparecen los productos que marcaste con el corazón.</p>
                        </div>
                    </div>
                    <div id="favoritesProfileGrid" class="favorites-profile-grid">
                        <% if (listaFavoritos != null && !listaFavoritos.isEmpty()) { %>
                            <% for (Producto favorito : listaFavoritos) {
                                String favId = String.valueOf(favorito.getIdProducto());
                                String favNombre = favorito.getNombreProd() != null ? favorito.getNombreProd() : "Producto Beauty Boost";
                                String favImagen = favorito.getImagenUrl();
                                double favPrecio = favorito.getPrecio();
                                String favPrecioF = String.format("%,.0f", favPrecio).replace(",", ".");
                            %>
                                <article class="favorite-profile-card" data-favorite-profile-id="<%= favId %>">
                                    <div class="favorite-profile-image">
                                        <img src="${pageContext.request.contextPath}/<%= java.net.URLEncoder.encode(favImagen, "UTF-8").replace("+", "%20") %>" alt="<%= favNombre %>">
                                    </div>
                                    <div class="favorite-profile-info">
                                        <span>Favorito</span>
                                        <h4><%= favNombre %></h4>
                                        <strong>$<%= favPrecioF %></strong>
                                        <button type="button" class="favorite-remove-btn" data-action="favorite" data-id="<%= favId %>">♥ Quitar</button>
                                    </div>
                                </article>
                            <% } %>
                        <% } else { %>
                            <div class="favorites-empty"><div>♡</div><h4>Aún no tienes favoritos</h4><p>Marca el corazón de un producto y aparecerá aquí.</p><a href="${pageContext.request.contextPath}/ProductoServlet" class="btn-primary-brown">Explorar productos</a></div>
                        <% } %>
                    </div>
                </div>

                <!-- PESTAÑA 5: SEGURIDAD Y PRIVACIDAD -->
                <div class="tab-content" id="tab5">
                    <div class="panel-header">
                        <div class="panel-title">
                            <h3>Seguridad y Privacidad</h3>
                            <p>Protección de tu cuenta, información personal y datos de navegación.</p>
                        </div>
                    </div>

                    <div class="privacy-section">
                        <h4>Términos y Condiciones del Servicio</h4>
                        <p>Al utilizar Beauty Boost aceptas utilizar la plataforma de manera lícita y responsable. Los productos, precios, disponibilidad, promociones y condiciones de entrega publicados pueden actualizarse sin previo aviso. La confirmación de una compra estará sujeta a la validación de los datos suministrados, disponibilidad del producto y procesamiento del pago.</p>
                        <p>El usuario es responsable de mantener la confidencialidad de sus credenciales y de la información proporcionada en su cuenta. No está permitido utilizar la plataforma para actividades fraudulentas, suplantación de identidad, alteración de información o cualquier conducta que afecte la seguridad del servicio.</p>
                    </div>

                    <div class="privacy-section">
                        <h4>Política de Privacidad y Tratamiento de Datos Personales — Habeas Data</h4>
                        <p>Beauty Boost trata los datos personales suministrados por el usuario para gestionar la cuenta, procesar pedidos, coordinar entregas, atender solicitudes, administrar favoritos y mejorar la experiencia de uso de la plataforma.</p>
                        <p>El tratamiento se realiza de acuerdo con la normativa colombiana aplicable en materia de protección de datos personales, incluyendo la Ley 1581 de 2012 y sus normas reglamentarias. El titular puede conocer, actualizar, rectificar o solicitar la supresión de sus datos cuando legalmente corresponda, así como consultar el uso dado a su información y presentar las solicitudes relacionadas con sus derechos de Habeas Data.</p>
                        <p>La información personal no debe compartirse con terceros ajenos al servicio salvo cuando sea necesario para cumplir la finalidad autorizada, una obligación legal o una orden de autoridad competente. Beauty Boost adopta medidas razonables de seguridad para proteger la información contra acceso, modificación, pérdida o uso no autorizado.</p>
                    </div>

                    <form action="${pageContext.request.contextPath}/PerfilServlet" method="POST" class="privacy-consent-box privacy-consent-form">
                        <input type="hidden" name="action" value="actualizarAutorizacion">
                        <label class="privacy-checkbox-label">
                            <input type="checkbox" name="autorizacionDatos" value="SI" <%= "SI".equalsIgnoreCase(String.valueOf(request.getAttribute("autorizacionDatos"))) ? "checked" : "" %>>
                            <span><strong>Autorizo el tratamiento de mis datos personales.</strong><br><small>Puedes activar o desactivar esta autorización. El cambio se guardará en tu cuenta.</small></span>
                        </label>
                        <button type="submit" class="btn-action-primary">Guardar autorización</button>
                    </form>
                </div>

            </section>

        </div>

    </main>

    <!-- VENTANA MODAL VER DETALLES -->
    <div id="modalDetallePedido" class="bb-inline-81a35c9cca">
        <div class="bb-inline-17b61f8826">
            <div class="bb-inline-bfb8206173">
                <h3 class="bb-inline-e54c96e959" id="mNumPedido">Detalle del Pedido</h3>
                <button data-action="close-order-modal" class="bb-inline-20cf0e10ee">✕</button>
            </div>
            <div class="bb-inline-9d628185af">
                <p class="bb-inline-ff227d0632"><strong>Fecha de Orden:</strong> <span id="mFecha"></span></p>
                <p class="bb-inline-ff227d0632"><strong>Estado Actual:</strong> <span id="mEstado" class="bb-inline-2be10b6143"></span></p>
                <p class="bb-inline-ff227d0632"><strong>Cliente:</strong> <%= user.getNombre() %> <%= user.getApellido() %></p>
                <p class="bb-inline-ff227d0632"><strong>Dirección de Entrega:</strong> <span id="mDireccion"></span></p>
            </div>

            <div class="bb-inline-9622ad4b00">
                <span class="bb-inline-34d9ba89ca">Productos en esta orden</span>
                <div id="mListaProductos" class="bb-inline-da2bd0f01f">
                </div>
            </div>

            <div class="bb-inline-a5b2f35371">
                <strong>Total Pagado:</strong>
                <strong class="bb-inline-09134fe590" id="mTotal"></strong>
            </div>

            <div class="bb-inline-fc8d9bf156">
                <button data-action="close-order-modal" class="btn-action-primary bb-inline-89f798958e">Cerrar</button>
            </div>
        </div>
    </div>

    <!-- SCRIPTS DE PESTAÑAS, MODAL E IMPRESIÓN DE FACTURA -->
    
    <script src="${pageContext.request.contextPath}/Vista/js/beauty-boost.js"></script>
</body>
</html>