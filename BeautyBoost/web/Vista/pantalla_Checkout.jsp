<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Modelo.Usuario" %>
<%
    boolean usuarioLogueado = (session.getAttribute("usuarioLogueado") != null);
    Usuario user = (Usuario) session.getAttribute("usuarioLogueado");
    
    if (!usuarioLogueado || user == null) {
        response.sendRedirect(request.getContextPath() + "/Vista/Login.jsp");
        return;
    }
    
    String nombreCompleto = user.getNombre() + " " + user.getApellido();
    String correoUsuario = user.getCorreo();
    String telefonoUsuario = user.getTelefono() != null ? user.getTelefono() : "";
    String direccionCheckout = "";
    try {
        Controlador.DireccionEnvioDAO direccionDAO = new Controlador.DireccionEnvioDAO();
        java.util.List<java.util.Map<String,Object>> direccionesCheckout = direccionDAO.listarDireccionesPorUsuario(user.getIdUsuario());
        if (direccionesCheckout != null && !direccionesCheckout.isEmpty()) {
            java.util.Map<String,Object> d = direccionesCheckout.get(0);
            direccionCheckout = (d.get("direccion") != null ? d.get("direccion").toString() : "");
            if (d.get("ciudad") != null && !d.get("ciudad").toString().isEmpty()) {
                direccionCheckout += (direccionCheckout.isEmpty() ? "" : " • ") + d.get("ciudad").toString();
            }
        }
    } catch (Exception ignored) {}
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Beauty Boost - Checkout</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Vista/css/style.css">
    
</head>
<body class="catalog-page bb-inline-b9dafe400b" data-context-path="${pageContext.request.contextPath}" data-user-email="<%= correoUsuario %>" data-logged-in="true" data-favorite-ids="<%= session.getAttribute("favoritos") != null ? session.getAttribute("favoritos") : "[]" %>">

    <!-- HEADER SUPERIOR -->
    <header class="custom-header no-print">
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

    <!-- NAVBAR HORIZONTAL -->
    <nav class="horizontal-navbar no-print">
        <div class="navbar-categories">
            <a href="${pageContext.request.contextPath}/ProductoServlet" class="navbar-link">Todo</a>
            <a href="${pageContext.request.contextPath}/ProductoServlet" class="navbar-link">Maquillaje</a>
            <a href="${pageContext.request.contextPath}/ProductoServlet" class="navbar-link">Cuidado Facial</a>
            <a href="${pageContext.request.contextPath}/ProductoServlet" class="navbar-link">Brochas</a>
            <a href="${pageContext.request.contextPath}/ProductoServlet" class="navbar-link">Novedades</a>
            <a href="${pageContext.request.contextPath}/ProductoServlet" class="navbar-link">Más Vendidos</a>
        </div>
        <div class="navbar-right">
            <a href="${pageContext.request.contextPath}/PerfilServlet" class="header-user navbar-user-name" title="Mi Perfil"><span class="header-user-icon">👤</span><strong><%= nombreCompleto %></strong></a>
            <a href="${pageContext.request.contextPath}/ProductoServlet?action=carrito" class="navbar-icon-btn" title="Carrito">🛒</a>
        </div>
    </nav>

    <!-- BARRA DE PASOS -->
    <div class="no-print bb-inline-b848126ea1">
        <div class="step-pill active" id="pillStep1">
            <span class="step-number" id="numStep1">1</span>
            <span>PASO 01<br><small class="bb-inline-1c653fba3d">Entrega y Envío</small></span>
        </div>
        <div class="bb-inline-bbd405d31a"></div>
        <div class="step-pill" id="pillStep2">
            <span class="step-number" id="numStep2">2</span>
            <span>PASO 02<br><small class="bb-inline-1c653fba3d">Método de Pago</small></span>
        </div>
        <div class="bb-inline-bbd405d31a"></div>
        <div class="step-pill" id="pillStep3">
            <span class="step-number" id="numStep3">3</span>
            <span>PASO 03<br><small class="bb-inline-1c653fba3d">Confirmación</small></span>
        </div>
    </div>

    <!-- CONTENEDOR PRINCIPAL CHECKOUT -->
    <main class="bb-inline-ec1ab214c8">
        
        <div class="bb-inline-b3e356fdb1 checkout-layout" id="checkoutMainLayout">
            
            <!-- COLUMNA IZQUIERDA -->
            <div id="leftStepContent">
                
                <!-- PASO 1: DATOS DE ENTREGA Y ENVÍO -->
                <div id="viewStep1">
                    <span class="bb-inline-41ad1eece1">● PROCESO DE COMPRA</span>
                    <h2 class="bb-inline-73f8bfad52">1. Datos de Entrega y Envío</h2>
                    <p class="bb-inline-f712c36805">Selecciona tu dirección de residencia y escoge el método de despacho ideal para tus cosméticos.</p>

                    <!-- DIRECCIÓN SELECCIONADA -->
                    <div class="bb-inline-526d6b41d7">
                        <div class="bb-inline-58ce971b91">
                            <strong class="bb-inline-0a46e2239b">📍 Dirección Seleccionada</strong>
                            <span class="bb-inline-665942c11a">PRINCIPAL / PREDETERMINADA</span>
                        </div>
                        <div class="bb-inline-1f697f12a9">
                            <div class="bb-inline-6d94a70f67">
                                <strong class="bb-inline-0a46e2239b"><%= nombreCompleto %></strong>
                                <span class="bb-inline-ccdca83d16">📞 +57 <%= telefonoUsuario %></span>
                            </div>
                            <div class="checkout-address-field">
                            <label for="txtDireccionEntrega">Dirección de entrega</label>
                            <input type="text" id="txtDireccionEntrega" class="form-control checkout-address-input"
                                   value="<%= direccionCheckout.replace("\"", "&quot;") %>"
                                   placeholder="Escribe tu dirección de entrega" required>
                            <small>Si tienes una dirección registrada, aparecerá aquí; puedes modificarla para este pedido.</small>
                        </div>
                        </div>
                        <div class="bb-inline-ea1a40af8c">
                            <a href="${pageContext.request.contextPath}/PerfilServlet" class="bb-inline-009e8a36cd">⚙ Cambiar o agregar nueva dirección</a>
                            <span class="bb-inline-89dd320454">✓ Verificada</span>
                        </div>
                    </div>

                    <!-- TIPO DE ENVÍO -->
                    <h3 class="bb-inline-9de1a8b255">Tipo de Envío <small class="bb-inline-ba8c2baa8e">Empaquetado biodegradable incluido</small></h3>
                    
                    <div class="shipping-option selected" id="optEstandar" data-action="shipping" data-type="estandar" data-cost="0">
                        <div class="bb-inline-e2ff0fd1a7">
                            <input type="radio" name="tipoEnvioRadio" id="radioEstandar" checked class="bb-inline-8ccca6bb58">
                            <div>
                                <strong class="bb-inline-727910aee5">Envío Estándar Nacional</strong> <span class="bb-inline-0849560822">2-4 días hábiles</span>
                                <p class="bb-inline-d9f0700df5">Coordinado con Servientrega & Coordinadora logística certificada. Despacho garantizado.</p>
                            </div>
                        </div>
                        <div class="bb-inline-13cbe03b9a">
                            <strong class="bb-inline-1871afe863">GRATIS</strong>
                            <div class="bb-inline-a9544daaf8">$14.500</div>
                        </div>
                    </div>

                    <div class="shipping-option" id="optExpress" data-action="shipping" data-type="express" data-cost="12000">
                        <div class="bb-inline-e2ff0fd1a7">
                            <input type="radio" name="tipoEnvioRadio" id="radioExpress" class="bb-inline-8ccca6bb58">
                            <div>
                                <strong class="bb-inline-727910aee5">Envío Express Mismo Día (Bogotá)</strong> <span class="bb-inline-1eb9cbddfe">Hoy Mismo</span>
                                <p class="bb-inline-d9f0700df5">Pedidos realizados antes de las 2:00 p.m. entregados hoy mismo en moto ecológica.</p>
                            </div>
                        </div>
                        <div class="bb-inline-13cbe03b9a">
                            <strong class="bb-inline-136a6a3211">$12.000</strong>
                            <div class="bb-inline-73ec7503bd">Urbano</div>
                        </div>
                    </div>

                    <!-- INSTRUCCIONES REPARTIDOR -->
                    <div class="bb-inline-20ed7253c5">
                        <label class="bb-inline-a273cfa7a2">Instrucciones para el Repartidor <small class="bb-inline-3145763e3c">(Opcional)</small></label>
                        <textarea placeholder="Ej: Dejar en portería o timbre 402, llamar antes de llegar..." class="bb-inline-972603d11b"></textarea>
                    </div>

                    <div class="bb-inline-c2bcea0858">
                        <a href="${pageContext.request.contextPath}/ProductoServlet?action=carrito" class="bb-inline-749f745a4c">← Volver al carrito</a>
                        <button type="button" data-action="step2" class="btn-primary-brown">Continuar al Pago →</button>
                    </div>
                </div>

                <!-- PASO 2: MÉTODO DE PAGO -->
                <div id="viewStep2" class="bb-inline-f2892a2e8a">
                    
                    <div class="bb-inline-ca6d40e21e">
                        <div>
                            <span class="bb-inline-2b0cd266f4">Entregar a:</span> <strong><%= nombreCompleto %></strong> <span class="bb-inline-7df03542c6" id="lblTipoEnvioSeleccionado">Envío Estándar</span>
                            <div class="bb-inline-94511f8cb9" id="step2Direccion">—</div>
                        </div>
                        <a href="javascript:void(0)" data-action="step1" class="bb-inline-d79b199654">Editar</a>
                    </div>

                    <span class="bb-inline-41ad1eece1">● PASO SEGURO</span>
                    <h2 class="bb-inline-3443cb4db9">2. Método de Pago</h2>

                    <!-- PESTAÑAS DE PAGO -->
                    <div class="bb-inline-5a88a34a54">
                        <div class="payment-tab selected" id="tabTarjeta" data-action="payment-tab" data-tab="tarjeta">
                            💳 Tarjeta Crédito/Débito<br><small class="bb-inline-5ae340470e">Visa, Master, Amex</small>
                        </div>
                        <div class="payment-tab" id="tabPse" data-action="payment-tab" data-tab="pse">
                            🏦 PSE<br><small class="bb-inline-5ae340470e">Transferencia bancaria</small>
                        </div>
                        <div class="payment-tab" id="tabNequi" data-action="payment-tab" data-tab="nequi">
                            📱 Nequi<br><small class="bb-inline-5ae340470e">Pago móvil</small>
                        </div>
                        <div class="payment-tab" id="tabDaviplata" data-action="payment-tab" data-tab="daviplata">
                            📲 Daviplata<br><small class="bb-inline-5ae340470e">Pago móvil</small>
                        </div>
                        <div class="payment-tab" id="tabContraentrega" data-action="payment-tab" data-tab="contraentrega">
                            📦 Contra Entrega<br><small class="bb-inline-5ae340470e">Paga al recibir en casa</small>
                        </div>
                    </div>

                    <!-- FORMULARIO TARJETA CON SELECTOR DE FECHA TIPO CALENDARIO -->
                    <div id="formTarjeta" class="bb-inline-6b5cfd21c9">
                        <div>
                            <label class="bb-inline-d6fdea1487">Nombre del titular como aparece en el plástico</label>
                            <input type="text" id="txtNombreTitular" placeholder="Ej. Juan Pérez" class="bb-inline-a94e9d8edd">
                        </div>
                        <div>
                            <label class="bb-inline-d6fdea1487">Número de Tarjeta</label>
                            <input type="text" id="txtNumeroTarjeta" placeholder="4242 •••• •••• 4242" maxlength="19" class="bb-inline-a94e9d8edd">
                        </div>
                        <div class="bb-inline-e3ad0a09ee">
                            <div>
                                <label class="bb-inline-d6fdea1487">Fecha de Vencimiento</label>
                                <input type="date" id="txtFechaVencimiento" class="bb-inline-78cb83f71e">
                            </div>
                            <div>
                                <label class="bb-inline-d6fdea1487">Código CVV / CVC</label>
                                <input type="password" id="txtCvv" placeholder="•••" maxlength="4" class="bb-inline-a94e9d8edd">
                            </div>
                        </div>
                        <div>
                            <label class="bb-inline-d6fdea1487">Número de Cuotas / Modalidad de Pago</label>
                            <select id="selectCuotas" class="bb-inline-e12b7ae022">
                                <option value="pago_unico">Pago único completo (De contado / Sin cuotas)</option>
                                <option value="1_cuota">1 cuota (Sin interés)</option>
                                <option value="3_cuotas">3 cuotas</option>
                                <option value="6_cuotas">6 cuotas</option>
                                <option value="12_cuotas">12 cuotas</option>
                            </select>
                        </div>
                        <label class="bb-inline-355153d023">
                            <input type="checkbox" checked class="bb-inline-8ccca6bb58"> Guardar esta tarjeta de forma 100% segura para agilizar mis compras en Beauty Boost.
                        </label>
                    </div>

                    <!-- FORMULARIO PSE -->
                    <div id="formPse" class="payment-form-extra" hidden>
                        <div>
                            <label>Banco</label>
                            <select id="pseBanco" class="form-control">
                                <option value="">Selecciona tu banco</option>
                                <option>Bancolombia</option><option>Banco de Bogotá</option><option>Davivienda</option>
                                <option>BBVA</option><option>Banco de Occidente</option><option>Otro</option>
                            </select>
                        </div>
                        <div>
                            <label>Tipo de persona</label>
                            <select id="pseTipoPersona" class="form-control">
                                <option value="natural">Persona natural</option>
                                <option value="juridica">Persona jurídica</option>
                            </select>
                        </div>
                        <div>
                            <label>Correo para PSE</label>
                            <input type="email" id="pseCorreo" class="form-control" value="<%= correoUsuario %>">
                        </div>
                    </div>

                    <!-- FORMULARIO NEQUI -->
                    <div id="formNequi" class="payment-form-extra" hidden>
                        <div>
                            <label>Número de celular Nequi</label>
                            <input type="tel" id="nequiTelefono" class="form-control" placeholder="Ej. 3001234567" maxlength="10" inputmode="numeric">
                        </div>
                    </div>

                    <!-- FORMULARIO DAVIPLATA -->
                    <div id="formDaviplata" class="payment-form-extra" hidden>
                        <div>
                            <label>Tipo de Documento</label>
                            <select id="daviplataTipoDocumento" class="form-control">
                                <option value="">Selecciona el tipo</option>
                                <option value="CC">Cédula de ciudadanía</option>
                                <option value="CE">Cédula de extranjería</option>
                                <option value="NIT">NIT</option>
                            </select>
                        </div>
                        <div>
                            <label>Número de Documento</label>
                            <input type="text" id="daviplataDocumento" class="form-control" placeholder="Número de documento" inputmode="numeric">
                        </div>
                        <div>
                            <label>Número de Celular DaviPlata</label>
                            <input type="tel" id="daviplataTelefono" class="form-control" placeholder="Ej. 3001234567" maxlength="10" inputmode="numeric">
                        </div>
                    </div>

                    <!-- FORMULARIO CONTRAENTREGA -->
                    <div id="formContraentrega" class="payment-form-extra" hidden>
                        <div>
                            <label>Nombre de quien realiza el pedido</label>
                            <input type="text" id="contraNombrePedido" class="form-control" value="<%= nombreCompleto %>">
                        </div>
                        <div>
                            <label>Número de documento</label>
                            <input type="text" id="contraDocumento" class="form-control" placeholder="Número de documento">
                        </div>
                        <div>
                            <label>Dirección</label>
                            <input type="text" id="contraDireccion" class="form-control" placeholder="Dirección de entrega">
                        </div>
                        <div>
                            <label>Teléfono</label>
                            <input type="tel" id="contraTelefono" class="form-control" value="<%= telefonoUsuario %>">
                        </div>
                        <div>
                            <label>Nombre de quien recibe</label>
                            <input type="text" id="contraRecibe" class="form-control" placeholder="Nombre de la persona que recibirá el pedido">
                        </div>
                        <div>
                            <label>Teléfono de quien recibe</label>
                            <input type="tel" id="contraRecibeTelefono" class="form-control" placeholder="Ej. 3001234567" maxlength="10" inputmode="numeric">
                        </div>
                    </div>

                    <!-- CONFIRMACIÓN PREVIA AL PAGO -->
                    <div id="paymentReview" class="payment-review" hidden>
                        <div class="payment-review-header">
                            <div>
                                <span class="review-kicker">CONFIRMACIÓN DE DATOS</span>
                                <h3>Revisa tu pedido antes de pagar</h3>
                            </div>
                            <button type="button" class="review-edit-btn" data-action="edit-payment">Editar</button>
                        </div>
                        <div class="payment-review-grid">
                            <div><span>Entrega</span><strong id="reviewDireccion">—</strong></div>
                            <div><span>Método de pago</span><strong id="reviewMetodo">—</strong></div>
                            <div><span>Datos del pago</span><strong id="reviewDatosPago">—</strong></div>
                            <div><span>Total</span><strong id="reviewTotal">$0</strong></div>
                        </div>
                        <button type="button" data-action="confirm-payment" class="btn-primary-brown review-confirm-btn">Confirmar datos y realizar pago</button>
                    </div>

                    <div class="bb-inline-515ab0523d">
                        <button type="button" data-action="step1" class="bb-inline-a56b789cdf">← Volver a Entrega</button>
                        <button type="button" data-action="review-payment" class="btn-primary-brown">Revisar y Confirmar Pago →</button>
                    </div>
                </div>

            </div>

            <!-- COLUMNA DERECHA: RESUMEN DE COMPRA -->
            <div class="bb-inline-619354f95e" id="rightSummaryContainer">
                <div class="bb-inline-611bde391e">
                    <h3 class="bb-inline-4b021c7b96">Resumen del pedido</h3>
                    <span class="bb-inline-1676291a28" id="lblNumArticulos">0 Artículos</span>
                </div>
                
                <!-- CUPÓN -->
                <div class="bb-inline-57bb7c030e">
                    <input type="text" placeholder="Código promo (Ej: BOOST10)" class="bb-inline-a727a16b70">
                    <button type="button" class="bb-inline-f4a715a3a2">Aplicar</button>
                </div>

                <!-- LISTA DE PRODUCTOS DEL CARRITO -->
                <div id="checkoutItemsList" class="bb-inline-0bc85677ca">
                </div>

                <div class="bb-inline-31ce3dd584">
                    <div class="bb-inline-6d94a70f67">
                        <span>Subtotal productos</span>
                        <span id="checkoutSubtotal">$0</span>
                    </div>
                    <div class="bb-inline-6d94a70f67">
                        <span>Costo de Envío</span>
                        <span id="checkoutCostEnvio" class="bb-inline-89dd320454">GRATIS</span>
                    </div>
                    <div class="bb-inline-3db2f2acce">
                        <span>Descuento aplicado</span>
                        <span>-$0</span>
                    </div>
                    <div class="bb-inline-d5dd45c057">
                        <span>Total Estimado</span>
                        <span id="checkoutTotal" class="bb-inline-09134fe590">$0</span>
                    </div>
                </div>

                <div class="bb-inline-8f7f113664">
                    <div>🛡️ Pago 100% Seguro cifrado SSL de 256-bit</div>
                    <div>🔄 Garantía de cambio sin costo por 30 días</div>
                    <div>🌿 Fórmulas limpias, veganas y cruelty-free certificadas</div>
                </div>
            </div>

        </div>

        <!-- PASO 3: CONFIRMACIÓN Y ESTADO DEL PEDIDO -->
        <div id="viewStep3" class="bb-inline-ef46da33f8">
            
            <!-- HEADER DE LOGO EXCLUSIVO PARA IMPRESIÓN -->
            <div class="print-logo-header bb-inline-f2892a2e8a">
                <div class="bb-inline-69cbc288a9">BEAUTY BOOST</div>
                <div class="bb-inline-5bdaf9a12b">COMPROBANTE OFICIAL DE COMPRA<br>www.beautyboost.com.co</div>
            </div>

            <div class="bb-inline-a32987e64e">
                <div class="no-print bb-inline-988d6c05c8">✓</div>
                <span class="bb-inline-b1623c55e0" id="lblOrdenConfirmadaNum">ORDEN CONFIRMADA #BB-89241</span>
                <h1 class="bb-inline-f286ab5e44">¡Gracias por tu compra, <%= user.getNombre() %>! ✨</h1>
                <p class="bb-inline-7e3ed307b5">Hemos enviado el recibo detallado y la guía de seguimiento a <strong><%= correoUsuario %></strong></p>
                <div class="bb-inline-40c4abd181">📅 <%= new java.text.SimpleDateFormat("dd 'de' MMMM, yyyy", new java.util.Locale("es", "ES")).format(new java.util.Date()) %> • <span id="confirmedShipping">Envío seleccionado</span></div>
            </div>

            <div class="bb-inline-9058467f70">
                
                <div class="bb-inline-d8152b1a39">
                    
                    <div class="bb-inline-ac8fdb44f5">
                        <div class="bb-inline-58ce971b91">
                            <strong class="bb-inline-0a46e2239b">📦 Estado del Pedido</strong>
                            <span class="bb-inline-3eec613cb9">EN TIEMPO</span>
                        </div>

                        <div class="bb-inline-f61b54c610">
                            <div class="bb-inline-ee3d55bf99">
                                <div class="bb-inline-97aac22e35">🛍️</div>
                                <strong class="bb-inline-ea7e39405d">Recibido</strong><br>Hoy
                            </div>
                            <div class="bb-inline-ee3d55bf99">
                                <div class="bb-inline-186dddd64a">📦</div>
                                <span>Preparación</span><br>Pendiente
                            </div>
                            <div class="bb-inline-ee3d55bf99">
                                <div class="bb-inline-186dddd64a">🚚</div>
                                <span>En camino</span><br>Est. 2 días
                            </div>
                            <div class="bb-inline-ee3d55bf99">
                                <div class="bb-inline-186dddd64a">🏡</div>
                                <span>Entregado</span><br>2-4 días
                            </div>
                        </div>
                    </div>

                    <div class="bb-inline-6428dcd24a">
                        <div class="bb-inline-388ce0d231">DESTINO DEL PAQUETE</div>
                        <h4 class="bb-inline-3d358a40c2">Dirección de Entrega</h4>
                        <p class="bb-inline-7658ace12c"><strong><%= nombreCompleto %></strong><br><span id="confirmedAddress">—</span></p>
                    </div>

                    <div class="bb-inline-6428dcd24a">
                        <div class="bb-inline-388ce0d231">TRANSACCIÓN BANCARIA</div>
                        <h4 class="bb-inline-3d358a40c2">Método de Pago</h4>
                        <div class="bb-inline-c2bcea0858">
                            <div class="bb-inline-5aff1d31f6">💳 <strong id="confirmedPaymentMethod">Método de pago</strong><br><span id="lblTarjetaTerminacion">Datos confirmados</span></div>
                            <span class="bb-inline-bbd45d7fa2">Pago confirmado</span>
                        </div>
                    </div>

                </div>

                <div class="bb-inline-aedb1f31f8">
                    <div class="bb-inline-f62f462334">
                        <h3 class="bb-inline-7e2101291e">Artículos Comprados</h3>
                        <span class="bb-inline-a842f32832">Boutique Bag</span>
                    </div>

                    <div id="confirmedItemsList" class="bb-inline-a72855efba">
                    </div>

                    <div class="bb-inline-41c8082a49">
                        <div class="bb-inline-6d94a70f67">
                            <span>Subtotal</span>
                            <span id="finalSubtotal">$0</span>
                        </div>
                        <div class="bb-inline-6d94a70f67">
                            <span>Envío Nacional</span>
                            <span class="bb-inline-89dd320454">GRATIS</span>
                        </div>
                        <div class="bb-inline-0a985211e1">
                            <span>Impuestos IVA (19% incluido)</span>
                            <span id="finalIva">$0</span>
                        </div>
                        <div class="bb-inline-cdfcc5c8ab">
                            <span>Total Pagado</span>
                            <span id="finalTotal" class="bb-inline-09134fe590">$0</span>
                        </div>
                    </div>

                </div>

            </div>

            <!-- BOTONES FINALES -->
            <div class="no-print bb-inline-fb026c7b27">
                <a href="${pageContext.request.contextPath}/ProductoServlet" class="btn-primary-brown bb-inline-bc605c49f5">🛍️ Seguir comprando en el catálogo</a>
                <button type="button" data-action="print" class="bb-inline-17e16cf007">📄 Descargar comprobante / Factura PDF</button>
            </div>

        </div>

    </main>

    <!-- FOOTER -->
    <footer class="no-print bb-inline-1ae6c2619a">
        <div class="bb-inline-738a8d6fd9">
            
            <div class="bb-inline-42ad7a3ad0">
                <h3 class="bb-inline-3266e72c2c">SUSCRÍBETE A BEAUTY BOOST</h3>
                <div class="bb-inline-d5f7d6ccce">
                    <input type="email" placeholder="Tu email aquí..." class="bb-inline-22045c3743">
                    <button type="button" class="bb-inline-7e6c1e3ab3">Unirme</button>
                </div>
            </div>

            <div class="bb-inline-3498049fa2">
                <div>
                    <h5 class="bb-inline-127a5af174">BEAUTY BOOST</h5>
                    <p class="bb-inline-af31375b05">Cosmética boutique inspirada en el brillo natural y el ritual diario de cuidado personal. Fórmulas limpias y elegancia táctil.</p>
                </div>
                <div>
                    <h5 class="bb-inline-127a5af174">NAVEGACIÓN</h5>
                    <ul class="bb-inline-7c47dacb28">
                        <li><a href="${pageContext.request.contextPath}/ProductoServlet" class="bb-inline-a4b36df3d4">Catálogo Completo</a></li>
                        <li><a href="#" class="bb-inline-a4b36df3d4">Nuevos Lanzamientos</a></li>
                        <li><a href="#" class="bb-inline-a4b36df3d4">Favoritos de la Comunidad</a></li>
                    </ul>
                </div>
                <div>
                    <h5 class="bb-inline-127a5af174">ATENCIÓN AL CLIENTE</h5>
                    <ul class="bb-inline-7c47dacb28">
                        <li><a href="#" class="bb-inline-a4b36df3d4">Seguimiento de Pedido</a></li>
                        <li><a href="#" class="bb-inline-a4b36df3d4">Envíos & Devoluciones</a></li>
                        <li><a href="#" class="bb-inline-a4b36df3d4">Preguntas Frecuentes</a></li>
                    </ul>
                </div>
            </div>

            <div class="bb-inline-610f70aeec">
                © 2026 Beauty Boost Colombia. Todos los derechos reservados.
            </div>
        </div>
    </footer>

    <!-- SCRIPT DE NAVEGACIÓN Y ENVÍO A MYSQL -->
    
    <script src="${pageContext.request.contextPath}/Vista/js/beauty-boost.js"></script>
</body>
</html>