$(document).ready(function () {
    
    function cargarEmpleado() {
        $.ajax({
            url:'http://localhost:5077/E-TradeTech/ServletRRHHMostrar',
            method: 'GET',
//            dataType: 'json',
            success: function (data) {
                console.log("Productos recibidos:", data);
                const $tbody = $('#cuerpo-tabla-RRHH');
                $tbody.empty(); // Limpiar la tabla antes de insertar
                data.forEach(function (p) {
                    const fila = `
                        <tr>
                            <td>${p.id}</td>
                            <td>${p.nombre}</td>
                            <td>${p.Departamento}</td>
                            <td>$ ${p.Salario}</td>
                            <td>${p.FechaIN}</td>
                            <td>
                                <button class="editar" onclick="abrirFormularioEditar('${p.nombre}', '${p.Departamento}', '${p.Salario}', '${p.FechaIN}')">Editar</button>
                                <button class="eliminar">Eliminar</button>
                            </td>
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


function abrirFormulario() {
    document.getElementById("Formulario").style.display = "block";
}


function cerrarFormulario() {
    event.preventDefault();
    document.getElementById("Formulario").style.display = "none";
}

function abrirFormularioEditar(nombre, departamento, salario, fechaIngreso) {
    document.getElementById('FormularioEditar').style.display = 'block';
    document.getElementById('editNombre').value = nombre;
    document.getElementById('editDepartamento').value = departamento;
    document.getElementById('editSalario').value = salario;
    document.getElementById('editFechaIngreso').value = fechaIngreso;
}

function cerrarFormularioEditar() {
    event.preventDefault();
    document.getElementById('FormularioEditar').style.display = 'none';
}

function abrirFormularioMemo() {
    event.preventDefault();
    document.getElementById("FormularioMemorando").style.display = "block";
}

function cerrarFormularioMemo() {
    event.preventDefault();
    document.getElementById("FormularioMemorando").style.display = "none";
}

function generarDocumento() {
    alert(`Se ha generado un memorando`);
    cerrarFormulario();
}

function abrirFormularioCen() {
    event.preventDefault();
    document.getElementById("FormularioCen").style.display = "block";
}

function cerrarFormularioCen() {
    event.preventDefault();
    document.getElementById("FormularioCen").style.display = "none";
}

function generarCensantias() {
    alert(`Se ha generado un memorando`);
    cerrarFormulario();
}
