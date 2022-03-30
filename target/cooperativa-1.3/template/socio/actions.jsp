<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:include page="../controls.jsp">
  <jsp:param name="create" value="/socio" />
  <jsp:param name="delete" value="/socio/delete?codigo=${codigo}" />
  <jsp:param name="findAll" value="/socio/findAll" />
  <jsp:param name="form" value="socio"/>
</jsp:include>