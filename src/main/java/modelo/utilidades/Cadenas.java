package modelo.utilidades;

public class Cadenas {

    private static final int LONGITUD_MINIMA = 2;
    private static final int LONGITUD_MAXIMA = 50;

    public static boolean esTextoValido(String texto, int minimo, int maximo){
        if(texto == null || texto.isBlank()){
            return false;
        }
        int longitud = texto.trim().length();
        return longitud >= minimo && longitud <= maximo;
    }

    public static boolean esNombreValido(String texto){
        return esTextoValido(texto, LONGITUD_MINIMA, LONGITUD_MAXIMA);
    }

    public static boolean esEmailValido(String email){
        if(email == null){
            return false;
        }
        String emailNormalizado = email.trim().toLowerCase();
        return emailNormalizado.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }

    public static String normalizarEmail(String email){
        return email == null ? null : email.trim().toLowerCase();
    }

    public static String repetir(String caracter, int repeticiones){
        return caracter.repeat(Math.max(0, repeticiones));
    }
}
