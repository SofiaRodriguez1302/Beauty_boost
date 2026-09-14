<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    boolean usuarioLogueado = (session.getAttribute("usuarioLogueado") != null || session.getAttribute("usuario") != null || session.getAttribute("user") != null);
    
    // Obtenemos el correo del usuario logueado para aislar el carrito de forma única en localStorage
    String correoLogin = "";
    String nombreLogin = "";
    if (session.getAttribute("usuarioLogueado") != null) {
        Object obj = session.getAttribute("usuarioLogueado");
        if (obj instanceof Modelo.Usuario) {
            correoLogin = ((Modelo.Usuario) obj).getCorreo();
            nombreLogin = ((Modelo.Usuario) obj).getNombre();
        } else {
            correoLogin = obj.toString();
        }
    } else if (session.getAttribute("usuario") != null) {
        Object obj = session.getAttribute("usuario");
        if (obj instanceof Modelo.Usuario) {
            correoLogin = ((Modelo.Usuario) obj).getCorreo();
            nombreLogin = ((Modelo.Usuario) obj).getNombre();
        } else {
            correoLogin = obj.toString();
        }
    }
    if (correoLogin == null) correoLogin = "";
    correoLogin = correoLogin.trim();
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Beauty Boost - Carrito</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Vista/css/style.css">
</head>
<body class="catalog-page" data-context-path="${pageContext.request.contextPath}" data-user-email="<%= correoLogin %>" data-logged-in="<%= usuarioLogueado %>" data-favorite-ids="<%= session.getAttribute("favoritos") != null ? session.getAttribute("favoritos") : "[]" %>">

    <!-- HEADER SUPERIOR ESTRUCTURADO -->
    <header class="custom-header">
        <div class="header-top-row">
            <div class="header-logo-circle">
                <img src="${pageContext.request.contextPath}/img/logo.png" alt="Logo Beauty Boost">
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
            <a href="${pageContext.request.contextPath}/ProductoServlet" class="navbar-link">Todo</a>
            <a href="${pageContext.request.contextPath}/ProductoServlet" class="navbar-link">Maquillaje</a>
            <a href="${pageContext.request.contextPath}/ProductoServlet" class="navbar-link">Cuidado Facial</a>
            <a href="${pageContext.request.contextPath}/ProductoServlet" class="navbar-link">Brochas</a>
            <a href="${pageContext.request.contextPath}/ProductoServlet" class="navbar-link">Novedades</a>
            <a href="${pageContext.request.contextPath}/ProductoServlet" class="navbar-link">Más Vendidos</a>
        </div>
        <div class="navbar-right">
            <% if (usuarioLogueado) { %><a href="${pageContext.request.contextPath}/PerfilServlet" class="header-user navbar-user-name" title="Mi Perfil"><span class="header-user-icon">👤</span><strong><%= nombreLogin %></strong></a><% } else { %><a href="${pageContext.request.contextPath}/LoginServlet" class="header-user navbar-user-name login-header-btn">Iniciar Sesión</a><% } %>
            <a href="${pageContext.request.contextPath}/ProductoServlet?action=carrito" title="Carrito" class="navbar-icon-btn bb-inline-7326222bcb">🛒</a>
        </div>
    </nav>

    <!-- CONTENEDOR PRINCIPAL DEL CARRITO -->
    <main class="catalog-container bb-inline-e9be306f00 cart-page-main">
        <div class="bb-inline-b75fad0009">
            <a href="${pageContext.request.contextPath}/ProductoServlet" class="bb-inline-92c99a7642">← Volver a productos</a>
            <h2 class="bb-inline-b7b1371297 cart-page-title">Tu Carrito</h2>
        </div>

        <div class="bb-inline-bae9fbbcc8 cart-layout">
            
            <!-- Lado Izquierdo: Contenedor Dinámico de Productos en el Carrito -->
            <div id="cartItemsContainer" class="bb-inline-ec61f20d00 cart-panel">
                <!-- Se llena dinámicamente con JavaScript -->
            </div>

            <!-- Lado Derecho: Resumen de Compra -->
            <div class="bb-inline-191fec1f5f cart-summary">
                <h3 class="bb-inline-5f296d6e42">Resumen</h3>
                
                <div class="bb-inline-e14b6b5f7e">
                    <span>Subtotal</span>
                    <span id="resumenSubtotal">$0</span>
                </div>
                <div class="bb-inline-e14b6b5f7e">
                    <span>Envío</span>
                    <span>$0</span>
                </div>
                <div class="bb-inline-49ae9b662d">
                    <span>Impuestos</span>
                    <span>$0</span>
                </div>
                
                <div class="bb-inline-93e3863627">
                    <span>Total</span>
                    <span id="resumenTotal" class="bb-inline-7326222bcb">$0</span>
                </div>

                <button data-action="pay" class="bb-inline-e58af5b106">IR A PAGAR</button>
            </div>

        </div>
    </main>

    <!-- SCRIPT DE RENDERIZADO Y GESTIÓN DEL CARRITO -->
    

    
    <script src="${pageContext.request.contextPath}/Vista/js/beauty-boost.js"></script>
</body>
</html>