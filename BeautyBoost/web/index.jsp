<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Beauty Boost</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Vista/css/style.css">
</head>
<body class="portada-page">
    <main class="welcome-hero" aria-label="Bienvenida a Beauty Boost">
        <div class="welcome-carousel" id="welcomeCarousel" aria-label="Carrusel de imágenes de Beauty Boost">
            <div class="welcome-slide" style="background-image:url('${pageContext.request.contextPath}/Vista/img/maquillaje0.png');" aria-hidden="true"></div>
            <div class="welcome-slide" style="background-image:url('${pageContext.request.contextPath}/Vista/img/maquillaje1.png');" aria-hidden="true"></div>
            <div class="welcome-slide" style="background-image:url('${pageContext.request.contextPath}/Vista/img/maquillaje3.png');" aria-hidden="true"></div>
        </div>
        <div class="welcome-overlay"></div>

        <section class="welcome-content">
            <div class="welcome-brand">
                <div class="welcome-logo">
                    <img src="${pageContext.request.contextPath}/Vista/img/logo.png" alt="Logo Beauty Boost">
                </div>
                <h1>BEAUTY BOOST</h1>
                <p>Realza tu brillo <span>natural</span></p>
            </div>

            <a href="${pageContext.request.contextPath}/ProductoServlet" class="portada-explore-btn" aria-label="Explorar tienda">
                <span>EXPLORAR TIENDA</span>
                <span class="arrow" aria-hidden="true">→</span>
            </a>
            <div class="welcome-indicators" id="welcomeIndicators" aria-label="Indicadores de portada">
                <button type="button" class="welcome-indicator active" data-slide="0" aria-label="Ver portada 1" aria-current="true"></button>
                <button type="button" class="welcome-indicator" data-slide="1" aria-label="Ver portada 2"></button>
                <button type="button" class="welcome-indicator" data-slide="2" aria-label="Ver portada 3"></button>
                <button type="button" class="welcome-indicator" data-slide="3" aria-label="Ver portada 4"></button>
            </div>
        </section>
    </main>

    <script src="${pageContext.request.contextPath}/Vista/js/portada-carousel.js"></script>
</body>
</html>
