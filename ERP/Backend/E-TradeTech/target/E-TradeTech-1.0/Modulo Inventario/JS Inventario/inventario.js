window.addEventListener('DOMContentLoaded', cargarProductos);

function abrirFormulario(){
    document.getElementById("Formulario").style.display="block";
}

function cerrarFormulario(){
    event.preventDefault();
    document.getElementById("Formulario").style.display="none";
}

function abrirFormularioEditar(producto, categoria, cantidad, precio) {
    document.getElementById('FormularioEditar').style.display = 'flex';

    document.getElementById('editProducto').value = producto;
    document.getElementById('categoria2').value = categoria.toLowerCase();
    document.getElementById('editCantidad').value = cantidad;
    document.getElementById('editPrecio').value = precio;
}

function abrirFormularioEditar2(nombre, precio, estado, detalles, cantidad) {
    document.getElementById('FormularioEditar2').style.display = 'flex';

    document.getElementById('editNombre').value = nombre;
    document.getElementById('editPrecio').value = precio;
    document.getElementById('estado').value = estado.toLowerCase();
    document.getElementById('editDetalles').value = detalles;
    document.getElementById('editCantidad').value = cantidad;
}

function cerrarFormularioEdi() {
    event.preventDefault();
    document.getElementById('FormularioEditar').style.display = 'none';
}


function cerrarFormularioEditar() {
    event.preventDefault();
    document.getElementById('FormularioEditar2').style.display = 'none';
}

async function cargarProductos() {
    try {
        const respuesta = await fetch('ServletInventarioMostrar');
        const productos = await respuesta.json();

        const tbody = document.getElementById('cuerpo-tabla-productos');
        tbody.innerHTML = ''; // limpia contenido anterior

        productos.forEach(p => {
            const fila = document.createElement('tr');
            fila.innerHTML = `
                <td>${p.id}</td>
                <td>${p.nombre}</td>
                <td>${p.categoria}</td>
                <td>${p.stock}</td>
                <td>$${Number(p.precio).toLocaleString()}</td>
                <td>
                    <button class="editar" onclick="abrirFormularioEditar('${p.nombre}', '${p.categoria}', ${p.stock}, '${p.precio}')">Editar</button>
                    <button class="eliminar">Eliminar</button>
                </td>
            `;
            tbody.appendChild(fila);
        });

    } catch (error) {
        console.error('Error al cargar productos:', error);
    }
}