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
  


