<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Beauty Boost - Recuperar Contraseña</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Vista/css/style.css">
    
</head>
<body>

    <header class="custom-header">
        <div class="header-top-row">
            <a href="${pageContext.request.contextPath}/Vista/Login.jsp" class="btn-back-circle" title="Volver">←</a>
            <div class="app-name-oval"><h1>BEAUTY BOOST</h1></div>
            <div class="bb-inline-98815b2359"></div>
        </div>
    </header>

    <main class="bb-inline-279593b0c6">
        <div class="bb-inline-025cddde60">
            <h2 class="bb-inline-827522e558">Recuperar Contraseña</h2>
            
            <% 
                String mensaje = (String) request.getAttribute("mensaje");
                String error = (String) request.getAttribute("error");
                
                if (mensaje != null) { 
            %>
                <div class="bb-inline-e3a7d52ff8">
                    <%= mensaje %>
                </div>
                
                <!-- Botón directo para ir a Iniciar Sesión una vez enviado el correo -->
                <a href="${pageContext.request.contextPath}/Vista/Login.jsp" class="bb-inline-bdb265b4bb">IR A INICIAR SESIÓN</a>
                <a href="${pageContext.request.contextPath}/RecuperarPasswordServlet" class="bb-inline-4d1f5394cd">Intentar con otro correo</a>

            <% } else { %>
                <p class="bb-inline-4e2e10c6b3">Ingresa tu correo electrónico registrado y te enviaremos tu contraseña actual a Gmail.</p>

                <% if (error != null) { %>
                    <div class="bb-inline-70bb49a67d"><%= error %></div>
                <% } %>

                <form action="${pageContext.request.contextPath}/RecuperarPasswordServlet" method="POST">
                    <div class="bb-inline-4a3180e2eb">
                        <label class="bb-inline-4e49a119e8">Correo Electrónico</label>
                        <input type="email" name="txtCorreo" required placeholder="usuario@gmail.com" class="bb-inline-74e65c20c7">
                    </div>
                    <button type="submit" class="bb-inline-c07a41dd69">Enviar Instrucciones</button>
                </form>
            <% } %>

        </div>
    </main>

</body>
</html>