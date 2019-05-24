-- phpMyAdmin SQL Dump
-- version 4.5.2
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 24-05-2019 a las 22:05:00
-- Versión del servidor: 10.1.13-MariaDB
-- Versión de PHP: 5.6.20

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `estudioAlvarez`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Agenda`
--

CREATE TABLE `Agenda` (
  `idAgenda` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `descripcion` varchar(1000) NOT NULL,
  `nombreYapellido` varchar(1000) DEFAULT NULL,
  `responsable` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `Agenda`
--

INSERT INTO `Agenda` (`idAgenda`, `fecha`, `descripcion`, `nombreYapellido`, `responsable`) VALUES
(35, '2019-05-08', 'aasasda', 'descripcion nueva', 'Cuti cuti'),
(36, '2019-05-16', 'crear una actividad para el cliente', 'juan cuello 1', 'Mateo ');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Empleado`
--

CREATE TABLE `Empleado` (
  `idEmpleado` int(11) NOT NULL,
  `Nombre` varchar(100) DEFAULT NULL,
  `Apellido` varchar(100) DEFAULT NULL,
  `Cargo` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `Empleado`
--

INSERT INTO `Empleado` (`idEmpleado`, `Nombre`, `Apellido`, `Cargo`) VALUES
(3, 'Mateo', '', 'administrativo'),
(4, 'Cuti', 'cuti', 'Administrativo'),
(5, 'juan', 'cuello', 'infomático');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Expediente`
--

CREATE TABLE `Expediente` (
  `idExpediente` int(11) NOT NULL,
  `orden` int(11) NOT NULL,
  `cuit` varchar(200) NOT NULL,
  `dni` varchar(200) DEFAULT NULL,
  `nombre` varchar(100) NOT NULL,
  `tipoDeDocumento` varchar(200) DEFAULT NULL,
  `sexo` varchar(100) NOT NULL,
  `apellido` varchar(100) NOT NULL,
  `direccion` varchar(100) NOT NULL,
  `nroDeAltura` int(11) NOT NULL,
  `piso` varchar(100) DEFAULT NULL,
  `depto` varchar(100) DEFAULT NULL,
  `barrio` varchar(100) DEFAULT NULL,
  `telefono` varchar(200) NOT NULL,
  `fechaDeNacimiento` date DEFAULT NULL,
  `edad` int(11) DEFAULT NULL,
  `claveSeguridadSocial` varchar(200) DEFAULT NULL,
  `claveFiscal` varchar(200) DEFAULT NULL,
  `claveCidi` varchar(200) DEFAULT NULL,
  `cobraBeneficio` varchar(5) DEFAULT NULL,
  `fechaDeAltaDeExpediente` date DEFAULT NULL,
  `codigoPostal` varchar(150) DEFAULT NULL,
  `localidad` varchar(150) DEFAULT NULL,
  `tipoDeTramite` varchar(250) DEFAULT NULL,
  `procedencia` varchar(500) DEFAULT NULL,
  `estadoDelTramite` varchar(500) DEFAULT NULL,
  `fechaDeCobro` date DEFAULT NULL,
  `nacionalidad` varchar(250) DEFAULT NULL,
  `tipoDeExpediente` varchar(150) DEFAULT NULL,
  `caratula` varchar(500) DEFAULT NULL,
  `nroDeExpediente` varchar(500) DEFAULT NULL,
  `juzgadoODependencia` varchar(500) DEFAULT NULL,
  `observaciones` varchar(800) DEFAULT NULL,
  `fechaDeAtencion` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `Expediente`
--

INSERT INTO `Expediente` (`idExpediente`, `orden`, `cuit`, `dni`, `nombre`, `tipoDeDocumento`, `sexo`, `apellido`, `direccion`, `nroDeAltura`, `piso`, `depto`, `barrio`, `telefono`, `fechaDeNacimiento`, `edad`, `claveSeguridadSocial`, `claveFiscal`, `claveCidi`, `cobraBeneficio`, `fechaDeAltaDeExpediente`, `codigoPostal`, `localidad`, `tipoDeTramite`, `procedencia`, `estadoDelTramite`, `fechaDeCobro`, `nacionalidad`, `tipoDeExpediente`, `caratula`, `nroDeExpediente`, `juzgadoODependencia`, `observaciones`, `fechaDeAtencion`) VALUES
(13, 1, '20666666660', '6666666', 'juan', NULL, 'masculino', 'cuello', 'dadjasjdasjd', 0, '1', '', 'dasdasd', '12312312', '2019-05-16', 0, '', '', '', 'Si', '2019-05-21', '', '', 'Jubilación Ordinaria', 'juan', '', '2019-05-15', 'Argentina', 'administrativo', NULL, NULL, NULL, '', '2019-05-08'),
(14, 21, '359767551', '9767551', 'alexis', NULL, 'masculino', 'cuello', 'charcas', 1584, '', '', 'gral paz', '8547485', '1996-05-08', 23, '4564', '231', '123123', 'Si', '2019-05-21', '5000', 'cordoba', 'Pensión Directa', 'ramiro rama', 'completo', '2019-05-07', 'Argentina', 'judicial', 'caratula', 'caratula', 'caratula', NULL, NULL),
(15, 12, '653459551', '3459551', 'ramiro', NULL, 'masculino', 'jugan', 'direccion', 10, '', '', 'gral paz', '231231', '2019-05-22', 0, '12312', '231', '54646', 'Si', '2019-05-16', '89856', 'cordoba', 'Jubilación Ordinaria', 'juan', 'completado', '2019-05-16', 'Argentina', 'judicial', 'caratula', '11111111', 'juzgado 2', NULL, NULL),
(16, 89, '650548229', '0548229', 'juan', NULL, 'masculino', 'v', 'charcas', 7894, '', '', 'yofre', '4569852', '1990-05-22', 29, '4566', '4561', '4651', 'No', '2019-05-14', '8521', 'alta gracia', 'Reajuste', 'ramiro rama', 'completado', '2019-05-10', 'Extranjero', 'judicial', 'caratula 3', 'caratula 5', 'juzgado 3', NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Procedencia`
--

CREATE TABLE `Procedencia` (
  `idProcedencia` int(11) NOT NULL,
  `nombreProcedencia` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `Procedencia`
--

INSERT INTO `Procedencia` (`idProcedencia`, `nombreProcedencia`) VALUES
(2, 'juan'),
(3, 'ramiro rama'),
(4, 'juan pablo cuello');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `Agenda`
--
ALTER TABLE `Agenda`
  ADD PRIMARY KEY (`idAgenda`);

--
-- Indices de la tabla `Empleado`
--
ALTER TABLE `Empleado`
  ADD PRIMARY KEY (`idEmpleado`);

--
-- Indices de la tabla `Expediente`
--
ALTER TABLE `Expediente`
  ADD PRIMARY KEY (`idExpediente`);

--
-- Indices de la tabla `Procedencia`
--
ALTER TABLE `Procedencia`
  ADD PRIMARY KEY (`idProcedencia`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `Agenda`
--
ALTER TABLE `Agenda`
  MODIFY `idAgenda` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=37;
--
-- AUTO_INCREMENT de la tabla `Empleado`
--
ALTER TABLE `Empleado`
  MODIFY `idEmpleado` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT de la tabla `Expediente`
--
ALTER TABLE `Expediente`
  MODIFY `idExpediente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;
--
-- AUTO_INCREMENT de la tabla `Procedencia`
--
ALTER TABLE `Procedencia`
  MODIFY `idProcedencia` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
