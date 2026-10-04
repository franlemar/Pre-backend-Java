package modelo;

public class UsuarioAdmin extends Usuario {

    public UsuarioAdmin(int id, String nombre, String email, String password, boolean estado) {
        super(id, nombre, email, password, estado);
    }

    // Constructor de retrocompatibilidad
    public UsuarioAdmin(int id, String nombre, String email, boolean estado) {
        this(id, nombre, email, "1234", estado);
    }

    @Override
    public boolean esAdmin() {
        return true;
    }

    @Override
    public String getTipoUsuario() {
        return "ADMINISTRADOR";
    }
}
