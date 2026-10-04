package modelo;

public abstract class Usuario {

    private final int id;
    private String nombre;
    private String email;
    private String password;
    private boolean estado;

    public Usuario(int id, String nombre, String email, String password, boolean estado){
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.estado = estado;
    }

    public abstract boolean esAdmin();

    public abstract String getTipoUsuario();

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean comprobarPassword(String passIngresada) {
        return this.password != null && this.password.equals(passIngresada);
    }

    public boolean isActivo() {
        return estado;
    }

    public String getEstadoTexto() {
        return estado ? "ACTIVO" : "INACTIVO";
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String toString() {
        return "ID: " + id
                + " | Rol: " + getTipoUsuario()
                + " | Nombre: " + nombre
                + " | Email: " + email
                + " | Estado: " + getEstadoTexto();
    }
}
