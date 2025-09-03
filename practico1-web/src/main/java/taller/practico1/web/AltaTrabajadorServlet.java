package taller.practico1.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import taller.practico1.TrabajadorSalud;
import taller.practico1.TrabajadorSaludServiceLocal;

@WebServlet("/altaTrabajador")
public class AltaTrabajadorServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @EJB
    private TrabajadorSaludServiceLocal trabajadorService;

    // Este método se ejecuta cuando se recibe una petición POST
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        // 1. Leer los parámetros del formulario
        String cedula = request.getParameter("cedula");
        String nombre = request.getParameter("nombre");
        String fechaIngresoStr = request.getParameter("fechaIngreso");
        String especialidad = request.getParameter("especialidad");

        try {
            // 2. Crear el objeto TrabajadorSalud
            TrabajadorSalud nuevoTrabajador = new TrabajadorSalud();
            nuevoTrabajador.setCedula(cedula);
            nuevoTrabajador.setNombreCompleto(nombre);
            nuevoTrabajador.setFechaIngreso(LocalDate.parse(fechaIngresoStr)); // Convertir String a LocalDate
            nuevoTrabajador.setEspecialidad(especialidad);

            // 3. Llamar al servicio EJB para dar de alta
            trabajadorService.altaTrabajador(nuevoTrabajador);

            // 4. Redirigir al usuario a la página de listado para ver el resultado
            response.sendRedirect("listarTrabajadores");

        } catch (Exception e) {
            // Manejo de errores simple: mostrar el error en una página
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.println("<html><body>");
                out.println("<h1>Error al dar de alta al trabajador</h1>");
                out.println("<p style='color:red;'>" + e.getMessage() + "</p>");
                out.println("<a href='index.jsp'>Volver al formulario</a>");
                out.println("</body></html>");
            }
        }
    }
}
