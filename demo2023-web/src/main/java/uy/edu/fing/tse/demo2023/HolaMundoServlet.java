package uy.edu.fing.tse.demo2023;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/HolaMundoServlet")
public class HolaMundoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private String mensaje = "";
    
    public void init(ServletConfig config) throws ServletException {
    	mensaje = "Hola mundo desde un servlet TSE 2023!!!";    	
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	      	response.setContentType("text/html");

			HttpSession session = request.getSession();
			String sesion = "TSE: " + new Date().toString();
			session.setAttribute("Sesion" , sesion);
			System.out.println("Grabo datos en sesion - " + sesion);

			PrintWriter out = response.getWriter();
			  
			String docType =
			         "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
			
			out.println(docType +
			     "<html>\n" +	
			     "<head><title>" + mensaje + "</title></head>\n"+
			     "<body bgcolor = \"#f0f0f0\">\n" +
			     "<h1 align = \"center\">" + mensaje + "</h1></body></html>\n"
			  );
	}



}
