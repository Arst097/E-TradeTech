USE ETradeTechDB;

UPDATE Usuario SET Departamento = 'Administracion' WHERE Usuario_id = 1;
UPDATE Usuario SET Departamento = 'Inventario' WHERE Usuario_id IN (2,3,4);
UPDATE Usuario SET Departamento = 'Compras' WHERE Usuario_id IN (5,6,7,8);

ALTER TABLE Usuario
ALTER COLUMN Departamento nvarchar(70) NOT NULL;

UPDATE Usuario SET Salario = 2100000 WHERE Usuario_id = 1;
UPDATE Usuario SET Salario = 1500000 WHERE Usuario_id IN (2,3,4);
UPDATE Usuario SET Salario = 2500000 WHERE Usuario_id IN (5,6,7);
UPDATE Usuario SET Salario = 2800000 WHERE Usuario_id = 8;

ALTER TABLE Usuario
ALTER COLUMN Salario INT NOT NULL;

UPDATE Usuario SET Contrato = 'Termino indefinido' WHERE Usuario_id = 1;
UPDATE Usuario SET Contrato = 'Termino fijo' WHERE Usuario_id IN (2,3);
UPDATE Usuario SET Contrato = 'Por Horas' WHERE Usuario_id = 4;
UPDATE Usuario SET Contrato = 'Termino fijo' WHERE Usuario_id IN (5,6);
UPDATE Usuario SET Contrato = 'Contrato de aprendizaje' WHERE Usuario_id = 7;
UPDATE Usuario SET Contrato = 'Termino indefinido' WHERE Usuario_id = 8;

ALTER TABLE Usuario
ALTER COLUMN Contrato nvarchar(70) NOT NULL;

EXEC sp_rename 'Usuario', 'Empleado';

EXEC sp_rename 'Empleado.Usuario_id', 'Empleado_id', 'COLUMN';