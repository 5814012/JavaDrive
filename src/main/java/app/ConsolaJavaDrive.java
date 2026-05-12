package app;

import logica.*;
import model.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ConsolaJavaDrive {
    private final GestorClientes gestorClientes;
    private final GestorFlota gestorFlota;
    private final GestorReservas gestorReservas;
    private final GestorInformes gestorInformes;
    private final Scanner sc;

    public ConsolaJavaDrive() {
        gestorClientes = new GestorClientes();
        gestorFlota = new GestorFlota();
        gestorReservas = new GestorReservas();
        gestorInformes = new GestorInformes();
        sc = new Scanner(System.in);
    }

    public void iniciar() {
        System.out.println("JavaDrive. El super gestor de reservas para tu alquiler de super vehiculos.\n");
        gestorClientes.cargarClientes();
        gestorFlota.cargarVehiculos();
        int i;
        do {
            i = mostrarMenu();
            switch (i) {
                case 1 -> listarVehiculosDisponibles();
                case 2 -> crearCliente();
                case 3 -> crearVehiculo();
                case 4 -> pedirDatosReserva();
                case 5 -> generarInformeXML();
                case 0 -> {
                    gestorClientes.guardarClientes();
                    gestorFlota.guardarVehiculos();
                    System.out.println("datos guardados.");
                }
                default -> System.out.println("[ERROR] Opcion no valida.");
            }
        } while (i != 0);
    }

    void listarVehiculosDisponibles() {
        System.out.println("\n-> VEHICULOS DISPONIBLES <-");
        var disponibles = gestorFlota.getDisponibles();
        if (disponibles.isEmpty()) {
            System.out.println("No hay vehiculos disponibles.");
        } else {
            for (Vehiculo v : disponibles) {
                System.out.println(v + " | " + v.obtenerDetalles());
            }
        }
        System.out.println();
    }

    void crearCliente() {
        System.out.print("DNI: ");
        String dni = sc.nextLine().trim();
        if (gestorClientes.buscarCliente(dni) != null) {
            System.out.println("[ERROR] Ya existe un cliente con ese DNI.");
            return;
        }
        System.out.print("Nombre completo: ");
        String nombre = sc.nextLine().trim();
        System.out.print("Telefono: ");
        String telefono = sc.nextLine().trim();
        gestorClientes.addCliente(new Cliente(dni, nombre, telefono));
        System.out.println("Cliente creado correctamente.");
    }

    void crearVehiculo() {
        System.out.print("Tipo (COCHE/FURGONETA): ");
        String tipo = sc.nextLine().trim().toUpperCase();
        System.out.print("Matricula: ");
        String matricula = sc.nextLine().trim();
        System.out.print("Marca: ");
        String marca = sc.nextLine().trim();
        System.out.print("Modelo: ");
        String modelo = sc.nextLine().trim();

        if (tipo.equals("COCHE")) {
            System.out.print("Tipo de coche (Pequeño/Familiar/Deportivo): ");
            String tipoCoche = sc.nextLine().trim();
            Tipo tipos = Tipo.valueOf(tipoCoche.toUpperCase());
            System.out.print("Numero de plazas (2-7): ");
            int plazas = Integer.parseInt(sc.nextLine().trim());
            gestorFlota.addVehiculo(new Coche(matricula, marca, modelo, tipos, plazas));
            System.out.println("Coche añadido.");
        } else if (tipo.equals("FURGONETA")) {
            System.out.print("Es de carga? (true/false): ");
            boolean esCarga = Boolean.parseBoolean(sc.nextLine().trim());
            System.out.print(esCarga ? "Capacidad (kg): " : "Capacidad (personas): ");
            int capacidad = Integer.parseInt(sc.nextLine().trim());
            gestorFlota.addVehiculo(new Furgoneta(matricula, marca, modelo, esCarga, capacidad));
            System.out.println("Furgoneta añadida.");
        } else {
            System.out.println("[ERROR] Ese tipo de vehiculo no existe.");
        }
    }

    void pedirDatosReserva() {
        System.out.print("DNI del cliente: ");
        String dni = sc.nextLine().trim();
        Cliente cliente = gestorClientes.buscarCliente(dni);
        if (cliente == null) {
            System.out.println("[ERROR] Cliente no encontrado.");
            return;
        }

        System.out.print("Matricula del vehiculo: ");
        String matricula = sc.nextLine().trim();
        Vehiculo vehiculo = gestorFlota.buscarVehiculo(matricula);
        if (vehiculo == null) {
            System.out.println("[ERROR] Vehiculo no encontrado.");
            return;
        }
        if (!vehiculo.isDisponible()) {
            System.out.println("[ERROR] El vehiculo no esta disponible.");
            return;
        }

        try {
            System.out.print("Fecha de recogida (YYYY-MM-DD): ");
            LocalDate fI = LocalDate.parse(sc.nextLine().trim());
            System.out.print("Fecha de devolucion (YYYY-MM-DD): ");
            LocalDate fF = LocalDate.parse(sc.nextLine().trim());
            if (!fF.isAfter(fI)) {
                System.out.println("[ERROR] La fecha de devolucion debe ser mas tarde a la de recogida.");
                return;
            }
            Reserva reserva = gestorReservas.realizarReserva(cliente, vehiculo, fI, fF);
            System.out.println("Reserva R" + reserva.getIdReserva() + " realizada.");
        } catch (DateTimeParseException e) {
            System.out.println("[ERROR] Formato incorrecto.");
        }
    }

    void generarInformeXML() {
        gestorInformes.generarInformeXML(
                gestorClientes.getClientes(),
                gestorFlota.getVehiculos()
        );
        System.out.println("Se ha generado el XML en: data/reporte_completo.xml");
    }

    int mostrarMenu() {
        System.out.println("""
                ╔═════════════════════════════════════════╗ ╔═════════════════════════════════════╗
                ║   JAVADRIVE                             ║ ║ Hecho por Jose Miguel y Jose Daniel ║
                ╠═════════════════════════════════════════╣ ╚═════════════════════════════════════╝
                ║   1. Listar vehiculos disponibles       ║ ╔═════════════╗
                ║   2. Crear cliente                      ║ ║  Creditos   ║
                ║   3. Añadir vehiculo                    ║ ║  del menu   ║
                ║   4. Realizar reserva                   ║ ║  a Jose     ║
                ║   5. Generar informe (XML)              ║ ║  Luis F.L   ║
                ║   0. Salir                              ║ ║             ║
                ╚═════════════════════════════════════════╝ ╚═════════════╝""");
        System.out.print("-> ");
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
