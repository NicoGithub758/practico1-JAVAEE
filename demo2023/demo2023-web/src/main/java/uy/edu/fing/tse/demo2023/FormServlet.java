package uy.edu.fing.tse.demo2023;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FormServlet
 */
@WebServlet("/FormServlet")
public class FormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FormServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String title = "Obteniendo los datos de un formulario utilizando el método GET";
		String docType = 
				"<!doctype html>";
		out.println(docType +
				"<html>\n" +
				"<head><title>" + title + "</title></head>" +
				"<body bgcolor=\"#f0f0f0\">\n" +
				"<h1 align = \"center\">" + title + "</h1>\n" + 
				"<ul>\n" +
				"    <li><b>Nombre:</b> " + request.getParameter("nombre") + "\n" +
				"    <li><b>Apellido:</b> " + request.getParameter("apellido") + "\n" +
				"</ul>\n" + 
				"</body></html");
		
		
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
