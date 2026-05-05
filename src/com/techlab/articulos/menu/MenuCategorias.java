package com.techlab.articulos.menu;

import java.util.Scanner;

import com.techlab.articulos.model.Categoria;
import com.techlab.articulos.model.TipoArticulo;
import com.techlab.articulos.repository.Repositorio;
import com.techlab.articulos.utils.Secuencias;

/*
 * CLASE 3 - CATEGORÍA COMO OBJETO DENTRO DE ARTÍCULO
 * --------------------------------------------------
 * OBJETIVO DIDÁCTICO:
 * Hasta la clase anterior, el artículo tenía:
 * - código
 * - nombre
 * - precio
 *
 * Ahora damos un paso más en POO:
 * un objeto puede tener como atributo a OTRO objeto.
 *
 * En vez de guardar la categoría como texto, vamos a guardar un objeto Categoria.
 *
 * Esto es importante porque:
 * 1) modela mejor la realidad
 * 2) evita repetir datos sueltos
 * 3) prepara el camino para relaciones entre clases
 * 4) luego conecta muy bien con Spring Boot y bases de datos
 *
 * En esta etapa todavía NO hacemos CRUD de categorías.
 * Para no complejizar demasiado, trabajaremos con categorías PRECARGADAS.
 */
public class MenuCategorias extends Menu {

    private Repositorio<Categoria> repoCategorias;

    private static final String[] OPCIONES = {
        "1. Crear categoría",
        "2. Listar categorías",
        "3. Consultar por código",
        "4. Consultar por nombre",
        "5. Eliminar una categoría",
        "0. Volver al menú principal"
    };

    public MenuCategorias(Scanner scanner, Repositorio<Categoria> repoCategorias) {
        super(scanner);
        this.repoCategorias = repoCategorias;
    }

    @Override
    public void mostrarMenu() {
        imprimirMenu("MENÚ CATEGORÍAS", OPCIONES);
    }

    @Override
    public void ejecutar() {
        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            System.out.print("Seleccione una opción: ");
            String input = scanner.nextLine();
            switch (input) {
                case "1": crearCategoria(); break;
                case "2": listarCategorias(); break;
                case "3": consultarCategoriaPorCodigo(); break;
                case "4": consultarCategoriaPorNombre(); break;
                case "5": eliminarCategoria(); break;
                case "0":
                    salir = true;
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }







    private void listarCategorias() {
        System.out.println("\n--- CATEGORÍAS DISPONIBLES ---");

        if (repoCategorias.estaVacio()) {
            imprimirVacio("No existen categorías ingresadas.");
            pausar();
            return;
        }

        for (Categoria categoria : repoCategorias.listar()) {
            System.out.println(categoria);
        }
        pausar();
    }




    private void crearCategoria() {
        System.out.println("\n--- CREAR CATEGORÍA ---");

        int codigo = Secuencias.generarCodigoCategoria();

        String nombre = leerTexto("Ingrese el nombre de la categoría: ");
        String descripcion = leerTexto("Ingrese la descripción de la categoría: ");

        System.out.println("Tipo de artículos de esta categoría:");
        System.out.println("  1. Electrónico");
        System.out.println("  2. Alimenticio");
        System.out.println("  3. Otro");
        TipoArticulo tipo;
        while (true) {
            String opcion = leerTexto("Seleccione el tipo (1/2/3): ");
            if (opcion.equals("1")) { tipo = TipoArticulo.ELECTRONICO; break; }
            if (opcion.equals("2")) { tipo = TipoArticulo.ALIMENTICIO; break; }
            if (opcion.equals("3")) { tipo = TipoArticulo.OTRO; break; }
            System.out.println("Opción no válida. Intente de nuevo.");
        }

        repoCategorias.agregar(new Categoria(codigo, nombre, descripcion, tipo));
        System.out.println("Categoría creada con código " + codigo + ".");
        pausar();
    }

    private void consultarCategoriaPorCodigo() {
        System.out.println("\n--- CONSULTAR CATEGORÍA POR CÓDIGO ---");

        if (repoCategorias.estaVacio()) {
            System.out.println("No hay categorías cargadas.");
            pausar();
            return;
        }

        int codigo = leerEntero("Ingrese el código de la categoría: ");
        Categoria categoria = repoCategorias.buscarPorCodigo(codigo);

        if (categoria == null) {
            System.out.println("La categoría no existe.");
        } else {
            System.out.println("Categoría encontrada:");
            System.out.println(categoria);
        }
        pausar();
    }

    private void consultarCategoriaPorNombre() {
        System.out.println("\n--- CONSULTAR CATEGORÍA POR NOMBRE ---");

        if (repoCategorias.estaVacio()) {
            System.out.println("No hay categorías cargadas.");
            pausar();
            return;
        }

        String nombre = leerTexto("Ingrese el nombre de la categoría: ");

        boolean encontrada = false;
        for (Categoria categoria : repoCategorias.listar()) {
            if (categoria.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                System.out.println(categoria);
                encontrada = true;
            }
        }

        if (!encontrada) {
            System.out.println("No se encontró ninguna categoría con ese nombre.");
        }
        pausar();
    }

    private void eliminarCategoria() {
        System.out.println("\n--- ELIMINAR CATEGORÍA ---");

        if (repoCategorias.estaVacio()) {
            System.out.println("No hay categorías cargadas.");
            pausar();
            return;
        }

        int codigo = leerEntero("Ingrese el código de la categoría a eliminar: ");
        Categoria categoria = repoCategorias.buscarPorCodigo(codigo);

        if (categoria == null) {
            System.out.println("La categoría no existe.");
            pausar();
            return;
        }

        repoCategorias.eliminar(categoria);
        System.out.println("Categoría eliminada correctamente.");
        pausar();
    }
}

