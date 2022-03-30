//Definicion de variables
'use strict';
const url = 'http://localhost:8080/cooperativa/api/socio';
const contenedor = document.querySelector('tbody');
let resultados = '';
const modalArticulo = new bootstrap.Modal(document.getElementById('modalArticulo'));
const formArticulo = document.querySelector('form');
const nombre = document.getElementById('nombre');
const apellido = document.getElementById('apellido');
const direccion = document.getElementById('direccion');
let opcion = '';
btnCrear.addEventListener('click', () => {
    nombre.value = '';
    apellido.value = '';
    direccion.value = '';
    modalArticulo.show();
    opcion = 'crear';
});
//Funcion para mostrar los resultados

const mostrar = (socios) => {
    socios.forEach(socio => {
//        resultados += ` <tr>
//                            <td>${socio.codigo}</td>
//                            <td>${socio.descripcion}</td>
//                            <td>${socio.apellido}</td>
//                            <td>${socio.direccion}</td>
//                            <td class="text-center"><a class="btnEditar btn btn-primary">Editar</a><a class="btnBorrar btn btn-danger">Borrar</a></td>
//                        </tr>
//                     `
        resultados += "<tr>";
        resultados += "<td style='text-align: center; vertical-align: middle;'>" + socio.codigo + "</td>";
        resultados += "<td style='text-align: center; vertical-align: middle;'>" + socio.nombre + "</td>";
        resultados += "<td style='text-align: center; vertical-align: middle;'>" + socio.apellido + "</td>";
        resultados += "<td style='text-align: center; vertical-align: middle;'>" + socio.direccion + "</td>";
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

    alertify.confirm("Borrar Articulo.",
            function () {
                fetch(url + "/delete", {
                    method: 'DELETE',
                    headers: {
                        'Content-type': 'application/json'
                    },
                    body: JSON.stringify({
                        codigo: id.valueOf()
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
    const nombreForm = fila.children[1].innerHTML;
    const apellidoForm = fila.children[2].innerHTML;
    const direccionForm = fila.children[3].innerHTML;
    nombre.value = nombreForm;
    apellido.value = apellidoForm;
    direccion.value = direccionForm;
    opcion = 'editar';
    modalArticulo.show();
    //console.log(fila.children[2].innerHTML);
});
//procedimiento para crear y editar
formArticulo.addEventListener('submit', (e) => {
    e.preventDefault();
    if (opcion == 'crear') {
        //console.log('Opcion crear')
        fetch(url, {
            method: 'POST',
            headers: {
                'Content-type': 'application/json'
            },
            body: JSON.stringify({
                codigo: "0",
                nombre: nombre.value,
                apellido: apellido.value,
                direccion: direccion.value
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
                codigo: idForm.valueOf(),
                nombre: nombre.value,
                apellido: apellido.value,
                direccion: ''
            })
        })
                .then(response => response.json())
                .then(response => location.reload());
        //console.log('Opcion editar')
    }
    modalArticulo.hide();
});