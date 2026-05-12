package model;

public class Coche extends Vehiculo {
    private Tipo tipo;
    private int numPlazas;

    public Coche(String matricula, String marca, String modelo, Tipo tipo, int numPlazas) {
        super(matricula, marca, modelo);
        this.tipo = tipo;
        this.numPlazas = numPlazas;
    }

    public Tipo getTipo() { return tipo; }
    public void setTipo(Tipo tipo) { this.tipo = tipo; }
    public int getNumPlazas() { return numPlazas; }
    public void setNumPlazas(int numPlazas) { this.numPlazas = numPlazas; }

    @Override
    public String obtenerDetalles() {
        return "model.Coche " + tipo + ", Plazas: " + numPlazas;
    }
}
