<h2>Instrucciones de Configuración de Entorno</h2>

<ol>
  <li>Versiones
    <ul>
      <li>Netbeans 19</li>
      <li>JDK 17</li>
      <li>Payara Server 6.2025.4</li>
      <li>SQLServer 2022 16.0.1135.2</li>
    </ul>
  </li>
  <li>Modularización
    <ul>
      <li>Source Packages
        <ul>
          <li>Uso_Comun (Clases compartidas: usuarios, roles, utilidades, excepciones)</li>
          <li>Web (Servlets “genéricos” – login, dashboard; utilidades web)</li>
          <li>Seguridad (Filtros, listeners y configuración de seguridad)</li>
          <li>Por cada módulo:
            <ul>
              <li>Servlets Específicos (Controllers)</li>
              <li>Lógica de Negocio con Singleton (Services)</li>
              <li>Acceso a Datos (DAO)</li>
              <li>Entidades del Módulo (models)</li>
            </ul>
          </li>
        </ul>
      </li>
    </ul>
  </li>
</ol>
