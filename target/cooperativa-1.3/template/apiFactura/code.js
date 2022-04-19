//Definicion de variables
'use strict';
const url = 'http://localhost:8080/cooperativa/api/consumo';
const section = document.querySelector('section');
let resultados = '';
const formBuscar = document.querySelector('form');
const buscar = document.getElementById('buscar');

buscar.value = '';
section.value = ''

//Funcion para mostrar los resultados
const mostrar = (consumoSocios) => {
    consumoSocios.forEach(consumoSocio => {
        const myArticle = document.createElement('article');
        const myH2 = document.createElement('h2');
        const myPara1 = document.createElement('p');
        const myPara2 = document.createElement('p');
        const myPara3 = document.createElement('p');
        const myPara4 = document.createElement('p');
        const myPara5 = document.createElement('p');
        
        myH2.textContent = 'Periodo: '+ consumoSocio.periodo;
        myPara1.textContent = 'Consumo: '+ consumoSocio.cantidad;
        myPara2.textContent = 'Importe: '+ consumoSocio.importe;
        myPara3.textContent = 'Estado: '+ consumoSocio.estado;
        myPara4.textContent = 'ID consumo: '+ consumoSocio.id;
        myPara5.textContent = 'Codigo de socio: '+ consumoSocio.cod_socio;
        
        myArticle.className = ''
        
        myArticle.appendChild(myH2);
        myArticle.appendChild(myPara1);
        myArticle.appendChild(myPara2);
        myArticle.appendChild(myPara3);
        myArticle.appendChild(myPara4);
        myArticle.appendChild(myPara5);
        
        section.appendChild(myArticle);
        
        
        
//        resultados += "<p>" + consumoSocio.estado + "</p>";
//        resultados += "<p>" + consumoSocio.id + "</p>";
//        resultados += "<p>" + consumoSocio.periodo + "</p>";
//        resultados += "<p>" + consumoSocio.importe + "</p>";
//        resultados += "<p>" + consumoSocio.cantidad + "</p>";
//        resultados += "<p>" + consumoSocio.cod_socio + "</p>";
    });
    //section.innerHTML = resultados;
    console.log("resultado:"+ resultados);
};

formBuscar.addEventListener('submit', (e) => {
    e.preventDefault();
    section.textContent = '';
        fetch(url + "/findCodSocio", {
            method: 'POST',
            headers: {
                'Content-type': 'application/json'
            },
            body: JSON.stringify({
               cod_socio : buscar.value,
            })
        })
                .then(response => response.json())
                .then(data => {
                    mostrar(data);
                });
    
});