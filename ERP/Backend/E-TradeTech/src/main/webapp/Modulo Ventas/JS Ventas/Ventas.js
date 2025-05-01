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