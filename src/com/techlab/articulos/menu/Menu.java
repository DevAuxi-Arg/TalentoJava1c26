package com.techlab.articulos.menu;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import com.techlab.articulos.utils.Validaciones;

/**
 * CONSIGNA DE ESTA CLASE
 * ------------------------------------------------------------
 * Esta debe ser la clase base de todos los menús.
 *
 * Objetivo:
 * centralizar la lógica común para no repetir código.
 *
 * Esta clase debe:
 * - guardar un Scanner compartido
 * - declarar el método mostrarMenu()
 * - declarar el método ejecutar()
 *
 * Además, podés agregar métodos protegidos reutilizables, por ejemplo:
 * - leerEntero(String mensaje)
 * - leerDouble(String mensaje)
 * - leerTexto(String mensaje)
 * - leerSiNo(String mensaje)
 *
 * IMPORTANTE:
 * Esta clase debe ser abstracta, porque no tiene sentido crear un
 * "menú genérico" instanciable. Solo debe servir como base para:
 * - MenuArticulos
 * - MenuCategorias
 */
public abstract class Menu {

    protected Scanner scanner;
    private static final int ANCHO = 40;

    public Menu(Scanner scanner) {
        this.scanner = scanner;
    }

    public abstract void mostrarMenu();

    public abstract void ejecutar();

    protected void limpiarPantalla() {
        try {
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            // fallback: saltos de línea
            for (int i = 0; i < 40; i++) System.out.println();
        }
    }

    protected void imprimirMenu(String titulo, String[] opciones) {
        limpiarPantalla();
        String marco = "*".repeat(ANCHO);
        System.out.println("\n" + marco);
        System.out.println("*" + " ".repeat(ANCHO - 2) + "*");
        System.out.println("*" + centrar(titulo) + "*");
        System.out.println("*" + " ".repeat(ANCHO - 2) + "*");
        for (String opcion : opciones) {
            System.out.println("*" + centrar(opcion) + "*");
        }
        System.out.println("*" + " ".repeat(ANCHO - 2) + "*");
        System.out.println(marco);
    }

    private String centrar(String texto) {
        int espacios = ANCHO - 2 - texto.length();
        int izq = espacios / 2;
        int der = espacios - izq;
        return " ".repeat(Math.max(0, izq)) + texto + " ".repeat(Math.max(0, der));
    }

    protected int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: debe ingresar un número entero válido.");
            }
        }
    }

    protected double leerDouble(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                double valor = Double.parseDouble(scanner.nextLine());
                if (!Validaciones.noNegativo(valor)) {
                    System.out.println("Error: el valor no puede ser negativo.");
                    continue;
                }
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Error: debe ingresar un número decimal válido.");
            }
        }
    }

    protected void pausar() {
        System.out.print("\nPresione <Enter> para continuar...");
        scanner.nextLine();
    }

    protected void imprimirVacio(String mensaje) {
        String separador = "=".repeat(mensaje.length());
        System.out.println(separador);
        System.out.println(mensaje);
        System.out.println(separador);
    }

    protected String leerTexto(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = scanner.nextLine();
            if (Validaciones.textoNoVacio(texto)) {
                return texto.trim();
            }
            System.out.println("Error: el texto no puede estar vacío.");
        }
    }

    protected String leerTextoConDefault(String mensaje, String defaultValor) {
        System.out.print(mensaje);
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? defaultValor : input;
    }

    protected boolean leerSiNo(String mensaje) {
        while (true) {
            System.out.print(mensaje + " (s/n): ");
            String respuesta = scanner.nextLine().trim().toLowerCase();
            if (respuesta.equals("s")) return true;
            if (respuesta.equals("n")) return false;
            System.out.println("Error: ingrese 's' para sí o 'n' para no.");
        }
    }

    protected LocalDate leerFecha(String mensaje) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        while (true) {
            try {
                System.out.print(mensaje);
                return LocalDate.parse(scanner.nextLine().trim(), formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Error: ingrese la fecha en formato dd/MM/yyyy.");
            }
        }
    }
}
