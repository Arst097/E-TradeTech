USE ETradeTechDB;

INSERT INTO Cliente (ClienteID, Telefono, Nombre, Correo)
VALUES 
  (1, '555-1234', 'Juan Perez', 'juan.perez@email.com'),
  (2, '555-5678', 'Maria Gomez', 'maria.gomez@email.com'),
  (3, '555-9012', 'Carlos Ruiz', 'carlos.ruiz@email.com');
GO

UPDATE Pedidos SET ClienteID = 1 WHERE PedidoID IN (1, 2);
UPDATE Pedidos SET ClienteID = 2 WHERE PedidoID IN (3, 4);
UPDATE Pedidos SET ClienteID = 3 WHERE PedidoID IN (5);
GO

IF NOT EXISTS (
    SELECT *
    FROM Pedidos
    WHERE ClienteID IS NULL
)
BEGIN
    ALTER TABLE Pedidos
    ALTER COLUMN ClienteID int NOT NULL;
    PRINT 'La columna "Precio" fue modificada exitosamente a NOT NULL.';
END
ELSE
BEGIN
    PRINT 'Error: Existen valores NULL en la columna "Precio". No se puede convertir a NOT NULL.';
END
