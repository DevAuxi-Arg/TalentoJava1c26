import java.util.Scanner;
import com.techlab.articulos.menu.MenuArticulos;
import com.techlab.articulos.menu.MenuCategorias;
import com.techlab.articulos.menu.MenuPrincipal;
import com.techlab.articulos.repository.Repositorio;
import com.techlab.articulos.model.Articulo;
import com.techlab.articulos.model.Categoria;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Repositorio<Articulo> repoArticulos = new Repositorio<>();
        Repositorio<Categoria> repoCategorias = new Repositorio<>();
        MenuArticulos.precargarCategorias(repoCategorias);
        MenuArticulos menuArticulos = new MenuArticulos(scanner, repoArticulos, repoCategorias);
        MenuCategorias menuCategorias = new MenuCategorias(scanner, repoCategorias);
        MenuPrincipal menuPrincipal = new MenuPrincipal(scanner, menuArticulos, menuCategorias);
        menuPrincipal.ejecutar();
        scanner.close();
    }
}
