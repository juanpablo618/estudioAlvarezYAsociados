CREATE TABLE IF NOT EXISTS documentosSentencias (
    nroDeOrden INT NOT NULL,
    documento LONGBLOB,
    nombreDelDocumento VARCHAR(255),
    PRIMARY KEY (nroDeOrden)
);

CREATE TABLE IF NOT EXISTS documentosSentenciasDos (
    nroDeOrden INT NOT NULL,
    documento LONGBLOB,
    nombreDelDocumento VARCHAR(255),
    PRIMARY KEY (nroDeOrden)
);

CREATE TABLE IF NOT EXISTS documentosSentenciasTres (
    nroDeOrden INT NOT NULL,
    documento LONGBLOB,
    nombreDelDocumento VARCHAR(255),
    PRIMARY KEY (nroDeOrden)
);

CREATE TABLE IF NOT EXISTS documentosSentenciasCuatro (
    nroDeOrden INT NOT NULL,
    documento LONGBLOB,
    nombreDelDocumento VARCHAR(255),
    PRIMARY KEY (nroDeOrden)
);

CREATE TABLE IF NOT EXISTS documentosSentenciasCinco (
    nroDeOrden INT NOT NULL,
    documento LONGBLOB,
    nombreDelDocumento VARCHAR(255),
    PRIMARY KEY (nroDeOrden)
);
