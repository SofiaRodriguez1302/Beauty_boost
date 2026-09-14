<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Beauty Boost - Iniciar Sesión</title>
    <!-- Rutas dinámicas para CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Vista/css/style.css">
    
</head>
<body>

    <header class="custom-header">
        <div class="header-top-row">
            <a href="${pageContext.request.contextPath}/ProductoServlet" class="btn-back-circle" title="Volver">←</a>
            <a href="${pageContext.request.contextPath}/ProductoServlet" class="header-brand">
                <span class="header-logo-circle"><img src="${pageContext.request.contextPath}/img/logo.png" alt="Logo Beauty Boost"></span>
                <span class="app-name-oval"><h1>BEAUTY BOOST</h1></span>
            </a>
            <div class="bb-inline-98815b2359"></div>
        </div>
    </header>

    <!-- CONTENEDOR PRINCIPAL DE LOGIN -->
    <main class="login-container-main">

        <!-- LADO IZQUIERDO: MENSAJE BIENVENIDA Y FRASE DE LA MARCA -->
        <div class="info-card-left">
            <div>
                <span class="welcome-pill">✦ Bienvenida de nuevo</span>
                <h2>Realza tu belleza única con cada detalle</h2>
                <p class="desc">Inicia sesión para acceder a tus favoritos guardados, realizar compras rápidas y descubrir colecciones diseñadas exclusivamente para ti.</p>
            </div>
            <div>
                <div class="feature-box-item">
                    <strong>Tu espacio de cuidado</strong>
                    <span>Gestiona tus pedidos y lista de deseos al instante.</span>
                </div>
            </div>
        </div>

        <!-- LADO DERECHO: FORMULARIO DE ACCESO -->
        <div class="form-card-right">
            <h2 class="bb-inline-f5feb949cf">Iniciar Sesión</h2>
            <p class="bb-inline-5a8d0988e5">Ingresa tus credenciales de acceso</p>

            <!-- MOSTRAR MENSAJE DE ERROR SI EXISTE -->
            <%
                String successMsg = (String) request.getAttribute("success");
                String errorMsg = (String) request.getAttribute("error");
                if (successMsg != null) {
            %>
                <div class="alert-success login-feedback-success">
                    <%= successMsg %>
                </div>
            <%
                } else if (errorMsg != null) {
            %>
                <div class="bb-inline-5c343ae86c login-feedback-error">
                    <%= errorMsg %>
                </div>
            <%
                }
            %>

            <!-- Formulario apuntando hacia LoginServlet -->
            <form action="${pageContext.request.contextPath}/LoginServlet" method="POST">
                <div class="form-group bb-inline-4a3180e2eb">
                    <label for="txtCorreo" class="bb-inline-4e49a119e8">Gmail</label>
                    <input type="email" id="txtCorreo" name="txtCorreo" placeholder="usuario@gmail.com" required class="form-control bb-inline-74e65c20c7">
                </div>
                <div class="form-group bb-inline-4a3180e2eb">
                    <label for="txtPassword" class="bb-inline-4e49a119e8">Contraseña</label>
                    <div class="password-field">
                        <input type="password" id="txtPassword" name="txtPassword" placeholder="••••••••" required class="form-control bb-inline-74e65c20c7">
                        <button type="button" class="password-toggle" data-toggle-password="txtPassword" aria-label="Mostrar contraseña">👁</button>
                    </div>
                </div>
                
                <div class="bb-inline-b75fad0009">
                    <a href="${pageContext.request.contextPath}/RecuperarPasswordServlet" class="forgot-password-link bb-inline-9ebf19e097">¿Olvidaste tu contraseña?</a>
                </div>
                
                <button type="submit" class="btn-primary bb-inline-9a7ae80aab">ENTRAR</button>

                <!-- APARTADO PARA REGISTRARSE -->
                <p class="bb-inline-a2fff56195">
                    ¿No tienes una cuenta? 
                    <a href="${pageContext.request.contextPath}/Vista/Registro.jsp" class="bb-inline-53b6088095">Regístrate aquí</a>
                </p>
            </form>
        </div>

    </main>

    <script src="${pageContext.request.contextPath}/Vista/js/beauty-boost.js"></script>
</body>
</html>