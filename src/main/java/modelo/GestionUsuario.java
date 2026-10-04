package modelo;

import modelo.utilidades.Cadenas;
import modelo.utilidades.GestorCSV;

import java.util.ArrayList;
import java.util.HashMap;

public class GestionUsuario {

    private static final String RUTA_FICHERO = "datos/usuarios.csv";
    private static final String CABECERA = "id;nombre;email;password;estado;rol";

    private HashMap<String, Usuario> usuarios;
    private int proximoId;

    public GestionUsuario() {
        this.usuarios = new HashMap<>();
        this.proximoId = 1;
    }

    public int getProximoId() {
        return proximoId;
    }

    public boolean aniadirUsuario(Usuario nuevoUsuario) {
        if(nuevoUsuario == null){
            return false;
        }

        String clave = Cadenas.normalizarEmail(nuevoUsuario.getEmail());
        if(usuarios.containsKey(clave)){
            return false;
        }

        usuarios.put(clave, nuevoUsuario);

        if(nuevoUsuario.getId() >= proximoId){
            proximoId = nuevoUsuario.getId() + 1;
        }
        return true;
    }

    public Usuario buscaPorEmail(String email) {
        return usuarios.get(Cadenas.normalizarEmail(email));
    }

    public ArrayList<Usuario> getUsuarios() {
        ArrayList<Usuario> lista = new ArrayList<>(usuarios.values());
        lista.sort((u1, u2) -> Integer.compare(u1.getId(), u2.getId()));
        return lista;
    }

    public int getNumUsuarios() {
        return usuarios.size();
    }



    public Usuario eliminaUsuario(String email) {
        return usuarios.remove(Cadenas.normalizarEmail(email));
    }

    public void guardarEnCSV() {
        ArrayList<String> lineas = new ArrayList<>();
        lineas.add(CABECERA);

        for(Usuario usuario : usuarios.values()){
            lineas.add(usuario.getId() + ";"
                    + usuario.getNombre() + ";"
                    + usuario.getEmail() + ";"
                    + usuario.getPassword() + ";"
                    + usuario.isActivo() + ";"
                    + usuario.getTipoUsuario());
        }

        GestorCSV.escribir(RUTA_FICHERO, lineas);
    }

    public void cargarDesdeCSV() {
        ArrayList<String> lineas = GestorCSV.leer(RUTA_FICHERO);
        usuarios.clear();

        for(int i = 1; i < lineas.size(); i++){
            if(lineas.get(i).isBlank()){
                continue;
            }

            try {
                String[] campos = lineas.get(i).split(";", -1);
                int id = Integer.parseInt(campos[0].trim());
                String nombre = campos[1];
                String email = Cadenas.normalizarEmail(campos[2]);
                String password = campos[3].trim();
                boolean estado = Boolean.parseBoolean(campos[4].trim());
                String rolStr = campos[5].trim().toUpperCase();

                Usuario usuario;
                if(rolStr.equals("ADMINISTRADOR")) {
                    usuario = new UsuarioAdmin(id, nombre, email, password, estado);
                } else {
                    usuario = new UsuarioCliente(id, nombre, email, password, estado);
                }

                usuarios.put(email, usuario);

                if(id >= proximoId){
                    proximoId = id + 1;
                }
            } catch (RuntimeException e) {
                
            }
        }

        if(usuarios.isEmpty()){
            cargarUsuariosDeEjemplo();
            guardarEnCSV();
        }
    }

    private void cargarUsuariosDeEjemplo() {
        usuarios.put("admin@tienda.com", new UsuarioAdmin(proximoId++, "Administrador", "admin@tienda.com", "admin123", true));
        usuarios.put("ana.garcia@gmail.com", new UsuarioCliente(proximoId++, "Ana García", "ana.garcia@gmail.com", "1234", true));
        usuarios.put("luis.mena@gmail.com", new UsuarioCliente(proximoId++, "Luis Mena", "luis.mena@gmail.com", "1234", true));
        usuarios.put("marta.ruiz@gmail.com", new UsuarioCliente(proximoId++, "Marta Ruiz", "marta.ruiz@gmail.com", "1234", true));
        usuarios.put("carlos.pico@gmail.com", new UsuarioCliente(proximoId++, "Carlos Pico", "carlos.pico@gmail.com", "1234", false));
    }
}
