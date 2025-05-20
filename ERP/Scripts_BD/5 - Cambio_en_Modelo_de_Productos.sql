USE ETradeTechDB;

-- Actualizar modelos con valores representativos y repetidos
UPDATE Producto SET Modelo = 'Lenovo ThinkPad X1' WHERE ProductoID IN (1, 11);
UPDATE Producto SET Modelo = 'HP Spectre x360' WHERE ProductoID IN (2, 13);
UPDATE Producto SET Modelo = 'Dell XPS 13' WHERE ProductoID IN (3, 14);
UPDATE Producto SET Modelo = 'iPhone 14 Pro' WHERE ProductoID IN (4, 15);
UPDATE Producto SET Modelo = 'Samsung Galaxy S23' WHERE ProductoID IN (5, 16);
UPDATE Producto SET Modelo = 'iPad Pro 12.9' WHERE ProductoID IN (6, 17);
UPDATE Producto SET Modelo = 'Samsung Galaxy Tab S8' WHERE ProductoID IN (7, 18);
UPDATE Producto SET Modelo = 'Apple Watch Series 9' WHERE ProductoID IN (8, 19);
UPDATE Producto SET Modelo = 'Xbox Series X' WHERE ProductoID IN (9, 20);
UPDATE Producto SET Modelo = 'Nintendo Switch OLED' WHERE ProductoID IN (10, 12);