CREATE TABLE IF NOT EXISTS documentosCartaPoderDos (
    nroDeOrden INT NOT NULL,
    documento LONGBLOB,
    nombreDelDocumento VARCHAR(255),
    PRIMARY KEY (nroDeOrden)
);
