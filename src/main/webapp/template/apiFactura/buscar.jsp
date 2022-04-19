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
        <h1 class="alert alert-success text-center">BUSCAR CONSUMO</h1>
            <form class="row row-cols-lg-auto g-3 align-items-center">
                    <div class="col-12">
                        <button type="submit" class="btn btn-primary">Buscar</button>
                    </div>
                    <div class="col-12">
                        <input id="buscar" type="text" class="form-control" autofocus>
                    </div>
            </form>
        
        <section>

        </section>



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
