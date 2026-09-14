<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Beauty Boost - Crear Cuenta</title>
    <!-- CSS Principal -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Vista/css/style.css">
    
</head>
<body>

    <!-- HEADER CON BOTÓN VOLVER -->
    <header class="custom-header">
        <div class="header-top-row">
            <a href="${pageContext.request.contextPath}/ProductoServlet" class="btn-back-circle" title="Volver">←</a>
            <div class="app-name-oval">
                <h1>BEAUTY BOOST</h1>
            </div>
            <div class="bb-inline-98815b2359"></div>
        </div>
    </header>

    <!-- CONTENEDOR DE REGISTRO -->
    <main class="register-container-main">

        <!-- LADO IZQUIERDO: INFORMACIÓN DE BIENVENIDA -->
        <div class="info-card-left">
            <div>
                <span class="welcome-pill">Bienvenida a Beauty Boost</span>
                <h2>Regístrate y descubre un mundo de belleza personalizada</h2>
                <p class="desc">Accede a recomendaciones especiales, ofertas exclusivas y seguimiento fácil de tus pedidos.</p>
            </div>
            <div>
                <div class="feature-box-item">
                    <strong>+50 marcas</strong>
                    <span>Productos seleccionados</span>
                </div>
                <div class="feature-box-item">
                    <strong>Envío rápido</strong>
                    <span>Compras sin complicaciones</span>
                </div>
            </div>
        </div>

        <!-- LADO DERECHO: FORMULARIO SEGÚN BASE DE DATOS -->
        <div class="form-card-right">
            <h2>Crear cuenta</h2>
            <p class="sub">Completa tus datos para empezar a comprar.</p>

            <!-- MENSAJE DE ERROR O ÉXITO SI EXISTE -->
            <%
                String errorMsg = (String) request.getAttribute("error");
                if (errorMsg != null) {
            %>
                <div class="bb-inline-5c343ae86c">
                    <%= errorMsg %>
                </div>
            <%
                }
            %>

            <!-- FORMULARIO APUNTANDO AL NUEVO REGISTRO SERVLET -->
            <form action="${pageContext.request.contextPath}/RegistroServlet" method="POST">
                
                <!-- NOMBRES Y APELLIDOS -->
                <div class="form-row-double">
                    <div class="form-group-custom">
                        <label for="txtNombre">Nombres</label>
                        <input type="text" id="txtNombre" name="txtNombre" class="form-control-custom" placeholder="Ingresa tus nombres" required>
                    </div>
                    <div class="form-group-custom">
                        <label for="txtApellido">Apellidos</label>
                        <input type="text" id="txtApellido" name="txtApellido" class="form-control-custom" placeholder="Ingresa tus apellidos" required>
                    </div>
                </div>

                <!-- TIPO Y NÚMERO DE DOCUMENTO -->
                <div class="form-row-double">
                    <div class="form-group-custom">
                        <label for="cboTipoDoc">Tipo de Documento</label>
                        <select id="cboTipoDoc" name="cboTipoDoc" class="form-control-custom" required>
                            <option value="1">Cédula de Ciudadanía</option>
                            <option value="2">Cédula de Extranjería</option>
                            <option value="3">Pasaporte</option>
                            <option value="5">Tarjeta de Identidad</option>
                        </select>
                    </div>
                    <div class="form-group-custom">
                        <label for="txtIdentificacion">Número de Documento</label>
                        <input type="text" id="txtIdentificacion" name="txtIdentificacion" class="form-control-custom" placeholder="Ej. 1098765432" inputmode="numeric" maxlength="20" required>
                        <small class="field-error" id="documentoError"></small>
                    </div>
                </div>

                <!-- TELÉFONO Y FECHA DE NACIMIENTO -->
                <div class="form-row-double">
                    <div class="form-group-custom">
                        <label for="txtTelefono">Teléfono</label>
                        <input type="tel" id="txtTelefono" name="txtTelefono" class="form-control-custom" placeholder="Ej. 3158889900" inputmode="numeric" maxlength="20" required>
                        <small class="field-error" id="telefonoError"></small>
                    </div>
                    <div class="form-group-custom">
                        <label for="txtFechaNacimiento">Fecha de Nacimiento</label>
                        <input type="text" id="txtFechaNacimientoDisplay" name="txtFechaNacimientoDisplay" class="form-control-custom" placeholder="DD/MM/AAAA" inputmode="numeric" maxlength="10" autocomplete="bday" required>
                        <input type="hidden" id="txtFechaNacimiento" name="txtFechaNacimiento">
                        <small class="field-hint">Formato: DD/MM/AAAA</small>
                        <small class="field-error" id="fechaError"></small>
                    </div>
                </div>

                <!-- CORREO ELECTRÓNICO -->
                <div class="form-group-custom">
                    <label for="txtCorreo">Correo electrónico</label>
                    <input type="email" id="txtCorreo" name="txtCorreo" class="form-control-custom" placeholder="ejemplo@gmail.com" required>
                </div>

                <!-- CONTRASEÑA -->
                <div class="form-group-custom">
                    <label for="txtClave">Contraseña</label>
                    <div class="password-field">
                        <input type="password" id="txtClave" name="txtClave" class="form-control-custom" placeholder="Mínimo 8 caracteres" minlength="8" required>
                        <button type="button" class="password-toggle" data-toggle-password="txtClave" aria-label="Mostrar contraseña">👁</button>
                    </div>
                </div>

                <!-- AUTORIZACIÓN DE TRATAMIENTO DE DATOS -->
                <div class="form-group-custom">
                    <label class="checkbox-label-custom">
                        <input type="checkbox" name="chkAutorizacion" value="SI" required checked>
                        <span>Acepto la autorización y tratamiento de datos personales.</span>
                    </label>
                </div>

                <button type="submit" class="btn-submit-account">CREAR CUENTA</button>
            </form>

            <p class="login-redirect-text">
                ¿Ya tienes cuenta? <a href="${pageContext.request.contextPath}/Vista/Login.jsp">Inicia sesión</a>
            </p>
        </div>

    </main>

    <script src="${pageContext.request.contextPath}/Vista/js/beauty-boost.js"></script>
</body>
</html>