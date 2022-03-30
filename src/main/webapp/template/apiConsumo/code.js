//Definicion de variables
'use strict';
const url = 'http://localhost:8080/cooperativa/api/consumo';
const contenedor = document.querySelector('tbody');
const combo = document.querySelector('select');
let monto;
let resultados = '';
let resultadosCombo = '<option value="" selected> --Seleccione-- </option>';
const modalConsumo = new bootstrap.Modal(document.getElementById('modalConsumo'));
const formConsumo = document.querySelector('form');
const lectura = document.getElementById('lectura');
const socio = document.getElementById('socios');

let opcion = '';
btnCrear.addEventListener('click', () => {
    lectura.value = '';
    socio.value = '';
    modalConsumo.show();
    opcion = 'crear';
});
//Funcion para mostrar los resultados

const mostrar = (consumos) => {
    consumos.forEach(consumo => {
//        resultados += ` <tr>
//                            <td>${socio.codigo}</td>
//                            <td>${socio.descripcion}</td>
//                            <td>${socio.apellido}</td>
//                            <td>${socio.direccion}</td>
//                            <td class="text-center"><a class="btnEditar btn btn-primary">Editar</a><a class="btnBorrar btn btn-danger">Borrar</a></td>
//                        </tr>
//                     `
        monto = Number(consumo.Mfin) - Number(consumo.Minicio);
        resultados += "<tr>";
        resultados += "<td style='text-align: center; vertical-align: middle;'>" + consumo.id + "</td>";
        resultados += "<td style='text-align: center; vertical-align: middle;'>" + consumo.Finicio + "</td>";
        resultados += "<td style='text-align: center; vertical-align: middle;'>" + consumo.Ffin + "</td>";
        resultados += "<td style='text-align: center; vertical-align: middle;'>" + consumo.estado + "</td>";
        resultados += "<td style='text-align: center; vertical-align: middle;'>" + monto + "</td>";
        resultados += "<td style='text-align: center; vertical-align: middle;'>" + consumo.cod_socio + "</td>";
        resultados += "<td class='text-center'><a class='btnEditar btn btn-primary'>Editar</a><a class='btnBorrar btn btn-danger'>Borrar</a></td>";
        resultados += "</tr>";
    });

    contenedor.innerHTML = resultados;

};

//Procedimiento Mostrar
fetch(url + "/findAll", {
    method: 'GET'
})
        .then(response => response.json())
        .then(data => mostrar(data))
        .catch(error => console.log(error));


const mostrarCombo = (socios) => {
    socios.forEach(socio => {
        resultadosCombo += "<option value=" + socio.codigo + ">" + socio.nombre + "</option>";
    });
    combo.innerHTML = resultadosCombo;
};

fetch(url + "/comboSocio", {
    method: 'GET'
})
        .then(response => response.json())
        .then(data => mostrarCombo(data))
        .catch(error => console.log(error));


const on = (element, event, selector, handler) => {
    element.addEventListener(event, e => {
        if (e.target.closest(selector)) {
            handler(e);
        }
    });
};

//procedimieto borrar
on(document, 'click', '.btnBorrar', e => {
    const fila = e.target.parentNode.parentNode;
    const id = fila.firstElementChild.innerHTML;

    alertify.confirm("Borrar Consumo.",
            function () {
                fetch(url + "/delete", {
                    method: 'DELETE',
                    headers: {
                        'Content-type': 'application/json'
                    },
                    body: JSON.stringify({
                        id: id.valueOf()
                    })
                })
                        .then(res => res.json())
                        .then(() => location.reload());
                //alertify.success('Ok');
            },
            function () {
                alertify.error('Cancel');
            });
});

//procedimiento editar
let idForm = 0;
on(document, 'click', '.btnEditar', e => {
    const fila = e.target.parentNode.parentNode;
    idForm = fila.children[0].innerHTML;
    const codigo_socioForm = fila.children[5].innerHTML;
    lectura.value = "";
    socio.value = codigo_socioForm;
    opcion = 'editar';
    modalConsumo.show();
    //console.log(fila.children[2].innerHTML);
    resultadosCombo = '';
    const mostrarComboSeleccionado = (socios) => {
        socios.forEach(socio => {
            if (codigo_socioForm === socio.codigo) {
                resultadosCombo += "<option value=" + socio.codigo + " selected>" + socio.nombre + "</option>";
            } else {
                resultadosCombo += "<option value=" + socio.codigo + ">" + socio.nombre + "</option>";
            }
        });
        combo.innerHTML = resultadosCombo;
    };

    fetch(url + "/comboSocio", {
        method: 'GET'
    })
            .then(response => response.json())
            .then(data => mostrarComboSeleccionado(data))
            .catch(error => console.log(error));

});
//procedimiento para crear y editar
formConsumo.addEventListener('submit', (e) => {
    e.preventDefault();
    if (opcion == 'crear') {
        //console.log('Opcion crear')
        fetch(url, {
            method: 'POST',
            headers: {
                'Content-type': 'application/json'
            },
            body: JSON.stringify({
                id: "0",
                lectura: lectura.value,
                socio: socio.value,
            })
        })
                .then(response => response.json())
                .then(data => {
                    const nuevoArticulo = []
                    nuevoArticulo.push(data);
                    mostrar(nuevoArticulo);
                });
    }
    if (opcion == 'editar') {
        fetch(url + "/update", {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: idForm.valueOf(),
                lectura: lectura.value,
                socio: socio.value,
            })
        })
                .then(response => response.json())
                .then(response => location.reload());
        //console.log('Opcion editar')
    }
    modalConsumo.hide();
});


