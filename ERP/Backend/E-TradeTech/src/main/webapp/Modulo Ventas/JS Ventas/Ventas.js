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
    $.ajax({
        url: 'http://localhost:5077/E-TradeTech/MostrarProductosDisponiblesVentas',
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            console.log('Datos recibidos:', data);
            
            // Verificación adicional de estructura
            if (!data.Producto || !data.Precios) {
                console.error('Estructura incorrecta. Se esperaba {Producto: [], Precios: []}');
                return;
            }

            const $select = $('#categoria2');
            $select.empty().append('<option value="">Seleccione un producto</option>');

            // Usamos forEach para mejor legibilidad
            data.Producto.forEach((producto, index) => {
                const precio = data.Precios[index];
                if (producto && precio !== undefined) {
                    $select.append(new Option(producto, precio));
                }
            });

            console.log('Opciones generadas:', $select.find('option').length);
        },
        error: function(xhr, status, error) {
            console.error('Error en AJAX:', status, error);
            $('#categoria2').html('<option value="">Error al cargar productos</option>');
        }
    });
//    
//    $(document).ready(function() {
//        const testData = {
//            Producto: ["Producto 1", "Producto 2", "Producto 3"],
//            Precios: [100, 200, 300]
//        };
//
//        const $select = $('#categoria2');
//        $select.empty();
//
//        testData.Producto.forEach((nombre, index) => {
//            $select.append(`<option value="${testData.Precios[index]}">${nombre}</option>`);
//        });
//    });
    
    $('#categoria2').on('change', function() {
        const precio = $(this).val();
        $('#valorUnitario').val(precio);
    });
    
    function calcularTotal() {
        const cantidad = parseFloat($('#cantidad').val());
        const valorUnitario = parseFloat($('#valorUnitario').val());

        if (!isNaN(cantidad) && !isNaN(valorUnitario)) {
          const total = cantidad * valorUnitario;
          $('#total').val(total.toFixed(2)); // dos decimales
        } else {
          $('#total').val('');
        }
    }

      // Recalcular cuando cambie la cantidad o el producto
    $('#cantidad').on('input', calcularTotal);
    $('#categoria2').on('change', function() {
        const precio = $(this).val();
        $('#valorUnitario').val(precio);
        calcularTotal(); // también actualiza el total
    });
    
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
