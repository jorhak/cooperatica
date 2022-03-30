<%@page contentType="text/html" pageEncoding="UTF-8"%>

<form action="/cooperativa/consumo" method="POST" id="consumo">
    <input type="text" name="id" value="${id}" hidden>  
    <div>
        <label for="lectura">Lectura</label>
        <input type="text" name="lectura" value="${lectura}" id="lectura" required>
    </div>
    <div>
        <label for="socios">Socio</label>
        <select name="socios" id="socios">
            ${socios}
        </select>
    </div>
</form>