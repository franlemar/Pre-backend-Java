package modelo.utilidades;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class GestorCSV {

    public static void escribir(String rutaFichero, ArrayList<String> lineas) {
        crearCarpetaSiNoExiste(rutaFichero);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaFichero, StandardCharsets.UTF_8))) {
            for (String linea : lineas) {
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al escribir el CSV: " + e.getMessage());
        }
    }

    public static ArrayList<String> leer(String rutaFichero) {
        File archivo = new File(rutaFichero);

        if (!archivo.exists()) {
            return new ArrayList<>();
        }

        ArrayList<String> lineas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo, StandardCharsets.UTF_8))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lineas.add(linea);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el CSV: " + e.getMessage());
            return new ArrayList<>();
        }

        return lineas;
    }

    private static void crearCarpetaSiNoExiste(String rutaFichero) {
        File archivo = new File(rutaFichero);
        File carpeta = archivo.getParentFile();

        if (carpeta != null && !carpeta.exists()) {
            carpeta.mkdirs();
        }
    }
}