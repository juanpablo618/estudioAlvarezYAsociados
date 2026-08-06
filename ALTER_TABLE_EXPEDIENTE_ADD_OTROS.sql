ALTER TABLE Expediente
    ADD COLUMN otrosDanosAccidente TINYINT(1) NULL,
    ADD COLUMN otrosDanosIncumplimientoContractualLeySeguros TINYINT(1) NULL,
    ADD COLUMN otrosDivorcio TINYINT(1) NULL,
    ADD COLUMN otrosOtroSeleccionado TINYINT(1) NULL,
    ADD COLUMN otrosDemandado VARCHAR(255) NULL,
    ADD COLUMN otrosCuit VARCHAR(255) NULL,
    ADD COLUMN otrosDomicilio VARCHAR(255) NULL,
    ADD COLUMN otrosTerceroCitadoGarantia VARCHAR(255) NULL,
    ADD COLUMN otrosObservaciones TEXT NULL;
