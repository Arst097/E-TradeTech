USE ETradeTechDB;

IF NOT EXISTS (
    SELECT *
    FROM Producto
    WHERE Precio IS NULL
)
BEGIN
    ALTER TABLE Producto
    ALTER COLUMN Precio decimal(10,2) NOT NULL;
    PRINT 'La columna "Precio" fue modificada exitosamente a NOT NULL.';
END
ELSE
BEGIN
    PRINT 'Error: Existen valores NULL en la columna "Precio". No se puede convertir a NOT NULL.';
END

IF NOT EXISTS (
    SELECT *
    FROM Producto
    WHERE Categoria IS NULL
)
BEGIN
    ALTER TABLE Producto
    ALTER COLUMN Categoria varchar(20) NOT NULL;
    PRINT 'La columna "Categoria" fue modificada exitosamente a NOT NULL.';
END
ELSE
BEGIN
    PRINT 'Error: Existen valores NULL en la columna "Categoria". No se puede convertir a NOT NULL.';
END