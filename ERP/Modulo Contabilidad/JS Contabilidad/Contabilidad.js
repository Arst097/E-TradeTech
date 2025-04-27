function abrirFormulario() {
    document.getElementById("Formulario").style.display = "block";
}

function cerrarFormulario() {
    event.preventDefault();
    document.getElementById("Formulario").style.display = "none";
}

function abrirFormularioEditar(descripcion, categoria, monto, fecha) {
    document.getElementById('FormularioEditar').style.display = 'block';
    document.getElementById('editDescripcion').value = descripcion;
    document.getElementById('editCategoria').value = categoria;
    document.getElementById('editMonto').value = monto;
    document.getElementById('editFecha').value = fecha;
}

function cerrarFormularioEditar() {
    event.preventDefault();
    document.getElementById('FormularioEditar').style.display = 'none';
}
