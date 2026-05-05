package com.techlab.articulos.model;

import com.techlab.articulos.interfaces.Calculable;

public class ArticuloElectronico extends Articulo implements Calculable {

    private static final double IVA = 0.105;

    private int garantiaMeses;

    public ArticuloElectronico(int codigo, String nombre, double precio, String descripcion, Categoria categoria, int garantiaMeses) {
        super(codigo, nombre, precio, descripcion, categoria);
        this.garantiaMeses = garantiaMeses;
    }

    public int getGarantiaMeses() {
        return garantiaMeses;
    }

    public void setGarantiaMeses(int garantiaMeses) {
        this.garantiaMeses = garantiaMeses;
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
                ", garantía=" + garantiaMeses + " meses" +
                ", categoría=" + (getCategoria() != null ? getCategoria().getNombre() : "sin categoría") +
                '}';
    }
}
