package com.techlab.articulos.model;

import com.techlab.articulos.interfaces.Calculable;
import java.time.LocalDate;

public class ArticuloAlimenticio extends Articulo implements Calculable {

    private static final double IVA = 0.21;

    private LocalDate fechaVencimiento;

    public ArticuloAlimenticio(int codigo, String nombre, double precio, String descripcion, Categoria categoria, LocalDate fechaVencimiento) {
        super(codigo, nombre, precio, descripcion, categoria);
        this.fechaVencimiento = fechaVencimiento;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
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
                ", fechaVencimiento=" + fechaVencimiento +
                ", categoría=" + (getCategoria() != null ? getCategoria().getNombre() : "sin categoría") +
                '}';
    }
}