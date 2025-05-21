USE ETradeTechDB;

ALTER TABLE Usuario
ADD Departamento nvarchar(70) NULL;

ALTER TABLE Usuario
ADD Salario INT NULL;

ALTER TABLE Usuario
ADD Fecha_Ingreso DATETIME NOT NULL DEFAULT GETDATE();

ALTER TABLE Usuario
ADD Contrato nvarchar(70) NULL;



