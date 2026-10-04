package modelo;

public class ProductoDigital extends Producto {

    private double pesoDescarga;
    private String licencia;

    public ProductoDigital(int id, String nombre, double precio, int stock, CategoriaProducto categoria,
                           double pesoDescarga, String licencia){
        super(id, nombre, precio, stock, categoria);
        this.pesoDescarga = pesoDescarga;
        this.licencia = licencia;
    }

    public String getTipoProducto() {
        return "DIGITAL";
    }

    public double calcularImporte(int cantidad) {
        return getPrecio() * cantidad;
    }

    public double getPesoDescarga() {
        return pesoDescarga;
    }

    public void setPesoDescarga(double pesoDescarga) {
        this.pesoDescarga = pesoDescarga;
    }

    public String getLicencia() {
        return licencia;
    }

    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }

    public String toString() {
        return super.toString()
                + " | Tamaño de descarga: " + pesoDescarga + " GB"
                + " | Licencia: " + licencia;
    }
}
