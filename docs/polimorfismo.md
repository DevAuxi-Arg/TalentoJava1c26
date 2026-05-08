# 🔄 Polimorfismo en el Listado de Artículos

## ¿Cómo funciona el listado si hay productos con diferente estructura?

Cuando ejecutas **Listar Artículos** y has cargado `ArticuloAlimenticio`, `ArticuloElectronico` y `ArticuloGeneral` con atributos distintos, el sistema muestra correctamente cada uno respetando su propia estructura. Esto es posible gracias al **polimorfismo**.

---

## Concepto

### El repositorio guarda referencias de tipo base
```java
Repositorio<Articulo> repoArticulos
```

Aunque el repositorio declara que guarda `Articulo` (la clase base), en realidad almacena referencias a objetos que pueden ser:
- `ArticuloElectronico`
- `ArticuloAlimenticio`
- `ArticuloGeneral`

### El método `listarArticulos()` itera genéricamente

```java
private void listarArticulos() {
    System.out.println("\n--- LISTADO DE ARTÍCULOS ---");

    if (repoArticulos.estaVacio()) {
        imprimirVacio("No existen artículos ingresados.");
        pausar();
        return;
    }

    for (Articulo articulo : repoArticulos.listar()) {
        System.out.println(articulo);  // ← Aquí ocurre la magia
    }
    pausar();
}
```

Cuando llamas a `System.out.println(articulo)`, Java ejecuta el método `toString()` de la **clase real del objeto**, no de la clase base. Esto se llama **dispatch dinámico**.

---

## Ejemplo: Tres `toString()` diferentes

### ArticuloElectronico

```java
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
```

**Salida:**
```
ArticuloElectronico {código=1, nombre='Laptop', precio=1000.0, precioFinal=1105.00 (IVA 10.5%), garantía=24 meses, categoría=Electrónica}
```

---

### ArticuloAlimenticio

```java
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
```

**Salida:**
```
ArticuloAlimenticio {código=2, nombre='Leche', precio=50.0, precioFinal=60.50 (IVA 21%), fechaVencimiento=2026-12-31, categoría=Alimentos}
```

---

### ArticuloGeneral

```java
@Override
public String toString() {
    return "ArticuloGeneral {" +
            "código=" + getCodigo() +
            ", nombre='" + getNombre() + '\'' +
            ", precio=" + getPrecio() +
            String.format(", precioFinal=%.2f", calcularPrecioFinal()) +
            " (IVA 21%)" +
            ", categoría=" + (getCategoria() != null ? getCategoria().getNombre() : "sin categoría") +
            '}';
}
```

**Salida:**
```
ArticuloGeneral {código=3, nombre='Limpiador', precio=30.0, precioFinal=36.30 (IVA 21%), categoría=Limpieza}
```

---

## Resultado completo en consola

```
--- LISTADO DE ARTÍCULOS ---
ArticuloElectronico {código=1, nombre='Laptop', precio=1000.0, precioFinal=1105.00 (IVA 10.5%), garantía=24 meses, categoría=Electrónica}
ArticuloAlimenticio {código=2, nombre='Leche', precio=50.0, precioFinal=60.50 (IVA 21%), fechaVencimiento=2026-12-31, categoría=Alimentos}
ArticuloGeneral {código=3, nombre='Limpiador', precio=30.0, precioFinal=36.30 (IVA 21%), categoría=Limpieza}
```

**Cada línea muestra solo los atributos relevantes de su tipo**, aunque estén todos guardados en un mismo `Repositorio<Articulo>`.

---

## ¿Por qué es importante?

1. **Un contenedor genérico**: El repositorio no necesita saber qué tipo específico guarda; trabaja con la clase base `Articulo`.

2. **Representación correcta**: Cada subtipo muestra exactamente lo que le corresponde (garantía, fecha de vencimiento, o nada).

3. **Extensibilidad**: Si en el futuro agregas un nuevo tipo de artículo (ej: `ArticuloPromocional`), el listado funcionará automáticamente sin cambios en `listarArticulos()`.

4. **Cálculo dinámico del IVA**: Cada subtipo implementa `Calculable.calcularPrecioFinal()` con su propia lógica de IVA, y se invoca automáticamente al listar.

---

## Términos clave

| Término | Significado |
|---|---|
| **Polimorfismo** | Capacidad de un objeto de tomar múltiples formas (comportarse de manera diferente según su tipo real). |
| **Dispatch dinámico** | Decisión en tiempo de ejecución de cuál método invocar, basada en el tipo real del objeto. |
| **Herencia** | `ArticuloElectronico`, `ArticuloAlimenticio` y `ArticuloGeneral` heredan de `Articulo`. |
| **Sobrescritura** | Cada subtipo redefine `toString()` para mostrar su propia estructura. |
| **Interfaz Calculable** | Contrato común que garantiza que todo `Articulo` puede calcular su precio final. |
---

## 🔄 Infografía sobre Polimorfismo en ArrayList de Java 

<div align="center">
  <img src="../assets/listar.png" alt="Diagrama listar()" width="800">
</div>

---

## Conclusión

El polimorfismo permite que el código sea:
- ✅ **Simple**: Un solo método `listarArticulos()` que funciona para todos los tipos.
- ✅ **Flexible**: Soporta múltiples tipos sin cambios.
- ✅ **Mantenible**: Cada clase es responsable de su propia representación y cálculos.

[← Volver al README](../README.md#doc)