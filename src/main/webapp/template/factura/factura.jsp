<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html lang="es">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <%@include file="../styles.html"%>
    <title>${title}</title>
  </head>

  <body>
    <!-- importando menu -->
    <%@include file="../navbar.html"%>

    <div class="container-fluid pt-md-2">    
      <!-- accion de botones parametrizadas -->
      <div class="row pt-md-3">
        <%@include file="./actions.jsp"%>
      </div>

      <div class="row pt-md-3">
        <div class="col-md-12">
          <div class="card">
            <div class="card-body">
              <h5 class="card-title">${title}</h5>
              <div class="row">
                <div class="col-md-12">
                  <%@include file="./form.jsp"%>
                </div>
              </div>
            </div>
          </div>    
        </div>
      </div>

      <div class="container-fluid pt-md-5">
        <div class="row">        
          <%@include file="./table.jsp"%>
        </div>
      </div>
    </div>

    <%@include file="../javascript.html"%>
  </body>
</html>