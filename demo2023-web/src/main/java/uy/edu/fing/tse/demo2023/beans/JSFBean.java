package uy.edu.fing.tse.demo2023.beans;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named("jsfBean")
@SessionScoped
public class JSFBean implements Serializable {

	
	private static final long serialVersionUID = 1L;

	private String message;

	public JSFBean() {
		message = "Bienvenidos a TSE 2023!";
	}
	
	public String getBienvenida() {
		return this.message;
	}

}
