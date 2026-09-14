-- =====================================================
-- BEAUTY BOOST - BASE DE DATOS UNIFICADA (ACTUALIZADA Y CORREGIDA)
-- =====================================================

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema script_beauty_boost
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `script_beauty_boost`;
CREATE SCHEMA `script_beauty_boost` DEFAULT CHARACTER SET utf8mb4 ;
USE `script_beauty_boost` ;

-- -----------------------------------------------------
-- Table `script_beauty_boost`.`ciudades`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `script_beauty_boost`.`ciudades` (
  `id_ciudades` INT NOT NULL AUTO_INCREMENT,
  `descripcion_ciudad` VARCHAR(45) NOT NULL,
  `codigo_postal` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_ciudades`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `script_beauty_boost`.`roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `script_beauty_boost`.`roles` (
  `id_rol` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id_rol`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `script_beauty_boost`.`tipo_usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `script_beauty_boost`.`tipo_usuario` (
  `id_tipo_usuario` INT NOT NULL AUTO_INCREMENT,
  `nombre_tipo_usuario` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_tipo_usuario`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `script_beauty_boost`.`tipo_documento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `script_beauty_boost`.`tipo_documento` (
  `id_tipo_documento` INT NOT NULL AUTO_INCREMENT,
  `descripcion_tipo_documento` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_tipo_documento`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `script_beauty_boost`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `script_beauty_boost`.`usuario` (
  `id_usuario` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `apellido` VARCHAR(45) NOT NULL,
  `numero_identificacion` VARCHAR(20) NOT NULL,
  `telefono` VARCHAR(20) NOT NULL,
  `correo` VARCHAR(45) NOT NULL,
  `clave` VARCHAR(255) NOT NULL,
  `fecha_nacimiento` DATETIME NOT NULL,
  `fecha_vencimiento_clave` DATE NOT NULL,
  `autorizacion_datos` VARCHAR(45) NOT NULL,
  `estado_usuario` VARCHAR(20) NOT NULL DEFAULT 'Activo',
  `tipo_documento_id_tipo_documento` INT NOT NULL,
  `tipo_usuario_id_tipo_usuario` INT NOT NULL,
  `roles_id_rol` INT NOT NULL DEFAULT 2,
  PRIMARY KEY (`id_usuario`),
  UNIQUE INDEX `numero_identificacion_unique` (`numero_identificacion` ASC),
  UNIQUE INDEX `correo_unique` (`correo` ASC),
  INDEX `fk_usuario_tipo_documento1_idx` (`tipo_documento_id_tipo_documento` ASC),
  INDEX `fk_usuario_tipo_usuario1_idx` (`tipo_usuario_id_tipo_usuario` ASC),
  INDEX `fk_usuario_roles1_idx` (`roles_id_rol` ASC),
  CONSTRAINT `fk_usuario_tipo_documento1`
    FOREIGN KEY (`tipo_documento_id_tipo_documento`)
    REFERENCES `script_beauty_boost`.`tipo_documento` (`id_tipo_documento`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuario_tipo_usuario1`
    FOREIGN KEY (`tipo_usuario_id_tipo_usuario`)
    REFERENCES `script_beauty_boost`.`tipo_usuario` (`id_tipo_usuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuario_roles1`
    FOREIGN KEY (`roles_id_rol`)
    REFERENCES `script_beauty_boost`.`roles` (`id_rol`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `script_beauty_boost`.`direccion_envio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `script_beauty_boost`.`direccion_envio` (
  `id_direccion` INT NOT NULL AUTO_INCREMENT,
  `direccion_envio` VARCHAR(85) NOT NULL,
  `usuario_id_usuario` INT NOT NULL,
  `ciudades_id_ciudades` INT NOT NULL,
  PRIMARY KEY (`id_direccion`),
  INDEX `fk_direccion_envio_usuario1_idx` (`usuario_id_usuario` ASC),
  INDEX `fk_direccion_envio_ciudades1_idx` (`ciudades_id_ciudades` ASC),
  CONSTRAINT `fk_direccion_envio_usuario1`
    FOREIGN KEY (`usuario_id_usuario`)
    REFERENCES `script_beauty_boost`.`usuario` (`id_usuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_direccion_envio_ciudades1`
    FOREIGN KEY (`ciudades_id_ciudades`)
    REFERENCES `script_beauty_boost`.`ciudades` (`id_ciudades`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `script_beauty_boost`.`resena_usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `script_beauty_boost`.`resena_usuario` (
  `id_resena` INT NOT NULL AUTO_INCREMENT,
  `observacion` VARCHAR(45) NOT NULL,
  `calificacion` INT NOT NULL,
  `fecha` DATETIME NOT NULL,
  `usuario_id_usuario` INT NOT NULL,
  PRIMARY KEY (`id_resena`),
  INDEX `fk_resena_usuario_usuario1_idx` (`usuario_id_usuario` ASC),
  CONSTRAINT `fk_resena_usuario_usuario1`
    FOREIGN KEY (`usuario_id_usuario`)
    REFERENCES `script_beauty_boost`.`usuario` (`id_usuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `script_beauty_boost`.`cabeza_carrito`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `script_beauty_boost`.`cabeza_carrito` (
  `id_carrito` INT NOT NULL AUTO_INCREMENT,
  `fecha_creacion` DATE NOT NULL,
  `fecha_actualizacion` DATE NOT NULL,
  PRIMARY KEY (`id_carrito`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `script_beauty_boost`.`categoria`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `script_beauty_boost`.`categoria` (
  `id_categoria` INT NOT NULL AUTO_INCREMENT,
  `nombre_categoria` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_categoria`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `script_beauty_boost`.`producto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `script_beauty_boost`.`producto` (
  `id_producto` INT NOT NULL AUTO_INCREMENT,
  `nombre_prod` VARCHAR(45) NOT NULL,
  `descripcion_prod` VARCHAR(45) NOT NULL,
  `precio` DECIMAL(20,2) NOT NULL,
  `stock` INT NOT NULL,
  `imagen_url` VARCHAR(255) NULL DEFAULT 'img/default.jpg',
  `estado_producto` VARCHAR(20) NOT NULL DEFAULT 'Activo',
  `categoria_id_categoria` INT NOT NULL,
  PRIMARY KEY (`id_producto`),
  INDEX `fk_producto_categoria1_idx` (`categoria_id_categoria` ASC),
  CONSTRAINT `fk_producto_categoria1`
    FOREIGN KEY (`categoria_id_categoria`)
    REFERENCES `script_beauty_boost`.`categoria` (`id_categoria`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `script_beauty_boost`.`favoritos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `script_beauty_boost`.`favoritos` (
  `id_favorito` INT NOT NULL AUTO_INCREMENT,
  `usuario_id_usuario` INT NOT NULL,
  `producto_id_producto` INT NOT NULL,
  `fecha_agregado` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_favorito`),
  UNIQUE INDEX `usuario_producto_unique` (`usuario_id_usuario` ASC, `producto_id_producto` ASC),
  INDEX `fk_favoritos_usuario1_idx` (`usuario_id_usuario` ASC),
  INDEX `fk_favoritos_producto1_idx` (`producto_id_producto` ASC),
  CONSTRAINT `fk_favoritos_usuario1`
    FOREIGN KEY (`usuario_id_usuario`)
    REFERENCES `script_beauty_boost`.`usuario` (`id_usuario`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_favoritos_producto1`
    FOREIGN KEY (`producto_id_producto`)
    REFERENCES `script_beauty_boost`.`producto` (`id_producto`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `script_beauty_boost`.`estado_pedido`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `script_beauty_boost`.`estado_pedido` (
  `id_estado_pedio` INT NOT NULL AUTO_INCREMENT,
  `descripcion_estado` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_estado_pedio`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `script_beauty_boost`.`pedido`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `script_beauty_boost`.`pedido` (
  `id_pedido` INT NOT NULL AUTO_INCREMENT,
  `fecha_pedido` DATE NOT NULL,
  `numero_pedido` INT NOT NULL,
  `total` DECIMAL(20,2) NOT NULL,
  `estado_pedido` VARCHAR(20) NOT NULL DEFAULT 'Activo',
  `usuario_id_usuario` INT NOT NULL,
  `estado_pedido_id_estado_pedido` INT NOT NULL DEFAULT 1,
  PRIMARY KEY (`id_pedido`),
  INDEX `fk_cabeza_pedido_usuario1_idx` (`usuario_id_usuario` ASC),
  INDEX `fk_pedido_estado_pedido1_idx` (`estado_pedido_id_estado_pedido` ASC),
  CONSTRAINT `fk_cabeza_pedido_usuario1`
    FOREIGN KEY (`usuario_id_usuario`)
    REFERENCES `script_beauty_boost`.`usuario` (`id_usuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_pedido_estado_pedido1`
    FOREIGN KEY (`estado_pedido_id_estado_pedido`)
    REFERENCES `script_beauty_boost`.`estado_pedido` (`id_estado_pedio`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `script_beauty_boost`.`metodo_de_pago`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `script_beauty_boost`.`metodo_de_pago` (
  `id_metodo_de_pago` INT NOT NULL AUTO_INCREMENT,
  `descripcion_metodo_de_pago` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_metodo_de_pago`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `script_beauty_boost`.`pago`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `script_beauty_boost`.`pago` (
  `id_pago` INT NOT NULL AUTO_INCREMENT,
  `fecha_pago` DATE NOT NULL,
  `monto_pagado` DECIMAL(10,2) NOT NULL,
  `metodo_de_pago_id_metodo_pago` INT NOT NULL,
  `pedido_id_pedido` INT NOT NULL,
  PRIMARY KEY (`id_pago`),
  INDEX `fk_pago_metodo_de_pago1_idx` (`metodo_de_pago_id_metodo_pago` ASC),
  INDEX `fk_pago_pedido1_idx` (`pedido_id_pedido` ASC),
  CONSTRAINT `fk_pago_metodo_de_pago1`
    FOREIGN KEY (`metodo_de_pago_id_metodo_pago`)
    REFERENCES `script_beauty_boost`.`metodo_de_pago` (`id_metodo_de_pago`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_pago_pedido1`
    FOREIGN KEY (`pedido_id_pedido`)
    REFERENCES `script_beauty_boost`.`pedido` (`id_pedido`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `script_beauty_boost`.`empresas_envio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `script_beauty_boost`.`empresas_envio` (
  `id_empresa_envio` INT NOT NULL AUTO_INCREMENT,
  `nombre_empresa` VARCHAR(45) NOT NULL,
  `nit` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id_empresa_envio`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `script_beauty_boost`.`control_envio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `script_beauty_boost`.`control_envio` (
  `id_control` INT NOT NULL AUTO_INCREMENT,
  `fecha_entrega` DATE NULL,
  `codigo_seguimiento` VARCHAR(45) NOT NULL,
  `estado_pedio_id_estado_pedio` INT NOT NULL,
  `empresas_envio_id_empresa_envio` INT NOT NULL,
  `pedido_id_pedido` INT NOT NULL,
  PRIMARY KEY (`id_control`),
  INDEX `fk_control_envio_estado_pedio1_idx` (`estado_pedio_id_estado_pedio` ASC),
  INDEX `fk_control_envio_empresas_envio1_idx` (`empresas_envio_id_empresa_envio` ASC),
  INDEX `fk_control_envio_pedido1_idx` (`pedido_id_pedido` ASC),
  CONSTRAINT `fk_control_envio_estado_pedio1`
    FOREIGN KEY (`estado_pedio_id_estado_pedio`)
    REFERENCES `script_beauty_boost`.`estado_pedido` (`id_estado_pedio`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_control_envio_empresas_envio1`
    FOREIGN KEY (`empresas_envio_id_empresa_envio`)
    REFERENCES `script_beauty_boost`.`empresas_envio` (`id_empresa_envio`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_control_envio_pedido1`
    FOREIGN KEY (`pedido_id_pedido`)
    REFERENCES `script_beauty_boost`.`pedido` (`id_pedido`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `script_beauty_boost`.`detalle_carrito`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `script_beauty_boost`.`detalle_carrito` (
  `id_detalle_carrito` INT NOT NULL AUTO_INCREMENT,
  `cantidad` INT NOT NULL,
  `descripcion` VARCHAR(45) NULL,
  `carrito_id_carrito` INT NOT NULL,
  `producto_id_producto` INT NOT NULL,
  PRIMARY KEY (`id_detalle_carrito`),
  INDEX `fk_detalle_carrito_carrito1_idx` (`carrito_id_carrito` ASC),
  INDEX `fk_detalle_carrito_producto1_idx` (`producto_id_producto` ASC),
  CONSTRAINT `fk_detalle_carrito_carrito1`
    FOREIGN KEY (`carrito_id_carrito`)
    REFERENCES `script_beauty_boost`.`cabeza_carrito` (`id_carrito`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_detalle_carrito_producto1`
    FOREIGN KEY (`producto_id_producto`)
    REFERENCES `script_beauty_boost`.`producto` (`id_producto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `script_beauty_boost`.`detalle_pedido`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `script_beauty_boost`.`detalle_pedido` (
  `id_detalle_pedido` INT NOT NULL AUTO_INCREMENT,
  `cantidad` INT NOT NULL,
  `precio_unitario` DECIMAL(10,2) NOT NULL,
  `producto_id_producto` INT NOT NULL,
  `pedido_id_pedido` INT NOT NULL,
  PRIMARY KEY (`id_detalle_pedido`),
  INDEX `fk_detalle_pedido_producto1_idx` (`producto_id_producto` ASC),
  INDEX `fk_detalle_pedido_pedido1_idx` (`pedido_id_pedido` ASC),
  CONSTRAINT `fk_detalle_pedido_producto1`
    FOREIGN KEY (`producto_id_producto`)
    REFERENCES `script_beauty_boost`.`producto` (`id_producto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_detalle_pedido_pedido1`
    FOREIGN KEY (`pedido_id_pedido`)
    REFERENCES `script_beauty_boost`.`pedido` (`id_pedido`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- =====================================================
-- INSERCIÓN DE DATOS MAESTROS (DML)
-- =====================================================

INSERT INTO `roles` (`id_rol`, `nombre`) VALUES
(1, 'Administrador'),
(2, 'Cliente');

INSERT INTO `estado_pedido` (`id_estado_pedio`, `descripcion_estado`) VALUES
(1, 'Pendiente'),
(2, 'En camino'),
(3, 'Entregado');

INSERT INTO `ciudades` (`descripcion_ciudad`, `codigo_postal`) VALUES
('Bogotá', '110111'),
('Medellín', '050001'),
('Cali', '760001'),
('Barranquilla', '080001'),
('Cartagena', '130001'),
('Bucaramanga', '680001'),
('Pereira', '660001'),
('Santa Marta', '470001'),
('Cúcuta', '540001'),
('Manizales', '170001');

INSERT INTO `tipo_usuario` (`nombre_tipo_usuario`) VALUES
('Administrador'),
('Cliente VIP'),
('Cliente Frecuente'),
('Cliente Nuevo'),
('Soporte'),
('Distribuidor'),
('Vendedor'),
('Gestor de Inventario'),
('Invitado'),
('Super Admin');

INSERT INTO `tipo_documento` (`descripcion_tipo_documento`) VALUES
('Cédula de Ciudadanía'),
('Cédula de Extranjería'),
('Pasaporte'),
('NIT'),
('Tarjeta de Identidad'),
('Registro Civil'),
('Documento Nacional de Identidad'),
('Permiso Especial de Permanencia'),
('Carné de Extranjería'),
('PEP');

INSERT INTO `categoria` (`id_categoria`, `nombre_categoria`) VALUES
(1, 'Cuidado Facial'),
(2, 'Maquillaje'),
(3, 'Cuidado Capilar'),
(4, 'Corporal'),
(5, 'Fragancias'),
(6, 'Protección Solar'),
(7, 'Uñas'),
(8, 'Herramientas de Belleza'),
(9, 'Skincare Vegano'),
(10, 'Accesorios'),
(11, 'Brochas'),
(12, 'Mas Vendidos'),
(13, 'Novedades');

INSERT INTO `usuario` (`nombre`, `apellido`, `numero_identificacion`, `telefono`, `correo`, `clave`, `fecha_nacimiento`, `fecha_vencimiento_clave`, `autorizacion_datos`, `tipo_documento_id_tipo_documento`, `tipo_usuario_id_tipo_usuario`, `roles_id_rol`) VALUES
('Admin', 'System', '9999999999', '3000000000', 'admin.beautyboost@mail.com', '$2a$12$cB8xZGCCsA9k0zU902opWuOIZgWnL.te5APvwo4T0w7vYZ.qZFGVC', '1990-01-01 00:00:00', '2030-12-31', 'SI', 1, 1, 1),
('Paula', 'Gómez', '1098765432', '3158889900', 'paula.cliente@mail.com', '$2a$12$3fR9gpX7oAE/5WJa6OtFGuLjQuNTpKtid6BzqkPVDvCR9mW8uSBPS', '1999-06-15 10:30:00', '2028-12-31', 'SI', 1, 4, 2),
('Laura', 'Gómez', '1018456789', '3001234567', 'laura.gomez@mail.com', '$2a$12$cB8xZGCCsA9k0zU902opWuOIZgWnL.te5APvwo4T0w7vYZ.qZFGVC', '1992-05-15 08:30:00', '2026-12-31', 'SI', 1, 1, 1),
('Carlos', 'Pérez', '1020304050', '3109876543', 'carlos.perez@mail.com', '$2a$12$3fR9gpX7oAE/5WJa6OtFGuLjQuNTpKtid6BzqkPVDvCR9mW8uSBPS', '1988-11-20 14:15:00', '2026-12-31', 'SI', 1, 2, 2),
('Ana', 'Martínez', '1030405060', '3204567890', 'ana.martinez@mail.com', '$2a$12$3fR9gpX7oAE/5WJa6OtFGuLjQuNTpKtid6BzqkPVDvCR9mW8uSBPS', '1995-02-10 10:00:00', '2026-12-31', 'SI', 2, 3, 2),
('María', 'Rodríguez', '1040506070', '3012345678', 'maria.rodriguez@mail.com', '$2a$12$3fR9gpX7oAE/5WJa6OtFGuLjQuNTpKtid6BzqkPVDvCR9mW8uSBPS', '2000-07-25 18:45:00', '2026-12-31', 'SI', 1, 4, 2),
('Jorge', 'López', '1050607080', '3118765432', 'jorge.lopez@mail.com', '$2a$12$3fR9gpX7oAE/5WJa6OtFGuLjQuNTpKtid6BzqkPVDvCR9mW8uSBPS', '1985-09-12 11:20:00', '2026-12-31', 'SI', 3, 2, 2),
('Sofia', 'Hernández', '1060708090', '3213456789', 'sofia.hernandez@mail.com', '$2a$12$3fR9gpX7oAE/5WJa6OtFGuLjQuNTpKtid6BzqkPVDvCR9mW8uSBPS', '1998-03-30 16:10:00', '2026-12-31', 'SI', 1, 3, 2),
('David', 'Díaz', '1070809010', '3029876543', 'david.diaz@mail.com', '$2a$12$3fR9gpX7oAE/5WJa6OtFGuLjQuNTpKtid6BzqkPVDvCR9mW8uSBPS', '1991-12-05 09:00:00', '2026-12-31', 'SI', 2, 4, 2),
('Elena', 'Torres', '1080901020', '3124567890', 'elena.torres@mail.com', '$2a$12$3fR9gpX7oAE/5WJa6OtFGuLjQuNTpKtid6BzqkPVDvCR9mW8uSBPS', '1994-08-18 13:50:00', '2026-12-31', 'SI', 1, 2, 2);

INSERT INTO `direccion_envio` (`direccion_envio`, `usuario_id_usuario`, `ciudades_id_ciudades`) VALUES
('Calle 100 # 15-20', 1, 1),
('Carrera 43A # 1-50', 2, 2),
('Avenida 6N # 22-10', 3, 3),
('Calle 84 # 52-08', 4, 4),
('Carrera 7 # 32-12', 5, 1),
('Calle 50 # 13-45', 6, 6),
('Avenida El Poblado # 10-12', 7, 2),
('Carrera 27 # 45-09', 8, 7),
('Calle 30 # 18-22', 9, 5),
('Carrera 5 # 12-80', 10, 10);

INSERT INTO `resena_usuario` (`observacion`, `calificacion`, `fecha`, `usuario_id_usuario`) VALUES
('Excelente atención y productos', 5, '2026-08-01 10:00:00', 1),
('El envío llegó a tiempo', 4, '2026-08-02 11:30:00', 2),
('Buena relación calidad precio', 4, '2026-08-03 14:20:00', 3),
('El empaque llegó un poco dañado', 3, '2026-08-04 16:00:00', 4),
('Me encantan los productos de piel', 5, '2026-08-05 09:15:00', 5),
('Atención rápida por soporte', 5, '2026-08-06 12:45:00', 6),
('Podrían mejorar el tiempo de entrega', 3, '2026-08-07 15:10:00', 7),
('Productos 100% recomendados', 5, '2026-08-08 18:30:00', 8),
('Todo perfecto', 5, '2026-08-09 08:05:00', 9),
('No tenían stock de mi producto favorito', 2, '2026-08-10 13:00:00', 10);

INSERT INTO `cabeza_carrito` (`fecha_creacion`, `fecha_actualizacion`) VALUES
('2026-08-01', '2026-08-01'),
('2026-08-02', '2026-08-02'),
('2026-08-03', '2026-08-04'),
('2026-08-04', '2026-08-05'),
('2026-08-05', '2026-08-05'),
('2026-08-06', '2026-08-06'),
('2026-08-07', '2026-08-08'),
('2026-08-08', '2026-08-08'),
('2026-08-09', '2026-08-09'),
('2026-08-10', '2026-08-11');

-- =====================================================
-- INSERCIÓN DE PRODUCTOS CON RUTAS NORMALIZADAS
-- =====================================================

-- Categoría 2: Maquillaje
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Contorno oscuro en barra', 'Contorno en crema', 30000.00, 45, 'img/maquillaje_contorno_oscuro_en_barra.png', 'Activo', 2);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Fijador de maquillaje hidratante', 'Producto de maquillaje', 32000.00, 60, 'img/maquillaje_fijador_de_maquillaje_hidratante.png', 'Activo', 2);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Base líquida matificante', 'Base mate alta cobertura', 45000.00, 50, 'img/maquillaje_base_liquida_matificante.png', 'Activo', 2);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Lápiz de cejas retráctil', 'Lápiz para cejas', 20000.00, 70, 'img/maquillaje_lapiz_de_cejas_retractil.png', 'Activo', 2);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Labial Rojo Mate', 'Labial de larga duración', 35000.00, 100, 'img/maquillaje_labial_liquido_mate_fijo.png', 'Activo', 2);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Bronzer compacto opaco', 'Polvo bronceador', 33000.00, 40, 'img/maquillaje_bronzer_compacto_opaco.png', 'Activo', 2);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Prebase facial alisadora', 'Primer facial', 42000.00, 40, 'img/maquillaje_prebase_facial_alisadora.png', 'Activo', 2);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Paleta de sombras neutras', 'Sombras 12 tonos', 62000.00, 30, 'img/maquillaje_paleta_de_sombras_neutras.png', 'Activo', 2);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Corrector líquido de ojeras', 'Corrector ojeras', 28000.00, 60, 'img/maquillaje_corrector_liquido_de_ojeras.png', 'Activo', 2);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Rubor en crema rosado', 'Rubor cremoso', 27000.00, 50, 'img/maquillaje_rubor_en_crema_rosado.png', 'Activo', 2);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Delineador líquido negro', 'Delineador ojos mate', 24000.00, 50, 'img/maquillaje_delineador_liquido_negro.png', 'Activo', 2);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Iluminador líquido dorado', 'Iluminador facial', 29000.00, 35, 'img/maquillaje_iluminador_liquido_dorado.png', 'Activo', 2);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Máscara de pestañas alargadora', 'Pestañina negra', 31000.00, 80, 'img/maquillaje_mascara_de_pestanas_alargadora.png', 'Activo', 2);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Polvo traslúcido suelto', 'Polvo fijador', 38000.00, 65, 'img/maquillaje_polvo_traslucido_suelto.png', 'Activo', 2);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Gel transparente de cejas', 'Fijador de cejas', 21000.00, 55, 'img/maquillaje_gel_transparente_de_cejas.png', 'Activo', 2);
-- Categoría 13: Novedades
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Base infusionada con suero antiedad', 'Base antiedad con suero', 65000.00, 30, 'img/novedades_base_infusionada_con_suero_antiedad.png', 'Activo', 13);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Sombras líquidas duocromáticas', 'Sombras tornasol', 33000.00, 40, 'img/novedades_sombras_liquidas_duocromaticas.png', 'Activo', 13);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Corrector líquido alta cobertura total', 'Corrector alta cobertura', 32000.00, 50, 'img/novedades_corrector_liquido_alta_cobertura_total.png', 'Activo', 13);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Tinta de labios efecto peeling', 'Tinta duradera labios', 26000.00, 60, 'img/novedades_tinta_de_labios_efecto_peeling.png', 'Activo', 13);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Sérum facial en barra hidratante', 'Sérum barra facial', 49000.00, 30, 'img/novedades_serum_facial_en_barra_hidratante.png', 'Activo', 13);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Base líquida mate larga duración', 'Base líquida mate', 48000.00, 40, 'img/novedades_base_liquida_mate_larga_duracion.png', 'Activo', 13);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Parches con microagujas para arrugas', 'Parches faciales', 45000.00, 25, 'img/novedades_parches_con_microagujas_para_arrugas.png', 'Activo', 13);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Gel transparente fijación cejas extrema', 'Gel cejas ultra fijación', 25000.00, 45, 'img/novedades_gel_transparente_fijacion_cejas_extrema.png', 'Activo', 13);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Máscara pestañas efecto pestañas postizas', 'Pestañina volumen', 34000.00, 50, 'img/novedades_mascara_pestanas_efecto_pestanas_postizas.png', 'Activo', 13);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Máscara de pestañas cepillo giratorio', 'Pestañina innovadora', 36000.00, 40, 'img/novedades_mascara_de_pestanas_cepillo_giratorio.png', 'Activo', 13);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Polvo traslúcido fijador universal mate', 'Polvo mate fijador', 39000.00, 55, 'img/novedades_polvo_traslucido_fijador_universal_mate.png', 'Activo', 13);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Delineador con sello cat-eye integrado', 'Delineador cat eye', 27000.00, 60, 'img/novedades_delineador_con_sello_cat_eye_integrado.png', 'Activo', 13);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Protector solar invisible en barra', 'Protector barra FPS 50', 58000.00, 45, 'img/novedades_protector_solar_invisible_en_barra.png', 'Activo', 13);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Rubor inteligente que cambia según pH', 'Rubor reactivo pH', 31000.00, 50, 'img/novedades_rubor_inteligente_que_cambia_segun_ph.png', 'Activo', 13);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Iluminador corporal en gel brillante', 'Iluminador corporal', 39000.00, 35, 'img/novedades_iluminador_corporal_en_gel_brillante.png', 'Activo', 13);
-- Categoría 1: Cuidado Facial
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Gel limpiador espumoso suave', 'Gel limpiador facial', 41000.00, 60, 'img/cuidado_facial_gel_limpiador_espumoso_suave.png', 'Activo', 1);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Agua termal en spray', 'Agua termal hidratante', 45000.00, 30, 'img/cuidado_facial_agua_termal_en_spray.png', 'Activo', 1);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Agua micelar desmaquillante bifásica', 'Agua micelar 200ml', 32000.00, 40, 'img/cuidado_facial_agua_micelar_desmaquillante_bifasica.png', 'Activo', 1);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Mascarilla de tela ultra hidratante', 'Producto de cuidado facial', 45000.00, 45, 'img/cuidado_facial_mascarilla_de_tela_ultra_hidratante.png', 'Activo', 1);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Sérum de retinol nocturno', 'Producto de cuidado facial', 45000.00, 45, 'img/cuidado_facial_serum_de_retinol_nocturno.png', 'Activo', 1);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Sérum de ácido hialurónico', 'Sérum hidratante con ácido hialurónico', 85000.00, 50, 'img/cuidado_facial_serum_de_acido_hialuronico.png', 'Activo', 1);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Crema de noche reparadora', 'Crema de noche nutitiva', 68000.00, 25, 'img/cuidado_facial_crema_de_noche_reparadora.png', 'Activo', 1);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Exfoliante químico con AHA', 'Exfoliante químico suave', 59000.00, 35, 'img/cuidado_facial_exfoliante_quimico_con_aha.png', 'Activo', 1);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Sérum de vitamina C', 'Producto de cuidado facial', 45000.00, 45, 'img/cuidado_facial_serum_de_vitamina_c.png', 'Activo', 1);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Protector solar FPS 50', 'Producto de cuidado facial', 45000.00, 45, 'img/cuidado_facial_protector_solar_fps_50.png', 'Activo', 1);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Parches hidrocoloides para granitos', 'Producto de cuidado facial', 45000.00, 45, 'img/cuidado_facial_parches_hidrocoloides_para_granitos.png', 'Activo', 1);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Tónico Facial', 'Tónico con ácido hialurónico', 55000.00, 45, 'img/cuidado_facial_tonico_facial_hidratante_calmante.png', 'Activo', 1);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Mascarilla de arcilla purificante', 'Producto de cuidado facial', 45000.00, 45, 'img/cuidado_facial_mascarilla_de_arcilla_purificante.png', 'Activo', 1);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Crema para contorno de ojos', 'Producto de cuidado facial', 45000.00, 45, 'img/cuidado_facial_crema_para_contorno_de_ojos.png', 'Activo', 1);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Spray limpiador instantáneo sin enjuague', 'Producto de cuidado facial', 45000.00, 45, 'img/cuidado_facial_spray_limpiador_instantaneo_sin_enjuague.png', 'Activo', 1);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Crema hidratante ligera diaria', 'Crema facial diaria', 52000.00, 50, 'img/cuidado_facial_crema_hidratante_ligera_diaria.png', 'Activo', 1);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Brocha lengua de gato para base', 'Producto de cuidado facial', 45000.00, 45, 'img/cuidado_facial_brocha_lengua_de_gato_para_base.png', 'Activo', 1);
-- Categoría 11: Brochas
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Pincel fino para delinear ojos', 'Pincel eyeliner', 14000.00, 85, 'img/brochas_pincel_fino_para_delinear_ojos.png', 'Activo', 11);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Brocha cónica para iluminador preciso', 'Brocha iluminador', 26000.00, 40, 'img/brochas_brocha_conica_para_iluminador_preciso.png', 'Activo', 11);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Esponja de maquillaje de gota', 'Esponja difuminadora', 18000.00, 100, 'img/brochas_esponja_de_maquillaje_de_gota.png', 'Activo', 11);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Pincel plano para aplicar corrector', 'Pincel corrector', 17000.00, 60, 'img/brochas_pincel_plano_para_aplicar_corrector.png', 'Activo', 11);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Pincel angular para diseñar cejas', 'Pincel de cejas', 16000.00, 75, 'img/brochas_pincel_angular_para_disenar_cejas.png', 'Activo', 11);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Borla de felpa para polvos', 'Borla suave para maquillaje', 15000.00, 90, 'img/brochas_borla_de_felpa_para_polvos.png', 'Activo', 11);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Brocha grande para polvos sueltos', 'Brocha polvos', 32000.00, 60, 'img/brochas_brocha_grande_para_polvos_sueltos.png', 'Activo', 11);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Pincel plano para depositar sombras', 'Pincel sombras', 16000.00, 65, 'img/brochas_pincel_plano_para_depositar_sombras.png', 'Activo', 11);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Brocha angular para contorno definido', 'Brocha de contorno', 28000.00, 50, 'img/brochas_brocha_angular_para_contorno_definido.png', 'Activo', 11);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Brocha mofeta para acabados ligeros', 'Brocha mofeta', 31000.00, 35, 'img/brochas_brocha_mofeta_para_acabados_ligeros.png', 'Activo', 11);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Brocha suave para aplicar rubor', 'Brocha rubor', 27000.00, 50, 'img/brochas_brocha_suave_para_aplicar_rubor.png', 'Activo', 11);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Brocha kabuki de tope plano', 'Brocha base', 34000.00, 45, 'img/brochas_brocha_kabuki_de_tope_plano.png', 'Activo', 11);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Pincel difuminador ancho para transición', 'Pincel sombras', 19000.00, 80, 'img/brochas_pincel_difuminador_ancho_para_transicion.png', 'Activo', 11);
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Brocha lengua de gato para base', 'Brocha clásica base', 29000.00, 55, 'img/brochas_brocha_lengua_de_gato_para_base.png', 'Activo', 11);
-- Categoría 3: Cuidado Capilar
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Champú Sin Sulfatos', 'Champú hidratante 500ml', 48000.00, 40, 'img/default.jpg', 'Activo', 3);
-- Categoría 4: Corporal
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Crema Hidratante', 'Crema corporal de almendras', 42000.00, 60, 'img/default.jpg', 'Activo', 4);
-- Categoría 5: Fragancias
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Perfume Floral', 'Eau de Parfum 100ml', 180000.00, 20, 'img/default.jpg', 'Activo', 5);
-- Categoría 6: Protección Solar
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Protector Solar FPS 50', 'Protector toque seco 80g', 65000.00, 80, 'img/default.jpg', 'Activo', 6);
-- Categoría 7: Uñas
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Esmalte Gel', 'Esmalte secado rápido', 15000.00, 150, 'img/default.jpg', 'Activo', 7);
-- Categoría 8: Herramientas de Belleza
INSERT INTO `producto` (`nombre_prod`, `descripcion_prod`, `precio`, `stock`, `imagen_url`, `estado_producto`, `categoria_id_categoria`) VALUES ('Rodillo de Cuarzo', 'Masajeador facial', 30000.00, 30, 'img/default.jpg', 'Activo', 8);

-- =====================================================
-- NORMALIZACIÓN Y CONFIGURACIÓN FINAL
-- =====================================================
UPDATE producto
SET estado_producto = 'Activo'
WHERE id_producto > 0
  AND (estado_producto IS NULL OR TRIM(estado_producto) = '');

UPDATE producto
SET imagen_url = 'img/default.jpg'
WHERE id_producto > 0
  AND (imagen_url IS NULL OR TRIM(imagen_url) = '');

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;