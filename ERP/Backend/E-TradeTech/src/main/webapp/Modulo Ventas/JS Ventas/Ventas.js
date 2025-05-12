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
    $('#formularioProducto').on('submit', function (e) {
        e.preventDefault(); // Evita el envío normal del formulario

        // Recoger datos
        const nombre = $('input[id="Nom_producto"]').val();
        const categoria = $('select[id="categoria2"]').val();
        const stock = $('input[id="Can_Producto"]').val();
        const precio = $('input[id="Precio_producto"]').val();

        // Verifica que se estén recogiendo bien
        console.log({ nombre, categoria, stock, precio });

        // Aquí puedes hacer un AJAX, por ejemplo:
        $.ajax({
            url: 'http://localhost:5077/E-TradeTech/ServletAgregarProductos',
            method: 'POST',
            data: {
                nombre: nombre,
                categoria: categoria,
                stock: stock,
                precio: precio
            },
            success: function (respuesta) {
                alert(respuesta);
                // recargar tabla, cerrar modal, etc.
            },
            error: function (xhr) {
                alert("Error al agregar producto");
                console.error(xhr.responseText);
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