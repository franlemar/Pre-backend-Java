import controlador.Controlador;
import modelo.GestionCarrito;
import modelo.GestionPedido;
import modelo.GestionProducto;
import modelo.GestionUsuario;
import modelo.utilidades.GestorLogs;
import vista.Vista;

public class Main {

    public static void main(String[] args) {
        Vista vista = new Vista();
        GestorLogs logs = new GestorLogs("datos/registro_actividad.log");

        GestionProducto gestionProducto = new GestionProducto();
        GestionUsuario gestionUsuario = new GestionUsuario();
        GestionCarrito gestionCarrito = new GestionCarrito(gestionProducto);
        GestionPedido gestionPedido = new GestionPedido(gestionProducto);

        Controlador controlador = new Controlador(
                gestionProducto, gestionUsuario, gestionCarrito, gestionPedido, vista, logs);

        controlador.iniciarTienda();
    }
}
