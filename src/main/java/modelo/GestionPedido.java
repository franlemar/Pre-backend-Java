package modelo;

import modelo.utilidades.FuncionesPrecios;
import modelo.utilidades.GestorCSV;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class GestionPedido {

    private static final String RUTA_FICHERO = "datos/pedidos.csv";
    private static final String CABECERA = "numero;fecha;id_usuario;email;total;detalle";

    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private ArrayList<Pedido> pedidos;
    private int proximoNumeroFactura;
    private GestionProducto gestionProducto;

    public GestionPedido(GestionProducto gestionProducto) {
        this.pedidos = new ArrayList<>();
        this.proximoNumeroFactura = 1;
        this.gestionProducto = gestionProducto;
    }

    public Pedido cerrarPedido(Usuario usuario, Carrito carrito) {
        if(carrito.estaVacio() || !usuario.isActivo()){
            return null;
        }

        // 1. Validar que hay stock de todo
        for(int idProducto : carrito.getProductos().keySet()){
            int cantidad = carrito.getCantidadDe(idProducto);
            Producto producto = gestionProducto.buscarPorId(idProducto);
            if(producto == null || !gestionProducto.hayStock(producto, cantidad)){
                return null;
            }
        }

        // 2. Construir factura y descontar stock
        ArrayList<LineaPedido> lineasFactura = new ArrayList<>();
        for(int idProducto : carrito.getProductos().keySet()){
            int cantidad = carrito.getCantidadDe(idProducto);
            Producto producto = gestionProducto.buscarPorId(idProducto);
            if(producto != null){
                lineasFactura.add(new LineaPedido(producto, cantidad));
                gestionProducto.descontarStock(producto, cantidad);
            }
        }

        Pedido pedido = new Pedido(proximoNumeroFactura, usuario, lineasFactura);
        pedidos.add(pedido);
        proximoNumeroFactura++;

        carrito.vaciarCarrito();
        return pedido;
    }

    public ArrayList<Pedido> getHistorial() {
        return pedidos;
    }

    public ArrayList<Pedido> getHistorial(Usuario usuario) {
        ArrayList<Pedido> filtrados = new ArrayList<>();
        for(Pedido pedido : pedidos) {
            if(pedido.getUsuario().getId() == usuario.getId()) {
                filtrados.add(pedido);
            }
        }
        return filtrados;
    }

    public int getNumPedidos() {
        return pedidos.size();
    }

    public void guardarEnCSV() {
        ArrayList<String> lineas = new ArrayList<>();
        lineas.add(CABECERA);

        for(Pedido pedido : pedidos){
            lineas.add(pedido.getNumeroFactura() + ";"
                    + pedido.getFecha().format(FORMATO_FECHA) + ";"
                    + pedido.getUsuario().getId() + ";"
                    + pedido.getUsuario().getEmail() + ";"
                    + FuncionesPrecios.formatearPrecioParaFichero(pedido.calcularTotal()) + ";"
                    + serializarLineas(pedido));
        }
        GestorCSV.escribir(RUTA_FICHERO, lineas);
    }

    public int cargarDesdeCSV(GestionUsuario gestionUsuario) {
        ArrayList<String> lineas = GestorCSV.leer(RUTA_FICHERO);
        pedidos.clear();
        int descartados = 0;

        for(int i = 1; i < lineas.size(); i++){
            if(lineas.get(i).isBlank()){
                continue;
            }
            try{
                String[] campos = lineas.get(i).split(";", -1);
                int numero = Integer.parseInt(campos[0].trim());
                LocalDateTime fecha = LocalDateTime.parse(campos[1].trim(), FORMATO_FECHA);
                String email = campos[3];
                Usuario usuario = gestionUsuario.buscaPorEmail(email);

                if(usuario == null){
                    descartados++;
                    continue;
                }

                ArrayList<LineaPedido> lineasPedido = deserializarLineas(campos[5]);
                if (lineasPedido.isEmpty()) {
                    descartados++;
                    continue;
                }

                pedidos.add(new Pedido(numero, fecha, usuario, lineasPedido));

                if(numero >= proximoNumeroFactura){
                    proximoNumeroFactura = numero + 1;
                }
            }catch (RuntimeException e){
                descartados++;
            }
        }
        return descartados;
    }

    private String serializarLineas(Pedido pedido) {
        StringBuilder detalle = new StringBuilder();
        for(int i = 0; i < pedido.getLineas().size(); i++){
            LineaPedido linea = pedido.getLineas().get(i);
            detalle.append(linea.getProducto().getId()).append(":").append(linea.getCantidad());
            if (i < pedido.getLineas().size() - 1) {
                detalle.append(",");
            }
        }
        return detalle.toString();
    }

    private ArrayList<LineaPedido> deserializarLineas(String detalle) {
        ArrayList<LineaPedido> lineas = new ArrayList<>();
        if(detalle == null || detalle.isBlank()){
            return lineas;
        }

        String[] pares = detalle.split(",");
        for(String par : pares) {
            String[] partes = par.split(":");
            if(partes.length == 2) {
                try {
                    int idProducto = Integer.parseInt(partes[0].trim());
                    int cantidad = Integer.parseInt(partes[1].trim());
                    Producto producto = gestionProducto.buscarPorId(idProducto);
                    if (producto != null) {
                        lineas.add(new LineaPedido(producto, cantidad));
                    }
                } catch (NumberFormatException ignored) {}
            }
        }
        return lineas;
    }
}
