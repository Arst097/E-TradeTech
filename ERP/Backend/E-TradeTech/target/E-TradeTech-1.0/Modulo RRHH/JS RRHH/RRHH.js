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
