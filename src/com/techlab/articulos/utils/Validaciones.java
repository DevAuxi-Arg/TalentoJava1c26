package com.techlab.articulos.utils;

/**
 * CONSIGNA DE ESTA CLASE
 * ------------------------------------------------------------
 * Esta clase debe concentrar validaciones reutilizables.
 *
 * RECOMENDACIÓN
 * ------------------------------------------------------------
 * Hacerla utilitaria:
 * - clase final
 * - constructor privado
 * - métodos static
 *
 * VALIDACIONES SUGERIDAS
 * ------------------------------------------------------------
 * - validarTextoNoVacio(String texto)
 * - validarLongitudMaxima(String texto, int maximo)
 * - validarNoNegativo(int valor)
 * - validarNoNegativo(double valor)
 *
 * BENEFICIO
 * ------------------------------------------------------------
 * Evita repetir la misma lógica de validación en distintas partes
 * del sistema.
 */
public final class Validaciones {

    private Validaciones() {
    }

    public static boolean textoNoVacio(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }

    public static boolean longitudMaxima(String texto, int maximo) {
        return texto != null && texto.length() <= maximo;
    }

    public static boolean noNegativo(int valor) {
        return valor >= 0;
    }

    public static boolean noNegativo(double valor) {
        return valor >= 0.0;
    }
}
