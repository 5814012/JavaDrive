package logica;

import model.Cliente;
import java.util.ArrayList;
import java.util.List;

public class GestorClientes {
    private final List<Cliente> clientes = new ArrayList<>();
    private static final String CLIENTES_TXT = "data/clientes.txt";

    public void cargarClientes() {
        clientes.clear();
        clientes.addAll(GestorPersistencia.cargarClientes(CLIENTES_TXT));
    }

    public void guardarClientes() {
        GestorPersistencia.guardarClientes(CLIENTES_TXT, clientes);
    }

    public void addCliente(Cliente c) {
        clientes.add(c);
    }

    public Cliente buscarCliente(String dni) {
        for (Cliente c : clientes) {
            if (c.getDni().equalsIgnoreCase(dni)) return c;
        }
        return null;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }
}
