<div align="center">
  <img src="./assets/logo.png" alt="Logo" width="200">

  # 🗂️ Sistema CRUD — Artículos y Categorías
  
</div>


<table>
  <tr>
    <td valign="middle"><img src="./assets/toad.png" alt="Toad" width="100"></td>
    <td valign="middle">
      Sistema de gestión de artículos y categorías desarrollado en Java puro (sin frameworks). <br>
      Aplica principios de <strong>Programación Orientada a Objetos</strong>: herencia, abstracción, interfaces genéricas y el patrón Repositorio.
    </td>
  </tr>
</table>

---

## ✨ Características

- ✅ CRUD completo de **Artículos** (crear, listar, consultar, buscar, modificar, eliminar)
- ✅ CRUD completo de **Categorías**
- ✅ Subtipado de artículos: **Electrónico** (IVA 10.5%) y **Alimenticio** (IVA 21%)
- ✅ Cálculo de precio final con IVA aplicado al listar (polimorfismo via `Calculable`)
- ✅ Búsqueda parcial por nombre (contiene texto)
- ✅ Generación automática de códigos secuenciales
- ✅ Validaciones de entrada reutilizables
- ✅ Menús interactivos con limpieza de pantalla y pausas
- ✅ 4 categorías precargadas al inicio
- ✅ Repositorio genérico en memoria

---

## 🏗️ Arquitectura

```
src/
├── Main.java                              # Punto de entrada
└── com/techlab/articulos/
    ├── interfaces/
    │   ├── Identificable.java             # Contrato: int getCodigo()
    │   └── Calculable.java                # Contrato: calcularPrecioFinal()
    ├── model/
    │   ├── Categoria.java                 # Modelo: código, nombre, descripción
    │   ├── Articulo.java                  # Modelo base: código, nombre, precio, descripción, categoría
    │   ├── ArticuloAlimenticio.java       # Subtipo: implements Calculable — IVA 21%
    │   └── ArticuloElectronico.java       # Subtipo: implements Calculable — IVA 10.5%
    ├── repository/
    │   └── Repositorio.java               # Repositorio genérico <T extends Identificable>
    ├── menu/
    │   ├── Menu.java                      # Clase abstracta base para todos los menús
    │   ├── MenuPrincipal.java             # Menú raíz: rutea a Artículos o Categorías
    │   ├── MenuArticulos.java             # CRUD de artículos
    │   └── MenuCategorias.java            # CRUD de categorías
    └── utils/
        ├── Secuencias.java                # Generador de códigos auto-incrementales
        └── Validaciones.java              # Validadores estáticos reutilizables
```

---

## 🧩 Descripción de clases principales

### `Menu` (abstracta)
Base de todos los menús. Encapsula el `Scanner` compartido y expone métodos de utilidad:

| Método | Descripción |
|---|---|
| `imprimirMenu(titulo, opciones[])` | Renderiza el menú con separadores |
| `leerEntero(mensaje)` | Lee y valida un entero |
| `leerDouble(mensaje)` | Lee y valida un decimal no negativo |
| `leerTexto(mensaje)` | Lee un texto no vacío |
| `leerSiNo(mensaje)` | Lee confirmación s/n |
| `limpiarPantalla()` | Limpia la consola |
| `pausar()` | Espera Enter del usuario |

### `Articulo` y sus subtipos
`Articulo` es la clase base. Al crear un artículo se elige el tipo:

| Subtipo | IVA | `calcularPrecioFinal()` |
|---|---|---|
| `ArticuloElectronico` | 10.5% | `precio * 1.105` |
| `ArticuloAlimenticio` | 21% | `precio * 1.21` |

Al listar, el polimorfismo invoca automáticamente el `toString()` del subtipo correcto, mostrando el precio final con IVA incluido.

### `Calculable`
Interfaz con un único método `double calcularPrecioFinal()`. Implementada por `ArticuloElectronico` y `ArticuloAlimenticio` con lógicas distintas.

### `Identificable`
Interfaz con `int getCodigo()`. Implementada por `Articulo` y `Categoria`. Es el contrato que exige el repositorio genérico.

### `Repositorio<T extends Identificable>`
Almacenamiento en memoria con métodos: `agregar`, `listar`, `buscarPorCodigo`, `eliminar`, `estaVacio`.

### `Secuencias`
Clase utilitaria final con contadores estáticos. Genera códigos únicos para artículos y categorías automáticamente.

### `Validaciones`
Clase utilitaria final con validadores estáticos: `textoNoVacio`, `longitudMaxima`, `noNegativo` (para `int` y `double`).

---

## ⚙️ Requisitos

- **JDK 17** o superior
- No requiere frameworks ni dependencias externas

---

## 🔨 Compilación

Desde la raíz del proyecto:

```bash
javac -encoding UTF-8 -d bin src/Main.java src/com/techlab/articulos/**/*.java src/com/techlab/articulos/menu/*.java src/com/techlab/articulos/model/*.java src/com/techlab/articulos/repository/*.java src/com/techlab/articulos/interfaces/*.java src/com/techlab/articulos/utils/*.java
```

O usando la tarea de VS Code:

> **Terminal → Ejecutar tarea → Compilar Java**

---

## ▶️ Ejecución

```bash
java -cp bin Main
```

O usando la tarea de VS Code:

> **Terminal → Ejecutar tarea → Ejecutar Java**

---

## � UML:

## Diagrama de clases


<div align="center">
  <img src="./assets/uml.png" alt="Logo" width="800">
</div>


---

## �🗺️ Flujo de la aplicación

```
MenuPrincipal
├── 1. Artículos  →  MenuArticulos
│       ├── 1. Ingresar artículo
│       ├── 2. Listar todos
│       ├── 3. Consultar por código
│       ├── 4. Buscar por nombre
│       ├── 5. Modificar
│       ├── 6. Eliminar
│       ├── 7. Listar categorías
│       └── 0. Volver al menú principal
├── 2. Categorías →  MenuCategorias
│       ├── 1. Crear categoría
│       ├── 2. Listar todas
│       ├── 3. Consultar por código
│       ├── 4. Buscar por nombre
│       ├── 5. Eliminar
│       └── 0. Volver al menú principal
└── 0. Salir
```

---
## 👤 Autor: G.F. Escobar

Proyecto educativo desarrollado para **Talento Tech — Curso Java · 1° cuatrimestre 2026**.

**Profe:** Gisele  Milagros Gonzalez

Repositorio: [DevAuxi-Arg/TalentoJava1c26](https://github.com/DevAuxi-Arg/TalentoJava1c26)
