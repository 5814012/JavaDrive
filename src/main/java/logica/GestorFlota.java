package logica;

import model.*;
import java.util.ArrayList;
import java.util.List;

public class GestorFlota {
    private final List<Vehiculo> vehiculos = new ArrayList<>();
    private static final String VEHICULOS_TXT = "data/vehiculos.txt";

    public void cargarVehiculos() {
        vehiculos.clear();
        vehiculos.addAll(GestorPersistencia.cargarVehiculos(VEHICULOS_TXT));
    }

    public void guardarVehiculos() {
        GestorPersistencia.guardarVehiculos(VEHICULOS_TXT, vehiculos);
    }

    public void addVehiculo(Vehiculo v) {
        vehiculos.add(v);
    }

    public Vehiculo buscarVehiculo(String matricula) {
        for (Vehiculo v : vehiculos) {
            if (v.getMatricula().equalsIgnoreCase(matricula)) return v;
        }
        return null;
    }

    public List<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public List<Vehiculo> getDisponibles() {
        List<Vehiculo> disponibles = new ArrayList<>();
        for (Vehiculo v : vehiculos) {
            if (v.isDisponible()) disponibles.add(v);
        }
        return disponibles;
    }
}
