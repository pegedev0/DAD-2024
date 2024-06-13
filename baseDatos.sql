USE dad;

DROP TABLE IF EXISTS sensores;
DROP TABLE IF EXISTS actuadores;

CREATE TABLE sensores (
    IdSensor INT NOT NULL ,
    IdPlaca INT,
    Record DOUBLE,
    Time BIGINT NOT NULL DEFAULT UNIX_TIMESTAMP(),
    TipoSensor INT,
    IdGrupo INT
    
);

CREATE TABLE actuadores (
    LEDid INT NOT NULL ,
    IdGroup INT,
    LEDstate INT,
    LEDintensity INT,    
	 idPlaca INT,
	 Time BIGINT NOT NULL DEFAULT UNIX_TIMESTAMP()
);


