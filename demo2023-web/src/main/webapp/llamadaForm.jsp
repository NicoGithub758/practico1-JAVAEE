<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Llamada a métodos GET y POST desde un formulario</title>
</head>
<body>
<form action="Form">

<table>
<tr>
	<td>Nombre:</td>
	<td><input name="nombre" type="text"/></td>
</tr>
<tr>
	<td>Apellido:</td>
	<td><input name="apellido" type="text"/></td>
</tr>
<tr>
	<td><input type="submit" formmethod="get" value="Do GET" /></td>
	<td><input type="submit" formmethod="post" value="Do POST" /></td>
</tr>
</table>



<br/>

</form>


</body>
</html>