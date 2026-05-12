package logica;

import model.*;
import java.io.*;
import java.time.LocalDate;

public class GestorReservas {
    private int contadorReservas = 10000000;

    public Reserva realizarReserva(Cliente c, Vehiculo v, LocalDate fInicio, LocalDate fFin) {
        v.setDisponible(false);
        contadorReservas++;
        Reserva reserva = new Reserva(contadorReservas, c, v, fInicio, fFin);
        exportarTicket(reserva);
        return reserva;
    }

    public void exportarTicket(Reserva r) {
        new File("data").mkdirs();
        String nombreFichero = "data/reserva_R" + r.getIdReserva() + ".txt";
        try (PrintWriter pw = new PrintWriter(new FileWriter(nombreFichero))) {
            pw.print(r.generarLineaTicket());
        } catch (IOException e) {
            System.out.println("[ERROR] Exportando ticket: " + e.getMessage());
        }
    }

    public int getContadorReservas() {
        return contadorReservas;
    }
}
