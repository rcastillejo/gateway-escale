-- Table: escale_colegios

-- DROP TABLE escale_colegios;

CREATE TABLE escale_colegios
(
  codmodular character varying(7) NOT NULL,
  codlocal character varying(7),
  nombre character varying(255),
  codnivel character varying(255),
  gestion character varying(255),
  direccion character varying(255),
  ubigeo character varying(6),
  latitud numeric(10,5),
  altitud numeric(10,5),
  CONSTRAINT pk_codmodular PRIMARY KEY (codmodular)
)
WITH (
  OIDS=FALSE
);
--ALTER TABLE escale_colegios OWNER TO postgres;


-- Table: escale_colegios_detalle

-- DROP TABLE escale_colegios_detalle;

CREATE TABLE escale_colegios_detalle
(
  codmodular character varying(7) NOT NULL,
  codgrado integer NOT NULL,
  anio integer NOT NULL,
  cantidad integer,
  codtipo integer,
  CONSTRAINT pk_detalle PRIMARY KEY (codmodular, codgrado, anio)
)
WITH (
  OIDS=FALSE
);
--ALTER TABLE escale_colegios_detalle OWNER TO postgres;
