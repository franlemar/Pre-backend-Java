package modelo;

public class UsuarioCliente extends Usuario {

    public UsuarioCliente(int id, String nombre, String email, String password, boolean estado) {
        super(id, nombre, email, password, estado);
    }

    // Constructor de retrocompatibilidad
    public UsuarioCliente(int id, String nombre, String email, boolean estado) {
        this(id, nombre, email, "1234", estado);
    }

    @Override
    public boolean esAdmin() {
        return false;
    }

    @Override
    public String getTipoUsuario() {
        return "CLIENTE";
    }
}
