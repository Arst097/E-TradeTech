$(document).ready(function () {
 
    function cargarProveedores() {
        $.ajax({
            url:'http://localhost:5077/E-TradeTech/ServletProveedoresMostrar',
            method: 'GET',
//            dataType: 'json',
            success: function (data) {
                console.log("Productos recibidos:", data);
                const $tbody = $('#cuerpo-tabla-proveedores');
                $tbody.empty(); // Limpiar la tabla antes de insertar
                data.forEach(function (p) {
                    const fila = `
                        <tr id="tr-${data.id}">
                            <td>${p.id}</td>
                            <td>${p.nombre}</td>
                            <td>${p.telefono}</td>
                            <td>${p.Descripcion}</td>
                            <td>${p.Estado}</td>
                            <td>${p.Oferta}</td>
                            
                            <td><button class="editar" onclick="abrirFormularioEditarP('${p.nombre}', '${p.telefono}', '${p.Descripcion}', '${p.Estado}',  '${p.Oferta}')">Editar</button>
                                <button class="eliminar">Eliminar</button>
                                <div class="btn_cambiar_status">
                                    <button class="cambiar" onclick="cambiarStatus()">Cambiar Estatus</button>
                                </div>
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
    // Llamar al cargar la página
    
    $(function () {
        console.log("jQuery funciona jquery");
        cargarProveedores();
    });
    
    $('#btnAgregarProveedor').click(function() {
//        e.preventDefault(); // Evita el envío normal del formulario

        // Recoger datos
        const nombre = $('#nombre').val();
        const Status = $('#Status').val();
        const telefono = $('#telefono').val();
        const descripcion = $('#descripcion').val();
        const oferta = $('#oferta').val();


        // Aquí puedes hacer un AJAX, por ejemplo:
        $.ajax({
            url: 'http://localhost:5077/E-TradeTech/ServletAgregarProveedor',
            method: 'POST',
            data: {
                nombre: nombre,
                Status: Status,
                telefono: telefono,
                descripcion: descripcion,
                oferta:oferta
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


function abrirFormularioProveedores (){
    document.getElementById("Formulario").style.display="block";
}

function cerrarFormularioProveedores (){
    event.preventDefault();
    document.getElementById("Formulario").style.display="none";
}

function cerrarFormularioStatus(){
    event.preventDefault();
    document.getElementById("FormularioStatus").style.display="none";
}

function cambiarStatus(){
    document.getElementById("FormularioStatus").style.display="block";
}

function abrirFormularioEditarP(nombre, telefono, descripcion, estado, oferta) {
    document.getElementById('FormularioEditarProveedores').style.display = 'flex';

    document.getElementById('editNombre').value = nombre;
    document.getElementById('editTelefono').value = telefono;
    document.getElementById('editDescripcion').value = descripcion;
    document.getElementById('editEstado').value = estado;
    document.getElementById('editOferta').value = oferta;
}

function abrirFormularioEditarO(nombre, precio, estado, descripcion, contacto,SKU) {
    document.getElementById('FormularioEditarProveedoresOrde').style.display = 'flex';

    document.getElementById('editNombre').value = nombre;
    document.getElementById('editPrecio').value = precio;
    document.getElementById('Estado').value = estado.toLowerCase();
    document.getElementById('editDetalle').value = descripcion;
    document.getElementById('editContacto').value = contacto;
    document.getElementById('editSKU').value = SKU;
}

function cerrarFormularioEditarO() {
    event.preventDefault();
    document.getElementById('FormularioEditarProveedoresOrde').style.display = 'none';
}

document.addEventListener("DOMContentLoaded", function () {
    const selectStatus = document.getElementById("Status");
    const inputEstado = document.getElementById("editEstado");
  
    if (selectStatus && inputEstado) {
      selectStatus.addEventListener("change", function () {
        inputEstado.value = this.value;
      });
    }
  });

