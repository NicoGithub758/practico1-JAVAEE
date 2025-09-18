<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%! int ultimo = 0; %>
<%! public String fecha() {
		return new Date().toString();
	} %>


<html>
<head>
<meta charset="ISO-8859-1">
<title>Demo TSE 2022</title>
</head>
<body>

	<h1>Tabla JSP</h1>
	<table border ="2">
	
	<% for (int i = ultimo; i < ultimo + 10; i++) { %>
	
		<tr>
			<td>Número</td>
			<td><%= i %></td>
		</tr>
		<% }
		ultimo += 10; %>	
	</table>
	<p>La fecha y hora actual es <%= fecha() %></p>
</body>
</html>