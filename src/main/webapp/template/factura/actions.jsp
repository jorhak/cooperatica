<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:include page="../controls.jsp">
  <jsp:param name="create" value="/factura" />
  <jsp:param name="delete" value="/factura/delete?codigo=${codigo}" />
  <jsp:param name="findAll" value="/factura/findAll" />
  <jsp:param name="form" value="factura"/>
</jsp:include>