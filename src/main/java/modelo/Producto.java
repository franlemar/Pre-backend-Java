package modelo;

public abstract class Producto {

    private final int id;
    private String nombre;
    private double precio;
    private int stock;
    private CategoriaProducto categoria;

    public Producto(int id, String nombre, double precio, int stock, CategoriaProducto categoria){
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
    }

    //*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.MÉTODOS ABSTRACTOS.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*
    public abstract String getTipoProducto();

    public abstract double calcularImporte(int cantidad);

    //*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.GETTERS Y SETTERS.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public CategoriaProducto getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaProducto categoria) {
        this.categoria = categoria;
    }

    //*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.TO STRING.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*
    public String toString(){
        return "ID: " + id
                + " | " + nombre
                + " | Tipo: " + getTipoProducto()
                + " | PVP: " + precio + " €"
                + " | Stock: " + stock
                + " | Categoría: " + categoria;
    }
}
