package controlador;

import modelo.CategoriaProducto;
import modelo.GestionCarrito;
import modelo.GestionPedido;
import modelo.GestionProducto;
import modelo.GestionUsuario;
import modelo.Pedido;
import modelo.Producto;
import modelo.ProductoDigital;
import modelo.ProductoFisico;
import modelo.Usuario;
import modelo.UsuarioAdmin;
import modelo.UsuarioCliente;
import modelo.utilidades.FuncionesPrecios;
import modelo.utilidades.GestorLogs;
import vista.Vista;

import java.util.ArrayList;
import java.util.Scanner;

public class Controlador {

    private GestionProducto gestionProducto;
    private GestionUsuario gestionUsuario;
    private GestionCarrito gestionCarrito;
    private GestionPedido gestionPedido;
    private Vista vista;
    private GestorLogs logs;
    private Scanner s;

    private Usuario usuarioLogueado;

    public Controlador(GestionProducto gestionProducto, GestionUsuario gestionUsuario,
                       GestionCarrito gestionCarrito, GestionPedido gestionPedido,
                       Vista vista, GestorLogs logs) {
        this.gestionProducto = gestionProducto;
        this.gestionUsuario = gestionUsuario;
        this.gestionCarrito = gestionCarrito;
        this.gestionPedido = gestionPedido;
        this.vista = vista;
        this.logs = logs;
        this.s = new Scanner(System.in);
    }

    //*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.MENÚ DE INICIO.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*
    public void iniciarTienda() {
        vista.tituloPrograma();
        cargarDatos();

        int opcionMenu = 0;
        do {
            usuarioLogueado = null;
            vista.menuLogin();
            
            try {
                opcionMenu = Integer.parseInt(s.nextLine().trim());
            } catch (NumberFormatException e) {
                opcionMenu = -1;
            }

            switch(opcionMenu){
                case 1:
                    if (loginUsuario()) {
                        buclePrincipalAutenticado();
                    }
                    break;

                case 2:
                    registrarUsuarioNuevo();
                    break;

                case 3:
                    // Salir
                    break;

                default:
                    vista.opcionNoValida();
            }

        } while (opcionMenu != 3);

        vista.mostrarDespedida();
    }

    private boolean loginUsuario() {
        vista.pedirCorreo();
        String email = s.nextLine().trim();
        Usuario u = gestionUsuario.buscaPorEmail(email);

        if (u == null) {
            vista.errorUsuarioNoEncontrado(email);
            return false;
        }

        if (!u.isActivo()) {
            vista.errorUsuarioInactivo(u.getNombre());
            return false;
        }

        int intentosRestantes = 3;
        boolean passwordCorrecta = false;
        while (intentosRestantes > 0 && !passwordCorrecta) {
            vista.pedirPassword();
            String passIngresada = s.nextLine().trim();
            if (u.comprobarPassword(passIngresada)) {
                passwordCorrecta = true;
            } else {
                intentosRestantes--;
                if (intentosRestantes > 0) {
                    vista.errorPasswordIncorrecta(intentosRestantes);
                }
            }
        }

        if (!passwordCorrecta) {
            vista.mensajeError("Demasiados intentos fallidos. Inicio de sesión cancelado.");
            return false;
        }

        usuarioLogueado = u;
        vista.mensajeBienvenida(usuarioLogueado);
        logs.registrar("Inicio de sesión", "sistema", usuarioLogueado.getEmail());
        return true;
    }

    private void registrarUsuarioNuevo() {
        vista.pedirDatosUsuario("Introduce el nombre del nuevo usuario: ");
        String nombre = s.nextLine().trim();

        vista.pedirDatosUsuario("Introduce tu correo electrónico: ");
        String email = s.nextLine().trim();

        vista.pedirPassword();
        String password = s.nextLine().trim();
        if (password.length() < 4) {
            vista.mensajeError("La contraseña debe tener al menos 4 caracteres.");
            return;
        }

        vista.menuSeleccionRol();
        String opcionRol = s.nextLine().trim();
        
        Usuario usuario;
        int proximoId = gestionUsuario.getProximoId();
        if (opcionRol.equals("2")) {
            usuario = new UsuarioAdmin(proximoId, nombre, email, password, true);
        } else {
            usuario = new UsuarioCliente(proximoId, nombre, email, password, true);
        }

        if(gestionUsuario.aniadirUsuario(usuario)){
            gestionUsuario.guardarEnCSV();
            vista.registroCorrecto();
            logs.registrar("Registro nuevo usuario", "sistema", usuario.getEmail());
        } else {
            vista.mensajeError("El correo proporcionado no es válido o ya está en uso.");
        }
    }

    //*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.BUCLE PRINCIPAL (AUTENTICADO).*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*
    private void buclePrincipalAutenticado() {
        if (usuarioLogueado.esAdmin()) {
            bucleAdministrador();
        } else {
            bucleCliente();
        }
    }

    private void bucleAdministrador() {
        int opcionMenu = 0;
        do {
            vista.menuAdministrador();
            try {
                opcionMenu = Integer.parseInt(s.nextLine().trim());
            } catch (NumberFormatException e) {
                opcionMenu = -1;
            }

            switch(opcionMenu){
                case 1:
                    gestionProductos();
                    break;
                case 2:
                    gestionUsuarios();
                    break;
                case 3:
                    gestionCarritoMenu();
                    break;
                case 4:
                    cerrarPedido();
                    break;
                case 5:
                    vista.mostrarHistorial(gestionPedido.getHistorial());
                    break;
                case 6:
                    logs.registrar("Cierre de sesión", "sistema", usuarioLogueado.getEmail());
                    break;
                default:
                    vista.opcionNoValida();
            }
        } while (opcionMenu != 6);
    }

    private void bucleCliente() {
        int opcionMenu = 0;
        do {
            vista.menuCliente();
            try {
                opcionMenu = Integer.parseInt(s.nextLine().trim());
            } catch (NumberFormatException e) {
                opcionMenu = -1;
            }

            switch(opcionMenu){
                case 1:
                    vista.mostrarProductos(gestionProducto.getProductos());
                    break;
                case 2:
                    gestionCarritoMenu();
                    break;
                case 3:
                    cerrarPedido();
                    break;
                case 4:
                    vista.mostrarHistorial(gestionPedido.getHistorial(usuarioLogueado));
                    break;
                case 5:
                    logs.registrar("Cierre de sesión", "sistema", usuarioLogueado.getEmail());
                    break;
                default:
                    vista.opcionNoValida();
            }
        } while (opcionMenu != 5);
    }

    private void cargarDatos() {
        gestionProducto.cargarDesdeCSV();
        logs.registrar("Acceso a datos de productos", "sistema",
                gestionProducto.getNumProductos() + " productos cargados");

        gestionUsuario.cargarDesdeCSV();
        logs.registrar("Acceso a datos de usuarios", "sistema",
                gestionUsuario.getNumUsuarios() + " usuarios cargados");

        int descartados = gestionPedido.cargarDesdeCSV(gestionUsuario);
        logs.registrar("Acceso al historial de pedidos", "sistema",
                gestionPedido.getNumPedidos() + " pedidos cargados");

        if(descartados > 0){
            vista.mensajeError("Se han descartado " + descartados
                    + " pedidos cuyo cliente ya no existe en la tienda");
        }
    }

    //*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.MENÚ DE PRODUCTOS.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*
    private void gestionProductos() {
        int opcionMenu;

        do{
            vista.menuProductos();
            opcionMenu = Integer.parseInt(s.nextLine().trim());

            switch(opcionMenu){
                case 1:
                    altaProducto();
                    break;

                case 2:
                    bajaProducto();
                    break;

                case 3:
                    vista.mostrarProductos(gestionProducto.getProductos());
                    break;

                case 4:
                    buscarProductos();
                    break;

                case 5:
                    return;

                default:
                    vista.opcionNoValida();
            }

        }while(true);
    }

    private void altaProducto() {
        vista.tituloAltaProducto();
        vista.pedirTipoProducto();
        int opcionTipo = Integer.parseInt(s.nextLine().trim());
        
        while(opcionTipo != 1 && opcionTipo != 2){
            vista.opcionNoValida();
            vista.pedirTipoProducto();
            opcionTipo = Integer.parseInt(s.nextLine().trim());
        }

        vista.pedirDatosProducto("Introduce el nombre del producto: ");
        String nombre = s.nextLine().trim();

        vista.pedirCategoriaProducto();
        String catStr = s.nextLine().trim().toUpperCase();
        CategoriaProducto categoria;
        try {
            categoria = CategoriaProducto.valueOf(catStr);
        } catch(Exception e) {
            categoria = CategoriaProducto.ELECTRONICA; // fallback
        }

        vista.pedirStockProducto();
        int stock = Integer.parseInt(s.nextLine().trim());

        vista.pedirPrecioProducto();
        double precio = Double.parseDouble(s.nextLine().trim());

        Producto producto;
        if(opcionTipo == 1){
            vista.pedirPeso();
            vista.pedirDatosProducto("Introduce el peso del producto en kg: ");
            double peso = Double.parseDouble(s.nextLine().trim());

            vista.pedirGastosEnvio();
            vista.pedirDatosProducto("Introduce los gastos de envío en euros: ");
            double gastosEnvio = Double.parseDouble(s.nextLine().trim());

            producto = new ProductoFisico(gestionProducto.getProximoId(), nombre, precio, stock, categoria, peso, gastosEnvio);
        } else {
            vista.pedirPesoDescarga();
            vista.pedirDatosProducto("Introduce el tamaño de descarga en GB: ");
            double pesoDescarga = Double.parseDouble(s.nextLine().trim());

            vista.pedirLicencia();
            String licencia = s.nextLine().trim();

            producto = new ProductoDigital(gestionProducto.getProximoId(), nombre, precio, stock, categoria, pesoDescarga, licencia);
        }

        if(gestionProducto.aniadirProducto(producto)){
            gestionProducto.guardarEnCSV();
            vista.mensajeProductoCreado(producto);
            logs.registrar("Alta de producto", usuarioLogueado.getEmail(), producto.getNombre());
        } else {
            vista.mensajeError("Error al añadir el producto");
        }
    }

    private void bajaProducto() {
        if(gestionProducto.getNumProductos() == 0){
            vista.noHayProductos();
            return;
        }

        vista.mostrarProductos(gestionProducto.getProductos());
        vista.pedirIdProducto();
        int idProducto = Integer.parseInt(s.nextLine().trim());

        Producto producto = gestionProducto.buscarPorId(idProducto);

        if(producto == null){
            vista.errorProductoNoEncontrado(idProducto);
            return;
        }

        if(!vista.pideConfirmacion("¿Seguro que quieres eliminar '" + producto.getNombre() + "'?", s)){
            vista.operacionCancelada();
            return;
        }

        gestionProducto.eliminaProducto(idProducto);
        gestionCarrito.quitarDelCarrito(idProducto);
        gestionProducto.guardarEnCSV();
        vista.mensajeProductoEliminado(producto);
        logs.registrar("Baja de producto", usuarioLogueado.getEmail(), producto.getNombre());
    }

    private void buscarProductos() {
        if(gestionProducto.getNumProductos() == 0){
            vista.noHayProductos();
            return;
        }

        vista.menuBusquedaProducto();
        int opcion = Integer.parseInt(s.nextLine().trim());

        switch(opcion){
            case 1:
                buscarPorCategoria(s);
                break;

            case 2:
                buscarPorPrecio(s);
                break;

            case 3:
                return;

            default:
                vista.opcionNoValida();
        }
    }

    private void buscarPorCategoria(Scanner s) {
        vista.pedirCategoriaProducto();
        String entrada = s.nextLine().trim().toUpperCase();
        CategoriaProducto categoria = null;

        try{
            categoria = CategoriaProducto.valueOf(entrada);
        }catch (IllegalArgumentException e){
            vista.categoriaNoValida();
            return;
        }

        vista.mostrarProductos(gestionProducto.buscarPorCategoria(categoria));
    }

    private void buscarPorPrecio(Scanner s) {
        vista.pedirPrecioRango("Introduce el precio mínimo en euros: ");
        double minimo = Double.parseDouble(s.nextLine().trim().replace(',', '.'));

        vista.pedirPrecioRango("Introduce el precio máximo en euros: ");
        double maximo = Double.parseDouble(s.nextLine().trim().replace(',', '.'));

        if(minimo > maximo){
            double auxiliar = minimo;
            minimo = maximo;
            maximo = auxiliar;
        }

        vista.mostrarProductos(gestionProducto.buscarPorRangoPrecio(minimo, maximo));
    }

    //*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.MENÚ DE USUARIOS.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*
    private void gestionUsuarios() {
        int opcionMenu;

        do{
            vista.menuUsuarios();
            try {
                opcionMenu = Integer.parseInt(s.nextLine().trim());
            } catch (NumberFormatException e) {
                opcionMenu = -1;
            }

            switch(opcionMenu){
                case 1:
                    altaUsuario();
                    break;

                case 2:
                    bajaUsuario();
                    break;

                case 3:
                    if(gestionUsuario.getNumUsuarios() == 0){
                        vista.sinUsuarios();
                    }else{
                        vista.mostrarUsuarios(gestionUsuario.getUsuarios(), "TODOS LOS USUARIOS");
                    }
                    break;

                case 4:
                    modificarEstadoUsuario();
                    break;

                case 5:
                    return;

                default:
                    vista.opcionNoValida();
            }

        }while(true);
    }

    private void modificarEstadoUsuario() {
        if (gestionUsuario.getNumUsuarios() == 0) {
            vista.sinUsuarios();
            return;
        }

        vista.mostrarUsuarios(gestionUsuario.getUsuarios(), "USUARIOS DE LA TIENDA");
        vista.pedirEmailABuscar();
        String email = s.nextLine();

        Usuario usuario = gestionUsuario.buscaPorEmail(email);

        if (usuario == null) {
            vista.errorUsuarioNoEncontrado(email);
            return;
        }

        if (usuario.getEmail().equalsIgnoreCase(usuarioLogueado.getEmail())) {
            vista.mensajeError("No puedes bloquearte a ti mismo.");
            return;
        }

        usuario.setEstado(!usuario.isActivo());
        gestionUsuario.guardarEnCSV();
        vista.mensajeEstadoUsuarioCambiado(usuario);
        logs.registrar("Cambio de estado", usuarioLogueado.getEmail(), 
                       usuario.getEmail() + " ha pasado a " + usuario.getEstadoTexto());
    }

    private void altaUsuario() {
        vista.tituloAltaUsuario();
        vista.pedirDatosUsuario("Introduce el nombre del usuario: ");
        String nombre = s.nextLine().trim();

        vista.pedirDatosUsuario("Introduce el correo electrónico: ");
        String email = s.nextLine().trim();

        Usuario usuario = new UsuarioCliente(gestionUsuario.getProximoId(), nombre, email, true);

        if(gestionUsuario.aniadirUsuario(usuario)){
            gestionUsuario.guardarEnCSV();
            vista.mensajeUsuarioCreado(usuario);
            logs.registrar("Alta de usuario", usuarioLogueado.getEmail(), usuario.getEmail());
        } else {
            vista.mensajeError("Error al añadir usuario.");
        }
    }

    private void bajaUsuario() {
        if(gestionUsuario.getNumUsuarios() == 0){
            vista.sinUsuarios();
            return;
        }

        vista.mostrarUsuarios(gestionUsuario.getUsuarios(), "USUARIOS DE LA TIENDA");
        vista.pedirEmailABuscar();
        String email = s.nextLine();

        Usuario usuario = gestionUsuario.buscaPorEmail(email);

        if(usuario == null){
            vista.errorUsuarioNoEncontrado(email);
            return;
        }

        if(!vista.pideConfirmacion("¿Seguro que quieres eliminar a '" + usuario.getNombre() + "'?", s)){
            vista.operacionCancelada();
            return;
        }

        gestionUsuario.eliminaUsuario(email);
        gestionUsuario.guardarEnCSV();
        vista.mensajeUsuarioEliminado(usuario);
        logs.registrar("Baja de usuario", usuarioLogueado.getEmail(), usuario.getEmail());
    }

    //*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.MENÚ DEL CARRITO.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*
    private void gestionCarritoMenu() {
        int opcionMenu;

        do{
            vista.menuCarrito();
            opcionMenu = Integer.parseInt(s.nextLine().trim());

            switch(opcionMenu){
                case 1:
                    anadirAlCarrito();
                    break;

                case 2:
                    quitarDelCarrito();
                    break;

                case 3:
                    mostrarCarrito();
                    break;

                case 4:
                    return;

                default:
                    vista.opcionNoValida();
            }

        }while(true);
    }

    private void mostrarCarrito() {
        if(gestionCarrito.estaVacio()){
            vista.carritoVacio();
            return;
        }

        vista.mostrarCarrito(gestionCarrito.getLineas(),
                gestionCarrito.calcularTotal(), gestionCarrito.getNumUnidades());
    }

    private void anadirAlCarrito() {
        if(gestionProducto.getNumProductos() == 0){
            vista.noHayProductos();
            return;
        }

        vista.mostrarProductos(gestionProducto.getProductos());
        vista.pedirIdProducto();
        int idProducto = Integer.parseInt(s.nextLine().trim());

        Producto producto = gestionProducto.buscarPorId(idProducto);

        if(producto == null){
            vista.errorProductoNoEncontrado(idProducto);
            return;
        }

        vista.pedirCantidad();
        int cantidad = Integer.parseInt(s.nextLine().trim());

        if(gestionCarrito.anadirAlCarrito(idProducto, cantidad)){
            vista.mensajeProductoAnadido(producto, cantidad);
            logs.registrar("Añadido al carrito", usuarioLogueado.getEmail(), producto.getNombre() + " x" + cantidad);
        } else {
            vista.errorStockInsuficiente(producto.getNombre(), producto.getStock(), cantidad);
        }
    }

    private void quitarDelCarrito() {
        if(gestionCarrito.estaVacio()){
            vista.carritoVacio();
            return;
        }

        mostrarCarrito();
        vista.pedirIdProducto();
        int idProducto = Integer.parseInt(s.nextLine().trim());

        if(!gestionCarrito.getCarrito().contieneProducto(idProducto)){
            vista.mensajeError("El producto con ID " + idProducto + " no está en el carrito");
            return;
        }

        Producto producto = gestionProducto.buscarPorId(idProducto);
        gestionCarrito.quitarDelCarrito(idProducto);

        if(producto != null){
            vista.mensajeProductoQuitado(producto);
            logs.registrar("Quitado del carrito", usuarioLogueado.getEmail(), producto.getNombre());
        }else{
            vista.mensajeError("Producto ID " + idProducto + " eliminado del carrito.");
        }
    }

    //*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.CERRAR PEDIDO.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*
    private void cerrarPedido() {
        if(gestionCarrito.estaVacio()){
            vista.carritoVacio();
            return;
        }

        mostrarCarrito();

        if(!vista.pideConfirmacionCompra(gestionCarrito.getNumUnidades(), gestionCarrito.calcularTotal(), s)){
            vista.operacionCancelada();
            return;
        }

        Pedido pedido = gestionPedido.cerrarPedido(usuarioLogueado, gestionCarrito.getCarrito());

        if(pedido == null){
            return;
        }
        
        gestionCarrito.vaciarCarrito();

        gestionProducto.guardarEnCSV();
        gestionPedido.guardarEnCSV();

        vista.mostrarFactura(pedido);
        vista.mensajeFacturaGenerada(pedido);
        logs.registrar("Cierre de pedido", usuarioLogueado.getEmail(),
                "Factura nº " + pedido.getNumeroFactura() + " por "
                        + FuncionesPrecios.formatearPrecio(pedido.calcularTotal()));
    }
}
