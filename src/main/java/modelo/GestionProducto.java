package modelo;

import modelo.utilidades.FuncionesPrecios;
import modelo.utilidades.GestorCSV;

import java.util.ArrayList;
import java.util.HashMap;

public class GestionProducto {

    private static final String RUTA_FICHERO = "datos/productos.csv";

    private static final String CABECERA =
            "id;nombre;precio;stock;categoria;tipo;peso;gastos_envio;peso_descarga;licencia";

    private HashMap<Integer, Producto> productos;
    private int proximoId;

    public GestionProducto() {
        this.productos = new HashMap<>();
        this.proximoId = 1;
    }

    public int getProximoId() {
        return proximoId;
    }

    public boolean aniadirProducto(Producto nuevoProducto) {
        if(nuevoProducto == null){
            return false;
        }

        if(productos.containsKey(nuevoProducto.getId())){
            return false;
        }

        if(nuevoProducto.getPrecio() < 0){
            return false;
        }

        productos.put(nuevoProducto.getId(), nuevoProducto);

        if(nuevoProducto.getId() >= proximoId){
            proximoId = nuevoProducto.getId() + 1;
        }

        return true;
    }

    public Producto buscarPorId(int idProducto) {
        return productos.get(idProducto);
    }

    public int getNumProductos() {
        return productos.size();
    }

    public Producto eliminaProducto(int idProducto) {
        return productos.remove(idProducto);
    }

    public boolean hayStock(Producto producto, int cantidad) {
        return cantidad > 0 && producto.getStock() >= cantidad;
    }

    public boolean descontarStock(Producto producto, int cantidad) {
        if(!hayStock(producto, cantidad)){
            return false;
        }
        producto.setStock(producto.getStock() - cantidad);
        return true;
    }

    public ArrayList<Producto> buscarPorCategoria(CategoriaProducto categoria) {
        ArrayList<Producto> resultado = new ArrayList<>();
        for(Producto producto : productos.values()){
            if(producto.getCategoria() == categoria){
                resultado.add(producto);
            }
        }
        resultado.sort((p1, p2) -> Integer.compare(p1.getId(), p2.getId()));
        return resultado;
    }

    public ArrayList<Producto> buscarPorRangoPrecio(double precioMinimo, double precioMaximo) {
        ArrayList<Producto> resultado = new ArrayList<>();
        for(Producto producto : productos.values()){
            if(producto.getPrecio() >= precioMinimo && producto.getPrecio() <= precioMaximo){
                resultado.add(producto);
            }
        }
        resultado.sort((p1, p2) -> Integer.compare(p1.getId(), p2.getId()));
        return resultado;
    }

    public ArrayList<Producto> getProductos() {
        ArrayList<Producto> lista = new ArrayList<>(productos.values());
        lista.sort((p1, p2) -> Integer.compare(p1.getId(), p2.getId()));
        return lista;
    }

    public ArrayList<Producto> ordenarPorNombre() {
        ArrayList<Producto> resultado = new ArrayList<>(productos.values());
        resultado.sort((p1, p2) -> p1.getNombre().compareToIgnoreCase(p2.getNombre()));
        return resultado;
    }

    public void guardarEnCSV() {
        ArrayList<String> lineas = new ArrayList<>();
        lineas.add(CABECERA);

        for(Producto producto : productos.values()){
            lineas.add(convertirEnLinea(producto));
        }

        GestorCSV.escribir(RUTA_FICHERO, lineas);
    }

    public void cargarDesdeCSV() {
        ArrayList<String> lineas = GestorCSV.leer(RUTA_FICHERO);
        productos.clear();

        for(int i = 1; i < lineas.size(); i++){
            if(lineas.get(i).isBlank()){
                continue;
            }

            Producto producto = convertirEnProducto(lineas.get(i));

            if(producto != null){
                productos.put(producto.getId(), producto);
                if(producto.getId() >= proximoId){
                    proximoId = producto.getId() + 1;
                }
            }
        }

        if(productos.isEmpty()){
            cargarProductosDeEjemplo();
            guardarEnCSV();
        }
    }

    private void cargarProductosDeEjemplo() {
        Producto p1 = new ProductoFisico(proximoId++, "Ratón inalámbrico", 24.99, 15, CategoriaProducto.ELECTRONICA, 0.12, 3.50);
        productos.put(p1.getId(), p1);
        
        Producto p2 = new ProductoFisico(proximoId++, "Camiseta de algodón", 12.50, 40, CategoriaProducto.ROPA, 0.25, 4.00);
        productos.put(p2.getId(), p2);
        
        Producto p3 = new ProductoDigital(proximoId++, "Antivirus premium", 39.90, 100, CategoriaProducto.ELECTRONICA, 0.45, "LIC-2024-XK9");
        productos.put(p3.getId(), p3);
        
        Producto p4 = new ProductoDigital(proximoId++, "Curso de Java", 89.00, 200, CategoriaProducto.LIBROS, 2.30, "EDU-JAVA-01");
        productos.put(p4.getId(), p4);
        
        Producto p5 = new ProductoFisico(proximoId++, "Cafetera automática", 89.95, 8, CategoriaProducto.HOGAR, 3.80, 6.20);
        productos.put(p5.getId(), p5);
        
        Producto p6 = new ProductoFisico(proximoId++, "Caja de galletas", 3.75, 120, CategoriaProducto.ALIMENTACION, 0.45, 2.50);
        productos.put(p6.getId(), p6);
    }

    private String convertirEnLinea(Producto producto) {
        double peso = 0;
        double gastosEnvio = 0;
        double pesoDescarga = 0;
        String licencia = "";

        if (producto instanceof ProductoFisico fisico) {
            peso = fisico.getPeso();
            gastosEnvio = fisico.getGastosEnvio();
        } else if (producto instanceof ProductoDigital digital) {
            pesoDescarga = digital.getPesoDescarga();
            licencia = digital.getLicencia();
        }

        return producto.getId() + ";"
                + producto.getNombre() + ";"
                + FuncionesPrecios.formatearPrecioParaFichero(producto.getPrecio()) + ";"
                + producto.getStock() + ";"
                + producto.getCategoria().name() + ";"
                + producto.getTipoProducto() + ";"
                + FuncionesPrecios.formatearPrecioParaFichero(peso) + ";"
                + FuncionesPrecios.formatearPrecioParaFichero(gastosEnvio) + ";"
                + FuncionesPrecios.formatearPrecioParaFichero(pesoDescarga) + ";"
                + licencia;
    }

    private Producto convertirEnProducto(String linea) {
        try{
            String[] campos = linea.split(";", -1);
            int id = Integer.parseInt(campos[0].trim());
            String nombre = campos[1];
            double precio = Double.parseDouble(campos[2].trim());
            int stock = Integer.parseInt(campos[3].trim());
            CategoriaProducto categoria = CategoriaProducto.valueOf(campos[4].trim());
            String tipo = campos[5].trim();

            if("FISICO".equalsIgnoreCase(tipo)){
                double peso = Double.parseDouble(campos[6].trim());
                double gastosEnvio = Double.parseDouble(campos[7].trim());
                return new ProductoFisico(id, nombre, precio, stock, categoria, peso, gastosEnvio);
            }

            double pesoDescarga = Double.parseDouble(campos[8].trim());
            return new ProductoDigital(id, nombre, precio, stock, categoria, pesoDescarga, campos[9]);

        }catch (RuntimeException e){
            return null;
        }
    }
}
