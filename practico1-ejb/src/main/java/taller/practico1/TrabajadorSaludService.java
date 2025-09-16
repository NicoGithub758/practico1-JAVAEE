package taller.practico1;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class TrabajadorSaludService implements TrabajadorSaludServiceLocal {

    // ¡Inyección de Dependencias!
    @EJB
    private TrabajadorSaludDAOLocal trabajadorDAO;

    @Override
    public void altaTrabajador(TrabajadorSalud trabajador) throws Exception {
        // --- REGLA DE NEGOCIO ---
        // 1. Validar que la cédula no esté vacía.
        if (trabajador.getCedula() == null || trabajador.getCedula().trim().isEmpty()) {
            throw new Exception("La cédula no puede estar vacía.");
        }

        // 2. Validar que la cédula no exista previamente.
        if (trabajadorDAO.buscarTrabajadorPorCedula(trabajador.getCedula()) != null) {
            throw new Exception("Ya existe un trabajador con la cédula " + trabajador.getCedula());
        }

        // Si todas las validaciones pasan, persistimos el trabajador.
        trabajadorDAO.agregarTrabajador(trabajador);
    }

    @Override
    public List<TrabajadorSalud> obtenerTodosLosTrabajadores() {
        return trabajadorDAO.listarTrabajadores();
    }

    @Override
    public TrabajadorSalud obtenerTrabajadorPorCedula(String cedula) {
        return trabajadorDAO.buscarTrabajadorPorCedula(cedula);
    }
}