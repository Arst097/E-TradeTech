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

function abrirFormularioEditarP(nombre, direccion, detalles, area) {
    document.getElementById('FormularioEditarProveedores').style.display = 'flex';

    document.getElementById('editNombre').value = nombre;
    document.getElementById('editDireccion').value = direccion;
    document.getElementById('editDetalles').value = detalles;
    document.getElementById('editArea').value = area;
}

function abrirFormularioEditarO(nombre, precio, estado, detalles, contacto,SKU) {
    document.getElementById('FormularioEditarProveedoresOrde').style.display = 'flex';

    document.getElementById('editNombre').value = nombre;
    document.getElementById('editPrecio').value = precio;
    document.getElementById('Estado').value = estado.toLowerCase();
    document.getElementById('editDetalle').value = detalles;
    document.getElementById('editContacto').value = contacto;
    document.getElementById('editSKU').value = SKU;
}

function cerrarFormularioEditarO() {
    event.preventDefault();
    document.getElementById('FormularioEditarProveedoresOrde').style.display = 'none';
}




