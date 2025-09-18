<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Obtener parametros desde JSP</title>
</head>
<body>

<ul>
	<li>Nombre: <%=request.getParameter("nombre") %></li>
	<li>Apellido: <%=request.getParameter("apellido") %></li>
	<li>HTTP Method: <%=request.getMethod() %></li>
</ul>

</body>
</html>