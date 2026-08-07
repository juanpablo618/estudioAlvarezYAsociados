ALTER TABLE Expediente
    ADD COLUMN contenciosoPlenaJurisdiccion TINYINT(1) NULL,
    ADD COLUMN contenciosoAmparo TINYINT(1) NULL,
    ADD COLUMN contenciosoOtrosSeleccionado TINYINT(1) NULL,
    ADD COLUMN contenciosoNumeroExpedienteAdministrativo VARCHAR(255) NULL,
    ADD COLUMN contenciosoFechaInicioSolicitud DATE NULL,
    ADD COLUMN contenciosoResolucion TEXT NULL,
    ADD COLUMN contenciosoFechaResolucion DATE NULL,
    ADD COLUMN contenciosoVencimientoDemanda DATE NULL,
    ADD COLUMN contenciosoObservaciones TEXT NULL;
