<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%
    boolean usuarioLogueado = (session.getAttribute("usuarioLogueado") != null || session.getAttribute("usuario") != null || session.getAttribute("user") != null);
    
    // Obtenemos el correo del usuario logueado para aislar sus favoritos y carrito en localStorage
    String correoLogin = "";
    String nombreLogin = "";
    if (session.getAttribute("usuarioLogueado") != null) {
        Modelo.Usuario uTemp = (Modelo.Usuario) session.getAttribute("usuarioLogueado");
        correoLogin = uTemp.getCorreo();
        nombreLogin = uTemp.getNombre();
    } else if (session.getAttribute("usuario") != null) {
        correoLogin = session.getAttribute("usuario").toString();
            nombreLogin = correoLogin;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Beauty Boost - Catálogo</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Vista/css/style.css">
</head>
<body class="catalog-page" data-context-path="${pageContext.request.contextPath}" data-user-email="<%= correoLogin %>" data-logged-in="<%= usuarioLogueado %>" data-favorite-ids="<%= session.getAttribute("favoritos") != null ? session.getAttribute("favoritos") : "[]" %>">
    <%
        java.util.List<Modelo.Producto> productosCatalogo =
                (java.util.List<Modelo.Producto>) request.getAttribute("productosCatalogo");
    %>

    <!-- HEADER SUPERIOR ESTRUCTURADO -->
    <header class="custom-header">
        <div class="header-top-row">
            <div class="header-logo-circle">
                <img src="${pageContext.request.contextPath}/Vista/img/logo.png" alt="Logo Beauty Boost">
            </div>
            <div class="app-name-oval">
                <h1>BEAUTY BOOST</h1>
            </div>
            <div class="header-right-space"></div>
        </div>
    </header>

    <!-- NAVBAR HORIZONTAL DE NAVEGACIÓN Y BÚSQUEDA -->
    <nav class="horizontal-navbar">
        <div class="navbar-categories">
            <a href="javascript:void(0)" data-category="todo" class="navbar-link">Todo</a>
            <a href="javascript:void(0)" data-category="maquillaje" class="navbar-link">Maquillaje</a>
            <a href="javascript:void(0)" data-category="cuidado-facial" class="navbar-link">Cuidado Facial</a>
            <a href="javascript:void(0)" data-category="brochas" class="navbar-link">Brochas</a>
            <a href="javascript:void(0)" data-category="novedades" class="navbar-link">Novedades</a>
            <a href="javascript:void(0)" data-category="mas-vendidos" class="navbar-link">Más Vendidos</a>
        </div>
        <div class="navbar-right">
            <div class="input-container">
                <input placeholder="Buscar productos..." class="input" id="txtBuscar" type="text" onkeyup="filtrarProductos()">
                <svg class="icon" viewBox="0 0 24 24">
                    <path d="M15.5 14h-.79l-.28-.27A6.471 6.471 0 0 0 16 9.5 6.5 6.5 0 1 0 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z"/>
                </svg>
            </div>
            <% if (usuarioLogueado) { %><a href="${pageContext.request.contextPath}/PerfilServlet" class="header-user navbar-user-name" title="Mi Perfil"><span class="header-user-icon">👤</span><strong><%= nombreLogin %></strong></a><% } else { %><a href="${pageContext.request.contextPath}/LoginServlet" class="header-user navbar-user-name login-header-btn">Iniciar Sesión</a><% } %>
            <a href="javascript:void(0);" data-action="cart-access" class="navbar-icon-btn cart-nav-button" title="Carrito" aria-label="Carrito">🛒<span class="cart-count-badge is-empty" data-cart-count>0</span></a>
        </div>
    </nav>

    <!-- CONTENEDOR PRINCIPAL DEL CATÁLOGO -->
    <main class="catalog-container">
        <div class="slider-wrapper-layout">
            <div class="products-grid" id="productGrid"></div>
        </div>
    </main>

    <!-- MODAL PREMIUM DE DETALLES -->
    <div id="productModal" class="product-modal-overlay" role="dialog" aria-modal="true" aria-labelledby="modalNombre">
        <div class="modal-panel product-modal-panel">
            <button type="button" data-action="close-product-modal" class="modal-close" aria-label="Cerrar detalles">×</button>
            <div class="modal-product-image">
                <img id="modalImg" src="${pageContext.request.contextPath}/img/default.jpg" alt="Producto" class="modal-product-image-img">
            </div>
            <div class="modal-product-content">
                <span id="modalCategoria" class="modal-category-badge">Beauty Boost</span>
                <h2 id="modalNombre" class="modal-product-title"></h2>
                <div id="modalPrecio" class="modal-product-price"></div>
                <div class="modal-stock-row"><span class="modal-info-label">Disponibilidad</span><span id="modalStock" class="modal-stock">En existencia</span></div>
                <section class="modal-detail-section"><h4>Descripción del producto</h4><p id="modalDescripcion" class="modal-product-description"></p></section>
                <section class="modal-detail-section"><h4>Modo de empleo</h4><p id="modalModoUso" class="modal-use-text"></p></section>
                <div class="modal-quantity-row">
                    <span class="modal-quantity-label">Cantidad</span>
                    <div class="modal-quantity-control" aria-label="Seleccionar cantidad">
                        <button type="button" data-action="modal-qty" data-change="-1" class="modal-qty-btn" aria-label="Disminuir cantidad">−</button>
                        <input type="text" id="modalCantidadInput" value="1" readonly aria-label="Cantidad">
                        <button type="button" data-action="modal-qty" data-change="1" class="modal-qty-btn" aria-label="Aumentar cantidad">+</button>
                    </div>
                </div>
                <button type="button" data-action="add-modal" class="modal-add-button">🛍 Agregar al Carrito</button>
            </div>
        </div>
    </div>

    <!-- MODAL DE ÉXITO CARRITO -->
    <div id="cartSuccessModal" class="success-modal-overlay bb-inline-c91f383c07">
        <div class="bb-inline-0ecafc3766">
            <div class="bb-inline-815b262785">🛍️</div>
            <h3 class="bb-inline-5424676451">¡Producto agregado!</h3>
            <p class="bb-inline-cd9b1cf8f5">El producto se ha añadido correctamente a tu carrito de compras.</p>
            <div class="bb-inline-d5f7d6ccce">
                <button data-action="close-success" class="bb-inline-f34dd080ae">Seguir comprando</button>
                <button data-action="go-cart" class="bb-inline-f83b3aa116">Ver carrito</button>
            </div>
        </div>
    </div>

    <!-- MODAL DE ALERTA -->
    <div id="customAlertModal" class="login-alert-overlay bb-inline-c4da155410">
        <div class="bb-inline-0ecafc3766">
            <div class="bb-inline-5711b60f30">🔒</div>
            <h3 class="bb-inline-5424676451">Iniciar Sesión</h3>
            <p id="customAlertMessage" class="bb-inline-cd9b1cf8f5"></p>
            <button data-action="go-login" class="bb-inline-d6a764f76e">IR A INICIAR SESIÓN</button>
        </div>
    </div>

    <!-- Fallback accesible sin JavaScript: EL/JSTL usa directamente producto.imagenUrl. -->
    <noscript>
        <section class="catalog-container">
            <div class="products-grid">
                <c:forEach var="producto" items="${productosCatalogo}">
                    <article class="product-card">
                        <img src="${pageContext.request.contextPath}/${producto.imagenUrl}"
                             alt="${producto.nombreProd}"
                             onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/Vista/img/default.jpg';">
                        <h3>${producto.nombreProd}</h3>
                        <p>${producto.descripcionProd}</p>
                    </article>
                </c:forEach>
            </div>
        </section>
    </noscript>

    <!-- PRODUCTOS DESDE MYSQL: se envían al frontend para render dinámico -->
    <script>
        window.products = [
        <% 
            if (productosCatalogo != null) {
                for (int i = 0; i < productosCatalogo.size(); i++) {
                    Modelo.Producto p = productosCatalogo.get(i);
                    String nombre = p.getNombreProd() == null ? "" : p.getNombreProd();
                    String desc = p.getDescripcionProd() == null ? "" : p.getDescripcionProd();
                    String img = p.getImagenUrl();
                    String categoria = p.getCategoriaIdCategoria() == 1 ? "cuidado-facial"
                            : (p.getCategoriaIdCategoria() == 2 ? "maquillaje"
                            : (p.getCategoriaIdCategoria() == 11 ? "brochas"
                            : (p.getCategoriaIdCategoria() == 13 ? "novedades" : "otros")));

                    // Escapado seguro para insertar valores de MySQL dentro de una cadena JS.
                    nombre = nombre.replace("\\", "\\\\").replace("\"", "\\\"")
                            .replace("\r", " ").replace("\n", " ");
                    desc = desc.replace("\\", "\\\\").replace("\"", "\\\"")
                            .replace("\r", " ").replace("\n", " ");
                    img = img.replace("\\", "\\\\").replace("\"", "\\\"")
                            .replace("\r", " ").replace("\n", " ");
        %>
            {id:<%= p.getIdProducto() %>,nombre:"<%= nombre %>",categoria:"<%= categoria %>",categoriaIdCategoria:<%= p.getCategoriaIdCategoria() %>,precio:<%= p.getPrecio() %>,stock:<%= p.getStock() %>,imagen:"<%= img %>",descripcion:"<%= desc %>",badge:"Producto"}<%= i < productosCatalogo.size() - 1 ? "," : "" %>
        <%      }
            }
        %>
        ];
    </script>
    <script src="${pageContext.request.contextPath}/Vista/js/beauty-boost.js"></script>
</body>
</html>