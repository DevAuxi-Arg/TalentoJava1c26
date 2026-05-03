package com.techlab.articulos.menu;

import java.util.Scanner;

public class MenuPrincipal extends Menu {

    private static final String[] OPCIONES = {
        "1. Menú de Artículos",
        "2. Menú de Categorías",
        "0. Salir"
    };

    private final MenuArticulos menuArticulos;
    private final MenuCategorias menuCategorias;

    public MenuPrincipal(Scanner scanner, MenuArticulos menuArticulos, MenuCategorias menuCategorias) {
        super(scanner);
        this.menuArticulos = menuArticulos;
        this.menuCategorias = menuCategorias;
    }

    @Override
    public void mostrarMenu() {
        imprimirMenu("MENÚ PRINCIPAL", OPCIONES);
    }

    @Override
    public void ejecutar() {
        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            System.out.print("Seleccione una opción: ");
            String input = scanner.nextLine();
            switch (input) {
                case "1": menuArticulos.ejecutar(); break;
                case "2": menuCategorias.ejecutar(); break;
                case "0":
                    salir = true;
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }
}
