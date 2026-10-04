package modelo.utilidades;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GestorLogs {

    private String rutaArchivo;

    public GestorLogs(String ruta){
        this.rutaArchivo = ruta;
    }

    public void registrar(String accion, String nombreUsuario){
        registrar(accion, nombreUsuario, "sistema");
    }

    public void registrar(String accion, String nombreUsuario, String detalle){
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String fechaFormateada = ahora.format(formato);

        String lineaLog = String.format("(\"%s\"; %s; %s; %s)", accion, nombreUsuario, detalle, fechaFormateada);

        try{
            crearDirectorioSiNoExiste(rutaArchivo);
            try(BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo, true))){
                bw.write(lineaLog);
                bw.newLine();
            }
        }catch (IOException e){
            // No podemos loguear que ha fallado el log en la consola porque
            // la interfaz podría ser GUI u otra. Se ignora silenciosamente o
            // se lanza RuntimeException, optaremos por silenciar por robustez.
        }
    }

    private void crearDirectorioSiNoExiste(String rutaFichero) {
        java.io.File archivo = new java.io.File(rutaFichero);
        java.io.File carpeta = archivo.getParentFile();

        if(carpeta != null && !carpeta.exists()){
            carpeta.mkdirs();
        }
    }
}
