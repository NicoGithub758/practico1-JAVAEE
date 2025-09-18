package uy.edu.fing.tse.api;

import java.util.List;

import jakarta.ejb.Remote;
import uy.edu.fing.tse.entidades.PrestadorSalud;

@Remote
public interface PrestadorSaludServiceRemote extends PrestadorSaludServiceLocal {

    PrestadorSalud crear(PrestadorSalud prestador);
    PrestadorSalud obtener(String rut);
    void actualizar(PrestadorSalud prestador);
    void eliminar(String rut);
    List<PrestadorSalud> listar();

}
