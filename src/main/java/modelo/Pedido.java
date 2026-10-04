package modelo;

import modelo.utilidades.FuncionesPrecios;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Pedido {

    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");

    private final int numeroFactura;
    private final LocalDateTime fecha;
    private final Usuario usuario;
    private final ArrayList<LineaPedido> lineas;

    public Pedido(int numeroFactura, Usuario usuario, ArrayList<LineaPedido> lineas){
        this.numeroFactura = numeroFactura;
        this.fecha = LocalDateTime.now();
        this.usuario = usuario;
        this.lineas = new ArrayList<>(lineas);
    }

    public Pedido(int numeroFactura, LocalDateTime fecha, Usuario usuario, ArrayList<LineaPedido> lineas){
        this.numeroFactura = numeroFactura;
        this.fecha = fecha;
        this.usuario = usuario;
        this.lineas = new ArrayList<>(lineas);
    }

    public double calcularTotal() {
        double total = 0;
        for(LineaPedido linea : lineas){
            total += linea.calcularSubtotal();
        }
        return total;
    }

    public String getFechaLegible() {
        return fecha.format(FORMATO_FECHA);
    }

    public int getNumeroFactura() {
        return numeroFactura;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public ArrayList<LineaPedido> getLineas() {
        return lineas;
    }

    public String getNombreUsuario() {
        return usuario != null ? usuario.getNombre() : "";
    }

    public int getNumeroLineas() {
        return lineas.size();
    }

    public int getNumeroUnidades() {
        int unidades = 0;
        for(LineaPedido linea : lineas){
            unidades += linea.getCantidad();
        }
        return unidades;
    }

    @Override
    public String toString() {
        return "Factura nº " + numeroFactura
                + " | " + getFechaLegible()
                + " | " + usuario.getNombre()
                + " | " + getNumeroUnidades() + " unidades"
                + " | Total: " + FuncionesPrecios.formatearPrecio(calcularTotal());
    }
}
