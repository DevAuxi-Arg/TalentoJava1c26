package com.techlab.articulos.repository;

import java.util.ArrayList;
import java.util.List;

import com.techlab.articulos.interfaces.Identificable;

/**
 * CONSIGNA DE ESTA CLASE
 * ------------------------------------------------------------
 * Esta clase debe ser GENÉRICA.
 *
 * Debe modelarse así:
 * Repositorio<T extends Identificable>
 *
 * ¿Qué significa eso?
 * Que podrá trabajar con distintos tipos de objetos, siempre que esos
 * objetos tengan código.
 *
 * EJEMPLOS DE USO ESPERADOS
 * ------------------------------------------------------------
 * - Repositorio<Categoria>
 * - Repositorio<Articulo>
 *
 * ESTA CLASE DEBE GUARDAR LOS DATOS EN MEMORIA
 * ------------------------------------------------------------
 * Usando:
 * - ArrayList<T>
 *
 * MÉTODOS MÍNIMOS ESPERADOS
 * ------------------------------------------------------------
 * - agregar(T objeto)
 * - listar()
 * - buscarPorCodigo(int codigo)
 * - eliminar(T objeto)
 * - estaVacio()
 *
 * OBJETIVO DIDÁCTICO
 * ------------------------------------------------------------
 * Esta clase prepara el terreno para entender luego estructuras como:
 * JpaRepository<T, ID> en Spring Boot.
 */
public class Repositorio<T extends Identificable> {

    private ArrayList<T> lista = new ArrayList<>();

    public void agregar(T objeto) {
        lista.add(objeto);
    }

    public List<T> listar() {
        return lista;
    }

    public T buscarPorCodigo(int codigo) {
        for (T elemento : lista) {
            if (elemento.getCodigo() == codigo) {
                return elemento;
            }
        }
        return null;
    }

    public void eliminar(T objeto) {
        lista.remove(objeto);
    }

    public boolean estaVacio() {
        return lista.isEmpty();
    }
}
