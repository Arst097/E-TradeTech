USE ETradeTechDB;

-- Computadoras
UPDATE Producto SET Categoria = 'Computadoras', Precio = 4899.99 WHERE Modelo = 'Lenovo ThinkPad X1';
UPDATE Producto SET Categoria = 'Computadoras', Precio = 4599.50 WHERE Modelo = 'HP Spectre x360';
UPDATE Producto SET Categoria = 'Computadoras', Precio = 4299.00 WHERE Modelo = 'Dell XPS 13';

-- Teléfonos
UPDATE Producto SET Categoria = 'Telefonos', Precio = 3999.99 WHERE Modelo = 'iPhone 14 Pro';
UPDATE Producto SET Categoria = 'Telefonos', Precio = 3699.99 WHERE Modelo = 'Samsung Galaxy S23';

-- Tablets
UPDATE Producto SET Categoria = 'Tablets', Precio = 3299.00 WHERE Modelo = 'iPad Pro 12.9';
UPDATE Producto SET Categoria = 'Tablets', Precio = 2999.50 WHERE Modelo = 'Samsung Galaxy Tab S8';

-- Otros electrónicos
UPDATE Producto SET Categoria = 'Otros electronicos', Precio = 1999.99 WHERE Modelo = 'Apple Watch Series 9';
UPDATE Producto SET Categoria = 'Otros electronicos', Precio = 2999.00 WHERE Modelo = 'Xbox Series X';
UPDATE Producto SET Categoria = 'Otros electronicos', Precio = 2499.00 WHERE Modelo = 'Nintendo Switch OLED';