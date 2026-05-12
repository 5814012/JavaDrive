package logica;

import model.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestorPersistencia {

    public static List<Cliente> cargarClientes(String path) {
        List<Cliente> clientes = new ArrayList<>();
        File file = new File(path);
        if (!file.exists()) return clientes;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String l;
            while ((l = br.readLine()) != null) {
                l = l.trim();
                if (l.isEmpty()) continue;
                String[] p = l.split(";");
                if (p.length == 3) {
                    clientes.add(new Cliente(p[0], p[1], p[2]));
                }
            }
        } catch (IOException e) {
            System.out.println("[ERROR] Cargando clientes: " + e.getMessage());
        }
        return clientes;
    }

    public static void guardarClientes(String path, List<Cliente> clientes) {
        new File("data").mkdirs();
        try (PrintWriter pw = new PrintWriter(new FileWriter(path))) {
            for (Cliente c : clientes) {
                pw.println(c.getDni() + ";" + c.getNombre() + ";" + c.getTelefono());
            }
        } catch (IOException e) {
            System.out.println("[ERROR] Guardando clientes: " + e.getMessage());
        }
    }

    public static List<Vehiculo> cargarVehiculos(String path) {
        List<Vehiculo> vehiculos = new ArrayList<>();
        File file = new File(path);
        if (!file.exists()) return vehiculos;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String l;
            while ((l = br.readLine()) != null) {
                l = l.trim();
                if (l.isEmpty()) continue;
                String[] p = l.split(";");
                if (p[0].equalsIgnoreCase("COCHE") && p.length == 7) {
                    Tipo tipo = Tipo.valueOf(p[5].toUpperCase());
                    Coche c = new Coche(p[1], p[2], p[3], tipo, Integer.parseInt(p[6]));
                    c.setDisponible(Boolean.parseBoolean(p[4]));
                    vehiculos.add(c);
                } else if (p[0].equalsIgnoreCase("FURGONETA") && p.length == 7) {
                    Furgoneta furg = new Furgoneta(p[1], p[2], p[3], Boolean.parseBoolean(p[5]), Integer.parseInt(p[6]));
                    furg.setDisponible(Boolean.parseBoolean(p[4]));
                    vehiculos.add(furg);
                }
            }
        } catch (IOException e) {
            System.out.println("[ERROR] Cargando vehiculos: " + e.getMessage());
        }
        return vehiculos;
    }

    public static void guardarVehiculos(String path, List<Vehiculo> vehiculos) {
        new File("data").mkdirs();
        try (PrintWriter pw = new PrintWriter(new FileWriter(path))) {
            for (Vehiculo v : vehiculos) {
                if (v instanceof Coche c) {
                    pw.println("COCHE;" + c.getMatricula() + ";" + c.getMarca() + ";" + c.getModelo() + ";" + c.isDisponible() + ";" + c.getTipo() + ";" + c.getNumPlazas());
                } else if (v instanceof Furgoneta furg) {
                    pw.println("FURGONETA;" + furg.getMatricula() + ";" + furg.getMarca() + ";" + furg.getModelo() + ";" + furg.isDisponible() + ";" + furg.isEsDeCarga() + ";" + furg.getCapacidad());
                }
            }
        } catch (IOException e) {
            System.out.println("[ERROR] Guardando vehiculos: " + e.getMessage());
        }
    }
}
