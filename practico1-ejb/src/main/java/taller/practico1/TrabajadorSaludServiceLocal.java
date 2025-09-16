package taller.practico1;

import java.util.List;
import javax.ejb.Local;

@Local
public interface TrabajadorSaludServiceLocal {

    void altaTrabajador(TrabajadorSalud trabajador) throws Exception;

    List<TrabajadorSalud> obtenerTodosLosTrabajadores();

    TrabajadorSalud obtenerTrabajadorPorCedula(String cedula);

}