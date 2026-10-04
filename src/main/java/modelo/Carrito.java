package modelo;

import java.util.LinkedHashMap;

public class Carrito {

    private final LinkedHashMap<Integer, Integer> productos;

    public Carrito() {
        this.productos = new LinkedHashMap<>();
    }

    //*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.CRUD.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*

    public boolean anadirProducto(int idProducto, int cantidad) {
        if(cantidad <= 0){
            return false;
        }
        int cantidadTotal = productos.getOrDefault(idProducto, 0) + cantidad;
        productos.put(idProducto, cantidadTotal);

        return true;
    }

    public boolean quitarProducto(int idProducto) {
        return productos.remove(idProducto) != null;
    }

    public void vaciarCarrito() {
        productos.clear();
    }

    public boolean contieneProducto(int idProducto) {
        return productos.containsKey(idProducto);
    }

    public int getCantidadDe(int idProducto) {
        return productos.getOrDefault(idProducto, 0);
    }

    public int getNumUnidades() {
        int unidades = 0;
        for(Integer cantidad : productos.values()){
            unidades += cantidad;
        }
        return unidades;
    }

    public LinkedHashMap<Integer, Integer> getProductos() {
        return productos;
    }

    public boolean estaVacio() {
        return productos.isEmpty();
    }
}
