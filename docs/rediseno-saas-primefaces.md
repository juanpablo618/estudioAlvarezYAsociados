# Rediseño SaaS moderno para JSF + PrimeFaces

## 1) Estructura HTML/Facelets recomendada

- `template.xhtml` como shell principal:
  - `header.app-topbar` con logo, CTAs y usuario.
  - `aside.app-sidebar` con navegación principal.
  - `main.app-content` para módulos funcionales.
- Cada pantalla hereda del template con `ui:composition template="/template.xhtml"`.

## 2) CSS base del sistema

Se incorporó un sistema de tokens y componentes visuales reutilizables:

- Variables CSS (`:root`) para colores institucionales.
- Clases de layout:
  - `app-layout`, `app-topbar`, `app-sidebar`, `app-content`.
- Clases de componentes:
  - `saas-card`, `saas-table`, `saas-modal`, `status-badge`.
- Estilo de inputs, botones y tabla siguiendo criterios SaaS.

## 3) Sugerencias de componentes PrimeFaces (mapping)

- **Card**: `p:panel` o contenedor `<div class="saas-card">`.
- **Modal**: `p:dialog styleClass="saas-modal"`.
- **Table**: `p:dataTable styleClass="saas-table"` con columnas limpias y badges de estado.
- **Form**:
  - `p:inputText`, `p:inputTextarea`, `p:selectOneMenu`.
  - Labels con clase `saas-label`.
- **Buttons**:
  - `p:commandButton styleClass="btn-primary|btn-secondary|btn-ghost"`.
- **Estado**:
  - `<span class="status-badge status-badge--open|pending|closed">`.

## 4) Layout general del sistema

- **Top Bar**
  - Logo del estudio.
  - Acciones rápidas: Nueva Agenda, Nuevo Turno, Nueva Actividad.
  - Notificaciones, usuario logueado y salida.
- **Sidebar izquierdo**
  - Dashboard, Expedientes, Consultas, Agendas, Turnos, Actividades, Reportes, Configuración.
- **Zona central**
  - Cards con módulos.
  - DataTables con alta legibilidad.
  - Modales en 2 columnas para creación/edición.

## Pantalla de referencia

Se agregó `web/modern_ui_showcase.xhtml` como ejemplo funcional de:

- Tabla de expedientes.
- Modal moderno para Crear Agenda.
- Uso de componentes y clases reutilizables.
