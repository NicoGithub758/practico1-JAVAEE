<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Gestión de Trabajadores de Salud</title>
</head>
<body>

    <h1>Alta de Nuevo Trabajador de Salud</h1>
    
    <form action="altaTrabajador" method="POST">
        <label for="cedula">Cédula:</label><br>
        <input type="text" id="cedula" name="cedula" required><br><br>
        
        <label for="nombre">Nombre Completo:</label><br>
        <input type="text" id="nombre" name="nombre" required size="50"><br><br>
        
        <label for="fechaIngreso">Fecha de Ingreso:</label><br>
        <input type="date" id="fechaIngreso" name="fechaIngreso" required><br><br>
        
        <label for="especialidad">Especialidad:</label><br>
        <input type="text" id="especialidad" name="especialidad" required><br><br>
        
        <input type="submit" value="Dar de Alta">
    </form>

    <hr>
    
    <a href="listarTrabajadores">Ver lista de trabajadores</a>

</body>
</html>