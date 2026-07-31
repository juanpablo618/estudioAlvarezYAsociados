ALTER TABLE Expediente
    ADD COLUMN jurisdiccionVoluntariaSumariaInformacion TINYINT(1) NULL,
    ADD COLUMN jurisdiccionVoluntariaLimitacionCapacidad TINYINT(1) NULL,
    ADD COLUMN jurisdiccionVoluntariaOtro TINYINT(1) NULL,
    ADD COLUMN jurisdiccionVoluntariaOtroDetalle VARCHAR(255) NULL,
    ADD COLUMN jurisdiccionVoluntariaObservaciones TEXT NULL;
