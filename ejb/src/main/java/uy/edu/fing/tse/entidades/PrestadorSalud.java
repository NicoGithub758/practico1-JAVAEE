package uy.edu.fing.tse.entidades;

import java.time.LocalDate;
//import java.util.List;

public class PrestadorSalud {
    private long id;
    private String nombre;
    private String rut;
    private LocalDate fechaAlta;
    private boolean activo;
    //private List<UsuarioServicioSalud> lstUsuariosAfiliados;
    //private List<TrabajadorSalud> lstEmpleados;
    //private List<DocumentoClinico> lstDocumentoClinicoS;


    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
        public String getRut() {
        return rut;
    }
    public void setRut(String rut) {
        this.rut = rut;
    }
    public LocalDate getFechaAlta() {
        return fechaAlta;
    }
    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }
    public boolean isActivo() {
        return activo;
    }
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
