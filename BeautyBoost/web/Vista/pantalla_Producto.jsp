document.addEventListener('DOMContentLoaded', () => {
    renderProducts();
});

function renderProducts() {
    const productGrid = document.getElementById('productGrid');
    if (!productGrid) return;

    // Obtener la lista de productos definida en products-data.js
    const items = window.products || window.productsData || [];

    const BB_IMAGE_MAP = {
        'Base Matte Pro':'BaseMattePro.png','Base Fluida HD':'Base_Fluida_HD.png','Labial Matte Chic':'Labial Matte Chic.png','Labial Nude Soft':'Labial Nude Soft.png',
        'Lip Gloss Crystal':'Lip Gloss Crystal.png','Máscara Mega Volume':'Mascara Mega Volume.png','Máscara Waterproof Pro':'Mascara Waterproof Pro.png',
        'Paleta de Sombras Nude':'paleta de sombras Nude.png','Iluminador Golden Glow':'iluminador golden glow.png','Polvo Compacto Matte':'polvo compacto matte.png',
        'Spray Fijador Pro':'Spray Fijador Pro.png','Lápiz para Cejas Brow Define':'Lapiz para Cejas Brow Define.png',
        'base hidratante':'base hidratante.png','base natural':'base natural.png','Bronzer Bronze Sun':'bronzer bronze sun.png','Bronzer Sun Kiss':'bronzer sun kiss.png',
        'Contorno Stick Pro':'contorno stick pro.png','Corrector Bright Touch':'corrector bright touch.png','Corrector Full Cover':'corrector full cover.png','Corrector Perfect Skin':'corrector perfect skin.png',
        'Delineador Black Pro':'Delineador Black Pro.png','Delineador Liquid Pro':'Delineador Liquid Pro.png','Delineador Pencil Black':'Delineador Pencil Black.png','Gel Brow Fix':'Gel Brow Fix.png','Gel Fijador de Cejas':'gel fijador de cejas.png',
        'Iluminador Rose Light':'iluminador rose light.png','Iluminador Shine Gold':'iluminador shine gold.png','Brow Pencil Define':'Brow Pencil Define.png','Labial Red Passion':'Labial Red Passion.png','Labial Velvet':'Labial Velvet.png','Labial Nude':'Labial_Nude.png',
        'Lip Gloss Shine':'Lip Gloss Shine.png','Lip Tint Natural':'Lip Tint Natural.png','Mascara Mega Volume':'Mascara Mega Volume.png','Mascara Waterproof Pro':'Mascara Waterproof Pro.png','Mascara Volume Max':'Mascara_Volumen_Max.png',
        'Paleta de Sombras Escarchada':'paleta de sombras escarchada.png','Pestañas Postizas Glam':'Pestanas Postizas Glam.png','Polvo Suelto Soft Matte':'polvo suelto soft matte.png','Primer':'primer.png','Primer Hidratante':'primerhidratante.png','Primer Matte':'primermatte.png',
        'Rubor':'rubor.png','Rubor Coral':'ruborcoral.png','Rubor Rosa':'ruborrosa.png','Sombras Color':'sombras color.png','Sombras Nude':'sombras nude.png','Sombras':'sombras.png','Spray Fix Makeup':'Spray Fix Makeup.png'
    };
    function bbImage(product){
        const name=String(product?.nombre||'').trim();
        const file=BB_IMAGE_MAP[name] || String(product?.imagen||'').split('/').pop();
        return (window.contextPath || '') + '/Vista/img/' + encodeURI(String(file).replace(/^.*\/Vista/img\//i, '').replace(/^\/+/, ''));
    }


    if (items.length === 0) {
        productGrid.innerHTML = '<p class="bb-inline-f39251933c">No hay productos disponibles.</p>';
        return;
    }

    // Dibujar cada producto en la cuadr�cula
    productGrid.innerHTML = items.map(product => `
        <div class="product-card bb-inline-b789535a3e">
            <div class="product-img-container bb-inline-83dd4a9853">
                <img src="${bbImage(product)}" alt="${product.nombre}" class="bb-inline-d497b5d6a5" onerror="this.onerror=null;this.src=(window.contextPath || '')+'/img/default.jpg';">
            </div>
            <h3 class="bb-inline-f5577b3dec">${product.nombre}</h3>
            <p class="bb-inline-43083ce317">${product.categoria}</p>
            <p class="bb-inline-41ae4828e6">$${product.precio.toLocaleString('es-CO')}</p>
            <button data-action="detail-fragment" data-id="${product.id}" class="btn-primary bb-inline-efa1b77319">
                Ver detalles
            </button>
        </div>
    `).join('');
}

// Funci�n para desplegar la ventana emergente modal con la informaci�n detallada del producto
function verDetalle(id) {
    const items = window.products || window.productsData || [];
    const prod = items.find(p => p.id === id);
    if (!prod) return;

    const modal = document.getElementById('productModal');
    if (modal) {
        // Llenar los campos din�micamente con los IDs del modal
        const modalNombre = document.getElementById('modalNombre');
        const modalDescripcion = document.getElementById('modalDescripcion');
        const modalPrecio = document.getElementById('modalPrecio');
        const modalImg = document.getElementById('modalImg');
        const modalModoUso = document.getElementById('modalModoUso');
        const modalStock = document.getElementById('modalStock');

        if (modalNombre) modalNombre.innerText = prod.nombre;
        if (modalDescripcion) modalDescripcion.innerText = prod.descripcion || 'Sin descripci�n disponible.';
        if (modalPrecio) modalPrecio.innerText = '$' + prod.precio.toLocaleString('es-CO');
        if (modalImg) modalImg.src = bbImage(prod);
        if (modalModoUso) modalModoUso.innerText = prod.modoUso || 'Aplicar seg�n indicaci�n del producto.';
        if (modalStock) modalStock.innerText = prod.stock > 0 ? 'En existencia' : 'Agotado';

        // Mostrar el modal usando flex para centrarlo correctamente
        modal.style.display = 'flex';
    }
}

// Funci�n para cerrar la ventana modal
function cerrarModalDetalle() {
    const modal = document.getElementById('productModal');
    if (modal) {
        modal.style.display = 'none';
    }
}