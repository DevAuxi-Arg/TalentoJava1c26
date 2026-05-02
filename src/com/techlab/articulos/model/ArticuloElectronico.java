package com.techlab.articulos.model;

import com.techlab.articulos.interfaces.Calculable;

public class ArticuloElectronico extends Articulo implements Calculable {

    private static final double IVA = 0.105;

    public ArticuloElectronico(int codigo, String nombre, double precio, String descripcion, Categoria categoria) {
        super(codigo, nombre, precio, descripcion, categoria);
    }

    @Override
    public double calcularPrecioFinal() {
        return getPrecio() * (1 + IVA);
    }

    @Override
    public String toString() {
        return "ArticuloElectronico {" +
                "código=" + getCodigo() +
                ", nombre='" + getNombre() + '\'' +
                ", precio=" + getPrecio() +
                String.format(", precioFinal=%.2f", calcularPrecioFinal()) +
                " (IVA 10.5%)" +
                ", categoría=" + (getCategoria() != null ? getCategoria().getNombre() : "sin categoría") +
                '}';
    }
}
