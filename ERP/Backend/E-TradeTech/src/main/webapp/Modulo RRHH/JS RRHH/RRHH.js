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
    $('#btnAgregarEmpleado').click(function() {
            nombre = $("#nombre").val(),
//            departamento = $("#departamento").val(),
            departamento = $("#1departamento").val(),
            salario = $("#salario").val(),
            fechaIngreso = $("#fechaIngreso").val(),

        $.ajax({
            url: 'http://localhost:5077/E-TradeTech/ServletAgregarEmpleado', // Cambia si tu servlet tiene otra ruta
            type: "POST",
//            contentType: "application/json",
            data:{
                nombre: nombre,
                departamento: departamento,
                salario: salario,
                fechaIngreso:fechaIngreso
            },
            success: function(response) {
                alert(response);
                console.log("Respuesta del servidor:", response);
            },
            error: function(xhr, status, error) {
                alert("Error al realizar la venta.");
                console.error("Error:", error);
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
