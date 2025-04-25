USE ETradeTechDB;
GO

-- Insertar 8 Usuarios
INSERT INTO Usuario (Usuario_id, Nombre, Correo, Contraseña_SHA256)
VALUES
(1, 'Carlos Gestor', 'carlos@empresa.com', CONVERT(NVARCHAR(64), HASHBYTES('SHA2_256', 'gestor123'), 2)),
(2, 'Ana Despachador', 'ana@empresa.com', CONVERT(NVARCHAR(64), HASHBYTES('SHA2_256', 'ana456'), 2)),
(3, 'Luis Despachador', 'luis@empresa.com', CONVERT(NVARCHAR(64), HASHBYTES('SHA2_256', 'luis789'), 2)),
(4, 'Marta Despachador', 'marta@empresa.com', CONVERT(NVARCHAR(64), HASHBYTES('SHA2_256', 'marta012'), 2)),
(5, 'Pedro Compras', 'pedro@empresa.com', CONVERT(NVARCHAR(64), HASHBYTES('SHA2_256', 'pedro345'), 2)),
(6, 'Laura Compras', 'laura@empresa.com', CONVERT(NVARCHAR(64), HASHBYTES('SHA2_256', 'laura678'), 2)),
(7, 'Juan Compras', 'juan@empresa.com', CONVERT(NVARCHAR(64), HASHBYTES('SHA2_256', 'juan901'), 2)),
(8, 'Sofia Compras', 'sofia@empresa.com', CONVERT(NVARCHAR(64), HASHBYTES('SHA2_256', 'sofia234'), 2));
GO

-- Insertar Almacén
INSERT INTO Almacen (AlmacenID, Nombre, Direccion, Capacidad, Telefono)
VALUES (1, 'Almacén Central', 'Calle Principal 123', 100, '555-1000');
GO

-- Insertar Inventarios
INSERT INTO Inventario (InventarioID, tipo, AlmacenID)
VALUES
(1, 'Libre', 1),
(2, 'Reservado', 1);
GO

-- Insertar Gestor
INSERT INTO Gestores (GestorID, Usuario_Usuario_id, AlmacenID, Puesto, Telefono)
VALUES (1, 1, 1, 'Gerente de Almacén', '555-2000');
GO

-- Insertar Despachadores
INSERT INTO Despachador (DespachadorID, Usuario_Usuario_id, AlmacenID, Puesto, InventarioID, Telefono)
VALUES
(1, 2, 1, 'Despachador Senior', 1, '555-2001'),
(2, 3, 1, 'Despachador Junior', 1, '555-2002'),
(3, 4, 1, 'Despachador Nocturno', 1, '555-2003');
GO

-- Insertar Productos
INSERT INTO Producto (ProductoID, InventarioID, Modelo, Fecha_Entrada)
VALUES
-- Inventario Libre (1-10)
(1, 1, 'Dell XPS 15', '2023-01-01'),
(2, 1, 'HP Spectre x360', '2023-01-02'),
(3, 1, 'Lenovo Yoga 9i', '2023-01-03'),
(4, 1, 'Acer Swift X', '2023-01-04'),
(5, 1, 'Asus ZenBook 14', '2023-01-05'),
(6, 1, 'Microsoft Surface Laptop 5', '2023-01-06'),
(7, 1, 'Razer Blade 15', '2023-01-07'),
(8, 1, 'MSI Creator Z16', '2023-01-08'),
(9, 1, 'LG Gram 17', '2023-01-09'),
(10, 1, 'Samsung Galaxy Book3', '2023-01-10'),
-- Inventario Reservado (11-20)
(11, 2, 'MacBook Pro 16"', '2023-01-11'),
(12, 2, 'Lenovo ThinkPad X1', '2023-01-12'),
(13, 2, 'Dell Precision 7780', '2023-01-13'),
(14, 2, 'HP ZBook Fury 17', '2023-01-14'),
(15, 2, 'Microsoft Surface Studio', '2023-01-15'),
(16, 2, 'Asus ROG Zephyrus', '2023-01-16'),
(17, 2, 'Alienware x17 R2', '2023-01-17'),
(18, 2, 'Framework Laptop 16', '2023-01-18'),
(19, 2, 'Apple MacBook Air M2', '2023-01-19'),
(20, 2, 'Dell Latitude 9440', '2023-01-20');
GO

-- Listas de Contactos
INSERT INTO Lista_Contactos (Nombre, Descripcion)
VALUES
('Lista Compras Grupo A', 'Contactos principales de compras'),
('Lista Compras Individual', 'Contacto único de compras');
GO

-- Usuarios de Compras
INSERT INTO Usuario_Compras (Usuario_ComprasID, Usuario_Usuario_id, Lista_ContactosID, Puesto, Telefono)
VALUES
(1, 5, 1, 'Comprador Senior', '555-3001'),
(2, 6, 1, 'Comprador Junior', '555-3002'),
(3, 7, 1, 'Auxiliar Compras', '555-3003'),
(4, 8, 2, 'Especialista Compras', '555-3004');
GO

-- Proveedores
INSERT INTO Proveedor (Lista_ContactosID, Nombre, Descripcion, Telefono)
VALUES
(1, 'TecnoParts', 'Proveedor de componentes premium', '555-4001'),
(1, 'ElectroHard', 'Distribuidor de hardware', '555-4002'),
(1, 'ChipMaster', 'Especialistas en procesadores', '555-4003'),
(2, 'StorageTech', 'Soluciones de almacenamiento', '555-4004'),
(2, 'Suministros PC', 'Proveedor general de TI', '555-4005');
GO

-- Ofertas (2 por proveedor)
INSERT INTO Ofertas (OfertasID, ProveedorID, Producto_Ofertado, Precio_Unidad)
VALUES
(1, 1, 'Tarjeta Gráfica RTX 4090', '$1200'),
(2, 1, 'Procesador i9-13900K', '$650'),
(3, 2, 'Placa Base Z790', '$300'),
(4, 2, 'Memoria DDR5 32GB', '$180'),
(5, 3, 'Procesador Ryzen 9 7950X', '$620'),
(6, 3, 'Cooler líquido 360mm', '$150'),
(7, 4, 'Disco SSD 2TB NVMe', '$150'),
(8, 4, 'NAS 4 bahías 20TB', '$950'),
(9, 5, 'Monitor 4K 32"', '$450'),
(10, 5, 'Teclado mecánico gaming', '$120');
GO

-- Mensajeros
INSERT INTO Mensajero (MensajeroID, Nombre, Compañia, Telefono)
VALUES
(1, 'Roberto Mensajero', 'FastDelivery', '555-5001'),
(2, 'María Mensajera', 'ExpressCouriers', '555-5002');
GO

-- Historial Pedidos
INSERT INTO Historial_Pedidos (Historial_PredidosID)
VALUES (1);
GO

-- Pedidos
INSERT INTO Pedidos (PedidoID, Estado, DespachadorID, MensajeroID, Historial_PredidosID)
VALUES
(1, 'Procesando', 1, NULL, 1),
(2, 'Procesando', 2, NULL, 1),
(3, 'Procesando', 3, NULL, 1),
(4, 'Enviado', 1, 1, 1),
(5, 'Completado', 2, 2, 1);
GO

-- Relacionar Productos con Pedidos
UPDATE Producto SET PedidoID = 1 WHERE ProductoID BETWEEN 11 AND 15; -- 5 productos al pedido 1
UPDATE Producto SET PedidoID = 2 WHERE ProductoID BETWEEN 16 AND 17; -- 2 productos al pedido 2
UPDATE Producto SET PedidoID = 3 WHERE ProductoID = 18;             -- 1 producto al pedido 3
UPDATE Producto SET PedidoID = 4 WHERE ProductoID = 19;             -- 1 producto al pedido 4
UPDATE Producto SET PedidoID = 5 WHERE ProductoID = 20;             -- 1 producto al pedido 5
GO