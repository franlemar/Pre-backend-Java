package modelo.utilidades;

import java.util.Locale;

public class FuncionesPrecios {

    public static String formatearPrecio(double importe){
        return String.format(Locale.of("es", "ES"), "%,.2f €", importe);
    }

    public static String formatearPrecioParaFichero(double importe){
        // En CSV de España a menudo se usa el punto decimal o coma, pero
        // para estandarizar en este proyecto el archivo guarda con punto:
        return String.format(Locale.ROOT, "%.2f", importe);
    }
}
