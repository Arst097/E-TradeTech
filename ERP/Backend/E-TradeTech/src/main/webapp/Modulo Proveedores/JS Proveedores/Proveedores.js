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

