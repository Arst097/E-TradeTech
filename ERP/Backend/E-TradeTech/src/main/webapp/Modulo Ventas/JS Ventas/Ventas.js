$(document).ready(function () {
    
    function cargarEmpleado() {
        $.ajax({
            url:'http://localhost:5077/E-TradeTech/ServletMostrarVentas',
            method: 'GET',
//            dataType: 'json',
            success: function (data) {
                console.log("Productos recibidos:", data);
                const $tbody = $('#cuerpo-tabla-ventas');
                $tbody.empty(); // Limpiar la tabla antes de insertar
                data.forEach(function (p) {
                    const fila = `
                        <tr>
                            <td>${p.id}</td>
                            <td>${p.IdCliente}</td>
                            <td>${p.Productos}</td>
                            <td>$${p.valorTotal}</td>
                            <td>${p.FechaIN}</td>
                            <td><button class="editar" onclick="abrirFormularioEditar()">Editar</button>
                                <button class="eliminar">Eliminar</button></td>
                        </tr>  
                    `;
                    $tbody.append(fila);
                });
//                cargarProveedores();
            },
            error: function (xhr, status, error) {
                console.error('Error al cargar productos:', error);
            }
        });
    }
    
    
    $(function () {
    console.log("jQuery está funcionando");
    // tu código aquí
    cargarEmpleado();
    });
    $("#btnEnviar").click(function() {
            idCliente = $("#IdClientes").val(),
            nombreCliente = $("#nombreClienteInput").val(),
            producto = $("#categoria2").val(),
            cantidad = $("#cantidad").val(),
            valorUnitario = $("#valorUnitario").val(),
            total= $("#total").val();
        

        $.ajax({
            url: 'http://localhost:5077/E-TradeTech/ServletHacerVenta', // Cambia si tu servlet tiene otra ruta
            type: "POST",
//            contentType: "application/json",
            data:{
                idCliente: idCliente,
                nombreCliente: nombreCliente,
                producto: producto,
                cantidad: cantidad,
                valorUnitario:valorUnitario,
                total:total
            },
            success: function(response) {
                alert("Venta realizada exitosamente.");
                console.log("Respuesta del servidor:", response);
            },
            error: function(xhr, status, error) {
                alert("Error al realizar la venta.");
                console.error("Error:", error);
            }
        });
    });

});

function abrirFormularioVen() {
    event.preventDefault();
    document.getElementById("FormularioVen").style.display = "block";
}

function cerrarFormularioVen() {
    event.preventDefault();
    document.getElementById("FormularioVen").style.display = "none";
}

function abrirFormularioEditar() {
    event.preventDefault();
    document.getElementById('FormularioEditar').style.display = "block";
}

function cerrarFormularioEditar(){
    event.preventDefault();
    document.getElementById('FormularioEditar').style.display = 'none';
}