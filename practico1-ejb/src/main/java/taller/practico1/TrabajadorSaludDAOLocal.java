package taller.practico1;

import java.util.List;
import javax.ejb.Local;

@Local
public interface TrabajadorSaludDAOLocal {
    
    void agregarTrabajador(TrabajadorSalud trabajador);
    
    List<TrabajadorSalud> listarTrabajadores();

    TrabajadorSalud buscarTrabajadorPorCedula(String cedula);

}
