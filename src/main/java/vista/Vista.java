package vista;

import modelo.CategoriaProducto;
import modelo.LineaPedido;
import modelo.Pedido;
import modelo.Producto;
import modelo.Usuario;
import modelo.utilidades.Cadenas;
import modelo.utilidades.FuncionesPrecios;

import java.util.ArrayList;

public class Vista {

    private Estilos estilo = new Estilos();

    private static final int ANCHO_TABLA = 100;

    //*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.MENÚ PRINCIPAL Y LOGIN.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*
    
    public void menuLogin() {
        System.out.println();
        System.out.println(estilo.getPASTEL_PURPLE() + estilo.getBOLD() + estilo.getUNDERLINE()
                + "✦ BIENVENIDO A LA TIENDA ✦" + estilo.getANSI_RESET());
        System.out.println(estilo.getPASTEL_BLUE() + "1. Iniciar sesión");
        System.out.println("2. Registrarse");
        System.out.println(estilo.getNEON_PINK() + "3. Salir del programa" + estilo.getANSI_RESET());
        System.out.print(estilo.getPASTEL_GREEN() + "Seleccione una opción: " + estilo.getANSI_RESET());
    }

    public void pedirCorreo() {
        System.out.print(estilo.getPASTEL_GREEN() + "Introduce tu correo electrónico: " + estilo.getANSI_RESET());
    }

    public void pedirPassword() {
        System.out.print(estilo.getPASTEL_GREEN() + "Introduce tu contraseña: " + estilo.getANSI_RESET());
    }

    public void menuSeleccionRol() {
        System.out.println(estilo.getPASTEL_BLUE() + "Seleccione el tipo de cuenta:" + estilo.getANSI_RESET());
        System.out.println("1. Cliente (compras y pedidos)");
        System.out.println("2. Administrador (gestión de catálogo y usuarios)");
        System.out.print(estilo.getPASTEL_GREEN() + "Seleccione opción (1 o 2): " + estilo.getANSI_RESET());
    }

    public void errorPasswordIncorrecta(int intentosRestantes) {
        System.out.println(estilo.getNEON_RED() + estilo.getWARNING()
                + " Contraseña incorrecta. Intentos restantes: " + intentosRestantes + estilo.getANSI_RESET());
    }

    public void mensajeBienvenida(Usuario usuario) {
        System.out.println();
        System.out.println(estilo.getNEON_GREEN() + estilo.getBOLD()
                + "¡Bienvenido/a, " + usuario.getNombre() + "! (" + usuario.getTipoUsuario() + ")"
                + estilo.getANSI_RESET());
    }

    public void registroCorrecto() {
        System.out.println(estilo.getNEON_GREEN() + estilo.getBOLD()
                + "¡Registro completado con éxito! Ya puedes iniciar sesión."
                + estilo.getANSI_RESET());
    }

    public void errorPermisos() {
        System.out.println(estilo.getNEON_RED() + estilo.getWARNING()
                + " ACCESO DENEGADO: Necesitas permisos de ADMINISTRADOR para realizar esta acción."
                + estilo.getANSI_RESET());
    }

    public void menuAdministrador() {
        System.out.println();
        System.out.println(estilo.getPASTEL_PURPLE() + estilo.getBOLD() + estilo.getUNDERLINE()
                + "✦ MENÚ ADMINISTRADOR ✦" + estilo.getANSI_RESET());
        System.out.println(estilo.getPASTEL_BLUE() + "1. Gestionar productos");
        System.out.println("2. Gestionar usuarios");
        System.out.println("3. Gestionar carrito de compra");
        System.out.println("4. Cerrar pedido y generar factura");
        System.out.println("5. Consultar historial de pedidos");
        System.out.println(estilo.getNEON_PINK() + "6. Cerrar sesión" + estilo.getANSI_RESET());
        System.out.print(estilo.getPASTEL_GREEN() + "Seleccione la opción deseada: " + estilo.getANSI_RESET());
    }

    public void menuCliente() {
        System.out.println();
        System.out.println(estilo.getPASTEL_PURPLE() + estilo.getBOLD() + estilo.getUNDERLINE()
                + "✦ MENÚ CLIENTE ✦" + estilo.getANSI_RESET());
        System.out.println(estilo.getPASTEL_BLUE() + "1. Ver catálogo de productos");
        System.out.println("2. Gestionar carrito de compra");
        System.out.println("3. Cerrar pedido y generar factura");
        System.out.println("4. Consultar mis pedidos");
        System.out.println(estilo.getNEON_PINK() + "5. Cerrar sesión" + estilo.getANSI_RESET());
        System.out.print(estilo.getPASTEL_GREEN() + "Seleccione la opción deseada: " + estilo.getANSI_RESET());
    }

    public void tituloPrograma() {
        System.out.println();
        System.out.println(estilo.getPASTEL_BLUE()
                + " ██████╗ ███████╗███████╗████████╗██╗ ██████╗ ███╗   ██╗\n"
                + "██╔════╝ ██╔════╝██╔════╝╚══██╔══╝██║██╔═══██╗████╗  ██║\n"
                + "██║  ███╗█████╗  ███████╗   ██║   ██║██║   ██║██╔██╗ ██║\n"
                + "██║   ██║██╔══╝  ╚════██║   ██║   ██║██║   ██║██║╚██╗██║\n"
                + "╚██████╔╝███████╗███████║   ██║   ██║╚██████╔╝██║ ╚████║\n"
                + " ╚═════╝ ╚══════╝╚══════╝   ╚═╝   ╚═╝ ╚═════╝ ╚═╝  ╚═══╝\n"
                + estilo.getANSI_RESET());
    }

    public void mostrarDespedida() {
        System.out.println();
        System.out.println(estilo.getPASTEL_PURPLE() + estilo.getBOLD()
                + "¡Gracias por usar este software de gestión! " + estilo.getANSI_RESET());
        System.out.println(estilo.getPASTEL_BLUE() + "Hasta pronto :)" + estilo.getANSI_RESET());
        System.out.println();
    }

    //*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.MENÚ DE PRODUCTOS.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*
    public void menuProductos() {
        System.out.println();
        System.out.println(estilo.getPASTEL_PURPLE() + estilo.getBOLD()
                + "✦ GESTIÓN DE PRODUCTOS ✦" + estilo.getANSI_RESET());
        System.out.println(estilo.getPASTEL_BLUE() + "1. Dar de alta un producto");
        System.out.println("2. Dar de baja un producto");
        System.out.println("3. Listar todo el catálogo");
        System.out.println("4. Buscar productos");
        System.out.println(estilo.getNEON_PINK() + "5. Volver atrás" + estilo.getANSI_RESET());
        System.out.println(estilo.getPASTEL_GREEN() + "Seleccione la opción deseada: " + estilo.getANSI_RESET());
    }

    public void menuBusquedaProducto() {
        System.out.println();
        System.out.println(estilo.getPASTEL_PURPLE() + estilo.getBOLD()
                + "¿Cómo quieres buscar los productos?" + estilo.getANSI_RESET());
        System.out.println(estilo.getPASTEL_BLUE() + "1. Por categoría");
        System.out.println("2. Por rango de precio");
        System.out.println(estilo.getNEON_PINK() + "3. Volver atrás" + estilo.getANSI_RESET());
        System.out.println(estilo.getPASTEL_GREEN() + "Seleccione la opción deseada: " + estilo.getANSI_RESET());
    }

    public void tituloAltaProducto() {
        System.out.println();
        System.out.println(estilo.getPASTEL_PURPLE() + estilo.getBOLD()
                + "--- ALTA DE PRODUCTO ---" + estilo.getANSI_RESET());
    }

    public void pedirTipoProducto() {
        System.out.println(estilo.getPASTEL_BLUE() + "¿Qué tipo de producto quieres registrar?");
        System.out.println("  1. Producto físico (se envía, tiene peso y gastos de envío)");
        System.out.println("  2. Producto digital (se descarga, tiene tamaño y licencia)");
        System.out.println(estilo.getPASTEL_GREEN() + "Selecciona el tipo: " + estilo.getANSI_RESET());
    }

    public void pedirCategoriaProducto() {
        System.out.println(estilo.getPASTEL_BLUE() + "¿A qué categoría pertenece el producto?");
        System.out.println("  Categorías disponibles: " + listarCategorias());
        System.out.println(estilo.getPASTEL_GREEN() + "Introduce la categoría: " + estilo.getANSI_RESET());
    }

    private String listarCategorias() {
        StringBuilder lista = new StringBuilder();

        for(CategoriaProducto categoria : CategoriaProducto.values()){
            if(lista.length() > 0){
                lista.append(", ");
            }
            lista.append(categoria.name());
        }

        return lista.toString();
    }

    public void pedirPrecioProducto() {
        System.out.print(estilo.getPASTEL_GREEN() + "Introduce el precio del producto en euros: " + estilo.getANSI_RESET());
    }

    public void pedirStockProducto() {
        System.out.print(estilo.getPASTEL_GREEN() + "Introduce el número de unidades en stock: " + estilo.getANSI_RESET());
    }

    public void pedirPeso() {
        System.out.println(estilo.getPASTEL_BLUE() + "Datos del producto físico:");
    }

    public void pedirGastosEnvio() {
        System.out.println(estilo.getPASTEL_BLUE() + "Gastos de envío:");
    }

    public void pedirPesoDescarga() {
        System.out.println(estilo.getPASTEL_BLUE() + "Datos del producto digital:");
    }

    public void pedirLicencia() {
        System.out.print(estilo.getPASTEL_GREEN() + "Introduce la clave de licencia: " + estilo.getANSI_RESET());
    }

    public void pedirDatosProducto(String dato) {
        System.out.print(estilo.getPASTEL_GREEN() + dato + estilo.getANSI_RESET());
    }

    //*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.MENÚ DE USUARIOS.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*
    public void menuUsuarios() {
        System.out.println();
        System.out.println(estilo.getPASTEL_PURPLE() + estilo.getBOLD()
                + "✦ GESTIÓN DE USUARIOS ✦" + estilo.getANSI_RESET());
        System.out.println(estilo.getPASTEL_BLUE() + "1. Dar de alta un usuario");
        System.out.println("2. Dar de baja un usuario");
        System.out.println("3. Listar todos los usuarios");
        System.out.println("4. Bloquear / Desbloquear un usuario");
        System.out.println(estilo.getNEON_PINK() + "5. Volver atrás" + estilo.getANSI_RESET());
        System.out.print(estilo.getPASTEL_GREEN() + "Seleccione la opción deseada: " + estilo.getANSI_RESET());
    }

    public void mensajeEstadoUsuarioCambiado(Usuario usuario) {
        System.out.println(estilo.getNEON_GREEN() + "\nEstado del usuario actualizado correctamente.");
        System.out.println("El usuario " + usuario.getEmail() + " está ahora: " + usuario.getEstadoTexto() + estilo.getANSI_RESET());
    }

    public void tituloAltaUsuario() {
        System.out.println();
        System.out.println(estilo.getPASTEL_PURPLE() + estilo.getBOLD()
                + "--- ALTA DE USUARIO ---" + estilo.getANSI_RESET());
    }

    public void pedirDatosUsuario(String dato) {
        System.out.print(estilo.getPASTEL_GREEN() + dato + estilo.getANSI_RESET());
    }

    //*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.MENÚ DEL CARRITO.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*
    public void menuCarrito() {
        System.out.println();
        System.out.println(estilo.getPASTEL_PURPLE() + estilo.getBOLD()
                + "✦ GESTIÓN DEL CARRITO ✦" + estilo.getANSI_RESET());
        System.out.println(estilo.getPASTEL_BLUE() + "1. Añadir un producto al carrito");
        System.out.println("2. Quitar un producto del carrito");
        System.out.println("3. Ver el contenido y el total del carrito");
        System.out.println(estilo.getNEON_PINK() + "4. Volver atrás" + estilo.getANSI_RESET());
        System.out.println(estilo.getPASTEL_GREEN() + "Seleccione la opción deseada: " + estilo.getANSI_RESET());
    }

    public void pedirIdProducto() {
        System.out.print(estilo.getPASTEL_GREEN()
                + "Introduce el ID del producto: " + estilo.getANSI_RESET());
    }

    public void pedirCantidad() {
        System.out.print(estilo.getPASTEL_GREEN()
                + "Introduce la cantidad de unidades: " + estilo.getANSI_RESET());
    }

    public void pedirPrecioRango(String texto) {
        System.out.print(estilo.getPASTEL_GREEN() + texto + estilo.getANSI_RESET());
    }

    public void pedirEmailABuscar() {
        System.out.print(estilo.getPASTEL_GREEN()
                + "Introduce el correo electrónico del usuario: " + estilo.getANSI_RESET());
    }

    //*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.LISTADOS Y TABLAS.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*
    public void mostrarProductos(ArrayList<Producto> productos) {
        if(productos.isEmpty()){
            noHayProductos();
            return;
        }

        System.out.println();
        System.out.println(estilo.getNEON_BLUE() + Cadenas.repetir("=", ANCHO_TABLA) + estilo.getANSI_RESET());
        System.out.println(estilo.getNEON_BLUE() + estilo.getBOLD()
                + "CATÁLOGO DE PRODUCTOS (" + productos.size() + " productos)" + estilo.getANSI_RESET());
        System.out.println(estilo.getNEON_BLUE() + Cadenas.repetir("=", ANCHO_TABLA) + estilo.getANSI_RESET());

        System.out.println(String.format("%-4s %-22s %-9s %10s %6s  %s",
                "ID", "NOMBRE", "TIPO", "PVP", "STOCK", "CATEGORÍA"));
        System.out.println(Cadenas.repetir("-", ANCHO_TABLA));

        for(Producto producto : productos){
            System.out.println(String.format("%-4d %-22s %-9s %10s %6s  %s",
                    producto.getId(),
                    producto.getNombre(),
                    producto.getTipoProducto(),
                    FuncionesPrecios.formatearPrecio(producto.getPrecio()),
                    producto.getStock(),
                    producto.getCategoria()));
        }

        System.out.println(estilo.getNEON_BLUE() + Cadenas.repetir("=", ANCHO_TABLA) + estilo.getANSI_RESET());
    }

    public void mostrarUsuarios(ArrayList<Usuario> usuarios, String titulo) {
        if(usuarios.isEmpty()){
            sinUsuarios();
            return;
        }

        System.out.println();
        System.out.println(estilo.getNEON_BLUE() + Cadenas.repetir("=", ANCHO_TABLA) + estilo.getANSI_RESET());
        System.out.println(estilo.getNEON_BLUE() + estilo.getBOLD()
                + titulo + " (" + usuarios.size() + " usuarios)" + estilo.getANSI_RESET());
        System.out.println(estilo.getNEON_BLUE() + Cadenas.repetir("=", ANCHO_TABLA) + estilo.getANSI_RESET());

        for(Usuario usuario : usuarios){
            String color = usuario.isActivo() ? estilo.getNEON_GREEN() : estilo.getNEON_RED();
            System.out.println(String.format("%-4d %-22s %-28s %s%s%s",
                    usuario.getId(), usuario.getNombre(), usuario.getEmail(),
                    color, usuario.getEstadoTexto(), estilo.getANSI_RESET()));
        }

        System.out.println(estilo.getNEON_BLUE() + Cadenas.repetir("=", ANCHO_TABLA) + estilo.getANSI_RESET());
    }

    public void mostrarCarrito(ArrayList<LineaPedido> lineas, double total, int numUnidades) {
        if(lineas.isEmpty()){
            carritoVacio();
            return;
        }

        System.out.println();
        System.out.println(estilo.getGOLD() + Cadenas.repetir("=", ANCHO_TABLA) + estilo.getANSI_RESET());
        System.out.println(estilo.getGOLD() + estilo.getBOLD()
                + "CARRITO DE COMPRA" + estilo.getANSI_RESET());
        System.out.println(estilo.getGOLD() + Cadenas.repetir("=", ANCHO_TABLA) + estilo.getANSI_RESET());

        for(LineaPedido linea : lineas){
            System.out.println(String.format("%-22s %-9s %3d x %10s = %12s",
                    linea.getNombreProducto(),
                    linea.getTipoProducto(),
                    linea.getCantidad(),
                    FuncionesPrecios.formatearPrecio(linea.getPrecioUnitario()),
                    FuncionesPrecios.formatearPrecio(linea.calcularSubtotal())));
        }

        System.out.println(estilo.getGOLD() + Cadenas.repetir("-", ANCHO_TABLA) + estilo.getANSI_RESET());
        System.out.println(String.format("Unidades: %d | TOTAL A PAGAR: %s",
                numUnidades,
                estilo.getBOLD() + estilo.getNEON_GREEN()
                        + FuncionesPrecios.formatearPrecio(total) + estilo.getANSI_RESET()));
        System.out.println(estilo.getGOLD() + Cadenas.repetir("=", ANCHO_TABLA) + estilo.getANSI_RESET());
    }

    public void mostrarHistorial(ArrayList<Pedido> pedidos) {
        if(pedidos.isEmpty()){
            noHayPedidos();
            return;
        }

        System.out.println();
        System.out.println(estilo.getNEON_BLUE() + Cadenas.repetir("=", ANCHO_TABLA) + estilo.getANSI_RESET());
        System.out.println(estilo.getNEON_BLUE() + estilo.getBOLD()
                + "HISTORIAL DE PEDIDOS (" + pedidos.size() + " facturas)" + estilo.getANSI_RESET());
        System.out.println(estilo.getNEON_BLUE() + Cadenas.repetir("=", ANCHO_TABLA) + estilo.getANSI_RESET());
        System.out.println(String.format("%-8s %-20s %-20s %6s %14s",
                "Factura", "Fecha", "Cliente", "Uds", "Total"));

        for(Pedido pedido : pedidos){
            System.out.println(String.format("%-8d %-20s %-20s %6d %14s",
                    pedido.getNumeroFactura(),
                    pedido.getFechaLegible(),
                    pedido.getNombreUsuario(),
                    pedido.getNumeroUnidades(),
                    FuncionesPrecios.formatearPrecio(pedido.calcularTotal())));
        }

        System.out.println(estilo.getNEON_BLUE() + Cadenas.repetir("=", ANCHO_TABLA) + estilo.getANSI_RESET());
    }

    public void mostrarFactura(Pedido pedido) {
        System.out.println();
        System.out.println(estilo.getNEON_GREEN());
        String separador = "=".repeat(78);
        String lineaStr = "-".repeat(78);
        System.out.println(separador);
        System.out.println("FACTURA Nº " + pedido.getNumeroFactura());
        System.out.println(separador);
        System.out.println("Fecha de emisión : " + pedido.getFechaLegible());
        System.out.println("Cliente          : " + pedido.getUsuario().getNombre());
        System.out.println("Correo           : " + pedido.getUsuario().getEmail());
        System.out.println(lineaStr);
        System.out.println("DETALLE DE LA FACTURA");
        System.out.println(lineaStr);
        for(int i = 0; i < pedido.getLineas().size(); i++){
            System.out.printf("%2d. %s%n", i + 1, pedido.getLineas().get(i).toString());
        }
        System.out.println(lineaStr);
        System.out.printf("Líneas           : %d%n", pedido.getLineas().size());
        System.out.printf("Unidades         : %d%n", pedido.getNumeroUnidades());
        System.out.println("TOTAL A PAGAR    : " + modelo.utilidades.FuncionesPrecios.formatearPrecio(pedido.calcularTotal()));
        System.out.println(separador);
        System.out.println(estilo.getANSI_RESET());
    }

    //*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.MENSAJES DE CONFIRMACIÓN.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*
    public void mensajeError(String mensaje) {
        System.out.println(estilo.getNEON_RED() + estilo.getWARNING() + " ERROR: "
                + mensaje + estilo.getANSI_RESET());
    }

    public void opcionNoValida() {
        System.out.println(estilo.getNEON_RED() + estilo.getWARNING()
                + " La opción seleccionada no es válida." + estilo.getANSI_RESET());
    }

    public void operacionCancelada() {
        System.out.println(estilo.getPASTEL_YELLOW()
                + "Operación cancelada." + estilo.getANSI_RESET());
    }

    public boolean pideConfirmacion(String pregunta, java.util.Scanner s) {
        System.out.print(estilo.getPASTEL_YELLOW() + pregunta
                + " (s/n): " + estilo.getANSI_RESET());
        String respuesta = s.nextLine().trim();
        return respuesta.equalsIgnoreCase("s") || respuesta.equalsIgnoreCase("si");
    }

    public boolean pideConfirmacion(String pregunta) {
        return pideConfirmacion(pregunta, new java.util.Scanner(System.in));
    }

    //*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.MENSAJES DE ERROR.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*
    public void noHayProductos() {
        System.out.println(estilo.getPASTEL_YELLOW()
                + "No hay productos en el catálogo todavía." + estilo.getANSI_RESET());
    }

    public void sinUsuarios() {
        System.out.println(estilo.getPASTEL_YELLOW()
                + "No hay usuarios registrados en la tienda." + estilo.getANSI_RESET());
    }



    public void carritoVacio() {
        System.out.println(estilo.getPASTEL_YELLOW()
                + "El carrito de compra está vacío." + estilo.getANSI_RESET());
    }

    public void noHayPedidos() {
        System.out.println(estilo.getPASTEL_YELLOW()
                + "Todavía no se ha cerrado ningún pedido." + estilo.getANSI_RESET());
    }

    public void categoriaNoValida() {
        System.out.println(estilo.getNEON_RED() + estilo.getWARNING()
                + " Esa categoría no existe." + estilo.getANSI_RESET());
    }

    public void precioNoValido() {
        System.out.println(estilo.getNEON_RED() + estilo.getWARNING()
                + " El precio debe ser un número mayor o igual que 0." + estilo.getANSI_RESET());
    }

    public void emailNoValido() {
        System.out.println(estilo.getNEON_RED() + estilo.getWARNING()
                + " El correo electrónico no tiene un formato válido." + estilo.getANSI_RESET());
    }

    public void errorDatoInvalido(String nombreDato) {
        System.out.println(estilo.getNEON_RED() + estilo.getWARNING()
                + " El " + nombreDato + " introducido no es válido." + estilo.getANSI_RESET());
    }

    public void errorProductoNoEncontrado(int idProducto) {
        System.out.println(estilo.getNEON_RED() + estilo.getWARNING()
                + " No existe ningún producto en el catálogo con el ID " + idProducto
                + estilo.getANSI_RESET());
    }

    public void errorProductoYaExiste(int idProducto) {
        System.out.println(estilo.getNEON_RED() + estilo.getWARNING()
                + " Ya existe un producto con el ID " + idProducto
                + ", no se puede volver a dar de alta." + estilo.getANSI_RESET());
    }

    public void errorStockInsuficiente(String nombreProducto, int stockActual, int cantidadPedida) {
        System.out.println(estilo.getNEON_RED() + estilo.getWARNING()
                + " Stock insuficiente para '" + nombreProducto + "': hay " + stockActual
                + " unidad(es) y se han pedido " + cantidadPedida + "." + estilo.getANSI_RESET());
    }

    public void errorCantidadInvalida(int cantidad) {
        System.out.println(estilo.getNEON_RED() + estilo.getWARNING()
                + " La cantidad introducida (" + cantidad
                + ") no es válida: debe ser un número entero mayor que 0." + estilo.getANSI_RESET());
    }

    public void errorPrecioInvalido(double precio) {
        System.out.println(estilo.getNEON_RED() + estilo.getWARNING()
                + " El precio introducido (" + precio
                + " €) no es válido: debe ser un número mayor o igual que 0." + estilo.getANSI_RESET());
    }

    public void errorUsuarioNoEncontrado(String email) {
        System.out.println(estilo.getNEON_RED() + estilo.getWARNING()
                + " No existe ningún usuario registrado con el correo " + email
                + estilo.getANSI_RESET());
    }

    public void errorUsuarioYaExiste(String email) {
        System.out.println(estilo.getNEON_RED() + estilo.getWARNING()
                + " El correo " + email + " ya está registrado en la tienda." + estilo.getANSI_RESET());
    }

    public void errorUsuarioInactivo(String nombreUsuario) {
        System.out.println(estilo.getNEON_RED() + estilo.getWARNING()
                + " El usuario '" + nombreUsuario
                + "' está dado de baja (inactivo) y no puede realizar esta operación."
                + estilo.getANSI_RESET());
    }

    //*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.MENSAJES DE OPERACIÓN.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*
    public void mensajeProductoCreado(Producto producto) {
        System.out.println(estilo.getNEON_GREEN()
                + "Producto '" + producto.getNombre() + "' dado de alta con ID " + producto.getId()
                + estilo.getANSI_RESET());
    }

    public void mensajeProductoEliminado(Producto producto) {
        System.out.println(estilo.getNEON_GREEN()
                + "Producto '" + producto.getNombre() + "' eliminado del catálogo." + estilo.getANSI_RESET());
    }

    public void mensajeUsuarioCreado(Usuario usuario) {
        System.out.println(estilo.getNEON_GREEN()
                + "Usuario '" + usuario.getNombre() + "' dado de alta con ID " + usuario.getId()
                + estilo.getANSI_RESET());
    }

    public void mensajeUsuarioEliminado(Usuario usuario) {
        System.out.println(estilo.getNEON_GREEN()
                + "Usuario '" + usuario.getNombre() + "' eliminado de la tienda." + estilo.getANSI_RESET());
    }

    public void mensajeProductoAnadido(Producto producto, int cantidad) {
        System.out.println(estilo.getNEON_GREEN()
                + "Se han añadido " + cantidad + " unidad(es) de '" + producto.getNombre()
                + "' al carrito." + estilo.getANSI_RESET());
    }

    public void mensajeProductoQuitado(Producto producto) {
        System.out.println(estilo.getNEON_GREEN()
                + "Se ha quitado '" + producto.getNombre() + "' del carrito." + estilo.getANSI_RESET());
    }

    public boolean pideConfirmacionCompra(int numeroUnidades, double total, java.util.Scanner s) {
        System.out.println(estilo.getGOLD() + estilo.getBOLD()
                + "Va a cerrar una compra de " + numeroUnidades + " unidad(es) por un total de "
                + FuncionesPrecios.formatearPrecio(total) + estilo.getANSI_RESET());
        return pideConfirmacion("¿Confirma la compra?", s);
    }

    public boolean pideConfirmacionCompra(int numeroUnidades, double total) {
        return pideConfirmacionCompra(numeroUnidades, total, new java.util.Scanner(System.in));
    }

    public void mensajeFacturaGenerada(Pedido pedido) {
        System.out.println(estilo.getNEON_GREEN() + estilo.getBOLD()
                + "✔ Compra realizada con éxito. Factura nº " + pedido.getNumeroFactura()
                + estilo.getANSI_RESET());
    }
}
