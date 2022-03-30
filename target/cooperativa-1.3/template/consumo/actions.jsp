<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:include page="../controls.jsp">
  <jsp:param name="create" value="/consumo" />
  <jsp:param name="delete" value="/consumo/delete?codigo=${codigo}" />
  <jsp:param name="findAll" value="/consumo/findAll" />
  <jsp:param name="form" value="consumo"/>
</jsp:include>