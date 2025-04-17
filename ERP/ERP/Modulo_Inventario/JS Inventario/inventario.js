function abrirFormulario (){
    document.getElementById("Formulario").style.display="block";
}

function cerrarFormulario (){
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

function cerrarFormularioEditar() {
    event.preventDefault();
    document.getElementById('FormularioEditar2').style.display = 'none';
}
