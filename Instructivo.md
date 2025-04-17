<h2>Instrucciones de Configuración de Entorno</h2>

1. Versiones
   Netbeans 19
   JDK 17
   Payara Server 6.2025.4
   SQLServer 16.0.1135.2
   
2. Modularización
   Source Packages
       Uso_Comun (Clases compartidas: usuarios, roles, utilidades, excepciones)
       Web (Servlets “genéricos” (login, dashboard), utilidades web)
       Seguridad (Filtros, listeners y configuración de seguridad)
       Por cada modulo:
           -> Servlets Especificos (Controllers)
           -> Logica de Negoció con Singleton (Services)
           -> Acceso a datos (DAO)
           -> Entidades del Modulo (models)
