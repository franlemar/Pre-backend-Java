package modelo;

import java.util.ArrayList;

public class GestionCarrito {

    private Carrito carrito;
    private GestionProducto gestionProducto;

    public GestionCarrito(GestionProducto gestionProducto) {
        this.carrito = new Carrito();
        this.gestionProducto = gestionProducto;
    }

    public boolean anadirAlCarrito(int idProducto, int cantidad) {
        Producto producto = gestionProducto.buscarPorId(idProducto);

        if(producto == null || cantidad <= 0){
            return false;
        }

        int cantidadTotal = carrito.getCantidadDe(idProducto) + cantidad;

        if(!gestionProducto.hayStock(producto, cantidadTotal)){
            return false;
        }

        return carrito.anadirProducto(idProducto, cantidad);
    }

    public boolean quitarDelCarrito(int idProducto) {
        return carrito.quitarProducto(idProducto);
    }

    public double calcularTotal() {
        double total = 0;
        for(LineaPedido linea : getLineas()){
            total += linea.calcularSubtotal();
        }
        return total;
    }

    public ArrayList<LineaPedido> getLineas() {
        ArrayList<LineaPedido> lineas = new ArrayList<>();
        for(int idProducto : carrito.getProductos().keySet()){
            int cantidad = carrito.getCantidadDe(idProducto);
            Producto producto = gestionProducto.buscarPorId(idProducto);
            if(producto != null){
                lineas.add(new LineaPedido(producto, cantidad));
            }
        }
        return lineas;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public boolean estaVacio() {
        return carrito.estaVacio();
    }

    public int getNumUnidades() {
        return carrito.getNumUnidades();
    }

    public int getCantidadDe(int idProducto) {
        return carrito.getCantidadDe(idProducto);
    }

    public void vaciarCarrito() {
        carrito.vaciarCarrito();
    }
}
