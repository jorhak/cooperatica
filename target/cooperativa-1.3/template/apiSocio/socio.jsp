<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html lang="en">

    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <!-- CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/alertifyjs@1.13.1/build/css/alertify.min.css" />
        <!-- Default theme -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/alertifyjs@1.13.1/build/css/themes/default.min.css" />
        <title>${titulo}</title>
        <!--<title>FETCH CRUD DE JavaScript</title>-->
        <style>
            body {
                background-color: #f7f6f6;
            }

            table thead {
                background-color: #0a4f70;
                color: white;
            }
        </style>
    </head>

    <body>
        <!-- importando menu -->
    <%@include file="../navbar.html"%>
        <h1 class="alert alert-success text-center">Lista de SOCIOS</h1>
        <div class="container mt-4 shadow-lg p-3 mb-5 bg-body rounded">
            <button id="btnCrear" type="button" class="btn btn-primary" data-bs-toggle="modal">CREAR</button>
            <table id="tablaArticulos" class="table mt-2 table-border table-striped">
                <thead>
                    <tr class="text-center">
                        <th>CODIGO</th>
                        <th>NOMBRE</th>
                        <th>APELLIDO</th>
                        <th>DIRECCION</th>
                        <th>ACCIONES</th>
                    </tr>
                </thead>
                <tbody>

                </tbody>
            </table>
        </div>



        <div id="modalArticulo" class="modal fade" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header bg-primary text-white">
                        <h5 class="modal-title" id="exampleModalLabel">SOCIO</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form>
                            <div class="mb-3">
                                <label for="nombre" class="col-form-label">Nombre:</label>
                                <input id="nombre" type="text" class="form-control" autofocus>
                            </div>
                            <div class="mb-3">
                                <label for="apellido" class="col-form-label">Apellido:</label>
                                <input id="apellido" type="text" class="form-control">
                            </div>
                            <div class="mb-3">
                                <label for="direccion" class="col-form-label">Direccion:</label>
                                <input id="direccion" type="text" class="form-control">
                            </div>

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-primary">Guardar</button>
                    </div>
                    </form>
                </div>
            </div>
        </div>

        <!-- Option 1: Bootstrap Bundle with Popper -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>

        <!-- JavaScript -->
        <script src="https://cdn.jsdelivr.net/npm/alertifyjs@1.13.1/build/alertify.min.js"></script>
        <script >
            <%@include file="./code.js"%>
        </script>
    </body>

</html>

