<%@page contentType="text/html" pageEncoding="UTF-8"%>

<form action="/cooperativa/socio" method="POST" id="socio">
    <input type="text" name="codigo" value="${codigo}" hidden>  
    <div>
        <label for="codigo">Nombre</label>
        <input type="text" name="nombre" value="${nombre}" id="nombre" required>
    </div>
    <div>
        <label for="codigo">Apellido</label>
        <input type="text" name="apellido" value="${apellido}" id="apellido" required>
    </div> 
    <div>
        <label for="codigo">Direccion</label>
        <input type="text" name="direccion" value="${direccion}" id="direccion" required>
    </div>
</form>