package taller.practico1.web;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage; // Importante para mostrar mensajes
import jakarta.faces.context.FacesContext;   // Importante para mostrar mensajes
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import taller.practico1.TrabajadorSalud;
import taller.practico1.TrabajadorSaludServiceLocal;

@Named("trabajadorSaludBean")
@ViewScoped
public class TrabajadorSaludBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private TrabajadorSaludServiceLocal trabajadorService;

    private List<TrabajadorSalud> trabajadores;
    private TrabajadorSalud nuevoTrabajador;

    // --- NUEVAS PROPIEDADES PARA LA BÚSQUEDA ---
    private String cedulaBusqueda; // Almacena la cédula a buscar
    private TrabajadorSalud trabajadorEncontrado; // Almacena el resultado

    @PostConstruct
    public void init() {
        this.nuevoTrabajador = new TrabajadorSalud();
        this.cargarLista();
    }

    private void cargarLista() {
        this.trabajadores = trabajadorService.obtenerTodosLosTrabajadores();
    }

    public void darDeAlta() {
        try {
            this.nuevoTrabajador.setFechaIngreso(LocalDate.now());
            trabajadorService.altaTrabajador(this.nuevoTrabajador);
            this.nuevoTrabajador = new TrabajadorSalud();
            this.cargarLista();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- NUEVO MÉTODO DE ACCIÓN PARA BUSCAR ---
    public void buscarPorCedula() {
        this.trabajadorEncontrado = trabajadorService.obtenerTrabajadorPorCedula(this.cedulaBusqueda);

        // Si no se encuentra, mostramos un mensaje al usuario
        if (this.trabajadorEncontrado == null) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_WARN, "No encontrado", "No existe un trabajador con la cédula ingresada."));
        }
    }

    // --- GETTERS Y SETTERS (EXISTENTES Y NUEVOS) ---

    // Getters y setters para el listado y alta (ya los tienes)
    public List<TrabajadorSalud> getTrabajadores() { return trabajadores; }
    public void setTrabajadores(List<TrabajadorSalud> t) { this.trabajadores = t; }
    public TrabajadorSalud getNuevoTrabajador() { return nuevoTrabajador; }
    public void setNuevoTrabajador(TrabajadorSalud t) { this.nuevoTrabajador = t; }

    // NUEVOS Getters y Setters para la búsqueda
    public String getCedulaBusqueda() { return cedulaBusqueda; }
    public void setCedulaBusqueda(String cedulaBusqueda) { this.cedulaBusqueda = cedulaBusqueda; }
    public TrabajadorSalud getTrabajadorEncontrado() { return trabajadorEncontrado; }
    public void setTrabajadorEncontrado(TrabajadorSalud t) { this.trabajadorEncontrado = t; }
}