# 🏛️ Sistema de Gestión de Expedientes Legales

## 📌 Descripción General
Sistema integral desarrollado para la gestión completa de expedientes legales en estudios jurídicos. Facilita el seguimiento de casos, gestión de agendas, comunicaciones y documentación legal de manera eficiente y organizada.

## 🏗️ Arquitectura del Sistema

### Tecnologías Principales
- **Backend**: Java EE
- **Frontend**: JavaServer Faces (JSF)
- **Base de Datos**: Relacional (JPA)
- **Persistencia**: Hibernate
- **Servidor de Aplicaciones**: Java EE compatible (ej: GlassFish, WildFly)

### Módulos Principales

#### 1. Gestión de Expedientes
- Creación y seguimiento de expedientes legales
- Clasificación por tipo (Administrativo, Judicial, Sin Carpeta)
- Vinculación con documentos y comunicaciones
- Historial completo de movimientos

#### 2. Gestión de Agendas
- Programación de actividades
- Asignación de responsables
- Seguimiento de tareas pendientes
- Sistema de prioridades

#### 3. Comunicaciones
- Registro de comunicaciones entrantes/salientes
- Vinculación con expedientes
- Seguimiento de correspondencia

#### 4. Usuarios y Permisos
- Sistema de autenticación
- Roles de usuario
- Control de accesos

## 🗂️ Estructura de Datos

### Entidades Principales

#### 1. Expediente
- **ID único**: Identificador único del expediente
- **Número de orden**: Para vinculación física/virtual
- **Datos personales**: Nombre, apellido, CUIT, DNI, etc.
- **Datos de contacto**: Dirección, teléfono, email
- **Tipo**: Administrativo, Judicial o Sin Carpeta
- **Estado actual**: Situación procesal actual
- **Fechas importantes**: Fechas clave del expediente

#### 2. Agenda
- **ID único**: Identificador de la agenda
- **Fecha**: Fecha programada
- **Descripción**: Detalle de la actividad
- **Responsable**: Usuario asignado
- **Prioridad**: Nivel de prioridad
- **Estado**: Pendiente, Realizado, Reagendado
- **Vínculo a expediente**: Relación con el expediente correspondiente

#### 3. Comunicación
- **Tipo**: Carta, Email, Notificación, etc.
- **Fecha**: Fecha de emisión/recepción
- **Remitente/Destinatario**: Datos de contacto
- **Contenido**: Detalle de la comunicación
- **Documentos adjuntos**: Archivos relacionados
- **Vínculo a expediente**: Relación con el expediente correspondiente

## 🔄 Flujo de Trabajo

### 1. Creación de Expediente
1. Ingreso de datos básicos del cliente
2. Asignación de número de orden
3. Clasificación inicial (generalmente "Sin Carpeta")
4. Generación de documentación inicial

### 2. Seguimiento de Casos
1. Actualización de estados
2. Registro de novedades
3. Gestión documental
4. Control de plazos y vencimientos

### 3. Gestión de Agendas
1. Programación de actividades
2. Asignación de tareas
3. Seguimiento de cumplimiento
4. Reagendamiento cuando sea necesario

## 🏷️ Tipos de Expedientes

### 1. Sin Carpeta (Fondo Beige)
- Estado inicial de todo expediente
- Período de evaluación inicial
- Recopilación de documentación

### 2. Administrativo (Fondo Blanco)
- Trámites ante organismos públicos
- Gestiones pre-judiciales
- Documentación complementaria

### 3. Judicial (Fondo Verde)
- Casos en instancia judicial
- Seguimiento de causas
- Gestión de presentaciones y audiencias

## 📅 Módulo de Gestión de Agendas

### Funcionalidades Avanzadas

#### 1. Reagendamiento Inteligente (Botón Amarillo)
- **Propósito**: Reorganización eficiente de tareas no completadas
- **Funcionamiento**:
  - Crea réplica exacta para el próximo día hábil
  - Conserva todos los datos y vínculos originales
  - Mantiene la trazabilidad con la tarea original
- **Casos de Uso**:
  - Tareas pendientes al cierre del día
  - Necesidad de posposición por fuerza mayor
  - Reorganización de prioridades

#### 2. Gestión de Tareas (Botón Verde Fluor)
- **Propósito**: Cierre rápido de actividades
- **Características**:
  - Registro de finalización inmediata
  - Opción de agregar comentarios de cierre
  - Generación automática de historial
- **Recomendado para**:
  - Actividades puntuales
  - Gestiones sin seguimiento posterior
  - Tareas administrativas simples

#### 3. Gestión Completa (Botón Verde Oscuro)
- **Acceso a todas las funcionalidades**:
  - Creación y edición detallada
  - Asignación de prioridades
  - Vinculación con múltiples expedientes
  - Adjuntar documentos
  - Historial completo de modificaciones
- **Características avanzadas**:
  - Búsqueda y filtros avanzados
  - Exportación de datos
  - Informes de productividad

## ⚙️ Configuración y Personalización

### Parámetros del Sistema
- **Configuraciones Generales**:
  - Formatos de fecha y hora
  - Tipos de documentos admitidos
  - Plantillas predefinidas
  - Límites de almacenamiento

### Seguridad
- **Autenticación**:
  - Inicio de sesión seguro
  - Recuperación de contraseña
  - Bloqueo por intentos fallidos
- **Autorización**:
  - Roles predefinidos
  - Permisos configurables
  - Auditoría de accesos

## 📊 Reportes y Análisis

### Informes Disponibles
1. **Productividad por Usuario**
   - Tareas completadas
   - Tiempo promedio por tarea
   - Eficiencia en el seguimiento

2. **Seguimiento de Expedientes**
   - Tiempo promedio por etapa
   - Alertas de inactividad
   - Análisis de demoras

3. **Gestión de Carga Laboral**
   - Distribución de tareas
   - Identificación de cuellos de botella
   - Balance de carga de trabajo

## 🛠️ Mantenimiento y Soporte

### Políticas de Respaldo
- Copias de seguridad diarias automáticas
- Retención de 30 días
- Opción de exportación manual

### Actualizaciones
- Notificaciones de nuevas versiones
- Actualizaciones programadas
- Registro de cambios

## 📚 Documentación Adicional

### Manuales de Usuario
- Guía de inicio rápido
- Manual de procedimientos
- Preguntas frecuentes

### Capacitación
- Videos tutoriales
- Sesiones de entrenamiento
- Material de referencia

## 📞 Soporte Técnico
- **Horario de Atención**: Lunes a Viernes de 9:00 a 18:00 hs
- **Contacto**: juan@estudioalvarez.com
- **Teléfono**: +54 351 3220 999
- **Soporte Remoto**: Disponible bajo solicitud


---
*Documentación actualizada el 29 de Agosto de 2025*
*© 2025 Estudio Álvarez y Asociados - Todos los derechos reservados*
