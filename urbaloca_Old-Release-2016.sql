/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 100137
Source Host           : localhost:3306
Source Database       : urbaloca_old

Target Server Type    : MYSQL
Target Server Version : 100137
File Encoding         : 65001

Date: 2019-06-29 14:53:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for banip
-- ----------------------------
DROP TABLE IF EXISTS `banip`;
CREATE TABLE `banip` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `ip` varchar(255) NOT NULL,
  `tiempo` varchar(255) NOT NULL,
  `motivo` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of banip
-- ----------------------------

-- ----------------------------
-- Table structure for catalogo
-- ----------------------------
DROP TABLE IF EXISTS `catalogo`;
CREATE TABLE `catalogo` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `categoria` varchar(50) NOT NULL,
  `mueble` varchar(255) NOT NULL,
  `descripcion` text NOT NULL,
  `precio` varchar(50) NOT NULL,
  `miniatura` text NOT NULL,
  `imagen` text NOT NULL,
  `tipo` int(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of catalogo
-- ----------------------------
INSERT INTO `catalogo` VALUES ('1', '1', 'Cubo', 'Cubo 1 test', '1', '1.png', '1_', '3');
INSERT INTO `catalogo` VALUES ('2', '5', 'Planta', 'Queda muy decorativa.', '3', '2.png', '2_', '2');
INSERT INTO `catalogo` VALUES ('3', '4', 'Nevera', 'Para guardar los refrescos', '2', '3.png', '3_', '2');
INSERT INTO `catalogo` VALUES ('4', '2', 'Mesa de madera', 'Tambien vale de separador.', '2', '4.png', '4_', '2');
INSERT INTO `catalogo` VALUES ('5', '1', 'Trofeo de Oro', 'Para los ganadores.', '10', '5.png', '5_', '2');
INSERT INTO `catalogo` VALUES ('6', '1', 'Trofeo de Plata', 'Para los 2dos ganadores.', '5', '6.png', '6_', '2');
INSERT INTO `catalogo` VALUES ('7', '1', 'Trofeo de Bronce', 'Para los 3ros ganadores.', '3', '7.png', '7_', '2');
INSERT INTO `catalogo` VALUES ('8', '2', 'Separador azul', 'Para tener intimidad.', '2', '8.png', '8_', '2');

-- ----------------------------
-- Table structure for catalogo_categorias
-- ----------------------------
DROP TABLE IF EXISTS `catalogo_categorias`;
CREATE TABLE `catalogo_categorias` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of catalogo_categorias
-- ----------------------------
INSERT INTO `catalogo_categorias` VALUES ('1', 'Trofeos');
INSERT INTO `catalogo_categorias` VALUES ('2', 'Estantes');
INSERT INTO `catalogo_categorias` VALUES ('3', 'Sillas');
INSERT INTO `catalogo_categorias` VALUES ('4', 'Exclusivos');
INSERT INTO `catalogo_categorias` VALUES ('5', 'Extras');

-- ----------------------------
-- Table structure for clima
-- ----------------------------
DROP TABLE IF EXISTS `clima`;
CREATE TABLE `clima` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `estado` varchar(250) NOT NULL,
  `desc` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of clima
-- ----------------------------
INSERT INTO `clima` VALUES ('1', 'Soleado', 'Hace un dia tranquilo y agradable en Urbaloca, disfrútalo!');
INSERT INTO `clima` VALUES ('2', 'Nublado', 'Parece ser que las nubes nos visitan, pero no hay previsión de lluvia.');
INSERT INTO `clima` VALUES ('3', 'Lluvioso', 'Están cayendo algunas gotas por Urbaloca, pero que el tiempo no impida la diversión!');
INSERT INTO `clima` VALUES ('4', 'Nevando', 'Vaya vaya! Parece que la nieve aparece en la urbanización, a jugar!');
INSERT INTO `clima` VALUES ('5', 'Tormenta', 'La lluvia ha ido a más y atravesamos una pequeña tormenta, esperemos que pase rápido!');
INSERT INTO `clima` VALUES ('6', 'Viento fuerte', 'El viento, procedente del norte viene con rachas elevadas, id con cuidado!');

-- ----------------------------
-- Table structure for concursos
-- ----------------------------
DROP TABLE IF EXISTS `concursos`;
CREATE TABLE `concursos` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `titulo` text NOT NULL,
  `fecha` varchar(255) NOT NULL,
  `texto` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of concursos
-- ----------------------------

-- ----------------------------
-- Table structure for contador
-- ----------------------------
DROP TABLE IF EXISTS `contador`;
CREATE TABLE `contador` (
  `id` int(50) NOT NULL,
  `ip` varchar(255) NOT NULL,
  `fecha` varchar(255) NOT NULL,
  `hora` varchar(255) NOT NULL,
  `horau` varchar(255) NOT NULL,
  `diau` varchar(255) NOT NULL,
  `aniou` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of contador
-- ----------------------------

-- ----------------------------
-- Table structure for mantenimiento
-- ----------------------------
DROP TABLE IF EXISTS `mantenimiento`;
CREATE TABLE `mantenimiento` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `estado_web` varchar(250) NOT NULL DEFAULT '1',
  `estado_cliente` varchar(250) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mantenimiento
-- ----------------------------
INSERT INTO `mantenimiento` VALUES ('1', '1', '1');

-- ----------------------------
-- Table structure for noticias
-- ----------------------------
DROP TABLE IF EXISTS `noticias`;
CREATE TABLE `noticias` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `titulo` text NOT NULL,
  `fecha` varchar(255) NOT NULL,
  `descripcion` text NOT NULL,
  `texto` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of noticias
-- ----------------------------
INSERT INTO `noticias` VALUES ('1', 'Bienvenido a Urbaloca', '16/05/2013', '<p>&iexcl;Hey Loko,&nbsp;<strong>bienvenido a Urbaloca</strong>!&nbsp;Como habr&aacute;s podido observar, hemos reabierto UrbaLoca con un nuevo dise&ntilde;o, un s<strong>ervidor 24 horas</strong>, un juego&nbsp;<strong>propio</strong>,&nbsp;<strong>muebles</strong>,&nbsp;<strong>salas</strong>.... estamos<strong>&nbsp;trabajando dia a dia</strong>&nbsp;para actualizar UL para que disfrutes al maximo de todos nuestros servicios...</p>', '<p>&iexcl;Hey Loko, <strong>bienvenido a Urbaloca</strong>!</p>\r\n<p>Como habr&aacute;s podido observar, hemos reabierto UrbaLoca con un nuevo dise&ntilde;o, un s<strong>ervidor 24 horas</strong>, un juego <strong>propio</strong>, <strong>muebles</strong>, <strong>salas</strong>.... estamos<strong> trabajando dia a dia</strong> para actualizar UL para que disfrutes al maximo de todos nuestros servicios.</p>\r\n<p>Ya puedes comprar <strong>muebles</strong>, hacerte socio del <strong>club vip</strong> o, simplemente, <strong>hacer amigos</strong> dentro de la Urba.&nbsp;</p>\r\n<p><em>&iexcl;Un saludo loko!</em></p>');
INSERT INTO `noticias` VALUES ('2', 'Ya puedes pintar tu sala en Urbaloca', '18/05/2013', '<p>Hola loko! Ya puedes pintar tu sala en UrbaLoca y personalizarla al m&aacute;ximo!</p>', '<p>&iexcl;Hola Loko!</p>\r\n<p>Ya puedes <strong>pintar tu sala</strong> en <strong>UrbaLoca</strong> y personalizarla al m&aacute;ximo! Para ello,<strong> ve al cat&aacute;logo</strong> y haz click <strong>aqu&iacute;</strong>:</p>\r\n<p><img src=\"img/pintarsala2.png\" alt=\"\" width=\"350\" /></p>\r\n<p>Selecciona tus colores favoritos, <strong>son gratis</strong>! Disfruta de estas actualizaciones, y a pintar!</p>');
INSERT INTO `noticias` VALUES ('3', 'Nuevos cambios en UrbaLoca', '20/05/2013', '<p>&iexcl;Hola loko!&nbsp;Hemos visto que a<strong>lgunos usuarios han reportado recientes bugs</strong>&nbsp;en&nbsp;<strong>UrbaLoca</strong>; esto se debe a que&nbsp;<strong>hemos programado un nuevo sistema de Log-in</strong>&nbsp;para entrar a la urbanizaci&oacute;n, ahora no deber&aacute;s tener ning&uacute;n problema con los accesos...</p>', '<p>&iexcl;Hola loko!</p>\r\n<p>Hemos visto que a<strong>lgunos usuarios han reportado recientes bugs</strong> en <strong>UrbaLoca</strong>; esto se debe a que <strong>hemos programado un nuevo sistema de Log-in</strong> para entrar a la urbanizaci&oacute;n, ahora no deber&aacute;s tener ning&uacute;n problema con los accesos.</p>\r\n<p><strong>&iexcl;Recuerda iniciar sesi&oacute;n en la Web para entrar!</strong></p>\r\n<p>Tambien hemos implementado las <strong>nuevas ropas</strong> en UL, ahora tu loko ya no ir&aacute; desnudo.</p>\r\n<p>Aprobecho tambien para dar las felicitaciones a <strong>Antonio285</strong>, el nuevo <strong>Gu&iacute;a de la urbanizaci&oacute;n</strong>.</p>');
INSERT INTO `noticias` VALUES ('4', 'Se han reiniciado las fichas', '21/05/2013', '<p>Hola a todos, en estos momentos estamos arreglando algunos bugs en UrbaLoca...</p>', '<p>Hola a todos,</p>\r\n<p>En estos momentos, estamos arreglando algunos bugs en el navegador. Un ejemplo de &eacute;stas actualizaciones ser&aacute;:</p>\r\n<ul>\r\n<li>La posibilidad de establecer <strong>contrase&ntilde;a</strong> a tus salas</li>\r\n<li>No podr&aacute;s crear salas <strong>sin nombre</strong></li>\r\n<li>Activacion de los<strong> usuarios en sala </strong><em>(0/25)</em></li>\r\n<li>Arreglos en el <strong>navegador</strong><em> (se duplica)</em></li>\r\n</ul>\r\n<p><em>Te recordamos que estos cambios van a ser introducidos de forma paulatina, no te asustes si en las pr&oacute;ximas horas est&aacute; el servidor cerrado, pues nuestro equipo est&aacute; trabajando en la mejora de la urbanizaci&oacute;n.</em></p>');
INSERT INTO `noticias` VALUES ('5', 'La versión 1 cada vez más cerca', '23/05/2013', '<p>Hola loko!&nbsp;Cada vez est&aacute; m&aacute;s cerca la versi&oacute;n&nbsp;<strong>Estable</strong>&nbsp;de UrbaLoca. Desde que ha salido la beta, muchos de vosotros habeis&nbsp;<strong>colaborado reportando bugs y errores</strong>&nbsp;encontrados. Como habr&eacute;is podido observar, el dise&ntilde;o se encuentra en fase Beta...</p>', '<p>Hola loko!</p>\r\n<p>Cada vez est&aacute; m&aacute;s cerca la versi&oacute;n <strong>Estable</strong> de UrbaLoca. Desde que ha salido la beta, muchos de vosotros habeis <strong>colaborado reportando bugs y errores</strong> encontrados. Como habr&eacute;is podido observar, el dise&ntilde;o se encuentra en fase Beta.</p>\r\n<p>Desde UrbaLoca estamos <strong>preparando la nueva versi&oacute;n Estable</strong>, que incluir&aacute; las siguientes<strong> funciones</strong> nuevas:</p>\r\n<p>Dentro de la<strong> Web</strong>:</p>\r\n<ul>\r\n<li>Un nuevo<strong> dise&ntilde;o Web</strong>, con nuevas funciones</li>\r\n<li>Posibilidad de<strong> elegir ropa</strong> en el <strong>registro</strong> de usuarios</li>\r\n<li>Panel de Mi cuenta mejorado, posibilidad de <strong>cambiar la placa a traves de la web</strong>.</li>\r\n<li>Cambio de<strong> datos personales</strong> (contrase&ntilde;a, email, fecha de nacimiento...)</li>\r\n<li><strong>Cat&aacute;logo Web</strong> mejorado</li>\r\n<li>Sistema de <strong>denuncias/ayuda</strong> propio y mejorado</li>\r\n<li>Se abrir&aacute;n las <strong>solicitudes de Gu&iacute;a</strong></li>\r\n<li>Sistema de<strong> referidos</strong>, con el que podr&aacute;s <strong>ganar loks por conseguir registrados</strong></li>\r\n<li>Y m&aacute;s sorpresas :)</li>\r\n</ul>\r\n<p>Dentro de la <strong>Urbanizaci&oacute;n</strong>:</p>\r\n<ul>\r\n<li>Nueva<strong> interfaz gr&aacute;fica</strong></li>\r\n<li>Posibilidad de<strong> poner contrase&ntilde;a a tus salas</strong></li>\r\n<li>Se activar&aacute; el<strong> contador de usuarios en sala</strong> (0/25)</li>\r\n<li>Una <strong>zona p&uacute;blica</strong> para uso y disfrute de todos</li>\r\n<li>M&aacute;s<strong> muebles y placas</strong></li>\r\n<li>Regalos por ser<strong> miembro del club Vip</strong></li>\r\n<li>Nuevo sistema de<strong> ayuda</strong> dentro del juego</li>\r\n<li>Ayuda para llamar a un Gu&iacute;a</li>\r\n<li>Ropas de<strong> chica</strong></li>\r\n<li>Y lo dem&aacute;s, es un secreto :)</li>\r\n</ul>\r\n<p>Espero que estas noticias os vayan abriendo el apetito...<strong> &iexcl;y estad atentos de la web!</strong></p>');
INSERT INTO `noticias` VALUES ('6', 'Ya falta menos para el gran estreno', '25/05/2013', '<p>Ya falta cada vez menos para el estreno de la nueva web, junto con todas las funciones que ello conlleva. Estamos trabajando duro para...</p>', '<p>Ya falta cada vez menos para el estreno de la nueva web, junto con todas las funciones que ello conlleva. Estamos trabajando duro para que puedas disfrutar de la version Stable lo antes posible.</p>\r\n<p>&iexcl;&iexcl;Nos vemos en la urba!!</p>');

-- ----------------------------
-- Table structure for publicas
-- ----------------------------
DROP TABLE IF EXISTS `publicas`;
CREATE TABLE `publicas` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  `max` varchar(50) NOT NULL DEFAULT '50',
  `cerrada` varchar(50) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of publicas
-- ----------------------------

-- ----------------------------
-- Table structure for rooms
-- ----------------------------
DROP TABLE IF EXISTS `rooms`;
CREATE TABLE `rooms` (
  `id` int(17) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `owner` varchar(255) NOT NULL,
  `maxu` varchar(255) NOT NULL,
  `muebles` text NOT NULL,
  `color` varchar(50) NOT NULL DEFAULT '1',
  `suelo` int(50) NOT NULL DEFAULT '1',
  `pass` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of rooms
-- ----------------------------
INSERT INTO `rooms` VALUES ('19', 'Sala de prueba', 'escavo', '25', '2;1;4;6::2;1;10;2::1;2;8;8::3;1;0;7::5;1;1;0::1;1;4;0::2;1;0;2', '1', '1', '');

-- ----------------------------
-- Table structure for useronline
-- ----------------------------
DROP TABLE IF EXISTS `useronline`;
CREATE TABLE `useronline` (
  `timestamp` varchar(255) NOT NULL,
  `ip` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of useronline
-- ----------------------------
INSERT INTO `useronline` VALUES ('1561573853', '::1');
INSERT INTO `useronline` VALUES ('1561573854', '::1');
INSERT INTO `useronline` VALUES ('1561573977', '::1');
INSERT INTO `useronline` VALUES ('1561574001', '::1');
INSERT INTO `useronline` VALUES ('1561574011', '::1');
INSERT INTO `useronline` VALUES ('1561574018', '::1');
INSERT INTO `useronline` VALUES ('1561574019', '::1');
INSERT INTO `useronline` VALUES ('1561574019', '::1');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(17) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  `pass` varchar(255) NOT NULL,
  `fnac` varchar(255) NOT NULL,
  `baneado` int(2) NOT NULL DEFAULT '0',
  `fechaban` varchar(255) NOT NULL,
  `motivo` text NOT NULL,
  `ip` varchar(255) NOT NULL,
  `rango` varchar(100) NOT NULL DEFAULT 'USR',
  `fichas` varchar(255) NOT NULL DEFAULT '0',
  `email` varchar(255) NOT NULL,
  `activado` varchar(50) NOT NULL DEFAULT '0',
  `codeact` varchar(255) NOT NULL,
  `placas` text NOT NULL,
  `puesta` varchar(255) NOT NULL,
  `visible` varchar(5) NOT NULL DEFAULT '1',
  `nuevo` varchar(25) NOT NULL DEFAULT '1',
  `vip` varchar(50) NOT NULL,
  `muebles` text NOT NULL,
  `pelo` varchar(200) NOT NULL DEFAULT '1',
  `camisa` varchar(200) NOT NULL DEFAULT '1',
  `pants` varchar(200) NOT NULL DEFAULT '1',
  `zapatos` varchar(200) NOT NULL DEFAULT '1',
  `logueado` int(50) NOT NULL DEFAULT '0',
  `idserver` varchar(255) NOT NULL DEFAULT '0',
  `mision` varchar(255) NOT NULL DEFAULT 'No tengo mision',
  `estoyen` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', 'test', 'fb469d7ef430b0baf0cab6c436e70375', '', '0', '', '', '/127.0.0.1', 'ADM', '809', 'tu_correo@test.com', '1', '', 'ADM;GUI', 'ADM', '1', '1', '', '1;5::2;2::3;1', '2', '8', '1', '2', '0', '', 'No tengo mision', '');
SET FOREIGN_KEY_CHECKS=1;
