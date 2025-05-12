USE ETradeTechDB;

ALTER TABLE Cliente
DROP CONSTRAINT FK_Cliente_Usuario;

ALTER TABLE Cliente
DROP COLUMN Usuario_Usuario_id;

ALTER TABLE Cliente
ADD Nombre VARCHAR(45) NOT NULL DEFAULT 'cliente default',
    Correo VARCHAR(100) NULL;