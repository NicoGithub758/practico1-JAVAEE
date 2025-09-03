package taller.practico1;

import java.util.List;
import jakarta.ejb.Local;

@Local
public interface TrabajadorSaludServiceLocal {

    void altaTrabajador(TrabajadorSalud trabajador) throws Exception;

    List<TrabajadorSalud> obtenerTodosLosTrabajadores();

    TrabajadorSalud obtenerTrabajadorPorCedula(String cedula);

}