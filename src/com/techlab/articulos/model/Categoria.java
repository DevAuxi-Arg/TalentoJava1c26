package com.techlab.articulos.model;

import com.techlab.articulos.interfaces.Identificable;

public class Categoria implements Identificable {

    private int codigo;
    private String nombre;
    private String descripcion;
    private TipoArticulo tipo;

    public Categoria(int codigo, String nombre, String descripcion, TipoArticulo tipo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoArticulo getTipo() {
        return tipo;
    }

    public void setTipo(TipoArticulo tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Categoría {" +
                "código=" + codigo +
                ", nombre='" + nombre + '\'' +
                ", tipo=" + tipo +
                ", descripción='" + descripcion + '\'' +
                '}';
    }
}
