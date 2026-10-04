package modelo;

import modelo.utilidades.FuncionesPrecios;

public class ProductoFisico extends Producto {

    private double peso;
    private double gastosEnvio;

    public ProductoFisico(int id, String nombre, double precio, int stock, CategoriaProducto categoria,
                          double peso, double gastosEnvio){
        super(id, nombre, precio, stock, categoria);
        this.peso = peso;
        this.gastosEnvio = gastosEnvio;
    }

    public String getTipoProducto() {
        return "FISICO";
    }

    public double calcularImporte(int cantidad) {
        return (getPrecio() * cantidad) + gastosEnvio;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getGastosEnvio() {
        return gastosEnvio;
    }

    public void setGastosEnvio(double gastosEnvio) {
        this.gastosEnvio = gastosEnvio;
    }

    public String toString() {
        return super.toString()
                + " | Peso: " + peso + " kg"
                + " | Gastos de envío: " + FuncionesPrecios.formatearPrecio(gastosEnvio);
    }
}
