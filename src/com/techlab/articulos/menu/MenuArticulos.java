package com.techlab.articulos.menu;

import java.util.Scanner;

import com.techlab.articulos.model.Articulo;
import com.techlab.articulos.model.ArticuloAlimenticio;
import com.techlab.articulos.model.ArticuloElectronico;
import com.techlab.articulos.model.Categoria;
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

public class MenuArticulos extends Menu {

    private Repositorio<Articulo> repoArticulos;
    private Repositorio<Categoria> repoCategorias;

    private static final String[] OPCIONES = {
        "1. Ingresar artículo",
        "2. Listar artículos",
        "3. Consultar por código",
        "4. Buscar por nombre",
        "5. Modificar un artículo",
        "6. Eliminar un artículo",
        "7. Listar categorías disponibles",
        "0. Volver al menú principal"
    };

    public MenuArticulos(Scanner scanner, Repositorio<Articulo> repoArticulos, Repositorio<Categoria> repoCategorias) {
        super(scanner);
        this.repoArticulos = repoArticulos;
        this.repoCategorias = repoCategorias;
    }

    @Override
    public void mostrarMenu() {
        imprimirMenu("MENÚ ARTÍCULOS", OPCIONES);
    }

    @Override
    public void ejecutar() {
        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            System.out.print("Seleccione una opción: ");
            String input = scanner.nextLine();
            switch (input) {
                case "1": ingresarArticulo(); break;
                case "2": listarArticulos(); break;
                case "3": consultarArticulo(); break;
                case "4": buscarArticuloPorNombre(); break;
                case "5": modificarArticulo(); break;
                case "6": eliminarArticulo(); break;
                case "7": listarCategorias(); break;
                case "0":
                    salir = true;
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    /*
     * MÉTODO: precargarCategorias
     * --------------------------------------------------
     * Este método crea algunas categorías de ejemplo y las agrega a la lista.
     *
     * ¿Por qué lo hacemos así en esta etapa?
     * Porque todavía no queremos agregar el CRUD de categorías.
     * Solo queremos que el alumno entienda que:
     * - existe una clase Categoria
     * - un Articulo tiene una Categoria
     * - el usuario elige una categoría existente
     */
    public static void precargarCategorias(Repositorio<Categoria> repoCategorias) {
        repoCategorias.agregar(new Categoria(Secuencias.generarCodigoCategoria(), "Electrónica", "Productos tecnológicos y electrónicos"));
        repoCategorias.agregar(new Categoria(Secuencias.generarCodigoCategoria(), "Periféricos", "Accesorios para computadora"));
        repoCategorias.agregar(new Categoria(Secuencias.generarCodigoCategoria(), "Alimentos", "Productos alimenticios"));
        repoCategorias.agregar(new Categoria(Secuencias.generarCodigoCategoria(), "Limpieza", "Artículos de limpieza del hogar"));
    }

    /*
     * MÉTODO: ingresarArticulo
     * --------------------------------------------------
     * Ahora, además de pedir código, nombre y precio,
     * también debemos pedir una categoría.
     *
     * Pero ya no será un String libre.
     * El usuario deberá elegir una categoría existente por código.
     */
    private void ingresarArticulo() {
        System.out.println("\n--- INGRESAR ARTÍCULO ---");

        int codigo = Secuencias.generarCodigoArticulo();

        String nombre = leerTexto("Ingrese el nombre del artículo: ");
        String descripcion = leerTexto("Ingrese la descripción del artículo: ");
        double precio = leerDouble("Ingrese el precio del artículo (sin IVA): ");

        listarCategorias();

        Categoria categoriaElegida = pedirCategoriaExistente();

        System.out.println("Tipo de artículo:");
        System.out.println("  1. Electrónico (IVA 10.5%)");
        System.out.println("  2. Alimenticio (IVA 21%)");
        String tipo = leerTexto("Seleccione el tipo (1/2): ");

        Articulo articulo;
        if (tipo.equals("1")) {
            articulo = new ArticuloElectronico(codigo, nombre, precio, descripcion, categoriaElegida);
        } else {
            articulo = new ArticuloAlimenticio(codigo, nombre, precio, descripcion, categoriaElegida);
        }
        repoArticulos.agregar(articulo);

        System.out.println("Artículo ingresado con código " + codigo + ".");
        pausar();
    }

    /*
     * MÉTODO: listarArticulos
     * --------------------------------------------------
     * Muestra todos los artículos guardados en memoria.
     */
    private void listarArticulos() {
        System.out.println("\n--- LISTADO DE ARTÍCULOS ---");

        if (repoArticulos.estaVacio()) {
            imprimirVacio("No existen artículos ingresados.");
            pausar();
            return;
        }

        for (Articulo articulo : repoArticulos.listar()) {
            System.out.println(articulo);
        }
        pausar();
    }

    private void buscarArticuloPorNombre() {
        System.out.println("\n--- BUSCAR ARTÍCULO POR NOMBRE ---");

        if (repoArticulos.estaVacio()) {
            imprimirVacio("No existen artículos ingresados.");
            pausar();
            return;
        }

        String termino = leerTexto("Ingrese el nombre o parte del nombre: ");

        boolean encontrado = false;
        for (Articulo articulo : repoArticulos.listar()) {
            if (articulo.getNombre().toLowerCase().contains(termino.toLowerCase())) {
                System.out.println(articulo);
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("No se encontró ningún artículo con ese nombre.");
        }
        pausar();
    }

    /*
     * MÉTODO: consultarArticulo
     * --------------------------------------------------
     * Busca un artículo por código y lo muestra.
     */
    private void consultarArticulo() {
        System.out.println("\n--- CONSULTAR ARTÍCULO ---");

        if (repoArticulos.estaVacio()) {
            System.out.println("No hay artículos cargados.");
            pausar();
            return;
        }

        int codigo = leerEntero("Ingrese el código del artículo a consultar: ");

        Articulo articulo = repoArticulos.buscarPorCodigo(codigo);

        if (articulo == null) {
            System.out.println("El artículo no existe.");
        } else {
            System.out.println("Artículo encontrado:");
            System.out.println(articulo);
        }
        pausar();
    }

    /*
     * MÉTODO: modificarArticulo
     * --------------------------------------------------
     * Permite modificar:
     * - nombre
     * - precio
     * - categoría
     *
     * Esto es importante porque ahora el alumno ve que también
     * se puede cambiar un atributo que es un objeto.
     */
    private void modificarArticulo() {
        System.out.println("\n--- MODIFICAR ARTÍCULO ---");

        if (repoArticulos.estaVacio()) {
            System.out.println("No hay artículos cargados.");
            pausar();
            return;
        }

        int codigo = leerEntero("Ingrese el código del artículo a modificar: ");

        Articulo articulo = repoArticulos.buscarPorCodigo(codigo);

        if (articulo == null) {
            System.out.println("El artículo no existe.");
            pausar();
            return;
        }

        String nuevoNombre = leerTexto("Ingrese el nuevo nombre del artículo: ");
        String nuevaDescripcion = leerTexto("Ingrese la nueva descripción del artículo: ");
        double nuevoPrecio = leerDouble("Ingrese el nuevo precio del artículo: ");

        listarCategorias();
        Categoria nuevaCategoria = pedirCategoriaExistente();

        articulo.setNombre(nuevoNombre);
        articulo.setDescripcion(nuevaDescripcion);
        articulo.setPrecio(nuevoPrecio);
        articulo.setCategoria(nuevaCategoria);

        System.out.println("Artículo modificado correctamente.");
        pausar();
    }

    /*
     * MÉTODO: eliminarArticulo
     * --------------------------------------------------
     * Elimina un artículo por código.
     */
    private void eliminarArticulo() {
        System.out.println("\n--- ELIMINAR ARTÍCULO ---");

        if (repoArticulos.estaVacio()) {
            System.out.println("No hay artículos cargados.");
            pausar();
            return;
        }

        int codigo = leerEntero("Ingrese el código del artículo a eliminar: ");

        Articulo articulo = repoArticulos.buscarPorCodigo(codigo);

        if (articulo == null) {
            System.out.println("El artículo no existe.");
            pausar();
            return;
        }

        repoArticulos.eliminar(articulo);

        System.out.println("Artículo eliminado correctamente.");
        pausar();
    }

    /*
     * MÉTODO: listarCategorias
     * --------------------------------------------------
     * Muestra las categorías precargadas disponibles.
     *
     * Esto ayuda a que el usuario sepa qué opciones puede elegir
     * al momento de ingresar o modificar un artículo.
     */
    private void listarCategorias() {
        System.out.println("\n--- CATEGORÍAS DISPONIBLES ---");

        for (Categoria categoria : repoCategorias.listar()) {
            System.out.println(categoria);
        }
    }

    /*
     * MÉTODO: pedirCategoriaExistente
     * --------------------------------------------------
     * Este método obliga al usuario a elegir una categoría válida.
     *
     * ¿Qué hace?
     * 1) pide el código de la categoría
     * 2) busca si existe
     * 3) si no existe, vuelve a pedirlo
     */
    private Categoria pedirCategoriaExistente() {

        while (true) {
            int codigoCategoria = leerEntero("Ingrese el código de la categoría: ");

            Categoria categoria = repoCategorias.buscarPorCodigo(codigoCategoria);

            if (categoria != null) {
                return categoria;
            }

            System.out.println("Error: la categoría no existe.");
        }
    }

}

