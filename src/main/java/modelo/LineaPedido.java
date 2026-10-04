package modelo;

import modelo.utilidades.FuncionesPrecios;

public class LineaPedido {

    private Producto producto;
    private int cantidad;

    public LineaPedido(Producto producto, int cantidad){
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public double calcularSubtotal() {
        return producto.calcularImporte(cantidad);
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getNombreProducto() {
        return producto != null ? producto.getNombre() : "";
    }

    public String getTipoProducto() {
        return producto != null ? producto.getTipoProducto() : "";
    }

    public double getPrecioUnitario() {
        return producto != null ? producto.getPrecio() : 0.0;
    }

    public int getIdProducto() {
        return producto != null ? producto.getId() : 0;
    }

    @Override
    public String toString() {
        return cantidad + " x " + producto.getNombre()
                + " (" + producto.getTipoProducto() + ")"
                + " | Subtotal: " + FuncionesPrecios.formatearPrecio(calcularSubtotal());
    }
}
