USE ETradeTechDB;

BEGIN TRY
	BEGIN TRANSACTION;

	ALTER TABLE Cliente ADD 
		Documento VARCHAR(10) NOT NULL DEFAULT '0000000000',
		Direccion VARCHAR(150);

	CREATE TABLE Factura (
		FacturaID INT IDENTITY(1,1) PRIMARY KEY,
		PedidoID INT NOT NULL,
		Numero_Factura VARCHAR(50) NOT NULL,
		Fecha_Emision DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
		Descripcion TEXT,
		Envio DECIMAL(12,2),
		Impuestos DECIMAL(12,2),
		Total DECIMAL(12,2) NOT NULL,
		Metodo_Pago VARCHAR(50), --De contado o credito
		Medio_Pago VARCHAR(50), -- Efectivo, Transferencia bancaria, Tarjeta, Chaque y Consignacion
		Nota TEXT,
		CUFE VARCHAR(100) NOT NULL,
		QR TEXT,
		FOREIGN KEY (PedidoID) REFERENCES Pedidos(PedidoID)
	);

	CREATE TABLE Movimiento_Contable (
		Movimiento_ContableID INT IDENTITY(1,1) PRIMARY KEY,
		Descripcion VARCHAR(255),
		Categoria VARCHAR(20) NOT NULL, -- Ingreso o Gasto
		Monto DECIMAL(12,2) NOT NULL,
		Fecha_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
		Metodo_pago VARCHAR(50) NOT NULL,
		FacturaID INT,
		FOREIGN KEY (FacturaID) REFERENCES Factura(FacturaID)
	);
	COMMIT TRANSACTION;
	PRINT 'EJECUTADO CON EXITO';
END TRY
BEGIN CATCH
	ROLLBACK TRANSACTION;
    PRINT 'Ocurrió un error: ' + ERROR_MESSAGE();
END CATCH
