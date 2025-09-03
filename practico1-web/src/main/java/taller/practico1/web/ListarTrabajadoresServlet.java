package taller.practico1.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import taller.practico1.TrabajadorSalud;
import taller.practico1.TrabajadorSaludServiceLocal;

// La anotación @WebServlet mapea este servlet a una URL
@WebServlet("/listarTrabajadores")
public class ListarTrabajadoresServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // Inyectamos nuestro EJB de negocio, ¡igual que antes!
    @EJB
    private TrabajadorSaludServiceLocal trabajadorService;

    // Este método se ejecuta cuando se hace una petición GET a la URL
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Establecemos el tipo de contenido de la respuesta
        response.setContentType("text/html;charset=UTF-8");

        // 2. Obtenemos el "pincel" para escribir en la respuesta
        try (PrintWriter out = response.getWriter()) {
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Lista de Trabajadores de Salud</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Lista de Trabajadores de Salud</h1>");
            
            try {
                // 3. Usamos el servicio EJB para obtener los datos
                List<TrabajadorSalud> trabajadores = trabajadorService.obtenerTodosLosTrabajadores();

                if (trabajadores.isEmpty()) {
                    out.println("<p>No hay trabajadores registrados.</p>");
                } else {
                    out.println("<table border='1'>");
                    out.println("<tr><th>ID</th><th>Cédula</th><th>Nombre Completo</th><th>Fecha Ingreso</th><th>Especialidad</th></tr>");
                    for (TrabajadorSalud t : trabajadores) {
                        out.println("<tr>");
                        out.println("<td>" + t.getId() + "</td>");
                        out.println("<td>" + t.getCedula() + "</td>");
                        out.println("<td>" + t.getNombreCompleto() + "</td>");
                        out.println("<td>" + t.getFechaIngreso() + "</td>");
                        out.println("<td>" + t.getEspecialidad() + "</td>");
                        out.println("</tr>");
                    }
                    out.println("</table>");
                }
            } catch (Exception e) {
                // Manejo básico de errores
                out.println("<p style='color:red;'>Error al obtener la lista de trabajadores: " + e.getMessage() + "</p>");
            }
            
            out.println("</body>");
            out.println("</html>");
        }
    }
}
