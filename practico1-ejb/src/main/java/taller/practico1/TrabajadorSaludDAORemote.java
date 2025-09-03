package taller.practico1;

import java.util.List;
import jakarta.ejb.Remote;

@Remote // La única diferencia
public interface TrabajadorSaludDAORemote {
    
    void agregarTrabajador(TrabajadorSalud trabajador);
    
    List<TrabajadorSalud> listarTrabajadores();

    TrabajadorSalud buscarTrabajadorPorCedula(String cedula);

}