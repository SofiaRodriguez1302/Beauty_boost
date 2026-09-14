(function () {
    'use strict';

    const body = document.body;
    const contextPath = body ? (body.dataset.contextPath || '') : '';
    const userEmail = body ? (body.dataset.userEmail || '') : '';
    const usuarioLogueado = body ? body.dataset.loggedIn === 'true' : false;
    window.contextPath = contextPath;

    function safeJSON(key) {
        try { return JSON.parse(localStorage.getItem(key)) || []; } catch (e) { return []; }
    }
    function cartKey() { return 'beautyBoostCart_' + (userEmail || 'invitado'); }
    function favKey() { return 'beautyBoostFavs_' + (userEmail || 'invitado'); }
    function money(value) { return '$' + Number(value || 0).toLocaleString('es-CO'); }
    function escaparHTML(value) {
        return String(value == null ? '' : value)
            .replace(/&/g, '&amp;')
            .replace(/</g, '&lt;')
            .replace(/>/g, '&gt;')
            .replace(/\"/g, '&quot;')
            .replace(/'/g, '&#039;');
    }
    function showModal(id) { const el=document.getElementById(id); if(el) el.classList.add('modal-visible'); }
    function hideModal(id) { const el=document.getElementById(id); if(el) el.classList.remove('modal-visible'); }
    function showInlineMessage(message) {
        let box=document.getElementById('bbInlineMessage');
        if(!box){ box=document.createElement('div'); box.id='bbInlineMessage'; box.className='form-validation-error'; document.body.prepend(box); }
        box.textContent=message; box.scrollIntoView({behavior:'smooth',block:'center'});
    }

    /* ==================== CATÁLOGO ==================== */
    // Catálogo local de respaldo. Los nombres de imagen coinciden con los archivos reales
    // de img/. Si la BD entrega una imagen distinta, se normaliza por nombre de producto.
    const IMAGENES_PRODUCTO = {
        'base hidratante': 'base hidratante.png',
        'base natural': 'base natural.png',
        'Base Fluida HD': 'Base_Fluida_HD.png',
        'Base Matte Pro': 'BaseMattePro.png',
        'Bronzer Bronze Sun': 'bronzer bronze sun.png',
        'Bronzer Sun Kiss': 'bronzer sun kiss.png',
        'Contorno Stick Pro': 'contorno stick pro.png',
        'Corrector Bright Touch': 'corrector bright touch.png',
        'Corrector Full Cover': 'corrector full cover.png',
        'Corrector Perfect Skin': 'corrector perfect skin.png',
        'Delineador Black Pro': 'Delineador Black Pro.png',
        'Delineador Liquid Pro': 'Delineador Liquid Pro.png',
        'Delineador Pencil Black': 'Delineador Pencil Black.png',
        'Gel Brow Fix': 'Gel Brow Fix.png',
        'Gel Fijador de Cejas': 'gel fijador de cejas.png',
        'Iluminador Golden Glow': 'iluminador golden glow.png',
        'Iluminador Rose Light': 'iluminador rose light.png',
        'Iluminador Shine Gold': 'iluminador shine gold.png',
        'Lápiz para Cejas Brow Define': 'Lapiz para Cejas Brow Define.png',
        'Brow Pencil Define': 'Brow Pencil Define.png',
        'Labial Matte Chic': 'Labial Matte Chic.png',
        'Labial Nude Soft': 'Labial Nude Soft.png',
        'Labial Red Passion': 'Labial Red Passion.png',
        'Labial Velvet': 'Labial Velvet.png',
        'Labial Nude': 'Labial_Nude.png',
        'Lip Gloss Crystal': 'Lip Gloss Crystal.png',
        'Lip Gloss Shine': 'Lip Gloss Shine.png',
        'Lip Tint Natural': 'Lip Tint Natural.png',
        'Máscara Mega Volume': 'Mascara Mega Volume.png',
        'Mascara Mega Volume': 'Mascara Mega Volume.png',
        'Máscara Waterproof Pro': 'Mascara Waterproof Pro.png',
        'Mascara Waterproof Pro': 'Mascara Waterproof Pro.png',
        'Mascara Volume Max': 'Mascara_Volumen_Max.png',
        'Máscara Volume Max': 'Mascara_Volumen_Max.png',
        'Paleta de Sombras Escarchada': 'paleta de sombras escarchada.png',
        'Paleta de sombras escarchada': 'paleta de sombras escarchada.png',
        'Paleta de Sombras Nude': 'paleta de sombras Nude.png',
        'Paleta de sombras Nude': 'paleta de sombras Nude.png',
        'Pestañas Postizas Glam': 'Pestanas Postizas Glam.png',
        'Pestañas Postizas Glam': 'Pestanas Postizas Glam.png',
        'Polvo Compacto Matte': 'polvo compacto matte.png',
        'Polvo Suelto Soft Matte': 'polvo suelto soft matte.png',
        'Primer': 'primer.png',
        'Primer Hidratante': 'primerhidratante.png',
        'Primer Matte': 'primermatte.png',
        'Rubor': 'rubor.png',
        'Rubor Coral': 'ruborcoral.png',
        'Rubor Rosa': 'ruborrosa.png',
        'Sombras Color': 'sombras color.png',
        'Sombras Nude': 'sombras nude.png',
        'Sombras': 'sombras.png',
        'Spray Fijador Pro': 'Spray Fijador Pro.png',
        'Spray Fix Makeup': 'Spray Fix Makeup.png'
    };

    function resolverImagenProducto(producto) {
        // La imagen oficial viene de MySQL. No la sustituimos por un mapa estático.
        // Se aceptan rutas como img/archivo.png, Vista/img/archivo.png o solo archivo.png.
        if (!producto) return 'default.jpg';
        let rawImage = String(producto.imagen || producto.imagenUrl || '').trim().replace(/\\/g, '/');
        if (!rawImage) return 'default.jpg';
        rawImage = rawImage.replace(/^https?:\/\/[^/]+/i, '');
        rawImage = rawImage.replace(/^.*?Vista\/img\//i, '');
        rawImage = rawImage.replace(/^.*?web\/img\//i, '');
        rawImage = rawImage.replace(/^\/+/, '');
        if (/^img\//i.test(rawImage)) rawImage = rawImage.substring(4);
        return rawImage || 'default.jpg';
    }

    const listaProductosGlobal = [
        { id: 1, nombre: 'Base Matte Pro', categoria: 'maquillaje', precio: 45000, imagen: resolverImagenProducto({nombre:'Base Matte Pro'}), descripcion: 'Base de alta cobertura con acabado mate duradero.', badge: 'Popular' },
        { id: 2, nombre: 'Base Fluida HD', categoria: 'maquillaje', precio: 48000, imagen: resolverImagenProducto({nombre:'Base Fluida HD'}), descripcion: 'Textura ligera para un acabado natural de alta definición.', badge: 'Nuevo' },
        { id: 3, nombre: 'Labial Matte Chic', categoria: 'maquillaje', precio: 25000, imagen: resolverImagenProducto({nombre:'Labial Matte Chic'}), descripcion: 'Labial líquido mate de larga duración sin resecar.', badge: 'Ofertado' },
        { id: 4, nombre: 'Labial Nude Soft', categoria: 'maquillaje', precio: 22000, imagen: resolverImagenProducto({nombre:'Labial Nude Soft'}), descripcion: 'Tono nude versátil con textura suave y cremosa.', badge: 'Top' },
        { id: 5, nombre: 'Lip Gloss Crystal', categoria: 'maquillaje', precio: 18000, imagen: resolverImagenProducto({nombre:'Lip Gloss Crystal'}), descripcion: 'Brillo labial ultra hidratante efecto cristalino.', badge: 'Nuevo' },
        { id: 6, nombre: 'Máscara Mega Volume', categoria: 'maquillaje', precio: 32000, imagen: resolverImagenProducto({nombre:'Máscara Mega Volume'}), descripcion: 'Volumen extremo sin dejar grumos.', badge: 'Popular' },
        { id: 7, nombre: 'Máscara Waterproof Pro', categoria: 'maquillaje', precio: 35000, imagen: resolverImagenProducto({nombre:'Máscara Waterproof Pro'}), descripcion: 'Pestañina resistente al agua de máxima duración.', badge: 'Pro' },
        { id: 8, nombre: 'Paleta de Sombras Nude', categoria: 'novedades', precio: 65000, imagen: resolverImagenProducto({nombre:'Paleta de Sombras Nude'}), descripcion: 'Tonos neutros altamente pigmentados.', badge: 'Novedad' },
        { id: 9, nombre: 'Iluminador Golden Glow', categoria: 'mas-vendidos', precio: 28000, imagen: resolverImagenProducto({nombre:'Iluminador Golden Glow'}), descripcion: 'Polvo iluminador tono dorado cálido resplandeciente.', badge: 'Más Vendido' },
        { id: 10, nombre: 'Polvo Compacto Matte', categoria: 'cuidado-facial', precio: 30000, imagen: resolverImagenProducto({nombre:'Polvo Compacto Matte'}), descripcion: 'Controla el brillo facial con acabado aterciopelado.', badge: 'Básico' },
        { id: 11, nombre: 'Spray Fijador Pro', categoria: 'cuidado-facial', precio: 38000, imagen: resolverImagenProducto({nombre:'Spray Fijador Pro'}), descripcion: 'Fija y prolonga la duración de tu maquillaje.', badge: 'Pro' },
        { id: 12, nombre: 'Lápiz para Cejas Brow Define', categoria: 'brochas', precio: 20000, imagen: resolverImagenProducto({nombre:'Lápiz para Cejas Brow Define'}), descripcion: 'Lápiz de precisión para rellenar y enmarcar cejas.', badge: 'Especial' }
    ];

    function categoriaPorId(id) {
        switch (Number(id)) {
            case 1: return 'cuidado-facial';
            case 2: return 'maquillaje';
            case 11: return 'brochas';
            case 13: return 'novedades';
            default: return 'otros';
        }
    }

    function normalizarProductosBD(items) {
        return (Array.isArray(items) ? items : []).map(p => {
            const categoria = p.categoria || categoriaPorId(p.categoriaIdCategoria);
            return { ...p, id: Number(p.id), categoria, imagen: resolverImagenProducto(p) };
        });
    }

    function codificarRutaWeb(ruta) {
        return String(ruta || '').split('/').filter(Boolean).map(segment => encodeURIComponent(segment)).join('/');
    }

    function rutaImagen(producto) {
        let ruta = String(resolverImagenProducto(producto) || '').trim().replace(/\\/g, '/');
        ruta = ruta.replace(/^.*?Vista\/img\//i, '');
        ruta = ruta.replace(/^.*?web\/img\//i, '');
        ruta = ruta.replace(/^\/+/, '');
        if (/^img\//i.test(ruta)) ruta = ruta.substring(4);
        if (!ruta) ruta = 'default.jpg';
        // encodeURIComponent por segmento evita que espacios, #, tildes y otros
        // caracteres del nombre se interpreten como parte de la URL.
        return contextPath + '/img/' + codificarRutaWeb(ruta);
    }

    let productosCatalogoActual = [];
    let filtroCategoriaActual = 'todo';
    let filtroBusquedaActual = '';

    function productosFuente() {
        return productosCatalogoActual.length ? productosCatalogoActual : listaProductosGlobal;
    }

    function aplicarFiltrosCatalogo() {
        const fuente = productosFuente();
        const q = filtroBusquedaActual.toLowerCase().trim();
        let resultado = fuente;

        if (filtroCategoriaActual !== 'todo') {
            if (filtroCategoriaActual === 'mas-vendidos') {
                const destacados = fuente.filter(p => p.esMasVendido === true || p.esMasVendido === 'true' || p.badge === 'Más Vendido');
                resultado = destacados.length ? destacados : [...fuente].sort((a, b) => Number(b.stock || 0) - Number(a.stock || 0)).slice(0, 12);
            } else {
                resultado = fuente.filter(p => String(p.categoria || '').toLowerCase() === filtroCategoriaActual);
            }
        }

        if (q) {
            resultado = resultado.filter(p => (String(p.nombre || '') + ' ' + String(p.descripcion || '')).toLowerCase().includes(q));
        }
        renderizarGrid(resultado);
    }

    let productoSeleccionadoId = null;

    function verDetalleFragment(id){
        abrirModalDetalle(id);
    }

    function actualizarContadorCarrito() {
        const total = safeJSON(cartKey()).reduce((sum, item) => sum + (Number(item.cantidad) || 0), 0);
        document.querySelectorAll('[data-cart-count]').forEach(function(el) {
            el.textContent = String(total);
            el.classList.toggle('is-empty', total === 0);
        });
    }

    function mostrarToast(mensaje) {
        let toast = document.getElementById('bbToast');
        if (!toast) {
            toast = document.createElement('div');
            toast.id = 'bbToast';
            toast.className = 'bb-toast';
            document.body.appendChild(toast);
        }
        toast.textContent = mensaje;
        toast.classList.add('show');
        clearTimeout(window.__bbToastTimer);
        window.__bbToastTimer = setTimeout(function(){ toast.classList.remove('show'); }, 2200);
    }

    function agregarProductoAlCarrito(id, cantidad) {
        if (!usuarioLogueado) {
            mostrarAlertaPersonalizada('Debes iniciar sesión para agregar productos al carrito.');
            return false;
        }
        const p = productosFuente().find(x => Number(x.id) === Number(id));
        if (!p) return false;
        if (Number(p.stock || 0) <= 0) {
            mostrarToast('Este producto está agotado.');
            return false;
        }
        const q = Math.max(1, Number(cantidad) || 1);
        let cart = safeJSON(cartKey());
        const i = cart.findIndex(x => Number(x.id) === Number(p.id));
        if (i >= 0) {
            cart[i].cantidad = Math.max(1, Number(cart[i].cantidad) || 0) + q;
            cart[i].imagen = p.imagen;
            cart[i].precio = p.precio;
            cart[i].nombre = p.nombre;
        } else {
            cart.push({id:p.id, nombre:p.nombre, precio:p.precio, imagen:p.imagen, cantidad:q});
        }
        localStorage.setItem(cartKey(), JSON.stringify(cart));
        actualizarContadorCarrito();
        mostrarToast('✓ ' + p.nombre + ' se agregó al carrito');
        return true;
    }

    function renderizarGrid(items) {
        const grid = document.getElementById('productGrid');
        if (!grid) return;
        const favoritos = safeJSON(favKey());
        if (!items || !items.length) {
            grid.innerHTML = '<div class="catalog-empty-message">No se encontraron productos en esta sección.</div>';
            return;
        }
        grid.innerHTML = items.map(function(p) {
            const fav = favoritos.some(x => Number(x.id) === Number(p.id));
            const nombre = String(p.nombre || 'Producto');
            const descripcion = String(p.descripcion || 'Producto Beauty Boost para complementar tu rutina.').trim();
            const stock = Number(p.stock || 0);
            const disponible = stock > 0;
            return '<article class="product-item-card" data-product-id="'+p.id+'" tabindex="0" role="button" aria-label="Ver detalles de '+escaparHTML(nombre)+'">' +
                '<span class="product-badge">'+escaparHTML(p.badge || 'Producto')+'</span>' +
                '<button type="button" class="product-favorite '+(fav?'is-favorite':'')+'" data-action="favorite" data-id="'+p.id+'" title="'+(fav?'Quitar de favoritos':'Marcar como favorito')+'" aria-pressed="'+(fav?'true':'false')+'">'+(fav?'♥':'♡')+'</button>' +
                '<div class="product-img-box"><img src="'+rutaImagen(p)+'" alt="'+escaparHTML(nombre)+'" loading="lazy"></div>' +
                '<div class="product-rating-row"><span class="product-rating">★ 4.9</span></div>' +
                '<h3 class="product-name">'+escaparHTML(nombre)+'</h3>' +
                '<p class="product-short-description">'+escaparHTML(descripcion)+'</p>' +
                '<div class="product-card-footer"><div><span class="product-price-value">'+money(p.precio)+'</span><span class="product-stock-mini '+(disponible?'':'out')+'">'+(disponible?'En existencia':'Agotado')+'</span></div>' +
                '<button type="button" class="add-to-cart-btn" data-action="add-cart" data-id="'+p.id+'" title="Agregar al carrito" aria-label="Agregar '+escaparHTML(nombre)+' al carrito">+</button></div>' +
                '<button type="button" class="product-detail-link" data-action="detail" data-id="'+p.id+'">Ver detalles</button>' +
                '</article>';
        }).join('');

        grid.querySelectorAll('.product-img-box img').forEach(function(img) {
            img.addEventListener('error', function() {
                this.onerror = null;
                this.src = contextPath + '/img/default.jpg';
            });
        });
    }

    function renderizarFavoritosPerfil(){
        // El perfil se renderiza en el servidor con favoritesList. No lo reemplazamos
        // desde localStorage, porque este puede no contener productos que no estén
        // en el catálogo de respaldo del frontend.
        const box=document.getElementById('favoritesProfileGrid');
        if(!box)return;
        if(box.querySelector('.favorite-profile-card'))return;
        const favoritos=safeJSON(favKey());
        if(!favoritos.length && !box.querySelector('.favorites-empty')){
            box.innerHTML='<div class="favorites-empty"><div>♡</div><h4>Aún no tienes favoritos</h4><p>Marca el corazón de un producto y aparecerá aquí.</p><a href="'+contextPath+'/ProductoServlet" class="btn-primary-brown">Explorar productos</a></div>';
        }
    }

    function obtenerIdsFavoritosSesion(){
        if(!usuarioLogueado) return [];
        const raw=document.body?.dataset.favoriteIds || '';
        const ids=(raw.match(/\\d+/g)||[]).map(Number).filter(Boolean);
        return [...new Set(ids)];
    }
    function sincronizarFavoritosConSesion(){
        if(!usuarioLogueado) return;
        const ids=obtenerIdsFavoritosSesion();
        const favoritos=ids.map(id=>productosFuente().find(p=>Number(p.id)===id)).filter(Boolean);
        localStorage.setItem(favKey(),JSON.stringify(favoritos));
    }
    function actualizarEstadoVisualFavorito(id, marcado){
        document.querySelectorAll('.product-favorite[data-id="'+id+'"]').forEach(btn=>{
            btn.classList.toggle('is-favorite',marcado);
            btn.textContent=marcado?'♥':'♡';
            btn.setAttribute('aria-pressed',marcado?'true':'false');
            btn.title=marcado?'Quitar de favoritos':'Marcar como favorito';
        });
    }
    async function toggleFavorito(id){
        if(!usuarioLogueado){mostrarAlertaPersonalizada('Debes iniciar sesión para agregar productos a tus favoritos.');return;}
        const p=productosFuente().find(x=>Number(x.id)===Number(id));
        let fav=safeJSON(favKey());
        const i=fav.findIndex(x=>Number(x.id)===Number(id));
        const profileCard=document.querySelector('.favorite-profile-card[data-favorite-profile-id="'+id+'"]');
        const anterior=i>=0 || !!profileCard;

        if(p){
            if(anterior){
                const idx=fav.findIndex(x=>Number(x.id)===Number(id));
                if(idx>=0)fav.splice(idx,1);
            }else{
                fav.push(p);
            }
            localStorage.setItem(favKey(),JSON.stringify(fav));
        }

        actualizarEstadoVisualFavorito(id,!anterior);
        if(profileCard && anterior){
            profileCard.remove();
        }
        actualizarContadorFavoritos(anterior ? Math.max(0, Number(document.getElementById('favoritesCountBadge')?.textContent||0)-1) : fav.length);

        try{
            const r=await fetch(contextPath+'/PerfilServlet?action=toggleFavorito&productoId='+encodeURIComponent(id),{
                method:'POST',credentials:'same-origin',headers:{'X-Requested-With':'XMLHttpRequest'}
            });
            const data=await r.json();
            if(!r.ok || !data.ok) throw new Error(data.message||'No se pudo sincronizar el favorito');
            actualizarContadorFavoritos(Number(data.total)||0);

            const box=document.getElementById('favoritesProfileGrid');
            if(box && Number(data.total)===0 && !box.querySelector('.favorite-profile-card')){
                box.innerHTML='<div class="favorites-empty"><div>♡</div><h4>Aún no tienes favoritos</h4><p>Marca el corazón de un producto y aparecerá aquí.</p><a href="'+contextPath+'/ProductoServlet" class="btn-primary-brown">Explorar productos</a></div>';
            } else if(box && Number(data.total)>0){
                const empty=box.querySelector('.favorites-empty');
                if(empty) empty.remove();
            }
        }catch(err){
            // Revertir la operación local si el servidor rechazó el cambio.
            if(p){
                let actual=safeJSON(favKey());
                const j=actual.findIndex(x=>Number(x.id)===Number(id));
                if(anterior && j<0)actual.push(p);
                if(!anterior && j>=0)actual.splice(j,1);
                localStorage.setItem(favKey(),JSON.stringify(actual));
            }
            actualizarEstadoVisualFavorito(id,anterior);
            showInlineMessage('No fue posible guardar el favorito. Inténtalo de nuevo.');
            location.reload();
        }
    }
    function actualizarContadorFavoritos(total){
        const value=Number(total)||0;
        ['favoritesCountBadge'].forEach(id=>{const e=document.getElementById(id);if(e)e.textContent=value});
        const title=document.getElementById('favoritesCountTitle');if(title)title.textContent='('+value+')';
    }
    function filtrarCategoria(cat){
        filtroCategoriaActual = String(cat || 'todo').toLowerCase();
        document.querySelectorAll('.navbar-link[data-category]').forEach(link => {
            link.classList.toggle('active', link.dataset.category === filtroCategoriaActual);
        });
        aplicarFiltrosCatalogo();
    }

    function filtrarProductos(){
        const input=document.getElementById('txtBuscar');
        filtroBusquedaActual=(input ? input.value : '').toLowerCase().trim();
        aplicarFiltrosCatalogo();
    }
    function abrirModalDetalle(id) {
        const p = productosFuente().find(x => Number(x.id) === Number(id));
        if (!p) return;
        productoSeleccionadoId = Number(p.id);
        setText('modalCategoria', categoriaVisible(p));
        setText('modalNombre', p.nombre || 'Producto');
        setText('modalDescripcion', p.descripcion || 'Sin descripción disponible.');
        setText('modalPrecio', money(p.precio));
        setText('modalModoUso', p.modoUso || modoUsoPorCategoria(p));
        const stock = Number(p.stock || 0);
        const stockEl = document.getElementById('modalStock');
        if (stockEl) {
            stockEl.textContent = stock > 0 ? 'En existencia' : 'Agotado';
            stockEl.classList.toggle('out', stock <= 0);
        }
        const img = document.getElementById('modalImg');
        if (img) {
            img.onerror = function(){ this.onerror=null; this.src=contextPath+'/img/default.jpg'; };
            img.src = rutaImagen(p);
            img.alt = p.nombre || 'Producto';
        }
        const qty = document.getElementById('modalCantidadInput');
        if (qty) qty.value = '1';
        const add = document.querySelector('[data-action="add-modal"]');
        if (add) add.disabled = stock <= 0;
        showModal('productModal');
    }

    function categoriaVisible(p) {
        const map = {'cuidado-facial':'Skincare','maquillaje':'Maquillaje','brochas':'Brochas','novedades':'Novedad','mas-vendidos':'Más vendido'};
        return map[String(p.categoria || '').toLowerCase()] || 'Beauty Boost';
    }

    function modoUsoPorCategoria(p) {
        const c = String(p.categoria || '').toLowerCase();
        if (c === 'maquillaje') return 'Aplicar sobre la piel limpia y difuminar de manera uniforme según el acabado deseado.';
        if (c === 'cuidado-facial') return 'Aplicar sobre la piel limpia como parte de la rutina facial y seguir con los productos habituales.';
        if (c === 'brochas') return 'Utilizar con movimientos suaves y limpiar después de cada uso para conservar las fibras en buen estado.';
        return 'Aplicar según las indicaciones del producto y ajustar la cantidad a la intensidad deseada.';
    }

    function cambiarCantidadModal(cambio) {
        const input = document.getElementById('modalCantidadInput');
        if (!input) return;
        let n = parseInt(input.value, 10) || 1;
        n = Math.max(1, n + Number(cambio || 0));
        input.value = String(n);
    }

    function agregarDesdeModal() {
        if (!usuarioLogueado) {
            mostrarAlertaPersonalizada('Debes iniciar sesión para agregar productos al carrito.');
            return;
        }
        const p = productosFuente().find(x => Number(x.id) === Number(productoSeleccionadoId));
        if (!p) return;
        if (Number(p.stock || 0) <= 0) {
            mostrarToast('Este producto está agotado.');
            return;
        }
        const q = Math.max(1, parseInt(document.getElementById('modalCantidadInput')?.value, 10) || 1);
        agregarProductoAlCarrito(p.id, q);
        hideModal('productModal');
    }

    function cerrarModalDetalle(){hideModal('productModal')}
    function cerrarSuccessModal(){hideModal('cartSuccessModal')}
    function irAlCarritoDesdeModal(){location.href=contextPath+'/ProductoServlet?action=carrito'}
    function mostrarAlertaPersonalizada(msg){const e=document.getElementById('customAlertMessage');if(e)e.textContent=msg;showModal('customAlertModal')}
    function redirigirLogin(){location.href=contextPath+'/Vista/Login.jsp'}
    function verificarAccesoCarrito(e){if(e)e.preventDefault();if(!usuarioLogueado)mostrarAlertaPersonalizada('Para ingresar al apartado del carrito debes iniciar sesión.');else location.href=contextPath+'/ProductoServlet?action=carrito'}

    /* ==================== CARRITO ==================== */
    function renderCartItems(container, mode){
        const cart=safeJSON(cartKey());if(!container)return cart;
        if(!cart.length){container.innerHTML='<div class="cart-empty"><div class="cart-empty-icon">🛍️</div><h3>Tu carrito está vacío</h3><p>Agrega productos para verlos aquí.</p><a class="btn-primary" href="'+contextPath+'/ProductoServlet">EXPLORAR PRODUCTOS</a></div>';return cart;}
        let subtotal=0;
        container.innerHTML=cart.map((item,index)=>{const total=item.precio*item.cantidad;subtotal+=total;return '<div class="cart-item"><div class="cart-item-main"><img class="cart-item-image" src="'+rutaImagen(item)+'" alt="'+item.nombre+'"><div><h4 class="cart-item-name">'+item.nombre+'</h4><p class="cart-item-meta">Cantidad: '+item.cantidad+'</p><strong class="cart-item-price">'+money(item.precio)+'</strong></div></div><div class="cart-item-actions">'+(mode==='editable'?'<div class="qty-control"><button data-action="qty" data-index="'+index+'" data-change="-1">−</button><span class="qty-value">'+item.cantidad+'</span><button data-action="qty" data-index="'+index+'" data-change="1">+</button></div>':'')+'<span class="cart-item-price">'+money(total)+'</span><button class="btn-remove" data-action="remove" data-index="'+index+'">Eliminar</button></div></div>'}).join('');
        return cart;
    }
    function cargarCarrito(){
        const container=document.getElementById('cartItemsContainer');const cart=renderCartItems(container,document.body.contains(document.querySelector('.qty-control'))||document.getElementById('resumenSubtotal')?'editable':'summary');
        if(document.getElementById('lblSubtotal')){const sub=cart.reduce((a,x)=>a+x.precio*x.cantidad,0),ship=sub?10000:0,tax=Math.round(sub*.05);document.getElementById('lblSubtotal').textContent=money(sub);document.getElementById('lblEnvio').textContent=money(ship);document.getElementById('lblImpuestos').textContent=money(tax);document.getElementById('lblTotal').textContent=money(sub+ship+tax);}
        if(document.getElementById('resumenSubtotal')){const sub=cart.reduce((a,x)=>a+x.precio*x.cantidad,0);document.getElementById('resumenSubtotal').textContent=money(sub);document.getElementById('resumenTotal').textContent=money(sub);}
        const btn=document.getElementById('btnPagar');if(btn)btn.disabled=!cart.length;
    }
    function cambiarCantidad(index,cambio){let c=safeJSON(cartKey());if(!c[index])return;c[index].cantidad+=cambio;if(c[index].cantidad<=0)c.splice(index,1);localStorage.setItem(cartKey(),JSON.stringify(c));cargarCarrito()}
    function eliminarItem(index){let c=safeJSON(cartKey());c.splice(index,1);localStorage.setItem(cartKey(),JSON.stringify(c));cargarCarrito()}
    function eliminarDelCarrito(index){eliminarItem(index)}
    function procesarPago(){const cart=safeJSON(cartKey());if(!usuarioLogueado){location.href=contextPath+'/LoginServlet';return;}if(!cart.length){showInlineMessage('Tu carrito está vacío.');return;}location.assign(contextPath+'/ProductoServlet?action=checkout&step=2')}

    /* ==================== CHECKOUT ==================== */
    let costoEnvioActual=0;
    function cargarResumenCheckout(){
        const c=safeJSON(cartKey()),box=document.getElementById('checkoutItemsList');if(!box)return;const n=document.getElementById('lblNumArticulos');if(n)n.textContent=c.length+' Artículos';let sub=0;
        if(!c.length){box.innerHTML='<p class="field-hint">No hay productos en el carrito.</p>';setText('checkoutSubtotal',money(0));setText('checkoutTotal',money(0));return;}
        box.innerHTML=c.map(item=>{const t=item.precio*item.cantidad;sub+=t;return '<div class="checkout-item"><div class="checkout-item-main"><img class="checkout-item-image" src="'+rutaImagen(item)+'" alt="'+item.nombre+'"><div><span class="checkout-item-name">'+item.nombre+'</span><span class="checkout-item-meta">Cant: '+item.cantidad+' • '+money(item.precio)+'</span></div></div><span class="checkout-item-total">'+money(t)+'</span></div>'}).join('');setText('checkoutSubtotal',money(sub));setText('checkoutTotal',money(sub+costoEnvioActual));
    }
    function setText(id,v){const e=document.getElementById(id);if(e)e.textContent=v}
    function seleccionarEnvio(tipo,costo){costoEnvioActual=costo;['optEstandar','optExpress'].forEach(id=>{const e=document.getElementById(id);if(e)e.classList.remove('selected')});const selected=tipo==='estandar'?'optEstandar':'optExpress';document.getElementById(selected)?.classList.add('selected');const rs=document.getElementById('radioEstandar'),re=document.getElementById('radioExpress');if(rs)rs.checked=tipo==='estandar';if(re)re.checked=tipo==='express';setText('checkoutCostEnvio',tipo==='estandar'?'GRATIS':'$12.000');setText('lblTipoEnvioSeleccionado',tipo==='estandar'?'Envío Estándar':'Envío Express Mismo Día');cargarResumenCheckout()}
    function irAlPaso2(){toggleStep(1,2)}
    function volverAlPaso1(){toggleStep(2,1)}
    function toggleStep(from,to){document.getElementById('viewStep'+from)?.classList.remove('step-visible');document.getElementById('viewStep'+from)?.classList.add('step-hidden');document.getElementById('viewStep'+to)?.classList.remove('step-hidden');document.getElementById('viewStep'+to)?.classList.add('step-visible');for(let i=1;i<=3;i++){document.getElementById('pillStep'+i)?.classList.toggle('active',i===to);document.getElementById('pillStep'+i)?.classList.toggle('completed',i<to);if(document.getElementById('numStep'+i)&&i<to)document.getElementById('numStep'+i).textContent='✓'}window.scrollTo({top:0,behavior:'smooth'})}
    let metodoPagoActual='tarjeta';
    function cambiarTabPago(tab){
        metodoPagoActual=tab;
        const forms={tarjeta:'Tarjeta',pse:'Pse',nequi:'Nequi',daviplata:'Daviplata',contraentrega:'Contraentrega'};
        Object.keys(forms).forEach(function(x){
            const id='tab'+forms[x];
            document.getElementById(id)?.classList.toggle('selected',x===tab);
            const form=document.getElementById('form'+forms[x]);
            if(form){
                form.hidden=x!==tab;
                form.querySelectorAll('input,select,textarea').forEach(function(el){
                    el.disabled=x!==tab;
                    el.required=false;
                });
            }
        });
        const requiredByMethod={
            tarjeta:['txtNombreTitular','txtNumeroTarjeta','txtFechaVencimiento','txtCvv','selectCuotas'],
            pse:['pseTipoPersona','pseBanco','pseCorreo'],
            nequi:['nequiTelefono'],
            daviplata:['daviplataTipoDocumento','daviplataDocumento','daviplataTelefono'],
            contraentrega:['contraNombrePedido','contraDocumento','contraDireccion','contraTelefono','contraRecibe','contraRecibeTelefono']
        };
        (requiredByMethod[tab]||[]).forEach(function(id){const el=document.getElementById(id);if(el){el.disabled=false;el.required=true;}});
        // Un formulario oculto no participa en la validación HTML del navegador.
        document.getElementById('paymentReview')?.setAttribute('hidden','');
    }
    function obtenerDireccionCheckout(){
        const e=document.getElementById('txtDireccionEntrega');return e?e.value.trim():'';
    }
    function datosPagoValidos(){
        const req=(id)=>{const e=document.getElementById(id);return e?e.value.trim():''};
        if(metodoPagoActual==='tarjeta') return req('txtNombreTitular')&&req('txtNumeroTarjeta')&&req('txtFechaVencimiento')&&req('txtCvv')&&req('selectCuotas');
        if(metodoPagoActual==='pse') return req('pseTipoPersona')&&req('pseBanco')&&req('pseCorreo');
        if(metodoPagoActual==='nequi') return req('nequiTelefono');
        if(metodoPagoActual==='daviplata') return req('daviplataTipoDocumento')&&req('daviplataDocumento')&&req('daviplataTelefono');
        return req('contraNombrePedido')&&req('contraDocumento')&&req('contraDireccion')&&req('contraTelefono')&&req('contraRecibe')&&req('contraRecibeTelefono');
    }
    function etiquetaMetodoPago(){return {tarjeta:'Tarjeta Crédito/Débito',pse:'PSE',nequi:'Nequi',daviplata:'Daviplata',contraentrega:'Contra Entrega'}[metodoPagoActual]||'Método de pago';}
    function datosPagoResumen(){
        const req=(id)=>{const e=document.getElementById(id);return e?e.value.trim():''};
        if(metodoPagoActual==='tarjeta'){const n=req('txtNumeroTarjeta');return 'Tarjeta terminada en •••• '+(n.slice(-4)||'----')}
        if(metodoPagoActual==='pse')return 'Banco: '+req('pseBanco')+' · '+req('pseTipoPersona');
        if(metodoPagoActual==='nequi')return 'Celular: '+req('nequiTelefono');
        if(metodoPagoActual==='daviplata')return 'Documento: '+req('daviplataDocumento')+' · Celular: '+req('daviplataTelefono');
        return 'Recibe: '+req('contraRecibe')+' · Teléfono: '+req('contraRecibeTelefono');
    }
    function revisarPago(){
        const direccion=obtenerDireccionCheckout();
        if(!direccion){showInlineMessage('Escribe la dirección donde quieres recibir el pedido.');document.getElementById('txtDireccionEntrega')?.focus();return}
        if(!datosPagoValidos()){showInlineMessage('Completa los datos correspondientes al método de pago seleccionado.');return}
        setText('reviewDireccion',direccion);setText('reviewMetodo',etiquetaMetodoPago());setText('reviewDatosPago',datosPagoResumen());setText('reviewTotal',document.getElementById('checkoutTotal')?.textContent||'$0');
        setText('step2Direccion',direccion);
        const review=document.getElementById('paymentReview');if(review){review.hidden=false;review.scrollIntoView({behavior:'smooth',block:'center'})}
    }
    function editarPago(){const r=document.getElementById('paymentReview');if(r)r.hidden=true}
    async function procesarPagoFinal(){
        if(!obtenerDireccionCheckout() || !datosPagoValidos()){revisarPago();return}
        const c=safeJSON(cartKey());if(!c.length){showInlineMessage('Tu carrito está vacío.');return}
        const card=document.getElementById('txtNumeroTarjeta');const last=card&&card.value.trim().length>=4?card.value.trim().slice(-4):'4242';setText('lblTarjetaTerminacion','•••• '+last);let sub=0;const list=document.getElementById('confirmedItemsList');if(list)list.innerHTML=c.map(item=>{const t=item.precio*item.cantidad;sub+=t;return '<div class="checkout-item"><div class="checkout-item-main"><img class="checkout-item-image" src="'+rutaImagen(item)+'" alt="'+item.nombre+'"><div><strong class="checkout-item-name">'+item.nombre+'</strong><span class="checkout-item-meta">Cant: '+item.cantidad+'</span></div></div><span class="checkout-item-total">'+money(t)+'</span></div>'}).join('');
        const total=sub+costoEnvioActual,iva=Math.round(sub*.19),order=Math.floor(Math.random()*90000)+10000;setText('finalSubtotal',money(sub));setText('finalIva',money(iva));setText('finalTotal',money(total));setText('lblOrdenConfirmadaNum','ORDEN CONFIRMADA #BB-'+order);setText('confirmedAddress',obtenerDireccionCheckout());setText('confirmedPaymentMethod',etiquetaMetodoPago());setText('lblTarjetaTerminacion',datosPagoResumen());setText('confirmedShipping',document.getElementById('lblTipoEnvioSeleccionado')?.textContent||'Envío seleccionado');
        try{
            const params=new URLSearchParams();
            params.append('action','procesarCompra');
            params.append('numeroPedido',order);
            params.append('total',total);
            params.append('carritoJson',JSON.stringify(c));
            params.append('direccionEntrega',obtenerDireccionCheckout());
            params.append('metodoPago',metodoPagoActual);
            ['txtNombreTitular','txtNumeroTarjeta','txtFechaVencimiento','txtCvv','selectCuotas','pseTipoPersona','pseBanco','pseCorreo','nequiTelefono','daviplataTipoDocumento','daviplataDocumento','daviplataTelefono','contraNombrePedido','contraDocumento','contraDireccion','contraTelefono','contraRecibe','contraRecibeTelefono'].forEach(function(id){const el=document.getElementById(id);if(el && !el.disabled) params.append(id,el.value||'');});
            const response=await fetch(contextPath+'/ProductoServlet',{
                method:'POST',
                credentials:'same-origin',
                headers:{'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8','X-Requested-With':'XMLHttpRequest'},
                body:params
            });
            let data=null;
            try{data=await response.json();}catch(ignore){}
            if(!response.ok || !data || data.status!=='success'){
                throw new Error(data?.message || 'No fue posible registrar la orden.');
            }
        }catch(e){
            console.error('Error persistiendo la orden:',e);
            showInlineMessage('No se pudo registrar el pedido. Tu carrito se conservará para que puedas intentarlo nuevamente.');
            return;
        }
        localStorage.removeItem(cartKey());document.getElementById('checkoutMainLayout')?.classList.add('step-hidden');document.getElementById('viewStep3')?.classList.add('step-visible');document.getElementById('pillStep2')?.classList.remove('active');document.getElementById('pillStep2')?.classList.add('completed');document.getElementById('pillStep3')?.classList.add('active','completed');setText('numStep2','✓');setText('numStep3','✓');window.scrollTo({top:0,behavior:'smooth'});
    }

    /* ==================== PERFIL ==================== */
    function switchTab(tabId,titleText){for(let i=1;i<=5;i++){document.getElementById('btnTab'+i)?.classList.remove('active');document.getElementById('tab'+i)?.classList.remove('active')}const n=tabId.replace('tab','');document.getElementById('btnTab'+n)?.classList.add('active');document.getElementById(tabId)?.classList.add('active');setText('lblBreadcrumbCurrent',titleText)}
    function cerrarModal(){hideModal('modalDetallePedido')}
    function printInvoice(card){
        const num=card.dataset.num,fecha=card.dataset.fecha,total=card.dataset.total,direccion=card.dataset.direccion,productos=card.dataset.productos,win=window.open('','_blank');if(!win)return;let rows='';try{JSON.parse(productos).forEach(p=>rows+='<tr><td>'+p.cantidad+' x '+p.nombre+'</td><td>Aprobado / Procesado</td><td>$ '+p.subtotal+'</td></tr>')}catch(e){rows='<tr><td>Compra de cosméticos Beauty Boost</td><td>Aprobado / Procesado</td><td>'+total+'</td></tr>'}win.document.write('<!doctype html><html><head><title>Factura Pedido #'+num+' - Beauty Boost</title><link rel="stylesheet" href="'+contextPath+'/Vista/css/style.css"></head><body><div class="print-logo-header"><div><strong>BEAUTY BOOST</strong><div>Comprobante Oficial de Compra</div></div><div><strong>Factura de Pedido #'+num+'</strong><br>Fecha: '+fecha+'</div></div><p><strong>Cliente:</strong> '+document.body.dataset.customerName+'</p><p><strong>Correo:</strong> '+document.body.dataset.customerEmail+'</p><p><strong>Dirección de Envío:</strong> '+direccion+'</p><table class="data-table"><thead><tr><th>Concepto / Producto</th><th>Estado</th><th>Monto Total</th></tr></thead><tbody>'+rows+'</tbody></table><div class="summary-total">Total Pagado: '+total+'</div></body></html>');win.document.close();win.onload=()=>win.print()
    }

    /* ==================== REGISTRO ==================== */
    function validarRegistro(event){
        const form=event.target,doc=document.getElementById('txtIdentificacion'),tel=document.getElementById('txtTelefono'),date=document.getElementById('txtFechaNacimientoDisplay'),hidden=document.getElementById('txtFechaNacimiento');
        let ok=true;const setErr=(id,msg)=>{const e=document.getElementById(id);if(e)e.textContent=msg};setErr('documentoError','');setErr('telefonoError','');setErr('fechaError','');
        if(doc){doc.value=doc.value.replace(/\D/g,'');if(doc.value.length<10){setErr('documentoError','El documento debe tener mínimo 10 números.');ok=false}}
        if(tel){tel.value=tel.value.replace(/\D/g,'');if(tel.value.length<10){setErr('telefonoError','El teléfono debe tener mínimo 10 números.');ok=false}}
        if(date){const v=date.value.trim();const m=/^(\d{2})\/(\d{2})\/(\d{4})$/.exec(v);let valid=false;if(m){const d=Number(m[1]),mo=Number(m[2]),y=Number(m[3]),dt=new Date(y,mo-1,d);valid=dt.getFullYear()===y&&dt.getMonth()===mo-1&&dt.getDate()===d;if(valid&&hidden)hidden.value=y+'-'+String(mo).padStart(2,'0')+'-'+String(d).padStart(2,'0')}if(!valid){setErr('fechaError','La fecha debe tener el formato DD/MM/AAAA y ser una fecha válida.');ok=false}}
        if(!ok){event.preventDefault();return false}return true;
    }
    function maskDate(e){let v=e.target.value.replace(/\D/g,'').slice(0,8);if(v.length>4)v=v.slice(0,2)+'/'+v.slice(2,4)+'/'+v.slice(4);else if(v.length>2)v=v.slice(0,2)+'/'+v.slice(2);e.target.value=v}

    /* ==================== EVENTOS ==================== */
    document.addEventListener('click',function(e){
        const category=e.target.closest('[data-category]');
        if(category){e.preventDefault();filtrarCategoria(category.dataset.category);return;}
        const card=e.target.closest('.product-item-card');
        const action=e.target.closest('[data-action]');
        if(action){
            const a=action.dataset.action,id=Number(action.dataset.id);
            if(a==='favorite'){e.stopPropagation();toggleFavorito(id);return}
            if(a==='add-cart'){e.stopPropagation();agregarProductoAlCarrito(id,1);return}
            if(a==='detail'){e.stopPropagation();abrirModalDetalle(id);return}
            if(a==='detail-fragment'){e.stopPropagation();verDetalleFragment(id);return}
            if(a==='qty'){cambiarCantidad(Number(action.dataset.index),Number(action.dataset.change));return}
            if(a==='remove'){eliminarItem(Number(action.dataset.index));return}
            if(a==='cart-access'){verificarAccesoCarrito(e);return}
            if(a==='close-product-modal'){cerrarModalDetalle();return}
            if(a==='modal-qty'){cambiarCantidadModal(Number(action.dataset.change));return}
            if(a==='add-modal'){agregarDesdeModal();return}
            if(a==='close-success'){cerrarSuccessModal();return}
            if(a==='go-cart'){irAlCarritoDesdeModal();return}
            if(a==='go-login'){redirigirLogin();return}
            if(a==='switch-tab'){switchTab(action.dataset.tab,action.dataset.title);return}
            if(a==='close-order-modal'){cerrarModal();return}
            if(a==='shipping'){seleccionarEnvio(action.dataset.type,Number(action.dataset.cost));return}
            if(a==='step2'){irAlPaso2();return}
            if(a==='step1'){volverAlPaso1();return}
            if(a==='payment-tab'){cambiarTabPago(action.dataset.tab);return}
            if(a==='review-payment'){revisarPago();return}
            if(a==='confirm-payment'){procesarPagoFinal();return}
            if(a==='edit-payment'){editarPago();return}
            if(a==='print'){window.print();return}
            if(a==='pay'){procesarPago();return}
            if(a==='go-checkout'){procesarPago();return}
        }
        if(card)abrirModalDetalle(Number(card.dataset.productId));
        if(e.target.matches('[data-close-modal]'))hideModal(e.target.dataset.closeModal);
    });
    document.addEventListener('keydown',e=>{if(e.key==='Escape'){['productModal','cartSuccessModal','customAlertModal','modalDetallePedido'].forEach(hideModal)}});
    document.addEventListener('DOMContentLoaded',function(){
        window.togglePassword=function(inputId, button){
            const input=document.getElementById(inputId);
            if(!input)return false;
            const visible=input.type==='text';
            input.setAttribute('type', visible ? 'password' : 'text');
            if(button){
                button.textContent=visible?'👁':'🙈';
                button.setAttribute('aria-label',visible?'Mostrar contraseña':'Ocultar contraseña');
                button.setAttribute('aria-pressed',visible?'false':'true');
            }
            return false;
        };
        document.querySelectorAll('[data-toggle-password]').forEach(function(btn){
            btn.addEventListener('click',function(e){
                e.preventDefault();
                togglePassword(btn.dataset.togglePassword,btn);
            });
        });
        const grid=document.getElementById('productGrid');
        if(grid){
            productosCatalogoActual = normalizarProductosBD(window.products);
            if(!productosCatalogoActual.length) productosCatalogoActual = listaProductosGlobal.slice();
            const params = new URLSearchParams(window.location.search);
            const initialCategory = (params.get('categoria') || params.get('id_categoria') || 'todo').toLowerCase();
            const initialSearch = params.get('busqueda') || params.get('buscar') || params.get('q') || '';
            const search = document.getElementById('txtBuscar');
            if(search && initialSearch) search.value = initialSearch;
            filtroCategoriaActual = initialCategory || 'todo';
            filtroBusquedaActual = initialSearch || '';
            sincronizarFavoritosConSesion();
            aplicarFiltrosCatalogo();
            actualizarContadorCarrito();
            document.querySelectorAll('.navbar-link[data-category]').forEach(link => {
                link.classList.toggle('active', link.dataset.category === filtroCategoriaActual);
            });
            search?.addEventListener('input',filtrarProductos);
        }
        if(document.getElementById('cartItemsContainer'))cargarCarrito();
        if(document.getElementById('checkoutItemsList')){const cart=safeJSON(cartKey());if(!cart.length){location.replace(contextPath+'/ProductoServlet?action=carrito');return;}cargarResumenCheckout();cambiarTabPago('tarjeta');setText('step2Direccion',obtenerDireccionCheckout());if(new URLSearchParams(location.search).get('step')==='2')irAlPaso2();}
        if(document.getElementById('favoritesProfileGrid'))renderizarFavoritosPerfil();
        const reg=document.querySelector('form[action*="RegistroServlet"]');if(reg){reg.addEventListener('submit',validarRegistro);document.getElementById('txtFechaNacimientoDisplay')?.addEventListener('input',maskDate)}
        document.querySelectorAll('.btn-ver-detalles').forEach(btn=>btn.addEventListener('click',function(){const card=this.closest('.tarjeta-pedido');if(!card)return;setText('mNumPedido','Pedido #'+card.dataset.num);setText('mFecha',card.dataset.fecha);setText('mEstado',card.dataset.estado);setText('mTotal',card.dataset.total);setText('mDireccion',card.dataset.direccion);const box=document.getElementById('mListaProductos');if(box){box.innerHTML='';try{JSON.parse(card.dataset.productos).forEach(p=>{const row=document.createElement('div');row.className='cart-item';row.innerHTML='<span>'+p.cantidad+' x '+p.nombre+'</span><strong>'+p.subtotal+'</strong>';box.appendChild(row)})}catch(e){box.textContent='No hay detalles de productos disponibles.'}}showModal('modalDetallePedido')}));
        document.querySelectorAll('.btn-descargar-factura').forEach(btn=>btn.addEventListener('click',function(){printInvoice(this.closest('.tarjeta-pedido'))}));
    });

    // Public API for existing JSP links/handlers. No business logic is changed.
    Object.assign(window,{verDetalleFragment,agregarProductoAlCarrito,actualizarContadorCarrito,filtrarCategoria,filtrarProductos,toggleFavorito,abrirModalDetalle,cambiarCantidadModal,verificarAccesoCarrito,agregarDesdeModal,cerrarSuccessModal,irAlCarritoDesdeModal,mostrarAlertaPersonalizada,redirigirLogin,cerrarModalDetalle,cargarCarrito,eliminarDelCarrito,cambiarCantidad,eliminarItem,procesarPago,cargarResumenCheckout,seleccionarEnvio,irAlPaso2,volverAlPaso1,cambiarTabPago,procesarPagoFinal,revisarPago,editarPago,renderizarFavoritosPerfil,switchTab,cerrarModal});
})();
