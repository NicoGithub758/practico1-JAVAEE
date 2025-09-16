package taller.practico1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class TrabajadorSaludDAO implements TrabajadorSaludDAOLocal, TrabajadorSaludDAORemote {

    // Usamos una lista sincronizada para seguridad en un entorno concurrente.
    private List<TrabajadorSalud> trabajadores;
    
    // Un generador de IDs atómico para asegurar IDs únicos.
    private final AtomicLong sequence = new AtomicLong(1);

    @PostConstruct
    public void init() {
        trabajadores = Collections.synchronizedList(new ArrayList<>());
    }

    @Override
    public void agregarTrabajador(TrabajadorSalud trabajador) {
        // Asignamos un ID único y lo agregamos a la lista.
        trabajador.setId(sequence.getAndIncrement());
        trabajadores.add(trabajador);
    }

    @Override
    public List<TrabajadorSalud> listarTrabajadores() {
        // Devolvemos una copia para evitar que el cliente modifique nuestra lista interna.
        return new ArrayList<>(trabajadores);
    }

    @Override
    public TrabajadorSalud buscarTrabajadorPorCedula(String cedula) {
        for (TrabajadorSalud t : trabajadores) {
            if (t.getCedula().equals(cedula)) {
                return t;
            }
        }
        return null; // Retornamos null si no se encuentra.
    }
}