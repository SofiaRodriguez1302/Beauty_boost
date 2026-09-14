<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    boolean usuarioLogueado = false;
    String correoLogin = "";
    String nombreLogin = "";
    
    if (session != null) {
        if (session.getAttribute("usuarioLogueado") != null) {
            usuarioLogueado = true;
            Object obj = session.getAttribute("usuarioLogueado");
            if (obj instanceof Modelo.Usuario) {
                correoLogin = ((Modelo.Usuario) obj).getCorreo();
            nombreLogin = ((Modelo.Usuario) obj).getNombre();
            } else {
                correoLogin = obj.toString();
            }
        } else if (session.getAttribute("usuario") != null) {
            usuarioLogueado = true;
            Object obj = session.getAttribute("usuario");
            if (obj instanceof Modelo.Usuario) {
                correoLogin = ((Modelo.Usuario) obj).getCorreo();
            nombreLogin = ((Modelo.Usuario) obj).getNombre();
            } else {
                correoLogin = obj.toString();
                nombreLogin = correoLogin;
            }
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
                <img src="${pageContext.request.contextPath}/Vista/img/logo.png" alt="Logo Beauty Boost">
            </div>
            <div class="app-name-oval">
                <h1>BEAUTY BOOST</h1>
            </div>
            <div class="header-right-space"><% if (usuarioLogueado) { %><a href="${pageContext.request.contextPath}/PerfilServlet" class="header-user navbar-user-name" title="Mi Perfil"><span class="header-user-icon">👤</span><strong><%= nombreLogin %></strong></a><% } else { %><a href="${pageContext.request.contextPath}/LoginServlet" class="header-user navbar-user-name login-header-btn">Iniciar Sesión</a><% } %></div>
        </div>
    </header>
<!-- CONTENEDOR PRINCIPAL DEL CARRITO -->
    <main class="bb-inline-f4f863494f">
        <div class="bb-inline-b75fad0009">
            <a href="${pageContext.request.contextPath}/ProductoServlet" class="bb-inline-df415f83eb">← Volver a productos</a>
            <h2 class="bb-inline-70aaa1ca3d">Tu Carrito</h2>
        </div>

        <div class="bb-inline-a5e3b247fb cart-layout">
            <div id="cartItemsContainer" class="bb-inline-e32c74714e cart-panel">
                <!-- Se llena con JS -->
            </div>

            <div class="bb-inline-9ea1a3343b cart-summary">
                <h3 class="bb-inline-2a6d2c2b88">Resumen</h3>
                <div class="bb-inline-b35718157e">
                    <span>Subtotal</span><span id="lblSubtotal">$0</span>
                </div>
                <div class="bb-inline-b35718157e">
                    <span>Envío</span><span id="lblEnvio">$0</span>
                </div>
                <div class="bb-inline-8c6fa8a40b">
                    <span>Impuestos (5%)</span><span id="lblImpuestos">$0</span>
                </div>
                <div class="bb-inline-f6be4a42b0">
                    <span>Total</span><span id="lblTotal" class="bb-inline-7326222bcb">$0</span>
                </div>
                <button id="btnPagar" data-action="go-checkout" class="bb-inline-41ff157c1d">IR A PAGAR</button>
            </div>
        </div>
    </main>

    
    <script src="${pageContext.request.contextPath}/Vista/js/beauty-boost.js"></script>
</body>
</html>