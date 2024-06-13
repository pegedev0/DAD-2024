USE dad;

DROP TABLE IF EXISTS sensores;
DROP TABLE IF EXISTS actuadores;

CREATE TABLE sensores (
    idSensor INT NOT NULL ,
    idPlaca INT,
    record DOUBLE,
    time BIGINT NOT NULL DEFAULT UNIX_TIMESTAMP(),
    tipoSensor INT,
    idGrupo INT
    
);

CREATE TABLE actuadores (
    idActuador INT NOT NULL ,
    idPlaca INT,
    state INT,
    intensity_id INT,    
	 idGrupo INT
);

INSERT INTO sensores (idSensor, idPlaca, record, tipoSensor, idGrupo)
VALUES (1, 1001, 25.5, 1, 0);