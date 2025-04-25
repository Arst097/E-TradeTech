-- Schema mydb
CREATE DATABASE ETradeTechDB;
GO

CREATE LOGIN access WITH PASSWORD = '123', CHECK_POLICY = OFF;

USE ETradeTechDB;
GO

-- Crear login 'access' con contraseña 123
CREATE USER access FOR LOGIN access;
GO
ALTER ROLE db_owner ADD MEMBER access;
GO

-- Table: Lista_Contactos
CREATE TABLE Lista_Contactos (
    Lista_ContactosID INT NOT NULL IDENTITY(1,1),
    Nombre NVARCHAR(45) NOT NULL,
    Descripcion NVARCHAR(100) NULL,
    CONSTRAINT PK_Lista_Contactos PRIMARY KEY (Lista_ContactosID),
    CONSTRAINT UQ_Lista_ContactosID UNIQUE (Lista_ContactosID)
);
GO

-- Table: Proveedor
CREATE TABLE Proveedor (
    ProveedorID INT NOT NULL IDENTITY(1,1),
    Lista_ContactosID INT NOT NULL,
    Nombre NVARCHAR(45) NOT NULL,
    Descripcion NVARCHAR(100) NULL,
    Telefono NVARCHAR(20) NOT NULL,
    CONSTRAINT PK_Proveedor PRIMARY KEY (ProveedorID),
    CONSTRAINT UQ_ProveedorID UNIQUE (ProveedorID),
    CONSTRAINT FK_Proveedor_Lista_Contactos FOREIGN KEY (Lista_ContactosID)
        REFERENCES Lista_Contactos (Lista_ContactosID)
        ON DELETE NO ACTION ON UPDATE NO ACTION
);
GO

-- Table: Usuario
CREATE TABLE Usuario (
    Usuario_id INT NOT NULL,
    Nombre NVARCHAR(45) NOT NULL,
    Correo NVARCHAR(100) NOT NULL,
    Contraseña_SHA256 NVARCHAR(64) NOT NULL,
    CONSTRAINT PK_Usuario PRIMARY KEY (Usuario_id),
    CONSTRAINT UQ_Usuario_id UNIQUE (Usuario_id)
);
GO

-- Table: Usuario_Compras
CREATE TABLE Usuario_Compras (
    Usuario_ComprasID INT NOT NULL,
    Usuario_Usuario_id INT NOT NULL,
    Lista_ContactosID INT NULL,
    Puesto NVARCHAR(70) NOT NULL,
    Telefono NVARCHAR(20) NOT NULL,
    CONSTRAINT PK_Usuario_Compras PRIMARY KEY (Usuario_ComprasID),
    CONSTRAINT UQ_Usuario_ComprasID UNIQUE (Usuario_ComprasID),
    CONSTRAINT FK_Usuario_Compras_Lista_Contactos FOREIGN KEY (Lista_ContactosID)
        REFERENCES Lista_Contactos (Lista_ContactosID)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT FK_Usuario_Compras_Usuario FOREIGN KEY (Usuario_Usuario_id)
        REFERENCES Usuario (Usuario_id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
);
GO

-- Table: Historial_Transacc_Inv
CREATE TABLE Historial_Transacc_Inv (
    Historial_Transacc_InvID INT NOT NULL,
    CONSTRAINT PK_Historial_Transacc_Inv PRIMARY KEY (Historial_Transacc_InvID),
    CONSTRAINT UQ_Historial_Transacc_InvID UNIQUE (Historial_Transacc_InvID)
);
GO

-- Table: Almacen
CREATE TABLE Almacen (
    AlmacenID INT NOT NULL,
    Nombre NVARCHAR(45) NOT NULL,
    Direccion NVARCHAR(45) NOT NULL,
    Capacidad INT NOT NULL,
    Telefono NVARCHAR(20) NOT NULL,
    CONSTRAINT PK_Almacen PRIMARY KEY (AlmacenID),
    CONSTRAINT UQ_AlmacenID UNIQUE (AlmacenID)
);
GO

-- Table: Inventario
CREATE TABLE Inventario (
    InventarioID INT NOT NULL,
    tipo NVARCHAR(45) NULL,
    AlmacenID INT NULL,
    CONSTRAINT PK_Inventario PRIMARY KEY (InventarioID),
    CONSTRAINT UQ_InventarioID UNIQUE (InventarioID),
    CONSTRAINT FK_Inventario_Almacen FOREIGN KEY (AlmacenID)
        REFERENCES Almacen (AlmacenID)
        ON DELETE NO ACTION ON UPDATE NO ACTION
);
GO

-- Table: Transaccion_Inv
CREATE TABLE Transaccion_Inv (
    Transaccion_InvID INT NOT NULL,
    Historial_Transacc_InvID INT NOT NULL,
    Inventario_EmisorID INT NOT NULL,
    Inventario_ReceptorD INT NOT NULL,
    Fecha DATETIME NOT NULL,
    CONSTRAINT PK_Transaccion_Inv PRIMARY KEY (Transaccion_InvID),
    CONSTRAINT FK_Transaccion_Inv_Historial FOREIGN KEY (Historial_Transacc_InvID)
        REFERENCES Historial_Transacc_Inv (Historial_Transacc_InvID)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT FK_Transaccion_Inv_Inventario1 FOREIGN KEY (Inventario_EmisorID)
        REFERENCES Inventario (InventarioID)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT FK_Transaccion_Inv_Inventario2 FOREIGN KEY (Inventario_ReceptorD)
        REFERENCES Inventario (InventarioID)
        ON DELETE NO ACTION ON UPDATE NO ACTION
);
GO

-- Create the Ofertas table
CREATE TABLE Ofertas (
    OfertasID INT NOT NULL,
    ProveedorID INT NOT NULL,
    Producto_Ofertado NVARCHAR(45) NOT NULL,
    Precio_Unidad NVARCHAR(45) NOT NULL,
    CONSTRAINT PK_Ofertas PRIMARY KEY (OfertasID),
    CONSTRAINT UQ_OfertasID UNIQUE (OfertasID),
    CONSTRAINT FK_Ofertas_Proveedor FOREIGN KEY (ProveedorID)
        REFERENCES Proveedor (ProveedorID)
        ON DELETE NO ACTION ON UPDATE NO ACTION
);
GO

-- Create the Despachador table
CREATE TABLE Despachador (
    DespachadorID INT NOT NULL,
    Usuario_Usuario_id INT NOT NULL,
    AlmacenID INT NOT NULL,
    Puesto NVARCHAR(70) NOT NULL,
    InventarioID INT NOT NULL,
    Telefono NVARCHAR(20) NOT NULL,
    CONSTRAINT PK_Despachador PRIMARY KEY (DespachadorID),
    CONSTRAINT UQ_DespachadorID UNIQUE (DespachadorID),
    CONSTRAINT FK_Despachador_Almacen FOREIGN KEY (AlmacenID)
        REFERENCES Almacen (AlmacenID)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT FK_Despachador_Inventario FOREIGN KEY (InventarioID)
        REFERENCES Inventario (InventarioID)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT FK_Despachador_Usuario FOREIGN KEY (Usuario_Usuario_id)
        REFERENCES Usuario (Usuario_id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
);
GO

-- Create the Cliente table
CREATE TABLE Cliente (
    ClienteID INT NOT NULL,
    Telefono NVARCHAR(20) NOT NULL,
    Usuario_Usuario_id INT NOT NULL,
    CONSTRAINT PK_Cliente PRIMARY KEY (ClienteID),
    CONSTRAINT UQ_ClienteID UNIQUE (ClienteID),
    CONSTRAINT FK_Cliente_Usuario FOREIGN KEY (Usuario_Usuario_id)
        REFERENCES Usuario (Usuario_id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
);
GO

-- Create the Mensajero table
CREATE TABLE Mensajero (
    MensajeroID INT NOT NULL,
    Nombre NVARCHAR(45) NOT NULL,
    Compañia NVARCHAR(75) NOT NULL,
    Telefono NVARCHAR(20) NOT NULL,
    CONSTRAINT PK_Mensajero PRIMARY KEY (MensajeroID),
    CONSTRAINT UQ_MensajeroID UNIQUE (MensajeroID)
);
GO

-- Create the Historial_Pedidos table
CREATE TABLE Historial_Pedidos (
    Historial_PredidosID INT NOT NULL,
    CONSTRAINT PK_Historial_Pedidos PRIMARY KEY (Historial_PredidosID),
    CONSTRAINT UQ_Historial_PredidosID UNIQUE (Historial_PredidosID)
);
GO

-- Create the Pedidos table
CREATE TABLE Pedidos (
    PedidoID INT NOT NULL,
    Estado NVARCHAR(30) NULL,
    ClienteID INT NULL,
    DespachadorID INT NULL,
    MensajeroID INT NULL,
    Historial_PredidosID INT NOT NULL,
    CONSTRAINT PK_Pedidos PRIMARY KEY (PedidoID),
    CONSTRAINT UQ_PedidoID UNIQUE (PedidoID),
    CONSTRAINT FK_Pedido_Cliente FOREIGN KEY (ClienteID)
        REFERENCES Cliente (ClienteID)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT FK_Pedido_Despachador FOREIGN KEY (DespachadorID)
        REFERENCES Despachador (DespachadorID)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT FK_Pedido_Mensajero FOREIGN KEY (MensajeroID)
        REFERENCES Mensajero (MensajeroID)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT FK_Pedidos_Historial_Pedidos FOREIGN KEY (Historial_PredidosID)
        REFERENCES Historial_Pedidos (Historial_PredidosID)
        ON DELETE NO ACTION ON UPDATE NO ACTION
);
GO

-- Create the Producto table
CREATE TABLE Producto (
    ProductoID INT NOT NULL,
    InventarioID INT NOT NULL,
    PedidoID INT NULL,
    Modelo NVARCHAR(100) NOT NULL,
    Fecha_Entrada DATETIME NOT NULL,
    CONSTRAINT PK_Producto PRIMARY KEY (ProductoID),
    CONSTRAINT UQ_ProductoID UNIQUE (ProductoID),
    CONSTRAINT FK_Producto_Inventario FOREIGN KEY (InventarioID)
        REFERENCES Inventario (InventarioID)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT FK_Producto_Pedidos FOREIGN KEY (PedidoID)
        REFERENCES Pedidos (PedidoID)
        ON DELETE NO ACTION ON UPDATE NO ACTION
);
GO

-- Create the Gestores table
CREATE TABLE Gestores (
    GestorID INT NOT NULL,
    Usuario_Usuario_id INT NOT NULL,
    AlmacenID INT NOT NULL,
    Puesto NVARCHAR(70) NOT NULL,
    Telefono NVARCHAR(20) NOT NULL,
    CONSTRAINT PK_Gestores PRIMARY KEY (GestorID),
    CONSTRAINT UQ_GestorID UNIQUE (GestorID),
    CONSTRAINT FK_Gestores_Almacen FOREIGN KEY (AlmacenID)
        REFERENCES Almacen (AlmacenID)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT FK_Gestores_Usuario FOREIGN KEY (Usuario_Usuario_id)
        REFERENCES Usuario (Usuario_id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
);
GO