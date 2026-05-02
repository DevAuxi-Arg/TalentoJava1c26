package com.techlab.articulos.model;

import com.techlab.articulos.interfaces.Calculable;

public class ArticuloAlimenticio extends Articulo implements Calculable {

    private static final double IVA = 0.21;

    public ArticuloAlimenticio(int codigo, String nombre, double precio, String descripcion, Categoria categoria) {
        super(codigo, nombre, precio, descripcion, categoria);
    }

    @Override
    public double calcularPrecioFinal() {
        return getPrecio() * (1 + IVA);
    }

    @Override
    public String toString() {
        return "ArticuloAlimenticio {" +
                "código=" + getCodigo() +
                ", nombre='" + getNombre() + '\'' +
                ", precio=" + getPrecio() +
                String.format(", precioFinal=%.2f", calcularPrecioFinal()) +
                " (IVA 21%)" +
                ", categoría=" + (getCategoria() != null ? getCategoria().getNombre() : "sin categoría") +
                '}';
    }
}
