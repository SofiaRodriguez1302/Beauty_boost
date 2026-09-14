(function () {
    'use strict';

    // La portada es una aplicación web JSP, por lo que el temporizador correcto
    // para el navegador es JavaScript. 4500 ms = 4.5 segundos.
    const INTERVALO_CARRUSEL = 4500;
    const slides = Array.from(document.querySelectorAll('.welcome-slide'));
    const indicators = Array.from(document.querySelectorAll('.welcome-indicator'));

    if (slides.length === 0) return;

    let indiceActual = 0;
    let temporizador = null;

    function mostrarSlide(indice) {
        indiceActual = (indice + slides.length) % slides.length;

        slides.forEach((slide, i) => {
            const activo = i === indiceActual;
            slide.classList.toggle('active', activo);
            slide.setAttribute('aria-hidden', activo ? 'false' : 'true');
        });

        indicators.forEach((indicator, i) => {
            const activo = i === indiceActual;
            indicator.classList.toggle('active', activo);
            if (activo) indicator.setAttribute('aria-current', 'true');
            else indicator.removeAttribute('aria-current');
        });
    }

    function siguienteSlide() {
        mostrarSlide(indiceActual + 1);
    }

    function iniciarCarrusel() {
        clearInterval(temporizador);
        temporizador = setInterval(siguienteSlide, INTERVALO_CARRUSEL);
    }

    indicators.forEach((indicator) => {
        indicator.addEventListener('click', function () {
            const indice = Number(this.dataset.slide);
            if (Number.isInteger(indice)) {
                mostrarSlide(indice);
                iniciarCarrusel();
            }
        });
    });

    // Pausar mientras el usuario mantiene el puntero sobre la portada.
    const hero = document.querySelector('.welcome-hero');
    if (hero) {
        hero.addEventListener('mouseenter', () => clearInterval(temporizador));
        hero.addEventListener('mouseleave', iniciarCarrusel);
    }

    document.addEventListener('visibilitychange', function () {
        if (document.hidden) clearInterval(temporizador);
        else iniciarCarrusel();
    });

    mostrarSlide(0);
    iniciarCarrusel();
})();
